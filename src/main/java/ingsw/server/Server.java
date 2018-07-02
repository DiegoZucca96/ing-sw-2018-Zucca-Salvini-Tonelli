package ingsw.server;

import ingsw.ClientData;
import ingsw.ClientState;
import ingsw.ServerHandler;
import ingsw.controller.Controller;
import ingsw.controller.RMIController;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author: Elio Salvini - Diego Zucca
 */
public class Server {

    private ArrayList<ClientData> listOfClients; //associa ad ogni nome di un client il relativo stato
    private ArrayList<String> listOfPlayers; //Nomi dei giocatori in partita
    private static Server instance = null;
    private RMIController controller;
    private int port;
    private final int timeSearch;
    private final int playerTimeMove;
    private ArrayList<String> inactivePlayers;


    public static void main(String[] args) throws RemoteException {

        Server server = Server.instance(1080);

        try {
            System.out.println("Server name: " + InetAddress.getLocalHost().getHostName());
            System.out.println("Server address: " + ipToString(InetAddress.getLocalHost().getAddress()));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        //cambio la policy per grantire l'accesso ad RMI
        System.setProperty("java.security.policy", "src/policy/sagrada.policy");
        System.setSecurityManager(new SecurityManager());

        //lego il controller al registry
        Registry registry = LocateRegistry.createRegistry(1081);
        registry.rebind("controller", server.getController());

        //lancio il server socket
        server.startServerSocket();
    }

    private static String ipToString(byte arrayAddress[]){
        String address = "";
        for ( byte i: arrayAddress){
            address = address + unsignedToBytes(i) + ".";
        }
        address = address.substring(0,address.length()-1);
        return address;
    }

    public static int unsignedToBytes(byte b) {
        return b & 0xFF;
    }

    private Server(int port) {
        listOfClients = new ArrayList<>();
        listOfPlayers = new ArrayList<>();
        this.port = port;
        Scanner in = new Scanner(System.in);
        System.out.println("Inserisci tempo di ricerca massimo: ");
        timeSearch = in.nextInt();
        System.out.println("Inserisci tempo massimo per fare una mossa: ");
        playerTimeMove = in.nextInt();
        inactivePlayers = new ArrayList<>();
        try {
            controller = new Controller(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static Server instance(int port) {
        if (instance == null) instance = new Server(port);
        return instance;
    }

    public RMIController getController() {
        return controller;
    }

    public void setController(RMIController controller) {
        this.controller = controller;
    }

    public ArrayList<String> getListOfClient() {
        ArrayList<String> nameClient = new ArrayList<>();
        for(ClientData client : listOfClients)
            nameClient.add(client.getName());
        return nameClient;
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
        listOfClients.add(new ClientData(account));
    }

    public void addAccount(String account, ServerHandler handler) throws RemoteException {
        listOfClients.add(new ClientData(account, handler));
        controller.disableClient(account);
    }

    public void setClientState(String clientName, ClientState state) {
        for (ClientData client : listOfClients) {
            if (client.getName().equals(clientName)) client.setState(state);
        }
    }

    public ClientState getClientState(String clientName){
        for (ClientData client : listOfClients) {
            if (client.getName().equals(clientName)) return  client.getState();
        }
        return null;
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

    public void addInactivePlayers(String clientName) {
        inactivePlayers.add(clientName);
    }

    public ArrayList<String> getInactivePlayer() {
        return inactivePlayers;
    }

    public boolean removePlayer(String name) {
        listOfPlayers.remove(listOfPlayers.indexOf(name));
        return true;
    }
}
