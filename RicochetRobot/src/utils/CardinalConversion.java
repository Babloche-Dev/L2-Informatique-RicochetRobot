package utils;

import utils.Cardinal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class CardinalConversion {
    private HashMap<Integer, Cardinal> horizontalMap;
    private HashMap<Integer, Cardinal> verticalMap;
    private ArrayList<Cardinal> listCardAngle;

    public CardinalConversion(){
        horizontalMap = new HashMap<Integer, Cardinal>();
        horizontalMap.put(0,Cardinal.WEST);
        horizontalMap.put(15,Cardinal.EAST);
        verticalMap = new HashMap<Integer, Cardinal>();
        verticalMap.put(0,Cardinal.NORTH);
        verticalMap.put(15,Cardinal.SOUTH);
        listCardAngle = new ArrayList<Cardinal>();
        listCardAngle.add(Cardinal.NORTH_EAST);
        listCardAngle.add(Cardinal.NORTH_WEST);
        listCardAngle.add(Cardinal.SOUTH_EAST);
        listCardAngle.add(Cardinal.SOUTH_WEST);
    }

    public Cardinal randomAngle(){
        Random random = new Random();
        return listCardAngle.get(random.nextInt(listCardAngle.size()));
    }

    public static Cardinal conversion(Cardinal side){
        if(side == Cardinal.WEST){
            return Cardinal.NORTH_WEST;
        }
        else if(side == Cardinal.EAST){
            return Cardinal.NORTH_EAST;
        }
        else if(side == Cardinal.NORTH){
            return Cardinal.NORTH_WEST;
        }
        else{
            return Cardinal.SOUTH_WEST;
        }
    }

    public HashMap<Integer, Cardinal> getHorizontalMap() {
        return horizontalMap;
    }

    public HashMap<Integer, Cardinal> getVerticalMap() {
        return verticalMap;
    }

}
