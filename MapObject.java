package adventuregame;

public class MapObject {
    private char mapIcon;
    private LocalMap currentMap;
    private String name;
    private int x;
    private int y;
    private boolean isPermeable;

    /**
     * Initial working constructor for the class
     * 
     * @param mapCharacter
     *            the character to use on the map.
     * 
     */
    public MapObject(
        char mapCharacter,
        String objectName,
        boolean isPermeable,
        int x,
        int y) {

        this.x = x;
        this.y = y;
        
        mapIcon = mapCharacter;
        name = objectName;
        this.isPermeable = isPermeable;
    }
    
    /**
     * Changes the map the player's in
     * 
     * @param x
     *            the new x coordinate to put the player on
     * @param y
     *            the new y coordinate to put the player on
     * @param map
     *            the new map to put the MapObject on
     */
    public void changeMap(int x, int y, LocalMap map) { 
        currentMap.removeObjectAt(this.x, this.y);
        setMap(map);
        map.addObjectAt(x, y, this);
    }
    
    /**
     * Returns the name
     * @return the name
     */
    public String getName() { 
        return name;
    }


    /**
     * returns the x coordinate
     * 
     * @return the x coordinate
     */
    public int getX() {
        return x;
    }


    /**
     * returns the y coordinate
     * 
     * @return the y coordinate
     */
    public int getY() {
        return y;
    }
    
    /**
     * setter for x
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }
    
    /**
     * setter for x
     * @param x
     */
    public void setY(int y) {
        this.y = y;
    }


    /**
     * Gives up the mapIcon for this object
     * 
     * @return the mapIcon character of this object
     */
    public char getMapIcon() {
        return mapIcon;
    }


    /**
     * getter for permeability (whether or not the player can walk through it)
     */
    public boolean isPermeable() {
        return isPermeable;
    }


    /**
     * Changes the permeability state for a mapObject
     * can only be modified from this class or a
     * sub class
     * 
     * @param state
     *            what the permeability should be
     */
    protected void setPermeability(boolean state) {
        isPermeable = state;
    }


    /**
     * setter for the currentMap field
     * 
     * @param map
     *            the new map
     */
    public void setMap(LocalMap map) {
        currentMap = map;

    }
    /**
     * Getter for the currentMap
     * @return currentMap the currentMap
     */
    public LocalMap getMap() { 
        return currentMap;
    }
    
    

}
