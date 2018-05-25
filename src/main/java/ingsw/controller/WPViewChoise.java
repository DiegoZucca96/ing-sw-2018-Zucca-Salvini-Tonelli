package ingsw.controller;

import java.io.Serializable;
import java.util.ArrayList;

public class WPViewChoise implements Serializable {

    private String name;
    private String difficulty;
    private InfoCell [][] wps;


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

    public InfoCell [][] getWps() {
        return wps;
    }

    public void setWps(InfoCell [][] wps) {
        this.wps = wps;
    }
}
