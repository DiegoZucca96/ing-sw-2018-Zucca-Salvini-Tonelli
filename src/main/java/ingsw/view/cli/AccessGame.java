package ingsw.view.cli;

import ingsw.client.Client;
import ingsw.model.ViewWP;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Author: Elio Salvini
 *
 * Class that manages the player's access to game
 */
public class AccessGame {

    private Client client;  //player's client
    private Scanner in;     //scanner to get player's input

    /**
     * constructor
     * @param client    player's client
     */
    public AccessGame(Client client){
        this.client = client;
        in = new Scanner(System.in);
    }

    /**
     * Method that manages the player's login to game
     * @return true if player successfully logged in
     */
    public boolean login(){
        System.out.println("Login:\nEnter your username:");
        if (client.login(in.nextLine())){
            System.out.println("Logged in successfully");
            return true;
        }
        else {
            System.out.println(ToString.printColored(ToString.ANSI_RED, "Login failed"));
            return false;
        }
    }

    /**
     * Method that manages the player's registration to game
     * @return true if player successfully registered to game
     */
    public boolean register(){
        System.out.println("Registration:\nEnter your username:");
        if (client.register(in.nextLine())){
            System.out.println("Registration completed successfully");
            return true;
        }
        else {
            System.out.println(ToString.printColored(ToString.ANSI_RED, "Registration failed"));
            return false;
        }
    }

    /**
     * Method that puts player on hold for window pattern choice
     */
    public void waitForPlayers(){
        System.out.print("Waiting for players...");
        int loadingIndex = 0 ;
        while(!client.waitForPlayers()){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (loadingIndex == 6) loadingIndex = 0;
            if (loadingIndex >= 3) System.out.print(".");
            else System.out.print('\b');
            loadingIndex++;
        }
        System.out.print("\n");
    }

    /**
     * Method that allows player to choice his window pattern
     */
    public void chooseWPs(){
        System.out.println("Choose your window pattern:");
        ArrayList<ViewWP> wps = client.getRandomWps();
        int i = 1;
        for (ViewWP wp: wps){
            System.out.println(i + " - \n" + ToString.viewWPToString(wp));
            i++;
        }
        int choice = Main.validateIntegerInput(1,4) -1;
        client.createHash(wps.get(choice).getNumberWP(), client.getName());
        client.addWP(wps.get(choice));
    }

    /**
     * Method that puts player on hold for game's start
     */
    public void waitForPlay(){
        System.out.print("Waiting for play...");
        int loadingIndex = 0 ;
        while(!client.readyToPlay()){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (loadingIndex == 6) loadingIndex = 0;
            if (loadingIndex >= 3) System.out.print(".");
            else System.out.print('\b');
            loadingIndex++;
        }
        client.orderWPChoise();
        System.out.print("\n");
    }

}
