package ingsw.view;

import javafx.scene.control.Button;

public class ButtonView extends Button {

    private int x;
    private int y;

    ButtonView(int x,int y){
        this.x=x;
        this.y=y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
