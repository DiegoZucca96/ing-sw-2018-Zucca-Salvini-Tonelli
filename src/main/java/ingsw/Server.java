package ingsw;

import ingsw.controller.Controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private ArrayList<String> listOfClient;
    private ArrayList<String> listOfPlayers;
    private static Server instance;
    private int port;
    private final int timeSearch;
    private final int playerTimeMove;

    public static void main(String[] args) throws RemoteException {
        Server server = Server.instance(1080);
        Controller controller = new Controller(server);
        Registry registry = LocateRegistry.createRegistry(1080);
        registry.rebind("controller", controller);
        System.out.println("Server ready");
    }


    private Server(int port) {
        listOfClient = new ArrayList<>();
        listOfPlayers = new ArrayList<>();
        this.port = port;
        Scanner in = new Scanner(System.in);
        System.out.print("Inserisci tempo di ricerca massimo: ");
        timeSearch = in.nextInt();
        System.out.print("Inserisci tempo massimo per fare una mossa: ");
        playerTimeMove = in.nextInt();
    }

    public static Server instance(int port) {
        if (instance == null) instance = new Server(port);
        return instance;
    }

    public ArrayList<String> getListOfClient() {
        return listOfClient;
    }

    public ArrayList<String> getListOfPlayers() {
        return listOfPlayers;
    }

    public int getTimeSearch() {
        return timeSearch;
    }

    public int getPlayerTimeMove() {
        return playerTimeMove;
    }

    public void addPlayers(String account) {
        listOfPlayers.add(account);
    }

    public void addAccount(String account) {
        listOfClient.add(account);
    }

    public void startServerSocket() {
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println(e.getMessage()); // porta non disponibile
            return;
        }
        try {
            System.out.println("Server ready");
            while (true) {
                Boolean endLoop = false;
                try {
                    Socket socket = serverSocket.accept();
                    executor.submit(new ServerHandler(socket));
                } catch (IOException e) {
                    endLoop = true;                  //connessione chiusa
                }
                if (endLoop) break;
            }
            executor.shutdown();
        } catch(Exception e){}
        finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

}
