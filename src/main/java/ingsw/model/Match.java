package ingsw.model;

import ingsw.observers.*;
import java.util.ArrayList;

public class Match {
    private int id;
    private int nPlayers;
    private Player currentPlayer;
    private boolean clockwiseRound;              //verso del round, se true "orario"
    private ArrayList<Player> players;
    private ArrayList<ToolCard> tools;           //ingsw.model.toolcard relative alla partita
    private ArrayList<PBObjectiveCard> pbCards;  //public objective card relative alla partita
    private RoundTrack roundTrack;
    private DraftPool draftPool;
    private ViewData init;
    private Observer viewObserver = null;

    public Match(int id, ArrayList<String> playersNames, ArrayList<Integer> playersWP) {    //viene passato l'id dal Server per identificare il match
        this.id = id;

        //Inizializzazione dei giocatori sulla base dei dati ricevuti dal costruttore
        players = new ArrayList<>();
        RandomGenerator rg = new RandomGenerator(4);
        for (int i = 0; i < playersNames.size(); i++) {
            Player player = new Player(playersNames.get(i), playersWP.get(i), Color.values()[rg.random()]);
            players.add(player);
        }
        nPlayers = players.size();
        clockwiseRound = true;
        currentPlayer = players.get(0);

        //Inizializzazione delle componenti del gioco lato model e grafico
        tools = new ArrayList<>();
        pbCards = new ArrayList<>();
        roundTrack = new RoundTrack();
        draftPool = new DraftPool(roundTrack);
        init = ViewData.instance();

        ArrayList<Integer> numToolCards = ToolCard.generateToolCard(init);
        tools.add(new ToolCard(numToolCards.get(0)));
        tools.add(new ToolCard(numToolCards.get(1)));
        tools.add(new ToolCard(numToolCards.get(2)));

        ArrayList<Integer> numPBCards = PBObjectiveCard.generatePBCard(init);
        pbCards.add(new PBObjectiveCard(numPBCards.get(0)));
        pbCards.add(new PBObjectiveCard(numPBCards.get(1)));
        pbCards.add(new PBObjectiveCard(numPBCards.get(2)));

        ArrayList<Die> dicelist = startRound();
        for(Die die : dicelist)
            init.getDraftPoolDice().add(die.toString());
    }

    //Metodo che istanzia il gioco lato model e restituisce a lato view
    public ViewData getInit(){
        return init;
    }

    public boolean getClockwiseRound() {
        return clockwiseRound;
    }

