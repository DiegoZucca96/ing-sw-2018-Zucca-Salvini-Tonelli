package ingsw.view;


import ingsw.controller.RMIController;
import ingsw.model.Cell;
import ingsw.model.InitializerView;
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

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;


/**Author : Alessio Tonelli
 *
 * this class is used two times :
 *
 * first: case parameter i = 1 ___ client must wait other clients enter the game
 *
 * second: case parameter i = 2 ___ after choosing window pattern wait for other players' choice
 * */

public class Loading {

    private static Timeline timeline;
    private static Task copyWorker;
    private static int numFiles = 20;
    private static int access=0;
    private static int single = 0;

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


    public static void display(Stage primaryStage, ArrayList<ArrayList<Integer>[][]> randomWps, String comment, int i, List<Cell> myWindow, String titleWp, String diffWp) throws RemoteException {

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
                            final Label lbl1 = new Label(comment);
                            lblProgress.setFont(Font.font("GB18030 Bitmap", FontWeight.BOLD, 20));
                            lblProgress.setTextFill(Color.WHITE);
                            if(i==1){
                                try {
                                    if(client.getSizeOfPlayers()==1){ //Fa qualcosa mentre aspetta
                                        //System.out.println(controller.getTimeSearch());
                                        single++;
                                    }
                                    if(client.getSizeOfPlayers()==2 && access == 0 && single == 0){
                                        client.search();
                                        access++;
                                    }
                                    //System.out.println("Timer pari a:"+controller.getTimeSearch());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try{
                                    if(client.getTimeSearch()==0) {
                                        timeline.stop();
                                        WPRendering.display(init);
                                        Private.display(init);
                                        primaryStage.close();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            if (i==2) {
                                try {
                                    while(controller.getWindowChosen().size()< controller.getListOfPlayers().size())
                                        lbl1.setText("Players are choosing, wait");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                timeline.stop();
                                Play.display(myWindow,init,titleWp,diffWp);
                                primaryStage.close();
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

        final Label lbl2 = new Label("");
        lbl2.setFont(Font.font("GB18030 Bitmap", FontWeight.BOLD, 10));
        lbl2.setTextFill(Color.WHITE);

        try {
            if(controller.getListOfPlayers().size()==2){
                lbl2.setText("Found one!");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            if(controller.getListOfPlayers().size()==3){
                lbl2.setText("Found two! Only one remaining...");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }


        root.getChildren().addAll(leftPane, rightPane,lbl2);
        root.setStyle("-fx-background-color:black;");

        primaryStage.initModality(Modality.WINDOW_MODAL);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.setOpacity(0.7);
        primaryStage.showAndWait();
    }

}
