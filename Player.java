package adventuregame;

public class Player extends Character {

    

    /**
     * Constructor for the player object
     * 
     * @param mapIcon
     *            the icon for the player
     * @param name
     *            the name of the player.
     */
    public Player() {
        super('X', "Player", 4, 4);
    }

    /**
     * Moves the player depending 
     * @param dir the direction in which the player will be moving
     * @return whether or not the movement happened.
     */
    public boolean move(Direction dir) {
        return getMap().moveObjectOnMap(dir, this);
    }
    
    
    
    public boolean withinProximity(int x, int y) { 
        return (getX() == x - 1 && getY() == y) || 
               (getX() == x && getY() == y + 1) ||
               (getX() == x + 1 && getY() == y) || 
               (getX() == x && getY() == y - 1);
            
    }
    
    
    
    

}
