package minesweeper.windows;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import minesweeper.model.Difficulty;

import java.util.Objects;

public class TitleWindow implements Window {
    private final int WINDOW_WIDTH = 400;
    private final int WINDOW_HEIGHT = 450;
    private final int MAX_CUSTOM_ROWS = 30;
    private final int MAX_CUSTOM_COLS = 50;
    private final int MAX_CUSTOM_MINES = 1490;

    /**
     * Get the title window scene
     */
    @Override
    public Scene getScene(Stage stage) {
        // Set window size
        stage.setWidth(WINDOW_WIDTH);
        stage.setHeight(WINDOW_HEIGHT);

        // Title label
        Label titleLabel = new Label("Minesweeper");

        // Choose difficulty buttons
        ToggleGroup group = new ToggleGroup();
        RadioButton easy = new RadioButton("Easy");
        RadioButton medium = new RadioButton("Medium");
        RadioButton hard = new RadioButton("Hard");
        RadioButton custom = new RadioButton("Custom");

        // Assign buttons to radio group
        easy.setToggleGroup(group);
        medium.setToggleGroup(group);
        hard.setToggleGroup(group);
        custom.setToggleGroup(group);

        // Set width of difficulties to max
        easy.setMaxWidth(Double.MAX_VALUE);
        medium.setMaxWidth(Double.MAX_VALUE);
        hard.setMaxWidth(Double.MAX_VALUE);
        custom.setMaxWidth(Double.MAX_VALUE);

        // Choose difficulty logic
        easy.setUserData(Difficulty.EASY);
        medium.setUserData(Difficulty.MEDIUM);
        hard.setUserData(Difficulty.HARD);

        // Select easy as default difficulty
        easy.setSelected(true);

        // Difficulty options
        VBox difficultyOptions = new VBox(easy,medium,hard,custom);
        difficultyOptions.setAlignment(Pos.CENTER);
        difficultyOptions.setPrefWidth(150);

        // Custom difficulty
        Spinner<Integer> rowsSpinner = new Spinner<>(2, MAX_CUSTOM_ROWS, 0);
        Spinner<Integer> colsSpinner = new Spinner<>(2, MAX_CUSTOM_COLS, 0);
        Spinner<Integer> minesSpinner = new Spinner<>(1, MAX_CUSTOM_MINES, 0);

        // Change width so the text next to them is visible
        rowsSpinner.setPrefWidth(65);
        colsSpinner.setPrefWidth(65);
        minesSpinner.setPrefWidth(65);

        // Make it possible to paste numbers into spinners
        rowsSpinner.setEditable(true);
        colsSpinner.setEditable(true);
        minesSpinner.setEditable(true);

        // Prevent text from being typed into spinners (only want integers)
        restrictToNumbers(rowsSpinner);
        restrictToNumbers(colsSpinner);
        restrictToNumbers(minesSpinner);

        // Custom difficulty rows cols mines
        VBox rowsDifficulty = new VBox(new Label("Rows"), rowsSpinner);
        VBox colsDifficulty = new VBox(new Label("Cols"), colsSpinner);
        VBox minesDifficulty = new VBox(new Label("Mines"), minesSpinner);

        //Add them to a hbox
        HBox customDifficultyOptions = new HBox(rowsDifficulty,colsDifficulty,minesDifficulty);

        // Make spinners visible only if the custom radio button is selected
        customDifficultyOptions.visibleProperty().bind(custom.selectedProperty());
        customDifficultyOptions.managedProperty().bind(custom.selectedProperty());

        // Start button
        Button startButton = new Button("Start");
        startButton.setOnAction(e -> {
            Difficulty chosenDifficulty;

            // Handle custom difficulty
            if (custom.isSelected()) {
                chosenDifficulty = new Difficulty("Custom", rowsSpinner.getValue(), colsSpinner.getValue(), minesSpinner.getValue());
                chosenDifficulty.setDescription("Custom: " + rowsSpinner.getValue() + "x" + colsSpinner.getValue() + "x" + minesSpinner.getValue());
                // Check if the custom difficulty set by the user is safe
                if (!chosenDifficulty.isValid()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, chosenDifficulty.getValidationError());
                    alert.showAndWait();
                    return;
                }
            } else {
                chosenDifficulty = (Difficulty) group.getSelectedToggle().getUserData();
            }

            stage.setScene(new MainWindow(chosenDifficulty).getScene(stage));
        });

        // Exit button
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> {
            stage.close();
        });

        // Add CSS
        titleLabel.getStyleClass().add("title-label");
        startButton.getStyleClass().add("start-button");
        exitButton.getStyleClass().add("exit-button");
        difficultyOptions.getStyleClass().add("difficulty-options");
        customDifficultyOptions.getStyleClass().add("custom-difficulty-options");
        rowsDifficulty.getStyleClass().add("spinner-box");
        colsDifficulty.getStyleClass().add("spinner-box");
        minesDifficulty.getStyleClass().add("spinner-box");

        // Assign all created components to root
        VBox root = new VBox(titleLabel, startButton, difficultyOptions, customDifficultyOptions, exitButton);

        // Load CSS
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/title.css")).toExternalForm());

        // Return finished TitleWindow scene
        return scene;
    }

    /**
     * Restricts spinners to only use integers
     */
    private void restrictToNumbers(Spinner<Integer> spinner) {
        spinner.getEditor().textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*")) {
                spinner.getEditor().setText(oldVal);
            }
        });
    }

}
