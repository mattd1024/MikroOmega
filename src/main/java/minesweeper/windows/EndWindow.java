package minesweeper.windows;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import minesweeper.MainApp;
import minesweeper.model.Difficulty;

import java.util.Objects;

public class EndWindow {
    private final int WINDOW_WIDTH = 400;
    private final int WINDOW_HEIGHT = 450;

    public Scene getScene(Stage stage, double totalTime, Difficulty chosenDifficulty, boolean won) {
        // Change size of window
        stage.setWidth(WINDOW_WIDTH);
        stage.setHeight(WINDOW_HEIGHT);

        // Labels
        Label statusLabel = new Label(won ? "You won!" : "You exploded!");
        Label totalTimeLabel = new Label("Total time: " + String.format("%.2f", totalTime) + "s");
        Label difficultyLabel = new Label("Difficulty: " + chosenDifficulty.getDescription());

        // Title button
        Button titleButton = new Button("Title");
        titleButton.setOnAction(e -> {
            new MainApp().start(stage);
        });

        // Exit button
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> {
            stage.close();
        });

        // Root
        VBox root = new VBox(statusLabel, totalTimeLabel, difficultyLabel, titleButton, exitButton);

        // Add CSS
        statusLabel.getStyleClass().add("status-label");
        totalTimeLabel.getStyleClass().add("info-label");
        difficultyLabel.getStyleClass().add("info-label");
        exitButton.getStyleClass().add("exit-button");
        titleButton.getStyleClass().add("title-button");

        // Load CSS
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/end.css")).toExternalForm());

        // Return finished TitleWindow scene
        return scene;
    }
}
