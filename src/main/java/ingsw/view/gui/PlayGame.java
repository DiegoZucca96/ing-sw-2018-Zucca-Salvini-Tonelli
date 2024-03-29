package ingsw.view.gui;


import ingsw.client.Client;
import ingsw.model.ViewWP;
import ingsw.model.ViewData;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.util.ArrayList;

/**
 * Author : Alessio Tonelli - Diego Zucca
 * This is class in which you play actually.*/
public class PlayGame {
    private Label clockLbl;
    private int timeseconds;
    private int timeBegin;
    private Timeline timeline;
    private Client client;
    private ViewData init ;
    private Button skipBtn;
    private Button useToolBtn;
    private Button takeDieBtn;
    private Button exitBtn;
    private Button infoBtn;
    private Button cancelBtn;
    private GridPane toolGrid;
    private GridPane pbGrid;
    private GridPane cellsGrid;
    private GridPane gridEn;
    private GridPaneRound gridRound;
    private GridPaneWindow myWindowGrid;
    private GridPaneWEnemy mutableGridEn;
    private GridPaneDraftPool draftPoolGrid;
    private static final String styleSheet = "-fx-text-fill: goldenrod; -fx-font: italic 15 \"serif\"";
    private ViewData updateView;
    private boolean choosePressed=false;
    private static boolean usingTool=false;
    private Label textLbl;
    private Button buttonDie;
    private ViewWP myWindow;
    private GridPane secondGrid;
    private GridPane gridTks;
    private int cardSelected;
    private GridPane tokenUsed;
    private Label numTokenUsed;
    private ImageView viewUsed;
    private int playersLeft = 0;

    /**Constructor.
     * (don't know why it doesn't work here-> prefer to create a very thin constructor to avoid the problem)
     */
    public PlayGame(Client client){
        this.client=client;
        init= null;
    }

