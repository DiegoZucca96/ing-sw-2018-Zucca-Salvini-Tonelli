package ingsw;

import ingsw.model.*;
import ingsw.model.windowpattern.WindowPattern;
import org.junit.jupiter.api.*;
import junit.framework.TestCase;
import java.util.ArrayList;

class ToolCardTest extends TestCase{

    @Test
    public void testTool1(){
        DraftPool dp = new DraftPool(new RoundTrack());
        ArrayList<String> players = new ArrayList<>();
        players.add("Diego");
        players.add("Ezio");
        ArrayList<Integer> playersWP = new ArrayList<>();
        playersWP.add(2);
        playersWP.add(4);
        Match match = new Match(1,players,playersWP);
        Die die1 = new Die(1, Color.BLUE);
        Die die2 = new Die(1, Color.GREEN);
        Die die3 = new Die(6, Color.YELLOW);
        Die die4 = new Die(6, Color.RED);
        dp.throwsDice(5);
        match.setDraftPool(dp);
        dp.setDie(0,die1);
        dp.setDie(1,die2);
        dp.setDie(2,die3);
        dp.setDie(3,die4);
        Coordinate c3 = new Coordinate(0,2);
        Coordinate c1 = new Coordinate(0,0);


        ToolCard t1 = new ToolCard(1);
        ArrayList<ToolCard> tools = new ArrayList<>();
        tools.add(t1);
        match.setTools(tools);
        //Controllo se è la prima volta che la uso e se effettivamente è quella che volevo
        assertFalse(t1.isAlreadyUsed());
        assertEquals(1,t1.getIdCard());

        PlayerToolParameter pt = new PlayerToolParameter();
        pt.setC1(c3);
        pt.setDieModified(5);
        match.playerUseTool(1,null);
        match.playerUseTool(1,pt);
        //Verifico che è cambiato il valore e che la carta è stata usata
        assertEquals(5,dp.getDie(2).getNumber());
        assertTrue(t1.isAlreadyUsed());

        pt.setDieModified(4);
        match.playerUseTool(1,null);
        match.playerUseTool(1,pt);
        //Verifico che effettivamente è cambiato
        assertEquals(4,dp.getDie(2).getNumber());

        //Consumo token
        match.playerUseTool(1,null);

        pt.setC1(c1);
        pt.setDieModified(2);
        if(match.playerUseTool(1,null))
            match.playerUseTool(1,pt);
        //Verifico che non sia cambiato perchè ho finito i token
        assertEquals(1,dp.getDie(0).getNumber());
    }

    @Test
    public void testTool2(){
        ArrayList<String> players = new ArrayList<>();
        players.add("Diego");
        ArrayList<Integer> playersWP = new ArrayList<>();
        playersWP.add(8);
        Match match = new Match(1,players,playersWP);
        WindowPattern window = match.getCurrentPlayer().getWindowPattern();
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
        ToolCard t2 = new ToolCard(2);
        ArrayList<ToolCard> tools = new ArrayList<>();
        tools.add(t2);
        match.setTools(tools);


        window.addDie(d,die,window.getCellMatrix());
        //Testo che normalmente non si possa inserire il dado
        assertTrue(window.getCellMatrix()[d.getX()][d.getY()].isEmpty());
        assertFalse(t2.isAlreadyUsed());

        PlayerToolParameter pt = new PlayerToolParameter();
        pt.setC1(c);
        pt.setD1(d);
        match.playerUseTool(2,null);
        match.playerUseTool(2,pt);
        //Verifico la funzionalità della ToolCard
        assertFalse(window.getCellMatrix()[d.getX()][d.getY()].isEmpty());
        assertTrue(window.getCellMatrix()[c.getX()][c.getY()].isEmpty());
        assertTrue(t2.isAlreadyUsed());

        Coordinate wrongDest = new Coordinate(3,4);
        pt.setD1(wrongDest);
        match.playerUseTool(2,null);
        match.playerUseTool(2,pt);
        //Verifico che rispetti tutte le altre restrizioni
        assertFalse(window.getCellMatrix()[d.getX()][d.getY()].isEmpty());
        assertTrue(window.getCellMatrix()[wrongDest.getX()][wrongDest.getY()].isEmpty());
    }

