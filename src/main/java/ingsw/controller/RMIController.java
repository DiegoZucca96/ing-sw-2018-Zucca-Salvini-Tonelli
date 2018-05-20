package ingsw.controller;

import com.sun.org.apache.regexp.internal.RE;
import ingsw.model.Cell;
import ingsw.model.InitializerView;
import ingsw.model.windowpattern.WindowPattern;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface RMIController extends Remote {
    //Qua ci vanno tutti i metodi che il controller implementerà

    ArrayList<String> getListOfPlayers() throws RemoteException;

    void addPlayers(String saveUsername) throws RemoteException;

    void addAccount(String account)throws RemoteException;

    int getTimeSearch() throws RemoteException;

    int getPlayerTimeMove() throws RemoteException;

    void search() throws RemoteException;

    ArrayList<WindowPattern> getWindowChosen() throws RemoteException;

    void addWindow(List<Cell> myWindow) throws RemoteException;

    InitializerView initializeView() throws RemoteException;

    boolean access(String account) throws RemoteException;
}
