package ingsw.view;

import ingsw.controller.RMIController;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class GuiCaller extends UnicastRemoteObject {

    private RMIController controller;

    public GuiCaller(RMIController controller) throws RemoteException {
        super();
        this.controller=controller;
    }

    public void start() throws Exception {
        String[] a={};

        Registry registry=LocateRegistry.getRegistry("localhost",1080);
        UnicastRemoteObject.exportObject( this, 0);
        this.controller=(RMIController) registry.lookup("controller");

    }
}
