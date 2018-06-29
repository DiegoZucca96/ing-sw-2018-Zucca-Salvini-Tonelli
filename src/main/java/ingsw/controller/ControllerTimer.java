package ingsw.controller;

import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class is dedicated to manage the research time and the action time of players
 */
public class ControllerTimer {

    private int timeRemaining;
    private int timeMoveRemaining;
    private final int timeSearch;
    private final int playerTimeMove;

    /**
     * Constructor of ControllerTimer
     * @param timeSearch it is the research time
     * @param playerTimeMove it is the action time of players
     */
    ControllerTimer(int timeSearch, int playerTimeMove){
        this.timeSearch = timeSearch;
        this.timeRemaining = timeSearch;
        this.playerTimeMove = playerTimeMove;
        this.timeMoveRemaining = playerTimeMove;
    }

    /**
     * This is a timer function that makes a countdown during the search for a match.
     * It starts with a delay of one second and decreases the time every second when the search conditions are respected
     * @param controller it is the reference to Controller
     * @param timer it is the timer passed by Controller
     */
    public void search(RMIController controller, Timer timer){
        final int delay = 1000;
        final int period = 1000;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(timeRemaining>0) try {
                    if(controller.getSizeOfPlayers()>=2 && controller.getSizeOfPlayers()<4)
                        timeRemaining--;
                    else{
                        if(controller.getSizeOfPlayers()==4)
                            timeRemaining=0;
                        else
                            timeRemaining = timeSearch;
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }, delay, period);
    }

    /**
     * This is a timer function that makes a countdown during a player's turn in the game.
     * It starts with a delay of one second and decreases the time every second.
     * If the time is over, it calls skip method to pass the turn to the next player.
     * @param controller it is the reference to Controller
     * @param timer it is the timer passed by Controller
     */
    public void startPlayerTimer(RMIController controller, Timer timer){
        final int delay = 1000;
        final int period = 1000;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(timeMoveRemaining==0){
                    try {
                        controller.skip(controller.getCurrentPlayerName());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                if(timeMoveRemaining>0){
                    timeMoveRemaining--;
                }
            }
        }, delay, period);
    }

    /**
     * Simple getter method
     * @return the research time remaining
     */
    public int getTimeRemaining() {
        return timeRemaining;
    }

    /**
     * Simple getter method
     * @return the action time remaining to a player to make his move
     */
    public int getTimeMoveRemaining() {
        return timeMoveRemaining;
    }

    /**
     * Simply setter method
     * @param timeMoveRemaining it is a time to set
     */
    public void setTimeMoveRemaining(int timeMoveRemaining) {
        this.timeMoveRemaining = timeMoveRemaining;
    }

    /**
     * Simply setter method
     * @param timeSearch it is a time to set
     */
    public void setTimeSearch(int timeSearch) {
        this.timeRemaining=timeSearch;
    }
}
