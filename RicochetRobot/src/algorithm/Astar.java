package algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

import games.structures.Box;
import games.Grid;
import games.structures.Robot;
import games.structures.Target;
import games.structures.Wall;
import utils.Cardinal;
import utils.Couple;
import utils.Direction;
import utils.Tuple;

public class Astar {
    Grid grid;

    public Astar(Grid grid) {
        this.grid = grid;
    }

    public int compare2Nodes(Node n1, Node n2) {
        if (n1.heuristic < n2.heuristic) {
            return 1;
        } else if (n1.heuristic == n2.heuristic) {
            return 0;
        } else {
            return -1;
        }
    }

    public ArrayList<Node> reconstructPath(HashMap<Node, Node> cameFrom, Node node) {
        ArrayList<Node> totalPath = new ArrayList<Node>();
        while (cameFrom.containsKey(node)) {
            totalPath.add(0, node);
            node = cameFrom.get(node);
        }

        return totalPath;
    }

    public ArrayList<Node> shortestPath(Node start, Robot robot, Target target, boolean optimisation) {
        double optimizer;
        ArrayList<Node> closedList = new ArrayList<>();
        final Comparator<Node> comparator = (node1, node2) -> compare2Nodes(node1, node2);
        PriorityQueue<Node> openList = new PriorityQueue<>(comparator);
        HashMap<Node, Node> cameFrom = new HashMap<Node, Node>();
        HashMap<Node, Double> cost = new HashMap<Node, Double>();
        openList.add(start);
        cost.put(start, 0.0);

        HashSet<Robot> copySet = new HashSet<>(start.robots.keySet());
        copySet.remove(robot);

        while (!openList.isEmpty()) {
            Node u = openList.poll();
            if (u.robots.get(robot).getA() == target.getPosition(0)
                    && u.robots.get(robot).getB() == target.getPosition(1)) {
                return reconstructPath(cameFrom, u);

            }

            ArrayList<Node> uNeighbour = getNeighbour(u, robot, target);

            // Boucle pour le déplacement des 4 robots
            /*
             * for (Robot eachrobot : copySet) { ArrayList<Node> eachRobotNeighbour =
             * getNeighbour(u, eachrobot, target); uNeighbour.addAll(eachRobotNeighbour); }
             */

            int compteur = 0;
            while (compteur < uNeighbour.size()) {
                Node v = uNeighbour.get(compteur);
                if (optimisation == true) {
                    optimizer = manhattanDistance(v, robot, target);
                } else {
                    optimizer = 0;
                }
                if (!closedList.contains(v) && !(openList.contains(v) && cost.get(v) < (cost.get(u) + 1))) {
                    cameFrom.put(v, u);
                    v.heuristic = (cost.get(u) + 1) + optimizer;
                    openList.add(v);
                    cost.put(v, (cost.get(u) + 1));

                }
                compteur += 1;
            }
            closedList.add(u);
            cost.remove(u);
            openList.remove(u);

        }
        return null;
    }

