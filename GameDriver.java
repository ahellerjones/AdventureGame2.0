package adventuregame;

import java.util.ArrayList;
import java.util.Scanner;

public class GameDriver {

    private Player player;
    private static boolean yesNoFlag;
    private boolean waitingForBuilding;
    Building cachedBuilding;
//    private boolean inputTextFlag;
    private char[][] charGrid;

    public GameDriver() {
        yesNoFlag = false;
     //   inputTextFlag = false;
        player = new Player();
        loadMaps();
        refresh();

    }


    /**
     * Possibly a constructor for loading in a saved game?
     * 
     * @param in
     *            the textfile for use for loading in a game.
     */
    public GameDriver(Scanner in) {
        // pass
    }


    /**
     * Makes all of the maps and sets them up
     * 
     */
    public void loadMaps() {

        MapObject t1 = new MapObject('T', "town", true, 1, 1);
        ArrayList<MapObject> m33 = new ArrayList<>();
        m33.add(t1);
        LocalMap map33 = new LocalMap("33", m33);

        MapObject t2 = new MapObject('T', "town", false, 0, 0);
        ArrayList<MapObject> m23 = new ArrayList<>();
        m23.add(t2);
        LocalMap map23 = new LocalMap("23", m23);
        map33.addObjectAt(player.getX(), player.getY(), player);
    }


    public void refresh() {
        charGrid = player.getMap().getCharGrid();
    }


    public char[][] getCharGrid() {
        return charGrid;
    }


    public Player getPlayer() {
        return player;
    }
    
    public static boolean getYesNo() { 
        return yesNoFlag;
    }
    
    /**
     * Changes the yes/no flag 
     */
    public void toggleYesNo(boolean yesNo) { 
        yesNoFlag = yesNo;
    }
    
    public void bufferBuilding(Building building) { 
        
    }
    
    public void checkSurroundings() { 
        MapObject[][] mapGrid = player.getMap().getMapObjectGrid();
        int arrayLength = mapGrid[0].length;
        for (int i = 0; i < arrayLength; i++) { 
            for (int j = 0; j < arrayLength; j++) { 
                if (mapGrid[i][j] != null && 
                    !(mapGrid[i][j].equals(player)) && 
                    player.withinProximity(i, j)) {
                    
                    
                    /**
                     * Check what kind of object the found one is. 
                     */
                    if (mapGrid[i][j].getClass() == NPC.class) { 
                        NPC castedNPC = (NPC) mapGrid[i][j];
                        castedNPC.interactions();
                    } else if (mapGrid[i][j].getClass() == Building.class) {
                        waitingForBuilding = true;
                        cachedBuilding = (Building) mapGrid[i][j];
                        
                        if (GameDriver.getYesNo()) { 
                            
                        }
                        
                        
                    }
                    
                    
                }
            }
        }
    }
    

}
