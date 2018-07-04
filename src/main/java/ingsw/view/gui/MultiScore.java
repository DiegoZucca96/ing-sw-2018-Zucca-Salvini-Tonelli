package ingsw.view.gui;

import ingsw.client.Client;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import java.util.Random;

/**Author : Alessio Tonelli - Diego Zucca
 *
 * this class is invoked by Victory in order to count parallel score.
 *
 * */

class MultiScore implements Runnable {
    private final GridPane grid;
    private Thread myThread;
    private Timeline timeline;
    private Label label;
    private Integer timeSeconds =0;
    private Integer numberRandom;
    private Client client;
    private String namePlayer;

    /**Constructor*/
    MultiScore(String s, Label label, Client c, GridPane grid) {
        this.label= label;
        this.client=c;
        this.namePlayer = s;
        this.numberRandom = new Random().nextInt(999);
        this.grid = grid;
    }

    /**Creates timeline to generate random number at same time for each players and at the end of time prints the winner.*/
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
                new KeyFrame(Duration.seconds(0.01), event1 -> {
                    timeSeconds++;
                    numberRandom = new Random().nextInt(999);
                    // update timerLabel
                    label.setText(namePlayer+"                      "+numberRandom.toString());
                    if (timeSeconds >= 600) {
                        label.setText(namePlayer+"                       "+client.getScore(namePlayer));
                        Label lab = new Label("The winner is"+"     "+client.findWinner());
                        lab.setStyle("-fx-text-fill: goldenrod; -fx-font: italic 90 \"serif\"; -fx-padding: 0 0 20 0");
                        grid.add(lab,0,client.getNumberOfPlayers());
                        timeline.stop();
                    }
                }));
        timeline.playFromStart();
    }

    /**Create new Thread which runs whit method run.*/
    public void start() {
        if (myThread == null) {
            myThread = new Thread(this);
            myThread.start();
        }
    }
}

