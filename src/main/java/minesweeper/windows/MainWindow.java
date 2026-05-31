package minesweeper.windows;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import minesweeper.model.Cell;
import minesweeper.model.Difficulty;
import minesweeper.model.Game;

import java.util.Objects;

public class MainWindow implements Window {
    private Game game;
    private Stage stage;
    private final Difficulty chosenDifficulty;
    private double elapsedTime;
    private Timeline timeline;
    private Button[][] cellButtons; // For visually editing the cells (for logic, use game.getBoard())
    private Label flagLabel;

    public MainWindow(Difficulty chosenDifficulty) {
        this.chosenDifficulty = chosenDifficulty;
    }

    /**
     * Get the main window scene
     */
    @Override
    public Scene getScene(Stage stage) {
        this.stage = stage;

        // Change width and height of window based on cell count
        int newWidth = chosenDifficulty.getCols() * 40;
        int newHeight = chosenDifficulty.getRows() * 40 + 75;
        // Make sure it has a minimum width and height, based on ratio
        if (newWidth < 400) {
            double ratio = (double) newHeight / newWidth;
            newWidth = 400;
            newHeight = (int) (newWidth * ratio);
        }
        if (newHeight < 450) {
            double ratio = (double) newWidth / newHeight;
            newHeight = 450;
            newWidth = (int) (newHeight * ratio);
        }
        stage.setWidth(newWidth);
        stage.setHeight(newHeight);

        // Create game object
        game = new Game(chosenDifficulty);

        // Flag label
        flagLabel = new Label("⚑ " + game.getRemainingFlags());

        // Reset button
        Button resetButton = new Button("RESET");
        resetButton.setOnAction(e -> {
            elapsedTime = 0;
            timeline.stop();
            stage.setScene(new MainWindow(chosenDifficulty).getScene(stage));
        });

        // Timer label
        Label timerLabel = new Label("⏰ 0:00");

        // Upper horizontal info box
        Region leftSpacer = new Region();
        Region rightSpacer = new Region();
        HBox.setHgrow(leftSpacer, Priority.ALWAYS);
        HBox.setHgrow(rightSpacer, Priority.ALWAYS);
        HBox infoBox = new HBox(flagLabel, leftSpacer, resetButton, rightSpacer, timerLabel);

        // Spreading the children (funi ahaha)
        HBox.setHgrow(resetButton, Priority.ALWAYS);

        // Add CSS classes to the horizontal info box
        infoBox.getStyleClass().add("info-box");
        flagLabel.getStyleClass().add("info-label");
        timerLabel.getStyleClass().addAll("info-label");
        resetButton.getStyleClass().add("reset-button");

        // Initialize the timeline
        timeline = new Timeline(new KeyFrame(Duration.millis(10), e -> {
            elapsedTime += 0.01;
            timerLabel.setText("⏰ " + String.format("%.2f", elapsedTime));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Game box
        GridPane cellGrid = new GridPane();
        cellGrid.getStyleClass().addAll("mine-grid");
        cellButtons = new Button[game.getRows()][game.getCols()];
        for (int r = 0; r < game.getRows(); r++) {
            for (int c = 0; c < game.getCols(); c++) {
                Button btn = createButton(r, c);
                cellButtons[r][c] = btn;
                cellGrid.add(btn, c, r);
            }
        }

        // Make cols and rows spread themselves across the window
        for (int i = 0; i < game.getCols(); i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(100.0 / game.getCols());
            cellGrid.getColumnConstraints().add(cc);
        }
        for (int i = 0; i < game.getRows(); i++) {
            RowConstraints rc = new RowConstraints();
            rc.setPercentHeight(100.0 / game.getRows());
            cellGrid.getRowConstraints().add(rc);
        }
        VBox.setVgrow(cellGrid, Priority.ALWAYS);
        cellGrid.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // Root
        VBox root = new VBox(infoBox, cellGrid);

        // Load CSS
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/main.css")).toExternalForm());

        // Return finished TitleWindow scene
        return scene;
    }

    /**
     * Creates grid buttons
     */
    public Button createButton(int r, int c) {
        Button btn = new Button();
        btn.getStyleClass().addAll("mine-cell", "mine-cell-unrevealed");
        btn.setFocusTraversable(false);
        btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        btn.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                handleLeftClick(r, c);
            } else if (event.getButton() == MouseButton.SECONDARY) {
                handleRightClick(r, c);
            }
        });

        return btn;
    }

