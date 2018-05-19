package ingsw.UnitTests;

import ingsw.model.*;
import org.junit.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;


public class MatchTest{

    private Match match;
    private DraftPool dp;
    private RoundTrack rt;
    private ArrayList<Player> players = new ArrayList<Player>();
    private ArrayList<ToolCard> tools = new ArrayList<ToolCard>();
    private ArrayList<PBObjectiveCard> pbObjectiveCards = new ArrayList<PBObjectiveCard>();
    private Player elio;
    private Player diego;
    private Player tony;
    private Player norma;
    private ToolCard tool1;
    private ToolCard tool2;
    private ToolCard tool3;
    private PBObjectiveCard pbObjectiveCard1;
    private PBObjectiveCard pbObjectiveCard2;
    private PBObjectiveCard pbObjectiveCard3;

    @Before
    public void setUpTests(){
        ArrayList<String> names = new ArrayList<String>();
        names.add("Elio");
        names.add("Diego");
        names.add("Tony");
        names.add("Norma");

        ArrayList<Integer > wps = new ArrayList<Integer>();
        wps.add(1);
        wps.add(2);
        wps.add(3);
        wps.add(4);

        match = new Match(1, names, wps);

        dp = mock(DraftPool.class);
        rt = mock(RoundTrack.class);

        elio = mock(Player.class);
        diego = mock(Player.class);
        tony = mock(Player.class);
        norma = mock(Player.class);
        tool1 = mock(ToolCard.class);
        tool2 = mock(ToolCard.class);
        tool3 = mock(ToolCard.class);
        pbObjectiveCard1 = mock(PBObjectiveCard.class);
        pbObjectiveCard2 = mock(PBObjectiveCard.class);
        pbObjectiveCard3 = mock(PBObjectiveCard.class);

        players.add(elio);
        players.add(diego);
        players.add(tony);
        players.add(norma);
        tools.add(tool1);
        tools.add(tool2);
        tools.add(tool3);
        pbObjectiveCards.add(pbObjectiveCard1);
        pbObjectiveCards.add(pbObjectiveCard2);
        pbObjectiveCards.add(pbObjectiveCard3);

        match.setDraftPool(dp);
        match.setRoundTrack(rt);
        match.setPlayers(players);
        match.setCurrentPlayer(elio);
        match.setTools(tools);
        match.setPbCards(pbObjectiveCards);

        //inizializzazione dei punteggi e dei token dei giocatori
        when(elio.getScore()).thenReturn(65);
        when(elio.getPVScore()).thenReturn(30);
        when(elio.getTokens()).thenReturn(4);
        when(diego.getScore()).thenReturn(65);
        when(diego.getPVScore()).thenReturn(30);
        when(diego.getTokens()).thenReturn(4);
        when(tony.getScore()).thenReturn(65);
        when(tony.getPVScore()).thenReturn(30);
        when(tony.getTokens()).thenReturn(4);
        when(norma.getScore()).thenReturn(65);
        when(norma.getPVScore()).thenReturn(27);
        when(norma.getTokens()).thenReturn(4);
    }

    @Test
    public void testMatch(){
        assertEquals(4,match.getNumOfPlayers());
        assertEquals(elio,match.getCurrentPlayer());
    }

    @Test
    public void testFindwinner(){
        assertEquals(elio, match.findWinner());
        when(tony.getTokens()).thenReturn(5);
        assertEquals(tony, match.findWinner());
        when(diego.getScore()).thenReturn(70);
        assertEquals(diego, match.findWinner());
    }

    @Test
    public void testNextTurn(){
        assertEquals(elio, match.getCurrentPlayer());
        match.nextTurn();
        assertEquals(diego, match.getCurrentPlayer());
        match.nextTurn();
        assertEquals(tony, match.getCurrentPlayer());
        match.nextTurn();
        assertEquals(norma, match.getCurrentPlayer());
        match.nextTurn();
        assertEquals(norma, match.getCurrentPlayer());
        match.nextTurn();
        assertEquals(tony, match.getCurrentPlayer());
        match.nextTurn();
        assertEquals(diego, match.getCurrentPlayer());
        match.nextTurn();
        assertEquals(elio, match.getCurrentPlayer());
    }

    //Testare ulteriori funzionalità di match a livello di unità non ha grande utilità ai fini della verifica del sw

}