    public int getNumOfPlayers() {
        return nPlayers;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    //NB --> metodo ad uso esclusivo dei test
    public void setDraftPool(DraftPool draftPool) {
        this.draftPool = draftPool;
    }

    //NB --> metodo ad uso esclusivo dei test
    public void setRoundTrack(RoundTrack roundTrack) {
        this.roundTrack = roundTrack;
    }

    //NB --> metodo ad uso esclusivo dei test
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    //NB --> metodo ad uso esclusivo dei test
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    //NB --> metodo ad uso esclusivo dei test
    public void setPbCards(ArrayList<PBObjectiveCard> pbCards) {
        this.pbCards = pbCards;
    }

    //NB --> metodo ad uso esclusivo dei test
    public void setTools(ArrayList<ToolCard> tools) {
        this.tools = tools;
    }

    //inizia il round (lancia i dadi), con turno già sul primo giocatore
    public ArrayList<Die> startRound() {
        return draftPool.throwsDice(nPlayers * 2 + 1);
    }

    //termina il round e setta a tutti i giocatori il loro primo turno
    public void endRound() {
        draftPool.cleanDraftPool();
        roundTrack.nextRound();
        clockwiseRound = true;
        //il primo giocatore viene messo in fondo alla lista in quanto diventa l'ultimo
        players.remove(currentPlayer);
        players.add(currentPlayer);
        currentPlayer = players.get(0);
        draftPool.throwsDice(getNumOfPlayers()*2 + 1);
    }

    //NB nextTurn va chiamata 7 volte con 4 giocatori, al termine si chiama endRound (tengo conto se è il primo o secondo turno tramite clockwiseRound)
    //il turno va al prossimo giocatore
    public void nextTurn() {
        if (currentPlayer == players.get(players.size()-1) && clockwiseRound) {
            clockwiseRound = false;
        } else {
            try {
                if (clockwiseRound) {
                    currentPlayer = players.get(players.indexOf(currentPlayer) + 1);
                } else {
                    currentPlayer = players.get(players.indexOf(currentPlayer) - 1);
                }
            } catch (IndexOutOfBoundsException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    //riceve indice del dado nella draft pool che si vuole posizionare e destinazione sulla WP del currentPlayer
    public Die playerTakeDie(int dieIndex) {
        return draftPool.getDie(dieIndex);
    }

    //Mette un dado fittizio per mantenere la dimensione della draftpool (non viene mai più preso)
    public Die draftpoolRemoveDie(int dieIndex) {
        return draftPool.takeDie(dieIndex);
    }

    //posiziona un dado nella wp del current player
    public boolean positionDie(Die die, Coordinate destination){
        return currentPlayer.positionDie(die, destination);
    }

    //raccoglie i dati necessari per l'utilizzo di una tool card in un oggetto di tipo ObjectiveTool
    //se non è possibile utilizzare la carta indicata in pTParameter restituisce null
    private ObjectiveTool createToolParameter(int idCard, PlayerToolParameter pTParameter) {
        ObjectiveTool toolParameter = new ObjectiveTool();
        switch (idCard) {
            case 1: {
                toolParameter.setC1(pTParameter.getC1());
                toolParameter.setDieModified(pTParameter.getDieModified());
                toolParameter.setDp(draftPool);
                break;
            }
            case 2: {
                toolParameter.setWindow(currentPlayer.getWindowPattern());
                toolParameter.setC1(pTParameter.getC1());
                toolParameter.setD1(pTParameter.getD1());
                break;
            }
            case 3: {
                toolParameter.setWindow(currentPlayer.getWindowPattern());
                toolParameter.setC1(pTParameter.getC1());
                toolParameter.setD1(pTParameter.getD1());
                break;
            }
            case 4:{
                if(pTParameter.getPhase()==0){
                    toolParameter.setWindow(currentPlayer.getWindowPattern());
                    toolParameter.setC1(pTParameter.getC1());
                    toolParameter.setD1(pTParameter.getD1());
                    toolParameter.setPhase(pTParameter.getPhase());
                }
                else{
                    toolParameter.setWindow(currentPlayer.getWindowPattern());
                    toolParameter.setC2(pTParameter.getC2());
                    toolParameter.setD2(pTParameter.getD2());
                    toolParameter.setPhase(pTParameter.getPhase());
                }
                break;
            }
            case 5: {
                toolParameter.setC1(pTParameter.getC1());
                toolParameter.setD1(pTParameter.getD1());
                toolParameter.setRt(roundTrack);
                toolParameter.setDp(draftPool);
                toolParameter.setRound(pTParameter.getRound());
                break;
            }
            case 6: {
                toolParameter.setC1(pTParameter.getC1());
                toolParameter.setDp(draftPool);
                break;
            }
            case 7: {
                toolParameter.setListOfCoordinateY(pTParameter.getListOfCoordinateY());
                toolParameter.setDp(draftPool);
                break;
            }
            case 8: {
                toolParameter.setPlayer(currentPlayer);
                break;
            }
            case 9: {
                toolParameter.setWindow(currentPlayer.getWindowPattern());
                toolParameter.setC1(pTParameter.getC1());
                toolParameter.setD1(pTParameter.getD1());
                toolParameter.setDp(draftPool);
                break;
            }
            case 10: {
                toolParameter.setC1(pTParameter.getC1());
                toolParameter.setDp(draftPool);
                break;
            }
            case 11: {
                if(pTParameter.getPhase()==0){
                    toolParameter.setC1(pTParameter.getC1());
                    toolParameter.setWindow(currentPlayer.getWindowPattern());
                    toolParameter.setDp(draftPool);
                    toolParameter.setDiceBag(draftPool.getDiceBag());
                    toolParameter.setPhase(pTParameter.getPhase());
                }
                else if(pTParameter.getPhase() == 1){
                    toolParameter.setPhase(pTParameter.getPhase());
                    toolParameter.setDieModified(pTParameter.getDieModified());
                    toolParameter.setDp(draftPool);
                }
                else{
                    toolParameter.setC1(pTParameter.getC1());
                    toolParameter.setWindow(currentPlayer.getWindowPattern());
                    toolParameter.setDp(draftPool);
                    toolParameter.setPhase(pTParameter.getPhase());
                }
                break;
            }
            case 12: {
                if(pTParameter.getPhase() == 0){
                    toolParameter.setWindow(currentPlayer.getWindowPattern());
                    toolParameter.setRound(pTParameter.getRound());
                    toolParameter.setC1(pTParameter.getC1());
                    toolParameter.setRt(roundTrack);
                    toolParameter.setPhase(pTParameter.getPhase());
                }
                else if(pTParameter.getPhase()==1){
                    toolParameter.setWindow(currentPlayer.getWindowPattern());
                    toolParameter.setC1(pTParameter.getC1());
                    toolParameter.setD1(pTParameter.getD1());
                    toolParameter.setPhase(pTParameter.getPhase());
                }
                else{
                    toolParameter.setC1(pTParameter.getC1());
                    toolParameter.setD1(pTParameter.getD1());
                    toolParameter.setPhase(pTParameter.getPhase());
                    toolParameter.setWindow(currentPlayer.getWindowPattern());
                }
                break;
            }
            default:
                return null;
        }
        return toolParameter;
    }

    //usa la ingsw.model.toolcard passata come parametro, restituisce false se non può essere usata
    public boolean playerUseTool(int tool, PlayerToolParameter pTParameter) {
        if(pTParameter==null){
            for(ToolCard t : tools) {
                if (t.getIdCard() == tool) {
                    if (t.isAlreadyUsed()) {
                        if(currentPlayer.getTokens()>1) {
                            if(t.getIdCard()==8){
                                pTParameter = new PlayerToolParameter();
                                ObjectiveTool toolParameter = createToolParameter(tool,pTParameter);
                                t.doToolStrategy(toolParameter);
                            }
                            currentPlayer.useToken(t);
                            t.setNumTokenUsed(t.getNumTokenUsed() + 2);
                        }
                        else
                            return false;
                    }else{
                        if(t.getIdCard()==8){
                            pTParameter = new PlayerToolParameter();
                            ObjectiveTool toolParameter = createToolParameter(tool,pTParameter);
                            t.doToolStrategy(toolParameter);
                        }
                        currentPlayer.useToken(t);
                        t.setNumTokenUsed(t.getNumTokenUsed() + 1);
                    }
                }
            }
            notifyViewObserver();
            return true;
        }
        ObjectiveTool toolParameter = createToolParameter(tool,pTParameter);
        if (toolParameter == null) return false;
        for(ToolCard t : tools){
            if(t.getIdCard()==tool){
                boolean b = t.doToolStrategy(toolParameter);
                notifyViewObserver();
                return b;
            }
        }
        return false;
    }

    public void notifyViewObserver() {
        viewObserver = new DraftPoolObserver();
        viewObserver.update(draftPool, ViewData.instance());
        viewObserver = new WindowPatternObserver();
        viewObserver.update(currentPlayer.getWindowPattern(), ViewData.instance());
        viewObserver = new RoundTrackObserver();
        viewObserver.update(roundTrack, ViewData.instance());
        viewObserver = new ToolCardsObserver();
        for(ToolCard tool : tools)
            viewObserver.update(tool,ViewData.instance());
        viewObserver = null;
    }

    //calcola e setta il punteggio a tutti i giocatori
    public void playersScore() {
        for (Player p : players) {
            pbCards.get(0).doPBStrategy(p);
            pbCards.get(1).doPBStrategy(p);
            pbCards.get(2).doPBStrategy(p);
            p.personalScore();
        }
    }

    //trova il vincitore
    public Player findWinner() {
        ArrayList<Player> ties = new ArrayList<Player>();
        ArrayList<Player> tiesTmp = new ArrayList<Player>();
        Player winner = players.get(0);

        //ricerca giocatore con un punteggio più alto
        for (Player p : players) {
            if (p.getScore() > winner.getScore()) winner = p;
        }
        for (Player p : players) {
            if (p.getScore() == winner.getScore()) ties.add(p);
        }
        if (ties.size() == 1) return winner;

        //ricerca giocatore in parità con punteggio dovuto ad obiettivi privati più alto
        for (Player p : ties) {
            if (p.getPVScore() > winner.getPVScore()) winner = p;
        }
        for (Player p : ties) {
            if (p.getPVScore() == winner.getPVScore()) tiesTmp.add(p);
        }
        if (ties.size() == 1) return winner;

        //ricerca giocatore in parità con numero di token maggiore
        for (Player p: tiesTmp){
            if (p.getTokens() > winner.getTokens()) winner = p;
        }
        ties = new ArrayList<>();
        for (Player p : tiesTmp) {
            if (p.getTokens() == winner.getTokens()) ties.add(p);
        }
        if (ties.size() == 1) return winner;

        //ricerca del giocatore in parità con posizione minore
        for (Player p: ties){
            if(players.indexOf(p) < players.indexOf(winner)) winner = p;
        }
        return winner;
    }

    public int getRound() {
        return roundTrack.getRound();
    }
}
