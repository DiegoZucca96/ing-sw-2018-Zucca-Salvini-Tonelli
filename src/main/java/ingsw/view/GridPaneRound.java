package ingsw.view;

import ingsw.Client;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;

public class GridPaneRound extends GridPane {
    private Client client;
    private ArrayList<String> roundTrack;
    private int round;

    public GridPaneRound(Client client, ArrayList<String> roundTrack, int round){
        this.client=client;
        this.roundTrack=roundTrack;
        this.round=round;
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(0);
        grid.setPadding(new Insets(0, 20, 0, 20));


        for( int i=1; i<=10; i++){
            Button btnRound=buttonRound(i);
            grid.add(btnRound, i-1, 0);
        }


    }

    private Button buttonRound(int index){
        Button button;
        if(index<=round){
            final ImageView imageOk = new ImageView();
            String imagePath = "/ok.png";
            Image image = new Image(imagePath, 20, 20, false, false);
            imageOk.setImage(image);
            button = new Button("", imageOk);
        }else{
            button= new Button(Integer.toString(index));
            button.setFont(new Font("Tahoma", 20));
        }
        button.setOpacity(0.6);
        button.setOnAction(e-> {
            if(index<=round){
                diceRoundTrack(index);
            }else{
                Toolkit.getDefaultToolkit().beep();
            }
        });
        return button;
    }



    private Stage diceRoundTrack(int i){
        Stage stage = new Stage();
        Pane root = new Pane();
        Scene scene = new Scene(root);
        root.setPrefSize(client.getNumberOfPlayers()*70, 100);
        GridPane grid = new GridPane();
        grid.setHgap(20);
        //DieInfo client.getDieFromRoundTrack(j);

        for(int numDice=0; numDice<client.getNumberOfPlayers()*2+1; numDice++){
            Button button = addDieBtn(numDice);
            button.setPrefSize(40, 40);
            String dieStr = roundTrack.get(i);
            String numDie = dieStr.substring(dieStr.indexOf("(")+1,dieStr.indexOf(","));
            String colorDie = dieStr.substring(dieStr.indexOf(",")+1,dieStr.indexOf(")"));
            String pathDie = WPRendering.path(numDie, colorDie);
            Image myImage = new Image(pathDie, 50, 50, false, true);
            BackgroundImage myBI= new BackgroundImage(myImage,
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT);
            button.setBackground(new Background(myBI));

            grid.add(button, numDice, 0);
        }
        root.getChildren().add(grid);
        stage.setScene(scene);
        stage.show();
        return stage;
    }

    private Button addDieBtn(int numDice) {
        Button button = new Button();

        button.setOnAction(e -> {


        });
        this.add(button, numDice, 0);
        return button;

    }
}