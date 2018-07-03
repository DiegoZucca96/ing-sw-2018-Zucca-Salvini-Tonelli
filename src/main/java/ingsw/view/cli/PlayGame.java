package ingsw.view.cli;

import ingsw.Client;
import ingsw.model.ViewData;
import ingsw.model.ViewWP;

import java.util.Scanner;

/**
 * Author: Elio Salvini
 */
public class PlayGame {

    private Client client;
    private Scanner in;
    private ViewData init;
    private ViewData update;
    private boolean timerAlreadyStarted;
    private boolean newTurn;
    private boolean dieAlreadyTaken;
    private boolean toolCardAlreadyUsed;
    private boolean timerStopped;
    private boolean timeOut;
    private boolean[] toolCardsUsedOnce;

    public PlayGame(Client client){
        this.client = client;
        in = new Scanner(System.in);
        newTurn = true;
        dieAlreadyTaken = false;
        toolCardAlreadyUsed = false;
        timerStopped = false;
        timeOut = false;
        toolCardsUsedOnce = new boolean[3];
        toolCardsUsedOnce[0] = false;
        toolCardsUsedOnce[1] = false;
        toolCardsUsedOnce[2] = false;
    }

    public void setTimerAlreadyStarted(Boolean timerAlreadyStarted) {
        this.timerAlreadyStarted = timerAlreadyStarted;
    }

    public void setToolCardsUsedOnce(int toolCardIndex) {
        toolCardsUsedOnce[toolCardIndex] = true;
    }

    public void setTimeOut(boolean timeOut) {
        this.timeOut = timeOut;
    }

    public void setTimerStopped(boolean timerStopped) {
        this.timerStopped = timerStopped;
    }

    public void setNewTurn(Boolean newTurn) {
        this.newTurn = newTurn;
    }

    public void setDieAlreadyTaken(boolean dieAlreadyTaken) {
        this.dieAlreadyTaken = dieAlreadyTaken;
    }

    public boolean isTimerStopped() {
        return timerStopped;
    }

    public Client getClient() {
        return client;
    }

    public boolean myTurn(){
        return client.getPlayerState().equals("enabled");
    }

    public void availableCommands() {
        //the match is finished
        if (client.isFinish()){
            endGame();
            return;
        }

        //time's out, return to login
        if (timeOut) Main.accessGame(true);

        //initializing new turn
        if(newTurn) {
            printTurnSeparator();
            newTurn = false;
            timerAlreadyStarted = false;
        }

        //prints available commands
        if (myTurn()) {
            if(client.getRound() == 10 && !client.getClockwiseRound()){
                client.calculateScore();
            }
            timerStopped = true;
            myTurnCommands();
        } else {
            waitingForMyTurnCommands();
        }
    }

    private void endGame() {
        System.out.println("");
        for (String player: client.getListOfPlayers()){
            System.out.println(player + "'s score is: " + ToString.printColored(ToString.ANSI_BLUE, Integer.toString(client.getScore(player))));
        }
        String winner = client.findWinner();
        if(winner.equals(client.getName())) System.out.println("\nYou are the winner");
        else System.out.println("\nThe winner is " + winner);
        Main.setEndGame(true);
        timerStopped = true;
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
            default: System.out.println("Forbidden operation");
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
            default: System.out.println("Forbidden operation");
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
        toolCardAlreadyUsed = false;
        timerStopped = true;
        newTurn = true;
        refreshView();
    }


    private void useToolCard() {
        if(toolCardAlreadyUsed) {
            System.out.println("Command not available");
            return;
        }
        System.out.println("Choose tool card:");
        int toolCardChosen = Main.validateIntegerInput(1,3);
        if (toolCardChosen == 0) return;
        String toolCard = init.getToolCardsCLI().get(toolCardChosen-1);
        String toolCardName = toolCard.substring(0,toolCard.indexOf('\n'));
        switch (toolCardName){
            case "Pinza Sgrossatrice": ToolCard.toolCard1(this, toolCardChosen); break;
            case "Pennello per Eglomise": ToolCard.toolCard2(this, toolCardChosen); break;
            case "Alesatore per lamina di rame": ToolCard.toolCard3(this, toolCardChosen); break;
            case "Lathekin": ToolCard.toolCard4(this, toolCardChosen); break;
            case "Taglierina circolare": ToolCard.toolCard5(this, toolCardChosen); break;
            case "Pennello per Pasta Salda": ToolCard.toolCard6(this, toolCardChosen); break;
            case "Martelletto": ToolCard.toolCard7(this, toolCardChosen); break;
            case "Tenaglia a Rotelle": ToolCard.toolCard8(this, toolCardChosen); break;
            case "Riga in Sughero": ToolCard.toolCard9(this, toolCardChosen); break;
            case "Tampone Diamantato": ToolCard.toolCard10(this, toolCardChosen); break;
            case "Diluente per Pasta Salda": ToolCard.toolCard11(this, toolCardChosen); break;
            case "Taglierina Manuale": ToolCard.toolCard12(this, toolCardChosen); break;
            default: break;
        }
        refreshView();
    }

    private void takeDie() {
        if (dieAlreadyTaken) {
            System.out.println("Command not available");
            return;
        }
        int selectedDie = selectDie();
        if(selectedDie == -1) return;
        System.out.println("You have selected die number " + selectedDie+1);
        positionDie();
        dieAlreadyTaken = true;
    }

    public int dpDieValue(int selectedDie){
        return Integer.parseInt(client.updateView().getDraftPoolDice().get(selectedDie).substring(4,5));
    }
    public int selectDie() {
        System.out.println("Select a die:");
        int selectedDie = Main.validateIntegerInput(1,9)-1;
        if(selectedDie == -1) return -1;
        while (client.updateView().getDraftPoolDice().get(selectedDie).equals("die(0,WHITE)") || !client.takeDie(0,selectedDie)){
            stillInGame();
            System.out.println(ToString.printColored(ToString.ANSI_RED,"Invalid input"));
            System.out.println("Select a die:");
            selectedDie = Main.validateIntegerInput(1,9);
        }
        return selectedDie;
    }

    private void cleanScanner() {
        in.nextLine();

    }

    private void positionDie() {
        String coordinates = Main.validateCoordinates(true);
        if (coordinates.equals("0")) {
            client.nullSelection();
            return;
        }
        int row = Integer.parseInt(coordinates.substring(0,1))-1;
        int column = Integer.parseInt(String.valueOf(coordinates.charAt(2)))-1;
        while (!client.positionDie(row,column)){
            stillInGame();
            System.out.println(ToString.printColored(ToString.ANSI_RED,"Invalid input"));
            coordinates = Main.validateCoordinates(true);
            if(coordinates.equals("0")) {
                client.nullSelection();
                return;
            }
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
        if (Main.validateIntegerInput(1,2) == 1) {
            Main.setEndGame(true);
            timerStopped = true;
        }
    }

    public void initializeView(){
        init = client.initializeViewCLI();
        client.initializeView();
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

    public void printSeparator(){
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
        for(String card: init.getToolCardsCLI()){
            System.out.print(i + " - " + card);
            if(initialization) System.out.println("Cost: 1 token\n");
            else {
                if(toolCardsUsedOnce[i-1]) System.out.println("Cost: 2 token\n");
                else System.out.println("Cost: 1 token\n");
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

    public void printDraftPool(ViewData viewData){
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

    public void setToolCardAlreadyUsed(boolean toolCardAlreadyUsed) {
        this.toolCardAlreadyUsed = toolCardAlreadyUsed;
    }
}
