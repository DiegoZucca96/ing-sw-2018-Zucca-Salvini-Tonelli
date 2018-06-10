package ingsw.view;

import ingsw.Client;
import ingsw.model.ViewWP;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

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

    public GridPaneWindow( ViewWP myWindow, Client client, GridPaneDraftPool draftPool, PlayGame playGame) {

        this.client=client;
        this.draftPool=draftPool;
        this.playGame = playGame;
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
                if(client.getPlayerWPs(client.getName()).equals(myWindow)){
                    btnCell.setOpacity(0.5);
                }else{
                    String dieStr = myWindow.getWp()[i][j].getDie();
                    if(dieStr==null){
                        number = Integer.toString(myWindow.getWp()[i][j].getNum());
                        color = String.valueOf(myWindow.getWp()[i][j].getColor());
                    }else{
                        number =dieStr.substring(dieStr.indexOf("(")+1, dieStr.indexOf(","));
                        color = dieStr.substring(dieStr.indexOf(",")+1, dieStr.indexOf(")"));
                    }

                    String path = WPRendering.path(number, color);
                    Image myImage = new Image(path, 50, 50, false, true);
                    BackgroundImage myBI= new BackgroundImage(myImage,
                            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                            BackgroundSize.DEFAULT);
                    btnCell.setBackground(new Background(myBI));
                }

            }
        }
    }


    private Button addButtonWp(int i, int j) {
        Button button = new Button();

        button.setOnAction(e -> {

            if(accessWindow){
                if(playGame.getCardSelected()==9) {
                    ToolView toolView = new ToolView();
                    toolView.setStartRow1(playGame.getDraftPoolGrid().getDieInfo().getRow());
                    toolView.setStartCol1(playGame.getDraftPoolGrid().getDieInfo().getColumn());
                    toolView.setEndRow1(i);
                    toolView.setEndCol1(j);
                    button.setBackground(playGame.getDraftPoolGrid().getDieInfo().getBackground());
                    if (client.useToolCard(9, toolView)) {
                        client.nullSelection();
                        client.setInsertedDie(true);
                        //if(client.positionDie(toolView.getStartRow1(), toolView.getStartCol1()))
                        playGame.update(1, 1, 1);
                        playGame.getDraftPoolGrid().getButtonDieSelected().setOpacity(0);
                        button.setBackground(playGame.getDraftPoolGrid().getButton(toolView.getStartRow1(), toolView.getStartCol1()).getBackground());
                        playGame.getDraftPoolGrid().getButton(toolView.getStartRow1(), toolView.getStartCol1()).setOpacity(0);
                        playGame.onPositionWPButton();
                    }
                }
                accessWindow = false;
            }
            else if (client.positionDie(i, j)){
                addCellInfo(button.getBackground(), i, j);      //salvo il backgruond della cella
                button.setBackground(draftPool.getButtonDieSelected().getBackground());       //setto il nuovo background col dado
                draftPool.getButton(draftPool.getDieInfo().getRow(), draftPool.getDieInfo().getColumn()).setOpacity(0);
                playGame.setChoosePressed(false);
                playGame.onPositionWPButton();
                draftPool.getButtonDieSelected().setOpacity(0);
                //playGame.resetOnButton();
            }
            else{
                Toolkit.getDefaultToolkit().beep();
            }
        });
        this.add(button, j, i);
        return button;
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
}