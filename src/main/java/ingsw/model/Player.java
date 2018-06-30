package ingsw.model;

import ingsw.model.windowpattern.WindowPattern;
import java.io.IOException;

/**
 * This class contains the information about a player of the match
 */
public class Player {

    private String name;
    private int nFavoriteTokens;
    private int score;
    private int pvScore;
    private WindowPattern windowPattern;
    private PVObjectiveCard pvObjectiveCard;
    private Die dieSelected;
    private Coordinate coordinateDieSelected;
    private boolean insertedDie; //boolean value, true if player has already place a die
    private boolean isTool8Used; //boolean value, true if player has used tool card number 8

    /**
     * Constructor
     * @param name it is the name of the player
     * @param wpType it is the number of his window
     * @param pvColor it is the color of his private card
     */
    public Player(String name, int wpType, Color pvColor){
        this.name = name;
        score = 0;
        pvScore = 0;
        try {
            windowPattern = new WindowPFactory().createWindowPattern(wpType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        nFavoriteTokens = windowPattern.getDifficulty();
        pvObjectiveCard = new PVObjectiveCard(pvColor);
        this.insertedDie = false;
        this.isTool8Used = false;
    }

    /**
     * Simply getter method
     * @return the selected die
     */
    public Die getDieSelected() {
        return dieSelected;
    }

    /**
     * Simply setter method
     * @param dieSelected it is the selected die that has to be assign
     */
    public void setDieSelected(Die dieSelected) {
        this.dieSelected = dieSelected;
    }

    /**
     * Simply getter method
     * @return the coordinate of the selected die
     */
    public Coordinate getCoordinateDieSelected() {
        return coordinateDieSelected;
    }

    /**
     * Simply setter method
     * @param coordinateDieSelected it is the coordinate of the selected die that has to be assign
     */
    public void setCoordinateDieSelected(Coordinate coordinateDieSelected) {
        this.coordinateDieSelected = coordinateDieSelected;
    }

    /**
     * Simply getter method
     * @return the private card
     */
    public PVObjectiveCard getPvObjectiveCard() {
        return pvObjectiveCard;
    }

    /**
     * Simply getter method
     * @return the player's score
     */
    public int getScore(){
        return score;
    }

    /**
     * Simply getter method
     * @return the player's private score
     */
    public int getPVScore() {
        return pvScore;
    }

    /**
     * Simply getter method
     * @return the player's name
     */
    public String getName(){
        return name;
    }

    /**
     * Simply getter method
     * @return the player's token remaining
     */
    public int getTokens(){
        return nFavoriteTokens;
    }

    /**
     * Simply getter method
     * @return the player's window
     */
    public WindowPattern getWindowPattern() {
        return windowPattern;
    }

    /**
     * Simply setter method, exclusively used for testing
     * @param windowPattern it is the window that has to be assign
     */
    public void setWindowPattern(WindowPattern windowPattern) {
        this.windowPattern = windowPattern;
    }

    /**
     * simple setter method
     * @param score player's score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Simply setter method, exclusively used for testing
     * @param pvObjectiveCard it is the private card that has to be assign
     */
    public void setPvObjectiveCard(PVObjectiveCard pvObjectiveCard) {
        this.pvObjectiveCard = pvObjectiveCard;
    }

    /**
     * Simply getter method
     * @return true if player has already place a die in his turn, otherwise false
     */
    public boolean getInsertedDie() {
        return insertedDie;
    }

    /**
     * Simply setter method
     * @param value it is a boolean value that has to be assign
     */
    public void setInsertedDie(boolean value) {
        this.insertedDie = value;
    }

    /**
     * Simply getter method
     * @return true if player has used the tool card number 8, otherwise false
     */
    public boolean getTool8Used() {
        return isTool8Used;
    }

    /**
     * Simply setter method
     * @param tool8Used it is a boolean value that has to be assign
     */
    public void setTool8Used(boolean tool8Used) {
        this.isTool8Used = tool8Used;
    }

    /**
     * Method that sum additionalScore to the current score
     * @param additionalScore it is the additional score
     */
    public void addScore(int additionalScore){
        score += additionalScore;
    }

    /**
     * Method that subtract surplusScore to the current score
     * @param surplusScore it is the score that has to be subtract
     */
    public void subScore(int surplusScore){
        score -= surplusScore;
    }

    /**
     * Method that consume the player's token
     * @param tool it is the tool card that the player is using
     */
    public void useToken(ToolCard tool){
        if(tool.isAlreadyUsed()) nFavoriteTokens -= 2;
        else{
            nFavoriteTokens--;
            tool.setAlreadyUsed(true);
        }
    }

    /**
     * It positions a die into the player's window
     * @param die it is the die the player wants to place
     * @param coordinates it is the position where place the die
     * @return true if the die can be placed, otherwise false
     */
    public boolean positionDie(Die die, Coordinate coordinates){
        return windowPattern.addDie(coordinates, die, windowPattern.getCellMatrix());
    }

    /**
     * This method assigns to the player his personal score, without count the public card's points
     */
    public void personalScore(){
        addScore(nFavoriteTokens);
        pvScore = windowPattern.countDie(pvObjectiveCard.getColor(), windowPattern.getCellMatrix());
        addScore(pvScore);
        subScore(windowPattern.countEmptyCells(windowPattern.getCellMatrix()));
    }
}
