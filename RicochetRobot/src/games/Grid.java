package games;

import games.structures.*;
import games.structures.Void;
import utils.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Grid {

    private HashMap<Couple<Integer, Integer>, Tuple<Box, Box, Box>> board;
    private HashMap<Robot, Couple<Integer, Integer>> robots;
    public Target currentTarget;
    public Robot currentRobot;

    public Grid() {
        board = new HashMap<Couple<Integer, Integer>, Tuple<Box, Box, Box>>();
        robots = new HashMap<Robot, Couple<Integer, Integer>>();

    }

    //Cette methode permet de générer un plateau générique contenant seulement les murs de délimitations et la zone centrale
    public void setInitGrid() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                Couple<Integer, Integer> key = new Couple<Integer, Integer>(i, j);
                Box box;
                if ((i == 7 && j == 8) || (i == 8 && j == 7) || (i == 8 && j == 8) || (i == 7 && j == 7)) {
                    box = new Wall(Cardinal.CENTER, i, j);
                } else {
                    if (i == 0 && j == 0) {
                        box = new Wall(Cardinal.NORTH_WEST, i, j);
                    } else if (i == 0 && j == 15) {
                        box = new Wall(Cardinal.NORTH_EAST, i, j);
                    } else if (i == 15 && j == 0) {
                        box = new Wall(Cardinal.SOUTH_WEST, i, j);
                    } else if (i == 15 && j == 15) {
                        box = new Wall(Cardinal.SOUTH_EAST, i, j);
                    } else if (i == 0) {
                        box = new Wall(Cardinal.NORTH, i, j);
                    } else if (i == 15) {
                        box = new Wall(Cardinal.SOUTH, i, j);
                    } else if (j == 0) {
                        box = new Wall(Cardinal.WEST, i, j);
                    } else if (j == 15) {
                        box = new Wall(Cardinal.EAST, i, j);
                    } else {
                        box = new Void();
                    }
                }
                Tuple<Box, Box, Box> value = new Tuple<Box, Box, Box>(box, null,null);
                board.put(key, value);
            }
        }
        setLockCase(7, 7);
        setLockCase(7, 8);
        setLockCase(8, 7);
        setLockCase(8, 8);
    }

    //cette methode permet de générer une liste d'entier afin d'avoir les coordonnées random pour placer les murs exterieurs en fonction
    //des contraintes de placement dans le plateau (highter1/lower1 -> délimitent i / highter2/lower2 -> délimitent j)
    //le String order permet de finir si nous sommes en présence d'une contrainte au niveau de i ou de j / par exemple +-+- = jiji
    public int[] randomExternList(int lower, int highter, int lower2, int highter2, String order) {
        String[] listOrder = order.split(" ");
        int[] listNumber = new int[4];
        for (int i = 0; i < 4; i++) {
            int highterLevel;
            int lowerlevel;
            if (listOrder[i].equals("+")) {
                highterLevel = highter2;
                lowerlevel = lower2;
            } else {
                highterLevel = highter;
                lowerlevel = lower;
            }
            int numberRandom = (int) (Math.random() * (highterLevel - lowerlevel)) + lowerlevel;
            while (contains(listNumber, numberRandom)) {
                numberRandom = (int) (Math.random() * (highterLevel - lowerlevel)) + lowerlevel;

            }
            listNumber[i] = numberRandom;
        }
        return listNumber;
    }

    //cette methode permet de générer une liste de couple afin d'avoir les coordonnées random pour placer les murs interieurs en fonction
    //des contraintes de placement dans le plateau (highteri/loweri -> délimitent i / highterj/lowerj -> délimitent j)
    public ArrayList<Couple<Integer, Integer>> randomInternList(int lower_i, int highter_i, int lower_j, int highter_j) {
        ArrayList<Couple<Integer, Integer>> listNumber = new ArrayList<Couple<Integer, Integer>>();
        Random rand = new Random();
        for (int i = 0; i < 4; i++) {
            ArrayList<Couple<Integer, Integer>> listValidCouple = getValidCouple(lower_i, highter_i, lower_j,
                    highter_j);
            //System.out.println(listValidCouple.size());
            Couple<Integer, Integer> couple = listValidCouple.get(rand.nextInt(listValidCouple.size()));
            setLockCase(couple.getA(), couple.getB());
            listNumber.add(couple);
        }
        return listNumber;
    }

    // cette methode permet de créer une methode contains pour les int[]
    public boolean contains(int[] tab, int value) {
        for (int element : tab) {
            if (element == value) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<Couple<Integer, Integer>> getValidCouple(int lower_i, int highter_i, int lower_j, int highter_j) {
        ArrayList<Couple<Integer, Integer>> listCouple = new ArrayList<Couple<Integer, Integer>>();
        for (int i = lower_i; i <= highter_i; i++) {
            for (int j = lower_j; j <= highter_j; j++) {
                if (!checkLockCase(i, j, 0, 0)) {
                    Couple<Integer, Integer> couple = new Couple<Integer, Integer>(i, j);
                    if (board.get(couple).getA() instanceof Void) {
                        listCouple.add(couple);
                    }
                }
            }
        }
        return listCouple;
    }

    //Generation des murs exterieurs par zone du plateau en fonction des coordonnées données dans les list random
    public void externWallGenerator() {
        int[] randomHorizontal = randomExternList(2, 6, 9, 13, "- + - +");
        int[] randomVertical = randomExternList(2, 6, 9, 13, "- - + +");
        int listsize = 0;
        CardinalConversion cardConv = new CardinalConversion();
        for (int i = 0; i < 16; i += 15) {
            for (int j = 0; j < 16; j += 15) {
                Box wall1 = board.get(new Couple<Integer, Integer>(i, randomHorizontal[listsize])).getA();
                Box wall2 = board.get(new Couple<Integer, Integer>(randomVertical[listsize], j)).getA();
                if (wall1 instanceof Wall && wall2 instanceof Wall) {
                    ((Wall) wall1).setCardinal(cardConv.conversion(cardConv.getVerticalMap().get(i)));
                    ((Wall) wall2).setCardinal(cardConv.conversion(cardConv.getHorizontalMap().get(j)));
                    setLockCase(i, randomHorizontal[listsize]);
                    setLockCase(randomVertical[listsize], j);
                }
                listsize++;
            }
        }
    }


    //Generation des murs interieurs par zone du plateau en fonction des coordonnées données dans les list random
    public void internWallGenerator() {
        CardinalConversion cardConv = new CardinalConversion();
        for (int k = 0; k < 4; k++) {
            ArrayList<Couple<Integer, Integer>> listNumber = null;
            switch (k) {
            case 0:
                listNumber = randomInternList(1, 6, 1, 6);
                break;
            case 1:
                listNumber = randomInternList(1, 6, 9, 14);
                break;
            case 2:
                listNumber = randomInternList(9, 14, 1, 6);
                break;
            case 3:
                listNumber = randomInternList(9, 14, 9, 14);
                break;
            default:
                break;
            }
            for (int couple = 0; couple < 4; couple++) {
                Box wall = new Wall(cardConv.randomAngle(), listNumber.get(k).getA(), listNumber.get(k).getB());
                Couple<Integer, Integer> key = new Couple<Integer, Integer>(listNumber.get(couple).getA(),
                        listNumber.get(couple).getB());

                if (board.get(key).getA() instanceof Void) {
                    board.get(key).setA(wall);
                }
            }
        }
    }

    //Generation des robots, prend en parti le systeme de randomisation des murs interieurs
    public void setRobot() {
        ArrayList<Couple<Integer, Integer>> listCoordinate = randomInternList(0, 15, 0, 15);
        for (int k = 0; k < 4; k++) {
            Robot robot;
            switch (k) {
            case 0:
                robot = new Robot(Color.RED, listCoordinate.get(k).getA(), listCoordinate.get(k).getB());
                robots.put(robot, listCoordinate.get(k));
                break;
            case 1:
                robot = new Robot(Color.BLUE, listCoordinate.get(k).getA(), listCoordinate.get(k).getB());
                robots.put(robot, listCoordinate.get(k));
                break;
            case 2:
                robot = new Robot(Color.GREEN, listCoordinate.get(k).getA(), listCoordinate.get(k).getB());
                robots.put(robot, listCoordinate.get(k));
                break;
            case 3:
                robot = new Robot(Color.YELLOW, listCoordinate.get(k).getA(), listCoordinate.get(k).getB());
                robots.put(robot, listCoordinate.get(k));
                break;
            default:
                //ce cas n'arrive jamais
                robot = new Robot(Color.GALAXY, listCoordinate.get(k).getA(), listCoordinate.get(k).getB());
                break;
            }
            Couple<Integer, Integer> key = new Couple<Integer, Integer>(listCoordinate.get(k).getA(),
                    listCoordinate.get(k).getB());
            board.get(key).setB(robot);
        }
    }

    //La target se set de facon random sur un des murs de la zone interieurs d'angle.
    public void setTarget() {
        int CoordI = (int) ((Math.random() * 13) + 1);
        int CoordJ = (int) ((Math.random() * 13) + 1);
        Object box = board.get(new Couple<Integer, Integer>(CoordI, CoordJ)).getA();
        while (!(box instanceof Wall) || (CoordI == 7 && CoordJ == 7) || (CoordI == 8 && CoordJ == 8)
                || (CoordI == 7 && CoordJ == 8) || (CoordI == 8 && CoordJ == 7)) {
            CoordI = (int) ((Math.random() * 13) + 1);
            CoordJ = (int) ((Math.random() * 13) + 1);
            box = board.get(new Couple<Integer, Integer>(CoordI, CoordJ)).getA();
        }
        Random random = new Random();
        Color color = Color.values()[random.nextInt(Color.values().length)];
        Target target = new Target(color);
        target.setPosition(CoordI, CoordJ);
        currentTarget = target;
        board.get(new Couple<Integer, Integer>(CoordI, CoordJ)).setC(target);
    }

    public void setCurrentRobot() {
        for (Robot robot : robots.keySet()) {
            // à modifier pour le GALAXY
            if (currentTarget.getColor() == Color.GALAXY) {
                currentRobot = robot;
            }
            if (robot.getColor() == currentTarget.getColor()) {
                currentRobot = robot;
            }

        }

    }

    //Regarde les cases adjacentes d'une case pour savoir si il exise une case lock.
    private boolean isLockCase(int i, int j) {
        for (int k = -1; k < 1; k++) {
            for (int l = -1; l < 1; l++) {
                if (!(i + k < 0) && !(i + k > 15) && !(j + l < 0) && !(j + l > 15)) {
                    if (checkLockCase(i, j, k, l)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void setLockCase(int i, int j) {
        for (int k = -1; k <= 1; k++) {
            for (int l = -1; l <= 1; l++) {
                if (!(i + k < 0) && !(i + k > 15) && !(j + l < 0) && !(j + l > 15)) {
                    // System.out.println((i + k) + " " + (j + l));
                    if (!checkLockCase(i, j, k, l)) {
                        Couple<Integer, Integer> key = new Couple<Integer, Integer>(i + k, j + l);
                        if (board.containsKey(key)) {
                            Object box = board.get(key).getA();
                            if (box instanceof Void) {
                                ((Void) box).setLockCase(true);
                            }
                        }
                    }
                }
            }
        }
    }

    //Regarde si une case est lock.
    private boolean checkLockCase(int i, int j, int deltaI, int deltaJ) {
        return board.get(new Couple<Integer, Integer>(i + deltaI, j + deltaJ)).getA().isLockCase();
    }

    public void printGrid() {
        for (int i = 0; i < 16; i++) {
            System.out.print("\n");
            for (int j = 0; j < 16; j++) {
                Tuple<Box, Box,Box> value = board.get(new Couple<Integer, Integer>(i, j));
                if (value.getA() instanceof Wall) {
                    if (((Wall) value.getA()).getCardinal() == Cardinal.NORTH) {
                        System.out.print(" \033[0;34m—\033[0m");
                    } else if (((Wall) value.getA()).getCardinal() == Cardinal.SOUTH) {
                        System.out.print(" \033[0;31m—\033[0m");
                    } else if (((Wall) value.getA()).getCardinal() == Cardinal.WEST) {
                        System.out.print(" \033[0;33m|\033[0m");
                    } else if (((Wall) value.getA()).getCardinal() == Cardinal.EAST) {
                        System.out.print(" \033[0;32m|\033[0m");
                    } else if (((Wall) value.getA()).getCardinal() == Cardinal.NORTH_EAST) {
                        System.out.print(" \033[0;32mN\033[0m");
                    } else if (((Wall) value.getA()).getCardinal() == Cardinal.NORTH_WEST) {
                        System.out.print(" \033[0;33mN\033[0m");
                    } else if (((Wall) value.getA()).getCardinal() == Cardinal.SOUTH_EAST) {
                        System.out.print(" \033[0;32mS\033[0m");
                    } else if (((Wall) value.getA()).getCardinal() == Cardinal.SOUTH_WEST) {
                        System.out.print(" \033[0;33mS\033[0m");
                    } else {
                        System.out.print(" \033[0;31mO\033[0m");
                    }
                } else if (value.getB() instanceof Robot) {
                    if (((Robot) value.getB()).getColor() == Color.RED) {
                        System.out.print(" \033[0;31mX\033[0m");
                    } else if (((Robot) value.getB()).getColor() == Color.BLUE) {
                        System.out.print(" \033[0;34mX\033[0m");
                    } else if (((Robot) value.getB()).getColor() == Color.GREEN) {
                        System.out.print(" \033[0;32mX\033[0m");
                    } else if (((Robot) value.getB()).getColor() == Color.YELLOW) {
                        System.out.print(" \033[0;33mX\033[0m");
                    }
                } else if (value.getC() instanceof Target) {
                    System.out.println(" \033[0;35mT\033[0m");
                } else {
                    System.out.print(" .");
                }
            }
        }
    }

    public HashMap<Couple<Integer, Integer>, Tuple<Box,Box,Box>> getBoard() {
        return board;
    }

    public HashMap<Robot, Couple<Integer, Integer>> getRobots() {
        return robots;
    }
}
