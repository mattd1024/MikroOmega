package minesweeper;

import com.pixelduke.window.ThemeWindowManagerFactory;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import minesweeper.windows.TitleWindow;
import com.pixelduke.window.ThemeWindowManager;
import com.pixelduke.window.Win11ThemeWindowManager;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) {
        stage.setScene(new TitleWindow().getScene(stage));
        stage.setTitle("Minesweeper");
        stage.show();

        // Set a specific color for the windows 11 title bar
        ThemeWindowManager base =
                ThemeWindowManagerFactory.create();

        if (base instanceof Win11ThemeWindowManager manager) {

            manager.setWindowFrameColor(stage, Color.web("5865F2"));
        }

        //TODO add styling to everything
    }
}