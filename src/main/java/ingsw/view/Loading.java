package ingsw.view;



import ingsw.model.InitializerView;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Loading {


    private static final Integer STARTTIME = 15;

    private static Timeline timeline;
    private static Integer timeSeconds = STARTTIME;


    private static Task copyWorker;
    private static int numFiles = 20;

    private static Task createWorker(final int numFiles) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                for (int i = 0; i < numFiles; i++) {

                    Thread.sleep(1 * 250);

                }
                return true;
            }
        };
    }


    public static void display(Stage primaryStage, InitializerView init) {

        //MANAGE CYCLE PROGRESS
        final ProgressIndicator progressIndicator = new ProgressIndicator(0);

        progressIndicator.progressProperty().unbind();
        progressIndicator.setProgress(0);
        progressIndicator.progressProperty().unbind();
        copyWorker = createWorker(numFiles);
        progressIndicator.progressProperty().bind(copyWorker.progressProperty());


        //MANAGE THE LABEL "LOADING"
        final Label lblProgress = new Label("LOADING MATCH");
        lblProgress.setTextFill(Color.WHITE);

        timeline = new Timeline();
        timeline.setCycleCount(timeSeconds);
        timeline.setAutoReverse(true);
        final KeyValue kv = new KeyValue(lblProgress.opacityProperty(), 0);
        final KeyFrame kf = new KeyFrame(Duration.millis(2000), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();



        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        new EventHandler() {
                            @Override
                            public void handle(Event event) {
                                timeSeconds--;


                                if (timeSeconds <= 0) {
                                    timeline.stop();

                                    WindowPattern.display(init);
                                    Private.display(init);
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


        root.getChildren().addAll(leftPane, rightPane);
        root.setStyle("-fx-background-color:black;");

        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
