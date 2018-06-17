package ingsw.view.CLI;

import ingsw.Client;
import ingsw.model.ViewData;
import ingsw.model.ViewWP;

import java.util.Scanner;

public class PlayGame {

    private Client client;
    private String gameState;
    private Scanner in;
    private ViewData init;
    private ViewData update;
    private Boolean timerAlreadyStarted;
    private Boolean newTurn;

    public PlayGame(Client client){
        this.client = client;
        in = new Scanner(System.in);
        newTurn = true;
    }

    public void setTimerAlreadyStarted(Boolean timerAlreadyStarted) {
        this.timerAlreadyStarted = timerAlreadyStarted;
    }

    public void setNewTurn(Boolean newTurn) {
        this.newTurn = newTurn;
    }

    public boolean myTurn(){
        return client.getPlayerState().equals("enabled");
    }

    public void availableCommands() {
        if(newTurn) {
            printTurnSeparator();
            newTurn = false;
            timerAlreadyStarted = false;
        }
        if (myTurn()) {
            myTurnCommands();
        } else {
            waitingForMyTurnCommands();
        }
    }

    private void waitingForMyTurnCommands() {
        int turnDuration = client.getTimeMove();
        System.out.println(ToString.printColored(ToString.ANSI_BLUE, "It's " + client.getCurrentPlayer() + "'s turn"));
        System.out.println("This turn will end in " + turnDuration + " seconds");
        System.out.println("Choose a command:");
        System.out.println("1 - Refresh view");
        System.out.println("2 - Exit");
        if (!timerAlreadyStarted) {
            new Timer(turnDuration, client, this).start();
            timerAlreadyStarted = true;
        }
        int choice = Main.validateIntegerInput(1, 2, in.nextInt());
        switch (choice) {
            case 1:  refreshView(); break;
            case 2:  exitGame(); break;
        }
    }

    private void myTurnCommands() {
        int turnDuration = client.getTimeMove();
        System.out.println(ToString.printColored(ToString.ANSI_BLUE, "It's your turn"));
        System.out.println("Your turn will end in " + turnDuration + " seconds");
        System.out.println("Choose a command:");
        System.out.println("1 - Take die");
        System.out.println("2 - Use tool card");
        System.out.println("3 - Drop die");
        System.out.println("4 - Refresh view");
        System.out.println("5 - End turn");
        System.out.println("6 - Exit");
        if (!timerAlreadyStarted) {
            new Timer(turnDuration, client, this).start();
            timerAlreadyStarted = true;
        }
        int choice = Main.validateIntegerInput(1, 5, in.nextInt());
        switch (choice) {
            case 1: takeDie(); break;
            case 2: useToolCard(); break;
            case 3: dropDie(); break;
            case 4: refreshView(); break;
            case 5: endTurn(); break;
            case 6: exitGame(); break;
        }
    }

    private void refreshView() {
        update = client.updateView();
        printSeparator();
        printCurrentRound();
        printSeparator();
        printPBCards();
        printSeparator();
        printToolCards(false);
        printSeparator();
        printRoundTrack(update);
        printSeparator();
        printDraftPool(update);
        printSeparator();
        printWPs();
        printSeparator();
        printPlayerWP();
        printSeparator();
        printPVCard();
        printTokens();
        printSeparator();
    }

    private void endTurn() {

    }

    private void dropDie() {

    }

    private void useToolCard() {

    }

    private void takeDie() {

    }

    private void exitGame(){
        Main.setEndGame(true);
    }

    public void initializeView(){
        init = client.initializeViewCLI();
        printSeparator();
        printCurrentRound();
        printSeparator();
        printPBCards();
        printSeparator();
        printToolCards(true);
        printSeparator();
        printRoundTrack(init);
        printSeparator();
        printDraftPool(init);
        printSeparator();
        printWPs();
        printSeparator();
        printPlayerWP();
        printSeparator();
        printPVCard();
        printTokens();
        printSeparator();
    }

    private void printCurrentRound() {
        System.out.println("Round: " + client.getRound());
    }

    private void printSeparator(){
        System.out.println("--------------------------------------------------------------------------------------");
    }

    private void printTurnSeparator(){
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("                                        NEW TURN                                      ");
        System.out.println("--------------------------------------------------------------------------------------");
    }

    private void printCommandSeparator(){
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("//////////////////////////////////////////////////////////////////////////////////////");
        System.out.println("--------------------------------------------------------------------------------------");
    }

    private void printPBCards(){
        System.out.println("Public objective cards:\n");
        int i =1;
        for(String card: init.getPbCard()){
            System.out.println(i + " - " + card);
        }
    }

    private void printToolCards(Boolean initialization){
        int i = 1;
        System.out.println("Tool cards:\n");
        for(String card: init.getToolCard()){
            System.out.print(i + " - " + card);
            if(initialization) System.out.println("Cost: 1 token\n");
            else {
                String toolCard = update.getToolCard().get(i - 1);
                int stringIndex = toolCard.indexOf('+') + 1;
                if (stringIndex == 0) System.out.println("Cost: 1 token\n");
                else {
                    if (toolCard.substring(stringIndex, stringIndex + 1).equals("0")) {
                        System.out.println("Cost: 1 token\n");
                    } else System.out.println("Cost: 2 token\n");
                }
            }
        }
    }

    private void printRoundTrack(ViewData viewData){
        int i = 1;
        System.out.println("Round track:");
        for (String die: viewData.getRoundTrack()){
            System.out.println("Round " + i + " - " + ToString.viewDieToString(die));
            i++;
        }
    }

    private void printDraftPool(ViewData viewData){
        int i = 1;
        System.out.println("Draft Pool:");
        for (String die: viewData.getDraftPoolDice()){
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
        System.out.println("My window pattern:\n\n" + ToString.viewWPToString(client.getWP(client.getName())));
    }

    private void printPVCard(){
        System.out.println("Your private objective card: " + ToString.viewPVCardToString(client.getPVCard(client.getName())));
    }

    private void printTokens(){
        System.out.println("Tokens: " + client.getTokenRemaining(client.getName()));
    }
}
