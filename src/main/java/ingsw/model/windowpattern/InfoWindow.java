package ingsw.model.windowpattern;


import java.io.Serializable;

/**Author : Alessio Tonelli
 *
 *
 * TAKE WP'S INFO FROM XML FILE AND SEND IT TO THE GUI AND MODEL*/
public class InfoWindow implements Serializable {

    private String name;
    private String difficulty;

    /**
     * Simply getter method
     * @return the name of the window
     */
    public String getName() {
        return name;
    }

    /**
     * Simply setter method
     * @param name it is the name that has to be assign
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
     * @param difficulty it is the difficulty that has to be assign
     */
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

}
