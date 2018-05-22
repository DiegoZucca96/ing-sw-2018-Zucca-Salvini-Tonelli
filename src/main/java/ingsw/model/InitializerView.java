package ingsw.model;

import ingsw.model.windowpattern.InfoWindow;

import java.io.Serializable;
import java.util.ArrayList;

//Oggetto che porta le informazioni grafiche da model a view
public class InitializerView implements Serializable {
    private InfoWindow info;
    private ArrayList<Cell> images;
    private ArrayList<String> pbCard;
    private ArrayList<String> toolCard;
    private ArrayList<String> pvCard;


    public InitializerView() {
        this.info= new InfoWindow();
        this.images = new ArrayList<>();
        this.pbCard = new ArrayList<>();
        this.toolCard = new ArrayList<>();
        this.pvCard = new ArrayList<>();
    }

    public InfoWindow getInfo() {
        return info;
    }

    public ArrayList<Cell> getImages() {
        return images;
    }

    public ArrayList<String> getPbCard() {
        return pbCard;
    }

    public ArrayList<String> getToolCard() {
        return toolCard;
    }

    public ArrayList<String>  getPvCard() {
        return pvCard;
    }
}
