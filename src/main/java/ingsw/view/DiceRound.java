package ingsw.view;

import ingsw.model.InitializerView;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class DiceRound {

    public static Stage displayDice(Stage stage, int round, InitializerView init){

        GridPane grid = new GridPane();
        //StackPane stack1=new StackPane();

        //init.getDie

        Scene scene = new Scene(grid, 200, 200);
        stage.show();
        return stage;
    }
}
