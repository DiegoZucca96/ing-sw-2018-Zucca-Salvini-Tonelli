package ingsw.view;

import ingsw.model.InitializerView;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

//ASSEGNAZIONE CARTA PRIVATA

public class Private {

    public static void display(InitializerView init){



        StackPane p = new StackPane();
        Scene s1 = new Scene(p);
        Stage privateObj = new Stage();
        privateObj.setWidth(240);
        privateObj.setHeight(320);
        String pvCard = init.getPvCard().get(0);
        final ImageView selectedImage = new ImageView();
        String imagePath = pvCard;
        Image imagepv = new Image(imagePath, 240, 320, false, false);
        selectedImage.setImage(imagepv);

        privateObj.resizableProperty().setValue(Boolean.FALSE);

        Platform.setImplicitExit(false);

        privateObj.setOnCloseRequest(event -> event.consume());

        p.getChildren().add(selectedImage);
        privateObj.setTitle(" Your Private Card");
        privateObj.setScene(s1);
        privateObj.show();

    }
}
