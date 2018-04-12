package ingsw;

import java.util.ArrayList;
import java.util.Random;


public class Match {
    private int id;
    private int currentRound;
    private Player[] players;
    private int nPlayers;
    private ArrayList<Integer> tools;       //3 tools che contengono effettivamente tutte le informazioni
    //private int[] idTools;   //12 tool card che contengono il riferimento all'asset e nient altro
    private ArrayList<Integer> pbCard;   //3 PB scelte randomicamente
    //private int[] idPb; //10 pb card che contengono il riferimento
    private ArrayList<Integer> wp;
    private RoundTrack roundtrack;  

    //costruttore
    public Match(int id){    //viene passato il codice dal Server per tenere traccia di quale partita stiamo parlando
        this.id=id;
        this.currentRound=0;

        ArrayList<Player> players= new ArrayList<Player>();
        for(int i=0; i < server.listOfClient.size(); i++){
            if(server.listOfClient[i].client.playingAt() = id){
                players.add(server.listOfClient[i].client);
            }
        }
        nPlayers=players.size();

        tools = new ArrayList<Integer>();
        for (int idx = 1; idx <= 3; ++idx){
            if(!tools.contains(randomChooseTool()))
                tools.add(idx, randomChooseTool());
            else idx--;
        }

        pbCard = new ArrayList<Integer>();
        for (int idx = 1; idx <= 3; ++idx){
            if(!pbCard.contains(randomChoosePB()))
                pbCard.add(idx, randomChoosePB());
            else idx--;
        }

        wp = new ArrayList<Integer>();
        for(Player p: players){
            for (int idx = 1; idx <= 4; ++idx){
                if(!players.wp.contains(randomChooseWP()))
                    p.wp.add(idx, randomChooseWP());
                else idx--;
            }
        }

        roundtrack= new RoundTrack();



    }

    int getCode(){ return id; }

    int getNumOfPlayers(){ return nPlayers; }



    int randomChooseTool(){
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(12);
        return randomInt;
    }

    int randomChoosePB(){
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(10);
        return randomInt;
    }

    int randomChoosePV(){
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(5);
        return randomInt;
    }

    int randomChooseWP(){
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(24);
        return randomInt;
    }
    void updateRound(){
        if (){      //se siamo all'inizio o alla fine dell'array players devo aggiornare il round
            currentRound++;
        }
    }


}
