package ingsw.model;

import java.util.ArrayList;

/**
 * Class that stores the parameters coming from the client for the use of tool cards
 */
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
    private int phase;

    /**
     * Constructor, all these parameters are initialized even if someone will not be used by a specific tool card
     */
    public PlayerToolParameter() {
        this.die1 = null;
        this.die2 = null;
        this.c1 = null;
        this.c2 = null;
        this.d1 = null;
        this.d2 = null;
        this.dieModified = -1;
        this.round = -1;
        this.color = null;
        this.listOfCoordinateY = new ArrayList<>();
    }

    /**
     * Simply getter method
     * @return the first die
     */
    public Die getDie1() {
        return die1;
    }

    /**
     * Simply getter method
     * @return the second die
     */
    public Die getDie2() {
        return die2;
    }

    /**
     * Simply getter method
     * @return the first start coordinate
     */
    public Coordinate getC1() {
        return c1;
    }

    /**
     * Simply getter method
     * @return the second start coordinate
     */
    public Coordinate getC2() {
        return c2;
    }

    /**
     * Simply getter method
     * @return the first destination coordinate
     */
    public Coordinate getD1() {
        return d1;
    }

    /**
     * Simply getter method
     * @return the second destination coordinate
     */
    public Coordinate getD2() {
        return d2;
    }

    /**
     * Simply getter method
     * @return the new number of the selected die
     */
    public int getDieModified() {
        return dieModified;
    }

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
     * @return the color
     */
    public Color getColor() { return color;}

    /**
     * Simply getter method
     * @return the list of real coordinates
     */
    public ArrayList<Coordinate> getListOfCoordinateY() {
        return listOfCoordinateY;
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
     * @param die1 it is the first die that has to be assign
     */
    public void setDie1(Die die1) {
        this.die1 = die1;
    }

    /**
     * Simply setter method
     * @param die2 it is the second die that has to be assign
     */
    public void setDie2(Die die2) {
        this.die2 = die2;
    }

    /**
     * Simply setter method
     * @param c1 it is the first start coordinate that has to be assign
     */
    public void setC1(Coordinate c1) {
        this.c1 = c1;
    }

    /**
     * Simply setter method
     * @param c2 it is the second start coordinate that has to be assign
     */
    public void setC2(Coordinate c2) {
        this.c2 = c2;
    }

    /**
     * Simply setter method
     * @param d1 it is the first destination coordinate that has to be assign
     */
    public void setD1(Coordinate d1) {
        this.d1 = d1;
    }

    /**
     * Simply setter method
     * @param d2 it is the second destination coordinate that has to be assign
     */
    public void setD2(Coordinate d2) {
        this.d2 = d2;
    }

    /**
     * Simply setter method
     * @param dieModified it is the new number of the selected die that has to be assign
     */
    public void setDieModified(int dieModified) {
        this.dieModified = dieModified;
    }

    /**
     * Simply setter method
     * @param color it is the color that has to be assign
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Simply setter method
     * @param listOfCoordinateY it is the list of real coordinates that has to be assign
     */
    public void setListOfCoordinateY(ArrayList<String> listOfCoordinateY) {
        for(String c : listOfCoordinateY)
            this.listOfCoordinateY.add(new Coordinate(0, Integer.parseInt(c)));
    }
}
