package ingsw.controller;

import javafx.scene.Node;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMIController extends Remote {
    ArrayList getListOfClient() throws RemoteException;

    void addAccount(String saveUsername) throws RemoteException;
    //Qua ci vanno tutti i metodi che il controller implementerà
}
