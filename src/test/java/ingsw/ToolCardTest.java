package ingsw;

import ingsw.model.*;
import ingsw.model.windowpattern.WindowPattern;
import org.junit.jupiter.api.*;
import junit.framework.TestCase;


class ToolCardTest extends TestCase{

    @Test
    public void testTool1(){
        Die die1 = new Die(1, Color.BLUE);
        Die die2 = new Die(1, Color.GREEN);
        Die die3 = new Die(6, Color.YELLOW);
        Die die4 = new Die(6, Color.RED);
        ToolCard t1 = new ToolCard(1);
        //Controllo se è la prima volta che la uso e se effettivamente è quella che volevo
        assertFalse(t1.isAlreadyUsed());
        assertEquals(1,t1.getIdCard());

        ObjectiveTool object1 = new ObjectiveTool(die1,-1);
        t1.doToolStrategy(object1);
        //Verifico che non è cambiato a causa della violazione della regola
        assertEquals(1,die1.getNumber());

        ObjectiveTool object2 = new ObjectiveTool(die2,1);
        t1.doToolStrategy(object2);
        //Verifico che effettivamente è cambiato, ripeto per il valore all'estremo opposto
        assertEquals(2,die2.getNumber());

        ObjectiveTool object3 = new ObjectiveTool(die3,1);
        t1.doToolStrategy(object3);

        assertEquals(6,die3.getNumber());

        ObjectiveTool object4 = new ObjectiveTool(die4,-1);
        t1.doToolStrategy(object4);

        assertEquals(5,die4.getNumber());
    }

    @Test
    public void testTool2(){
        Player p = new Player("me","wp8",Color.YELLOW);
        WindowPattern window = p.getWindowPattern();
        Coordinate c = new Coordinate(0,2);
        Coordinate d = new Coordinate(2,1);
        Coordinate c1 = new Coordinate(0,0);
        Coordinate c2 = new Coordinate(0,1);
        Coordinate c3 = new Coordinate(1,0);
        Coordinate c4 = new Coordinate(1,1);
        Die die = new Die(4,Color.GREEN);
        Die die1 = new Die(6,Color.YELLOW);
        Die die2 = new Die(1,Color.BLUE);
        Die die3 = new Die(4,Color.GREEN);
        Die die4 = new Die(5,Color.VIOLET);
        window.addDie(c1,die1,window.getCellMatrix());
        window.addDie(c2,die2,window.getCellMatrix());
        window.addDie(c3,die3,window.getCellMatrix());
        window.addDie(c4,die4,window.getCellMatrix());
        window.addDie(c,die,window.getCellMatrix());
        ObjectiveTool object = new ObjectiveTool(window,c,d);
        ToolCard t2 = new ToolCard(2);
        window.addDie(d,die,window.getCellMatrix());
        //Testo che normalmente non si possa inserire il dado
        assertTrue(window.getCellMatrix()[d.getX()][d.getY()].isEmpty());

        t2.doToolStrategy(object);
        //Verifico la funzionalità della ToolCard
        assertFalse(window.getCellMatrix()[d.getX()][d.getY()].isEmpty());
        assertTrue(window.getCellMatrix()[c.getX()][c.getY()].isEmpty());

        Coordinate wrongDest = new Coordinate(3,4);
        ObjectiveTool wrongObject = new ObjectiveTool(window,d,wrongDest);
        t2.doToolStrategy(wrongObject);
        //Verifico che rispetti tutte le altre restrizioni
        assertFalse(window.getCellMatrix()[d.getX()][d.getY()].isEmpty());
        assertTrue(window.getCellMatrix()[wrongDest.getX()][wrongDest.getY()].isEmpty());
    }

