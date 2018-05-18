package ingsw;

import ingsw.model.*;
import ingsw.model.windowpattern.WindowPattern;
import org.junit.jupiter.api.*;
import junit.framework.TestCase;


class PBObjectiveCardTest extends TestCase{

    @Test
    public void testDoPBStrategy() {
        Player p = new Player("io",1, Color.BLUE);
        WindowPattern window = p.getWindowPattern();
        Coordinate c1 = new Coordinate(0,4);
        Coordinate c2 = new Coordinate(1,4);
        Coordinate c3 = new Coordinate(1,3);
        Coordinate c4 = new Coordinate(0,3);
        Die die1 = new Die(1,Color.BLUE);
        Die die2 = new Die (4,Color.VIOLET);
        Die die3 = new Die (3,Color.BLUE);
        Die die4 = new Die (2,Color.VIOLET);
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

        Die die5 = new Die (3,Color.GREEN);
        Die die6 = new Die (2,Color.YELLOW);
        Die die7 = new Die (5,Color.RED);
        Die die8 = new Die (1,Color.BLUE);
        Die die9 = new Die (3,Color.VIOLET);
        Coordinate c5 = new Coordinate(2,0);
        Coordinate c6 = new Coordinate(2,1);
        Coordinate c7 = new Coordinate(2,2);
        Coordinate c8 = new Coordinate(2,3);
        Coordinate c9 = new Coordinate(2,4);
        window.addDie(c9,die5,window.getCellMatrix());
        window.addDie(c8,die6,window.getCellMatrix());
        window.addDie(c7,die7,window.getCellMatrix());
        window.addDie(c6,die8,window.getCellMatrix());
        window.addDie(c5,die9,window.getCellMatrix());
        PBObjectiveCard pb4 = new PBObjectiveCard(1);
        pb4.doPBStrategy(p);

        assertEquals(14,p.getScore());
    }

    @Test
    public void testPB2(){
        Player p = new Player("io",2, Color.GREEN);
        WindowPattern window = p.getWindowPattern();
        Coordinate c1 = new Coordinate(0,0);
        Coordinate c2 = new Coordinate(1,0);
        Coordinate c3 = new Coordinate(2,0);
        Coordinate c4 = new Coordinate(3,0);
        Coordinate c5 = new Coordinate(0,1);
        Coordinate c6 = new Coordinate(0,2);
        Coordinate c7 = new Coordinate(1,2);
        Coordinate c8 = new Coordinate(2,2);
        Coordinate c9 = new Coordinate(3,2);
        Die die1 = new Die(1,Color.VIOLET);
        Die die2 = new Die (5,Color.GREEN);
        Die die3 = new Die (3,Color.RED);
        Die die4 = new Die (2,Color.YELLOW);
        Die die5 = new Die (6,Color.GREEN);
        Die die6 = new Die (4,Color.YELLOW);
        Die die7 = new Die (3,Color.RED);
        Die die8 = new Die (1,Color.VIOLET);
        Die die9 = new Die (5,Color.BLUE);
        window.addDie(c1,die1,window.getCellMatrix());
        window.addDie(c2,die2,window.getCellMatrix());
        window.addDie(c3,die3,window.getCellMatrix());
        window.addDie(c4,die4,window.getCellMatrix());
        window.addDie(c5,die5,window.getCellMatrix());
        window.addDie(c6,die6,window.getCellMatrix());
        window.addDie(c7,die7,window.getCellMatrix());
        window.addDie(c8,die8,window.getCellMatrix());
        window.addDie(c9,die9,window.getCellMatrix());
        PBObjectiveCard pb = new PBObjectiveCard(2);
        pb.doPBStrategy(p);
        assertEquals(10,p.getScore());
    }

