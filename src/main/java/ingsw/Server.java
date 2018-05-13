package ingsw;

import ingsw.controller.Controller;
import ingsw.model.Match;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class Server {

    private ArrayList<String> listOfClient;
    private ArrayList<String> listOfPlayers ;
    public static void main(String[] args) throws RemoteException {
        Server server = new Server();
        Controller controller = new Controller(server);
        Registry registry = LocateRegistry.createRegistry(1080);
        registry.rebind("controller", controller);
        System.out.println("Server ready");
    }


    public Server(){
        listOfClient=new ArrayList<>();
        listOfPlayers = new ArrayList<>();
    }

    public ArrayList<String> getListOfClient() {
        return listOfClient;
    }

    public ArrayList<String> getListOfPlayers() {
        return listOfPlayers;
    }

    public void addClient(String account){
        listOfClient.add(account);
        listOfPlayers.add(account);
    }

    public void addAccount(String account) {
        listOfClient.add(account);
    }

}
