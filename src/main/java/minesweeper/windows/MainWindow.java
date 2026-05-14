package minesweeper.windows;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import minesweeper.model.Difficulty;
import minesweeper.model.Game;

public class MainWindow {
    private Stage stage;
    private Game game;

    /**
     * Show the main window. Contains: main mine sweeping field
     */
    public void show(Stage stage, Difficulty chosenDifficulty) {
        // TODO finish main game window
        game = new Game(chosenDifficulty);

        // Upper horizontal info box
        Label testLabel = new Label("nigga");
        HBox infoBox = new HBox(testLabel);

        // Game box
        GridPane gameGrid = new GridPane();
        Button[][] buttons = new Button[game.getRows()][game.getCols()];
        for (int r = 0; r < game.getRows(); r++) {
            for (int c = 0; c < game.getCols(); c++) {
                Button btn = createButton();
                buttons[r][c] = btn;
                gameGrid.add(btn, c, r);
            }
        }

        // Root
        VBox root = new VBox(infoBox, gameGrid);

        // Stage configuration
        stage.setScene(new Scene(root));
        stage.setHeight(350);
        stage.setWidth(300);
        stage.show();
    }

    public Button createButton() {
        Button btn = new Button();
        return btn;
    }
}