    @Test
    public void testTool3(){
        ArrayList<String> players = new ArrayList<>();
        players.add("Diego");
        ArrayList<Integer> playersWP = new ArrayList<>();
        playersWP.add(9);
        Match match = new Match(1,players,playersWP);
        WindowPattern window = match.getCurrentPlayer().getWindowPattern();
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
        ToolCard t3 = new ToolCard(3);
        ArrayList<ToolCard> tools = new ArrayList<>();
        tools.add(t3);
        match.setTools(tools);

        window.addDie(d,die,window.getCellMatrix());
        //Testo che normalmente non si possa inserire il dado
        assertTrue(window.getCellMatrix()[d.getX()][d.getY()].isEmpty());

        PlayerToolParameter pt = new PlayerToolParameter();
        pt.setC1(c);
        pt.setD1(d);
        match.playerUseTool(3,null);
        match.playerUseTool(3,pt);
        //Verifico la funzionalità della ToolCard
        assertFalse(window.getCellMatrix()[d.getX()][d.getY()].isEmpty());
        assertTrue(window.getCellMatrix()[c.getX()][c.getY()].isEmpty());

        Coordinate wrongDest = new Coordinate(3,4);
        pt.setD1(wrongDest);
        match.playerUseTool(3,null);
        match.playerUseTool(3,pt);
        //Verifico che rispetti tutte le altre restrizioni
        assertFalse(window.getCellMatrix()[d.getX()][d.getY()].isEmpty());
        assertTrue(window.getCellMatrix()[wrongDest.getX()][wrongDest.getY()].isEmpty());
    }

    @Test
    public void testTool4(){
        ArrayList<String> players = new ArrayList<>();
        players.add("Diego");
        ArrayList<Integer> playersWP = new ArrayList<>();
        playersWP.add(10);
        Match match = new Match(1,players,playersWP);
        WindowPattern window = match.getCurrentPlayer().getWindowPattern();
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
        ToolCard t4 = new ToolCard(4);
        ArrayList<ToolCard> tools = new ArrayList<>();
        tools.add(t4);
        match.setTools(tools);

        PlayerToolParameter pt = new PlayerToolParameter();
        pt.setC1(c);
        pt.setD1(db);
        pt.setPhase(0);
        match.playerUseTool(4,null);
        match.playerUseTool(4,pt);
        //Testo che è tutto come prima a causa di violazione della regola
        assertTrue(window.getCellMatrix()[db.getX()][db.getY()].isEmpty());
        assertFalse(window.getCellMatrix()[c.getX()][c.getY()].isEmpty());

        pt.setD1(da);
        pt.setC2(c3);
        pt.setD2(db);
        match.playerUseTool(4,pt);
        pt.setPhase(1);
        match.playerUseTool(4,pt);
        //Testo il corretto funzionamento della ToolCard
        assertFalse(window.getCellMatrix()[da.getX()][da.getY()].isEmpty());
        assertFalse(window.getCellMatrix()[db.getX()][db.getY()].isEmpty());
        assertTrue(window.getCellMatrix()[c.getX()][c.getY()].isEmpty());
        assertTrue(window.getCellMatrix()[c3.getX()][c3.getY()].isEmpty());

        Coordinate c5 = new Coordinate(0,3);
        pt.setC1(c1);
        pt.setD1(c);
        pt.setC2(db);
        pt.setD2(c5);
        pt.setPhase(0);
        match.playerUseTool(4,pt);
        pt.setPhase(1);
        match.playerUseTool(4,pt);
        //Testo se reinserendo un dado in una cella precedentemente occupata crea problemi (verifica del setEmpty)
        assertFalse(window.getCellMatrix()[c.getX()][c.getY()].isEmpty());
        assertFalse(window.getCellMatrix()[c5.getX()][c5.getY()].isEmpty());
        assertTrue(window.getCellMatrix()[c1.getX()][c1.getY()].isEmpty());
        assertTrue(window.getCellMatrix()[db.getX()][db.getY()].isEmpty());

        pt.setC1(da);
        pt.setD1(c1);
        pt.setC2(c5);
        pt.setD2(db);
        pt.setPhase(0);
        match.playerUseTool(4,pt);
        pt.setPhase(1);
        match.playerUseTool(4,pt);
        //Testo se nel caso di violazione sul secondo dado il primo sia rimesso a posto
        assertFalse(window.getCellMatrix()[da.getX()][da.getY()].isEmpty());
        assertFalse(window.getCellMatrix()[c5.getX()][c5.getY()].isEmpty());
        assertTrue(window.getCellMatrix()[c1.getX()][c1.getY()].isEmpty());
        assertTrue(window.getCellMatrix()[db.getX()][db.getY()].isEmpty());

    }

