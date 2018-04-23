package ingsw;

import WindowPattern.WP1;
import junit.framework.TestCase;
import org.junit.jupiter.api.*;

public class WP1Test extends TestCase {
    private WP1 window = new WP1();
    private Cell[][] cellMatrix = window.getCellMatrix();
    private Coordinate coordinate = new Coordinate(3,3);
    private Coordinate dieCoordinate1 = new Coordinate(3,0);
    private Coordinate dieCoordinate2 = new Coordinate(0,3);
    private Coordinate dieCoordinate3 = new Coordinate(2,0);
    private Coordinate dieCoordinate4 = new Coordinate(2,4);
    private Die die= new Die(5,Color.YELLOW);

    @Test
    public void testVerifyPosition() {
        window.addDie(dieCoordinate1,die);
        window.addDie(dieCoordinate2,die);
        window.addDie(dieCoordinate3,die);
        window.addDie(dieCoordinate4,die);
        assertEquals(false, window.verifyPosition(coordinate));
    }

    @Test
    public void testEmpty(){                 //Una volta invocato il metodo verifiedPosition che restituisce correttamente true
        window.addDie(coordinate,die);       //salta la verifica sotto e quindi non fa la insertDie => Da sbagliato anche il test sopra
        assertTrue(cellMatrix[3][3].isEmpty());
    }

    @Test
    public void testVerifyCellConstraint(){
        assertFalse(window.verifyCellConstraint(coordinate,die));
    }
}