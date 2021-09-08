package algorithm;

import games.structures.Robot;
import utils.Couple;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class Node {
    double heuristic;
    public HashMap<Robot, Couple<Integer, Integer>> robots;
    public Robot robot;

    public Node(Robot robot, HashMap<Robot, Couple<Integer, Integer>> robots, double heuristic) {
        this.robot = robot;
        this.robots = new HashMap<Robot, Couple<Integer, Integer>>(robots);
        this.heuristic = heuristic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass())
            return false;

        if (o instanceof Node) {
            Node node = (Node) o;
            HashSet<Couple<Integer, Integer>> oSet = new HashSet<>(node.robots.values());
            HashSet<Couple<Integer, Integer>> thisSet = new HashSet<>(this.robots.values());
            if (thisSet.equals(oSet)) {
                return true;
            }
        }

        return false;

    }

    @Override
    public int hashCode() {
        return Objects.hash(robots);
    }

}
