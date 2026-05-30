package minesweeper.windows;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import minesweeper.model.Difficulty;

import java.util.Objects;

public class TitleWindow {
    private final int MAX_CUSTOM_ROWS = 30;
    private final int MAX_CUSTOM_COLS = 50;
    private final int MAX_CUSTOM_MINES = 1491;

    /**
     * Show the menu screen. Contains: start button, choose difficulty, end button.
     */
    public Scene getScene(Stage stage) {
        // Main title
        Label mainLabel = new Label("Minesweeper");
        mainLabel.setFont(Font.font("MONOSPACE", FontWeight.BOLD, 26));

        // Choose difficulty buttons
        ToggleGroup group = new ToggleGroup();
        RadioButton easy = new RadioButton("Easy");
        RadioButton medium = new RadioButton("Medium");
        RadioButton hard = new RadioButton("Hard");
        RadioButton custom = new RadioButton("Custom");

        //
        easy.setToggleGroup(group);
        medium.setToggleGroup(group);
        hard.setToggleGroup(group);
        custom.setToggleGroup(group);

        easy.setMaxWidth(Double.MAX_VALUE);
        medium.setMaxWidth(Double.MAX_VALUE);
        hard.setMaxWidth(Double.MAX_VALUE);
        custom.setMaxWidth(Double.MAX_VALUE);

        // Choose difficulty logic
        easy.setUserData(Difficulty.EASY);
        medium.setUserData(Difficulty.MEDIUM);
        hard.setUserData(Difficulty.HARD);

        easy.setSelected(true);

        // Difficulty options
        VBox difficultyOptions = new VBox(easy,medium,hard,custom);
        difficultyOptions.setPadding(new Insets(10));
        difficultyOptions.setAlignment(Pos.CENTER);

        // Custom difficulty
        Spinner<Integer> rowsSpinner = new Spinner<>(1, MAX_CUSTOM_ROWS, 0);
        Spinner<Integer> colsSpinner = new Spinner<>(1, MAX_CUSTOM_COLS, 0);
        Spinner<Integer> minesSpinner = new Spinner<>(1, MAX_CUSTOM_MINES, 0);

        // Change width so the text next to them is visible
        rowsSpinner.setPrefWidth(60);
        colsSpinner.setPrefWidth(60);
        minesSpinner.setPrefWidth(60);

        // Make it possible to paste numbers into spinners
        rowsSpinner.setEditable(true);
        colsSpinner.setEditable(true);
        minesSpinner.setEditable(true);

        HBox customDifficultyOptions = new HBox(
                new Label("Rows: "), rowsSpinner,
                new Label("Cols: "), colsSpinner,
                new Label ("Mines: "), minesSpinner);
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

            stage.setScene(new MainWindow().getScene(stage, chosenDifficulty));
        });

        // Exit button
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> {
            stage.close();
        });


        // Assign all created components to root
        VBox root = new VBox(mainLabel, startButton, difficultyOptions, customDifficultyOptions, exitButton);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));

        // Load CSS
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/titleWindow.css")).toExternalForm());

        // Return finished TitleWindow scene
        return scene;
    }
}
