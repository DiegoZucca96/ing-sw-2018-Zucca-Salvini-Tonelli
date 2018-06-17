package ingsw.view.CLI;

import ingsw.Client;
import ingsw.model.ViewWP;

import java.util.ArrayList;
import java.util.Scanner;

public class AccessGame {

    private Client client;
    private Scanner in;

    public AccessGame(Client client){
        this.client = client;
        in = new Scanner(System.in);
    }

    public boolean login(){
        System.out.println("Login:\nEnter your username:");
        if (client.login(in.nextLine())){
            System.out.println("Login successful");
            return true;
        }
        else {
            System.out.println("Login failed");
            return false;
        }
    }

    public boolean register(){
        System.out.println("Register:\nEnter your username:");
        if (client.register(in.nextLine())){
            System.out.println("Register successful");
            return true;
        }
        else {
            System.out.println("Registration failed");
            return false;
        }
    }

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

    public void chooseWPs(){
        System.out.println("Choose your window pattern:");
        ArrayList<ViewWP> wps = client.getRandomWps();
        int i = 1;
        for (ViewWP wp: wps){
            System.out.println(i + " - \n" + ToString.viewWPToString(wp));
            i++;
        }
        int choice = Main.validateIntegerInput(1,4,in.nextInt()) -1;
        client.createHash(wps.get(choice).getNumberWP(), client.getName());
        client.addWP(wps.get(choice));
    }

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
