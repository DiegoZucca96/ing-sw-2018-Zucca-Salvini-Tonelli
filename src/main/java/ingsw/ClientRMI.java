package ingsw;

import ingsw.controller.RMIController;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientRMI implements Client {

    private RMIController controller;

    public void startClient() throws IOException{
        Registry registry = LocateRegistry.getRegistry();
        try {
            controller = (RMIController) registry.lookup("controller");
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}
