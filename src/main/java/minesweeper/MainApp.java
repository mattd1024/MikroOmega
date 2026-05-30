package minesweeper;

import com.pixelduke.window.ThemeWindowManagerFactory;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import minesweeper.windows.TitleWindow;
import com.pixelduke.window.ThemeWindowManager;
import com.pixelduke.window.Win11ThemeWindowManager;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) {
        // Load fonts
        Font.loadFont(getClass().getResourceAsStream("/fonts/Nunito-Regular.ttf"), 14);

        stage.setScene(new TitleWindow().getScene(stage));
        stage.setTitle("Minesweeper");
        stage.setWidth(400);
        stage.setHeight(450);
        stage.setResizable(false);
        stage.show();

        // Set a specific color for the windows 11 title bar
        ThemeWindowManager base = ThemeWindowManagerFactory.create();

        if (base instanceof Win11ThemeWindowManager manager) {
            manager.setWindowFrameColor(stage, Color.web("5865F2"));
        }

        //TODO add styling to everything
    }
}