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

    //mette i dadi in avanzo relativi al turno precedente nella round track, lancia nDice nuovi dadi.
    public void throwsDice(int nDice){
        for(int i=0; i<nDice; i++){
            diceList.add(diceBag.randomDice());
        }
    }

    //restituisce il riferimento al dado in posizione index
    public Die getDie(int index){
        return diceList.get(index);
    }

    //fa get(index) e rimuove il dado dalla draft pool
    public Die takeDie(int index){
        Die result = diceList.get(index);
        diceList.remove(index);
        return result;
    }

    //aggiunge un dado nella draft pool
    public void addDie(Die die){
        diceList.add(die);
    }

    //NB -----> questo metodo espone il rep della classe, non possiamo tenerlo, è un errore grave
    public ArrayList<Die> getDiceList() {
        return diceList;
    }

    //mette i dadi che non sono stati usati nella round track
    public void cleanDraftPool(){
        if(!diceList.isEmpty()){
            for(int i=0; i<diceList.size(); i++){
                roundTrack.addDie(diceList.get(i), roundTrack.getRound());
            }
        }
    }
}
