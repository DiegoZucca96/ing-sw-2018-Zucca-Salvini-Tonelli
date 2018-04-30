package ingsw.model;

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

    public Match(int id, ArrayList<String> playersNames, ArrayList<String> playersWP){    //viene passato l'id dal Server per identificare il match
        this.id=id;

        /*for(int i=0; i < server.listOfClient.size(); i++){
            if(server.listOfClient[i].client.playingAt() = id){
                players.add(server.listOfClient[i].client);
            }
        }*/

        //Inizializzazione dei giocatori sulla base dei dati ricevuti dal costruttore
        players= new ArrayList<Player>();
        for(int i=0; i<playersNames.size(); i++){
            players.add(new Player(playersNames.get(i), playersWP.get(i), Color.values()[new RandomGenerator(5).random()]));
        }
        nPlayers=players.size();
        clockwiseRound = true;
        currentPlayer = players.get(0);

        // Inizializzazione delle tre carte utensili sfruttando tre numeri diversi generati casualmente
        tools = new ArrayList<ToolCard>();
        RandomGenerator rg = new RandomGenerator(12);

        tools.add(new ToolCard(rg.random()));
        tools.add(new ToolCard(rg.random()));
        tools.add(new ToolCard(rg.random()));

        //Inizializzazione delle public objective card
        pbCards = new ArrayList<PBObjectiveCard>();
        rg = new RandomGenerator(10);

        pbCards.add(new PBObjectiveCard(rg.random()));
        pbCards.add(new PBObjectiveCard(rg.random()));
        pbCards.add(new PBObjectiveCard(rg.random()));

        //Inizializzazione di round track e draft pool
        roundTrack = new RoundTrack();
        draftPool = new DraftPool(roundTrack);
    }

    public int getId(){
        return id;
    }

    public int getNumOfPlayers(){
        return nPlayers;
    }

    //inizia il round (lancia i dadi), con turno già sul primo giocatore
    public void startRound(){
        draftPool.throwsDice(nPlayers*2+1);
    }

    //termina il round e setta a tutti i giocatori il loro primo turno
    public void endRound(){
        draftPool.cleanDraftPool();
        roundTrack.nextRound();
        clockwiseRound = true;
        for(Player p:players)
            p.setMyRound(1);
    }

    //NB nextTurn va chiamata 7 volte con 4 giocatori, al termine si chiama endRound (tengo conto se è il primo o secondo turno tramite clockwiseRound)

    //il turno va al prossimo giocatore
    public void nextTurn(){
        if(currentPlayer == players.get(players.size()) && clockwiseRound){
            currentPlayer.setMyRound(2);
            clockwiseRound = false;
        }else {
            try{
                if (clockwiseRound){
                    currentPlayer.setMyRound(2);
                    currentPlayer = players.get(players.indexOf(currentPlayer) + 1);
                }else{
                    currentPlayer.setMyRound(2);
                    currentPlayer = players.get(players.indexOf(currentPlayer) - 1);
                }
            } catch (IndexOutOfBoundsException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    //riceve indice del dado nella draft pool che si vuole posizionare e destinazione sulla WP del currentPlayer
    public void playerMoveDie(int dieIndex, Coordinate destination){
        currentPlayer.positionDie(draftPool.takeDie(dieIndex), destination);
    }

    //raccoglie i dati necessari per l'utilizzo di una tool card in un oggetto di tipo ObjectiveTool
    //se non è possibile utilizzare la carta indicata in pTParameter restituisce null
    public ObjectiveTool createToolParameter(PlayerToolParameter pTParameter){
        int idCard = pTParameter.getIdCard();
        ObjectiveTool toolParameter;
        boolean allow = true;  //Serve a dare il consenso all'uso della ToolCard o meno
        switch(idCard){
            case 1:{
                toolParameter = new ObjectiveTool(pTParameter.getDie1(), pTParameter.getUp(), draftPool);
                break;
            }
            case 2:{
                toolParameter = new ObjectiveTool(currentPlayer.getWindowPattern(), pTParameter.getC1(), pTParameter.getD1());
                break;
            }
            case 3:{
                toolParameter = new ObjectiveTool(currentPlayer.getWindowPattern(), pTParameter.getC1(), pTParameter.getD1());
                break;
            }
            case 4:{
                toolParameter = new ObjectiveTool(currentPlayer.getWindowPattern(), pTParameter.getC1(), pTParameter.getC2(), pTParameter.getD1(), pTParameter.getD2());
                break;
            }
            case 5:{
                toolParameter = new ObjectiveTool(pTParameter.getDie1(), pTParameter.getDie2(), roundTrack, draftPool);
                break;
            }
            case 6:{
                toolParameter = new ObjectiveTool(pTParameter.getDie1(), currentPlayer.getWindowPattern(), draftPool);
                break;
            }
            case 7:{
                if(!clockwiseRound)
                    toolParameter = new ObjectiveTool(draftPool, draftPool.getDiceBag());
                else{
                    allow = false;
                    toolParameter = null;
                }
                break;
            }
            case 8:{
                toolParameter = new ObjectiveTool(currentPlayer.getWindowPattern(), pTParameter.getD1(), draftPool);
                break;
            }
            case 9:{
                toolParameter = new ObjectiveTool(pTParameter.getDie1(), currentPlayer.getWindowPattern(), pTParameter.getD1());
                break;
            }
            case 10:{
                toolParameter = new ObjectiveTool(pTParameter.getDie1(), draftPool, pTParameter.getTurnDie());
                break;
            }
            case 11:{
                toolParameter = new ObjectiveTool(pTParameter.getDie1(), currentPlayer.getWindowPattern(), draftPool.getDiceBag());
                break;
            }
            case 12:{
                toolParameter = new ObjectiveTool(currentPlayer.getWindowPattern(), pTParameter.getC1(), pTParameter.getC2(), pTParameter.getD1(), pTParameter.getD2(), roundTrack);
                break;
            }
            default: return null;
        }
        if(allow) {
            return toolParameter;
        } else {
            //System.out.print("Non puoi usare questa carta");
            return null;
        }
    }



    //usa la ingsw.model.toolcard passata come parametro
    public void playerUseTool(ToolCard tool){
        currentPlayer.useToolCard(tool);
    }

    //calcola e setta il punteggio a tutti i giocatori
    public void playersScore(){
        for(Player p : players){
            pbCards.get(0).doPBStrategy(p);
            pbCards.get(1).doPBStrategy(p);
            pbCards.get(2).doPBStrategy(p);
            p.pvScore();
        }
    }

    //trova il vincitore, non prevede ancora i pareggi
    public Player findWinner(){
        Player winner = players.get(0);
        for(Player p: players){
            if(p.getScore()>winner.getScore()) winner = p;
        }
        return winner;
    }

}
