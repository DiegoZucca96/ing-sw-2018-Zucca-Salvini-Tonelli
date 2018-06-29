package ingsw.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**This is class which display the winner in case he is the last remained.*/
public class VictoryAlone {

    public void start(String c) {

        String winner=c;

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
        Label victoryLabel = new Label();
        victoryLabel.setText("\n\nIl vincitore a tavolino è :\n\n            "+winner);
        victoryLabel.setStyle("-fx-text-fill: goldenrod; -fx-font: italic 100 \"serif\"; -fx-padding: 0 0 20 0");
        victoryLabel.setMaxWidth(Double.MAX_VALUE);
        victoryLabel.setAlignment(Pos.CENTER);
        grid.add(victoryLabel,0,0);
        GridPane.setFillWidth(victoryLabel, true);

        root.getChildren().addAll(background, grid);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
