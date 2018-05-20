package ingsw.view;



import ingsw.controller.RMIController;
import ingsw.model.Cell;
import ingsw.model.InitializerView;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
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

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
    private static Integer timeSeconds;
    private static Task copyWorker;
    private static int numFiles = 20;

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


    public static void display(Stage primaryStage, InitializerView init, String comment, int i, List<Cell> myWindow) {

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
        timeline.setCycleCount(timeSeconds);
        timeline.setAutoReverse(true);
        final KeyValue kv = new KeyValue(lblProgress.opacityProperty(), 0);
        final KeyFrame kf = new KeyFrame(Duration.millis(2000), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();


        //
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        event -> {
                            timeSeconds--;
                            timeSeconds--;

                            if (timeSeconds <= 0 && i==1) {
                                timeline.stop();

                                WPRendering.display(init);
                                Private.display(init);
                                primaryStage.close();
                            }

                            if (timeSeconds <= 0 && i==2) {
                                timeline.stop();

                                Play.display(myWindow,init);
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


        root.getChildren().addAll(leftPane, rightPane);
        root.setStyle("-fx-background-color:black;");

        primaryStage.initModality(Modality.WINDOW_MODAL);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.setOpacity(0.7);
        primaryStage.showAndWait();
    }

    public static void setTimeStart(int timer) {
        timeSeconds = timer;
    }
}
