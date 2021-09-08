package graphics;

import games.structures.Robot;
import games.structures.Target;
import games.structures.Wall;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import utils.Cardinal;

public class Tile extends Rectangle {

    public Tile(int x, int y){
        setWidth(Start.tileSize);
        setHeight(Start.tileSize);

        relocate(x * Start.tileSize, y * Start.tileSize);

        setFill(Color.valueOf("#ffffff"));
        setStroke(Color.DARKGRAY);
    }

    public static StackPane placeObject(Object object){
        Image image;
        StackPane sp = new StackPane();
        if(object instanceof Robot) {
            if (((Robot) object).getColor() == utils.Color.RED) {
                image = new Image("file:resources/redRobot.png");
            } else if (((Robot) object).getColor() == utils.Color.BLUE) {
                image = new Image("file:resources/blueRobot.png");
            } else if (((Robot) object).getColor() == utils.Color.GREEN) {
                image = new Image("file:resources/greenRobot.png");
            } else {
                image = new Image("file:resources/yellowRobot.png");
            }
        }
        else if(object instanceof Target){
            if(((Target)object).getColor()==utils.Color.RED){
                image = new Image("file:resources/redTarget.png");
            }else if(((Target)object).getColor()==utils.Color.BLUE) {
                image = new Image("file:resources/blueTarget.png");
            }else if(((Target)object).getColor()==utils.Color.GREEN) {
                image = new Image("file:resources/greenTarget.png");
            }else if(((Target)object).getColor()==utils.Color.YELLOW){
                image = new Image("file:resources/yellowTarget.png");
            }else{
                image = new Image("file:resources/galaxyTarget.png");
            }
        }
        else{
            if(((Wall)object).getCardinal()== Cardinal.NORTH){
                image = new Image("file:resources/topWall.png");
            }else if(((Wall)object).getCardinal()== Cardinal.SOUTH) {
                image = new Image("file:resources/bottomWall.png");
            }else if(((Wall)object).getCardinal()== Cardinal.EAST) {
                image = new Image("file:resources/rightWall.png");
            }else if(((Wall)object).getCardinal()== Cardinal.WEST) {
                image = new Image("file:resources/leftWall.png");
            }else if(((Wall)object).getCardinal()== Cardinal.NORTH_EAST) {
                image = new Image("file:resources/topRightWall.png");
            }else if(((Wall)object).getCardinal()== Cardinal.NORTH_WEST) {
                image = new Image("file:resources/topLeftWall.png");
            }else if(((Wall)object).getCardinal()== Cardinal.SOUTH_EAST) {
                image = new Image("file:resources/bottomRightWall.png");
            }else if(((Wall)object).getCardinal()== Cardinal.SOUTH_WEST){
                image = new Image("file:resources/bottomLeftWall.png");
            } else{
                image = new Image("file:resources/lock.png");
            }

        }
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(40);
            imageView.setFitWidth(40);
            sp.getChildren().add(imageView);
            return sp;
    }
}
