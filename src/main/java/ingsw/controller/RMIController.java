package ingsw.controller;

import ingsw.model.InitializerView;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMIController extends Remote {
    //Qua ci vanno tutti i metodi che il controller implementerà

    ArrayList<String> getListOfPlayers() throws RemoteException;

    void addPlayers(String saveUsername) throws RemoteException;

    void addAccount(String account)throws RemoteException;

    InitializerView initializeView() throws RemoteException;

    boolean access(String account) throws RemoteException;
}
