package ingsw.view.CLI;

import ingsw.Client;
import ingsw.ClientRMI;
import ingsw.ClientSocket;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static Scanner in;
    private static AccessGame accessGame;
    private static Client client;
    private static boolean endGame = false;


    public static void main(String[] args) {
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("                                   WELCOME TO SAGRADA                                 ");
        System.out.println("--------------------------------------------------------------------------------------");
        in = new Scanner(System.in);
        chooseConnection();
    }

    //Play a match
    private static void playGame() {
        PlayGame playGame = new PlayGame(client);
        playGame.initializeView();
        while(!endGame){
            playGame.availableCommands();
        }
    }

    public static int validateIntegerInput(int from, int to){
        int input;
        try{
            input = Integer.parseInt(in.nextLine());
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

    private static void chooseConnection(){
        System.out.println("Choose type of connection:\n1 - RMI\n2 - Socket");
        int choice = validateIntegerInput(1,2);
        if (choice == 2) client = new ClientSocket("127.0.0.1", 1080);
        else {
            client = new ClientRMI();
            try {
                client.startClient();
            } catch (IOException e) {
                e.printStackTrace(); //TODO handle connection error
            }
        }
        accessGame(false);
    }

    public static void setEndGame(boolean endGame) {
        Main.endGame = endGame;
    }

    //Access game (registration, login, window pattern choice,...)
    public static void accessGame(Boolean onlyLogin){
        accessGame = new AccessGame(client);
        if (!onlyLogin) while (!accessGame.register());
        while (!accessGame.login());
        accessGame.waitForPlayers();
        accessGame.chooseWPs();
        accessGame.waitForPlay();
        playGame();
    }

    public static String validateCoordinates() {
        int row, column;
        String coordinates = in.nextLine();
        try{
            row = Integer.parseInt(coordinates.substring(0,1))-1;
            column = Integer.parseInt(String.valueOf(coordinates.charAt(2)))-1;
        } catch (Exception e) {
            System.out.println(ToString.printColored(ToString.ANSI_RED,"Invalid input"));
            System.out.println("Enter a valid input:");
            return validateCoordinates();
        }
        if(coordinates.length()!=3 || coordinates.charAt(1) != ',' || row > 4 || column > 5){
            System.out.println(ToString.printColored(ToString.ANSI_RED,"Invalid input"));
            System.out.println("Enter a valid input:");
            return validateCoordinates();
        }
        return coordinates;
    }
}
