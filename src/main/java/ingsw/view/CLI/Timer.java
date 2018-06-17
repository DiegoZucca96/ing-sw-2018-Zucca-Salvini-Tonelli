package ingsw.view.CLI;

import ingsw.Client;

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
        while(remainingSeconds > 0){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            remainingSeconds--;
        }
        if (client.getPlayerState().equals("enabled")){
            if (!client.getActive()) {
                System.out.println(ToString.printColored(ToString.ANSI_RED,"Time's out!"));
                Main.accessGame(true);
            }
        }
        playGame.setNewTurn(true);
    }
}
