package ingsw.view.cli;

import ingsw.Client;

/**
 * Author: Elio Salvini
 */
public class Timer extends Thread {

    private int remainingSeconds;
    private Client client;
    private PlayGame playGame;

    public Timer(int remainingSeconds, Client client, PlayGame playGame){
        this.remainingSeconds = remainingSeconds;
        this.client = client;
        this.playGame = playGame;
    }

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
        if (playerState.equals("enabled")){
            if (!client.getActive()) {
                System.out.println(ToString.printColored(ToString.ANSI_RED,"Time's out!"));
                playGame.setTimeOut(true);
            }
        }
        playGame.setNewTurn(true);
    }
}