    @Test
    public void testTool3(){
        Player p = new Player("me","wp9",Color.YELLOW);
        WindowPattern window = p.getWindowPattern();
        Coordinate c = new Coordinate(0,2);
        Coordinate d = new Coordinate(2,2);
        Coordinate c1 = new Coordinate(0,0);
        Coordinate c2 = new Coordinate(0,1);
        Coordinate c3 = new Coordinate(1,0);
        Coordinate c4 = new Coordinate(1,1);
        Die die = new Die(2,Color.GREEN);
        Die die1 = new Die(6,Color.YELLOW);
        Die die2 = new Die(1,Color.BLUE);
        Die die3 = new Die(3,Color.GREEN);
        Die die4 = new Die(4,Color.VIOLET);
        window.addDie(c1,die1,window.getCellMatrix());
        window.addDie(c2,die2,window.getCellMatrix());
        window.addDie(c3,die3,window.getCellMatrix());
        window.addDie(c4,die4,window.getCellMatrix());
        window.addDie(c,die,window.getCellMatrix());
        ObjectiveTool object = new ObjectiveTool(window,c,d);
        ToolCard t2 = new ToolCard(2);
        window.addDie(d,die,window.getCellMatrix());
        //Testo che normalmente non si possa inserire il dado
        assertTrue(window.getCellMatrix()[d.getX()][d.getY()].isEmpty());

        t2.doToolStrategy(object);
        //Verifico la funzionalità della ToolCard
        assertFalse(window.getCellMatrix()[d.getX()][d.getY()].isEmpty());
        assertTrue(window.getCellMatrix()[c.getX()][c.getY()].isEmpty());

        Coordinate wrongDest = new Coordinate(3,4);
        ObjectiveTool wrongObject = new ObjectiveTool(window,d,wrongDest);
        t2.doToolStrategy(wrongObject);
        //Verifico che rispetti tutte le altre restrizioni
        assertFalse(window.getCellMatrix()[d.getX()][d.getY()].isEmpty());
        assertTrue(window.getCellMatrix()[wrongDest.getX()][wrongDest.getY()].isEmpty());
    }

    @Test
    public void testTool4(){
        Player p = new Player("me","wp10",Color.YELLOW);
        WindowPattern window = p.getWindowPattern();
        Coordinate c = new Coordinate(0,2);
        Coordinate da = new Coordinate(2,1);
        Coordinate db = new Coordinate(3,1);
        Coordinate c1 = new Coordinate(0,0);
        Coordinate c2 = new Coordinate(0,1);
        Coordinate c3 = new Coordinate(1,0);
        Coordinate c4 = new Coordinate(1,1);
        Die die = new Die(6,Color.GREEN);
        Die die1 = new Die(6,Color.YELLOW);
        Die die2 = new Die(1,Color.VIOLET);
        Die die3 = new Die(5,Color.RED);
        Die die4 = new Die(4,Color.YELLOW);
        window.addDie(c1,die1,window.getCellMatrix());
        window.addDie(c2,die2,window.getCellMatrix());
        window.addDie(c3,die3,window.getCellMatrix());
        window.addDie(c4,die4,window.getCellMatrix());
        window.addDie(c,die,window.getCellMatrix());
        ObjectiveTool object = new ObjectiveTool(window,c,c3,da,db);
        ToolCard t4 = new ToolCard(4);
        t4.doToolStrategy(object);
        //Testo il corretto funzionamento della ToolCard
        assertFalse(window.getCellMatrix()[da.getX()][da.getY()].isEmpty());
        assertFalse(window.getCellMatrix()[db.getX()][db.getY()].isEmpty());
        assertTrue(window.getCellMatrix()[c.getX()][c.getY()].isEmpty());
        assertTrue(window.getCellMatrix()[c3.getX()][c3.getY()].isEmpty());

        Coordinate c5 = new Coordinate(0,3);
        ObjectiveTool object1 = new ObjectiveTool(window,c1,db,c,c5);
        t4.doToolStrategy(object1);
        //Testo se reinserendo un dado in una cella precedentemente occupata crea problemi (verifica del setEmpty)
        assertFalse(window.getCellMatrix()[c.getX()][c.getY()].isEmpty());
        assertFalse(window.getCellMatrix()[c5.getX()][c5.getY()].isEmpty());
        assertTrue(window.getCellMatrix()[c1.getX()][c1.getY()].isEmpty());
        assertTrue(window.getCellMatrix()[db.getX()][db.getY()].isEmpty());

        ObjectiveTool object2 = new ObjectiveTool(window,da,c5,c1,db);
        t4.doToolStrategy(object2);
        //Testo se nel caso di violazione sul secondo dado il primo sia rimesso a posto
        assertFalse(window.getCellMatrix()[da.getX()][da.getY()].isEmpty());
        assertFalse(window.getCellMatrix()[c5.getX()][c5.getY()].isEmpty());
        assertTrue(window.getCellMatrix()[c1.getX()][c1.getY()].isEmpty());
        assertTrue(window.getCellMatrix()[db.getX()][db.getY()].isEmpty());

    }

