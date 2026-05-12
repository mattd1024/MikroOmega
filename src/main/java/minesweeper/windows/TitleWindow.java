package minesweeper.windows;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import minesweeper.model.Difficulty;

public class TitleWindow {
    private Stage stage;

    public TitleWindow(Stage stage) {
        this.stage = stage;
    }

    /**
     * Show the menu screen. Contains: start button, choose difficulty, end button.
     */
    public void show() {
        // Main title
        Label mainLabel = new Label("Minesweeper");
        mainLabel.setFont(Font.font("MONOSPACE", FontWeight.BOLD, 16));

        // Choose difficulty
        ToggleGroup group = new ToggleGroup();
        RadioButton easy = new RadioButton("Easy");
        RadioButton medium = new RadioButton("Medium");
        RadioButton hard = new RadioButton("Hard");
        easy.setToggleGroup(group);
        medium.setToggleGroup(group);
        hard.setToggleGroup(group);
        easy.setSelected(true);
        easy.setMaxWidth(Double.MAX_VALUE);
        medium.setMaxWidth(Double.MAX_VALUE);
        hard.setMaxWidth(Double.MAX_VALUE);

        VBox difficultyOptions = new VBox(easy,medium,hard);
        difficultyOptions.setPadding(new Insets(10));
        difficultyOptions.setAlignment(Pos.CENTER);

        // Exit button
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> {
            stage.close();
        });

        // Start button
        Button startButton = new Button("Start");
        startButton.setOnAction(e -> {
            Difficulty chosenDifficulty = (Difficulty) group.getSelectedToggle().getUserData();
            new MainWindow().show(stage, chosenDifficulty);
        });

        // Assign all created components to root
        VBox root = new VBox(mainLabel, startButton, difficultyOptions, exitButton);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));

        // Stage configuration
        stage.setScene(new Scene(root,300,350));
        stage.setTitle("Minesweeper");
        stage.setHeight(350);
        stage.setWidth(300);
        stage.show();
    }
}
