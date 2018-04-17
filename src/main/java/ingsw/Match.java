package ingsw;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;


public class Match {
    private int id;
    private int currentRound;
    private Player[] players;
    private int nPlayers;
    private ArrayList<ToolCard> tools;       //3 tools che contengono effettivamente tutte le informazioni

    private ArrayList<PBObjectiveCard> pbCard;   //3 PB scelte randomicamente
    private Set<Integer> alreadyUsed;
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

        tools = new ArrayList<ToolCard>();
        ArrayList<Integer> toolvalues = new ArrayList<>();
        for(int i=1; i<=12; i++){
            toolvalues.add(i);
        }
        for(int n=12; n>9; n--){ tools.add(new ToolCard(randomChoose(n, toolvalues))); }


        pbCard = new ArrayList<PBObjectiveCard>();
        ArrayList<Integer> pbvalues = new ArrayList<>();
        for(int i=1; i<=10; i++){
            pbvalues.add(i);
        }
        for(int n=10; n>7; n--){ pbCard.add(new PBObjectiveCard(randomChoose(n, pbvalues))); }






        wp = new ArrayList<WindowPFactory>();
        for(Player p: players){
            for (int idx = 1; idx <= 4; ++idx){
                if(!players.wp.contains(randomChooseWP()))
                    p.wp.add(idx, randomChooseWP());
                else idx--;
            }
        }

        for(Player p: players){
            do {
                players.pvCard = randomChoosePV();
                alreadyUsed.add(randomChoosePV());
            }while(alreadyUsed.contains(randomChoosePV());
        }

        roundtrack= new RoundTrack();



    }

    int getId(){ return id; }

    int getNumOfPlayers(){ return nPlayers; }



    int randomChoose(int n, ArrayList<Integer> values){
        int result;
        /*ArrayList<Integer> values = new ArrayList<>();
        for(int i=1; i<=12; i++){
            values.add(i);
        }*/

        RandomGenerator rg = new RandomGenerator(values);
        result = rg.random(n);
        return result;


    }

    int randomChoosePB(int n){
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
