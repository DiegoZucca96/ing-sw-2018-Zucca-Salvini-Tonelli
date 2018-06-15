package ingsw.model;

import ingsw.observers.Observer;
import ingsw.observers.RoundTrackObserver;
import java.util.ArrayList;

//Classe che rappresenta la round track del gioco, contiene tutti i dadi che avanzano nella draft pool alla fine di ogni round,
//memorizza il round corrente.

public class RoundTrack {

    private int currentRound;
    private ArrayList<ArrayList<Die>> extraDice;
    private Observer viewObserver;

    public RoundTrack() {
        currentRound = 1;
        extraDice = new ArrayList<>();
        viewObserver = new RoundTrackObserver();
    }

    //restituisce il round corrente
    public int getRound() {
        return currentRound;
    }

    //imposta il round corrente
    public void setRound(int currentRound) {
        if(currentRound >= 1 && currentRound <=10) this.currentRound = currentRound;
    }

    //incrementa il round corrente di uno, fino a raggiungere il valore massimo, 10
    public void nextRound(){
        if(currentRound <10)
            currentRound++;
    }

    //inserisce il dado nella lista all'indice round.
    public void addDie(Die die, int round){
        if(round>=1 && round<=10){
            for(int i=extraDice.size(); i<round; i++){
                extraDice.add(new ArrayList<Die>());
            }
            extraDice.get(round-1).add(die);
            notifyViewObserver();
        }
    }

    //restituisce il riferimento al dado nella posizione index della lista nella poszione round
    public Die getDie(int round, int index){
        if(round<1 || round>10) return null;
        try{
            return extraDice.get(round-1).get(index);
        } catch(IndexOutOfBoundsException e){
            return null;
        }
    }

    //esegue getDie e rimuove il riferiemento al dado dalla lista.
    public Die replaceDie(int round, int index, Die dpDie){
        Die result = getDie(round, index);
        if(result == null) return null;
        extraDice.get(round-1).set(index,dpDie);
        notifyViewObserver();
        return result;
    }

    public void notifyViewObserver(){
        viewObserver.update(this, ViewData.instance());
    }

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

