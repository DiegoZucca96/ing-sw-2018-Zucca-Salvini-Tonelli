package ingsw.view;

import ingsw.Client;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Shear;
import javafx.stage.Stage;
import javafx.util.Duration;

/**Author : Alessio Tonelli _ Diego Zucca _ Elio Salvini
 *
 * GUI: start first Scene
 *
 * */

public class GUI  {

    private Scene scene;
    private String saveUsername;
    private Stage window;
    private Client client;
    private String userName;

    public void display(Client c) {


        this.client=c;
        window = new Stage();
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
        btnPlay.setStyle("-fx-border-radius: 15.0; -fx-background-radius: 15.0; -fx-border-color: black");
        btnPlay.setOnAction(event -> loginStage(client));
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
        window.setScene(scene);
        window.setTitle("Sagrada");
        window.resizableProperty().setValue(Boolean.FALSE);
        window.show();
    }




    //NUOVO STAGE PER EFFETTUARE IL LOGIN, SE è LA PRIMA VOLTA PUò REGISTRARSI
    private Stage loginStage (Client client) {

        Stage stage = new Stage();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(12);

        HBox hbButtons = new HBox();
        hbButtons.setSpacing(10.0);

        //USERNAME E PASSWORD
        Label lblName = new Label("Nickname:");
        TextField tfName = new TextField();
        Label reg = new Label("Not an account?");
        Hyperlink link = new Hyperlink();
        link.setText("Click to register");
        link.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                signUp(stage, client);
                stage.close();
            }
        });



        //IMMAGINE DI OK PER LOG IN
        final ImageView imageOk = new ImageView();
        Label warning1 = new Label("");
        grid.add(warning1, 1,4);
        String imagePath = "/ok.png";
        Image image2 = new Image(imagePath, 20, 20, false, false);
        imageOk.setImage(image2);
        Button btnLogin = new Button( "Log in", imageOk);
        btnLogin.setOnAction(e->{
            // SERVER VERIFICA LE CREDENZIALI
            userName = tfName.getText();
                /*if (!username.isEmpty()) {
                    try {
                        if(!controller.getListOfPlayers().contains(username)){
                            if (controller.access(username)) {
                                if(controller.getListOfPlayers().size()<4) {
                                    controller.addPlayers(username);
                                    stage.close();
                                    window.close();
                                    InitializerView init = null;
                                    try {
                                        init = controller.initializeView();
                                    } catch (RemoteException e1) {
                                        e1.printStackTrace();
                                    }
                                    Loading.display(new Stage(), init, "LOADING MATCH", 1, null, null, null, controller);

                                } else {
                                    warning1.setText("MATCH IS FULL, SORRY");
                                    warning1.setTextFill(Color.RED);
                                }
                            } else {
                                warning1.setText("REGISTER NOW");
                                warning1.setTextFill(Color.RED);
                            }
                        }else{
                            warning1.setText("NICKNAME ALREADY TAKEN");
                            warning1.setTextFill(Color.RED);
                        }
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }
                }*/
                if (!userName.isEmpty()) {
                    if(!client.matchFound() && client.login(userName)){
                        stage.close();
                        window.close();
                        new Loading(client).display(new Stage(), "LOADING MATCH", null);
                    }else{
                        if(client.matchFound() && client.iAmBanned(userName)){
                            //NB! Viene ricreata la schermata iniziale del gioco, ma non è sincronizzato con tutto il resto
                            new PlayGame(client).display(client.getWP(userName));
                        }
                        else{
                            warning1.setText("Match is full, sorry");
                            warning1.setTextFill(Color.RED);
                        }
                    }
                }else{
                    warning1.setText("Insert at least one letter");
                    warning1.setTextFill(Color.RED);
                }
        });



        hbButtons.getChildren().add(btnLogin);
        grid.add(lblName, 0, 0);
        grid.add(tfName, 1, 0);
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
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.show();

        return stage;
    }

    //REGISTRAZIONE AL SERVER
    private Stage signUp (Stage oldStage, Client client) {

        Stage stage = new Stage();
        Pane root = new Pane();
        GridPane grid = new GridPane();
        grid.setLayoutX(50);
        grid.setLayoutY(50);
        grid.setHgap(10);
        grid.setVgap(12);

        HBox hbButtons = new HBox();
        hbButtons.setSpacing(10.0);


        Button btnSubmit = new Button("Submit");
        Button btnExit = new Button("Exit");


        Label lblName = new Label("User name:");
        TextField tfName = new TextField();



        //IMMAGINE DI BACKGROUND
        final ImageView backgrundImage = new ImageView();
        Label warning1 = new Label("");
        grid.add(warning1, 1,4);
        String imagePath = "/Tool0.png";
        Image image = new Image(imagePath, 450, 600, false, false);
        backgrundImage.setImage(image);


        //Verifca che non ci sia nessun altro utente con lo stesso nickname
        btnSubmit.setOnAction(e-> {
            saveUsername = tfName.getText();
            if (!saveUsername.isEmpty()) {
                if(client.register(saveUsername)){
                    stage.close();
                    oldStage.show();
                }else {
                    warning1.setText("NICKNAME ALREADY EXISTS");
                    warning1.setTextFill(Color.RED);
                }
            }
        });
        btnExit.setOnAction(e-> stage.close());

        hbButtons.getChildren().addAll(btnSubmit, btnExit);
        grid.add(lblName, 0, 0);
        grid.add(tfName, 1, 0);
        grid.add(hbButtons, 2, 3, 2, 1);


        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHalignment(HPos.RIGHT);
        grid.getColumnConstraints().add(column1);

        ColumnConstraints column2 = new ColumnConstraints();
        column2.setHalignment(HPos.LEFT);
        grid.getColumnConstraints().add(column2);

        root.getChildren().addAll(backgrundImage, grid);
        Scene scene2 = new Scene(root, 450, 300);
        stage.setScene(scene2);
        stage.setTitle("Nickname");
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.show();

        return stage;
    }

    public String getUserName() {
        return userName;
    }
}
