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

    public DiceBag getDiceBag() {
        return diceBag;
    }

    //NM --> metodo ad uso esclusivo dei test
    public void setDiceBag(DiceBag diceBag) {
        this.diceBag = diceBag;
    }

    //mette i dadi in avanzo relativi al turno precedente nella round track, lancia nDice nuovi dadi.
    public void throwsDice(int nDice){
        for(int i=0; i<nDice; i++){
            diceList.add(diceBag.randomDice());
        }
    }

    //restituisce il riferimento al dado in posizione index
    public Die getDie(int index){
        try{
            return diceList.get(index);
        }catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    //fa get(index) e rimuove il dado dalla draft pool
    public Die takeDie(int index){
        Die result = getDie(index);
        try{
            diceList.remove(index);
        } catch(IndexOutOfBoundsException e){
            return null;
        }
        return result;
    }

    //aggiunge un dado nella draft pool
    public void addDie(Die die){
        diceList.add(die);
    }

    //NB -----> questo metodo espone il rep della classe, non possiamo tenerlo, è un errore grave
    /*public ArrayList<Die> getDiceList() {
        return diceList;
    }*/

    //mette i dadi che non sono stati usati nella round track
    public void cleanDraftPool(){
        if(!diceList.isEmpty()){
            int size = diceList.size();
            for(int i=0; i<size; i++){
                roundTrack.addDie(takeDie(0), roundTrack.getRound());
            }
        }
    }
}
