package ingsw.view;


import ingsw.Client;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;

public class GridPaneDraftPool extends GridPane {


    private DieInfo dieInfo;
    private Client client;
    private Button b;
    private Button buttonDieSelected;
    private PlayGame playGame;


    public GridPaneDraftPool(Client client, ArrayList<String> diceList,Button buttonDieSelected, PlayGame playGame) {
        this.client = client;
        int diceThrows = client.getNumberOfPlayers()*2+1;
        this.buttonDieSelected = buttonDieSelected;
        this.playGame=playGame;
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
        }
    }

    private Button addButtonDP( int col) {
        Button button = new Button();
        button.setOpacity(1);
        action(button, 0, col);
        this.add(button, col, 0);
        return button;

    }


    private void action(Button button, int row, int col){
        button.setOnAction(e->{
            if(button.getOpacity()!=0){
                if(playGame.getChoosePressed()){
                    if(client.takeDie( row, col)){
                        dieInfo = new DieInfo(button.getBackground(), row, col);
                        buttonDieSelected.setBackground(button.getBackground());
                        buttonDieSelected.setOpacity(1);
                    }else{
                        Toolkit.getDefaultToolkit().beep();
                    }
                }else if(playGame.getUsingTool()){
                    switch (playGame.getCardSelected()){
                        case 1 :{
                            client.takeDie(row,col);
                            changeValueDie(button, row, col, 1);
                            break;
                        }
                        case 5 :{
                            if(client.takeDie(row,col)){
                                playGame.getGridRound().setAccessRound(true);
                                dieInfo = new DieInfo(button.getBackground(), row, col);
                                buttonDieSelected.setBackground(button.getBackground());
                                buttonDieSelected.setOpacity(1);
                            }
                            break;
                        }
                        case 6 :{
                            if(client.takeDie(row,col)){
                                ToolView toolView = new ToolView();
                                toolView.setStartRow1(row);
                                toolView.setStartCol1(col);
                                if(client.useToolCard(6,toolView)){
                                    updateDP(client.updateView().getDraftPoolDice());
                                    dieInfo = new DieInfo(button.getBackground(), row, col);
                                    buttonDieSelected.setBackground(button.getBackground());
                                    buttonDieSelected.setOpacity(1);
                                    playGame.setUsingTool(false);
                                    playGame.setCardSelected(0);
                                }
                            }
                            break;
                        }
                        /*case 8 :{
                            if(client.takeDie(row,col)){
                                dieInfo = new DieInfo(button.getBackground(), row, col);
                                buttonDieSelected.setBackground(button.getBackground());
                                buttonDieSelected.setOpacity(1);
                            }
                            break;
                        }*/
                        case 9 :{
                            if(client.takeDie(row,col)){
                                playGame.getGridWindow().setAccessWindow(true);
                                dieInfo = new DieInfo(button.getBackground(), row, col);
                                buttonDieSelected.setBackground(button.getBackground());
                                buttonDieSelected.setOpacity(1);
                            }
                            break;
                        }
                        case 10 :{
                            if(client.takeDie(row,col)) {
                                ToolView toolView = new ToolView();
                                toolView.setStartRow1(row);
                                toolView.setStartCol1(col);
                                if (client.useToolCard(10, toolView)) {
                                    updateDP(client.updateView().getDraftPoolDice());
                                    dieInfo = new DieInfo(button.getBackground(), row, col);
                                    buttonDieSelected.setBackground(button.getBackground());
                                    buttonDieSelected.setOpacity(1);
                                    playGame.setUsingTool(false);
                                    playGame.setCardSelected(0);
                                }
                            }
                            break;
                        }
                        case 11 :{
                            if(client.takeDie(row,col)) {
                                dieInfo = new DieInfo(null, row, col);
                                ToolView toolView = new ToolView();
                                toolView.setStartRow1(row);
                                toolView.setStartCol1(col);
                                toolView.setPhase(0);
                                if (client.useToolCard(11, toolView)) {
                                    updateDP(client.updateView().getDraftPoolDice());
                                    buttonDieSelected.setBackground(button.getBackground());
                                    buttonDieSelected.setOpacity(1);
                                    changeValueDie(button, row, col, 11);
                                }
                            }
                            break;
                        }
                        default:
                            break;
                    }
                }else{
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        });
    }

    private void changeValueDie(Button b, int row, int col, int card) {
        Stage stage = new Stage();
        GridPane root = new GridPane();
        root.setPadding(new Insets(20, 10, 20, 10));
        Scene scene = new Scene(root, 400, 100);
        root.setHgap(20);
        root.setVgap(20);
        Label label = new Label("Choose new value : ");
        root.add(label, 0, 0);
        final Spinner<Integer> spinner = new Spinner<>();
        final String path = b.getBackground().getImages().get(0).getImage().impl_getUrl();
        final int value = Integer.parseInt(path.substring(path.lastIndexOf("/")+1, path.lastIndexOf("/")+2));
        if (card == 1) {
            if(value<=6 && value >=1){
                if(value ==6){
                    SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(value-1, value, value);
                    spinner.setValueFactory(valueFactory);
                }
                else if(value==1){
                    SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(value, value+1, value);
                    spinner.setValueFactory(valueFactory);
                }
                else {
                    SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(value-1, value+1, value);
                    spinner.setValueFactory(valueFactory);
                }

            }
        }else if(card == 11){
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 6, value);
            spinner.setValueFactory(valueFactory);
        }else
            Toolkit.getDefaultToolkit().beep();

        root.add(spinner, 1, 0);
        Button button = new Button("Ok");
        root.add(button, 1, 1);
        ToolView toolView = new ToolView();
        button.setOnAction(event -> {
            if(card == 1){
                if(value!=spinner.getValue()){
                    toolView.setDieModified(spinner.getValue());
                    toolView.setStartRow1(row);
                    toolView.setStartCol1(col);
                    if(client.useToolCard(1,toolView)){
                        updateDP(client.updateView().getDraftPoolDice());
                        dieInfo = new DieInfo(b.getBackground(), row, col);
                        buttonDieSelected.setBackground(b.getBackground());
                        buttonDieSelected.setOpacity(1);
                        playGame.setUsingTool(false);
                        playGame.setCardSelected(0);
                    }
                    stage.close();
                }
                else {
                    Toolkit.getDefaultToolkit().beep();
                }
            }else if(card == 11){
                toolView.setDieModified(spinner.getValue());
                toolView.setPhase(1);
                if(client.useToolCard(11, toolView)){
                    updateDP(client.updateView().getDraftPoolDice());
                    buttonDieSelected.setBackground(getButton(row, col).getBackground());
                    buttonDieSelected.setOpacity(1);
                    playGame.getGridWindow().setAccessWindow(true);
                    client.takeDie(row, col);
                }
                stage.close();
            }else
                Toolkit.getDefaultToolkit().beep();
        });
        if(card == 1)
            stage.setTitle("Use tool 1");
        else
            stage.setTitle("Use tool 11");
        stage.setScene(scene);
        stage.show();
    }

    public void deselectBtn(){
        buttonDieSelected.setOpacity(0);
    }

    public Button getButton(int row, int col){
        for(Node node : this.getChildren()){
            if(GridPaneDraftPool.getRowIndex(node)==row && GridPaneDraftPool.getColumnIndex(node)== col){
                return (Button)node;
            }
        }
        return null;
    }

    public DieInfo getDieInfo(){
        return dieInfo;
    }

    public Button getButtonDieSelected() {
        return buttonDieSelected;
    }

    public void updateDP(ArrayList<String> draftPoolDice) {
        for(int i=0; i<draftPoolDice.size();i++){
            Button b = getButton(0,i);
            String numDie = draftPoolDice.get(i).substring(draftPoolDice.get(i).indexOf("(")+1,draftPoolDice.get(i).indexOf(","));
            String colorDie = draftPoolDice.get(i).substring(draftPoolDice.get(i).indexOf(",")+1, draftPoolDice.get(i).indexOf(")"));
            String pathDie = WPRendering.path(numDie, colorDie);
            if(pathDie.equalsIgnoreCase("/white.png"))
                b.setOpacity(0);
            else{
                b.setOpacity(1);
                Image myImage = new Image(pathDie, 58, 58, false, true);
                BackgroundImage myBI= new BackgroundImage(myImage,
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                        BackgroundSize.DEFAULT);
                b.setBackground(new Background(myBI));
            }
        }
    }
}