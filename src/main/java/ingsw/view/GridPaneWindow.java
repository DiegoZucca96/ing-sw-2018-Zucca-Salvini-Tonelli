package ingsw.view;

import ingsw.Client;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.awt.*;
import java.util.ArrayList;

public class GridPaneWindow extends GridPane {

    private ArrayList<DieInfo> dieInfos = new ArrayList<>();
    private ArrayList<CellInfo> cellInfos= new ArrayList<>();
    private Client client;
    private GridPane grid;
    private GridPaneDraftPool draftPool;

    public GridPaneWindow(int indexString, ArrayList<String> myWindow, Client client) {

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
                addCellInfo(button.getBackground(), i, j);      //salvo il backgruond della cella
                button.setBackground(draftPool.getDieInfo().getBackground());       //setto il nuovo background col dado
                draftPool.getButton(i, j).setTextFill(javafx.scene.paint.Color.TRANSPARENT);;
            }else
                Toolkit.getDefaultToolkit().beep();
            //System.out.printf("Mouse enetered cell [%d, %d]%n", colIndex, rowIndex);
        });
        grid.add(button, i, j);
        return button;
    }

    public ArrayList<DieInfo> getDieInfo(){
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
