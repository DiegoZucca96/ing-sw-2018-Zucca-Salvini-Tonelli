package ingsw;

import ingsw.controller.Controller;
import ingsw.model.Match;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class Server {

    private ArrayList<String> listOfClient;

    public static void main(String[] args) throws RemoteException {
        Server server = new Server();
        Controller controller = new Controller(server);
        Registry registry = LocateRegistry.createRegistry(1080);
        registry.rebind("controller", controller);
        System.out.println("Server ready");
    }


    public Server(){
        listOfClient=new ArrayList<>();
    }

    public ArrayList<String> getListOfClient() {
        return listOfClient;
    }

    public void addClient(String account){
        if(!listOfClient.contains(account)){
            listOfClient.add(account);
        }
    }

}
