package ingsw.model;

import java.io.Serializable;

/**
 * This class contains the graphic information of the windows
 */
public class ViewWP implements Serializable {

    private String name;
    private String difficulty;
    private InfoCell[][] wp;
    private int numberWP;

    /**
     * Simply getter method
     * @return the name of the window
     */
    public String getName() {
        return name;
    }

    /**
     * Simply setter method
     * @param name it is the name of the window that has to be assign
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Simply getter method
     * @return the difficulty of the window
     */
    public String getDifficulty() {
        return difficulty;
    }

    /**
     * Simply setter method
     * @param difficulty it is the difficulty of the window that has to be assign
     */
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Simply getter method
     * @return the InfoCell object of the window
     */
    public InfoCell [][] getWp() {
        return wp;
    }

    /**
     * Simply setter method
     * @param wps it is the InfoCell object that has to be assign
     */
    public void setWps(InfoCell [][] wps) {
        this.wp = wps;
    }

    /**
     * Simply getter method
     * @return the number of the window
     */
    public int getNumberWP() {
        return numberWP;
    }

    /**
     * Simply setter method
     * @param numberWP it is the numbero of the window that has to be assign
     */
    public void setNumberWP(int numberWP) {
        this.numberWP = numberWP;
    }
}