    @Test
    public void testPB3(){
        Player p = new Player("io",3, Color.BLUE);
        WindowPattern window = p.getWindowPattern();
        Coordinate c1 = new Coordinate(3,0);
        Coordinate c2 = new Coordinate(3,1);
        Coordinate c3 = new Coordinate(3,2);
        Coordinate c4 = new Coordinate(3,3);
        Coordinate c5 = new Coordinate(3,4);
        Coordinate c6 = new Coordinate(2,0);
        Die die1 = new Die(1,Color.BLUE);
        Die die2 = new Die (4,Color.YELLOW);
        Die die3 = new Die (3,Color.BLUE);
        Die die4 = new Die (2,Color.VIOLET);
        Die die5 = new Die (5,Color.GREEN);
        Die die6 = new Die (2,Color.VIOLET);
        window.addDie(c1,die1,window.getCellMatrix());
        window.addDie(c2,die2,window.getCellMatrix());
        window.addDie(c3,die3,window.getCellMatrix());
        window.addDie(c4,die4,window.getCellMatrix());
        window.addDie(c5,die5,window.getCellMatrix());
        window.addDie(c6,die6,window.getCellMatrix());
        PBObjectiveCard pb = new PBObjectiveCard(3);
        pb.doPBStrategy(p);
        assertEquals(5,p.getScore());
    }

    @Test
    public void testPB4(){
        Player p = new Player("io",4, Color.GREEN);
        WindowPattern window = p.getWindowPattern();
        Coordinate c1 = new Coordinate(0,0);
        Coordinate c2 = new Coordinate(1,0);
        Coordinate c3 = new Coordinate(2,0);
        Coordinate c4 = new Coordinate(3,0);
        Coordinate c5 = new Coordinate(0,1);
        Coordinate c6 = new Coordinate(0,2);
        Coordinate c7 = new Coordinate(1,2);
        Coordinate c8 = new Coordinate(2,2);
        Die die1 = new Die(1,Color.VIOLET);
        Die die2 = new Die (5,Color.GREEN);
        Die die3 = new Die (3,Color.RED);
        Die die4 = new Die (2,Color.YELLOW);
        Die die5 = new Die (6,Color.GREEN);
        Die die6 = new Die (4,Color.YELLOW);
        Die die7 = new Die (5,Color.VIOLET);
        Die die8 = new Die (3,Color.RED);
        window.addDie(c1,die1,window.getCellMatrix());
        window.addDie(c2,die2,window.getCellMatrix());
        window.addDie(c3,die3,window.getCellMatrix());
        window.addDie(c4,die4,window.getCellMatrix());
        window.addDie(c5,die5,window.getCellMatrix());
        window.addDie(c6,die6,window.getCellMatrix());
        window.addDie(c7,die7,window.getCellMatrix());
        window.addDie(c8,die8,window.getCellMatrix());
        PBObjectiveCard pb = new PBObjectiveCard(4);
        pb.doPBStrategy(p);
        assertEquals(4,p.getScore());
    }

    @Test
    public void testPB7(){
        Player p = new Player("io",5, Color.GREEN);
        WindowPattern window = p.getWindowPattern();
        Coordinate c1 = new Coordinate(0,0);
        Coordinate c2 = new Coordinate(1,1);
        Coordinate c3 = new Coordinate(2,2);
        Coordinate c4 = new Coordinate(3,3);
        Coordinate c5 = new Coordinate(0,1);
        Coordinate c6 = new Coordinate(0,2);
        Coordinate c7 = new Coordinate(0,3);
        Coordinate c8 = new Coordinate(0,4);
        Die die1 = new Die(1,Color.VIOLET);
        Die die2 = new Die (5,Color.GREEN);
        Die die3 = new Die (4,Color.RED);
        Die die4 = new Die (5,Color.GREEN);
        Die die5 = new Die (6,Color.BLUE);
        Die die6 = new Die (1,Color.YELLOW);
        Die die7 = new Die (5,Color.VIOLET);
        Die die8 = new Die (3,Color.RED);
        window.addDie(c1,die1,window.getCellMatrix());
        window.addDie(c2,die2,window.getCellMatrix());
        window.addDie(c3,die3,window.getCellMatrix());
        window.addDie(c4,die4,window.getCellMatrix());
        window.addDie(c5,die5,window.getCellMatrix());
        window.addDie(c6,die6,window.getCellMatrix());
        window.addDie(c7,die7,window.getCellMatrix());
        window.addDie(c8,die8,window.getCellMatrix());
        PBObjectiveCard pb = new PBObjectiveCard(7);
        pb.doPBStrategy(p);
        assertEquals(2,p.getScore());
    }

