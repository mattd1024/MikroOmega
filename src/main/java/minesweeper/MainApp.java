package minesweeper;

import javafx.application.Application;
import javafx.stage.Stage;
import minesweeper.windows.TitleWindow;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        new TitleWindow().show(stage);
    }
}