    @SuppressWarnings("all")
    public ArrayList<Node> getNeighbour(Node node, Robot robot, Target target) {
        ArrayList<Node> neighbour = new ArrayList<>();
        if (!isWall(node, robot, Direction.UP)) {
            HashMap<Robot, Couple<Integer, Integer>> copyrobots = new HashMap<Robot, Couple<Integer, Integer>>(
                    node.robots);

            Couple<Integer, Integer> coupleXY = new Couple((copyrobots.get(robot).getA() + Direction.UP.getX()),
                    (copyrobots.get(robot).getB() + Direction.UP.getY()));

            copyrobots.put(robot, coupleXY);
            Node newNode = new Node(robot, copyrobots, 0);
            boolean test = false;
            while (!test) {
                if (!isWall(newNode, robot, Direction.UP)) {
                    newNode.robots.get(robot).setA(newNode.robots.get(robot).getA() + Direction.UP.getX());
                    newNode.robots.get(robot).setB(newNode.robots.get(robot).getB() + Direction.UP.getY());
                    if (newNode.robots.get(robot).getA() == target.getPosition(0)
                            && newNode.robots.get(robot).getB() == target.getPosition(1)
                            && robot.getColor() == target.getColor()) {
                        test = true;
                    }
                } else {
                    test = true;
                }

            }

            neighbour.add(newNode);
        }
        if (!isWall(node, robot, Direction.RIGHT)) {
            HashMap<Robot, Couple<Integer, Integer>> copyrobots = new HashMap<Robot, Couple<Integer, Integer>>(
                    node.robots);
            Couple<Integer, Integer> coupleXY = new Couple((copyrobots.get(robot).getA() + Direction.RIGHT.getX()),
                    (copyrobots.get(robot).getB() + Direction.RIGHT.getY()));
            copyrobots.put(robot, coupleXY);
            Node newNode = new Node(robot, copyrobots, 0);

            boolean test = false;
            while (!test) {
                if (!isWall(newNode, robot, Direction.RIGHT)) {
                    newNode.robots.get(robot).setA(newNode.robots.get(robot).getA() + Direction.RIGHT.getX());
                    newNode.robots.get(robot).setB(newNode.robots.get(robot).getB() + Direction.RIGHT.getY());
                    if (newNode.robots.get(robot).getA() == target.getPosition(0)
                            && newNode.robots.get(robot).getB() == target.getPosition(1)
                            && robot.getColor() == target.getColor()) {
                        test = true;
                    }
                } else {
                    test = true;
                }
            }

            neighbour.add(newNode);
        }
        if (!isWall(node, robot, Direction.DOWN)) {
            HashMap<Robot, Couple<Integer, Integer>> copyrobots = new HashMap<Robot, Couple<Integer, Integer>>(
                    node.robots);
            Couple<Integer, Integer> coupleXY = new Couple((copyrobots.get(robot).getA() + Direction.DOWN.getX()),
                    (copyrobots.get(robot).getB() + Direction.DOWN.getY()));

            copyrobots.put(robot, coupleXY);
            Node newNode = new Node(robot, copyrobots, 0);

            boolean test = false;
            while (!test) {
                if (!isWall(newNode, robot, Direction.DOWN)) {
                    newNode.robots.get(robot).setA(newNode.robots.get(robot).getA() + Direction.DOWN.getX());
                    newNode.robots.get(robot).setB(newNode.robots.get(robot).getB() + Direction.DOWN.getY());
                    if (newNode.robots.get(robot).getA() == target.getPosition(0)
                            && newNode.robots.get(robot).getB() == target.getPosition(1)
                            && robot.getColor() == target.getColor()) {
                        test = true;
                    }
                } else {
                    test = true;
                }

            }

            neighbour.add(newNode);
        }
        if (!isWall(node, robot, Direction.LEFT)) {
            HashMap<Robot, Couple<Integer, Integer>> copyrobots = new HashMap<Robot, Couple<Integer, Integer>>(
                    node.robots);
            Couple<Integer, Integer> coupleXY = new Couple((copyrobots.get(robot).getA() + Direction.LEFT.getX()),
                    (copyrobots.get(robot).getB() + Direction.LEFT.getY()));

            copyrobots.put(robot, coupleXY);
            Node newNode = new Node(robot, copyrobots, 0);

            boolean test = false;
            while (!test) {
                if (!isWall(newNode, robot, Direction.LEFT)) {
                    newNode.robots.get(robot).setA(newNode.robots.get(robot).getA() + Direction.LEFT.getX());
                    newNode.robots.get(robot).setB(newNode.robots.get(robot).getB() + Direction.LEFT.getY());
                    if (newNode.robots.get(robot).getA() == target.getPosition(0)
                            && newNode.robots.get(robot).getB() == target.getPosition(1)
                            && robot.getColor() == target.getColor()) {
                        test = true;
                    }
                } else {
                    test = true;
                }

            }

            neighbour.add(newNode);
        }

        return neighbour;

    }

    public boolean isWall(Node node, Robot robot, Direction direction) {
        int coordRobotX = node.robots.get(robot).getA();
        int coordRobotY = node.robots.get(robot).getB();
        int coordDirectionX = direction.getX();
        int coordDirectionY = direction.getY();

        if ((coordRobotX + coordDirectionX < 0) || (coordRobotX + coordDirectionX > 15)
                || (coordRobotY + coordDirectionY < 0) || (coordRobotY + coordDirectionY > 15)) {
            return true;
        }

        Tuple<Box, Box, Box> box = grid.getBoard()
                .get(new Couple<Integer, Integer>(coordRobotX + coordDirectionX, coordRobotY + coordDirectionY));
        if (box.getA() instanceof Wall) {
            if (box.getC() instanceof Target) {

                if (direction == Direction.UP && ((Wall) box.getA()).getCardinal() != Cardinal.SOUTH_WEST
                        && ((Wall) box.getA()).getCardinal() != Cardinal.SOUTH_WEST) {

                    return false;
                }
                if (direction == Direction.DOWN && ((Wall) box.getA()).getCardinal() != Cardinal.NORTH_WEST
                        && ((Wall) box.getA()).getCardinal() != Cardinal.NORTH_EAST) {

                    return false;
                }
                if (direction == Direction.RIGHT && ((Wall) box.getA()).getCardinal() != Cardinal.SOUTH_WEST
                        && ((Wall) box.getA()).getCardinal() != Cardinal.NORTH_WEST) {

                    return false;
                }
                if (direction == Direction.LEFT && ((Wall) box.getA()).getCardinal() != Cardinal.NORTH_EAST
                        && ((Wall) box.getA()).getCardinal() != Cardinal.SOUTH_WEST) {

                    return false;
                }
            }

            return true;
        }

        if (box.getB() instanceof Robot) {

            return true;
        }

        return false;

    }

    public double manhattanDistance(Node node, Robot robot, Target target) {
        return Math.abs(target.getPosition(0) - node.robots.get(robot).getA())
                + Math.abs(target.getPosition(1) - node.robots.get(robot).getB());
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
