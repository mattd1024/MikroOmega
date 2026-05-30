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
    private final String WINDOWS_TITLEBAR_COLOR = "0a0a0a";
    private final String ICON_PATH = "/images/icon.png";
    private final String FONT_PATH = "/fonts/Nunito-Regular.ttf";
    private final boolean RESIZABLE = false;

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
        stage.setResizable(RESIZABLE);
        stage.show();

        // Set a specific color for the Windows 11 title bar
        // this MIGHT crash on win 10 idk
        System.err.println("WARNING: Topbar color styling is implemented for Windows 11, windows 10 is untested");
        ThemeWindowManager base = ThemeWindowManagerFactory.create();
        base.setDarkModeForWindowFrame(stage, true);

        if (base instanceof Win11ThemeWindowManager manager) {
            manager.setWindowFrameColor(stage, Color.web(WINDOWS_TITLEBAR_COLOR));
        }
    }
}