    @Test
    public void testTool5(){
        ArrayList<String> players = new ArrayList<>();
        players.add("Diego");
        ArrayList<Integer> playersWP = new ArrayList<>();
        playersWP.add(10);
        Match match = new Match(1,players,playersWP);
        DraftPool dp = new DraftPool(new RoundTrack());
        Die myDie = new Die(2,Color.RED);
        Coordinate c = new Coordinate(0,0);
        dp.throwsDice(3);
        match.setDraftPool(dp);
        dp.setDie(0,myDie);
        RoundTrack rt = new RoundTrack();
        rt.setRound(2);
        Die rtDie = new Die(3,Color.VIOLET);
        rt.addDie(rtDie,1);
        match.setRoundTrack(rt);
        ToolCard t5 = new ToolCard(5);
        ArrayList<ToolCard> tools = new ArrayList<>();
        tools.add(t5);
        match.setTools(tools);


        PlayerToolParameter pt = new PlayerToolParameter();
        pt.setC1(c);
        pt.setD1(c);
        pt.setRound(1);
        match.playerUseTool(5,null);
        match.playerUseTool(5,pt);
        //Verifico che effettivamente si siano scambiati i dadi
        assertEquals(3,dp.getDie(0).getNumber());
        assertEquals(Color.VIOLET,dp.getDie(0).getColor());
        assertEquals(2,rt.getDie(1,0).getNumber());
        assertEquals(Color.RED,rt.getDie(1,0).getColor());
    }

    @Test
    public void testTool6(){
        ArrayList<String> players = new ArrayList<>();
        players.add("Diego");
        ArrayList<Integer> playersWP = new ArrayList<>();
        playersWP.add(11);
        Match match = new Match(1,players,playersWP);
        DraftPool dp = new DraftPool(new RoundTrack());
        Die myDie = new Die(3,Color.RED);
        Coordinate c = new Coordinate(0,0);
        dp.throwsDice(3);
        match.setDraftPool(dp);
        dp.setDie(0,myDie);
        ToolCard t6 = new ToolCard(6);
        ArrayList<ToolCard> tools = new ArrayList<>();
        tools.add(t6);
        match.setTools(tools);


        //Testo la dimensione effettiva della DraftPool
        assertEquals(3,dp.getDiceListSize());

        PlayerToolParameter pt = new PlayerToolParameter();
        pt.setC1(c);
        match.playerUseTool(6,null);
        //Verifico che il cambio di valore è andato a buon fine
        assertTrue(match.playerUseTool(6,pt));

    }

    @Test
    public void testTool7(){
        ArrayList<String> players = new ArrayList<>();
        players.add("Diego");
        ArrayList<Integer> playersWP = new ArrayList<>();
        playersWP.add(12);
        Match match = new Match(1,players,playersWP);
        DraftPool dp = new DraftPool(new RoundTrack());
        Die myDie = new Die(0,Color.WHITE);
        dp.throwsDice(3);
        match.setDraftPool(dp);
        dp.setDie(0,myDie);
        ToolCard t7 = new ToolCard(7);
        ArrayList<ToolCard> tools = new ArrayList<>();
        tools.add(t7);
        match.setTools(tools);


        PlayerToolParameter pt = new PlayerToolParameter();
        ArrayList<String> coordinates = new ArrayList<>();
        coordinates.add("1");
        coordinates.add("2");
        pt.setListOfCoordinateY(coordinates);
        match.playerUseTool(7,null);
        //Verifico che i dadi sono cambiati e quelli invece già usati no
        assertTrue(match.playerUseTool(7,pt));
        assertTrue(dp.getDie(0).getColor().equals(Color.WHITE));
        assertTrue(dp.getDie(0).getNumber() == 0);

    }

    @Test
    public void testTool8(){
        ArrayList<String> players = new ArrayList<>();
        players.add("Diego");
        ArrayList<Integer> playersWP = new ArrayList<>();
        playersWP.add(12);
        Match match = new Match(1,players,playersWP);
        match.getCurrentPlayer().setInsertedDie(true);
        ToolCard t8 = new ToolCard(8);
        ArrayList<ToolCard> tools = new ArrayList<>();
        tools.add(t8);
        match.setTools(tools);


        //Controllo i valori iniziali
        assertTrue(match.getCurrentPlayer().getInsertedDie());
        assertFalse(t8.isAlreadyUsed());

        match.playerUseTool(8,null);
        //Verifico che posso inserire un altro dado
        assertFalse(match.getCurrentPlayer().getInsertedDie());
        assertTrue(t8.isAlreadyUsed());

    }

