package ingsw.view;

import javafx.animation.PauseTransition;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Warning {
    public Warning(String alert){
        Stage stage= new Stage();
        Pane root = new Pane();
        root.setPrefSize(300, 100);
        final Scene scene = new Scene(root);
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished( event -> stage.close() );
        delay.play();
        Text text = new Text(alert);
        text.setFill(Color.RED);
        text.setLayoutX(50);
        text.setLayoutY(50);
        root.getChildren().add(text);
        root.setStyle("-fx-background-color:black;");
        stage.setScene(scene);
        stage.setTitle("WARNING");
        stage.show();
    }
}