    /**The most important part of it is the timeline.
     * This thread shows countdown and the current player.
     * Then it calls the update and makes control on status of disconnection of client.
     * @param myWindow , contains information regarded only to cells.
     * @see ViewData , take info from the update.
     * @see GridPaneWindow
     * @see GridPaneRound
     * @see GridPaneWEnemy
     * @see GridPaneDraftPool
     */
    public void display(ViewWP myWindow){
        this.myWindow=myWindow;
        Stage stage=new Stage();
        stage.setWidth(1200);
        stage.setHeight(700);
        Pane root = new Pane();
        Scene scene = new Scene(root);
        stage.setOnCloseRequest(event -> event.consume());

        //BACKGROUND IMAGE
        final ImageView backGround = new ImageView();
        String imagePathB = "/backgroundtable.jpg";
        Image imageB = new Image(imagePathB, 1200, 700, false, false);
        backGround.setImage(imageB);

        //COUNTDOWN
        Pane currentInfo = new Pane();
        currentInfo.setLayoutX(280);
        currentInfo.setLayoutY(290);
        clockLbl= new Label();
        clockLbl.setStyle(styleSheet);
        currentInfo.getChildren().add(clockLbl);

        //DIE CHOSEN
        Pane currentInfo2 = new Pane();
        currentInfo2.setLayoutX(820);
        currentInfo2.setLayoutY(580);
        textLbl= new Label();
        textLbl.setStyle("-fx-text-fill: goldenrod; -fx-font: italic 18 \"serif\"");
        textLbl.setText("Dado selezionato");
        currentInfo2.getChildren().add(textLbl);

        Pane currentInfo3 = new Pane();
        currentInfo3.setLayoutX(830);
        currentInfo3.setLayoutY(600);
        buttonDie = new Button();
        buttonDie.setOpacity(0);
        buttonDie.setPrefSize(58,58);
        buttonDie.setOnAction(event -> event.consume());
        currentInfo3.getChildren().add(buttonDie);

        //INIZIALIZZAZIONE
        init = client.initializeView();
        timeBegin = client.getStartTimeMove();
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        /**Manage time with countdown, label, update*/
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(1000),
                        event -> {
                            timeseconds = client.getTimeMove();
                            if(timeseconds==-1000){
                                Platform.runLater(() -> {
                                    new Warning(stage,client);
                                });
                                timeline.stop();
                            }
                            else{
                                if(client.getPlayerState().equals("enabled")){
                                    timeseconds = client.getTimeMove();
                                    clockLbl.setStyle("-fx-text-fill: goldenrod; -fx-font: italic 22 \"serif\"");
                                    clockLbl.setText("Tocca a te    "+Integer.toString(timeseconds));
                                    if (timeseconds == timeBegin-1) {
                                        choosePressed=false;
                                        setUsingTool(false);
                                        gridRound.setAccessRound(false);
                                        getGridWindow().setAccessWindow(false);
                                        cardSelected=0;
                                        SepiaTone sepiaTone = new SepiaTone();
                                        sepiaTone.setLevel(0.7);
                                        for(int i=0; i<toolGrid.getChildren().size();i++){
                                            toolGrid.getChildren().get(i).setEffect(sepiaTone);
                                        }
                                        draftPoolGrid.deselectBtn();
                                        myWindowGrid.abortToolView();
                                    }
                                    if (timeseconds == timeBegin-1) {
                                        this.updateView = client.updateView();
                                        if(updateView!=null)
                                            update();
                                        resetOnButton();
                                    }else{ //Per il problema che a volte scala di due secondi e i bottoni non si attivano
                                        if (timeseconds == timeBegin-2 && !choosePressed && !getUsingTool()) {
                                            this.updateView = client.updateView();
                                            if(updateView!=null)
                                                update();
                                            resetOnButton();
                                        }
                                    }
                                    if(timeseconds%2==0){
                                        String exitPlayer = someoneLeftGame();
                                        String entryPlayer = someoneRejoinedGame();
                                        if(!exitPlayer.equalsIgnoreCase("nessuno")){
                                            new Warning("I giocatori " + exitPlayer + "sono usciti dal gioco", client.getRound());
                                        }
                                        if(!entryPlayer.equalsIgnoreCase("nessuno")){
                                            new Warning("I giocatori " + entryPlayer + "sono entrati nel gioco", client.getRound());
                                        }
                                    }
                                    if(timeseconds==0) {
                                        if(!client.getActive()){
                                            Platform.runLater(() -> {
                                                new GUI().display(client);
                                            });
                                            timeline.stop();
                                            stage.close();
                                        }else{
                                            new Warning("Timeout scaduto!","Fine turno");
                                        }
                                    }
                                }else {
                                    clockLbl.setStyle(styleSheet);
                                    clockLbl.setText("Tocca a "+client.getCurrentPlayer()+"      "+Integer.toString(timeseconds));
                                    if (timeseconds == timeBegin-1) {
                                        choosePressed=false;
                                        setUsingTool(false);
                                        gridRound.setAccessRound(false);
                                        getGridWindow().setAccessWindow(false);
                                        cardSelected=0;
                                        SepiaTone sepiaTone = new SepiaTone();
                                        sepiaTone.setLevel(0.7);
                                        for(int i=0; i<toolGrid.getChildren().size();i++){
                                            toolGrid.getChildren().get(i).setEffect(sepiaTone);
                                        }
                                        resetOffButton();
                                        draftPoolGrid.deselectBtn();
                                        myWindowGrid.abortToolView();
                                    }
                                    if(timeseconds%2==0){
                                        String exitPlayer = someoneLeftGame();
                                        String entryPlayer = someoneRejoinedGame();
                                        if(!exitPlayer.equalsIgnoreCase("nessuno")){
                                            new Warning("I giocatori " + exitPlayer + "sono usciti dal gioco", client.getRound());
                                        }
                                        if(!entryPlayer.equalsIgnoreCase("nessuno")){
                                            new Warning("I giocatori " + entryPlayer + "sono entrati nel gioco", client.getRound());
                                        }
                                    }
                                    if(timeseconds%2==0){
                                        if(client.isFinish()){
                                            client.calculateScore();
                                            new Victory().start(client);
                                            PauseTransition delay = new PauseTransition(Duration.seconds(1));
                                            delay.setOnFinished( e -> {
                                                client.stopTimer();
                                                timeline.stop();
                                                stage.close();
                                            });
                                            delay.play();
                                        }
                                    }
                                    if(timeseconds%5==0){
                                        this.updateView = client.updateView();
                                        if(updateView!=null)
                                            update();
                                    }
                                }
                            }
                        }));
        timeline.playFromStart();

        //TOKENS
        int numberOfTokens = Integer.parseInt(myWindow.getDifficulty());
        gridTks = new GridPane();
        gridTks.setLayoutY(345);
        gridTks.setLayoutX(720);
        gridTks.setVgap(20);
        for(int j = 0; j<numberOfTokens; j++){
            Circle token = new Circle(10, Paint.valueOf(Color.WHITESMOKE.toString()));
            gridTks.add(token, 0, j);
        }

        //ROUNDTRACK
        gridRound= new GridPaneRound(client, init.getRoundTrack(), 1,this);
        gridRound.setLayoutY(10);
        gridRound.setLayoutX(336);

        //PRIVATE
        Pane pvPane = pvPane(client.getPVCard(client.getName()));
        pvPane.setLayoutX(760);
        pvPane.setLayoutY(225);

        //TOOLCARD
        ImageView toolPane1 = Images(init.getToolCard(), 0);
        ImageView toolPane2 = Images(init.getToolCard(), 1);
        ImageView toolPane3 = Images(init.getToolCard(), 2);

        toolGrid = new GridPane();
        toolGrid.setLayoutX(-50);
        toolGrid.setLayoutY(-100);
        toolGrid.setVgap(-200);

        toolGrid.add(toolPane1, 0, 0);
        toolGrid.add(toolPane2, 0, 1);
        toolGrid.add(toolPane3, 0, 2);

        //TOKEN TOOLCARD
        tokenUsed = new GridPane();
        tokenUsed.setLayoutX(180);
        tokenUsed.setLayoutY(80);
        tokenUsed.setVgap(210);
        tokenUsed.setHgap(10);
        for(int i=0; i<3; i++){
            Circle tokenTool = new Circle(10, Color.WHITESMOKE);
            tokenTool.setOpacity(1);
            numTokenUsed = new Label();
            numTokenUsed.setStyle("-fx-text-fill: goldenrod; -fx-font: italic 18 \"serif\"");
            numTokenUsed.setText("0");
            numTokenUsed.setOpacity(1);
            tokenUsed.add(tokenTool,0,i);
            tokenUsed.add(numTokenUsed,1,i);
        }

        //PBCARD
        ImageView pbPane1 = Images(init.getPbCard(), 0);
        ImageView pbPane2 = Images(init.getPbCard(), 1);
        ImageView pbPane3 = Images(init.getPbCard(), 2);

        pbGrid = new GridPane();
        pbGrid.setLayoutX(950);
        pbGrid.setLayoutY(-100);
        pbGrid.setVgap(-200);

        pbGrid.add(pbPane1, 0, 0);
        pbGrid.add(pbPane2, 0, 1);
        pbGrid.add(pbPane3, 0, 2);

        //DRAFTPOOL
        draftPoolGrid = new GridPaneDraftPool(client,init.getDraftPoolDice(),buttonDie,this);
        draftPoolGrid.setLayoutX(200);
        draftPoolGrid.setLayoutY(600);

        //IMMUTABLE WINDOW MADE OF ONLY CELLS
        cellsGrid = addGridPane();

        //PANE WHICH IS BETWEEN THE TWO GRIDS
        Pane divideGrids = new Pane();
        divideGrids.setLayoutX(240);
        divideGrids.setLayoutY(330);
        divideGrids.getChildren().add(cellsGrid);

        //LA MIA WP
        myWindowGrid = new GridPaneWindow(myWindow, client, draftPoolGrid, this);
        myWindowGrid.setLayoutX(240);
        myWindowGrid.setLayoutY(330);

        //TITLE
        Label title = new Label();
        title.setText(client.getWP(client.getName()).getName());
        title.setStyle(styleSheet);
        title.setLayoutX(300);
        title.setLayoutY(550);

        //WP ENEMY
        GridPane eachGrid = new GridPane();
        eachGrid.setHgap(40);
        eachGrid.setPrefSize(720, 200);
        eachGrid.setLayoutX(240);
        eachGrid.setLayoutY(80);
        for(int index= 0; index < client.getNumberOfPlayers()-1; index++){
            gridEn = createGridEn(index);
            eachGrid.add(gridEn, index, 0);
        }

        //SECOND GRID ON EACHGRID TO PUT DICE
        secondGrid = new GridPane();
        secondGrid.setPrefSize(720, 200);
        secondGrid.setLayoutX(240);
        secondGrid.setLayoutY(80);
        int position = 0;
        for(int index= 0; index < client.getNumberOfPlayers(); index++){
            if(!(client.getListOfPlayers().get(index).equalsIgnoreCase(client.getName()))){
                mutableGridEn = new GridPaneWEnemy(client,client.getWP(client.getListOfPlayers().get(index)).getNumberWP());
                secondGrid.add(mutableGridEn, position, 0);
                position++;
            }
        }
        secondGrid.setHgap(40);

        //BUTTONS
        GridPane btnGrid = new GridPane();
        btnGrid.setVgap(20);
        skipBtn = new Button("End Turn");
        skipBtn.setOnAction(e-> {
            if(client.getPlayerState().equalsIgnoreCase("enabled")){
                choosePressed=false;
                setUsingTool(false);
                gridRound.setAccessRound(false);
                myWindowGrid.setDisable(true);
                getGridWindow().setAccessWindow(false);
                resetOffButton();
                client.setActive(true);
                if(client.iAmAlone()){
                    timeline.stop();
                    new VictoryAlone().start(client.getName());
                    client.skip();
                    stage.close();
                }else{
                    client.skip();
                    if(client.isFinish()){
                        client.calculateScore();
                        new Victory().start(client);
                        timeline.stop();
                        stage.close();
                    }
                }
            }
        });
        btnGrid.add(skipBtn, 0, 0);

        useToolBtn = new Button("Use Tool");
            useToolBtn.setOnAction(e-> {
                if(client.getPlayerState().equalsIgnoreCase("enabled"))  {
                    choosePressed=false;
                    setUsingTool(true);
                    client.setActive(true);
                    skipBtn.setDisable(true);
                    useToolBtn.setDisable(true);
                    takeDieBtn.setDisable(true);
                    cancelBtn.setDisable(false);
                    myWindowGrid.setDisable(false);
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
                    myWindowGrid.setDisable(false);
                }
            });

        btnGrid.add(takeDieBtn, 0, 2);

        exitBtn = new Button("Exit game");
        exitBtn.setOnAction(e-> {
            Warning warning = new Warning("Sei sicuro di voler uscire?");
            Boolean answer= warning.display();
            if(answer){
                client.setActive(false);
                client.skip();
                timeline.stop();
                Platform.exit();
            }
        });
        btnGrid.add(exitBtn, 0, 3);

        cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(e-> {
            if(client.getPlayerState().equalsIgnoreCase("enabled")){
                choosePressed=false;
                setUsingTool(false);
                gridRound.setAccessRound(false);
                getGridWindow().setAccessWindow(false);
                cardSelected=0;
                SepiaTone sepiaTone = new SepiaTone();
                sepiaTone.setLevel(0.7);
                for(int i=0; i<toolGrid.getChildren().size();i++){
                    toolGrid.getChildren().get(i).setEffect(sepiaTone);
                }
                int col = client.getCoordinateSelectedY();
                if(col==-1){            //se non ha selezionato nulla
                    resetOnButton();
                }else{
                    client.nullSelection();
                    resetOnButton();
                    draftPoolGrid.deselectBtn();
                    myWindowGrid.abortToolView();
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
        btnGrid.setLayoutY(330);

        resetOffButton();

        //ATTACH DI TUTTO
        root.getChildren().addAll(backGround, toolGrid, pbGrid, gridRound, divideGrids, myWindowGrid, title, btnGrid, eachGrid, secondGrid, pvPane, draftPoolGrid, gridTks,currentInfo,currentInfo2, currentInfo3,tokenUsed);
        stage.setScene(scene);
        stage.setTitle("Sagrada - " +client.getName());
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.show();
    }

    /**
     * Create grid for cells of my window pattern
     * @return GridPane
     */
    private GridPane addGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(3);
        gridPane.setVgap(3);
        String number;
        String color;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                number = Integer.toString(myWindow.getWp()[i][j].getNum());
                color = String.valueOf(myWindow.getWp()[i][j].getColor());
                PaneView paneView = new PaneView(number, color, 0);
                gridPane.add(paneView, j, i);
            }
        }
        return gridPane;
    }

    /**
     * Create a pane for my private card.
     * @param stringPvCard , contains color of the card.
     * @return Pane
     */
    private Pane pvPane(String stringPvCard){
        Pane pane = new Pane();
        pane.setLayoutY(200);
        pane.setLayoutX(130);
        final ImageView view = new ImageView();
        String imagePathT = stringPvCard;
        Image imageT = new Image(imagePathT, 300, 420, false, true);
        view.setImage(imageT);
        view.scaleXProperty().setValue(0.43);
        view.scaleYProperty().setValue(0.476);
        pane.getChildren().add(view);
        return pane;
    }

    /**
     * Method which allows player to use Tool Card. In particular, it makes attribute "usingTool" false because card
     * could not be used an other time unless you don't click button "Uning Tool".
     * When one tool is pressed it sets cardSelected to the value of the card chosen in the GridPane.
     * Some cards could be used immediately without accessing to neither DraftPool or Window or RoundTrack, so ToolView
     * is called.
     * @param stringCard , value of Tool Card which comes from ViewData.
     * @param i, position in the gridPane.
     * @return ImageView , contains image of card in that position.
     * @see ViewData
     * @see ToolView
     */
    private ImageView Images(ArrayList<String> stringCard, int i){
        final ImageView view = new ImageView();
        String imagePathT = stringCard.get(i);
        SepiaTone sepiaTone = new SepiaTone();
        Image imageT = new Image(imagePathT, 300, 420, false, true);
        if(imagePathT.substring(1, 2).equals("T")){
            sepiaTone.setLevel(0.7);
            view.setEffect(sepiaTone);
        }
        view.setOnMouseClicked(e->{
            if(usingTool){
                if(imagePathT.substring(1, 2).equals("T") && client.getTokenRemaining(client.getName())>0){
                    viewUsed = view;
                    view.setEffect(new Glow(0.3));
                    cardSelected = Integer.parseInt(imagePathT.substring(imagePathT.indexOf("l")+1, imagePathT.indexOf("+")));
                    if(client.getInsertedDie()){
                        if(cardSelected == 1 || cardSelected == 5 || cardSelected == 6 || cardSelected == 7 || cardSelected == 9 || cardSelected == 10 || cardSelected == 11) {
                            cardSelected = 0;
                            onPositionWPButton();
                            setUsingTool(false);
                            Toolkit.getDefaultToolkit().beep();
                        }else{
                            if(cardSelected!=8){
                                if(!client.useToolCard(cardSelected,null)){
                                    view.setEffect(sepiaTone);
                                    viewUsed=null;
                                    cardSelected=0;
                                    Toolkit.getDefaultToolkit().beep();
                                }
                            }
                        }
                    }else{
                        if(cardSelected!=8 && cardSelected!=7){
                            if(!client.useToolCard(cardSelected,null)){
                                view.setEffect(sepiaTone);
                                viewUsed=null;
                                cardSelected=0;
                                Toolkit.getDefaultToolkit().beep();
                            }
                        }
                    }
                }
                update();
            }
            if(cardSelected==7){
               if(!client.getClockwiseRound()) {
                   ToolView toolView = new ToolView();
                   for(int j = 0; j <client.getListOfPlayers().size()*2+1; j++ ){
                       if(draftPoolGrid.getButton(0, j).getOpacity()!=0){
                           toolView.setListOfCoordinateY(Integer.toString(j));
                       }
                   }
                   if(!toolView.getListOfCoordinateY().isEmpty())
                       if(client.useToolCard(7, toolView)){
                           client.useToolCard(7, null);
                           draftPoolGrid.updateDP(client.updateView().getDraftPoolDice());
                           update();
                           resetOnButton();
                           setUsingTool(false);
                           view.setEffect(sepiaTone);
                           viewUsed=null;
                       }else{
                           view.setEffect(sepiaTone);
                           viewUsed=null;
                           resetOnButton();
                           setUsingTool(false);
                           Toolkit.getDefaultToolkit().beep();
                       }
               }else{
                   view.setEffect(sepiaTone);
                   viewUsed=null;
                   cardSelected=0;
                   resetOnButton();
                   Toolkit.getDefaultToolkit().beep();
                   setUsingTool(false);
               }
            }
            if(cardSelected == 8){
               if(client.getInsertedDie() && client.getClockwiseRound()){
                   if(client.useToolCard(8,null)){
                       takeDieBtn.setDisable(false);
                       client.setTool8Used(true);
                       view.setEffect(sepiaTone);
                       viewUsed=null;
                       setUsingTool(false);
                       update();
                   }else{
                       setUsingTool(false);
                       view.setEffect(sepiaTone);
                       viewUsed=null;
                       Toolkit.getDefaultToolkit().beep();
                   }
               }
               else{
                   setUsingTool(false);
                   view.setEffect(sepiaTone);
                   viewUsed=null;
                   cardSelected=0;
                   Toolkit.getDefaultToolkit().beep();
               }
            }
            if(cardSelected == 2 || cardSelected == 3 || cardSelected == 4) {
                getGridWindow().setAccessWindow(true);
                getGridWindow().setFirstChoice(true);
            }
            if(cardSelected == 12){
                getGridWindow().setFirstChoice(true);
                getGridRound().setAccessRound(true);
            }
        });
        view.setImage(imageT);
        view.scaleXProperty().setValue(0.43);
        view.scaleYProperty().setValue(0.476);
        return view;
    }

    /**
     * Create enemy grid made of only cells.
     * @param player , could be 0, 1, 2 based on which is the position in the array of controller.
     * @return GridPane
     */
    private GridPane createGridEn(int player) {
        GridPane grid = new GridPane();
        grid.setVgap(1.5);
        grid.setHgap(1.5);
        String number;
        String color;
        ViewWP wp = client.getPlayerWPs(client.getName()).get(player);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                number = Integer.toString(wp.getWp()[i][j].getNum());
                color = String.valueOf(wp.getWp()[i][j].getColor());
                PaneView paneView = new PaneView(number, color, 1);
                grid.add(paneView, j, i);
            }
        }
        return grid;
    }

    /**
     * This is the method with is devoted to update some parts of the view.
     * There are : windows enemy, draftpool, roundtrack and toolcard.
     */
    public void update() {
        updateView = client.updateView();
        for(int i = 0; i<client.getNumberOfPlayers()-1; i++){
            if(updateView.getWps()!=null){
                if(updateView.getWps().get(i)!=null){
                    mutableGridEn = (GridPaneWEnemy) secondGrid.getChildren().get(i);
                    if(mutableGridEn.getNumberWP() == updateView.getWps().get(i).getNumberWP())
                        mutableGridEn.updateWindow(updateView.getWps().get(i));
                    else
                        mutableGridEn.updateWindow(updateView.getWps().get(i+1));
                }
            }
        }

        if(updateView.getDraftPoolDice()!=null){
            draftPoolGrid.updateDP(updateView.getDraftPoolDice());
        }

        if(updateView.getRoundTrack()!=null)
            gridRound.updateRound(updateView.getRoundTrack(), client.getRound());

        if(updateView.getToolCard()!=null)
            updateTool(updateView.getToolCard(),gridTks, tokenUsed, client.getName());

    }

    public String someoneLeftGame() {
        ArrayList<String> leftGame = client.someoneLeftGame();
        String namePlayersLeft = "";
        if (!leftGame.isEmpty()) {
            for (String s : leftGame) {
                namePlayersLeft = namePlayersLeft.concat(s);
                namePlayersLeft = namePlayersLeft.concat("  ");
            }
            return namePlayersLeft;
        }else
            return "nessuno";
    }

    public String someoneRejoinedGame(){
        ArrayList<String> rejoinGame = client.someoneRejoinedGame();
        String namePlayersRejoined = "";
        if(!rejoinGame.isEmpty()){
            for(String s : rejoinGame){
                namePlayersRejoined = namePlayersRejoined.concat(s);
                namePlayersRejoined = namePlayersRejoined.concat("  ");
            }
            return namePlayersRejoined;
        }else
            return "nessuno";
    }

    /**
     * Method used to delete a circle token from the table when a tool card is used and
     * increment the number of tokens associated to.
     * @param toolCards
     * @param gridTks
     * @param tokenUsed
     * @param name
     */
    private void updateTool(ArrayList<String> toolCards, GridPane gridTks, GridPane tokenUsed, String name){
        for(int i=0; i<toolCards.size();i++){
            String tool = toolCards.get(i);
            String numTokenUsed = tool.substring(tool.indexOf("+")+1,tool.indexOf("."));
            for(Node node : tokenUsed.getChildren()){
                if(GridPane.getColumnIndex(node)==1 && GridPane.getRowIndex(node)==i){
                    Label numToken = (Label) node;
                    numToken.setText(numTokenUsed);
                }
            }
        }
        int myTokenRemaining = client.getTokenRemaining(name);
        for(Node node : gridTks.getChildren()){
            if(GridPane.getRowIndex(node)<myTokenRemaining){
                Circle token = (Circle) node;
                token.setOpacity(1);
            }else{
                Circle token = (Circle) node;
                token.setOpacity(0);
            }
        }
    }

    /** Need to GridPaneDraftPool in order to access dice and take them if the value is true.
     * @see GridPaneDraftPool
     */
    public boolean getChoosePressed(){
        return choosePressed;
    }

    /** Need to GridPaneDraftPool in order to use a Tool Card by accessing dice and take them if the value is true.
     * @see GridPaneDraftPool
     */
    public boolean getUsingTool() {
        return usingTool;
    }

    /** Attribute used by the following classes in order to use a Tool Card.
     * Return value of the card.
     * @see GridPaneDraftPool
     * @see GridPaneRound
     * @see GridPaneWindow
     */
    public int getCardSelected(){
        return cardSelected;
    }

    /**Setter method*/
    public void setCardSelected(int cardSelected) {
        this.cardSelected = cardSelected;
    }

    /**Activate all buttons*/
    public void resetOnButton(){
        skipBtn.setDisable(false);
        useToolBtn.setDisable(false);
        takeDieBtn.setDisable(false);
        infoBtn.setDisable(false);
        cancelBtn.setDisable(false);
    }

    /**Disactivate all buttons*/
    public void resetOffButton(){
        skipBtn.setDisable(true);
        useToolBtn.setDisable(true);
        takeDieBtn.setDisable(true);
        infoBtn.setDisable(true);
        cancelBtn.setDisable(true);
    }

    /**
     * When a die is put on window these buttons are activated or disabled
     */
    public void onPositionWPButton() {
        skipBtn.setDisable(false);
        useToolBtn.setDisable(false);
        takeDieBtn.setDisable(true);
        infoBtn.setDisable(false);
        cancelBtn.setDisable(true);
        SepiaTone sepiaTone = new SepiaTone();
        sepiaTone.setLevel(0.7);
        if(viewUsed!=null){
            viewUsed.setEffect(sepiaTone);
            viewUsed = null;
        }
    }

    public void setBtnOnTakeDieClicked(){
        skipBtn.setDisable(true);
        cancelBtn.setDisable(false);
        infoBtn.setDisable(true);
        useToolBtn.setDisable(true);
    }


    public void setChoosePressed(boolean b) {
        choosePressed = b;
    }

    public void setUsingTool(boolean usingTool) {
        this.usingTool = usingTool;
    }

    public GridPaneRound getGridRound() {
        return gridRound;
    }

    public GridPaneDraftPool getDraftPoolGrid() {
        return draftPoolGrid;
    }

    public GridPaneWindow getGridWindow() {
        return myWindowGrid;
    }
}
