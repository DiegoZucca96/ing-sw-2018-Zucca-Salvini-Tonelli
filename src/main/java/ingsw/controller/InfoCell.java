package ingsw.controller;

import java.io.Serializable;
import java.util.ArrayList;

public class InfoCell  implements Serializable {

    private ArrayList<String> numCol;

    public InfoCell() {
        this.numCol= new ArrayList<>();
    }


    public ArrayList<String> getNumCol() {
        return numCol;
    }

    public void setNumCol(ArrayList<String> numCol) {
        this.numCol = numCol;
    }
}
