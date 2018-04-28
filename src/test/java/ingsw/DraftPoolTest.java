package ingsw;

import ingsw.model.*;
import junit.framework.TestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DraftPoolTest extends TestCase{

    private Die die;
    private DiceBag db;
    private RoundTrack rt;
    private DraftPool dp;

    @BeforeAll
    public void setUpTests(){
        die = mock(Die.class);
        db = mock(DiceBag.class);
        rt = mock(RoundTrack.class);
        dp = new DraftPool(rt);
        when(db.randomDice()).thenReturn(die);
        when(die.getNumber()).thenReturn(1);
        when(die.getColor()).thenReturn(Color.BLUE);
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

    @Test public void testCleanDraftPool(){
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
