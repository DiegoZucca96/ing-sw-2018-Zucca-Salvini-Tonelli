package ingsw;

import ingsw.model.*;
import ingsw.model.windowpattern.WindowPattern;
import org.junit.jupiter.api.*;
import junit.framework.TestCase;


class PBObjectiveCardTest extends TestCase{
    @Test
    public void testDoPBStrategy() {
        Player p = new Player("io","wp1", Color.BLUE);
        WindowPattern window = p.getWindowPattern();
        Coordinate c1 = new Coordinate(0,4);
        Coordinate c2 = new Coordinate(1,4);
        Coordinate c3 = new Coordinate(1,3);
        Coordinate c4 = new Coordinate(0,3);
        Die die1 = new Die(1,Color.BLUE);
        Die die2 = new Die (4,Color.GREEN);
        Die die3 = new Die (3,Color.BLUE);
        Die die4 = new Die (2,Color.GREEN);
        window.addDie(c1,die1,window.getCellMatrix());
        window.addDie(c2,die2,window.getCellMatrix());
        window.addDie(c3,die3,window.getCellMatrix());
        window.addDie(c4,die4,window.getCellMatrix());
        PBObjectiveCard pb1 = new PBObjectiveCard(5);
        PBObjectiveCard pb2 = new PBObjectiveCard(6);
        PBObjectiveCard pb3 = new PBObjectiveCard(9);
        pb1.doPBStrategy(p);
        pb2.doPBStrategy(p);
        assertEquals(4,p.getScore());
        pb3.doPBStrategy(p);
        assertEquals(8,p.getScore());
    }
}