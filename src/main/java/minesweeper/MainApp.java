package minesweeper;

import javafx.application.Application;
import javafx.stage.Stage;
import minesweeper.windows.TitleWindow;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        TitleWindow ts = new TitleWindow(stage);
        ts.show();
    }

    public static void main(String[] args) {
        launch();
    }
}