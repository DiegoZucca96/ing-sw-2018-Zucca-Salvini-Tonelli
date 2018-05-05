package ingsw;

import ingsw.model.windowpattern.WP1;
import ingsw.model.Cell;
import ingsw.model.Color;
import ingsw.model.Coordinate;
import ingsw.model.Die;
import junit.framework.TestCase;
import org.junit.jupiter.api.*;

public class WP1Test extends TestCase {
    private WP1 window = new WP1();
    private Cell[][] cellMatrix = window.getCellMatrix();
    private Coordinate coordinate = new Coordinate(1,2);
    private Coordinate dieCoordinate1 = new Coordinate(3,0);
    private Coordinate dieCoordinate2 = new Coordinate(0,4);
    private Coordinate dieCoordinate3 = new Coordinate(2,0);
    private Coordinate dieCoordinate4 = new Coordinate(1,1);
    private Die die= new Die(5, Color.BLUE);
    private Die die2= new Die(3,Color.GREEN);
    private Die die3= new Die(4,Color.GREEN);
    private Die die4= new Die(2,Color.BLUE);

//Test dei metodi di verifica nella WP (non vanno nel progetto originale causa errori)
    @Test
    public void testVerifyPosition() {
        window.addDie(dieCoordinate1,die4);
        window.addDie(dieCoordinate3,die2);
        window.addDie(dieCoordinate4,die3);
        assertEquals(true, window.verifyPosition(coordinate));
        assertFalse(window.verifyPosition(dieCoordinate2));
    }

    @Test
    public void testEmpty(){
        cellMatrix[0][2].insertDie(die);
        cellMatrix[0][2].setEmpty(false);
        //window.addDie(coordinate,die);
        assertFalse(cellMatrix[0][2].isEmpty());
    }

    @Test
    public void testVerifyCellConstraint(){
        assertTrue(window.verifyCellNumberConstraint(dieCoordinate3,die2));
        assertTrue(window.verifyCellColorConstraint(dieCoordinate3,die2));
    }

    @Test
    public void testVerifyDieConstraint(){
        cellMatrix[0][2].insertDie(die);
        assertFalse(window.verifyDieColorConstraint(dieCoordinate2,die));
        assertFalse(window.verifyDieNumberConstraint(dieCoordinate2,die));
    }
}