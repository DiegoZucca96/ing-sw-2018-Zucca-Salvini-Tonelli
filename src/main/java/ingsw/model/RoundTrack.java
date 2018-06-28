package ingsw.model;

import ingsw.observers.Observer;
import ingsw.observers.RoundTrackObserver;
import java.util.ArrayList;


/**
 * Class that represents the round track of the game. It contains the dice in excess at the end of each round,
 * it memorizes the current round
 */
public class RoundTrack {

    private int currentRound;
    private ArrayList<ArrayList<Die>> extraDice;    //dice in excess at the end of ech round
    private Observer viewObserver;

    public RoundTrack() {
        currentRound = 1;
        extraDice = new ArrayList<>();
        viewObserver = new RoundTrackObserver();
    }

    //simple getter
    public int getRound() {
        return currentRound;
    }

    /**
     * setter that verifies the value of the new round is between 1 and 10
     * @param currentRound
     */
    public void setRound(int currentRound) {
        if(currentRound >= 1 && currentRound <=10) this.currentRound = currentRound;
    }

    /**
     * This method increases of one the value of the current round until it reaches 10
     */
    public void nextRound(){
        if(currentRound <10)
            currentRound++;
    }

    /**
     * It adds a new die in the round track
     * @param die   die added in the round track
     * @param round round in which you want to place the die
     */
    public void addDie(Die die, int round){
        if(round>=1 && round<=10){
            for(int i=extraDice.size(); i<round; i++){
                extraDice.add(new ArrayList<Die>());
            }
            extraDice.get(round-1).add(die);
            notifyViewObserver();
        }
    }

    /**
     * getter
     * @param round round of the requested die
     * @param index position (sequential number) of the requested die in the round specified
     * @return die that belong to the round requested. in position index
     */
    public Die getDie(int round, int index){
        if(round<1 || round>10) return null;
        try{
            return extraDice.get(round-1).get(index);
        } catch(IndexOutOfBoundsException e){
            return null;
        }
    }

    /**
     * ???
     * @param round
     * @param index
     * @param dpDie
     * @return
     */
    //esegue getDie e rimuove il riferiemento al dado dalla lista.
    public Die replaceDie(int round, int index, Die dpDie){
        Die result = getDie(round, index);
        if(result == null) return null;
        extraDice.get(round-1).set(index,dpDie);
        notifyViewObserver();
        return result;
    }

    /**
     * This method notifies changes in this object to viewObserver
     */
    public void notifyViewObserver(){
        viewObserver.update(this, ViewData.instance());
    }

    /**
     * This method creates a representation of this class
     * @return  list of strings that represents this object
     */
    public ArrayList<String> toArrayString() {
        ArrayList<String> newRoundTrack = new ArrayList<>();
        for(int i=0; i<10; i++){
            for(int j=0; j<9; j++) {
                Die die = getDie(i + 1, j);
                if (die != null){
                    String path = Integer.toString(i+1).concat(die.toString());
                    newRoundTrack.add(path);
                }
            }
        }
        return newRoundTrack;
    }
}

