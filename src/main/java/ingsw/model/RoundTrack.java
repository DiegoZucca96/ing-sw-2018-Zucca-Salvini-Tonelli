package ingsw.model;

import java.util.ArrayList;

//Classe che rappresenta la round track del gioco, contiene tutti i dadi che avanzano nella draft pool alla fine di ogni round,
//memorizza il round corrente.

public class RoundTrack {

    private int currentRound;                           //round corrente
    private ArrayList<ArrayList<Die>> extraDice;        /*lista di liste di tipo Die che contiene i dadi della round track.
                                                          All'iesima posizione della prima lista troviamo la lista di dadi
                                                          avanzati all'iesimo round.*/

    public RoundTrack() {
        currentRound = 1;
        extraDice = new ArrayList<ArrayList<Die>>();
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
        if(currentRound <10) currentRound++;
    }

    //inserisce il dado nella lista all'indice round.
    public void addDie(Die die, int round){
        if(round>=1 && round<=10){
            for(int i=extraDice.size(); i<round; i++){
                extraDice.add(new ArrayList<Die>());
            }
            extraDice.get(round-1).add(die);
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
    public Die takeDie(int round, int index){
        Die result = getDie(round, index);
        if(result == null) return null;
        extraDice.get(round-1).remove(index);
        return result;
    }

}

