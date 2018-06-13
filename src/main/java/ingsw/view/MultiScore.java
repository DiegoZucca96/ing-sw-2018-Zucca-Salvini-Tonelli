package ingsw.view;


import ingsw.Client;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.util.Random;


/**Author : Alessio Tonelli
 *
 * this class is invoked by Victory in order to count parallel score
 *
 * */


class MultiScore implements Runnable {
    private final GridPane grid;
    private Thread myThread;
    //private static final Integer STARTTIME = 999;
    private Timeline timeline;
    private Label label;
    private Integer timeSeconds =0;
    private Integer numberRandom;
    private Client client;
    private String namePlayer;

    MultiScore(String s, Label label, Client c, GridPane grid) {
        this.label= label;
        this.client=c;
        this.namePlayer = s;
        this.numberRandom = new Random().nextInt(999);
        this.grid = grid;
    }


    public synchronized void run() {


        if (timeline != null) {
            timeline.stop();
        }

        timeSeconds = 0;
        // update timerLabel
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        // KeyFrame event handler
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(0.005), event1 -> {
                    timeSeconds++;
                    numberRandom = new Random().nextInt(999);
                    // update timerLabel
                    label.setText(namePlayer+"                      "+numberRandom.toString());
                    if (timeSeconds >= 5) {
                        label.setText(namePlayer+"                       "+client.getScore(namePlayer));
                        Label lab = new Label("The winner is"+"     "+client.findWinner());
                        lab.setStyle("-fx-text-fill: goldenrod; -fx-font: italic 90 \"serif\"; -fx-padding: 0 0 20 0");
                        grid.add(lab,0,client.getNumberOfPlayers());
                        timeline.stop();
                    }
                }));
        label.setText(namePlayer+"                      "+numberRandom.toString());
        timeline.playFromStart();
    }


    public void start() {
        if (myThread == null) {
            myThread = new Thread(this);
            myThread.start();
        }
    }
}

