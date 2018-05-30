package ingsw.view;

import javafx.scene.layout.Background;

public class DieInfo {

    private Background background;
    private int row;
    private  int column;

    public DieInfo(Background background, int row, int column){
        this.background=background;
        this.row=row;
        this.column=column;
    }

    public Background getBackground() {
        return background;
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
