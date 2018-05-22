package ingsw.view;


import ingsw.Client;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.awt.*;
import java.util.ArrayList;

public class GridPaneTrackingController extends GridPane {


    private Client client;
    private GridPane grid ;

    public GridPaneTrackingController(int indexString, ArrayList<String> myWindow, Client client) {

        this.client=client;
        int numCols = 5 ;
        int numRows = 4 ;

        for (int i = 0 ; i < numCols ; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setHgrow(Priority.SOMETIMES);
            grid.getColumnConstraints().add(colConstraints);
        }

        for (int i = 0 ; i < numRows ; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.SOMETIMES);
            grid.getRowConstraints().add(rowConstraints);
        }

        for (int i = 0 ; i < numRows ; i++) {
            for (int j = 0; j < numCols; j++) {
                Button btnCell = addButtonWp(i, j);
                btnCell.setPrefSize(55, 55);
                String cell = myWindow.get(indexString);
                String numCell = cell.substring(cell.indexOf(':')+1,cell.indexOf(',')-1);
                String colorCell = cell.substring(cell.indexOf(",")+1);
                String pathCell = WPRendering.pathCell(numCell, colorCell);
                Image myImage = new Image(pathCell, 55, 55, false, false);
                BackgroundImage myBI= new BackgroundImage(myImage,
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                        BackgroundSize.DEFAULT);
                btnCell.setBackground(new Background(myBI));
                indexString++;
            }
        }
    }

    private Button addButtonWp(int i, int j) {
        Button button = new Button();

        button.setOnMouseEntered(e -> {

            if(client.positionDie(i, j)){

            }else
                Toolkit.getDefaultToolkit().beep();
            //System.out.printf("Mouse enetered cell [%d, %d]%n", colIndex, rowIndex);
        });
        grid.add(button, i, j);
        return button;
    }

    public GridPaneTrackingController( Client client) {
        this.client = client;
        int numCols = 5;
        int numRows = 4;

        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setHgrow(Priority.SOMETIMES);
            grid.getColumnConstraints().add(colConstraints);
        }

        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.SOMETIMES);
            grid.getRowConstraints().add(rowConstraints);
        }

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button b = addButtonDP(row, col);
                b.setOpacity(0.6);
                b.setPrefSize(58, 58);
                if (client.getPlayerState().equalsIgnoreCase("disabled")) {
                    b.setDisable(true);
                } else b.setDisable(false);

                //b.setBackground(new BackgroundImage());

            }

        }
    }



    private Button addButtonDP(int row, int col) {
        Button button = new Button();

            button.setOnMouseEntered(e -> {
//saveInfo();
                if(client.takeDie(row,col)){

                }else
                    Toolkit.getDefaultToolkit().beep();
                button.setTextFill(javafx.scene.paint.Color.TRANSPARENT);
            });

        grid.add(button, row, col);
        return button;

    }


}