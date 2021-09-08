package games.structures;

import utils.Color;

import java.util.ArrayList;

public class Target implements Box {

    private Color color;
    ArrayList<Integer> position = new ArrayList<>();
    private Boolean isEmpty = true;

    public Target(Color color) {
        this.color = color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public boolean IsEmpty() {
        return this.isEmpty;
    }

    public void setEmpty(Boolean empty) {
        this.isEmpty = empty;
    }

    public void setPosition(int i, int j) {
        position.add(0, i);
        position.add(1, j);
    }

    public int getPosition(int i) {
        return this.position.get(i);
    }

    public ArrayList<Integer> getPosition() {
        return this.position;
    }

    @Override
    public boolean isLockCase() {
        return false;
    }
}
