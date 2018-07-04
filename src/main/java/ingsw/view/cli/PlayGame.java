package ingsw.view.cli;

import ingsw.client.Client;
import ingsw.model.ViewData;
import ingsw.model.ViewWP;

import java.util.Scanner;

/**
 * Author: Elio Salvini
 *
 * Class that realizes the CLI of main game
 */
public class PlayGame {

    private Client client;                  //client used to communicate with server
    private Scanner in;                     //scanner to collect player's input
    private ViewData init;                  //view data used to print view the first time
    private ViewData update;                //view data used to print view during game
    private boolean timerAlreadyStarted;    //true if timer is already started, and isn't stopped yet
    private boolean newTurn;                //true the first time view is printed after the beginning o the new turn
    private boolean dieAlreadyTaken;        //true if player has already token a die during his turn
    private boolean toolCardAlreadyUsed;    //true if player has already used a tool card during his turn
    private boolean timerStopped;           //true if timer has to be stopped before timeout
    private boolean timeOut;                //true if timer ended with a timeout
    private boolean[] toolCardsUsedOnce;    //true if the related tool card has been used once

    /**
     * constructor
     * @param client    player's client
     */
    public PlayGame(Client client){
        this.client = client;
        in = new Scanner(System.in);
        newTurn = true;
        timerAlreadyStarted = false;
        dieAlreadyTaken = false;
        toolCardAlreadyUsed = false;
        timerStopped = false;
        timeOut = false;
        toolCardsUsedOnce = new boolean[3];
        toolCardsUsedOnce[0] = false;
        toolCardsUsedOnce[1] = false;
        toolCardsUsedOnce[2] = false;
    }

    /**
     * simple setter
     */
    public void setTimerAlreadyStarted(Boolean timerAlreadyStarted) {
        this.timerAlreadyStarted = timerAlreadyStarted;
    }

    /**
     * simple setter
     */
    public void setToolCardsUsedOnce(int toolCardIndex) {
        toolCardsUsedOnce[toolCardIndex] = true;
    }

    /**
     * simple setter
     */
    public void setToolCardAlreadyUsed(boolean toolCardAlreadyUsed) {
        this.toolCardAlreadyUsed = toolCardAlreadyUsed;
    }

    /**
     * simple setter
     */
    public void setTimeOut(boolean timeOut) {
        this.timeOut = timeOut;
    }

    /**
     * simple setter
     */
    public void setTimerStopped(boolean timerStopped) {
        this.timerStopped = timerStopped;
    }

    /**
     * simple setter
     */
    public void setNewTurn(Boolean newTurn) {
        this.newTurn = newTurn;
    }

    /**
     * simple setter
     */
    public void setDieAlreadyTaken(boolean dieAlreadyTaken) {
        this.dieAlreadyTaken = dieAlreadyTaken;
    }

    /**
     * simple getter
     */
    public boolean isTimerStopped() {
        return timerStopped;
    }

    /**
     * simple getter
     */
    public Client getClient() {
        return client;
    }

    /**
     *
     * @return  true if player has the right to play
     */
    public boolean myTurn(){
        return client.getPlayerState().equals("enabled");
    }