    @Test
    public void testTool9(){
        ArrayList<String> players = new ArrayList<>();
        players.add("Diego");
        ArrayList<Integer> playersWP = new ArrayList<>();
        playersWP.add(12);
        Match match = new Match(1,players,playersWP);
        WindowPattern window = match.getCurrentPlayer().getWindowPattern();
        Coordinate d1 = new Coordinate(0,0);
        Coordinate d2 = new Coordinate(2,1);
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
        DraftPool dp = new DraftPool(new RoundTrack());
        Die myDie = new Die(3,Color.RED);
        Coordinate x1 = new Coordinate(0,0);
        Die myDie2 = new Die(1,Color.BLUE);
        Coordinate x2 = new Coordinate(0,1);
        dp.throwsDice(3);
        match.setDraftPool(dp);
        dp.setDie(0,myDie);
        dp.setDie(1,myDie2);
        ToolCard t9 = new ToolCard(9);
        ArrayList<ToolCard> tools = new ArrayList<>();
        tools.add(t9);
        match.setTools(tools);


        window.addDie(d1,die,window.getCellMatrix());
        //Testo il fatto che normalmente non si può mettere il dado
        assertTrue(window.getCellMatrix()[d1.getX()][d1.getY()].isEmpty());

        PlayerToolParameter pt = new PlayerToolParameter();
        pt.setC1(x1);
        pt.setD1(d1);
        match.playerUseTool(9,null);
        match.playerUseTool(9,pt);
        //Verifico la funzionalità della ToolCard per colore
        assertFalse(window.getCellMatrix()[d1.getX()][d1.getY()].isEmpty());

        pt.setC1(x2);
        pt.setD1(d2);
        match.playerUseTool(9,pt);
        //Verifico la funzionalità della ToolCard per numero
        assertFalse(window.getCellMatrix()[d2.getX()][d2.getY()].isEmpty());

        Coordinate wrongD = new Coordinate(1,0);
        pt.setD1(wrongD);
        match.playerUseTool(9,pt);
        //Verifico che le restrizioni rimangano verificate
        assertTrue(window.getCellMatrix()[wrongD.getX()][wrongD.getY()].isEmpty());

    }

    @Test
    public void testTool10(){
        ArrayList<String> players = new ArrayList<>();
        players.add("Diego");
        ArrayList<Integer> playersWP = new ArrayList<>();
        playersWP.add(12);
        Match match = new Match(1,players,playersWP);
        Die die = new Die(4,Color.BLUE);
        Die die2 = new Die(1,Color.VIOLET);
        Die die3 = new Die(-1,Color.GREEN);
        DraftPool dp = new DraftPool(new RoundTrack());
        Coordinate x1 = new Coordinate(0,0);
        Coordinate x2 = new Coordinate(0,1);
        Coordinate x3 = new Coordinate(0,2);
        dp.throwsDice(3);
        match.setDraftPool(dp);
        dp.setDie(0,die);
        dp.setDie(1,die2);
        dp.setDie(2,die3);
        ToolCard t10 = new ToolCard(10);
        ArrayList<ToolCard> tools = new ArrayList<>();
        tools.add(t10);
        match.setTools(tools);


        PlayerToolParameter pt = new PlayerToolParameter();
        pt.setC1(x1);
        match.playerUseTool(10,null);
        match.playerUseTool(10,pt);
        assertEquals(3,die.getNumber());

        pt.setC1(x2);
        match.playerUseTool(10,pt);
        assertEquals(6,die2.getNumber());

        pt.setC1(x3);
        assertFalse( match.playerUseTool(10,pt));

    }

