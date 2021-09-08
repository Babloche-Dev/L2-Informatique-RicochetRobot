package graphics;

import algorithm.Astar;
import algorithm.Node;
import games.structures.Box;
import games.Grid;
import games.structures.Robot;
import games.structures.Target;
import games.structures.Wall;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.Cardinal;
import utils.Color;
import utils.Couple;
import utils.Tuple;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class Start {

    HashMap<Couple<Integer, Integer>, Tuple<Box, Box, Box>> board;
    Stage window;
    Scene start;
    Grid grid;
    String firstText = "";

    public static final int tileSize = 40;
    public static final int width = 24;
    public static final int height = 16;

    public Start() {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        grid = getGrid();

        start = new Scene(createContent());

        window.setScene(start);
        window.setTitle("Ricochet Robot");
        window.getIcons().add(new Image("file:resources/redRobotIcon.png"));
        window.setResizable(false);
        window.show();
    }

    // The method that creates our initial board with all its members.
    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(width * tileSize, height * tileSize);

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                Tile tile = new Tile(i, j);
                root.getChildren().add(tile);
            }
        }

        board = returnBoard();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                Tuple<Box, Box, Box> value = board.get(new Couple<Integer, Integer>(i, j));
                if (value.getA() instanceof Wall) {
                    if (((Wall) value.getA()).getCardinal() == Cardinal.NORTH) {
                        addToRoot(value.getA(), root, j, i);
                    } else if (((Wall) value.getA()).getCardinal() == Cardinal.SOUTH) {
                        addToRoot(value.getA(), root, j, i);
                    } else if (((Wall) value.getA()).getCardinal() == Cardinal.WEST) {
                        addToRoot(value.getA(), root, j, i);
                    } else if (((Wall) value.getA()).getCardinal() == Cardinal.EAST) {
                        addToRoot(value.getA(), root, j, i);
                    } else if (((Wall) value.getA()).getCardinal() == Cardinal.NORTH_EAST) {
                        addToRoot(value.getA(), root, j, i);
                    } else if (((Wall) value.getA()).getCardinal() == Cardinal.NORTH_WEST) {
                        addToRoot(value.getA(), root, j, i);
                    } else if (((Wall) value.getA()).getCardinal() == Cardinal.SOUTH_EAST) {
                        addToRoot(value.getA(), root, j, i);
                    } else if (((Wall) value.getA()).getCardinal() == Cardinal.SOUTH_WEST) {
                        addToRoot(value.getA(), root, j, i);
                    } else {
                        addToRoot(value.getA(), root, j, i);
                    }
                }
                if (value.getC() instanceof Target) {
                    addToRoot(value.getC(), root, j, i);
                }
                if (value.getB() instanceof Robot) {
                    if (((Robot) value.getB()).getColor() == Color.RED) {
                        addToRoot(value.getB(), root, j, i);
                    } else if (((Robot) value.getB()).getColor() == Color.BLUE) {
                        addToRoot(value.getB(), root, j, i);
                    } else if (((Robot) value.getB()).getColor() == Color.GREEN) {
                        addToRoot(value.getB(), root, j, i);
                    } else if (((Robot) value.getB()).getColor() == Color.YELLOW) {
                        addToRoot(value.getB(), root, j, i);
                    }
                }
            }
        }

        Rectangle shape = new Rectangle();
        shape.setX(700);
        shape.setY(20);
        shape.setWidth(200);
        shape.setHeight(600);
        shape.setFill(javafx.scene.paint.Color.DARKGRAY);

        // The button which implements the Astar algorithm.
        Button startButton = new Button("START");
        startButton.setLayoutX(775);
        startButton.setLayoutY(600);
        startButton.setStyle("-fx-background-color: #b30000;");
        startButton.setTextFill(javafx.scene.paint.Color.WHITE);
        startButton.setOnAction(e -> {
            if (getShortestPath() != null) {
                StackPane sp = Tile.placeObject(grid.currentRobot);
                sp.setLayoutX(grid.currentTarget.getPosition(1) * tileSize);
                sp.setLayoutY(grid.currentTarget.getPosition(0) * tileSize);
                Tile tile = new Tile(grid.currentRobot.getPosition(1), grid.currentRobot.getPosition(0));
                Text text = new Text(createText(getShortestPath()));
                text.setLayoutX(760);
                text.setLayoutY(40);
                text.setFont(Font.font("verdana", FontPosture.REGULAR, 9));
                root.getChildren().addAll(sp, tile, text);
            } else {
                Text text = new Text("Aucune route trouvée");
                text.setLayoutX(750);
                text.setLayoutY(300);
                root.getChildren().add(text);
            }
        });
        root.getChildren().addAll(shape, startButton);

        return root;
    }

    private Grid getGrid() {
        Grid grid = new Grid();
        grid.setInitGrid();
        grid.externWallGenerator();
        grid.internWallGenerator();
        grid.setRobot();
        grid.setTarget();
        grid.setCurrentRobot();
        // grid.testRobot();
        return grid;
    }

    private HashMap<Couple<Integer, Integer>, Tuple<Box, Box, Box>> returnBoard() {
        return grid.getBoard();
    }

    public Pane addToRoot(Object object, Pane root, int j, int i) {
        StackPane sp = Tile.placeObject(object);
        sp.setLayoutX(j * tileSize);
        sp.setLayoutY(i * tileSize);
        root.getChildren().add(sp);
        return root;
    }

    public ArrayList<Node> getShortestPath() {
        Node start = new Node(grid.currentRobot, grid.getRobots(), 0);
        Astar astar = new Astar(grid);
        ArrayList<Node> path = astar.shortestPath(start, grid.currentRobot, grid.currentTarget, true);
        System.out.println(path);
        return path;
    }

    public String createText(ArrayList<Node> path) {
        String newligne = System.getProperty("line.separator");
        for (int i = 0; i < path.size(); i++) {
            path.get(i).robots.entrySet().forEach(entry -> {
                firstText += "X : " + entry.getValue().getA() + "          Y : " + entry.getValue().getB() + newligne;
            });
        }
        return firstText;
    }
}