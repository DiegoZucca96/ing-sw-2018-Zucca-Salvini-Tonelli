package ingsw.view;


import ingsw.Client;
import ingsw.controller.ViewWP;
import ingsw.model.ViewData;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;

public class PlayGame {

    private Timeline timeline;
    private Client client;
    private ViewData init ;
    private Button skipBtn;
    private Button useToolBtn;
    private Button takeDieBtn;
    private Button exitBtn;
    private GridPane gridRound;
    private GridPane toolGrid;
    private GridPane pbGrid;
    private GridPane myWindowGrid;
    private GridPane draftPoolGrid;

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

        //GLIGLIA DI BOTTONI PER LA ROUNDTRACK
        gridRound=addGridRound();
        gridRound.setLayoutY(10);
        gridRound.setLayoutX(336);


        //IMMAGINE BACKGROUND
        final ImageView backGround = new ImageView();
        String imagePathB = "/backgroundtable.jpg";
        Image imageB = new Image(imagePathB, 1200, 700, false, false);
        backGround.setImage(imageB);


        init = client.initializeView();


        //COUNTDOWN
        Pane currentInfo = new Pane();
        currentInfo.setLayoutX(300);
        currentInfo.setLayoutY(300);
        Label countdownLabel = countDown();
        countdownLabel.setPrefSize(20, 20);

        currentInfo.getChildren().add(countdownLabel);

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

        //LA MIA WP
        myWindowGrid = new GridPaneWindow(2, myWindow, client);
        myWindowGrid.setLayoutX(200);
        myWindowGrid.setLayoutY(350);

        //BOTTONI
        GridPane btnGrid = new GridPane();
        btnGrid.setVgap(20);
        skipBtn = new Button("End Turn");
        if(client.getPlayerState().equalsIgnoreCase("enable")){
            skipBtn.setOnAction(e-> {
                timeseconds=0;
                client.setActive();
                toolGrid.setDisable(true);
                client.skip();
            });
        }
        btnGrid.add(skipBtn, 0, 0);

        useToolBtn = new Button("Use Tool");
        if(client.getPlayerState().equalsIgnoreCase("enable")){
            useToolBtn.setOnAction(e-> {
                client.setActive();
                skipBtn.setDisable(true);
            });
        }
        btnGrid.add(useToolBtn, 0, 1);

        takeDieBtn = new Button("Take Die");
        if(client.getPlayerState().equalsIgnoreCase("enable")){
            takeDieBtn.setOnAction(e-> {
                new Warning("Select die", "Follow me");
                client.setActive();
                toolGrid.setDisable(true);
                skipBtn.setDisable(true);
            });
        }
        btnGrid.add(takeDieBtn, 0, 2);

        exitBtn = new Button("Exit game");
        exitBtn.setOnAction(e-> {
            Boolean answer= ConfirmExit.display("Quit", "Are you sure to exit without saving?");
            if(answer)
                Platform.exit();
        });
        btnGrid.add(exitBtn, 0, 3);
        btnGrid.setLayoutX(600);
        btnGrid.setLayoutY(350);

        //DRAFTPOOL
        draftPoolGrid = new GridPaneDraftPool(client,init.getDraftPoolDice());
        draftPoolGrid.setLayoutX(200);
        draftPoolGrid.setLayoutY(600);

        //WP AVVERSARIE
        GridPane eachGrid = new GridPane();
        eachGrid.setHgap(40);
        eachGrid.setPrefSize(720, 200);
        eachGrid.setLayoutX(240);
        eachGrid.setLayoutY(80);
        for(int index= 0; index < client.getNumberOfPlayers()-1; index++){

            GridPane gridPane = createGridEn(index);
            eachGrid.add(gridPane, index, 0);
        }


        root.getChildren().addAll(backGround, toolGrid, pbGrid, gridRound, myWindowGrid, btnGrid, eachGrid, pvPane, draftPoolGrid);
        stage.setScene(scene);
        stage.setTitle("Sagrada - " +client.getName());
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.show();
    }

    private Label countDown(){
        timeline = new Timeline();
        Label clockLbl = new Label();
        timeseconds = client.getTimeMove();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(500),
                        event -> {
                            if(client.getPlayerState()=="enable"){

                                clockLbl.setText("Tocca a te    "+Integer.toString(client.getTimeMove()));

                                if(client.getTimeMove()<=0){
                                    if(!client.getActive()){
                                        ConfirmExit.display(client);
                                    }

                                }
                            }else {
                                clockLbl.setText("Tocca a "client.getCurrentPlayer()+"      "+Integer.toString(client.getTimeMove()));

                            }
                        }));
        timeline.playFromStart();

        return clockLbl;
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


    private GridPane addGridRound(){
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(0);
        grid.setPadding(new Insets(0, 20, 0, 20));


        for( int i=1; i<=10; i++){
            Button btnRound = buttonRound(i);
            grid.add(btnRound, i-1, 0);
        }


        return grid;

    }

    private Button buttonRound(int index){
        Button button= new Button(Integer.toString(index));
        button.setFont(new Font("Tahoma", 20));
        button.setOpacity(0.6);
        final Tooltip tooltip = new Tooltip("Colore"+"\nNumero");
        tooltip.setFont(new Font("Arial", 16));
        button.setTooltip(tooltip);
        button.setOnAction(e-> diceRoundTrack(index));
        return button;
    }

    private Stage diceRoundTrack(int i){
        Stage stage = new Stage();
        Pane root = new Pane();
        Scene scene = new Scene(root);
        root.setPrefSize(150, 50);
        GridPane grid = new GridPane();
        grid.setHgap(50);
        //DieInfo client.getDieFromRoundTrack(j);

        for(int numDie=0; numDie<2; numDie++){
            Button button = new Button();
            button.setPrefSize(40, 40);

            //mettere immagine del dado

            grid.add(button, numDie, 0);
        }
        root.getChildren().add(grid);
        stage.setScene(scene);
        stage.show();
        return stage;
    }



    private GridPane createGridEn(int player) {
        GridPane grid = new GridPane();

        ViewWP wp = client.getPlayerWPs(client.getName()).get(player);      //devo togliere me stesso.. introdurre attributo nome nella viewWp


        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                Button btnCell = new Button();
                btnCell.setPrefSize(40, 40);
                String numCell = wp.getWps()[i][j].getNumCol().get(0);
                String colorCell = wp.getWps()[i][j].getNumCol().get(1);
                String pathCell = WPRendering.pathCell(numCell, colorCell);
                Image myImage = new Image(pathCell, 40, 40, false, true);
                BackgroundImage myBI = new BackgroundImage(myImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
                btnCell.setBackground(new Background(myBI));
                grid.add(btnCell, j, i);
            }
        }
        return grid;
    }


}
