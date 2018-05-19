package ingsw.view;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**Author : Alessio Tonelli
 *
 * This allows user to quit or make safe choice
 * */

public class ConfirmExit {

    static boolean answer;

    public static Boolean display(String title, String message){
        Stage window = new Stage();

        //Block event outside the window
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);
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
}
