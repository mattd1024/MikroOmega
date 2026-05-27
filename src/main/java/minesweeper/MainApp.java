package minesweeper;

import javafx.application.Application;
import javafx.stage.Stage;
import minesweeper.windows.TitleWindow;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        stage.setScene(new TitleWindow().getScene(stage));
        stage.setTitle("Minesweeper");
        stage.show();

        //TODO add styling to everything
    }
}