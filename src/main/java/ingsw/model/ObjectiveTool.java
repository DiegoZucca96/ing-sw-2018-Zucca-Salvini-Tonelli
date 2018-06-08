package ingsw.model;

import ingsw.model.windowpattern.WindowPattern;

// Oggetto da passare al metodo delle ToolCard, varia in base alla ToolCard usata
public class ObjectiveTool {
    private Die die1;
    private Die die2;
    private WindowPattern window;
    private int dieModified;//Per la ToolCard 1, serve a dire se si vuole alzare (1) o abbassare (-1) il valore (default 0)
    private Coordinate c1;
    private Coordinate c2;
    private Coordinate d1;
    private Coordinate d2;
    private RoundTrack rt;
    private DraftPool dp;
    private int turnDie;        // Se 1 bisogna ruotare, altrimenti è zero di base
    private DiceBag diceBag;
    private Color color;
    private int round;

    //Tool1
    public ObjectiveTool(Coordinate c1, int dieModified, DraftPool dp){
        this.c1 = c1;
        this.dieModified = dieModified;
        this.dp = dp;
    }

    public ObjectiveTool(WindowPattern window,Coordinate c1, Coordinate d1){
        this.window = window;
        this.c1 = c1;
        this.d1 = d1;
    }

    public ObjectiveTool(WindowPattern window, Coordinate c1, Coordinate c2, Coordinate d1, Coordinate d2){
        this.window = window;
        this.c1 = c1;
        this.c2 = c2;
        this.d1 = d1;
        this.d2 = d2;
    }

    //Tool5
    public ObjectiveTool(Coordinate c1, Coordinate d1, RoundTrack rt, DraftPool dp, int round){
        this.c1 = c1;
        this.d1 = d1;
        this.rt = rt;
        this.dp = dp;
        this.round = round;
    }

    public ObjectiveTool(Die die1, WindowPattern window, DraftPool dp){
        this.die1 = die1;
        this.window = window;
        this.dp = dp;
    }

    public ObjectiveTool(DraftPool dp){
        this.dp = dp;
    }

    public ObjectiveTool(WindowPattern window, Coordinate d1, DraftPool dp){
        this.window = window;
        this.d1 = d1;
        this.dp = dp;
    }

    public ObjectiveTool(Die die1, WindowPattern window, Coordinate d1){
        this.die1 = die1;
        this.window = window;
        this.d1 = d1;
    }

    //Tool10
    public ObjectiveTool(Coordinate c1, DraftPool dp){
        this.c1 = c1;
        this.dp = dp;
    }

    public ObjectiveTool(Die die1, WindowPattern window, DraftPool dp ,DiceBag diceBag){
        this.die1 = die1;
        this.window = window;
        this.diceBag = diceBag;
        this.dp = dp;
    }

    public ObjectiveTool(WindowPattern window, Coordinate c1, Coordinate c2, Coordinate d1, Coordinate d2, Color color){
        this.window = window;
        this.c1 = c1;
        this.c2 = c2;
        this.d1 = d1;
        this.d2 = d2;
        this.color = color;
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

    public int getTurnDie() {
        return turnDie;
    }

    public void setTurnDie(int turnDie) {
        this.turnDie = turnDie;
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
}
