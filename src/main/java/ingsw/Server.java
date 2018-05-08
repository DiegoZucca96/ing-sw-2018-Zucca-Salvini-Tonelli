package ingsw;

import ingsw.controller.Controller;
import ingsw.model.Match;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    private Client[] listOfClient;
    private Match[] listOfMatches;
    public static void main(String[] args) throws RemoteException {
        Controller controller = new Controller();
        Registry registry = LocateRegistry.createRegistry(1080);
        registry.rebind("controller", controller);
        System.out.println("Server ready");
    }
}
