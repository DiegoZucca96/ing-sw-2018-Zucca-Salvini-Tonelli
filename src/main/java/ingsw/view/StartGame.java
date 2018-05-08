package ingsw.view;

import ingsw.controller.RMIController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.transform.Shear;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.text.Font;
import javafx.scene.effect.Reflection;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class StartGame  extends Application{

    Scene scene;
    Stage window;
    private RMIController controller;
    //private GuiCaller guiCaller;



    public static void main(String[] args) {

        Application.launch(args);
    }

    public void start(Stage stage) throws Exception{


        Registry registry=LocateRegistry.getRegistry("localhost",1080);
        this.controller=(RMIController) registry.lookup("controller");



        window=stage;
        window.setWidth(1200);
        window.setHeight(700);
        VBox root = new VBox();
        scene = new Scene(root);



        //CREO IMMAGINE
        final ImageView selectedImage = new ImageView();
        String imagePath = "/SagradaMenu.png";
        Image image1 = new Image(imagePath, 1200, 700, false, false);
        selectedImage.setImage(image1);



        //CREO BOTTONE PLAY
        Button btnPlay= new Button();
        btnPlay.setText("PLAY");
        btnPlay.setFont(new Font("Tahoma", 40));
        btnPlay.getTransforms().add(new Shear(-0.50, 0));
        btnPlay.setEffect(new Reflection());
        btnPlay.setOnAction(event -> loginStage());
        btnPlay.setLayoutX(900.0);
        btnPlay.setLayoutY(300.0);


        //TEMPO LAMPEGGIO
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        final KeyValue kv = new KeyValue(btnPlay.opacityProperty(), 0);
        final KeyFrame kf = new KeyFrame(Duration.millis(2000), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();




        //ATTACH DI TUTTO: Pane e no StackPane
        Pane begin = new Pane();
        begin.getChildren().addAll(selectedImage, btnPlay);
        root.getChildren().addAll(begin);

        //MOSTRA
        btnPlay.getStylesheets().add("me/controlStyle.css");
        window.setScene(scene);
        window.setTitle("Sagrada");
        window.show();
    }




    //NUOVO STAGE PER EFFETTUARE IL LOGIN, SE è LA PRIMA VOLTA PUò REGISTRARSI
    private Stage loginStage () {

        Stage stage = new Stage();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(12);

        HBox hbButtons = new HBox();
        hbButtons.setSpacing(10.0);


        final ImageView imageOk = new ImageView();
        String imagePath = "/ok.png";
        Image image2 = new Image(imagePath, 20, 20, false, false);
        imageOk.setImage(image2);
        Button btnLogin = new Button( "Log in", imageOk);
        btnLogin.setOnAction(e->{
            if(true) {

                stage.close();
                window.close();
                WindowPattern.display();
                Private.display();
            }
        });

        Label lblName = new Label("Nickname:");
        TextField tfName = new TextField();
        Label lblPw = new Label("Password: ");
        PasswordField pfPw = new PasswordField();
        Label reg = new Label("Not an account?");
        Hyperlink link = new Hyperlink();
        link.setText("Click to register");
        link.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                signUp(stage);
            }
        });

        hbButtons.getChildren().add(btnLogin);
        grid.add(lblName, 0, 0);
        grid.add(tfName, 1, 0);
        grid.add(lblPw, 0, 1);
        grid.add(pfPw, 1, 1);
        grid.add(reg, 0, 2);
        grid.add(link, 1, 2);
        grid.add(hbButtons, 0, 3, 2, 3);



        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHalignment(HPos.RIGHT);
        grid.getColumnConstraints().add(column1);

        ColumnConstraints column2 = new ColumnConstraints();
        column2.setHalignment(HPos.LEFT);
        grid.getColumnConstraints().add(column2);

        Scene scene1 = new Scene(grid, 350, 250);
        stage.setScene(scene1);
        stage.setTitle("Nickname");
        stage.show();

        return stage;
    }

    //REGISTRAZIONE AL SERVER
    private Stage signUp (Stage oldStage) {

        Stage stage = new Stage();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(12);

        HBox hbButtons = new HBox();
        hbButtons.setSpacing(10.0);


        Button btnSubmit = new Button("Submit");
        Button btnExit = new Button("Exit");


        Label lblName = new Label("User name:");
        TextField tfName = new TextField();
        Label lblPwd = new Label("Password:");
        PasswordField pfPwd = new PasswordField();



        //Verifca che non ci sia nessun altro utente con lo stesso nickname
        btnSubmit.setOnAction(e-> {
            Boolean answer=confirmHandler(oldStage);
            if(answer){
                stage.close();
            }

            else {
                Label warning = new Label("Nickname already exists");
                warning.setTextFill(Color.RED);
                grid.add(warning, 1,3, 2, 1);
            }
        });
        btnExit.setOnAction(e-> stage.close());

        hbButtons.getChildren().addAll(btnSubmit, btnExit);
        grid.add(lblName, 0, 0);
        grid.add(tfName, 1, 0);
        grid.add(lblPwd, 0, 1);
        grid.add(pfPwd, 1, 1);
        grid.add(hbButtons, 2, 3, 2, 1);



        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHalignment(HPos.RIGHT);
        grid.getColumnConstraints().add(column1);

        ColumnConstraints column2 = new ColumnConstraints();
        column2.setHalignment(HPos.LEFT);
        grid.getColumnConstraints().add(column2);

        Scene scene2 = new Scene(grid, 450, 300);
        stage.setScene(scene2);
        stage.setTitle("Nickname");
        stage.show();

        return stage;
    }

    private boolean confirmHandler(Stage stage){
        //Tutta la parte di ricerca del server di un nickname uguale a quello appena scritto che ritornerà un boolean

        Boolean answer=false;

        if(answer){
            stage.close();
        }
        return answer;
    }
}
