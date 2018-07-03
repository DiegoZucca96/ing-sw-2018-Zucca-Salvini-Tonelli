package ingsw.view.gui;

import ingsw.Client;
import ingsw.model.ViewWP;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

/**
 * Author : Alessio Tonelli - Diego Zucca
 * Class dedicated to enemy's Windows*/
public class GridPaneWEnemy extends GridPane {

    private final int numberWP;
    private Client client;

    public GridPaneWEnemy(Client client, int index) {
        this.client=client;
        this.numberWP = index;
        this.setVgap(1.5);
        this.setHgap(1.5);

        for (int j = 0 ; j < 5 ; j++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setHgrow(Priority.SOMETIMES);
            this.getColumnConstraints().add(colConstraints);
        }

        for (int i = 0 ; i < 4 ; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.SOMETIMES);
            this.getRowConstraints().add(rowConstraints);
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                Button btnDie = addButtonWP(i, j);
                btnDie.setPrefSize(40, 40);
            }
        }
    }

    private Button addButtonWP( int row, int col) {
        Button button = new Button();
        button.setOpacity(0);
        add(button, col, row);
        return button;

    }

    public void updateWindow(ViewWP viewWP) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                updateButton(i, j,viewWP);
            }
        }
    }

    private void updateButton(int row, int col, ViewWP viewWP){
        for(Node node : this.getChildren()){
            if(GridPaneWEnemy.getRowIndex(node)==row && GridPaneWEnemy.getColumnIndex(node)== col){
                String dieStr = viewWP.getWp()[row][col].getDie();
                Button button = (Button) node;
                if(dieStr==null){
                    button.setOpacity(0);
                }else{
                    String numDie = dieStr.substring(dieStr.indexOf("(")+1,dieStr.indexOf(","));
                    String colorDie = dieStr.substring(dieStr.indexOf(",")+1, dieStr.indexOf(")"));
                    String pathDie = WPRendering.path(numDie, colorDie);
                    button.setOpacity(1);
                    Image myImage = new Image(pathDie, 40, 40, false, true);
                    BackgroundImage myBI= new BackgroundImage(myImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                            BackgroundSize.DEFAULT);
                    button.setBackground(new Background(myBI));
                }
            }
        }
    }

    public int getNumberWP() {
        return numberWP;
    }

}
