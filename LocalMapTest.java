package adventuregame;

import java.util.ArrayList;
import student.TestCase;

public class LocalMapTest extends TestCase {
    ArrayList<MapObject> mapList;
    ArrayList<MapObject> mapListUP;
    LocalMap map;
    LocalMap mapUP;
    LocalMap mapRIGHT;
    LocalMap mapDOWN;
    LocalMap mapLEFT;
    String mapKey;
    String mapKey1;
    MapObject a;
    MapObject b;
    MapObject c;
    MapObject d;
    MapObject perm;

    public void setUp() {
        mapList = new ArrayList<>();
        mapListUP = new ArrayList<>();
        mapKey = "12";
        mapKey1 = "02";
        a = new MapObject('a', "Jim", false, 1, 1);
        b = new MapObject('b', "Jim", false, 2, 2);
        c = new MapObject('c', "Jim", false, 3, 3);
        d = new MapObject('d', "Jim", false, 0, 0);
        perm = new MapObject('p', "Jim", true, 0, 0);
        mapList.add(a);
        mapList.add(b);
        mapList.add(c);
        map = new LocalMap(mapKey, mapList);
        mapUP = new LocalMap("02", mapListUP);
        mapRIGHT = new LocalMap("13", mapListUP);
        mapDOWN = new LocalMap("22", mapListUP);
        mapLEFT = new LocalMap("11", mapListUP);
    }


    /**
     * Tests the char grid
     */
    public void testCharGrid() {
        assertEquals('a', map.getCharGrid()[1][1]);
        assertEquals('b', map.getCharGrid()[2][2]);
        assertEquals('c', map.getCharGrid()[3][3]);
    }


    /**
     * Ensuring the remove method works properly
     */
    public void testRemoveObjectAt() {
        assertTrue(map.removeObjectAt(3, 3));
        assertEquals(0, map.getCharGrid()[3][3]);
    }


    /**
     * Ensuring that the method doesn't
     * do anything student when we remove
     * an entry that doesn't work.
     */
    public void testRemoveWithNothingThere() {
        assertFalse(map.removeObjectAt(1, 3));
        assertEquals(0, map.getCharGrid()[1][3]);
    }


    /**
     * tests the addObjectAt if the object is null
     */
    public void testAddObjectAtNull() {
        assertFalse(map.addObjectAt(1, 0, null));
        map.getCharGrid();
    }


    /**
     * Tests adding where an entry already
     * exists
     * 
     */
    public void testAddSpaceFull() {
        assertFalse(map.addObjectAt(1, 1, b));
    }


    /**
     * Test add normally
     */
    public void testAddNormal() {
        assertEquals(0, map.getCharGrid()[0][0]);
        assertTrue(map.addObjectAt(0, 0, a));
        assertEquals('a', map.getCharGrid()[0][0]);
    }


    /**
     * Tests the object's x,y coordinates after it gets moved
     * ensures that map.addObjectAt changes the object's actual x, y fields
     */
    public void testCoordinates() {
        map.addObjectAt(3, 2, a);
        assertEquals(3, a.getX());
        assertEquals(2, a.getY());
    }


    /**
     * Test the moveObjectOnMap with a null
     */
    public void testMoveObjectNull() {
        assertFalse(map.moveObjectOnMap(Direction.UP, null));
    }


    /**
     * Tests the moveObjectOnMap with an object that's not on the map
     */
    public void testMoveObjectNotOnMap() {
        map.removeObjectAt(2, 2);
        assertFalse(map.moveObjectOnMap(Direction.UP, b));
    }


    /**
     * Tests moving up with a non permeable object
     * in the way
     */

    public void testMovingUpWithSomethingInTheWay() {
        map.addObjectAt(1, 2, a);
        assertFalse(map.moveObjectOnMap(Direction.UP, b));
        assertEquals('a', map.getObjectAt(1, 2).getMapIcon());
    }


    /**
     * Tests moving up with a permeable object in the way
     */
    public void testMovingUpWithPerm() {
        map.addObjectAt(1, 2, perm);
        assertTrue(map.moveObjectOnMap(Direction.UP, b));
        assertEquals('b', map.getObjectAt(1, 2).getMapIcon());
    }


    /**
     * Tests to see if the map reloads to the correct thing
     * when you move off of a permeable
     * object
     * 
     */
    public void testMovingOffOfPerm() {
        map.addObjectAt(1, 2, perm);
        assertTrue(map.moveObjectOnMap(Direction.UP, b));
        assertEquals('b', map.getObjectAt(1, 2).getMapIcon());
        assertTrue(map.moveObjectOnMap(Direction.RIGHT, b));
        assertEquals('p', map.getObjectAt(1, 2).getMapIcon());
    }


    /**
     * Tests the move function with nothing in the way
     */
    public void testMovingNormal() {
        assertTrue(map.moveObjectOnMap(Direction.UP, b));
    }
    
    /**
     * Tests changing maps when moving up
     */
    public void testMovingUpAndChangingMaps() {
        map.addObjectAt(0, 0, d);
        map.moveObjectOnMap(Direction.UP, d);
        assertTrue(d.getMap().equals(mapUP));
         
    }
    /**
     * tests moving the map to  the right
     */
    public void testMovingLeftChangeMap() { 
        map.addObjectAt(0, 0, d);
        map.moveObjectOnMap(Direction.LEFT, d);
        assertTrue(d.getMap().equals(mapLEFT));
    }
    
    /**
     * Tests moving the map to the right
     */
    public void testMovingRightChangeMap() {
        map.addObjectAt(0, 4, d);
        map.moveObjectOnMap(Direction.RIGHT, d);
        assertTrue(d.getMap().equals(mapRIGHT));
    }
    
    /**
     * Tests moving the map down
     */
    public void testMovingDownChangeMap() {
        map.addObjectAt(4, 0, d);
        map.moveObjectOnMap(Direction.DOWN, d);
        assertTrue(d.getMap().equals(mapDOWN));
    }
    
    
}
