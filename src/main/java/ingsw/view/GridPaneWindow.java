package ingsw.view;

import ingsw.Client;
import ingsw.model.ViewWP;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;

public class GridPaneWindow extends GridPane {

    private ArrayList<DieInfo> dieInfos = new ArrayList<>();
    private ArrayList<CellInfo> cellInfos= new ArrayList<>();
    private Client client;
    private GridPaneDraftPool draftPool;
    private static boolean accessWindow = false;
    private PlayGame playGame;
    String number;
    String color;
    private boolean firstChoice = true;
    private ToolView toolView ;
    private ViewWP myWindow;

    public GridPaneWindow( ViewWP myWindow, Client client, GridPaneDraftPool draftPool, PlayGame playGame) {

        this.client=client;
        this.draftPool=draftPool;
        this.playGame = playGame;
        this.toolView = null;
        this.myWindow=myWindow;
        this.setHgap(3);
        this.setVgap(3);
        int numCols = 5 ;
        int numRows = 4 ;

        for (int j = 0 ; j < numCols ; j++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setHgrow(Priority.SOMETIMES);
            this.getColumnConstraints().add(colConstraints);
        }

        for (int i = 0 ; i < numRows ; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.SOMETIMES);
            this.getRowConstraints().add(rowConstraints);
        }

