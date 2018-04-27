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
        roundTrack.nextRound();
        clockwiseRound = true;
        for(Player p:players)
            p.setMyRound(1);
    }

    //NB nextTurn va chiamata 7 volte con 4 giocatori, al termine si chiama endRound (tengo conto se è il primo o secondo turno)
    public void nextTurn(){                         //il turno va al prossimo giocatore
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
                System.out.println(e.getMessage());
            }
        }
    }

    //riceve indice del dado nella draft pool che si vuole posizionare e destinazione sulla WP del currentPlayer
    public void playerMoveDie(int dieIndex, Coordinate destination){
        currentPlayer.positionDie(draftPool.takeDie(dieIndex), destination);
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
