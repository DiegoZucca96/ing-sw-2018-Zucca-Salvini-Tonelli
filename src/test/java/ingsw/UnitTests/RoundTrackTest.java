package ingsw.UnitTests;

import ingsw.model.*;
import junit.framework.TestCase;
import org.junit.*;


public class RoundTrackTest extends TestCase{

    private  RoundTrack rt;
    private  Die die1 = new Die(1,Color.BLUE);
    private  Die die2 = new Die(2,Color.BLUE);
    private  Die die3 = new Die(3,Color.BLUE);
    private  Die die4 = new Die(5,Color.VIOLET);

    public RoundTrackTest() {
        rt = new RoundTrack();
    }

    @Before
    public void setUpTests(){
        rt = new RoundTrack();
    }



    @Test
    public void testCurrentRound() {
        //verifico che il round corrente venga aggiornato correttamente, e non ecceda 10
        for (int i = 1; i <= 20; i++) {
            System.out.println(rt.getRound());
            if (i < 10) assertEquals(i, rt.getRound());
            else assertEquals(10, rt.getRound());
            rt.nextRound();
        }
    }

    @Test
    public void testAddTakeDie() {
        //verifica che i dadi vengano aggiunti correttamente
        rt.addDie(die1, 1);
        rt.addDie(die2, 1);
        rt.addDie(die3, 1);
        assertEquals(die1, rt.getDie(1, 0));
        assertEquals(die2, rt.getDie(1, 1));
        assertEquals(die3, rt.getDie(1, 2));

        //verifica che il rep venga aggiornato correttamente in caso di inserimento di dadi appartenenti a round non consecutivi
        rt.addDie(die1, 7);
        assertNull(rt.getDie(3, 0));
        assertNull(rt.getDie(5, 0));
        assertEquals(die1, rt.getDie(7, 0));

        //Verifica nel caso si modifichi la roundtrack per scambio o rimozione dadi
        assertEquals(rt.replaceDie(1, 1, die4).getNumber(),2);
        assertTrue(rt.getDie(1,1).getColor().equals(Color.VIOLET));
    }

    @Test
    public void testToString(){
        rt.addDie(die2,1);
        rt.addDie(die3,1);
        rt.addDie(die1,2);
        rt.addDie(die3,4);
        rt.addDie(die1,10);
        ViewData viewData = ViewData.instance();
        rt.notifyViewObserver();
        assertEquals("1die(2,BLUE)",viewData.getRoundTrack().get(0));
        assertEquals("1die(3,BLUE)",viewData.getRoundTrack().get(1));
        assertEquals("2die(1,BLUE)",viewData.getRoundTrack().get(2));
        assertEquals("4die(3,BLUE)",viewData.getRoundTrack().get(3));
        assertEquals("10die(1,BLUE)",viewData.getRoundTrack().get(4));

    }

}
