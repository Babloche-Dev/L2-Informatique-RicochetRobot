package games.structures;

import java.util.ArrayList;



public class Obstacle implements Box {

    ArrayList<Integer> position;

    public Obstacle(int i, int j) {
        this.position = new ArrayList<Integer>();
        this.position.add(i);
        this.position.add(j);
    }

    public ArrayList<Integer> getPosition() {
        return position;
    }

    public int getPosition(int i) {
        return position.get(i);
    }

    public void setPosition(ArrayList<Integer> position) {
        this.position = position;
    }

    public void setPosition(int x, int y) {
        this.position.set(0, x);
        this.position.set(1, y);
    }

    @Override
    public boolean isLockCase() {
        return true;
    }
}
