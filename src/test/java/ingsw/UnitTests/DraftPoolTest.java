package ingsw.UnitTests;

import ingsw.model.*;
import org.junit.*;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;


public class DraftPoolTest{

    private Die die;
    private DiceBag db;
    private RoundTrack rt;
    private DraftPool dp;

    public DraftPoolTest(){
    dp = new DraftPool(rt);
    db = null;
    rt = null;
    die = null;
    }

    @Before
    public void setUpTests(){
        die = mock(Die.class);
        db = mock(DiceBag.class);
        rt = mock(RoundTrack.class);
        dp = new DraftPool(rt);
        dp.setDiceBag(db);
        when(db.randomDice()).thenReturn(mock(Die.class));
        when(die.getNumber()).thenReturn(1);
        when(die.getColor()).thenReturn(Color.BLUE);
        when(rt.getRound()).thenReturn((2));
        doNothing().when(rt).addDie(die,2);
    }

    @Test
    public void testThrowsDice(){
         dp.throwsDice(3);
         //verifica che la lista restituita sia completa
         assertNotNull(dp.getDie(0));
         assertNotNull(dp.getDie(1));
         assertNotNull(dp.getDie(2));
         //verifica che la lista sia effettivamente formata da soli 3 dadi
         assertNull(dp.getDie(3));
    }

    @Test
    public void testTakeDie(){
        dp.throwsDice(3);
        //verifica che ci sia l'ultimo dado
        assertNotNull(dp.getDie(2));
        //prendo un dado, il numero dei dadi diminuisce
        assertNotNull(dp.takeDie(1));
        //l'ultimo dado non è più in posizione 2
        assertNull(dp.getDie(2));
    }

    @Test
    public void testCleanDraftPool(){
        dp.throwsDice(3);
        //draft pool piena
        assertNotNull(dp.getDie(0));
        assertNotNull(dp.getDie(1));
        assertNotNull(dp.getDie(2));
        //svuoto la draft pool
        dp.cleanDraftPool();
        //verifico che la draft pool sia effettivamente vuota
        assertNull(dp.getDie(0));
    }
}
