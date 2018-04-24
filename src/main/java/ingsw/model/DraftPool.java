package ingsw.model;

import java.util.ArrayList;

public class DraftPool {        //Classe che rappresenta la draft pool del gioco, tramite diceBag lancia i dadi e mette
                                //quelli in eccesso nella RoundTrack.

    private ArrayList<Die> diceList;
    private DiceBag diceBag;
    private RoundTrack roundTrack;

    public  DraftPool(RoundTrack roundTrack){
        diceList = new ArrayList<Die>();
        diceBag = new DiceBag();
        this.roundTrack = roundTrack;
    }

    public void throwsDice(int nDice){      //mette i dadi in avanzo relativi al turno precedente nella round track,
        if(!diceList.isEmpty()){            //lancia nDice nuovi dadi.
            for(int i=0; i<diceList.size(); i++){
                roundTrack.addDie(diceList.get(i), roundTrack.getRound()-1);    //quando viene chiamata il currentRound è già stato aggiornato
            }
        }

        for(int i=0; i<nDice; i++){
            diceList.add(diceBag.randomDice());
        }
    }

    public Die getDie(int index){       //restituisce il riferimento al dado in posizione index
        return diceList.get(index);
    }

    public Die takeDie(int index){      //fa get(index) e rimuove il dado dalla lista
        Die result = diceList.get(index);
        diceList.remove(index);
        return result;
    }
}
