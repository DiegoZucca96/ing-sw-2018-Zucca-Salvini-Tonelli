package ingsw.view;


import ingsw.Client;
import ingsw.controller.ViewWP;
import ingsw.model.ViewData;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

public class PlayGame {

    private Client client;
    private ViewData init ;


    public PlayGame(Client client){
        this.client=client;
        init= client.initializeView();
    }


    public void display(ViewWP myWindow){
        Stage stage=new Stage();
        stage.setWidth(1200);
        stage.setHeight(700);
        Pane root = new Pane();
        Scene scene = new Scene(root);

        //GLIGLIA DI BOTTONI PER LA ROUNDTRACK
        GridPane gridRound=addGridRound();
        gridRound.setLayoutY(10);
        gridRound.setLayoutX(336);


        //IMMAGINE BACKGROUND
        final ImageView backGround = new ImageView();
        String imagePathB = "/backgroundtable.jpg";
        Image imageB = new Image(imagePathB, 1200, 700, false, false);
        backGround.setImage(imageB);

        //PRIVATE
        Pane pvPane = Pane(init.getPvCard(), 0);
        pvPane.setLayoutX(550);
        pvPane.setLayoutY(400);

        //TOOLCARD
        Pane toolPane1 = Pane(init.getToolCard(), 0);
        Pane toolPane2 = Pane(init.getToolCard(), 1);
        Pane toolPane3 = Pane(init.getToolCard(), 2);

        GridPane toolGrid = new GridPane();
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

        GridPane pbGrid = new GridPane();
        pbGrid.setLayoutX(1050);
        pbGrid.setLayoutY(20);
        pbGrid.setVgap(20);

        pbGrid.add(pbPane1, 0, 0);
        pbGrid.add(pbPane2, 0, 1);
        pbGrid.add(pbPane3, 0, 2);

        //LA MIA WP
        GridPane myWindowGrid = new GridPaneWindow(2, myWindow, client);
        myWindowGrid.setLayoutX(200);
        myWindowGrid.setLayoutY(350);

        //BOTTONI
        BorderPane btnPane = new BorderPane();
        Button skipBtn = new Button("end Turn");
        btnPane.setLeft(skipBtn);
        Button useToolBtn = new Button("use Tool");
        btnPane.setCenter(useToolBtn);
        Button takeDieBtn = new Button("take Die");
        btnPane.setRight(takeDieBtn);
        btnPane.setLayoutX(200);
        btnPane.setLayoutY(600);

        //WP AVVERSARIE
        GridPane eachGrid = new GridPane();
        eachGrid.setHgap(40);
        eachGrid.setPrefSize(720, 200);
        eachGrid.setLayoutX(240);
        eachGrid.setLayoutY(80);
        for(int index= 0; index < 3; index++){
            GridPane gridPane = createGridEn(index);

            eachGrid.add(gridPane, index, 0);
        }


        root.getChildren().addAll(backGround, toolGrid, pbGrid, gridRound, myWindowGrid, btnPane, eachGrid, pvPane);
        stage.setScene(scene);
        stage.setTitle("Sagrada - " +client.getName());
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.show();
    }

    private Pane Pane(ArrayList<String> stringCard, int i){
        Pane pane = new Pane();
        pane.setLayoutY(200);
        pane.setLayoutX(120);


        final ImageView View = new ImageView();
        String imagePathT = stringCard.get(i);
        Image imageT = new Image(imagePathT, 120, 200, false, false);
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
        for(int numDie=0; numDie<2; numDie++){
            Button button = new Button();
            //mettere immagine del dado

            grid.add(button, numDie, 0);
        }
        root.getChildren().add(grid);
        stage.setScene(scene);
        stage.show();
        return stage;
    }

    private GridPane myWindow(ViewWP myWindow){

        GridPane gridWP = new GridPane();
        gridWP.setPrefSize(200, 200);
        Image myImage = new Image("/Symphony of Light.png", 200, 200, false, false);
        BackgroundImage myBI= new BackgroundImage(myImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        gridWP.setBackground(new Background(myBI));

        return gridWP;

    }

    private GridPane createGridEn(int player){
        GridPane grid = new GridPane();

        grid.setPrefSize(200, 200);
        Image myImage = new Image("/Symphony of Light.png", 200, 200, false, false);
        BackgroundImage myBI= new BackgroundImage(myImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        grid.setBackground(new Background(myBI));


        return grid;
    }
}
