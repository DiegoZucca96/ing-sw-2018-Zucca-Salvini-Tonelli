package ingsw.view.CLI;

import ingsw.Client;
import ingsw.model.ViewData;
import ingsw.model.ViewWP;

import java.util.ArrayList;
import java.util.Scanner;

public class PlayGame {

    private Client client;
    private String gameState;
    private Scanner in;
    private ViewData init;

    public PlayGame(Client client){
        this.client = client;
        in = new Scanner(System.in);
    }

    public void availableCommands(){
        System.out.println("1 - Take die");
        System.out.println("2 - Use tool card");
        System.out.println("3 - Drop die");
        System.out.println("4 - End turn");
        int choice = Main.validateIntegerInput(1,4, in.nextInt());
        switch (choice){
            case 1: takeDie(); break;
            case 2: useToolCard(); break;
            case 3: dropDie(); break;
            case 4: endTurn(); break;
        }
    }

    private void endTurn() {

    }

    private void dropDie() {

    }

    private void useToolCard() {

    }

    private void takeDie() {

    }

    public void initializeView(){
        init = client.initializeViewCLI();
        printSeparator();
        printPBCards();
        printSeparator();
        printToolCards();
        printSeparator();
        printRoundTrack();
        printSeparator();
        printDraftPool();
        printSeparator();
        printWPs();
        printSeparator();
        printPlayerWP();
        printSeparator();
        printPVCard();
        printTokens();
        printSeparator();
    }

    private void printSeparator(){
        System.out.println("--------------------------------------------------------------------------------------");
    }

    private void printTurnSeparator(){
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("                                        NEW TURN                                      ");
        System.out.println("--------------------------------------------------------------------------------------");
    }

    private void printPBCards(){
        System.out.println("Public objective cards:\n");
        int i =1;
        for(String card: init.getPbCard()){
            System.out.println(i + " - " + card);
        }
    }

    private void printToolCards(){
        int i = 1;
        System.out.println("Tool cards:\n");
        for(String card: init.getToolCard()){
            System.out.println(i + " - " + card);
        }
    }

    private void printRoundTrack(){
        int i = 1;
        System.out.println("Round track:");
        for (String die: init.getRoundTrack()){
            System.out.println("Round " + i + " - " + ToString.viewDieToString(die));
            i++;
        }
    }

    private void printDraftPool(){
        int i = 1;
        System.out.println("Draft Pool:");
        for (String die: init.getDraftPoolDice()){
            System.out.println( i + " - " + ToString.viewDieToString(die));
            i++;
        }
    }

    private void printWPs(){
        System.out.println("Players window patterns:\n");
        for (ViewWP wp: client.getPlayerWPs(client.getName())){
            System.out.println(ToString.viewWPToString(wp));
        }
    }

    private void printPlayerWP(){
        System.out.println("My window pattern:\n" + ToString.viewWPToString(client.getWP(client.getName())));
    }

    private void printPVCard(){
        System.out.println("Your private objective card: " + ToString.viewPVCardToString(client.getPVCard(client.getName())));
    }

    private void printTokens(){
        System.out.println("Tokens: " + client.getTokenRemaining(client.getName()));
    }
}
