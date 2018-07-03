package ingsw.server;

import ingsw.client.ClientData;
import ingsw.client.ClientSocket;
import ingsw.controller.ClientState;
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
 *
 * This class represents game's server, and it manages the start of socket connection,
 * and others matters related to connectivity
 *
 * @see ClientSocket
 * @see Controller
 */
public class Server {

    private ArrayList<ClientData> listOfClients;    //list that contains data related to the connected clients
    private ArrayList<String> listOfPlayers;        //players names
    private static Server instance = null;          //server instance
    private RMIController controller;               //game's controller
    private int port;                               //server port
    private final int timeSearch;                   //maximum time to search player for the beginning of  match
    private final int playerTimeMove;               //maximum time for a player to make a move
    private ArrayList<String> inactivePlayers;      //list of inactive players

    //inactive player = player who hasn't made any move during his last turn

    public static void main(String[] args) throws RemoteException {

        Server server = Server.instance(1080);

        try {
            System.out.println("Server name: " + InetAddress.getLocalHost().getHostName());
            System.out.println("Server address: " + ipToString(InetAddress.getLocalHost().getAddress()));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        //change of policy to grant the correct working of connection RMI side
        System.setProperty("java.security.policy", "src/policy/sagrada.policy");
        System.setSecurityManager(new SecurityManager());

        //binding controller to registry
        Registry registry = LocateRegistry.createRegistry(1081);
        registry.rebind("controller", server.getController());

        //launch of server to accept socket connections
        server.startServerSocket();
    }

    /**
     * Method that translate an array containing an ip address in a string
     * @param arrayAddress  array containing an ip address
     * @return  a string representing the ip address in the array passed as a parameter
     */
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

    /**
     * constructor that starts server
     * @param port  server port
     */
    private Server(int port) {
        listOfClients = new ArrayList<>();
        listOfPlayers = new ArrayList<>();
        this.port = port;

        //Inserting values for timers during match, or the search of players
        Scanner in = new Scanner(System.in);
        System.out.println("Inserisci tempo di ricerca massimo: ");
        timeSearch = in.nextInt();
        System.out.println("Inserisci tempo massimo per fare una mossa: ");
        playerTimeMove = in.nextInt();

        inactivePlayers = new ArrayList<>();

        //creating the controller
        try {
            controller = new Controller(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method used to respect the singleton pattern applied on this class
     * @param port server port
     * @return  instance of server
     */
    public static Server instance(int port) {
        if (instance == null) instance = new Server(port);
        return instance;
    }

    /**
     * simple getter
     */
    public RMIController getController() {
        return controller;
    }

    /**
     * simple setter
     * @param controller
     */
    public void setController(RMIController controller) {
        this.controller = controller;
    }

    /**
     *
     * @return list of player's nickname
     */
    public ArrayList<String> getListOfClient() {
        ArrayList<String> nameClient = new ArrayList<>();
        for(ClientData client : listOfClients)
            nameClient.add(client.getName());
        return nameClient;
    }

    /**
     * simple getter
     */
    public ArrayList<String> getListOfPlayers() {
        return listOfPlayers;
    }

    /**
     * simple getter
     */
    public int getTimeSearch() {
        return timeSearch;
    }

    /**
     * simple getter
     */
    public int getPlayerTimeMove() {
        return playerTimeMove;
    }

    /**
     * Method used to add a new player's nickname to the list of players
     * @param account   player's nickname
     */
    public void addPlayers(String account) {
        listOfPlayers.add(account);
    }

    /**
     * Method used to add a new player's nickname to the data contained in list of clients
     * (variant for players who uses an RMI client)
     * @param account   player's nickname
     */
    public void addAccount(String account) {
        listOfClients.add(new ClientData(account));
    }

    /**
     * Method used to add a new player's nickname to the data contained in list of clients
     * (variant for players who uses a socket client)
     * @param account   player's nickname
     */
    public void addAccount(String account, ServerHandler handler) throws RemoteException {
        listOfClients.add(new ClientData(account, handler));
        controller.disableClient(account);
    }

    /**
     * Method that allows to set the state of a client/player
     * @param clientName    player's nickname
     * @param state         new state cf the specified client/player
     */
    public void setClientState(String clientName, ClientState state) {
        for (ClientData client : listOfClients) {
            if (client.getName().equals(clientName)) client.setState(state);
        }
    }

    /**
     *
     * @param clientName    player's nickname
     * @return              specified client/player's state
     */
    public ClientState getClientState(String clientName){
        for (ClientData client : listOfClients) {
            if (client.getName().equals(clientName)) return  client.getState();
        }
        return null;
    }

    /**
     * Method that accepts socket connections, and launches a new thread (ServerHandler)
     * for each client connected
     *
     * @see ServerHandler
     */
    public void startServerSocket() {
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println(e.getMessage()); // port not available
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
                    endLoop = true;                  //closed connection
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

    /**
     * Method used to add an nactive layer to the inactive players list
     * @param clientName    player's nickname
     */
    public void addInactivePlayers(String clientName) {
        inactivePlayers.add(clientName);
    }

    /**
     * simple getter
     */
    public ArrayList<String> getInactivePlayer() {
        return inactivePlayers;
    }

    /**
     *
     * @param name nickname of player you want to remove from list of players
     * @return always true
     */
    public boolean removePlayer(String name) {
        listOfPlayers.remove(listOfPlayers.indexOf(name));
        return true;
    }
}
