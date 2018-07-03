package ingsw.view.gui;

import javafx.scene.layout.Background;

/**
 * Author : Alessio Tonelli
 * Class useful to save infos like background of die, his row and column in Window, DraftPool and RoundTrack.*/
public class DieInfo {

    private Background background;
    private int row;
    private  int column;

    /**Constructor*/
    public DieInfo(Background background, int row, int column){
        this.background=background;
        this.row=row;
        this.column=column;
    }

    /**Getter*/
    public Background getBackground() {
        return background;
    }

    /**Setter*/
    public void setBackground(Background background) {
        this.background = background;
    }

    /**Getter*/
    public int getRow() {
        return row;
    }

    /**Setter*/
    public void setRow(int row) {
        this.row = row;
    }

    /**Getter*/
    public int getColumn() {
        return column;
    }

    /**Setter*/
    public void setColumn(int column) {
        this.column = column;
    }
}
