package ingsw.view;


import ingsw.Client;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;

public class GridPaneDraftPool extends GridPane {


    private DieInfo dieInfo;
    private Client client;
    private Button b;


    public GridPaneDraftPool(Client client, ArrayList<String> diceList) {
        this.client = client;
        int diceThrows = client.getNumberOfPlayers()*2+1;

        this.setHgap(10);
        this.setVgap(10);

        for (int col = 0; col < diceThrows; col++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setHgrow(Priority.SOMETIMES);
            this.getColumnConstraints().add(colConstraints);
        }



        for(int col = 0; col < diceThrows; col++){
            b = addButtonDP(col);
            b.setPrefSize(58, 58);
            String numDie = diceList.get(col).substring(diceList.get(col).indexOf("(")+1,diceList.get(col).indexOf(","));
            String colorDie = diceList.get(col).substring(diceList.get(col).indexOf(",")+1, diceList.get(col).indexOf(")"));
            String pathDie = WPRendering.path(numDie, colorDie);
            Image myImage = new Image(pathDie, 58, 58, false, true);
            BackgroundImage myBI= new BackgroundImage(myImage,
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT);
            b.setBackground(new Background(myBI));
            /*if (client.getPlayerState().equalsIgnoreCase("disabled")) {
                b.setDisable(true);
            } else b.setDisable(false);*/
        }
    }

    private Button addButtonDP( int col) {
        Button button = new Button();
        button.setOpacity(1);
        button.setStyle("-fx-border-color: black");
        button.setOnAction(e -> {

            if(button.getOpacity()!=0){
                if(PlayGame.getChoosePressed()){
                    if(client.takeDie( 0, col)){
                        dieInfo = new DieInfo(button.getBackground(), 0, col);
                        button.setStyle("-fx-border-style: solid; -fx-border-color: orange; -fx-border-width: 3");
                        setDieInfo(dieInfo);
                    }else{
                        Toolkit.getDefaultToolkit().beep();
                    }
                }else{
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        });

        this.add(button, col, 0);
        return button;

    }

    public void deselectBtn(int row, int col){
        for(Node b : this.getChildren()){
            if(GridPaneDraftPool.getRowIndex(b).intValue()==row && GridPaneDraftPool.getColumnIndex(b).intValue()== col){
                Button button = new Button();
                button.setBackground(getDieInfo().getBackground());
                button.setStyle("-fx-border-color: black");
                getChildren().remove(b);
                button.setPrefSize(58, 58);
                button.setOpacity(1);
                add(button,col, 0);
            }
        }
    }

    public Button getButton(int row, int col){
        for(Node b : this.getChildren()){
            if(GridPaneDraftPool.getRowIndex(b).intValue()==row && GridPaneDraftPool.getColumnIndex(b).intValue()== col){
                return (Button)b;
            }
        }
        return null;
    }


    public DieInfo getDieInfo(){
        return dieInfo;
    }

    public void setDieInfo(DieInfo dieInfo) {
        this.dieInfo = dieInfo;
    }
}