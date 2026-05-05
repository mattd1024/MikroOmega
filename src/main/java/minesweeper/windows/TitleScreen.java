package minesweeper.windows;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TitleScreen {
    private Stage stage;

    public TitleScreen(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        ToggleGroup group = new ToggleGroup();
        RadioButton easy = new RadioButton("Easy");
        RadioButton medium = new RadioButton("Medium");
        RadioButton hard = new RadioButton("Hard");
        easy.setToggleGroup(group);
        medium.setToggleGroup(group);
        hard.setToggleGroup(group);
        easy.setSelected(true);
        easy.setMaxWidth(Double.MAX_VALUE);
        medium.setMaxWidth(Double.MAX_VALUE);
        hard.setMaxWidth(Double.MAX_VALUE);

        VBox difficultyOptions = new VBox(easy,medium,hard);
        difficultyOptions.setPadding(new Insets(10));
        difficultyOptions.setAlignment(Pos.CENTER);


        VBox root = new VBox(difficultyOptions);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));


        stage.setScene(new Scene(root,300,350));
        stage.setTitle("Minesweeper");
        stage.setHeight(350);
        stage.setWidth(300);
        stage.show();
    }
}
