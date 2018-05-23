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
        Scene scene = new Scene(root, 200, 200, Color.BLACK);
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished( event -> stage.close() );
        delay.play();
        Text text = new Text(alert);
        text.setFill(Color.RED);
        stage.setScene(scene);
        stage.setTitle("WARNING");
        stage.show();
    }
}
