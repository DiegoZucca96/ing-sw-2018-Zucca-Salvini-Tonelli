package ingsw.view;




import ingsw.Client;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**Author : Alessio Tonelli
 *
 * this is the last class used in GUI to display the winner
 *
 * click one time and it will calculate the score
 *
 * click the second time and it will give you the winner
 * */

public class Victory {

    private Label timerLabel ;
    private static int event =1;
    private Label nameLabel;
    private final String styleSheet ="-fx-text-fill: goldenrod; -fx-font: italic 100 \"serif\"; -fx-padding: 0 0 20 0";
    private Client client;

    public void start( Client c) {

        this.client=c;

        // Setup the Stage and the Scene (the scene graph)
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Victory");
        Pane root= new Pane();
        Scene scene = new Scene(root, 1200, 700);


        GridPane grid = new GridPane();
        grid.setLayoutX(100);
        grid.setLayoutY(10);
        grid.setVgap(50);
        grid.setHgap(400);

        //BACKGROUND
        String imagePath = "/victory.jpg";
        Image image = new Image(imagePath, 1200, 700, false, false);
        final ImageView background= new ImageView();
        background.setImage(image);



        scene.setOnMouseClicked( e-> {

            if(event ==1){
                event++;
                for (int i=0; i<4; i++) {

                    nameLabel= new Label();
                    timerLabel=new Label();
                    MultiScore threadScore = new MultiScore(timerLabel);
                    threadScore.start();
                    nameLabel.setText(client.getListOfPlayers().get(i));
                    nameLabel.setStyle(styleSheet);
                    timerLabel.setStyle(styleSheet);

                    grid.add(nameLabel, 0, i);
                    grid.add(timerLabel, 1, i);

                }
            }else{

                grid.setOpacity(0);
                Label lab = new Label("The winner is\n\n"+"     Tanta Roba");
                lab.setStyle(styleSheet);
                lab.setLayoutX(300);
                lab.setLayoutY(200);
                root.getChildren().add(lab);
            }

        });



        root.getChildren().addAll(background, grid);


        primaryStage.setScene(scene);
        primaryStage.show();
    }


}