package ingsw;

import WindowPattern.WP1;
import org.junit.jupiter.api.*;
import junit.framework.TestCase;


class PBObjectiveCardTest extends TestCase{
    @Test
    public void testDoPBStrategy() {
        Player p = new Player("io","www",Color.BLUE);
        WP1 window = new WP1();
        Coordinate c1 = new Coordinate(0,4);
        Coordinate c2 = new Coordinate(1,4);
        Coordinate c3 = new Coordinate(1,3);
        Coordinate c4 = new Coordinate(0,3);
        Die die1 = new Die(1,Color.BLUE);
        Die die2 = new Die (4,Color.GREEN);
        Die die3 = new Die (3,Color.BLUE);
        Die die4 = new Die (2,Color.GREEN);
        window.addDie(c1,die1);
        window.addDie(c2,die2);
        window.addDie(c3,die3);
        window.addDie(c4,die4);
        PBObjectiveCard pb1 = new PBObjectiveCard(5);
        PBObjectiveCard pb2 = new PBObjectiveCard(6);
        pb1.doPBStrategy(p,window);
        pb2.doPBStrategy(p,window);
        assertEquals(4,p.getScore());
    }

}