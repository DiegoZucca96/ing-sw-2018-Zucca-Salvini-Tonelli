package ingsw.model;

import ingsw.model.windowpattern.WindowPattern;
import java.util.ArrayList;

// Oggetto da passare al metodo delle ToolCard, varia in base alla ToolCard usata
public class ObjectiveTool {
    private int phase;
    private Die die1;
    private Die die2;
    private WindowPattern window;
    private int dieModified;
    private Coordinate c1;
    private Coordinate c2;
    private Coordinate d1;
    private Coordinate d2;
    private RoundTrack rt;
    private DraftPool dp;
    private DiceBag diceBag;
    private Color color;
    private int round;
    private ArrayList<Coordinate> listOfCoordinateY;
    private Player player;

    public ObjectiveTool() {
        this.phase = -1;
        this.die1 = null;
        this.die2 = null;
        this.window = null;
        this.dieModified = -1;
        this.c1 = null;
        this.c2 = null;
        this.d1 = null;
        this.d2 = null;
        this.rt = null;
        this.dp = null;
        this.diceBag = null;
        this.color = null;
        this.round = -1;
        this.listOfCoordinateY = null;
        this.player = null;
    }


    public Die getDie1() {
        return die1;
    }

    public void setDie1(Die die1) {
        this.die1 = die1;
    }

    public Die getDie2() {
        return die2;
    }

    public void setDie2(Die die2) {
        this.die2 = die2;
    }

    public WindowPattern getWindow() {
        return window;
    }

    public void setWindow(WindowPattern window) {
        this.window = window;
    }

    public int getDieModified() {
        return dieModified;
    }

    public void setDieModified(int dieModified) {
        this.dieModified = dieModified;
    }

    public Coordinate getC1() {
        return c1;
    }

    public void setC1(Coordinate c1) {
        this.c1 = c1;
    }

    public Coordinate getC2() {
        return c2;
    }

    public void setC2(Coordinate c2) {
        this.c2 = c2;
    }

    public Coordinate getD1() {
        return d1;
    }

    public void setD1(Coordinate d1) {
        this.d1 = d1;
    }

    public Coordinate getD2() {
        return d2;
    }

    public void setD2(Coordinate d2) {
        this.d2 = d2;
    }

    public RoundTrack getRt() {
        return rt;
    }

    public void setRt(RoundTrack rt) {
        this.rt = rt;
    }

    public DraftPool getDp() {
        return dp;
    }

    public void setDp(DraftPool dp) {
        this.dp = dp;
    }

    public DiceBag getDiceBag() {
        return diceBag;
    }

    public void setDiceBag(DiceBag diceBag) {
        this.diceBag = diceBag;
    }

    public Color getColor() { return color;}

    public void setColor(Color color) { this.color = color;}

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public ArrayList<Coordinate> getListOfCoordinateY() {
        return listOfCoordinateY;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public void setListOfCoordinateY(ArrayList<Coordinate> listOfCoordinateY) {
        this.listOfCoordinateY = listOfCoordinateY;
    }
}
