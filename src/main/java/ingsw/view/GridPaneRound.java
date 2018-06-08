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
    private static boolean accessRound = false;
    private PlayGame playGame;
    private DieInfo dieInfo = new DieInfo(null,-1,-1);
    private Stage stage;

    public GridPaneRound(Client client, ArrayList<String> roundTrack, int round, PlayGame playGame){
        this.client=client;
        this.roundTrack=roundTrack;
        this.round=round;
        this.playGame=playGame;
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
                        diceRoundTrack(currentRound+1);
                    }else{
                        Toolkit.getDefaultToolkit().beep();
                    }
                });
            }
        }
    }


    private void diceRoundTrack(int i){
        stage = new Stage();
        Pane root = new Pane();
        Scene scene = new Scene(root);
        root.setPrefSize((client.getNumberOfPlayers()*2+1)*70, 80);
        gridOfdDice = new GridPane();
        gridOfdDice.setHgap(20);
        //DieInfo client.getDieFromRoundTrack(j);

        ArrayList<String> currentDice = new ArrayList<>();
        for(String s : this.roundTrack){
            if(s.substring(0,1).equalsIgnoreCase(Integer.toString(i)))
                currentDice.add(s);
        }

        for(int numDice=0; numDice<client.getNumberOfPlayers()*2+1; numDice++){
            Button button = addDieBtn(numDice,i);
            button.setPrefSize(58, 58);
            if(currentDice.size()>numDice){
                String dieStr = currentDice.get(numDice);
                String numDie = dieStr.substring(dieStr.indexOf("(")+1,dieStr.indexOf(","));
                String colorDie = dieStr.substring(dieStr.indexOf(",")+1,dieStr.indexOf(")"));
                String pathDie = WPRendering.path(numDie, colorDie);
                Image myImage = new Image(pathDie, 58, 58, false, true);
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
        stage.setTitle("Round: "+i);
        stage.show();
    }

    private Button addDieBtn(int numDice,int i) {
        Button button = new Button();
        button.setOnAction(e -> {
            playGame.update(0,0,1);
            if(!button.getBackground().equals(Color.TRANSPARENT) && accessRound){
                if(PlayGame.getCardSelected()==5){
                    ToolView toolView = new ToolView();
                    toolView.setStartRow1(playGame.getDraftPoolGrid().getDieInfo().getRow());
                    toolView.setStartCol1(playGame.getDraftPoolGrid().getDieInfo().getColumn());
                    toolView.setEndRow1(0);
                    toolView.setRound(i);
                    toolView.setEndCol1(numDice);
                    dieInfo.setBackground(button.getBackground());
                    dieInfo.setRow(0);
                    dieInfo.setColumn(numDice);
                    if(client.useToolCard(5,toolView)){
                        client.nullSelection();
                        client.takeDie(toolView.getStartRow1(),toolView.getStartCol1());
                        playGame.update(0,1,1);
                        playGame.getDraftPoolGrid().getButtonDieSelected().setBackground(button.getBackground());
                        playGame.getDraftPoolGrid().getButton(toolView.getStartRow1(),toolView.getStartCol1()).setBackground(button.getBackground());
                        playGame.getDraftPoolGrid().getButton(toolView.getStartRow1(),toolView.getStartCol1()).setOpacity(1);
                        playGame.getDraftPoolGrid().getButtonDieSelected().setOpacity(1);
                    }
                }
                else{
                    //da impl
                }
                accessRound = false;
                stage.close();
            }else
                Toolkit.getDefaultToolkit().beep();

        });
        this.add(button, numDice, 0);
        return button;
    }

    public static void setAccessRound(boolean accessRound) {
        GridPaneRound.accessRound = accessRound;
    }


}