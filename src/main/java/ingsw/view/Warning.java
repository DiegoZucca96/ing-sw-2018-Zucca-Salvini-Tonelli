package ingsw.view;

import ingsw.Client;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


public class Warning {

    static boolean answer;
    static boolean stop;
    private String textMessage;
    Stage stage;

    public Warning(String alert, String title){
        Stage stage= new Stage();
        Pane root = new Pane();
        root.setPrefSize(300, 100);
        final Scene scene = new Scene(root);
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished( event -> stage.close() );
        delay.play();
        Text text = new Text(alert);
        text.setFill(Color.RED);
        text.setLayoutX(50);
        text.setLayoutY(50);
        root.getChildren().add(text);
        root.setStyle("-fx-background-color:black;");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }

    public Warning(Stage playgame, Client c){
        Stage stage = new Stage();
        Pane root = new Pane();
        Scene scene = new Scene(root, 400, 100);
        Label label = new Label("Connessione persa, ritorno al login");
        label.setAlignment(Pos.CENTER);
        PauseTransition delay = new PauseTransition(Duration.seconds(4));
        delay.setOnFinished( event -> {
                    playgame.close();
                    new GUI().display(c);
                    stage.close();
                });
        delay.play();
        root.getChildren().add(label);
        stage.setTitle("Connection lost");
        stage.setScene(scene);
        stage.show();
    }

    public Warning(Client c){
        stage = new Stage();
        Pane root = new Pane();
        Scene scene = new Scene(root, 600, 100);
        Label label = new Label("Connessione fallita, riconnessione in corso...");
        label.setLayoutX(130);
        label.setLayoutY(50);
        Button b = new Button("Annulla");
        b.setLayoutX(450);
        b.setLayoutY(50);
        b.setOnAction(e -> {
            stop=true;
            stage.close();
        });
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished( event -> {
            c.handleConnectionError();
            if(stop){
                stop=false;
                stage.close();
            }
        } );
        delay.play();
        root.getChildren().addAll(label,b);
        stage.setTitle("Connection failed");
        stage.setScene(scene);
        stage.show();
    }

    public Warning(String message){
        textMessage = message;
    }

    public Boolean display(){
        Stage window = new Stage();

        //Block event outside the window
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Exit?");
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(textMessage);
        Button b1 = new Button("Yes");
        Button b2 = new Button("No");
        b1.setOnAction(e -> {
            answer=true;
            window.close();
        });
        b2.setOnAction(e-> {
            answer= false;
            window.close();
        });

        HBox layout = new HBox(40);
        layout.getChildren().addAll(label, b1, b2);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        return answer;
    }

    public Stage getStage(){
        return stage;
    }

    public static boolean isStop() {
        return stop;
    }

    public static void setStop(boolean stop) {
        Warning.stop = stop;
    }
}
