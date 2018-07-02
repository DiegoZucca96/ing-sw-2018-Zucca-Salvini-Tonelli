package ingsw.view.GUI;

import ingsw.Client;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**Author : Alessio Tonelli - Diego Zucca
 *
 * this is the last class used in GUI to display the winner
 * @see MultiScore , class to generate random numbers display
 * */

public class Victory {

    private Label timerLabel ;
    private final String styleSheet ="-fx-text-fill: goldenrod; -fx-font: italic 70 \"serif\"; -fx-padding: 0 0 20 0";
    private Client client;

    /**Displays image and calls MultiScore for each player.*/
    public void start(Client c) {

        this.client=c;
        final int[] singleShowPoints = {0};
        // Setup the Stage and the Scene (the scene graph)
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Victory");
        Pane root= new Pane();
        Scene scene = new Scene(root, 1200, 700);

        GridPane grid = new GridPane();
        grid.setLayoutX(100);
        grid.setLayoutY(10);
        grid.setVgap(25);

        //BACKGROUND
        String imagePath = "/victory.jpg";
        Image image = new Image(imagePath, 1200, 700, false, false);
        final ImageView background= new ImageView();
        background.setImage(image);
        scene.setOnMouseEntered( e-> {
            if(singleShowPoints[0] == 0){
                singleShowPoints[0]++;
                for (int i=0; i<client.getNumberOfPlayers(); i++) {
                    timerLabel=new Label();
                    timerLabel.setStyle(styleSheet);
                    grid.add(timerLabel, 0, i);
                    MultiScore threadScore = new MultiScore(client.getListOfMatchPlayers().get(i),timerLabel, client,grid);
                    threadScore.start();
                }
            }

        });

        primaryStage.setOnCloseRequest(e -> {
            singleShowPoints[0]--;
            primaryStage.close();
        });
        root.getChildren().addAll(background, grid);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}