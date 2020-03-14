package adventuregame;

/**
 * This class works as an extension of mapObjects for characters
 * in the world. They could be traders, just regular people, or
 * combatants idk what they'll be.
 * 
 * Branches off into Player... and maybe NPC
 * 
 * @author andy
 *
 */
public class Character extends MapObject {

    /**
     * 
     * @param mapIcon
     *            the icon to display on the map
     * @param name
     *            the Character's name
     * @param x
     *            x coordinate
     * @param y
     *            y coordinate
     */
    public Character(char mapIcon, String name, int x, int y) {
        super(mapIcon, name, false, x, y);
    }


    public void assignMap(LocalMap map) {

    }

}