    @Test
    public void testTool11(){
        ArrayList<String> players = new ArrayList<>();
        players.add("Diego");
        ArrayList<Integer> playersWP = new ArrayList<>();
        playersWP.add(24);
        Match match = new Match(1,players,playersWP);
        WindowPattern window = match.getCurrentPlayer().getWindowPattern();
        Die dieModified = new Die(2,Color.VIOLET);
        Die die = new Die(3,Color.BLUE);
        Coordinate coordinate = new Coordinate(0,0);
        DraftPool dp = new DraftPool(new RoundTrack());
        dp.throwsDice(3);
        match.setDraftPool(dp);
        dp.setDie(0,dieModified);
        window.addDie(new Coordinate(3,0),die,window.getCellMatrix());
        ToolCard t11 = new ToolCard(11);
        ArrayList<ToolCard> tools = new ArrayList<>();
        tools.add(t11);
        match.setTools(tools);


        PlayerToolParameter pt = new PlayerToolParameter();
        pt.setC1(coordinate);
        pt.setPhase(0);
        match.playerUseTool(11,null);
        //Verifico che cambia il numero (potrebbe dare lo stesso valore, per quello verifico così)
        assertTrue(match.playerUseTool(11,pt));

        pt.setDieModified(5);
        pt.setPhase(1);
        match.playerUseTool(11,pt);
        //Verifico che davvero ha cambiato numero
        assertEquals(5,dp.getDie(0).getNumber());

        Coordinate c = new Coordinate(2,1);
        pt.setC1(c);
        pt.setPhase(2);
        match.playerUseTool(11,pt);
        //Verifico che sia inseribile
        assertFalse(window.getCellMatrix()[c.getX()][c.getY()].isEmpty());


    }

    @Test
    public void testTool12(){
        ArrayList<String> players = new ArrayList<>();
        players.add("Diego");
        ArrayList<Integer> playersWP = new ArrayList<>();
        playersWP.add(13);
        Match match = new Match(1,players,playersWP);
        WindowPattern window = match.getCurrentPlayer().getWindowPattern();
        RoundTrack rt = new RoundTrack();
        rt.setRound(2);
        Die rtDie = new Die(3,Color.VIOLET);
        Die wrongRtDie = new Die(3,Color.GREEN);
        rt.addDie(rtDie,1);
        rt.addDie(wrongRtDie,1);
        match.setRoundTrack(rt);
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
        ToolCard t12 = new ToolCard(12);
        ArrayList<ToolCard> tools = new ArrayList<>();
        tools.add(t12);
        match.setTools(tools);


        PlayerToolParameter pt = new PlayerToolParameter();
        pt.setC1(c1);
        pt.setRound(1);
        pt.setPhase(0);
        match.playerUseTool(12,null);
        match.playerUseTool(12,pt);

        pt.setC1(c1);
        pt.setD1(d1);
        pt.setPhase(1);
        match.playerUseTool(12,pt);

        pt.setC1(c4);
        pt.setD1(d2);
        pt.setPhase(2);
        match.playerUseTool(12,pt);

        //Verifico il corretto funzionamento di entrambi gli spostamenti
        assertFalse(window.getCellMatrix()[d1.getX()][d1.getY()].isEmpty());
        assertFalse(window.getCellMatrix()[d2.getX()][d2.getY()].isEmpty());
        assertTrue(window.getCellMatrix()[c1.getX()][c1.getY()].isEmpty());
        assertTrue(window.getCellMatrix()[c4.getX()][c4.getY()].isEmpty());

        Coordinate c5 = new Coordinate(0,3);
        pt.setC1(c1);
        pt.setRound(1);
        pt.setPhase(0);
        match.playerUseTool(12,pt);

        pt.setC1(d2);
        pt.setD1(c1);
        pt.setPhase(1);
        match.playerUseTool(12,pt);

        pt.setC1(d1);
        pt.setD1(c5);
        pt.setPhase(2);
        match.playerUseTool(12,pt);

        //Nonostante mi aspetti di non spostare il secondo dado, in realtà sposto solo quello perchè il primo non rispetta le restrizioni
        assertFalse(window.getCellMatrix()[d2.getX()][d2.getY()].isEmpty());
        assertTrue(window.getCellMatrix()[d1.getX()][d1.getY()].isEmpty());
        assertTrue(window.getCellMatrix()[c1.getX()][c1.getY()].isEmpty());
        assertFalse(window.getCellMatrix()[c5.getX()][c5.getY()].isEmpty());

        pt.setC1(c2);
        pt.setRound(1);
        pt.setPhase(0);
        match.playerUseTool(12,pt);

        pt.setC1(c5);
        pt.setD1(c1);
        pt.setPhase(1);
        match.playerUseTool(12,pt);

        //Non è cambiato nulla perchè il colore è sbagliato, anche se restizioni corrette
        assertTrue(window.getCellMatrix()[c1.getX()][c1.getY()].isEmpty());
        assertFalse(window.getCellMatrix()[c5.getX()][c5.getY()].isEmpty());
    }
}