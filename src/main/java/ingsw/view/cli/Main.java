package ingsw.view.cli;

import ingsw.client.Client;
import ingsw.client.ClientRMI;
import ingsw.client.ClientSocket;

import java.io.IOException;
import java.util.Scanner;

/**
 * Author: Elio Salvini
 *
 * Main class that runs the CLI
 *
 * @see AccessGame
 * @see PlayGame
 */
public class Main {

    private static Scanner in;              //scanner to get player's input
    private static AccessGame accessGame;   //reference to class used to introduce player to game
    private static Client client;           //reference to client used to connect with server's game
    private static boolean endGame = false; //true if the game is finished


    public static void main(String[] args) {
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("                                   WELCOME TO SAGRADA                                 ");
        System.out.println("--------------------------------------------------------------------------------------");
        in = new Scanner(System.in);
        chooseConnection();
    }

    /**
     * Play a match
     */
    private static void playGame() {
        PlayGame playGame = new PlayGame(client);
        playGame.initializeView();
        while(!endGame){
            playGame.availableCommands();
        }
    }

    /**
     * This method gets an int input and validates it
     * @param from minimum value allowed for the input
     * @param to maximum value allowed for the input
     * @return the validated input, or 0 if player inserted the key word "back"
     */
    public static int validateIntegerInput(int from, int to){
        int input;
        try{
            String inputString = in.nextLine();
            if (inputString.equals("back")) return 0;
            input = Integer.parseInt(inputString);
        } catch(Exception e){
            System.out.println(ToString.printColored(ToString.ANSI_RED,"Invalid input"));
            System.out.println("Enter a valid input:");
            return validateIntegerInput(from,to);
        }
        if (input > to || input < from) {
            System.out.println(ToString.printColored(ToString.ANSI_RED,"Invalid input"));
            System.out.println("Enter a valid input:");
            return validateIntegerInput(from,to);
        }
        return input;
    }

    /**
     * This method allows player to insert server IP address an to choose the type of connection (between RMI or socket)
     */
    private static void chooseConnection(){
        System.out.println("Insert server IP address:");
        String hostAddress = in.nextLine();

        //validating ip address
        while (!validateIPAddress(hostAddress)){
            System.out.println(ToString.printColored(ToString.ANSI_RED,"Invalid input"));
            System.out.println("Enter a valid input:");
            hostAddress = in.nextLine();
        }

        System.out.println("Choose type of connection:\n1 - RMI\n2 - Socket");
        int choice = 0;
        while(choice == 0){
            choice = validateIntegerInput(1,2);
        }
        if (choice == 2) client = new ClientSocket(hostAddress, 1080);
        else {
            client = new ClientRMI();
            try {
                client.startClient(hostAddress);
            } catch (Exception e) {
                System.out.println(ToString.printColored(ToString.ANSI_RED, "Connection error detected"));
            }
        }
        accessGame(false);
    }

    /**
     * simple setter
     */
    public static void setEndGame(boolean endGame) {
        Main.endGame = endGame;
    }

    /**
     * Access game (registration, login, window pattern choice,...)
     * @param reconnection  if true it notifies player to reconnect because he's recognized as inactive
     */
    public static void accessGame(Boolean reconnection){
        boolean successfullyLoggedIn = false;
        if (reconnection) System.out.println("\nReconnect to server");
        accessGame = new AccessGame(client);
        while (!successfullyLoggedIn) {
            System.out.println("Select operation:\n1 - Login\n2 - Registration");
            int operation = validateIntegerInput(1, 2);
            if (operation == 0) System.out.println(ToString.printColored(ToString.ANSI_RED, "Forbidden operation"));
            if (operation == 1) successfullyLoggedIn = accessGame.login();
            if (operation == 2) accessGame.register();
        }
        accessGame.waitForPlayers();
        accessGame.chooseWPs();
        accessGame.waitForPlay();
        playGame();
    }

    /**
     * Method that gets an input representing coordinates, and validates it
     * @param placeDie  true if the coordinates requested are used to place a die in a window pattern
     * @return  the validated coordinates, or 0 if player inserted the key word "back"
     */
    public static String validateCoordinates(boolean placeDie) {
        int row, column;
        if(placeDie) System.out.println("Insert coordinates where you wish to place the die:\n(Coordinates format = \"row,column\")");
        else System.out.println("Insert coordinates of the die you want to take:\n(Coordinates format = \"row,column\")");
        String coordinates = in.nextLine();
        if (coordinates.equals("back")) return "0";
        try{
            row = Integer.parseInt(coordinates.substring(0,1))-1;
            column = Integer.parseInt(String.valueOf(coordinates.charAt(2)))-1;
        } catch (Exception e) {
            System.out.println(ToString.printColored(ToString.ANSI_RED,"Invalid input"));
            System.out.println("Enter a valid input:");
            return validateCoordinates(placeDie);
        }
        if(!placeDie) {
            String takenDie = client.getWP(client.getName()).getWp()[row][column].getDie();
            if( takenDie == null || takenDie.equals("die(0,WHITE)")) {
                System.out.println(ToString.printColored(ToString.ANSI_RED,"Invalid input"));
                System.out.println("Enter a valid input:");
                return validateCoordinates(placeDie);
            }
        }
        if(coordinates.length()!=3 || coordinates.charAt(1) != ',' || row > 4 || column > 5){
            System.out.println(ToString.printColored(ToString.ANSI_RED,"Invalid input"));
            System.out.println("Enter a valid input:");
            return validateCoordinates(placeDie);
        }
        return coordinates;
    }

    /**
     * This method validates ip address format
     * @param ipAddress ip address you want to validate
     * @return  true if the ip address format is correct
     */
    public static boolean validateIPAddress(String ipAddress){
        String tmp;
        try {
            for (int i=0; i<4; i++){
                if(i==3){
                    if (Integer.parseInt(ipAddress) < 0 || Integer.parseInt(ipAddress) > 255) return false;
                    break;
                }
                tmp = ipAddress.substring(0,ipAddress.indexOf('.'));
                ipAddress = ipAddress.substring(ipAddress.indexOf('.')+1);
                if (Integer.parseInt(tmp) < 0 || Integer.parseInt(tmp) > 255) return false;
            }
        }catch (Exception e){
            return false;
        }

        return true;
    }
}
