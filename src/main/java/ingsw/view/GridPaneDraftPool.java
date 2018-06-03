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


    private DieInfo dieInfo = new DieInfo(null,-1,-1);
    private Client client;
    private Button b;
    private Button buttonDieSelected;


    public GridPaneDraftPool(Client client, ArrayList<String> diceList,Button buttonDieSelected) {
        this.client = client;
        int diceThrows = client.getNumberOfPlayers()*2+1;
        this.buttonDieSelected = buttonDieSelected;

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
        action(button, 0, col);
        this.add(button, col, 0);
        return button;

    }

    public void deselectBtn(int row, int col){
        buttonDieSelected.setOpacity(0);

           /* Button x = (Button) getChildren().get(i);
            Integer rowIndex = GridPane.getRowIndex(x);
            Integer columnIndex = GridPane.getColumnIndex(x);
            if(rowIndex != null && rowIndex==row && columnIndex != null && columnIndex== col){
                Button button = new Button();
                button.setBackground(dieInfo.getBackground());
                button.setStyle("-fx-border-color: black");
                getChildren().remove(x);
                button.setPrefSize(58, 58);
                button.setOpacity(1);
                action(button, 0, col);
                add(button,col, 0);
            }*/
    }

    public Button getButton(int row, int col){
        for(Node node : this.getChildren()){
            if(GridPaneDraftPool.getRowIndex(node)==row && GridPaneDraftPool.getColumnIndex(node)== col){
                return (Button)node;
            }
        }
        return null;
    }


    private void action(Button button, int row, int col){
        button.setOnAction(e->{
            if(button.getOpacity()!=0){
                if(PlayGame.getChoosePressed()){
                    if(client.takeDie( row, col)){
                        getDieInfo().setBackground(button.getBackground());
                        getDieInfo().setColumn(col);
                        getDieInfo().setRow(row);
                        //button.setStyle("-fx-border-style: solid; -fx-border-color: orange; -fx-border-width: 3");
                        buttonDieSelected.setBackground(button.getBackground());
                        buttonDieSelected.setOpacity(1);
                    }else{
                        Toolkit.getDefaultToolkit().beep();
                    }
                }else{
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        });
    }

    public DieInfo getDieInfo(){
        return dieInfo;
    }

    public Button getButtonDieSelected() {
        return buttonDieSelected;
    }
}