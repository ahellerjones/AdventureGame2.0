package adventuregame;

public class Building extends MapObject {

    private boolean canEnter;

    public Building(char mapChar, String objectName, int x, int y) {
        super(mapChar, objectName, false, x, y);
        canEnter = true;
    }


    /**
     * canEnter getter
     * 
     * @return
     */
    public boolean getEntry() {
        return canEnter;
    }


    /**
     * canEnter setter
     * 
     * @param
     */
    public void setEntry(boolean entry) {
        canEnter = entry;
    }

}
