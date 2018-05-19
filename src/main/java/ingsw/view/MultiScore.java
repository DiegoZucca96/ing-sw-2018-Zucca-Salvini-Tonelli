package ingsw.view;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;


/**Author : Alessio Tonelli
 *
 * this class is invoked by Victory in order to count parallel score
 *
 * */


class MultiScore implements Runnable {
    private Thread myThread;
    private static final Integer STARTTIME = 0;
    private Timeline timeline;
    private Label label;
    private Integer timeSeconds = 0;

    MultiScore( Label label) {
        this.label= label;
    }


    public synchronized void run() {


        if (timeline != null) {
            timeline.stop();
        }

        timeSeconds = STARTTIME;

        // update timerLabel
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        // KeyFrame event handler
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(0.05), event1 -> {
                    timeSeconds++;
                    // update timerLabel
                    label.setText(
                            timeSeconds.toString());
                    if (timeSeconds >= 100) {
                        timeline.stop();
                    }
                }));


        label.setText(timeSeconds.toString());



        timeline.playFromStart();
    }


    public void start() {
        if (myThread == null) {
            myThread = new Thread(this);
            myThread.start();
        }
    }
}

