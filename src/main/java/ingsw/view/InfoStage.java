package ingsw.view;

import javafx.animation.Transition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Shear;
import javafx.stage.Stage;
import javafx.util.Duration;

public class InfoStage {

    public static Stage displayAuthor(Stage stage){
        Scene scene = new Scene(new Group());
        stage.setTitle("Author");
        stage.setWidth(500);
        stage.setHeight(300);

        VBox vbox = new VBox();
        vbox.setLayoutX(20);
        vbox.setLayoutY(20);


        final String content = "            Il progetto è stato svolto dai laureandi \n\n"+
                                "   Diego Zucca\n\n\n"+"                Elio Salvini\n\n\n"+
                                "                                 Alessio Tonelli\n\n\n\n\n"+"              " +
                "                       Si ringrazia il Politecnico \n" +
                "                       per l'esperienza acquisita nel creare il gioco" ;
        final Text text = new Text(20, 20, "");
        text.getTransforms().add(new Shear(-0.35, 0));
        text.setFont(new Font(10));
        final Transition animation = new Transition() {
            {
                setCycleDuration(Duration.millis(15000));
            }

            protected void interpolate(double frac) {
                final int length = content.length();
                final int n = Math.round(length * (float) frac);
                text.setText(content.substring(0, n));
            }

        };

        animation.play();


        final ImageView logo = new ImageView();
        String imagePath = "images/logo.png";
        Image imageLogo = new Image(imagePath, 100, 100, false, false);
        logo.setImage(imageLogo);
        logo.setLayoutX(300);
        logo.setLayoutY(100);

        vbox.getChildren().add(text);
        vbox.setSpacing(10);
        ((Group) scene.getRoot()).getChildren().addAll(vbox, logo);

        stage.setScene(scene);
        stage.show();

        return stage;
    }


}
