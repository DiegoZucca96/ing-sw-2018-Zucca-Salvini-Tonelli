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
    private boolean timerAlreadyStarted;
    private boolean newTurn;
    private boolean dieAlreadyTaken;
    private boolean timerStopped;

    public PlayGame(Client client){
        this.client = client;
        in = new Scanner(System.in);
        newTurn = true;
        dieAlreadyTaken = false;
    }

    public void setTimerAlreadyStarted(Boolean timerAlreadyStarted) {
        this.timerAlreadyStarted = timerAlreadyStarted;
    }

    public void setNewTurn(Boolean newTurn) {
        this.newTurn = newTurn;
    }

    public boolean isTimerStopped() {
        return timerStopped;
    }

    public boolean myTurn(){
        return client.getPlayerState().equals("enabled");
    }

    public void availableCommands() {
        if(newTurn) {
            printTurnSeparator();
            newTurn = false;
            timerAlreadyStarted = false;
            timerStopped = true;
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
        int choice = Main.validateIntegerInput(1, 2);
        switch (choice) {
            case 1:
                printCommandSeparator();
                refreshView();
                break;
            case 2:
                printCommandSeparator();
                exitGame();
                break;
        }
    }

    private void myTurnCommands() {
        int turnDuration = client.getTimeMove();
        System.out.println(ToString.printColored(ToString.ANSI_BLUE, "It's your turn"));
        System.out.println("Your turn will end in " + turnDuration + " seconds");
        System.out.println("Choose a command:");
        System.out.println("1 - Take die");
        System.out.println("2 - Use tool card");
        System.out.println("3 - Refresh view");
        System.out.println("4 - End turn");
        System.out.println("5 - Exit");
        if (!timerAlreadyStarted) {
            new Timer(turnDuration, client, this).start();
            timerAlreadyStarted = true;
        }
        int choice = Main.validateIntegerInput(1, 5);
        switch (choice) {
            case 1:
                printCommandSeparator();
                client.setActive(true);
                takeDie();
                break;
            case 2:
                printCommandSeparator();
                client.setActive(true);
                useToolCard();
                break;
            case 3:
                printCommandSeparator();
                refreshView();
                break;
            case 4:
                printCommandSeparator();
                client.setActive(true);
                endTurn();
                break;
            case 5:
                printCommandSeparator();
                exitGame();
                break;
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
        printWPs();
        printSeparator();
        printPlayerWP();
        printSeparator();
        printDraftPool(update);
        printSeparator();
        printPVCard();
        printTokens();
        printSeparator();
    }

    private void endTurn() {
        client.skip();
        dieAlreadyTaken = false;
        newTurn = true;
        refreshView();
    }


    private void useToolCard() {


    }

    private void takeDie() {
        if (dieAlreadyTaken) {
            printCommandSeparator();
            System.out.println("Command not available");
            return;
        }
        System.out.println("Select a die:");
        //cleanScanner();
        int selectedDie = Main.validateIntegerInput(1,9)-1;
        while (client.updateView().getDraftPoolDice().get(selectedDie).equals("die(0,WHITE)") || !client.takeDie(0,selectedDie)){
            stillInGame();
            System.out.println(ToString.printColored(ToString.ANSI_RED,"Invalid input"));
            System.out.println("Select a die:");
            selectedDie = in.nextInt()-1;
        }
        System.out.println("You have selected die number" + selectedDie+1);
        dieAlreadyTaken = true;
        positionDie();
    }

    private void cleanScanner() {
        in.nextLine();

    }

    private void positionDie() {
        System.out.println("Insert coordinates where you wish to place the die:\n(Coordinates format \"row,column\")");
        String coordinates = Main.validateCoordinates();
        int row = Integer.parseInt(coordinates.substring(0,1))-1;
        int column = Integer.parseInt(String.valueOf(coordinates.charAt(2)))-1;
        while (!client.positionDie(row,column)){
            stillInGame();
            System.out.println(ToString.printColored(ToString.ANSI_RED,"Invalid input"));
            System.out.println("Insert coordinates where you wish to place the die:");
            coordinates = Main.validateCoordinates();
            row = Integer.parseInt(coordinates.substring(0,1))-1;
            column = Integer.parseInt(String.valueOf(coordinates.charAt(2)))-1;
        }
        System.out.println("Die positioned successfully");
        refreshView();
    }

    private void stillInGame(){
        if (!client.getActive()) {
            System.out.println(ToString.printColored(ToString.ANSI_RED,"You have lost your turn"));
            Main.accessGame(true);
        }

    }

    private void exitGame(){
        System.out.println("Are you sure?\n1 - Yes\n2 - No");
        if (Main.validateIntegerInput(1,2) == 1) Main.setEndGame(true);
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
        printWPs();
        printSeparator();
        printPlayerWP();
        printSeparator();
        printDraftPool(init);
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
            i++;
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
            i++;
        }
    }

    private void printRoundTrack(ViewData viewData){
        int i = 0;
        System.out.print("Round track:");
        for (String die: viewData.getRoundTrack()){
            if(Integer.parseInt(die.substring(0,1)) != i) {
                i++;
                System.out.print("\nRound " + i + " - ");
            }
            System.out.print(ToString.viewDieToString(die) + " ");
        }
        System.out.print("\n");
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
