package ingsw.model.windowpattern;


import java.io.Serializable;

/**Author : Alessio Tonelli
 *
 *
 * TAKE WP'S INFO FROM XML FILE AND SEND IT TO THE GUI AND MODEL*/
public class InfoWindow implements Serializable {

    private String name;
    private String difficulty;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

}
