package ingsw.view;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

//ASSEGNAZIONE CARTA PRIVATA

public class Private {

    public static void display(){



        StackPane p = new StackPane();
        Scene s1 = new Scene(p);
        Stage privateObj = new Stage();
        privateObj.setWidth(240);
        privateObj.setHeight(320);

        final ImageView selectedImage = new ImageView();
        String imagePath = "/p1.png";
        Image imagepv = new Image(imagePath, 240, 320, false, false);
        selectedImage.setImage(imagepv);


        p.getChildren().add(selectedImage);
        privateObj.setTitle(" Your Private Card");
        privateObj.setScene(s1);
        privateObj.show();

    }
}
