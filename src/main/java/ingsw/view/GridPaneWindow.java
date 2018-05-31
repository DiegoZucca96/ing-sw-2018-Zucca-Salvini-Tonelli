package ingsw.view;

import ingsw.Client;
import ingsw.model.Color;
import ingsw.model.ViewWP;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.awt.*;
import java.util.ArrayList;

public class GridPaneWindow extends GridPane {

    private ArrayList<DieInfo> dieInfos = new ArrayList<>();
    private ArrayList<CellInfo> cellInfos= new ArrayList<>();
    private Client client;
    private GridPaneDraftPool draftPool;
    String number;
    String color;

    public GridPaneWindow( ViewWP myWindow, Client client, GridPaneDraftPool draftPool) {

        this.client=client;
        this.draftPool=draftPool;
        int numCols = 5 ;
        int numRows = 4 ;

        for (int j = 0 ; j < numCols ; j++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setHgrow(Priority.SOMETIMES);
            this.getColumnConstraints().add(colConstraints);
        }

        for (int i = 0 ; i < numRows ; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.SOMETIMES);
            this.getRowConstraints().add(rowConstraints);
        }

        for (int i = 0 ; i < numRows ; i++) {
            for (int j = 0; j < numCols; j++) {

                Button btnCell = addButtonWp(i, j);
                btnCell.setPrefSize(50, 50);
                String dieStr = myWindow.getWp()[i][j].getDie();
                if(dieStr==null){
                    number = Integer.toString(myWindow.getWp()[i][j].getNum());
                    color = String.valueOf(myWindow.getWp()[i][j].getColor());
                }else{
                    number =dieStr.substring(dieStr.indexOf("(")+1, dieStr.indexOf(","));
                    color = dieStr.substring(dieStr.indexOf(",")+1, dieStr.indexOf(")"));
                }

                String path = WPRendering.path(number, color);
                Image myImage = new Image(path, 50, 50, false, true);
                BackgroundImage myBI= new BackgroundImage(myImage,
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                        BackgroundSize.DEFAULT);
                btnCell.setBackground(new Background(myBI));
            }
        }
    }

    private Button addButtonWp(int i, int j) {
        Button button = new Button();

        button.setOnAction(e -> {

            if(client.positionDie(i, j)){
                addCellInfo(button.getBackground(), i, j);      //salvo il backgruond della cella
                button.setBackground(draftPool.getDieInfo().getBackground());       //setto il nuovo background col dado
                draftPool.getButton(draftPool.getDieInfo().getRow(), draftPool.getDieInfo().getColumn()).setTextFill(javafx.scene.paint.Color.TRANSPARENT);
            }else{
                Toolkit.getDefaultToolkit().beep();
                draftPool.getButton(0, client.getCoordinateSelectedY()).setStyle(null);
            }


            //System.out.printf("Mouse enetered cell [%d, %d]%n", colIndex, rowIndex);
        });
        this.add(button, j, i);
        return button;
    }

    public ArrayList<DieInfo> getDieInfos(){
        return dieInfos;
    }

    public void addDieInfo(Background image, int i, int j) {
        DieInfo dieInfo = new DieInfo(image, i, j);
        dieInfos.add(dieInfo);
    }

    public ArrayList<CellInfo> getCellInfo(){
        return cellInfos;
    }

    public void addCellInfo(Background image, int i, int j) {
        CellInfo cellInfo= new CellInfo(image, i, j);
        cellInfos.add(cellInfo);
    }
}