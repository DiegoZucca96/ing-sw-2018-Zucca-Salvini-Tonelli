package ingsw.view;

import ingsw.Client;
import ingsw.controller.ViewWP;
import ingsw.model.ViewData;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.ArrayList;

import static ingsw.view.WPRendering.pathCell;


/**author : Alessio Tonelli
 *
 * THIS CLASS REPRESENTS THE TABLE ON WHICH MATCH TAKES PLACE
 *
 * */
public class Play {


    private Client client;
    private ViewData init ;


    public Play(Client client){
        this.client=client;
        init= client.initializeView();
    }

    public void display(ViewWP myWindow){

        Stage table = new Stage();
        table.setWidth(1200);
        table.setHeight(700);
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root);

        //MENU FILE - INFO - SKIP
        Pane paneMenu = new Pane();
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu(), toolMenu());
        ContextMenu contextFileMenu = new ContextMenu(exitMenuItem());
        table.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent me) -> {
            if (me.getButton() == MouseButton.SECONDARY || me.isControlDown()) {
                contextFileMenu.show(root, me.getScreenX(), me.getScreenY());
            } else {
                contextFileMenu.hide();
            }
        });
        paneMenu.getChildren().add(menuBar);
        paneMenu.setLayoutY(10);
        paneMenu.setLayoutX(1000);



        //GLIGLIA DI BOTTONI PER LA ROUNDTRACK
        GridPane gridRound=addGridRound();
        gridRound.setLayoutY(10);
        gridRound.setLayoutX(336);

        //IMMAGINE DRAFTPOOL PER VISUALIZZARE I DADI
        Pane pDraft = addPaneDraft();
        pDraft.setLayoutX(10.0);
        pDraft.setLayoutY(10.0);

        //IMMAGINE BACKGROUND
        final ImageView backGround = new ImageView();
        String imagePath = "/backgroundtable.jpg";
        Image image1 = new Image(imagePath, 1200, 700, false, false);
        backGround.setImage(image1);



        //LA MIA WINDOW PATTERN
        Pane paneWP = new Pane();
        paneWP.setLayoutY(420);
        paneWP.setLayoutX(10);
        /*GridPane myGrid = new GridPane();
        myGrid.setLayoutX(0);
        myGrid.setLayoutY(0);
        int k=2;
        for(int i=0; i<4; i++){
            for( int j=0; j<5; j++){
                Button btnCell = new Button();
                btnCell.setPrefSize(55, 55);
                String cell = myWindow.get(k);
                String numCell = cell.substring(cell.indexOf(':')+1,cell.indexOf(',')-1);
                String colorCell = cell.substring(cell.indexOf(",")+1);
                String pathCell = WPRendering.pathCell(numCell, colorCell);
                Image myImage = new Image(pathCell, 55, 55, false, false);
                BackgroundImage myBI= new BackgroundImage(myImage,
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                        BackgroundSize.DEFAULT);
                btnCell.setBackground(new Background(myBI));
                myGrid.add(btnCell, j, i);
                btnCell.setOnAction(e->{
                    getInfo();
                    Integer colIndex = myGrid.get
                    client.moveDie(index, i, j);
                });


                k++;
            }
        }*/

        GridPane myGrid = new GridPaneWindow(2, myWindow, client);
        myGrid.setLayoutX(0);
        myGrid.setLayoutY(0);


        Label info = new Label(myWindow.getName()+myWindow.getDifficulty());
        info.setLayoutY(230);
        info.setLayoutX(10);
        info.setStyle("-fx-text-fill: goldenrod; -fx-font: italic 15 \"serif\"; -fx-padding: 0 0 20 0; -fx-text-alignment: center");
        paneWP.getChildren().addAll(myGrid, info);


        //PAGINAZIONE DI VARI OGGETTI
        init = client.initializeView();
        AnchorPane playersWP = paginationPlayers(init);
        AnchorPane playersPb = paginationPublic(init);
        AnchorPane playersTool = paginationTool(init);


        Pane panePlayer= new Pane();
        panePlayer.setLayoutX(900);
        panePlayer.setLayoutY(360);
        panePlayer.getChildren().add(playersWP);

        Pane panePublic = new Pane();
        panePublic.setLayoutX(310);
        panePublic.setLayoutY(360);
        panePublic.getChildren().add(playersPb);

        Pane paneTool = new Pane();
        paneTool.setLayoutX(600);
        paneTool.setLayoutY(360);
        paneTool.getChildren().add(playersTool);



        //IMMAGINE ROUNDTRACK COME BACKGROUND DEI BOTTONI PER SELEZIONARE IL TURNO
        final ImageView round = new ImageView();
        String imagePathR = "/roundtrack.png";
        Image imageR = new Image(imagePathR, 500, 90, false, false);
        round.setImage(imageR);
        round.setLayoutX(350.0);
        round.setLayoutY(-20.0);



        root.getChildren().addAll( backGround, round, pDraft, paneWP, panePlayer, panePublic, paneTool, gridRound, paneMenu);
        table.setScene(scene);
        table.setTitle("Sagrada - "+client.getName());
        table.resizableProperty().setValue(Boolean.FALSE);
        table.show();

       /* table.setOnCloseRequest(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Close Confirmation");
            alert.setHeaderText("Quit match");
            alert.setContentText("Are you sure you want to exit?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                table.close();
                Private.;
            }
            if(result.get()==ButtonType.CANCEL){
                event.consume();
                alert.close();
            }
        });*/
    }




    private MenuItem exitMenuItem() {
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(actionEvent -> {
            Boolean answer= ConfirmExit.display("Quit", "Are you sure to exit without saving?");
            if(answer)
                Platform.exit();

        });

        return exitMenuItem;
    }
    private Menu fileMenu() {
        Menu fileMenu = new Menu("File");
        MenuItem authorMenuItem = new MenuItem("Authors of the Game");
        fileMenu.getItems().addAll( authorMenuItem,
                new SeparatorMenuItem(), exitMenuItem());
        authorMenuItem.setOnAction(e-> InfoStage.displayAuthor(new Stage()));

        return fileMenu;
    }



    private Menu toolMenu(){
        Menu toolMenu = new Menu("Tools");

        MenuItem  endTurnItem = new MenuItem("End Turn");
        endTurnItem.setOnAction(actionEvent -> {
            client.skip();
        });


        return toolMenu;
    }


    private GridPane addGridRound(){
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(0);
        grid.setPadding(new Insets(0, 20, 0, 20));


        for( int i=1; i<=10; i++){
            Button btnRound = new Button(".");
            btnRound.setFont(new Font("Tahoma", 20));
            btnRound.setOpacity(0.6);
            final Tooltip tooltip = new Tooltip("Colore"+"\nNumero");
            tooltip.setFont(new Font("Arial", 16));
            btnRound.setTooltip(tooltip);
            //btnRound.setOnAction(e-> DiceRound.displayDice(new Stage()));
            grid.add(btnRound, i-1, 0);
        }


        return grid;

    }


    private Pane addPaneDraft(){


        Pane pane = new Pane();
        pane.setPrefSize(280, 350);

        final ImageView imageDP = new ImageView();
        String imagePathD = "/draftpool.png";
        Image draft = new Image(imagePathD, 280, 350, false, false);



        GridPane gridDP = new GridPaneDraftPool(client);
        gridDP.setPrefSize(280, 350);
        gridDP.setHgap(3);
        gridDP.setVgap(8);
        BackgroundImage myBI= new BackgroundImage(draft,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        gridDP.setBackground(new Background(myBI));
/*
        for(int row=0; row<3; row++){
            for(int col=0; col<3; col++){
                Button b= new Button();
                b.setOpacity(0.6);
                b.setPrefSize(58, 58);
                if(client.getPlayerState().equalsIgnoreCase("disabled")){
                    b.setDisable(true);
                }else b.setDisable(false);

                //b.setBackground(new BackgroundImage());
                b.setOnAction(e-> {
                    //saveInfo();
                    if(client.takeDie()){

                    }else
                        Toolkit.getDefaultToolkit().beep();
                    b.setTextFill(javafx.scene.paint.Color.TRANSPARENT);
                });
                gridDP.add(b, col, row);

            }
        }*/
        gridDP.setAlignment(Pos.CENTER);
        pane.getChildren().add(gridDP);





        return pane;
    }


    private AnchorPane paginationPlayers(ViewData init){
        Pagination pagination = new Pagination(client.getNumberOfPlayers()-1, 0);
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                return createPageWp(pageIndex);
            }
        });

        AnchorPane anchor = new AnchorPane();
        AnchorPane.setTopAnchor(pagination, 10.0);
        AnchorPane.setRightAnchor(pagination, 10.0);
        AnchorPane.setBottomAnchor(pagination, 10.0);
        AnchorPane.setLeftAnchor(pagination, 10.0);
        anchor.getChildren().addAll(pagination);

        return anchor;
    }

    private VBox createPageWp(int pageIndex){
        VBox box = new VBox(5);
        final String styleSheet ="-fx-text-fill: blue; -fx-font: italic 15 \"serif\"";

        //mi interessa la lista degli avversari
        ArrayList<String> listOfPllayers = client.getListOfPlayers();
        listOfPllayers.remove(client.getName());

        Pane paneWP = new Pane();

        switch (pageIndex) {
            case 0: {
                /*String imagePath = "/Virtus.png";
                Image image1 = new Image(imagePath, 280, 280, false, false);
                BackgroundImage myBI= new BackgroundImage(image1,
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                        BackgroundSize.DEFAULT);
                gridWP.setBackground(new Background(myBI));

                for(int row=0; row<4; row++){
                    for(int col=0; col<5; col++){
                        Button b= new Button();
                        b.setOpacity(0.6);
                        b.setPrefSize(58, 58);
                        gridWP.add(b, col, row);

                    }
                }*/

                Label lblName0 = new Label(listOfPllayers.get(0));
                //lblName0.setTextFill(javafx.scene.paint.Color.BLUE);
                lblName0.setStyle(styleSheet);
                lblName0.setLayoutX(10);
                lblName0.setLayoutY(200);

                ViewWP wp0 = client.getPlayerWPs(client.getName()).get(0);

                GridPane gridWP0 = new GridPane();

                for(int i=0; i<4; i++){
                    for( int j=0; j<5; j++){
                        Button btnCell = new Button();
                        btnCell.setPrefSize(50, 50);
                        String numCell = wp0.getWps()[i][j].getNumCol().get(0);
                        String colorCell = wp0.getWps()[i][j].getNumCol().get(1);
                        String pathCell = pathCell(numCell, colorCell);
                        Image myImage = new Image(pathCell, 50, 50, false, false);
                        BackgroundImage myBI= new BackgroundImage(myImage,
                                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                                BackgroundSize.DEFAULT);
                        btnCell.setBackground(new Background(myBI));
                        gridWP0.add(btnCell, i, j);

                    }
                }

                paneWP.getChildren().addAll(gridWP0, lblName0);

                break;
            }
            case 1: {
                Label lblName1 = new Label(listOfPllayers.get(1));
                lblName1.setStyle(styleSheet);
                lblName1.setLayoutX(10);
                lblName1.setLayoutY(200);

                ViewWP wp1 = client.getPlayerWPs(client.getName()).get(1);

                GridPane gridWP1 = new GridPane();

                for(int i=0; i<4; i++){
                    for( int j=0; j<5; j++){
                        Button btnCell = new Button();
                        btnCell.setPrefSize(50, 50);
                        String numCell = wp1.getWps()[i][j].getNumCol().get(0);
                        String colorCell = wp1.getWps()[i][j].getNumCol().get(1);
                        String pathCell = pathCell(numCell, colorCell);
                        Image myImage = new Image(pathCell, 50, 50, false, false);
                        BackgroundImage myBI= new BackgroundImage(myImage,
                                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                                BackgroundSize.DEFAULT);
                        btnCell.setBackground(new Background(myBI));
                        gridWP1.add(btnCell, i, j);

                    }
                }

                paneWP.getChildren().addAll(gridWP1, lblName1);
                break;
            }
            case 2: {
                Label lblName2 = new Label(listOfPllayers.get(2));
                lblName2.setStyle(styleSheet);
                lblName2.setLayoutX(10);
                lblName2.setLayoutY(200);

                ViewWP wp2 = client.getPlayerWPs(client.getName()).get(2);

                GridPane gridWP2 = new GridPane();

                for(int i=0; i<4; i++){
                    for( int j=0; j<5; j++){
                        Button btnCell = new Button();
                        btnCell.setPrefSize(50, 50);
                        String numCell = wp2.getWps()[i][j].getNumCol().get(0);
                        String colorCell = wp2.getWps()[i][j].getNumCol().get(1);
                        String pathCell = pathCell(numCell, colorCell);
                        Image myImage = new Image(pathCell, 50, 50, false, false);
                        BackgroundImage myBI= new BackgroundImage(myImage,
                                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                                BackgroundSize.DEFAULT);
                        btnCell.setBackground(new Background(myBI));
                        gridWP2.add(btnCell, i, j);

                    }
                }

                paneWP.getChildren().addAll(gridWP2, lblName2);
                break;
            }


        }

        box.getChildren().add(paneWP);
        return box;
    }

    private AnchorPane paginationPublic(ViewData init){
        Pagination pagination = new Pagination(3, 0);
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                return createPagePb(pageIndex,init);
            }
        });

        AnchorPane anchor = new AnchorPane();
        AnchorPane.setTopAnchor(pagination, 10.0);
        AnchorPane.setRightAnchor(pagination, 10.0);
        AnchorPane.setBottomAnchor(pagination, 10.0);
        AnchorPane.setLeftAnchor(pagination, 10.0);
        anchor.getChildren().addAll(pagination);

        return anchor;
    }

    private VBox createPagePb(int pageIndex,ViewData init){
        VBox box = new VBox(5);
        String name=null;
        ArrayList<String> pbCards = init.getPbCard();
        final ImageView pbPlayers = new ImageView();
        switch (pageIndex) {
            case 0: {
                String imagePath = pbCards.get(0);
                Image image1 = new Image(imagePath, 200, 280, false, false);
                pbPlayers.setImage(image1);
                name = pbPlayers.getImage().impl_getUrl();
                break;
            }
            case 1: {
                String imagePath = pbCards.get(1);
                Image image1 = new Image(imagePath, 200, 280, false, false);
                pbPlayers.setImage(image1);
                name = pbPlayers.getImage().impl_getUrl();
                break;
            }
            case 2: {
                String imagePath = pbCards.get(2);
                Image image1 = new Image(imagePath, 200, 280, false, false);
                pbPlayers.setImage(image1);
                name = pbPlayers.getImage().impl_getUrl();
                break;
            }


        }

        final String finalName;

        if(name==null){
            finalName=null;
        }else
            finalName = name.substring(name.lastIndexOf("/"));

        pbPlayers.setOnMouseClicked(e-> {
            if(ConfirmExit.display("Public "+finalName.substring(1, finalName.indexOf(".")), "Sure to use this card?")){
                //client.useToolCard();  quale parametro?

            }
        });
        box.getChildren().add(pbPlayers);
        return box;
    }

    private AnchorPane paginationTool(ViewData init){
        Pagination pagination = new Pagination(3, 0);
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                return createPageTool(pageIndex,init);
            }
        });

        AnchorPane anchor = new AnchorPane();
        AnchorPane.setTopAnchor(pagination, 10.0);
        AnchorPane.setRightAnchor(pagination, 10.0);
        AnchorPane.setBottomAnchor(pagination, 10.0);
        AnchorPane.setLeftAnchor(pagination, 10.0);
        anchor.getChildren().addAll(pagination);

        return anchor;
    }

    private VBox createPageTool(int pageIndex,ViewData init){
        VBox box = new VBox(5);
        String name=null;
        ArrayList<String> toolCards = init.getToolCard();
        final ImageView tlPlayer= new ImageView();
        switch (pageIndex) {
            case 0: {

                String imagePath = toolCards.get(0);
                Image image1 = new Image(imagePath, 200, 280, false, false);
                tlPlayer.setImage(image1);
                name = tlPlayer.getImage().impl_getUrl();

                break;
            }
            case 1: {

                String imagePath = toolCards.get(1);
                Image image1 = new Image(imagePath, 200, 280, false, false);
                tlPlayer.setImage(image1);
                name = tlPlayer.getImage().impl_getUrl();
                break;
            }
            case 2: {

                String imagePath = toolCards.get(2);
                Image image1 = new Image(imagePath, 200, 280, false, false);
                tlPlayer.setImage(image1);
                name = tlPlayer.getImage().impl_getUrl();
                break;
            }


        }

        final String finalName;

        if(name==null){
            finalName=null;
        }else
            finalName = name.substring(name.lastIndexOf("/"));


        tlPlayer.setOnMouseClicked(e-> {
            if(ConfirmExit.display("Tool "+finalName.substring(1, finalName.indexOf(".")), "Sure to use this card?")){
                //client.useToolCard();  quale parametro?

            }
        });


        box.getChildren().add(tlPlayer);
        return box;
    }

}
