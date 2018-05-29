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


    public GridPaneDraftPool(Client client, ArrayList<String> diceList) {
        this.client = client;
        int diceThrows = client.getNumberOfPlayers()*2+1;

        this.setHgap(10);
        this.setVgap(10);

        /*for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setHgrow(Priority.SOMETIMES);
            this.getColumnConstraints().add(colConstraints);
        }

        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.SOMETIMES);
            this.getRowConstraints().add(rowConstraints);
        }*/

        /*for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                b = addButtonDP(row, col);
                b.setPrefSize(58, 58);
                if (client.getPlayerState().equalsIgnoreCase("disabled")) {
                    b.setDisable(true);
                } else b.setDisable(false);

            }
*/

        for(int col = 0; col < diceThrows; col++){
            b = addButtonDP( col);
            b.setPrefSize(58, 58);
            String numDie = diceList.get(col).substring(0,diceList.get(col).indexOf(","));
            String colorDie = diceList.get(col).substring(diceList.get(col).indexOf(","));
            String pathDie = WPRendering.pathDie(numDie, colorDie);
            Image myImage = new Image(pathDie, 50, 50, false, true);
            BackgroundImage myBI= new BackgroundImage(myImage,
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT);
            b.setBackground(new Background(myBI));
            if (client.getPlayerState().equalsIgnoreCase("disabled")) {
                b.setDisable(true);
            } else b.setDisable(false);
        }
    }

    private Button addButtonDP( int col) {
        Button button = new Button();
        button.setOpacity(1);

            button.setOnMouseEntered(e -> {
                dieInfo = new DieInfo(button.getBackground(), col);

                if(!dieInfo.getBackground().equals(Color.TRANSPARENT)){
                    button.setStyle("-fx-border-color: yellow");
                    setDieInfo(dieInfo);
                    client.takeDie( 0, col);
                }else{
                    Toolkit.getDefaultToolkit().beep();
                    dieInfo=null;
                }

            });

        this.add(button, col, 0);
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