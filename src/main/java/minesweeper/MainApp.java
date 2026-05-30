package minesweeper;

import com.pixelduke.window.ThemeWindowManagerFactory;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import minesweeper.windows.TitleWindow;
import com.pixelduke.window.ThemeWindowManager;
import com.pixelduke.window.Win11ThemeWindowManager;

import java.io.InputStream;

public class MainApp extends Application {
    private final String WINDOWS_TITLEBAR_COLOR = "5865F2";
    private final String ICON_PATH = "/images/icon.png";
    private final String FONT_PATH = "/fonts/Nunito-Regular.ttf";

    @Override
    public void start(Stage stage) {
        // Load icon
        InputStream iconStream = getClass().getResourceAsStream(ICON_PATH);
        if (iconStream != null) {
            stage.getIcons().add(new Image(iconStream));
        } else {
            System.err.println("Icon not found");
        }

        // Load fonts
        InputStream fontStream = getClass().getResourceAsStream(FONT_PATH);
        if (fontStream != null) {
            Font.loadFont(fontStream, 14);
        } else {
            System.err.println("Font not found");
        }

        // Create stage
        stage.setScene(new TitleWindow().getScene(stage));
        stage.setTitle("Minesweeper");
        stage.setWidth(400);
        stage.setHeight(450);
        stage.setResizable(false);
        stage.show();

        // Set a specific color for the Windows 11 title bar
        ThemeWindowManager base = ThemeWindowManagerFactory.create();
        if (base instanceof Win11ThemeWindowManager manager) {
            manager.setWindowFrameColor(stage, Color.web(WINDOWS_TITLEBAR_COLOR));
        }

        //TODO add styling to everything
    }
}