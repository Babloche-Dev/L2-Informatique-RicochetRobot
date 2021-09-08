package graphics;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

//The class that makes sure that you want to leave the menu after clicking on exit with the yes and no buttons.

public class ConfirmBox {
    static boolean answer;

    public static boolean display(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        window.setMinHeight(150);
        Label label = new Label();
        label.setText(message);

        Button yesButton = new Button("Yes");
        yesButton.setStyle("-fx-background-color: #ffffff;");
        Button noButton = new Button("No");
        noButton.setStyle("-fx-background-color: #ffffff;");

        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });
        noButton.setOnAction(e -> {
            answer = false;
            window.close();
        });

        VBox confirmBoxLayout = new VBox(10);
        confirmBoxLayout.getChildren().addAll(label, yesButton, noButton);
        confirmBoxLayout.setAlignment(Pos.CENTER);
        confirmBoxLayout.setStyle("-fx-background-color: #ffd633");
        Scene confirmBox = new Scene(confirmBoxLayout);
        window.setScene(confirmBox);
        window.getIcons().add(new Image("file:resources/redRobotIcon.png"));
        window.showAndWait();

        return answer;
    }
}
