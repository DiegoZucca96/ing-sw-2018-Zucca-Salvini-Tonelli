package ingsw.view;

import ingsw.Client;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;

public class GridPaneRound extends GridPane {
    private Client client;
    private ArrayList<String> roundTrack;
    private int round;
    private boolean toolUsed=false;
    private GridPane gridOfdDice;
    private int significantRound;

    public GridPaneRound(Client client, ArrayList<String> roundTrack, int round){
        this.client=client;
        this.roundTrack=roundTrack;
        this.round=round;
        this.setHgap(15);
        this.setVgap(0);
        this.setPadding(new Insets(0, 20, 0, 20));

        for( int i=1; i<=10; i++){
            Button btnRound=buttonRound(i);
            btnRound.setOpacity(0.6);
        }
    }

    private Button buttonRound(int currentRound){
        Button button;
        button = new Button(Integer.toString(currentRound));
        button.setFont(new Font("Tahoma", 20));
        this.add(button, currentRound-1, 0);
        return button;
    }

    public void updateRound(ArrayList<String> roundTrack, int round){
        this.roundTrack = roundTrack;
        this.significantRound=round;
        round--;                                           //allineamento con la griglia
        if(round > 0)
            updateButton(round -1);         //aggiorno il round precedente
    }

    private void updateButton(int currentRound) {
        for(Node node : getChildren()){
            if(GridPaneRound.getColumnIndex(node) == currentRound){
                Button button = (Button) node;
                button.setText("");
                String imagePath = "/ok.png";
                Image image = new Image(imagePath, 25, 25, false, true);
                BackgroundImage back = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                        BackgroundSize.DEFAULT);
                button.setBackground(new Background(back));

                button.setOnAction(e-> {
                    if(currentRound+1<=significantRound){
                        Stage diceExtraGrid = diceRoundTrack(currentRound+1);
                        diceExtraGrid.setTitle("Round: "+(currentRound+1));
                        diceExtraGrid.show();
                    }else{
                        Toolkit.getDefaultToolkit().beep();
                    }
                });
            }
        }
    }


    private Stage diceRoundTrack(int i){
        Stage stage = new Stage();
        Pane root = new Pane();
        Scene scene = new Scene(root);
        root.setPrefSize((client.getNumberOfPlayers()*2+1)*70, 70);
        gridOfdDice = new GridPane();
        gridOfdDice.setHgap(20);
        //DieInfo client.getDieFromRoundTrack(j);

        ArrayList<String> currentDice = new ArrayList<>();
        for(String s : this.roundTrack){
            if(s.substring(0,1).equalsIgnoreCase(Integer.toString(i)))
                currentDice.add(s);
        }

        for(int numDice=0; numDice<client.getNumberOfPlayers()*2+1; numDice++){
            Button button = addDieBtn(numDice);
            button.setPrefSize(40, 40);
            if(currentDice.size()>numDice){
                String dieStr = currentDice.get(numDice);
                String numDie = dieStr.substring(dieStr.indexOf("(")+1,dieStr.indexOf(","));
                String colorDie = dieStr.substring(dieStr.indexOf(",")+1,dieStr.indexOf(")"));
                String pathDie = WPRendering.path(numDie, colorDie);
                Image myImage = new Image(pathDie, 40, 40, false, true);
                BackgroundImage myBI= new BackgroundImage(myImage,
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                        BackgroundSize.DEFAULT);
                button.setBackground(new Background(myBI));

                gridOfdDice.add(button, numDice, 0);
            }
            else{
                numDice=client.getNumberOfPlayers()*2+1;
            }
        }
        root.getChildren().add(gridOfdDice);
        stage.setScene(scene);
        return stage;
    }

    private Button addDieBtn(int numDice) {
        //this.toolUsed = client.useToolCard();
        Button button = new Button();
        button.setOnAction(e -> {
            if(!button.getBackground().equals(Color.TRANSPARENT) /*&& toolUsed*/){
                //client.takeRTDie();
            }else
                Toolkit.getDefaultToolkit().beep();

        });
        this.add(button, numDice, 0);
        return button;
    }
}