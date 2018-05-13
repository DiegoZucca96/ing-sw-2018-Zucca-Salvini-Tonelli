package ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

//Oggetto che porta le informazioni grafiche da model a view
public class InitializerView implements Serializable {
    private ArrayList<String> images;
    private ArrayList<String> pbCard;
    private ArrayList<String> toolCard;
    private ArrayList<String> pvCard;


    public InitializerView() {
        this.images = new ArrayList<>();
        this.pbCard = new ArrayList<>();
        this.toolCard = new ArrayList<>();
        this.pvCard = new ArrayList<>();
    }

    public ArrayList<String> getImages() {
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