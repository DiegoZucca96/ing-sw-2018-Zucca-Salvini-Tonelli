package ingsw.model;

import ingsw.model.windowpattern.WindowPattern;
import java.util.ArrayList;

/**
 * Author : Diego Zucca - Alessio Tonelli
 *
 * This class contains all the information necessary to use the tool cards
 */
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

    /**
     * Constructor, all these parameters are initialized even if someone will not be used by a specific tool card
     */
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

    /**
     * Simply getter method
     * @return the first die
     */
    public Die getDie1() {
        return die1;
    }

    /**
     * Simply setter method
     * @param die1 it is the first die that has to be assign
     */
    public void setDie1(Die die1) {
        this.die1 = die1;
    }

    /**
     * Simply getter method
     * @return the second die
     */
    public Die getDie2() {
        return die2;
    }

    /**
     * Simply setter method
     * @param die2 it is the second die that has to be assign
     */
    public void setDie2(Die die2) {
        this.die2 = die2;
    }

    /**
     * Simply getter method
     * @return the window
     */
    public WindowPattern getWindow() {
        return window;
    }

    /**
     * Simply setter method
     * @param window it is the window that has to be assign
     */
    public void setWindow(WindowPattern window) {
        this.window = window;
    }

    /**
     * Simply getter method
     * @return the new number of the selected die
     */
    public int getDieModified() {
        return dieModified;
    }

    /**
     * Simply setter method
     * @param dieModified it is the new number of the selected die that has to be assign
     */
    public void setDieModified(int dieModified) {
        this.dieModified = dieModified;
    }

    /**
     * Simply getter method
     * @return the first start coordinate
     */
    public Coordinate getC1() {
        return c1;
    }

    /**
     * Simply setter method
     * @param c1 it is the first start coordinate that has to be assign
     */
    public void setC1(Coordinate c1) {
        this.c1 = c1;
    }

    /**
     * Simply getter method
     * @return the second start coordinate
     */
    public Coordinate getC2() {
        return c2;
    }

    /**
     * Simply setter method
     * @param c2 it is the second start coordinate that has to be assign
     */
    public void setC2(Coordinate c2) {
        this.c2 = c2;
    }

    /**
     * Simply getter method
     * @return the first destination coordinate
     */
    public Coordinate getD1() {
        return d1;
    }

    /**
     * Simply setter method
     * @param d1 it is the first destination coordinate that has to be assign
     */
    public void setD1(Coordinate d1) {
        this.d1 = d1;
    }

    /**
     * Simply getter method
     * @return the second destination coordinate
     */
    public Coordinate getD2() {
        return d2;
    }

    /**
     * Simply setter method
     * @param d2 it is the second destination coordinate that has to be assign
     */
    public void setD2(Coordinate d2) {
        this.d2 = d2;
    }

    /**
     * Simply getter method
     * @return the roundtrack
     */
    public RoundTrack getRt() {
        return rt;
    }

    /**
     * Simply setter method
     * @param rt it is the roundTrack that has to be assign
     */
    public void setRt(RoundTrack rt) {
        this.rt = rt;
    }

    /**
     * Simply getter method
     * @return the draftpool
     */
    public DraftPool getDp() {
        return dp;
    }

    /**
     * Simply setter method
     * @param dp it is the draftpool that has to be assign
     */
    public void setDp(DraftPool dp) {
        this.dp = dp;
    }

    /**
     * Simply getter method
     * @return the dicebag
     */
    public DiceBag getDiceBag() {
        return diceBag;
    }

    /**
     * Simply setter method
     * @param diceBag it is the dicebag that has to be assign
     */
    public void setDiceBag(DiceBag diceBag) {
        this.diceBag = diceBag;
    }

    /**
     * Simply getter method
     * @return the color
     */
    public Color getColor() { return color;}

    /**
     * Simply setter method
     * @param color it is the color that has to be assign
     */
    public void setColor(Color color) { this.color = color;}

    /**
     * Simply getter method
     * @return the round
     */
    public int getRound() {
        return round;
    }

    /**
     * Simply setter method
     * @param round it is the round that has to be assign
     */
    public void setRound(int round) {
        this.round = round;
    }

    /**
     * Simply getter method
     * @return the list of real coordinates
     */
    public ArrayList<Coordinate> getListOfCoordinateY() {
        return listOfCoordinateY;
    }

    /**
     * Simply getter method
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Simply setter method
     * @param player it is the player that has to be assign
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Simply getter method
     * @return the phase
     */
    public int getPhase() {
        return phase;
    }

    /**
     * Simply setter method
     * @param phase it is the phase that has to be assign
     */
    public void setPhase(int phase) {
        this.phase = phase;
    }

    /**
     * Simply setter method
     * @param listOfCoordinateY it is the list of real coordinates that has to be assign
     */
    public void setListOfCoordinateY(ArrayList<Coordinate> listOfCoordinateY) {
        this.listOfCoordinateY = listOfCoordinateY;
    }
}
