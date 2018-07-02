package ingsw.view.GUI;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Author : Alessio Tonelli - Diego Zucca
 * Class which connects Gui with Tool Cards in model.
 * It enrich its information with setter methods. This information is passed to model through Controller.
 * @see ingsw.controller.Controller
 */
public class ToolView implements Serializable {
    private int startRow1;
    private int startCol1;
    private int endRow1;
    private int endCol1;
    private int startRow2;
    private int startCol2;
    private int endRow2;
    private int endCol2;
    private int dieModified;
    private int round;
    private String color;
    private ArrayList<String> listOfCoordinateY = new ArrayList<>();
    private int phase = 0;      /**Important: some tool cards need to be executed partially. This attribute defines in which phase we must let start the card and where to stop it.*/


    public int getStartRow1() {
        return startRow1;
    }

    public void setStartRow1(int startRow1) {
        this.startRow1 = startRow1;
    }

    public int getStartCol1() {
        return startCol1;
    }

    public void setStartCol1(int startCol1) {
        this.startCol1 = startCol1;
    }

    public int getEndRow1() {
        return endRow1;
    }

    public void setEndRow1(int endRow1) {
        this.endRow1 = endRow1;
    }

    public int getEndCol1() {
        return endCol1;
    }

    public void setEndCol1(int endCol1) {
        this.endCol1 = endCol1;
    }

    public int getStartRow2() {
        return startRow2;
    }

    public void setStartRow2(int startRow2) {
        this.startRow2 = startRow2;
    }

    public int getStartCol2() {
        return startCol2;
    }

    public void setStartCol2(int startCol2) {
        this.startCol2 = startCol2;
    }

    public int getEndRow2() {
        return endRow2;
    }

    public void setEndRow2(int endRow2) {
        this.endRow2 = endRow2;
    }

    public int getEndCol2() {
        return endCol2;
    }

    public void setEndCol2(int endCol2) {
        this.endCol2 = endCol2;
    }

    public int getDieModified() {
        return dieModified;
    }

    public void setDieModified(int dieModified) {
        this.dieModified = dieModified;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    /**Since we need in tool card 7 save position in DraftPool of dice which must be thrown again,
     * this method returns array of positions.
     * */
    public ArrayList<String> getListOfCoordinateY() {
        return listOfCoordinateY;
    }

    /**Since we need in tool card 7 save position in DraftPool of dice which must be thrown again,
     * this method saves positions in an array.
     * */
    public void setListOfCoordinateY(String listOfCoordinateY) {
        this.listOfCoordinateY.add(listOfCoordinateY);
    }

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }
}
