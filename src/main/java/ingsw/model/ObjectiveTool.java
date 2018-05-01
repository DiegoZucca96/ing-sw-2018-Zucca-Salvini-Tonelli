package ingsw.model;

import ingsw.model.windowpattern.WindowPattern;

// Oggetto da passare al metodo delle ToolCard, varia in base alla ToolCard usata
public class ObjectiveTool {
    private Die die1;
    private Die die2;
    private WindowPattern window;
    private int up;             //Per la ToolCard 1, serve a dire se si vuole alzare (1) o abbassare (-1) il valore (default 0)
    private Coordinate c1;
    private Coordinate c2;
    private Coordinate destination1;
    private Coordinate destination2;
    private RoundTrack rt;
    private DraftPool dp;
    private int turnDie;        // Se 1 bisogna ruotare, altrimenti è zero di base
    private DiceBag diceBag;


    public ObjectiveTool(Die die1, int up, DraftPool dp){
        this.die1 = die1;
        this.up = up;
        this.dp = dp;
    }

    public ObjectiveTool(WindowPattern window,Coordinate c1, Coordinate destination1){
        this.window = window;
        this.c1 = c1;
        this.destination1 = destination1;
    }

    public ObjectiveTool(WindowPattern window, Coordinate c1, Coordinate c2, Coordinate destination1, Coordinate destination2){
        this.window = window;
        this.c1 = c1;
        this.c2 = c2;
        this.destination1 = destination1;
        this.destination2 = destination2;
    }

    public ObjectiveTool(Die die1, Die die2, RoundTrack rt, DraftPool dp ){
        this.die1 = die1;
        this.die2 = die2;
        this.rt = rt;
        this.dp = dp;
    }

    public ObjectiveTool(Die die1, WindowPattern window, DraftPool dp){
        this.die1 = die1;
        this.window = window;
        this.dp = dp;
    }

    public ObjectiveTool(DraftPool dp){
        this.dp = dp;
    }

    public ObjectiveTool(WindowPattern window, Coordinate destination1, DraftPool dp){
        this.window = window;
        this.destination1 = destination1;
        this.dp = dp;
    }

    public ObjectiveTool(Die die1, WindowPattern window, Coordinate destination1){
        this.die1 = die1;
        this.window = window;
        this.destination1 = destination1;
    }

    public ObjectiveTool(Die die1, DraftPool dp, int turnDie){
        this.die1 = die1;
        this.dp = dp;
        this.turnDie = turnDie;
    }

    public ObjectiveTool(Die die1, WindowPattern window, DraftPool dp ,DiceBag diceBag){
        this.die1 = die1;
        this.window = window;
        this.diceBag = diceBag;
        this.dp = dp;
    }

    public ObjectiveTool(WindowPattern window, Coordinate c1, Coordinate c2, Coordinate destination1, Coordinate destination2, RoundTrack rt){
        this.window = window;
        this.c1 = c1;
        this.c2 = c2;
        this.destination1 = destination1;
        this.destination2 = destination2;
        this.rt = rt;
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

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
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

    public Coordinate getDestination1() {
        return destination1;
    }

    public void setDestination1(Coordinate destination1) {
        this.destination1 = destination1;
    }

    public Coordinate getDestination2() {
        return destination2;
    }

    public void setDestination2(Coordinate destination2) {
        this.destination2 = destination2;
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

}
