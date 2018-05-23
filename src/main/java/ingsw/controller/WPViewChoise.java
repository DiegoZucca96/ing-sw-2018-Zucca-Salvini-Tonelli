package ingsw.controller;

import java.util.ArrayList;

public class WPViewChoise {

    private String name;
    private String difficulty;
    private ArrayList<String>[][] wps;


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

    public ArrayList<String>[][] getWps() {
        return wps;
    }

    public void setWps(ArrayList<String>[][] wps) {
        this.wps = wps;
    }
}
