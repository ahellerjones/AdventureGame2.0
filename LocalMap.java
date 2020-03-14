package adventuregame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for generating and holding maps... called LocalMaps because
 * Java decided to already use maps...!
 * The objects on the Local are stored in objectList
 * 
 * Each Local is represented by the localKey, a represenation
 * of the grid number's and the buildings entered.
 * 
 * The objectList is used to make it much easier to generate the
 * objects for a given map.
 * 
 * @author andy
 *
 */
public class LocalMap {
    private ArrayList<MapObject> objectList;
    private MapObject[][] mapObjectGrid;
    private String mapKey;
    private char[][] charGrid;
    public static Map<String, LocalMap> mapDictionary = new HashMap<>();

    /**
     * Constructor
     * 
     * @param mapKey
     *            - this is the string key for the Local
     *            to store the Local object into the dictionary
     * @param objectList
     *            - the objects to be stored in this map
     */
    public LocalMap(String mapKey, ArrayList<MapObject> objectList) {
        charGrid = new char[5][5];
        mapObjectGrid  = new MapObject[5][5];
        this.mapKey = mapKey;
        
        mapDictionary.put(mapKey, this);
        this.objectList = objectList;
        generateCharGrid();
        generateMapObjectGrid();
    }


    /**
     * removes the object at a specific coordinate
     * Also removes it from the objectList and deletes it forever
     * 
     * The program also checks to make sure that the object
     * to remove is not null.
     * 
     * @param x
     *            - the x coordinate
     * @param y
     *            - the y coordinate
     * 
     * @return whether or not the remove was successful.
     *         If the object did not exist, then return false
     *         if the object does exist, the:
     *         [mapObjectGrid grid location of [x][y] is set to null;
     *         the charGrid[x][y] is set to 0
     *         and the object is removed from the objectList
     * 
     */
    public boolean removeObjectAt(int x, int y) {
        MapObject objectToRemove = mapObjectGrid[x][y];
        if (objectToRemove != null) {
            mapObjectGrid[x][y] = null;
            charGrid[x][y] = 0;
            for (int i = 0; i < objectList.size(); i++) {
                if (objectToRemove.equals(objectList.get(i))) {
                    objectList.remove(i);
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Adds an object at the specified index
     * 
     * @param x
     *            - the x coordinate
     * @param y
     *            - the y coordinate
     * @param objectToAdd
     *            - the object to add to the map
     */
    public boolean addObjectAt(int x, int y, MapObject objectToAdd) {
        if (objectToAdd != null && (mapObjectGrid[x][y] == null
            || mapObjectGrid[x][y].isPermeable())) {

            objectToAdd.setX(x);
            objectToAdd.setY(y);
            objectToAdd.setMap(this);
            objectList.add(objectToAdd);
            mapObjectGrid[x][y] = objectToAdd;
            charGrid[x][y] = objectToAdd.getMapIcon();
            generateMapObjectGrid();
            generateCharGrid();
            return true;
        }
        return false;
    }


    /**
     * Returns the object at the specific location
     * Could be really really useful for collision checking
     * 
     * @param x
     *            - the x coordinate
     * @param y
     *            - the y coordinate
     */
    public MapObject getObjectAt(int x, int y) {
        return mapObjectGrid[x][y];
    }


    /**
     * Makes the character grid[][] using the
     * characters of each of the objects in the
     * map object list
     */
    private void generateCharGrid() {
        for (MapObject obj : objectList) {
            if (obj != null) {
                charGrid[obj.getX()][obj.getY()] = obj.getMapIcon();
            }
        }
    }


    /**
     * Makes the mapObject grid[][] using the
     * characters of each of the objects in the
     * map object list
     */
    private void generateMapObjectGrid() {
        for (MapObject obj : objectList) {
            mapObjectGrid[obj.getX()][obj.getY()] = obj;
        }
    }


    /**
     * refreshes the object Grid aand
     * gets the mapobject grid
     * 
     */
    public MapObject[][] getMapObjectGrid() {
        generateMapObjectGrid();
        return mapObjectGrid;
    }


    /**
     * refreshes the character grid
     * gets the char grid
     */
    public char[][] getCharGrid() {
        generateCharGrid();
        return charGrid;
    }


    /**
     * Moves an object to new a new location
     * given a direction using the Direction enum
     * If there's a non permeable object in the way, or the
     * obj is not in the map, the method returns false
     * If the object is at the border, the method refers
     * figures out the new map to load and loads it.
     * 
     * If the new map to load doesn't exist the program
     * will throw an exception and exit.
     * 
     * @param dir
     *            the Direction for the object to move.
     * @param obj
     *            the object to move
     * @return if the obj is null, or cannot be removed from the map
     *         (the object is not on the map
     *         Then the method will return false.
     *         If the object is at a border and needs to change maps then
     *         the progam loads and new map and places the object at it.
     * 
     *         If the move works, then the method will move the object
     *         one tile space in the Direction provided and
     *         return true.
     * 
     */
    public boolean moveObjectOnMap(Direction dir, MapObject obj) {
        // Checks whether the object is null, then removes the object
        // The removal process returns false if the object does not exist
        // and then therefore determines that  the movement was thus
        // false.
        if (obj == null || !removeObjectAt(obj.getX(), obj.getY())) {
            return false;
        }
        /**
         * These switch cases determine movement based on the Direction (an enum) that 
         * the obj wants to move
         * 
         * All movements basically do the same thing but with just changes in bounds
         * checking and movements based on the Direction.
         * 
         * First it checks whether or not the obj is at the boundary, if so, generate a new
         * map key for the map to move from ("x" + "y" + "c1" + "c2" ... 
         * and move the obj to that map
         * 
         * Else check if there are collisions and then move the object
         * based on whether or not the movement was succsessful.
         */
        switch (dir) {
            case UP:
                if (obj.getX() == 0) {
                    LocalMap newMap1 = mapDictionary.get(
                        generateNewMapKeyMovement(dir));
                    obj.changeMap(4, obj.getY(), newMap1);
                    return true;

                }
                else if (getObjectAt(obj.getX() - 1, obj.getY()) == null
                    || getObjectAt(obj.getX() - 1, obj.getY()).isPermeable()) {
                    addObjectAt(obj.getX() - 1, obj.getY(), obj);
                    return true;
                }
                addObjectAt(obj.getX(), obj.getY(), obj);
                return false;

            case RIGHT:
                if (obj.getY() == 4) {
                    LocalMap newMap = mapDictionary.get(
                        generateNewMapKeyMovement(dir));
                    
                    obj.changeMap(obj.getX(), 0, newMap);
                    return true;

                }
                else if (getObjectAt(obj.getX(), obj.getY() + 1) == null
                    || getObjectAt(obj.getX(), obj.getY() + 1).isPermeable()) {
                    
                    addObjectAt(obj.getX(), obj.getY() + 1, obj);
                    return true;
                }
                addObjectAt(obj.getX(), obj.getY(), obj);
                return false;

            case DOWN:
                if (obj.getX() == 4) {
                    //try { 
                        LocalMap newMap = mapDictionary.get(
                            generateNewMapKeyMovement(dir));
                    
                        obj.changeMap(0, obj.getY(), newMap);
                        return true;
                    //} catch (Exception e) { 
                       // System.out.println("Map not found, most likely map not added"
                         //   + " to dictionary");
                    //}

                }
                else if (getObjectAt(obj.getX() + 1, obj.getY()) == null
                    || getObjectAt(obj.getX() + 1, obj.getY()).isPermeable()) {
                    addObjectAt(obj.getX() + 1, obj.getY(), obj);
                    return true;
                }
                addObjectAt(obj.getX(), obj.getY(), obj);
                return false;

            case LEFT:
                if (obj.getY() == 0) {
                    LocalMap newMap = mapDictionary.get(
                        generateNewMapKeyMovement(dir));
                    obj.changeMap(obj.getX(), 4, newMap);
                    return true;

                }
                else if (getObjectAt(obj.getX(), obj.getY() - 1) == null
                    || getObjectAt(obj.getX(), obj.getY() - 1).isPermeable()) {
                    addObjectAt(obj.getX(), obj.getY() - 1, obj);
                    return true;
                } else { 
                    addObjectAt(obj.getX(), obj.getY(), obj);
                    return false;
                }
            default:
                return false;
        }
    }


    /**
     * Generates a new map key based on the current map's key
     * This method is called whenever it's detected that the player
     * has moved off of the map.
     * 
     * @param dir The direction to generate the new map from 
     * @return the mapString to be used 
     */
    private String generateNewMapKeyMovement(Direction dir) {
        String generatedMapKey = null;
        int xValue;
        int yValue;
        switch (dir) {
            case LEFT:
                yValue = Integer.parseInt(mapKey.substring(1, 2));
                generatedMapKey = mapKey.substring(0, 1) + (yValue - 1);
                break;
            case DOWN:
                xValue = Integer.parseInt(mapKey.substring(0, 1));
                generatedMapKey = (xValue + 1) + mapKey.substring(1, 2);
                break;
            case RIGHT:
                yValue = Integer.parseInt(mapKey.substring(1, 2));
                generatedMapKey = mapKey.substring(0, 1) + (yValue + 1);
                break;
            case UP:
                xValue = Integer.parseInt(mapKey.substring(0, 1));
                generatedMapKey = (xValue - 1) + mapKey.substring(1, 2);
                break;
        }
        return generatedMapKey;
    }
}
