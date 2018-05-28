package ingsw.model;

import java.util.ArrayList;
import java.util.Random;

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

    //NB --> metodo ad uso esclusivo dei test
    public void setDiceBag(DiceBag diceBag) {
        this.diceBag = diceBag;
    }

    //mette i dadi in avanzo relativi al turno precedente nella round track, lancia nDice nuovi dadi.
    public ArrayList<Die> throwsDice(int nDice){
        for(int i=0; i<nDice; i++){
            diceList.add(diceBag.randomDice());
        }
        return diceList;
    }

    //restituisce il riferimento al dado in posizione index
    public Die getDie(int index){
        try{
            return diceList.get(index);
        }catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    //fa get(index), aggiunge un dado finto per riempire la griglia
    public Die takeDie(int index){
        Die result = getDie(index);
        try{
            diceList.set(index,new Die(0,Color.WHITE));
        } catch(IndexOutOfBoundsException e){
            return null;
        }
        
        return result;
    }

    //aggiunge un dado nella draft pool
    public void addDie(Die die){
        diceList.add(die);
    }

    //Metodo che serve nella Tool11 per prendere il nuovo dado estratto con throwsDice
    public int getDiceListSize(){
        return diceList.size();
    }

    //mette i dadi che non sono stati usati nella round track
    public void cleanDraftPool(){
        if(!diceList.isEmpty()){
            int size = diceList.size();
            for(int i=0; i<size; i++){
                roundTrack.addDie(takeDie(0), roundTrack.getRound());
            }
        }
    }

    //Tiro nuovamente tutti i dadi della riserva (ToolCard 7)
    public void refreshDraftPool(){

       for(Die die: diceList){
            RandomGenerator rg = new RandomGenerator(6);
            die.setNumber(rg.random());
        }
    }

}
