package ingsw.model;

import java.io.Serializable;

/**Author : Alessio Tonelli
 *
 *
 * */


public class Cell implements Serializable {
    private Color color;              //colore della cella, per ora commentato perchè implementata da Diego la Enum
    private int number;                 //numero scritto sulla cella come vincolo
    private boolean empty;              //presenza o meno del dado
    private Die die;                    //se dado esiste altrimenti null
    private final Coordinate coordinate;

    //COSTRUTTORE
    public Cell(int number, Color color, Coordinate coordinate){

        this.number= number;
        this.color= color;
        this.coordinate=coordinate;
        insertDie(null);
        this.empty=true;

    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    //true <=> cella vuota
    public boolean isEmpty() {

        if(this.die==null)
            empty=true;
        else  empty=false;

        return empty;
    }

    public boolean insertDie(Die die){
        this.die=die;
        return true;
    }


    //Ritorna il riferimento all'oggetto contenuto nell'attributo
    public Die getDie() {
        return die;
    }

    //Ritorna sempre lo stesso oggetto, ma l'attributo viene settato a null
    public Die takeDie(){
        Die newDie = this.die;
        this.die=null;
        return newDie;

    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    @Override
    public String toString() {
        return "cell:" + number + "," + color;
    }


    public int getNumber() {
        return number;
    }
    public Color getColor(){
        return color;
    }
}
