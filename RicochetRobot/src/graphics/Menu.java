package graphics;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class Menu {

    Stage window;
    Scene menu;

    public Menu() {
        window = new Stage();

        Button startButton = new Button("START");
        startButton.setStyle("-fx-background-color: #b30000;");
        startButton.setTextFill(Color.WHITE);
        startButton.setOnAction(e -> new Start());

        Button exitButton = new Button("EXIT");
        exitButton.setStyle("-fx-background-color: #b30000;");
        exitButton.setTextFill(Color.WHITE);
        exitButton.setOnAction(e -> closeProgram());
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });

        VBox menuLayout = new VBox(50);
        menuLayout.getChildren().addAll(startButton, exitButton);
        menuLayout.setAlignment(Pos.CENTER);
        BackgroundImage background = new BackgroundImage(new Image("file:resources/menu.JPG"),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
                new BackgroundSize(200, 200, false, false, false, false));
        menuLayout.setBackground(new Background(background));
        menu = new Scene(menuLayout, 600, 600);

        window.setScene(menu);
        window.setResizable(false);
        window.setTitle("Ricochet Robot");
        window.getIcons().add(new Image("file:resources/redRobotIcon.png"));
        window.show();
    }

    public void closeProgram() {
        boolean answer = ConfirmBox.display("Don't! :(", "Are you sure that you wanna quit? :(");
        if (answer) {
            window.close();
        }
    }
}
