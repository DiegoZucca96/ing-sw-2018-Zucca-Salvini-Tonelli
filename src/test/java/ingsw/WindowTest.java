package ingsw;

import ingsw.model.*;
import ingsw.model.windowpattern.WindowPattern;
import junit.framework.TestCase;
import org.junit.jupiter.api.*;

class WindowTest extends TestCase {
    private Player p = new Player("X", 1, Color.BLUE);
    private WindowPattern window = p.getWindowPattern();
    private Cell[][] cellMatrix = window.getCellMatrix();
    private Coordinate coordinate = new Coordinate(1, 2);
    private Coordinate dieCoordinate1 = new Coordinate(3, 0);
    private Coordinate dieCoordinate2 = new Coordinate(0, 4);
    private Coordinate dieCoordinate3 = new Coordinate(2, 0);
    private Coordinate dieCoordinate4 = new Coordinate(1, 1);
    private Coordinate dieCoordinate6 = new Coordinate(0, 0);
    private Coordinate dieCoordinate7 = new Coordinate(0, 1);
    private Coordinate dieCoordinate8 = new Coordinate(0, 2);
    private Die die = new Die(5, Color.BLUE);
    private Die die2 = new Die(3, Color.GREEN);
    private Die die3 = new Die(4, Color.GREEN);
    private Die die4 = new Die(2, Color.BLUE);
    private Die die5 = new Die(3,Color.YELLOW);

    //Test dei metodi di verifica nella WP (non vanno nel progetto originale causa errori)
    @Test
    public void testVerifyPosition() {
        window.addDie(dieCoordinate1, die4, window.getCellMatrix());
        window.addDie(dieCoordinate3, die2, window.getCellMatrix());
        window.addDie(dieCoordinate4, die3, window.getCellMatrix());
        assertEquals(true, window.verifyPosition(coordinate, window.getCellMatrix()));
        assertFalse(window.verifyPosition(dieCoordinate2, window.getCellMatrix()));
    }

    @Test
    public void testEmpty() {
        cellMatrix[0][2].insertDie(die);
        cellMatrix[0][2].setEmpty(false);
        //window.addDie(coordinate,die);
        assertFalse(cellMatrix[0][2].isEmpty());
    }

    @Test
    public void testVerifyCellConstraint() {
        assertTrue(window.verifyCellNumberConstraint(dieCoordinate3, die2, window.getCellMatrix()));
        assertTrue(window.verifyCellColorConstraint(dieCoordinate3, die2, window.getCellMatrix()));
    }

    @Test
    public void testVerifyDieConstraint() {
        window.addDie(dieCoordinate6, die5,window.getCellMatrix());
        window.addDie(dieCoordinate7, die,window.getCellMatrix());
        assertFalse(window.verifyDieColorConstraint(dieCoordinate8, die4, window.getCellMatrix()));
        assertFalse(window.verifyDieNumberConstraint(dieCoordinate8, die, window.getCellMatrix()));
        assertTrue(window.verifyDieNumberConstraint(dieCoordinate8, die4, window.getCellMatrix()));
    }
}