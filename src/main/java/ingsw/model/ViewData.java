package ingsw.model;

import ingsw.controller.ViewWP;
import ingsw.model.windowpattern.InfoWindow;

import java.io.Serializable;
import java.util.ArrayList;

//Oggetto che porta le informazioni grafiche da model a view
public class ViewData implements Serializable {

    private InfoWindow info;
    private ArrayList<Cell> images;
    private ArrayList<ViewWP> wps;
    private ArrayList<String> pbCards;
    private ArrayList<String> toolCards;
    private ArrayList<String> pvCards;


    public ViewData() {
        info = new InfoWindow();
        images = new ArrayList<>();
        wps = new ArrayList<>();
        pbCards = new ArrayList<>();
        toolCards = new ArrayList<>();
        pvCards = new ArrayList<>();
    }

    public InfoWindow getInfo() {
        return info;
    }

    public ArrayList<Cell> getImages() {
        return images;
    }

    public ArrayList<ViewWP> getWps() {
        return wps;
    }

    public ArrayList<String> getPbCard() {
        return pbCards;
    }

    public ArrayList<String> getToolCard() {
        return toolCards;
    }

    public ArrayList<String>  getPvCard() {
        return pvCards;
    }

    public void addWp(ViewWP wp){
        wps.add(wp);
    }

    public void addPBCard(String pbCard){
        pbCards.add(pbCard);
    }

    public void addPVCard(String pvCard){
        pbCards.add(pvCard);
    }

    public void addToolCard(String toolCard){
        toolCards.add(toolCard);
    }
}
