package ingsw.controller;

import com.sun.org.apache.regexp.internal.RE;
import ingsw.model.InitializerView;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMIController extends Remote {
    //Qua ci vanno tutti i metodi che il controller implementerà

    ArrayList<String> getListOfPlayers() throws RemoteException;

    void addPlayers(String saveUsername) throws RemoteException;

    void addAccount(String account)throws RemoteException;

    int getTimeSearch() throws RemoteException;

    int getPlayerTimeMove() throws RemoteException;

    InitializerView initializeView() throws RemoteException;

    boolean access(String account) throws RemoteException;
}
