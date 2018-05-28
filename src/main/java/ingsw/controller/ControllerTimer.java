package ingsw.controller;

import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;

public class ControllerTimer {

    private int timeRemaining;
    private int timeMoveRemaining;
    private final int timeSearch;
    private final int playerTimeMove;
    
    ControllerTimer(int timeSearch, int playerTimeMove){
        this.timeSearch = timeSearch;
        this.timeRemaining = timeSearch;
        this.playerTimeMove = playerTimeMove;
        this.timeMoveRemaining = playerTimeMove;
    }
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

    public void startPlayerTimer(RMIController controller, Timer timer){
        final int delay = 1000;
        final int period = 1000;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(timeMoveRemaining<0){
                    try {
                        controller.skip(controller.getCurrentPlayerName());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                if(timeMoveRemaining>=0)
                    timeMoveRemaining--;
            }
        }, delay, period);
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }

    public int getTimeMoveRemaining() {
        return timeMoveRemaining;
    }

    public void setTimeMoveRemaining(int timeMoveRemaining) {
        this.timeMoveRemaining = timeMoveRemaining;
    }
}