    @Test
    public void testPB8(){
        Player p = new Player("io",6, Color.GREEN);
        WindowPattern window = p.getWindowPattern();
        Coordinate c1 = new Coordinate(1,0);
        Coordinate c2 = new Coordinate(0,1);
        Coordinate c3 = new Coordinate(1,2);
        Coordinate c4 = new Coordinate(2,3);
        Coordinate c5 = new Coordinate(1,3);
        Coordinate c6 = new Coordinate(0,4);
        Coordinate c7 = new Coordinate(2,2);
        Coordinate c8 = new Coordinate(3,2);
        Die die1 = new Die(6,Color.VIOLET);
        Die die2 = new Die (1,Color.GREEN);
        Die die3 = new Die (2,Color.RED);
        Die die4 = new Die (3,Color.YELLOW);
        Die die5 = new Die (5,Color.GREEN);
        Die die6 = new Die (4,Color.YELLOW);
        Die die7 = new Die (5,Color.VIOLET);
        Die die8 = new Die (1,Color.RED);
        window.addDie(c1,die1,window.getCellMatrix());
        window.addDie(c2,die2,window.getCellMatrix());
        window.addDie(c3,die3,window.getCellMatrix());
        window.addDie(c4,die4,window.getCellMatrix());
        window.addDie(c5,die5,window.getCellMatrix());
        window.addDie(c6,die6,window.getCellMatrix());
        window.addDie(c7,die7,window.getCellMatrix());
        window.addDie(c8,die8,window.getCellMatrix());
        PBObjectiveCard pb = new PBObjectiveCard(8);
        pb.doPBStrategy(p);
        assertEquals(5,p.getScore());
    }

    @Test
    public void testPB10(){
        Player p = new Player("io",7, Color.GREEN);
        WindowPattern window = p.getWindowPattern();
        Coordinate c1 = new Coordinate(0,0);
        Coordinate c2 = new Coordinate(1,0);
        Coordinate c3 = new Coordinate(2,0);
        Coordinate c4 = new Coordinate(3,0);
        Coordinate c5 = new Coordinate(0,1);
        Coordinate c6 = new Coordinate(0,2);
        Coordinate c7 = new Coordinate(0,3);
        Coordinate c8 = new Coordinate(0,4);
        Coordinate c9 = new Coordinate(1,4);
        Coordinate c10 = new Coordinate(2,4);
        Coordinate c11 = new Coordinate(3,4);
        Die die1 = new Die(1,Color.VIOLET);
        Die die2 = new Die (5,Color.GREEN);
        Die die3 = new Die (6,Color.RED);
        Die die4 = new Die (2,Color.BLUE);
        Die die5 = new Die (6,Color.GREEN);
        Die die6 = new Die (3,Color.YELLOW);
        Die die7 = new Die (5,Color.BLUE);
        Die die8 = new Die (3,Color.RED);
        Die die9 = new Die (1,Color.VIOLET);
        Die die10 = new Die (4,Color.YELLOW);
        Die die11 = new Die (1,Color.GREEN);
        window.addDie(c1,die1,window.getCellMatrix());
        window.addDie(c2,die2,window.getCellMatrix());
        window.addDie(c3,die3,window.getCellMatrix());
        window.addDie(c4,die4,window.getCellMatrix());
        window.addDie(c5,die5,window.getCellMatrix());
        window.addDie(c6,die6,window.getCellMatrix());
        window.addDie(c7,die7,window.getCellMatrix());
        window.addDie(c8,die8,window.getCellMatrix());
        window.addDie(c9,die9,window.getCellMatrix());
        window.addDie(c10,die10,window.getCellMatrix());
        window.addDie(c11,die11,window.getCellMatrix());
        PBObjectiveCard pb = new PBObjectiveCard(10);
        pb.doPBStrategy(p);
        assertEquals(8,p.getScore());
    }
}