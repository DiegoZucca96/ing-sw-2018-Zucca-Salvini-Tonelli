package ingsw.view;

import ingsw.model.InitializerView;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

//SCELTA DELLE WINDOW PATTERN


public class WindowPattern {

    public static void display(InitializerView init){

        GridPane gp= new GridPane();
        gp.setHgap(10);
        gp.setVgap(10);
        gp.setPadding(new Insets(0, 10, 0, 10));
        Scene s2= new Scene(gp);
        Stage windowPattern = new Stage();
        windowPattern.setWidth(1260);
        windowPattern.setHeight(320);

        //Con queste due operazioni ottengo tutti i parametri che sono stati generati dal model (Problema di sincronizzarli con quelli salvati nel model)
        ArrayList<String> images = init.getImages();

        final ImageView wp1 = new ImageView();
        String imagePath1 = images.get(0);;
        Image image1 = new Image(imagePath1, 300, 300, false, false);
        wp1.setImage(image1);
        gp.add(wp1, 0,0);

        final ImageView wp2 = new ImageView();
        String imagePath2 = images.get(1);;
        Image image2 = new Image(imagePath2, 300, 300, false, false);
        wp2.setImage(image2);
        gp.add(wp2, 1,0);

        final ImageView wp3 = new ImageView();
        String imagePath3 = images.get(2);;
        Image image3 = new Image(imagePath3, 300, 300, false, false);
        wp3.setImage(image3);
        gp.add(wp3, 2,0);

        final ImageView wp4 = new ImageView();
        String imagePath4 = images.get(3);;
        Image image4 = new Image(imagePath4, 300, 300, false, false);
        wp4.setImage(image4);
        gp.add(wp4, 3,0);

        wp1.setOnMouseClicked(event -> {
            Play.display(imagePath1,init);
            windowPattern.close();
        });
        wp2.setOnMouseClicked(event -> {
            Play.display(imagePath2,init);
            windowPattern.close();
        });
        wp3.setOnMouseClicked(event -> {
            Play.display(imagePath3,init);
            windowPattern.close();
        });
        wp4.setOnMouseClicked(event -> {
            Play.display(imagePath4,init);
            windowPattern.close();
        });



        windowPattern.setTitle("Choose window pattern");
        windowPattern.setScene(s2);
        windowPattern.show();
    }


}
