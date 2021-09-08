package games;

import algorithm.Astar;
import algorithm.Node;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        Grid grid = new Grid();
        grid.setInitGrid();
        grid.externWallGenerator();
        grid.internWallGenerator();
        grid.setRobot();
        grid.setTarget();
        grid.setCurrentRobot();

        System.out.println("Couleur actuelle : " + grid.currentTarget.getColor());
        long debut = System.currentTimeMillis();
        Node start = new Node(grid.currentRobot, grid.getRobots(), 0);
        System.out.println("Robot à sa position initiale : x : " + grid.currentRobot.getPosition(0) + " y :"
                + grid.currentRobot.getPosition(1));
        System.out.println("Position de la target : x : " + grid.currentTarget.getPosition(0) + " y : "
                + grid.currentTarget.getPosition(1));

        Astar astar = new Astar(grid);
        astar.shortestPath(start, grid.currentRobot, grid.currentTarget, true);
        printPath(astar.shortestPath(start, grid.currentRobot, grid.currentTarget, true));

        System.out.println("Taille du chemin trouvé : "
                + astar.shortestPath(start, grid.currentRobot, grid.currentTarget, true).size());
        System.out.println("Programme terminé");

        System.out.println("Temps en millisecondes : ");
        System.out.println(System.currentTimeMillis() - debut + "ms");

    }

    public static void printPath(ArrayList<Node> path) {
        for (int i = 0; i < path.size(); i++) {
            path.get(i).robots.entrySet().forEach(entry -> {
                System.out.println(" X : " + entry.getValue().getA() + " Y : " + entry.getValue().getB());
            });
        }
    }

}
