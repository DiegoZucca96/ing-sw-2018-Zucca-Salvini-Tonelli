package ingsw.view;


import ingsw.Client;
import ingsw.model.ViewWP;
import ingsw.model.ViewData;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class PlayGame {

    private Label clockLbl;
    private int timeseconds;
    private int timeBegin;
    private Timeline timeline;
    private Client client;
    private ViewData init ;
    private static Button skipBtn;
    private static Button useToolBtn;
    private static Button takeDieBtn;
    private static Button exitBtn;
    private static Button infoBtn;
    private static Button cancelBtn;
    private GridPane gridRound;
    private GridPane toolGrid;
    private GridPane pbGrid;
    private GridPaneWindow myWindowGrid;
    private GridPaneDraftPool draftPoolGrid;
    private static final String styleSheet = "-fx-text-fill: goldenrod; -fx-font: italic 15 \"serif\"";
    private ViewData updateView;
    private GridPane gridEn;
    private static int begin=0;
    private static boolean choosePressed=false;


    public PlayGame(Client client){
        this.client=client;
        init= null;
    }




    public void display(ViewWP myWindow){
        Stage stage=new Stage();
        stage.setWidth(1200);
        stage.setHeight(700);
        Pane root = new Pane();
        Scene scene = new Scene(root);




        //IMMAGINE BACKGROUND
        final ImageView backGround = new ImageView();
        String imagePathB = "/backgroundtable.jpg";
        Image imageB = new Image(imagePathB, 1200, 700, false, false);
        backGround.setImage(imageB);



        //COUNTDOWN
        Pane currentInfo = new Pane();
        currentInfo.setLayoutX(300);
        currentInfo.setLayoutY(300);
        clockLbl= new Label();
        clockLbl.setStyle(styleSheet);
        currentInfo.getChildren().add(clockLbl);

        //INIZIALIZZAZIONE
        init = client.initializeView();
        timeBegin = client.getTimeMove();

        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(1000),
                        event -> {
                            timeseconds = client.getTimeMove();
                            if(client.getPlayerState().equals("enabled")){
                                clockLbl.setStyle("-fx-text-fill: goldenrod; -fx-font: italic 22 \"serif\"");
                                clockLbl.setText("Tocca a te    "+Integer.toString(timeseconds));
                                if (timeseconds == timeBegin) {
                                    //draftPoolGrid.setDisable(true);
                                    resetOnButton();
                                }
                                if(timeseconds==0) {
                                    if(!client.getActive()){
                                        Platform.runLater(() -> {
                                            Boolean answer= ConfirmExit.display("Disconnected", "Do you still want to play?");
                                            if(answer)
                                                client.rejoinedPlayer(client.getName());
                                            else
                                                Platform.exit();
                                        });
                                    }
                                }
                            }else {
                                clockLbl.setText("Tocca a "+client.getCurrentPlayer()+"      "+Integer.toString(timeseconds));
                                if (timeseconds == timeBegin) {
                                    //draftPoolGrid.setDisable(true);
                                    resetOffButton();
                                }
                                if(timeseconds%5==0){
                                    this.updateView = client.updateView();

                                    for(int i =0; i<client.getNumberOfPlayers(); i++){
                                        if(updateView.getWps().get(i)!=null)
                                            gridEn = createGridEn(i);
                                    }

                                    if(updateView.getRoundTrack()!=null)
                                        gridRound=new GridPaneRound(client, updateView.getRoundTrack(), client.getRound());
                                    if(updateView.getDraftPoolDice()!=null){
                                        draftPoolGrid = new GridPaneDraftPool(client, updateView.getDraftPoolDice());
                                    }
                                }

                            }
                        }));
        timeline.playFromStart();


        //ROUNDTRACK
        gridRound= new GridPaneRound(client, init.getRoundTrack(), 1);
        gridRound.setLayoutY(10);
        gridRound.setLayoutX(336);


        //PRIVATE
        Pane pvPane = pvPane(client.getPVCard(client.getName()));          //come associo a quella giusta
        pvPane.setLayoutX(800);
        pvPane.setLayoutY(350);

        //TOOLCARD
        Pane toolPane1 = Pane(init.getToolCard(), 0);
        Pane toolPane2 = Pane(init.getToolCard(), 1);
        Pane toolPane3 = Pane(init.getToolCard(), 2);

        toolGrid = new GridPane();
        toolGrid.setLayoutX(10);
        toolGrid.setLayoutY(20);
        toolGrid.setVgap(20);

        toolGrid.add(toolPane1, 0, 0);
        toolGrid.add(toolPane2, 0, 1);
        toolGrid.add(toolPane3, 0, 2);

        //PBCARD
        Pane pbPane1 = Pane(init.getPbCard(), 0);
        Pane pbPane2 = Pane(init.getPbCard(), 1);
        Pane pbPane3 = Pane(init.getPbCard(), 2);

        pbGrid = new GridPane();
        pbGrid.setLayoutX(1050);
        pbGrid.setLayoutY(20);
        pbGrid.setVgap(20);

        pbGrid.add(pbPane1, 0, 0);
        pbGrid.add(pbPane2, 0, 1);
        pbGrid.add(pbPane3, 0, 2);

        //DRAFTPOOL
        draftPoolGrid = new GridPaneDraftPool(client,init.getDraftPoolDice());
        draftPoolGrid.setLayoutX(200);
        draftPoolGrid.setLayoutY(600);

        //LA MIA WP
        myWindowGrid = new GridPaneWindow(myWindow, client, draftPoolGrid);
        myWindowGrid.setLayoutX(200);
        myWindowGrid.setLayoutY(350);


        //WP AVVERSARIE
        GridPane eachGrid = new GridPane();
        eachGrid.setHgap(40);
        eachGrid.setPrefSize(720, 200);
        eachGrid.setLayoutX(240);
        eachGrid.setLayoutY(80);
        for(int index= 0; index < client.getNumberOfPlayers()-1; index++){
            gridEn = createGridEn(index);
            eachGrid.add(gridEn, index, 0);
        }

        //BOTTONI
        GridPane btnGrid = new GridPane();
        btnGrid.setVgap(20);
        skipBtn = new Button("End Turn");
        skipBtn.setOnAction(e-> {
            if(client.getPlayerState().equalsIgnoreCase("enabled")){
                choosePressed=false;
                client.setActive(true);
                client.skip();
            }
        });
        btnGrid.add(skipBtn, 0, 0);

        useToolBtn = new Button("Use Tool");
            useToolBtn.setOnAction(e-> {
                if(client.getPlayerState().equalsIgnoreCase("enabled"))  {
                    choosePressed=true;
                    client.setActive(true);
                    skipBtn.setDisable(true);
                    useToolBtn.setDisable(false);
                }
            });

        btnGrid.add(useToolBtn, 0, 1);

        takeDieBtn = new Button("Take Die");
            takeDieBtn.setOnAction(e-> {
                if(client.getPlayerState().equalsIgnoreCase("enabled")){
                    choosePressed=true;
                    client.setActive(true);
                    draftPoolGrid.setDisable(false);
                    setBtnOnTakeDieClicked();
                }
            });

        btnGrid.add(takeDieBtn, 0, 2);

        exitBtn = new Button("Exit game");
        exitBtn.setOnAction(e-> {
            Boolean answer= ConfirmExit.display("Quit", "Are you sure to exit without saving?");
            if(answer){
                client.setActive(false);
                client.skip();
                Platform.exit();
            }
        });
        btnGrid.add(exitBtn, 0, 3);

        cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(e-> {
            if(client.getPlayerState().equalsIgnoreCase("enabled")){
                choosePressed=false;
                int col = client.getCoordinateSelectedY();
                if(col==-1){            //se non ha selezionato nulla
                    resetOnButton();
                }else{
                    client.nullSelection();
                    //draftPoolGrid.setDisable(true);
                    resetOnButton();
                    draftPoolGrid.deselectBtn(0, col);
                }
            }
        });
        btnGrid.add(cancelBtn, 0, 4);

        infoBtn = new Button("Authors");
        infoBtn.setOnAction(e-> {
            InfoStage.displayAuthor(new Stage());
        });
        btnGrid.add(infoBtn, 0, 5);
        btnGrid.setLayoutX(600);
        btnGrid.setLayoutY(350);


        //ATTACH DI TUTTO
        root.getChildren().addAll(backGround, toolGrid, pbGrid, gridRound, myWindowGrid, btnGrid, eachGrid, pvPane, draftPoolGrid, currentInfo);
        stage.setScene(scene);
        stage.setTitle("Sagrada - " +client.getName());
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.show();
    }




    private Pane pvPane(String stringPvCard){
        Pane pane = new Pane();
        pane.setLayoutY(200);
        pane.setLayoutX(130);


        final ImageView View = new ImageView();
        String imagePathT = stringPvCard;
        Image imageT = new Image(imagePathT, 130, 200, false, true);
        View.setImage(imageT);

        View.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                View.setScaleX(1.3);
                View.setScaleY(1.3);
            }
        });

        View.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                View.setScaleX(1);
                View.setScaleY(1);
            }
        });

        pane.getChildren().add(View);

        return pane;
    }

    private Pane Pane(ArrayList<String> stringCard, int i){
        Pane pane = new Pane();
        pane.setLayoutY(200);
        pane.setLayoutX(130);


        final ImageView View = new ImageView();
        String imagePathT = stringCard.get(i);
        Image imageT = new Image(imagePathT, 130, 200, false, true);
        View.setImage(imageT);

        View.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                View.setScaleX(1.3);
                View.setScaleY(1.3);
            }
        });

        View.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                View.setScaleX(1);
                View.setScaleY(1);
            }
        });

        pane.getChildren().add(View);

        return pane;
    }


    private GridPane createGridEn(int player) {
        GridPane grid = new GridPane();
        String number;
        String color;
        ViewWP wp;
        if(begin<client.getNumberOfPlayers()){
            wp= client.getPlayerWPs(client.getName()).get(player);
            begin++;
        }else
            wp = updateView.getWps().get(player);



        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                Button btnCell = new Button();
                btnCell.setPrefSize(40, 40);
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
                btnCell.setBackground(new Background(myBI));
                grid.add(btnCell, j, i);
            }
        }
        return grid;
    }

    public static boolean getChoosePressed(){
        return choosePressed;
    }

    public static void resetOnButton(){
        skipBtn.setDisable(false);
        useToolBtn.setDisable(false);
        takeDieBtn.setDisable(false);
        infoBtn.setDisable(false);
        cancelBtn.setDisable(false);
    }

    public static void resetOffButton(){
        skipBtn.setDisable(true);
        useToolBtn.setDisable(true);
        takeDieBtn.setDisable(true);
        infoBtn.setDisable(true);
        cancelBtn.setDisable(true);
    }

    public static void onPositionWPButton() {
        skipBtn.setDisable(false);
        useToolBtn.setDisable(false);
        takeDieBtn.setDisable(true);
        infoBtn.setDisable(false);
        cancelBtn.setDisable(true);
    }

    public static void setBtnOnTakeDieClicked(){
        skipBtn.setDisable(true);
        cancelBtn.setDisable(false);
        infoBtn.setDisable(true);
        useToolBtn.setDisable(true);
    }


    public static void setChoosePressed(boolean b) {
        choosePressed = b;
    }
}
