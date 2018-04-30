package ingsw.model;

//Classe che immagazzina i parametri (NB --> provenienti dal client) per l'utilizzo delle tool card
public class PlayerToolParameter {

    private int idCard;
    private Die die1;
    private Die die2;
    private Coordinate c1;
    private Coordinate c2;
    private Coordinate d1;
    private Coordinate d2;
    private int up;
    private int turnDie;

    public PlayerToolParameter(int idCard, Die die1, int upOrTurnDie){
        this.idCard = idCard;
        this.die1 = die1;
        this.up = upOrTurnDie;
        turnDie = upOrTurnDie;
    }

    public PlayerToolParameter(int idCard, Die die1){
        this.idCard = idCard;
        this.die1 = die1;
    }

    public PlayerToolParameter(int idCard, Coordinate c1, Coordinate d1){
        this.idCard = idCard;
        this.c1 = c1;
        this.d1 = d1;
    }

    public PlayerToolParameter(int idCard, Coordinate c1, Coordinate c2, Coordinate d1, Coordinate d2){
        this.idCard = idCard;
        this.c1 = c1;
        this.c2 = c2;
        this.d1 = d1;
        this.d2 = d2;
    }

    public PlayerToolParameter(int idCard, Die die1, Die die2){
        this.idCard = idCard;
        this.die1 = die1;
        this.die2 = die2;
    }

    public PlayerToolParameter(int idCard, Coordinate d1){
        this.idCard = idCard;
        this.d1 = d1;
    }

    public PlayerToolParameter(int idCard, Die die1, Coordinate d1){
        this.idCard = idCard;
        this.die1 = die1;
        this.d1 = d1;
    }

    public int getIdCard() {
        return idCard;
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

    public int getUp() {
        return up;
    }

    public int getTurnDie() {
        return turnDie;
    }
}
