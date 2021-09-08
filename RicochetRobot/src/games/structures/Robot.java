package games.structures;

import utils.Color;
import utils.Direction;

public class Robot extends Obstacle {

    private Color color;
    private Direction direction;

    public Robot(Color color, int i, int j) {
        super(i, j);
        this.color = color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return this.direction;
    }

}
