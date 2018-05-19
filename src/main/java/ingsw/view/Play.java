package ingsw.view;

import ingsw.model.InitializerView;
import ingsw.model.windowpattern.CellRender;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
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
import java.util.List;

public class Play {


    public static void display(List<CellRender> myWindow, InitializerView init){

        Stage table = new Stage();
        table.setWidth(1200);
        table.setHeight(700);
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root);

        //MENU FILE - INFO - SKIP
        Pane paneMenu = new Pane();
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu(), infoMenu(), toolMenu());
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
        GridPane myGrid = new GridPane();
        int k=0;
        for(int i=0; i<4; i++){
            for( int j=0; j<5; j++){
                Button btnCell = new Button();
                btnCell.setPrefSize(55, 55);
                String numCell = myWindow.get(k).getNumber();
                String colorCell = myWindow.get(k).getColor();
                String pathCell = WPRendering.pathCell(numCell, colorCell);
                Image myImage = new Image(pathCell, 55, 55, false, false);
                BackgroundImage myBI= new BackgroundImage(myImage,
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                        BackgroundSize.DEFAULT);
                btnCell.setBackground(new Background(myBI));
                myGrid.add(btnCell, j, i);
                k++;
            }
        }

        paneWP.getChildren().add(myGrid);


        //PAGINAZIONE DI VARI OGGETTI
        AnchorPane playersWP = paginationPlayers();
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
        table.setTitle("Sagrada");
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


    private static MenuItem exitMenuItem() {
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(actionEvent -> {
            Boolean answer= ConfirmExit.display("Quit", "Are you sure to exit without saving?");
            if(answer)
                Platform.exit();
        });

        return exitMenuItem;
    }
    private static Menu fileMenu() {
        Menu fileMenu = new Menu("File");
        MenuItem saveMenuItem = new MenuItem("Save Match");
        fileMenu.getItems().addAll( saveMenuItem,
                new SeparatorMenuItem(), exitMenuItem());
        //saveMenuItem.setOnAction(e->);

        return fileMenu;
    }

    private static Menu infoMenu() {
        Menu infoMenu = new Menu("Info");

        MenuItem authorMenuItem = new MenuItem("Authors of the Game");
        authorMenuItem.setOnAction(e-> InfoStage.displayAuthor(new Stage()));

        infoMenu.getItems().addAll(authorMenuItem);

        return infoMenu;
    }

    private static Menu toolMenu(){
        Menu toolMenu = new Menu("Tools");

        MenuItem  menuSkip = new MenuItem("Skip>>");
        //menuSkip.setOnAction(e-> );
        toolMenu.getItems().add(menuSkip);
        return toolMenu;
    }



    private static GridPane addGridRound(){
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


    private static Pane addPaneDraft(){


        Pane pane = new Pane();
        pane.setPrefSize(280, 350);

        final ImageView imageDP = new ImageView();
        String imagePathD = "/draftpool.png";
        Image draft = new Image(imagePathD, 280, 350, false, false);



        GridPane gridDP = new GridPane();
        gridDP.setPrefSize(280, 350);
        gridDP.setHgap(3);
        gridDP.setVgap(8);
        BackgroundImage myBI= new BackgroundImage(draft,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        gridDP.setBackground(new Background(myBI));

        for(int row=0; row<3; row++){
            for(int col=0; col<3; col++){
                Button b= new Button();
                b.setOpacity(0.6);
                b.setPrefSize(58, 58);
                //b.setBackground(new BackgroundImage());
                gridDP.add(b, col, row);

            }
        }
        gridDP.setAlignment(Pos.CENTER);
        pane.getChildren().add(gridDP);





        return pane;
    }


    private static AnchorPane paginationPlayers(){
        Pagination pagination = new Pagination(3, 0);
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

    private static VBox createPageWp(int pageIndex){
        VBox box = new VBox(5);

        Pane paneWP = new Pane();

        switch (pageIndex) {
            case 0: {
                GridPane gridWP = new GridPane();
                gridWP.setPrefSize(280, 280);
                gridWP.setHgap(3);
                gridWP.setVgap(8);
                String imagePath = "/Virtus.png";
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
                }
                paneWP.getChildren().add(gridWP);
                /*final ImageView wpPlayer = new ImageView();
                String imagePath = "/Virtus.png";
                Image image1 = new Image(imagePath, 280, 280, false, false);
                wpPlayer.setImage(image1);*/
                break;
            }
            case 1: {
                GridPane gridWP = new GridPane();
                gridWP.setPrefSize(280, 280);
                gridWP.setHgap(3);
                gridWP.setVgap(8);
                String imagePath = "/SunCatcher.png";
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
                }
                paneWP.getChildren().add(gridWP);

                /*
                final ImageView wpPlayer = new ImageView();

                String imagePath = "/SunCatcher.png";
                Image image1 = new Image(imagePath, 280, 280, false, false);
                wpPlayer.setImage(image1);*/
                break;
            }
            case 2: {
                GridPane gridWP = new GridPane();
                gridWP.setPrefSize(280, 280);
                gridWP.setHgap(3);
                gridWP.setVgap(8);
                String imagePath = "/KaleidoscopicDream.png";
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
                }
                paneWP.getChildren().add(gridWP);

                /*
                final ImageView wpPlayer = new ImageView();

                String imagePath = "/KaleidoscopicDream.png";
                Image image1 = new Image(imagePath, 280, 280, false, false);
                wpPlayer.setImage(image1);*/
                break;
            }


        }

        box.getChildren().add(paneWP);
        return box;
    }

    private static AnchorPane paginationPublic(InitializerView init){
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

    private static VBox createPagePb(int pageIndex,InitializerView init){
        VBox box = new VBox(5);
        ArrayList<String> pbCards = init.getPbCard();
        final ImageView pbPlayers = new ImageView();
        switch (pageIndex) {
            case 0: {
                String imagePath = pbCards.get(0);
                Image image1 = new Image(imagePath, 200, 280, false, false);
                pbPlayers.setImage(image1);
                break;
            }
            case 1: {
                String imagePath = pbCards.get(1);
                Image image1 = new Image(imagePath, 200, 280, false, false);
                pbPlayers.setImage(image1);
                break;
            }
            case 2: {
                String imagePath = pbCards.get(2);
                Image image1 = new Image(imagePath, 200, 280, false, false);
                pbPlayers.setImage(image1);
                break;
            }


        }

        box.getChildren().add(pbPlayers);
        return box;
    }

    private static AnchorPane paginationTool(InitializerView init){
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

    private static VBox createPageTool(int pageIndex,InitializerView init){
        VBox box = new VBox(5);
        ArrayList<String> toolCards = init.getToolCard();
        final ImageView tlPlayer = new ImageView();
        switch (pageIndex) {
            case 0: {
                String imagePath = toolCards.get(0);
                Image image1 = new Image(imagePath, 200, 280, false, false);
                tlPlayer.setImage(image1);
                break;
            }
            case 1: {
                String imagePath = toolCards.get(1);
                Image image1 = new Image(imagePath, 200, 280, false, false);
                tlPlayer.setImage(image1);
                break;
            }
            case 2: {
                String imagePath = toolCards.get(2);
                Image image1 = new Image(imagePath, 200, 280, false, false);
                tlPlayer.setImage(image1);
                break;
            }


        }

        tlPlayer.setOnMouseClicked(e-> {
                    Stage stage  = new Stage();
                    Scene sce= new Scene(new Group());

                    stage.setScene(sce);
                    stage.show();
                }
        );

        box.getChildren().add(tlPlayer);
        return box;
    }

}