        for (int i = 0 ; i < numRows ; i++) {
            for (int j = 0; j < numCols; j++) {
                Button btnCell = addButtonWp(i, j);
                btnCell.setPrefSize(50, 50);
                btnCell.setOpacity(0);
            }
        }
    }


    private Button addButtonWp(int i, int j) {
        Button button = new Button();

        button.setOnAction(e -> {
            if(accessWindow){
                if(playGame.getCardSelected()==9) {
                    toolView = new ToolView();
                    toolView.setStartRow1(playGame.getDraftPoolGrid().getDieInfo().getRow());
                    toolView.setStartCol1(playGame.getDraftPoolGrid().getDieInfo().getColumn());
                    toolView.setEndRow1(i);
                    toolView.setEndCol1(j);
                    button.setBackground(playGame.getDraftPoolGrid().getDieInfo().getBackground());
                    if (client.useToolCard(9, toolView)) {
                        client.nullSelection();
                        client.setInsertedDie(true);
                        playGame.update();
                        playGame.getDraftPoolGrid().getButtonDieSelected().setOpacity(0);
                        button.setBackground(playGame.getDraftPoolGrid().getButton(toolView.getStartRow1(), toolView.getStartCol1()).getBackground());
                        playGame.getDraftPoolGrid().getButton(toolView.getStartRow1(), toolView.getStartCol1()).setOpacity(0);
                        playGame.onPositionWPButton();
                        playGame.setUsingTool(false);
                        playGame.setCardSelected(0);
                    }else{
                        Toolkit.getDefaultToolkit().beep();
                    }
                    toolView = null;
                    setAccessWindow(false);
                    button.setOpacity(1);
                }else if(playGame.getCardSelected()==2){
                    if(toolView==null)
                        toolView = new ToolView();
                    if(firstChoice){
                        toolView.setStartRow1(i);
                        toolView.setStartCol1(j);
                        addDieInfo(button.getBackground(), i, j);
                        if(client.takeWPDie(i, j))
                            firstChoice = false;
                        playGame.getDraftPoolGrid().getButtonDieSelected().setBackground(button.getBackground());
                        playGame.getDraftPoolGrid().getButtonDieSelected().setOpacity(1);
                    }else{
                        toolView.setEndRow1(i);
                        toolView.setEndCol1(j);
                        if(client.useToolCard(2, toolView)){
                            playGame.update();
                            updateMyself();
                            getButton(getDieInfos().get(0).getRow(), getDieInfos().get(0).getColumn()).setOpacity(0);
                            playGame.getDraftPoolGrid().getButtonDieSelected().setOpacity(0);
                            client.nullSelection();
                            firstChoice = true;
                            setAccessWindow(false);
                            toolView = null;
                            playGame.onPositionWPButton();
                            playGame.setUsingTool(false);
                            playGame.setCardSelected(0);
                            if(!client.getInsertedDie())
                                playGame.resetOnButton();
                        }else{
                            Toolkit.getDefaultToolkit().beep();
                        }
                    }
                }else if(playGame.getCardSelected()==3){
                    if(toolView==null)
                        toolView = new ToolView();
                    if(firstChoice){
                        toolView.setStartRow1(i);
                        toolView.setStartCol1(j);
                        addDieInfo(button.getBackground(), i, j);
                        if(client.takeWPDie(i, j))
                            firstChoice = false;
                        playGame.getDraftPoolGrid().getButtonDieSelected().setBackground(button.getBackground());
                        playGame.getDraftPoolGrid().getButtonDieSelected().setOpacity(1);
                    }else{
                        toolView.setEndRow1(i);
                        toolView.setEndCol1(j);
                        if(client.useToolCard(3, toolView)){
                            playGame.update();
                            updateMyself();
                            playGame.getDraftPoolGrid().getButtonDieSelected().setOpacity(0);
                            client.nullSelection();
                            firstChoice = true;
                            setAccessWindow(false);
                            toolView = null;
                            playGame.onPositionWPButton();
                            playGame.setUsingTool(false);
                            playGame.setCardSelected(0);
                            if(!client.getInsertedDie())
                                playGame.resetOnButton();
                        }else{
                            Toolkit.getDefaultToolkit().beep();
                        }
                    }
                }else if(playGame.getCardSelected()==4){
                    if (toolView == null) {
                        toolView = new ToolView();
                        toolView.setPhase(0);
                    }
                    if(firstChoice){
                        if(toolView.getPhase()==0){
                            toolView.setStartRow1(i);
                            toolView.setStartCol1(j);
                            if(client.takeWPDie(i,j)){
                                playGame.getDraftPoolGrid().getButtonDieSelected().setBackground(button.getBackground());
                                playGame.getDraftPoolGrid().getButtonDieSelected().setOpacity(1);
                            }
                            toolView.setPhase(1);
                        }else{
                            toolView.setEndRow1(i);
                            toolView.setEndCol1(j);
                            firstChoice=false;
                            toolView.setPhase(0);
                            if(client.useToolCard(4,toolView)){
                                updateMyself();
                                playGame.getDraftPoolGrid().getButtonDieSelected().setOpacity(0);
                                client.nullSelection();
                            }else{
                                firstChoice=true;
                                toolView.setPhase(1);
                                Toolkit.getDefaultToolkit().beep();
                            }
                        }
                    }else{
                        if(toolView.getPhase()==0){
                            toolView.setStartRow2(i);
                            toolView.setStartCol2(j);
                            if(client.takeWPDie(i,j)){
                                playGame.getDraftPoolGrid().getButtonDieSelected().setBackground(button.getBackground());
                                playGame.getDraftPoolGrid().getButtonDieSelected().setOpacity(1);
                            }
                            toolView.setPhase(1);
                        }else{
                            toolView.setEndRow2(i);
                            toolView.setEndCol2(j);
                            if(client.useToolCard(4,toolView)){
                                updateMyself();
                                playGame.update();
                                playGame.getDraftPoolGrid().getButtonDieSelected().setOpacity(0);
                                client.nullSelection();
                                firstChoice = true;
                                setAccessWindow(false);
                                toolView = null;
                                playGame.onPositionWPButton();
                                playGame.setUsingTool(false);
                                playGame.setCardSelected(0);
                                if(!client.getInsertedDie())
                                    playGame.resetOnButton();
                            }else{
                                firstChoice=true;
                                toolView.setPhase(0);
                                updateMyself();
                                playGame.update();
                                playGame.getDraftPoolGrid().getButtonDieSelected().setOpacity(0);
                                client.nullSelection();
                                Toolkit.getDefaultToolkit().beep();
                                new Warning("Posizione errata, riprova", "Tool 4");
                            }
                        }
                    }
                }else if(playGame.getCardSelected() == 11){
                    toolView = new ToolView();
                    toolView.setStartRow1(i);
                    toolView.setStartCol1(j);
                    toolView.setPhase(2);
                    if(client.useToolCard(11, toolView)){
                        playGame.getDraftPoolGrid().getButtonDieSelected().setOpacity(0);
                        updateMyself();
                        client.nullSelection();
                        playGame.update();
                        DieInfo dieInfo = playGame.getDraftPoolGrid().getDieInfo();
                        Button button1 =  playGame.getDraftPoolGrid().getButton(dieInfo.getRow(), dieInfo.getColumn());
                        button1.setOpacity(0);
                        client.setInsertedDie(true);
                        playGame.onPositionWPButton();
                        playGame.setUsingTool(false);
                        playGame.setCardSelected(0);
                    }else{
                        Toolkit.getDefaultToolkit().beep();
                        client.nullSelection();
                        new Warning("Posizione errata, riprova", "Tool 11");
                    }
                }else if(playGame.getCardSelected()==12){
                    if (toolView == null) {
                        toolView = new ToolView();
                        toolView.setPhase(1);
                    }
                    if(toolView.getPhase()==1){
                        if(firstChoice){
                            toolView.setStartRow1(i);
                            toolView.setStartCol1(j);
                            if(client.takeWPDie(i,j)){
                                playGame.getDraftPoolGrid().getButtonDieSelected().setBackground(button.getBackground());
                                playGame.getDraftPoolGrid().getButtonDieSelected().setOpacity(1);
                                firstChoice=false;
                            }
                        }else{
                            toolView.setEndRow1(i);
                            toolView.setEndCol1(j);
                            if(client.useToolCard(12,toolView)){
                                updateMyself();
                                playGame.getDraftPoolGrid().getButtonDieSelected().setOpacity(0);
                                client.nullSelection();
                                firstChoice = true;
                                makeAnotherMove();
                            }else{
                                playGame.getDraftPoolGrid().getButtonDieSelected().setOpacity(0);
                                client.nullSelection();
                                firstChoice = true;
                                Toolkit.getDefaultToolkit().beep();
                                new Warning("Colore del dado scelto sbagliato oppure\n\n posizione di destinazione invalida, riprova","Use tool 12");
                            }
                        }
                    }else{
                        if(firstChoice){
                            toolView.setStartRow2(i);
                            toolView.setStartCol2(j);
                            if(client.takeWPDie(i,j)){
                                playGame.getDraftPoolGrid().getButtonDieSelected().setBackground(button.getBackground());
                                playGame.getDraftPoolGrid().getButtonDieSelected().setOpacity(1);
                                firstChoice = false;
                            }
                        }else{
                            toolView.setEndRow2(i);
                            toolView.setEndCol2(j);
                            if(client.useToolCard(12,toolView)){
                                updateMyself();
                                playGame.getDraftPoolGrid().getButtonDieSelected().setOpacity(0);
                                client.nullSelection();
                                firstChoice = true;
                                setAccessWindow(false);
                                toolView = null;
                                playGame.update();
                                playGame.onPositionWPButton();
                                playGame.setUsingTool(false);
                                playGame.setCardSelected(0);
                                if(!client.getInsertedDie())
                                    playGame.resetOnButton();
                            }else{
                                playGame.getDraftPoolGrid().getButtonDieSelected().setOpacity(0);
                                client.nullSelection();
                                firstChoice = true;
                                Toolkit.getDefaultToolkit().beep();
                                new Warning("Colore del dado scelto sbagliato oppure\n\n posizione di destinazione invalida, riprova","Use tool 12");
                            }
                        }
                    }
                }
            }else if (client.positionDie(i, j)){
                draftPool.getButton(draftPool.getDieInfo().getRow(), draftPool.getDieInfo().getColumn()).setOpacity(0);
                playGame.setChoosePressed(false);
                playGame.onPositionWPButton();
                draftPool.getButtonDieSelected().setOpacity(0);
                updateMyself();
            }
            else{
                Toolkit.getDefaultToolkit().beep();
            }
        });
        this.add(button, j, i);
        return button;
    }

    private void makeAnotherMove() {
        Stage stage = new Stage();
        GridPane root = new GridPane();
        root.setPadding(new Insets(20, 10, 20, 10));
        Scene scene = new Scene(root, 400, 100);
        root.setHgap(20);
        root.setVgap(20);
        Label label = new Label("Vuoi spostare anche il secondo dado?");
        root.add(label, 1, 0);
        Button buttonYes = new Button("Yes");
        Button buttonNo = new Button("No");
        root.add(buttonYes, 1, 1);
        root.add(buttonNo, 2, 1);
        buttonYes.setOnAction(event -> {
            toolView.setPhase(2);
            stage.close();
        });
        buttonNo.setOnAction(event -> {
            setAccessWindow(false);
            toolView = null;
            playGame.update();
            playGame.onPositionWPButton();
            playGame.setUsingTool(false);
            playGame.setCardSelected(0);
            if(!client.getInsertedDie())
                playGame.resetOnButton();
            stage.close();
        });
        stage.setTitle("Use tool 12");
        stage.setScene(scene);
        stage.show();
    }

    private void updateMyself() {
        int index = client.getListOfPlayers().indexOf(client.getName());
        myWindow = client.updateView().getWps().get(index);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                updateButton(i, j,myWindow);
            }
        }
    }

    private void updateButton(int i, int j, ViewWP myWindow) {
        for(Node node : this.getChildren()){
            if(GridPaneWindow.getRowIndex(node)==i && GridPaneWindow.getColumnIndex(node)== j){
                String dieStr = myWindow.getWp()[i][j].getDie();
                Button button = (Button) node;
                if(dieStr==null){
                    button.setOpacity(0);
                }else{
                    String numDie = dieStr.substring(dieStr.indexOf("(")+1,dieStr.indexOf(","));
                    String colorDie = dieStr.substring(dieStr.indexOf(",")+1, dieStr.indexOf(")"));
                    String pathDie = WPRendering.path(numDie, colorDie);
                    button.setOpacity(1);
                    Image myImage = new Image(pathDie, 50, 50, false, true);
                    BackgroundImage myBI= new BackgroundImage(myImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                            BackgroundSize.DEFAULT);
                    button.setBackground(new Background(myBI));
                }
            }
        }
    }


    public ArrayList<DieInfo> getDieInfos(){
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

    public void setAccessWindow(boolean accessWindow) {
        this.accessWindow= accessWindow;
    }

    public void setFirstChoice(boolean b) {
        this.firstChoice = b;
    }

    public Button getButton(int row, int col){
        for(Node node : this.getChildren()){
            if(GridPaneDraftPool.getRowIndex(node)==row && GridPaneDraftPool.getColumnIndex(node)== col){
                return (Button)node;
            }
        }
        return null;
    }

    public void abortToolView() {
        this.toolView = null;
    }
}