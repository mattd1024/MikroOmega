package minesweeper.windows;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import minesweeper.model.Difficulty;

public class EndWindow {

    public Scene getScene(Stage stage, double totalTime, Difficulty chosenDifficulty, boolean won) {
        // Labels
        Label gameStatusLabel = new Label(won ? "You won!" : "You lost!");
        Label totalTimeLabel = new Label("Total time: " + String.format("%.2f", totalTime) + "s");
        Label difficultyLabel = new Label("Difficulty: " + chosenDifficulty.getDescription());

        // Root
        VBox root = new VBox(gameStatusLabel, totalTimeLabel, difficultyLabel);

        // Load CSS
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/styles/endWindow.css").toExternalForm());

        // Return finished TitleWindow scene
        return scene;
    }
}
