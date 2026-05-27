package minesweeper.windows;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import minesweeper.model.Cell;
import minesweeper.model.Difficulty;
import minesweeper.model.Game;

public class MainWindow {
    private Game game;
    private Button cellButtons[][]; // For visually editing the cells (for logic, use game.getBoard())
    private Stage stage;
    private Difficulty chosenDifficulty;
    private double elapsedTime;
    private Timeline timeline;

    /**
     * Show the main window. Contains: main mine sweeping field
     */
    public Scene getScene(Stage stage, Difficulty chosenDifficulty) {
        this.stage = stage;
        this.chosenDifficulty = chosenDifficulty;

        game = new Game(chosenDifficulty);

        // Upper horizontal info box
        Label testLabel = new Label("slepickametlicka");
        Label timerLabel = new Label();
        HBox infoBox = new HBox(testLabel, timerLabel);

        // Initialize the timeline
        timeline = new Timeline(new KeyFrame(Duration.millis(10), e -> {
            elapsedTime += 0.01;
            timerLabel.setText(String.format("%.2f", elapsedTime));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Game box
        GridPane cellGrid = new GridPane();
        cellButtons = new Button[game.getRows()][game.getCols()];
        for (int r = 0; r < game.getRows(); r++) {
            for (int c = 0; c < game.getCols(); c++) {
                Button btn = createButton(r, c);
                cellButtons[r][c] = btn;
                cellGrid.add(btn, c, r);
            }
        }

        // Root
        VBox root = new VBox(infoBox, cellGrid);

        // Stage configuration
        return new Scene(root, 300, 350);
    }

    /**
     * Creates buttons
     * @param r
     * @param c
     * @return
     */
    public Button createButton(int r, int c) {
        Button btn = new Button();

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
     * @param r
     * @param c
     */
    public void handleLeftClick(int r, int c) {
        Cell cell = game.getCells()[r][c];

        // 1. Cell is flagged || revealed -> do nothingh
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
            cellButtons[r][c].setText("💥");
            stage.setScene(new EndWindow().getScene(stage, elapsedTime, chosenDifficulty, game.isWon()));
        }

        // 4. Is safe -> reveal
        if (!cell.isMine()) {
            floodReveal(r, c);
        }
        // Check if the game has been won
        if (game.isWon()) {
            timeline.stop();
            stage.setScene(new EndWindow().getScene(stage, elapsedTime, chosenDifficulty, game.isWon()));
        }
    }

    /**
     * Handles right clicking
     * @param r
     * @param c
     */
    public void handleRightClick(int r, int c) {
        Cell cell = game.getCells()[r][c];

        // 1. Cell is revealed -> do nothingh
        if (cell.isRevealed()) {
            return;
        }

        // 2. Cell is not flagged -> flag it
        if (!cell.isFlagged()) {
            cell.setFlagged(true);
            updateButtonVisuals(r, c);
        // 3. Cell is flagged -> unflag it
        } else {
            cell.setFlagged(false);
            updateButtonVisuals(r, c);
        }
    }

    /**
     * Recursive method for revealing all empty cells around an empty cell
     * @param r
     * @param c
     */
    public void floodReveal(int r, int c) {
        // Check if the coordinates are valid
        if (!game.inBounds(r, c)) {
            return;
        }

        // Get the respective cell
        Cell cell = game.getCells()[r][c];

        // If the cell is revealed or flagged, skip
        if (cell.isRevealed() || cell.isFlagged()) {
            return;
        }

        cell.setRevealed(true);
        updateButtonVisuals(r, c);

        // Go over surrounding cells without choosing the same one
        if (cell.getAdjacentMines() == 0) {
            for (int dr = -1; dr <= 1; dr++) {
                for (int dc = -1; dc <= 1; dc++) {
                    if (!(dr == 0 && dc == 0)) { // Dont want to call floodReveal on the same cell indefinitely
                        floodReveal(r + dr, c + dc);
                    }
                }
            }// Only flood reveal if there are no surrounding mines
        }

    }

    /**
     * Updates the UI of a cell basen on its stats
     * @param r
     * @param c
     */
    public void updateButtonVisuals(int r, int c) {
        Cell cell = game.getCells()[r][c];
        Button btn = cellButtons[r][c];

        if (cell.isFlagged()) {
            btn.setText("🚩");
        } else if(cell.isRevealed()) {
            if (cell.isMine()) {
                btn.setText("💥");
            } else {
                int n = cell.getAdjacentMines();
                if (n != 0) {
                    btn.setText(String.valueOf(n));
                } else {
                    btn.setText("  ");
                }
            }
        } else {
            btn.setText("  ");
        }
    }
}
