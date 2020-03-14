package adventuregame;

/**
 * This class represents an NPC that will just sit in the world.
 * The fields represent whether the NPC will initiate
 * talk or fight upon interaction.
 * @author andy
 *
 */
public class NPC extends Character {
    private boolean wantsToTalk;
    private boolean wantsToFight;
    private String[][] talkStrings;

    public NPC(
        char mapIcon,
        String name,
        int x,
        int y,
        boolean wantsToTalk,
        boolean wantsToFight) {
        
        
        super(mapIcon, name, x, y);
        this.wantsToTalk = wantsToTalk;
        this.wantsToFight = wantsToFight;
    }
    
    public void interactions() { 
        //spit out dialogue
    }
    
    

}
