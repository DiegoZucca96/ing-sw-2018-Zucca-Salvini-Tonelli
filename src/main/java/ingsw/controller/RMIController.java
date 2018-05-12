package ingsw.controller;

import ingsw.model.InitializerView;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMIController extends Remote {
    //Qua ci vanno tutti i metodi che il controller implementerà

    ArrayList getListOfClient() throws RemoteException;

    void addAccount(String saveUsername) throws RemoteException;

    InitializerView initializeView() throws RemoteException;
}
