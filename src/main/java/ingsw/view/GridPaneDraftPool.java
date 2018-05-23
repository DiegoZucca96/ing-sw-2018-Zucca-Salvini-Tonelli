package ingsw.view;


import ingsw.Client;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;

public class GridPaneDraftPool extends GridPane {


    private DieInfo dieInfo;
    private Client client;
    private GridPaneWindow windowPattern;
    private Button b;


    public GridPaneDraftPool(Client client) {
        this.client = client;
        int numCols = 3;
        int numRows = 3;

        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setHgrow(Priority.SOMETIMES);
            this.getColumnConstraints().add(colConstraints);
        }

        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.SOMETIMES);
            this.getRowConstraints().add(rowConstraints);
        }

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                b = addButtonDP(row, col);
                b.setPrefSize(58, 58);
                if (client.getPlayerState().equalsIgnoreCase("disabled")) {
                    b.setDisable(true);
                } else b.setDisable(false);

            }

        }
    }

    private Button addButtonDP(int row, int col) {
        Button button = new Button();

            button.setOnMouseEntered(e -> {
                dieInfo = new DieInfo(button.getBackground(), row, col);

                if(!dieInfo.getBackground().equals(Color.TRANSPARENT)){
                    button.setStyle("-fx-border-color: yellow");
                    setDieInfo(dieInfo);
                    client.takeDie( row, col);
                }else{
                    Toolkit.getDefaultToolkit().beep();
                    dieInfo=null;
                }

            });

        this.add(button, row, col);
        return button;

    }

    public Button getButton(int row, int col){
        return b;
    }


    public DieInfo getDieInfo(){
        return dieInfo;
    }

    public void setDieInfo(DieInfo dieInfo) {
        this.dieInfo = dieInfo;
    }
}