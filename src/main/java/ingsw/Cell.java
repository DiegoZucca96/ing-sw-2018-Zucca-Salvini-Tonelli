package ingsw;


public class Cell {
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

    public void insertDie(Die die){

        this.die=die;
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


    /*
    public Coordinate getInputCoordinate(){

        return coordinate;
    }*/

    public int getNumber() {
        return number;
    }
    public Color getColor(){
        return color;
    }
}
