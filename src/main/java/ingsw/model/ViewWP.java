package ingsw.model;

import ingsw.controller.InfoCell;

import java.io.Serializable;
import java.util.ArrayList;

public class ViewWP implements Serializable {

    private String name;
    private String difficulty;
    private InfoCell[][] wp;
    private int numberWP;


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

    public InfoCell [][] getWp() {
        return wp;
    }

    public void setWps(InfoCell [][] wps) {
        this.wp = wps;
    }

    public int getNumberWP() {
        return numberWP;
    }

    public void setNumberWP(int numberWP) {
        this.numberWP = numberWP;
    }
}