    /**
     * Handles left clicking
     */
    public void handleLeftClick(int r, int c) {
        Cell cell = game.getCells()[r][c];

        // 1. Cell is flagged || revealed -> do nothing
        if (cell.isFlagged() || cell.isRevealed()) {
            return;
        }

        // 2. Is a first click -> build mines around first click and begin timer
        if (game.isFirstClick()) {
            // Build mines around
            game.placeMines(r, c);
            game.setFirstClick(false);

            // Timer
            timeline.play();
        }

        // 3. Is a mine -> explode
        if (cell.isMine()) {
            timeline.stop();
            cell.setRevealed(true);
            updateButtonVisuals(r, c);
            stage.setScene(new EndWindow(elapsedTime, chosenDifficulty, game.isWon()).getScene(stage));
        }

        // 4. Is safe -> reveal
        if (!cell.isMine()) {
            game.floodReveal(r, c);
            for (int row = 0; row < game.getRows(); row++) {
                for (int col = 0; col < game.getCols(); col++) {
                    updateButtonVisuals(row, col);
                }
            }
        }
        // Check if the game has been won
        if (game.isWon()) {
            // Stuff to do before going to the end window

            timeline.stop();

            // Go to the end window
            stage.setScene(new EndWindow(elapsedTime, chosenDifficulty, game.isWon()).getScene(stage));
        }
    }

    /**
     * Handles right-clicking
     */
    public void handleRightClick(int r, int c) {
        Cell cell = game.getCells()[r][c];

        // 1. Cell is revealed -> do nothing
        if (cell.isRevealed()) {
            return;
        }

        // 2. Cell is not flagged -> flag it
        if (!cell.isFlagged()) {
            // Check if we have enough flags to use one
            if (game.getRemainingFlags() > 0) {
                cell.setFlagged(true);
                updateButtonVisuals(r, c);

                // Remove one flag from remaining flags
                game.decrementRemainingFlags();
                // Update flag label
                flagLabel.setText("⚑ " + game.getRemainingFlags());
            }


        // 3. Cell is flagged -> unflag it
        } else {
            cell.setFlagged(false);
            updateButtonVisuals(r, c);

            // Add one flag to remaining flags
            game.incrementRemainingFlags();
            // Update flag label
            flagLabel.setText("⚑ " + game.getRemainingFlags());
        }
    }

    /**
     * Updates the UI of a cell based on its stats
     */
    public void updateButtonVisuals(int r, int c) {
        Cell cell = game.getCells()[r][c];
        Button btn = cellButtons[r][c];

        btn.getStyleClass().removeAll("mine-cell-unrevealed", "mine-cell-flagged", "mine-cell-revealed");
        btn.getStyleClass().removeIf(style -> style.startsWith("num-"));

        if (cell.isFlagged()) {
            btn.getStyleClass().add("mine-cell-flagged");
            btn.setText("⚑");
        } else if(cell.isRevealed()) {
            btn.getStyleClass().add("mine-cell-revealed");

            if (cell.isMine()) {
                btn.setText("✸");
            } else {
                int n = cell.getAdjacentMines();
                if (n != 0) {
                    btn.setText(String.valueOf(n));
                    btn.getStyleClass().add("num-" + n);
                } else {
                    btn.setText("");
                }
            }
        } else {
            btn.getStyleClass().add("mine-cell-unrevealed");
            btn.setText("");
        }
    }
}
