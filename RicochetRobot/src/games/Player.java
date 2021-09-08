package games;

public class Player {

    private String name;

    private int score;

    private int path;

    public Player(String name) {

        this.name = name;

    }

    public String getName() {

        return name;

    }

    public int getPath() {

        return path;

    }

    public int getScore() {

        return score;

    }

    public void setPath(int path) {

        this.path = path;

    }

    public void setPoint(int point) {

        this.score += point;

    }

}
