package ingsw.view.GUI;

import ingsw.Client;
import ingsw.model.ViewWP;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**Author : Alessio Tonelli - Diego Zucca
 *
 * this class is used two times :
 *
 * first: case parameter myWindow is null ___ client must wait other clients enter the game.
 *
 * second: case parameter myWindow is not null because it was chosen ___ wait for other players' choice.
 * */

public class Loading {

    private static Timeline timeline;
    private static Task copyWorker;
    private static int numFiles = 20;
    private Client client;

    public Loading(Client c){
        this.client=c;
    }

    /**
     * Simple time function to make the progress circle.
     */
    private static Task createWorker(final int numFiles) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                for (int i = 0; i < numFiles; i++) {
                    Thread.sleep(1000);
                }
                return true;
            }
        };
    }

    /**Timeline on the progress circle and on label showing comment.
     * Button "Annulla" which allow client to exit from lobby.
     *
     * @param primaryStage is the main stage of the class
     * @param comment is the message which has to be shown while waiting
     * @param myWindow is the information passed from controller regarded to my window pattern
     */
    public void display(Stage primaryStage, String comment, ViewWP myWindow) {

        //MANAGE CYCLE PROGRESS
        final ProgressIndicator progressIndicator = new ProgressIndicator(0);
        progressIndicator.progressProperty().unbind();
        progressIndicator.setProgress(0);
        progressIndicator.progressProperty().unbind();
        copyWorker = createWorker(numFiles);
        progressIndicator.progressProperty().bind(copyWorker.progressProperty());

        //MANAGE THE LABEL "LOADING"
        final Label lblProgress = new Label(comment);
        lblProgress.setFont(Font.font("GB18030 Bitmap", FontWeight.BOLD, 20));
        lblProgress.setTextFill(Color.WHITE);

        //MANAGE TIME
        timeline = new Timeline();
        timeline.setCycleCount(10000000);
        timeline.setAutoReverse(true);
        final KeyValue kv = new KeyValue(lblProgress.opacityProperty(), 0);
        final KeyFrame kf = new KeyFrame(Duration.millis(1000), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();

        timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(500),
                        event -> {
                            if(myWindow==null){
                                if(client.waitForPlayers()){
                                    timeline.stop();
                                    new WPRendering().display(client.getRandomWps(), client);
                                    primaryStage.close();
                                }
                                else{
                                    if(client.getListOfPlayers().size()==0)
                                        timeline.stop();
                                }
                            }
                            if (myWindow!=null) {
                                if (client.readyToPlay()){
                                    timeline.stop();
                                    client.orderWPChoise();
                                    new PlayGame(client).display(myWindow);
                                    primaryStage.close();
                                }
                            }
                        }));
        timeline.playFromStart();

        Pane root = new Pane();
        Scene scene = new Scene(root, 500, 250);
        Pane leftPane = new Pane();
        leftPane.setLayoutX(100);
        leftPane.setLayoutY(100);
        leftPane.getChildren().add(progressIndicator);
        Pane rightPane = new Pane();
        rightPane.setLayoutX(200);
        rightPane.setLayoutY(100);
        rightPane.getChildren().addAll(lblProgress);
        if(myWindow==null){
            Button stopBtn = new Button("Annulla");
            stopBtn.setStyle("-fx-border-color: black");
            stopBtn.setOpacity(0.5);
            stopBtn.setLayoutX(400);
            stopBtn.setLayoutY(200);
            stopBtn.setOnAction(e->{
                client.removePlayer(client.getName());
                timeline.stop();
                primaryStage.close();
                new GUI().display(client);
            });
            root.getChildren().add(stopBtn);
        }
        root.getChildren().addAll(leftPane, rightPane);
        root.setStyle("-fx-background-color:black;");

        primaryStage.initModality(Modality.WINDOW_MODAL);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.setOpacity(0.7);
        primaryStage.showAndWait();
    }
}