    /**
     * Method that prints the next available commands and initializes variable for a new turn
     */
    public void availableCommands() {

        //time's out, return to login
        if (timeOut) {
            timeOut = false;
            Main.accessGame(true);
        }

        //the match is finished
        boolean onlyOnePlayer = client.iAmAlone();
        if (client.isFinish() || onlyOnePlayer){
            endGame(onlyOnePlayer);
            return;
        }

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
            myTurnCommands();
        } else {
            waitingForMyTurnCommands();
        }
    }

    /**
     * It manages the end of the game
     * @param onlyOnePlayer     true if player won because all others players has retreated from game
     */
    private void endGame(boolean onlyOnePlayer) {

        //victory due to retreat of others players
        if(onlyOnePlayer){
            System.out.println("\nAll others players has retreated from match");
            System.out.println(ToString.printColored(ToString.ANSI_BLUE,"You are the winner"));
            Main.setEndGame(true);
            timerStopped = true;
            return;
        }

        //standard victory
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

    /**
     * This method prints commands available if the current turn isn't of the player (who's running this CLI)
     */
    private void waitingForMyTurnCommands() {
        int turnDuration = client.getTimeMove();

        //Connection error detected
        if (turnDuration == -1000) Main.accessGame(true);

        //Available commands
        System.out.println(ToString.printColored(ToString.ANSI_BLUE, "It's " + client.getCurrentPlayer() + "'s turn"));
        System.out.println("This turn will end in " + turnDuration + " seconds");
        System.out.println("Choose a command:");
        System.out.println("1 - Refresh view");
        System.out.println("2 - Exit");

        /*start timer
        if (!timerAlreadyStarted) {
            new Timer(turnDuration, client, this).start();
            timerAlreadyStarted = true;
        }*/

        //player's commands
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

    /**
     * This method prints available commands if the current turn is of the player (who's running this CLI)
     */
    private void myTurnCommands() {
        int turnDuration = client.getTimeMove();

        //Connection error detected
        if (turnDuration == -1000) Main.accessGame(true);

        //Available commands
        System.out.println(ToString.printColored(ToString.ANSI_BLUE, "It's your turn"));
        System.out.println("Your turn will end in " + turnDuration + " seconds");
        System.out.println("Choose a command:");
        System.out.println("1 - Take die");
        System.out.println("2 - Use tool card");
        System.out.println("3 - Refresh view");
        System.out.println("4 - End turn");
        System.out.println("5 - Exit");

        //start timer
        if (!timerAlreadyStarted) {
            new Timer(turnDuration, client, this).start();
            timerAlreadyStarted = true;
        }

        //player's commands
        int choice = Main.validateIntegerInput(1, 5);
        if(timeOut) return;
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

    /**
     * This method prints a new time the game's view (using updateView() of client)
     *
     */
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

    /**
     * This command ends player's turn
     */
    private void endTurn() {
        client.skip();
        dieAlreadyTaken = false;
        toolCardAlreadyUsed = false;
        timerStopped = true;
        newTurn = true;
        refreshView();
    }


    /**
     * Method that allows the use of a tool card
     */
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
        toolCardChosen -= 1;
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

    /**
     * Method that allows player to take and position a die from the draft pool to the window pattern
     */
    private void takeDie() {
        if (dieAlreadyTaken) {
            System.out.println("Command not available");
            return;
        }
        int selectedDie = selectDie() + 1;
        if(selectedDie == 0) return;
        System.out.println("You have selected die number " + selectedDie);
        positionDie();
    }

    /**
     *
     * @param selectedDie   number (positional index in draft pool) of the selected die
     * @return              selected die's value
     */
    public int dpDieValue(int selectedDie){
        return Integer.parseInt(client.updateView().getDraftPoolDice().get(selectedDie).substring(4,5));
    }

    /**
     *
     * @return selected die index, -1 if player inserted key word "back"
     */
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

    /**
     * Method that allows player to position a die on his window patterns
     */
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
        dieAlreadyTaken = true;
        refreshView();
    }

    /**
     * Method that verifies if player is considered inactive, and manages the situation if he is it
     */
    private void stillInGame(){
        if (!client.getActive()) {
            System.out.println(ToString.printColored(ToString.ANSI_RED,"You have lost your turn"));
            Main.accessGame(true);
        }

    }

    /**
     * Command used to quit game
     */
    private void exitGame(){
        System.out.println("Are you sure?\n1 - Yes\n2 - No");
        if (Main.validateIntegerInput(1,2) == 1) {
            Main.setEndGame(true);
            timerStopped = true;
        }
    }

    /**
     * Method used to print game's view for the first time
     */
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

    /**
     * Method that prints the current round
     */
    private void printCurrentRound() {
        System.out.println("Round: " + client.getRound());
    }

    /**
     * It prints a generic separator
     */
    public void printSeparator(){
        System.out.println("--------------------------------------------------------------------------------------");
    }

    /**
     * It prints a turn separator
     */
    private void printTurnSeparator(){
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("                                        NEW TURN                                      ");
        System.out.println("--------------------------------------------------------------------------------------");
    }

    /**
     * It prints a command separator
     */
    private void printCommandSeparator(){
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("//////////////////////////////////////////////////////////////////////////////////////");
        System.out.println("--------------------------------------------------------------------------------------");
    }

    /**
     * Method that collects data from a viewData object to print public objective cards
     */
    private void printPBCards(){
        System.out.println("Public objective cards:\n");
        int i =1;
        for(String card: init.getPbCardsCLI()){
            System.out.println(i + " - " + card);
            i++;
        }
    }

    /**
     * Method that prints tool cards
     * @param initialization    true if it is the first time that tool cards are printed
     */
    private void printToolCards(Boolean initialization){
        int i = 1;
        System.out.println("Tool cards:\n");
        for(String card: init.getToolCardsCLI()){
            System.out.print(i + " - " + card);
            if(initialization) System.out.println("Cost: 1 token\n");
            else {
                if(toolCardsUsedOnce[i-1]) System.out.println("Cost: 2 tokens\n");
                else System.out.println("Cost: 1 token\n");
            }
            i++;
        }
    }

    /**
     * Method that prints round track
     * @param viewData  object containing data used to print the round track
     */
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

    /**
     * Method that prints the draft pool
     * @param viewData  object containing data used to print the draft pool
     */
    public void printDraftPool(ViewData viewData){
        int i = 1;
        System.out.println("Draft Pool:");
        for (String die: viewData.getDraftPoolDice()){
            System.out.println( i + " - " + ToString.viewDieToString(die));
            i++;
        }
    }

    /**
     * Method that prints windows pattern
     *
     * @see ToString
     */
    private void printWPs(){
        System.out.println("Players windows pattern:\n");
        for (ViewWP wp: client.getPlayerWPs(client.getName())){
            System.out.println(ToString.viewWPToString(wp));
        }
    }

    /**
     * Method used to print player's window pattern
     * @see ToString
     */
    private void printPlayerWP(){
        System.out.println("My window pattern:\n\n" + ToString.viewWPToString(client.getWP(client.getName())));
    }

    /**
     * Method used to print PV objective cards
     *
     * @see ToString
     */
    private void printPVCard(){
        System.out.println("Your private objective card: " + ToString.viewPVCardToString(client.getPVCard(client.getName())));
    }

    /**
     * Method used to print player's remaining tokens
     */
    private void printTokens(){
        System.out.println("Tokens: " + client.getTokenRemaining(client.getName()));
    }

}
