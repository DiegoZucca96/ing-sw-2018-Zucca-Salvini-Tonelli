package ingsw.UnitTests;

import ingsw.model.Color;
import ingsw.model.PVObjectiveCard;
import ingsw.model.Player;
import ingsw.model.ToolCard;
import ingsw.model.windowpattern.WindowPattern;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerTest {

    private Player p;
    private WindowPattern wp;
    private PVObjectiveCard pvCard;

    @Before
    public void setUpTests(){
        p = new Player("Norma", "wp1", Color.BLUE);
        wp = mock(WindowPattern.class);
        pvCard = mock(PVObjectiveCard.class);
        p.setWindowPattern(wp);
        p.setPvObjectiveCard(pvCard);
    }

    @Test
    public void testUseToken(){
        ToolCard t = mock(ToolCard.class);
        when(t.isAlreadyUsed()).thenReturn(false);
        assertEquals(4,p.getTokens());
        p.useToken(t);
        assertEquals(3,p.getTokens());
        when(t.isAlreadyUsed()).thenReturn(true);
        p.useToken(t);
        assertEquals(1,p.getTokens());
    }

    @Test
    public void testPersonalScore(){
        when(wp.countDie(pvCard.getColor(), wp.getCellMatrix())).thenReturn(10);
        when(wp.countEmptyCells(wp.getCellMatrix())).thenReturn(5);
        p.personalScore();
        assertEquals(9, p.getScore());
        assertEquals(10,p.getPVScore());
    }
}
