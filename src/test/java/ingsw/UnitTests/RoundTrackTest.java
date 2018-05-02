package ingsw.UnitTests;

import ingsw.model.*;
import org.junit.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class RoundTrackTest{

    private  RoundTrack rt;
    private  Die die1;
    private  Die die2;
    private  Die die3;

    public RoundTrackTest() {
        rt = new RoundTrack();
    }

    @Before
    public void setUpTests(){
        rt = new RoundTrack();
        die1 = mock(Die.class);
        die2 = mock(Die.class);
        die3 = mock(Die.class);
        when(die1.getNumber()).thenReturn(1);
        when(die1.getColor()).thenReturn(Color.BLUE);
        when(die2.getNumber()).thenReturn(2);
        when(die2.getColor()).thenReturn(Color.BLUE);
        when(die3.getNumber()).thenReturn(3);
        when(die3.getColor()).thenReturn(Color.BLUE);
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

        //verifica che venga rimosso il dado
        assertEquals(die1, rt.takeDie(1, 0));
        assertNull(rt.getDie(1, 2));

        //verifica che il rep venga aggiornato correttamente in caso di inserimento di dadi appartenenti a round non consecutivi
        rt.addDie(die1, 7);
        assertNull(rt.getDie(3, 0));
        assertNull(rt.getDie(5, 0));
        assertEquals(die1, rt.getDie(7, 0));
    }

}