    @Test
    public void testTool6(){
        Player p = new Player("me","wp11",Color.YELLOW);
        WindowPattern window = p.getWindowPattern();
        DraftPool dp = new DraftPool(new RoundTrack());
        Coordinate c1 = new Coordinate(3,1);
        Coordinate c2 = new Coordinate(3,2);
        Coordinate c3 = new Coordinate(2,1);
        Coordinate c4 = new Coordinate(1,1);
        Coordinate c5 = new Coordinate(1,2);
        Coordinate c6 = new Coordinate(1,3);
        Coordinate c7 = new Coordinate(2,3);
        Coordinate c8 = new Coordinate(0,0);
        Coordinate c9 = new Coordinate(3,0);
        Coordinate c10 = new Coordinate(0,4);
        Coordinate c11 = new Coordinate(3,4);
        Coordinate c12 = new Coordinate(2,2);
        Die die1 = new Die(5,Color.YELLOW);
        Die die2 = new Die(2,Color.VIOLET);
        Die die3 = new Die(1,Color.RED);
        Die die4 = new Die(4,Color.YELLOW);
        Die die5 = new Die(3,Color.GREEN);
        Die die6 = new Die(5,Color.BLUE);
        Die die7 = new Die(4,Color.GREEN);
        Die die8 = new Die (6,Color.BLUE);
        Die myDie = new Die(3,Color.RED);
        window.addDie(c1,die1,window.getCellMatrix());
        window.addDie(c2,die2,window.getCellMatrix());
        window.addDie(c3,die3,window.getCellMatrix());
        window.addDie(c4,die4,window.getCellMatrix());
        window.addDie(c5,die5,window.getCellMatrix());
        window.addDie(c6,die6,window.getCellMatrix());
        window.addDie(c7,die7,window.getCellMatrix());
        window.addDie(c8,die1,window.getCellMatrix());
        window.addDie(c9,die3,window.getCellMatrix());
        window.addDie(c10,die2,window.getCellMatrix());
        window.addDie(c11,die7,window.getCellMatrix());
        window.addDie(c12,die8,window.getCellMatrix());
        ObjectiveTool object = new ObjectiveTool(myDie,window,dp);
        ToolCard t6 = new ToolCard(6);
        dp.throwsDice(5);
        //Testo la dimensione effettiva della DraftPool
        assertEquals(5,dp.getDiceListSize());

        t6.doToolStrategy(object);
        //Verifico che il dado sia stato messo nella DraftPool perchè non ci sono posti per inserirlo
        assertEquals(6,dp.getDiceListSize());

        window.removeDie(c5,window.getCellMatrix());
        ObjectiveTool object2 = new ObjectiveTool(die6,window,dp);
        t6.doToolStrategy(object2);
        //Visto che è inseribile guardo se non è cambiato nulla
        assertEquals(6,dp.getDiceListSize());
    }

