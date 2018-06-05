package ingsw.view;

import ingsw.Client;
import ingsw.model.ViewWP;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class GridPaneWEnemy extends GridPane {

    private Client client;

    public GridPaneWEnemy(Client client, int index) {
        setLayoutX(200);
        setLayoutY(50);

        this.client=client;

        String number;
        String color;
        ViewWP wp=client.getPlayerWPs(client.getName()).get(index);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                Button btnDie = addButtonWP(i, j);
                btnDie.setPrefSize(40, 40);
                String dieStr = wp.getWp()[i][j].getDie();
                if(dieStr==null){
                    number = Integer.toString(wp.getWp()[i][j].getNum());
                    color = String.valueOf(wp.getWp()[i][j].getColor());
                }else{
                    number =dieStr.substring(dieStr.indexOf("(")+1, dieStr.indexOf(","));
                    color = dieStr.substring(dieStr.indexOf(",")+1, dieStr.indexOf(")"));
                }
                String path = WPRendering.path(number, color);
                Image myImage = new Image(path, 40, 40, false, true);
                BackgroundImage myBI = new BackgroundImage(myImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
                btnDie.setBackground(new Background(myBI));

            }
        }

    }

    private Button addButtonWP( int row, int col) {
        Button button = new Button();
        button.setOpacity(1);
        add(button, col, row);
        return button;

    }



    public void updateWindow(ViewWP viewWP) {
        if(client.getWP(client.getName()).getNumberWP()!=viewWP.getNumberWP()){
            for (int i = 0; i < 4; i++) {
                for(int j =0;j<5;j++){
                    Button b = getButton(i,j);
                    String dieStr = viewWP.getWp()[i][j].getDie();
                    if(dieStr==null){
                        if(b==null){
                            b= new Button();
                        }
                        b.setOpacity(0);
                    }else{
                        String numDie = dieStr.substring(dieStr.indexOf("(")+1,dieStr.indexOf(","));
                        String colorDie = dieStr.substring(dieStr.indexOf(",")+1, dieStr.indexOf(")"));
                        String pathDie = WPRendering.path(numDie, colorDie);
                        if(pathDie.equalsIgnoreCase("/white.png")){
                            if(b==null){
                                b= new Button();
                            }
                            b.setOpacity(0);
                        }else{
                            if(b==null){
                                b= new Button();
                            }
                            b.setOpacity(1);
                            Image myImage = new Image(pathDie, 40, 40, false, true);
                            BackgroundImage myBI= new BackgroundImage(myImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                                    BackgroundSize.DEFAULT);
                            b.setBackground(new Background(myBI));
                        }
                    }
                }
            }
        }
    }

    private Button getButton(int row, int col){
        for(Node node : this.getChildren()){
            if(GridPaneWEnemy.getRowIndex(node)==row && GridPaneWEnemy.getColumnIndex(node)== col){
                return (Button)node;
            }
        }
        return null;
    }

}
