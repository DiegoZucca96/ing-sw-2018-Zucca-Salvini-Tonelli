package ingsw.controller;

import ingsw.model.Color;

import java.io.Serializable;

public class InfoCell  implements Serializable {

    private int num;
    private Color color;
    private String die;

    public InfoCell(int num, Color color, String die){
        this.num=num;
        this.color=color;
        this.die=die;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getDie() {
        return die;
    }

    public void setDie(String die) {
        this.die = die;
    }
}
