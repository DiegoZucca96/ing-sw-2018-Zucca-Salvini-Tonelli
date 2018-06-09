package ingsw.model;

import java.util.ArrayList;

//Classe che immagazzina i parametri (NB --> provenienti dal client) per l'utilizzo delle tool card
public class PlayerToolParameter {

    private Die die1;
    private Die die2;
    private Coordinate c1;
    private Coordinate c2;
    private Coordinate d1;
    private Coordinate d2;
    private int dieModified;
    private int round;
    private Color color;
    private ArrayList<Coordinate> listOfCoordinateY = new ArrayList<>();
    private boolean doubleTurn;

    public PlayerToolParameter( Coordinate c1, int dieModified){
        this.c1 = c1;
        this.dieModified = dieModified;
    }

    public PlayerToolParameter( Die die1){
        this.die1 = die1;
    }

    public PlayerToolParameter( Coordinate c1, Coordinate d1, int round){
        this.c1 = c1;
        this.d1 = d1;
        this.round=round;
    }

    public PlayerToolParameter( Coordinate c1, Coordinate c2, Coordinate d1, Coordinate d2, Color color){
        this.c1 = c1;
        this.c2 = c2;
        this.d1 = d1;
        this.d2 = d2;
        this.color = color;
    }

    public PlayerToolParameter( Die die1, Die die2){
        this.die1 = die1;
        this.die2 = die2;
    }

    public PlayerToolParameter( Coordinate c1){
        this.c1 = c1;
    }

    public PlayerToolParameter( Die die1, Coordinate d1){
        this.die1 = die1;
        this.d1 = d1;
    }
    //Tool 7
    public PlayerToolParameter ( ArrayList<String> listOfCoordinateY){
        for(String c : listOfCoordinateY)
            this.listOfCoordinateY.add(new Coordinate(0, Integer.parseInt(c)));
    }

    //Tool8
    public PlayerToolParameter (boolean doubleTurn){
        this.doubleTurn = doubleTurn;
    }
    //Tool9
    public PlayerToolParameter(Coordinate c1, Coordinate d1){
        this.c1 = c1;
        this.d1 = d1;
    }


    public Die getDie1() {
        return die1;
    }

    public Die getDie2() {
        return die2;
    }

    public Coordinate getC1() {
        return c1;
    }

    public Coordinate getC2() {
        return c2;
    }

    public Coordinate getD1() {
        return d1;
    }

    public Coordinate getD2() {
        return d2;
    }

    public int getDieModified() {
        return dieModified;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public Color getColor() { return color;}

    public ArrayList<Coordinate> getListOfCoordinateY() {
        return listOfCoordinateY;
    }

    public boolean isDoubleTurn() {
        return doubleTurn;
    }
}
