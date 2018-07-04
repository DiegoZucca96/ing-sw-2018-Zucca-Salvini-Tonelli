package ingsw.view.cli;

import ingsw.client.Client;

/**
 * Author: Elio Salvini
 *
 * Timer used in PlayGame to verify that the player doesn't take too much time to make a move
 */
public class Timer extends Thread {

    private int remainingSeconds;   //remaining seconds before timeout
    private Client client;          //player's client
    private PlayGame playGame;      //reference to PlayGame

    /**
     * constructor
     * @param remainingSeconds  remaining seconds before timeout
     * @param client            player's client
     * @param playGame          PlayGame reference
     */
    public Timer(int remainingSeconds, Client client, PlayGame playGame){
        this.remainingSeconds = remainingSeconds;
        this.client = client;
        this.playGame = playGame;
    }

    /**
     * Execution of timer
     */
    public void run(){
        String playerState = client.getPlayerState();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        remainingSeconds -= 2;
        playGame.setTimerStopped(false);
        while(remainingSeconds > 0){
            try {
                Thread.sleep(1000);
                if (playGame.isTimerStopped()) return;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            remainingSeconds--;
        }

        //Time's out
        if (playerState.equals("enabled")){
            System.out.println(ToString.printColored(ToString.ANSI_RED,"Time's out!"));
            if (!client.getActive()) {
                playGame.setTimeOut(true);
            }
        }
        playGame.setNewTurn(true);
    }
}
