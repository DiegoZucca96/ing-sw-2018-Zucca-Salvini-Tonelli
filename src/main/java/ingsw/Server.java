package ingsw;

import ingsw.controller.Controller;
import ingsw.controller.RMIController;
import ingsw.model.RandomGenerator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private ArrayList<ClientData> listOfClients; //associa ad ogni nome di un client il relativo stato
    private ArrayList<String> listOfPlayers; //Nomi dei giocatori in partita
    private static Server instance = null;
    private RMIController controller;
    private int port;
    private ClientState enableClient;
    private ClientState disableClient;
    private final int timeSearch;
    private final int playerTimeMove;
    private ArrayList<String> bannedPlayers;


    public static void main(String[] args) throws RemoteException {
        Server server = Server.instance(1080);

        //cambio la policy per grantire l'accesso ad RMI
        System.setProperty("java.security.policy", "src/policy/sagrada.policy");
        System.setSecurityManager(new SecurityManager());

        //lego il controller al registry
        Registry registry = LocateRegistry.createRegistry(1081);
        registry.rebind("controller", server.getController());

        //lancio il server socket
        server.startServerSocket();
    }


    private Server(int port) {
        listOfClients = new ArrayList<>();
        listOfPlayers = new ArrayList<>();
        this.port = port;
        Scanner in = new Scanner(System.in);
        System.out.println("Inserisci tempo di ricerca massimo: ");
        timeSearch = in.nextInt();
        //timeSearch = 120;
        System.out.println("Inserisci tempo massimo per fare una mossa: ");
        playerTimeMove = in.nextInt();
        //playerTimeMove = 120;
        bannedPlayers = new ArrayList<>();
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

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
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


    public void addBannedPlayers(String clientName) {
        bannedPlayers.add(clientName);
    }
}