    @Test
    public void testTool9(){
        Player p = new Player("me","wp12",Color.YELLOW);
        WindowPattern window = p.getWindowPattern();
        Coordinate d = new Coordinate(2,1);
        Coordinate c1 = new Coordinate(3,4);
        Coordinate c2 = new Coordinate(3,3);
        Coordinate c3 = new Coordinate(2,4);
        Coordinate c4 = new Coordinate(2,3);
        Die die = new Die(1,Color.GREEN);
        Die die1 = new Die(6,Color.YELLOW);
        Die die2 = new Die(1,Color.BLUE);
        Die die3 = new Die(4,Color.GREEN);
        Die die4 = new Die(5,Color.VIOLET);
        window.addDie(c1,die1,window.getCellMatrix());
        window.addDie(c2,die2,window.getCellMatrix());
        window.addDie(c3,die3,window.getCellMatrix());
        window.addDie(c4,die4,window.getCellMatrix());
        ObjectiveTool object = new ObjectiveTool(die,window,d);
        ToolCard t9 = new ToolCard(9);
        window.addDie(d,die,window.getCellMatrix());
        //Testo il fatto che normalmente non si può mettere il dado
        assertTrue(window.getCellMatrix()[d.getX()][d.getY()].isEmpty());

        t9.doToolStrategy(object);
        //Verifico la funzionalità della ToolCard
        assertFalse(window.getCellMatrix()[d.getX()][d.getY()].isEmpty());

        Coordinate wrongD = new Coordinate(1,0);
        ObjectiveTool object2 = new ObjectiveTool(die,window,wrongD);
        t9.doToolStrategy(object2);
        //Verifico che le restrizioni rimangano verificate
        assertTrue(window.getCellMatrix()[wrongD.getX()][wrongD.getY()].isEmpty());

    }

    @Test
    public void testTool10(){
        Die die = new Die(4,Color.BLUE);
        Die die2 = new Die(1,Color.VIOLET);
        Die die3 = new Die(-1,Color.GREEN);
        ObjectiveTool object = new ObjectiveTool(die);
        ObjectiveTool object2 = new ObjectiveTool(die2);
        ObjectiveTool object3 = new ObjectiveTool(die3);
        ToolCard t10 = new ToolCard(10);
        t10.doToolStrategy(object);
        t10.doToolStrategy(object2);
        t10.doToolStrategy(object3);

        assertEquals(3,die.getNumber());
        assertEquals(6,die2.getNumber());
        assertEquals(-1,die3.getNumber());
    }

    @Test
    public void testTool12(){
        Player p = new Player("me","wp13",Color.YELLOW);
        WindowPattern window = p.getWindowPattern();
        Coordinate d1 = new Coordinate(2,0);
        Coordinate d2 = new Coordinate(1,2);
        Coordinate c1 = new Coordinate(0,0);
        Coordinate c2 = new Coordinate(0,1);
        Coordinate c3 = new Coordinate(1,0);
        Coordinate c4 = new Coordinate(1,1);
        Die die1 = new Die(2,Color.VIOLET);
        Die die2 = new Die(1,Color.BLUE);
        Die die3 = new Die(4,Color.YELLOW);
        Die die4 = new Die(6,Color.VIOLET);
        window.addDie(c1,die1,window.getCellMatrix());
        window.addDie(c2,die2,window.getCellMatrix());
        window.addDie(c3,die3,window.getCellMatrix());
        window.addDie(c4,die4,window.getCellMatrix());
        ObjectiveTool object = new ObjectiveTool(window,c1,c4,d1,d2,Color.VIOLET);
        ToolCard t12 = new ToolCard(12);
        t12.doToolStrategy(object);
        //Verifico il corretto funzionamento di entrambi gli spostamenti
        assertFalse(window.getCellMatrix()[d1.getX()][d1.getY()].isEmpty());
        assertFalse(window.getCellMatrix()[d2.getX()][d2.getY()].isEmpty());
        assertTrue(window.getCellMatrix()[c1.getX()][c1.getY()].isEmpty());
        assertTrue(window.getCellMatrix()[c4.getX()][c4.getY()].isEmpty());

        Coordinate c5 = new Coordinate(0,3);
        ObjectiveTool object2 = new ObjectiveTool(window,d2,d1,c1,c5,Color.VIOLET);
        t12.doToolStrategy(object2);
        //Nonostante mi aspetti di non spostare il secondo dado, in realtà sposto solo quello perchè il primo non rispetta le restrizioni
        assertFalse(window.getCellMatrix()[d2.getX()][d2.getY()].isEmpty());
        assertTrue(window.getCellMatrix()[d1.getX()][d1.getY()].isEmpty());
        assertTrue(window.getCellMatrix()[c1.getX()][c1.getY()].isEmpty());
        assertFalse(window.getCellMatrix()[c5.getX()][c5.getY()].isEmpty());

    }
}