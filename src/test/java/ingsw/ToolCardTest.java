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
}