package ingsw;

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
        extraDice = new ArrayList<>();
    }

    public int getRound() {                             //restituisce il round corrente
        return currentRound;
    }

    public void setRound(int currentRound) {            //imposta il round corrente (non impedisce di impostare valori <0 o >10)
        this.currentRound = currentRound;
    }

    public void nextRound(){                            //incrementa il round corrente di uno, fino a raggiungere il valore massimo, 10
        if(currentRound <10) currentRound++;
    }

    public void addDie(Die die, int round){             //inserisce il dado nella lista all'indice round.
        for(int i=extraDice.size(); i<round; i++){
            extraDice.add(new ArrayList<>());
        }
        extraDice.get(round).add(die);
    }

    public Die getDie(int round, int index){            //restituisce il riferimento al dado nella posizione index della lista nella
        return extraDice.get(round).get(index);         //poszione round
    }

    public Die takeDie(int round, int index){           //esegue getDie e rimuove il riferiemento al dado dalla lista.
        Die result = getDie(round, index);
        extraDice.get(round).remove(index);
        return result;
    }

}

