package minesweeper;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import minesweeper.windows.TitleScreen;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        TitleScreen ts = new TitleScreen(stage);
        ts.show();
    }

    public static void main(String[] args) {
        launch();
    }
}