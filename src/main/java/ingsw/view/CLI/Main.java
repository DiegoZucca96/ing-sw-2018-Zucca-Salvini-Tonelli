package ingsw.view.CLI;

import ingsw.Client;
import ingsw.ClientRMI;
import ingsw.ClientSocket;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static Scanner in;
    private static AccesGame accesGame;
    private static Client client;

    public static void main(String[] args) {
        in = new Scanner(System.in);
        chooseConnection();

        //Access game (registration, login, window pattern choice,...)
        accesGame = new AccesGame(client);
        while (!accesGame.register());
        while (!accesGame.login());
        accesGame.waitForPlayers();
        accesGame.chooseWPs();
        accesGame.waitForPlay();

        //Play a match
        boolean endGame = false;
        PlayGame playGame = new PlayGame(client);
        while(!endGame){
            playGame.initializeView();
            playGame.availableCommands();
        }
    }

    public static int validateIntegerInput(int from, int to, int input){
        if (input > to || input < from) {
            System.out.println(ToString.printColored(ToString.ANSI_RED,"Invalid input"));
            System.out.println("Enter a valid input:");
            return validateIntegerInput(from,to, in.nextInt());
        }
        return input;
    }

    private static void chooseConnection(){
        System.out.println("Choose type of connection:\n1 - RMI\n2 - Socket");
        int choice = validateIntegerInput(1,2,in.nextInt());
        if (choice == 2) client = new ClientSocket("127.0.0.1", 1080);
        else {
            client = new ClientRMI();
            try {
                client.startClient();
            } catch (IOException e) {
                e.printStackTrace(); //TODO handle connection error
            }
        }
    }
}
