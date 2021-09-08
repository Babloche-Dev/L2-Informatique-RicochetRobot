package games.structures;

import utils.Cardinal;

public class Wall extends Obstacle {

    private Cardinal cardinal;

    public Wall(Cardinal cardinal, int i, int j) {
        super(i,j);
        this.cardinal = cardinal;
    }

    public Cardinal getCardinal() {
        return cardinal;
    }

    public void setCardinal(Cardinal cardinal) {
        this.cardinal = cardinal;
    }
}
