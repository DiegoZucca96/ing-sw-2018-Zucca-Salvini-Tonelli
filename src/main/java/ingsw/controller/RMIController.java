package ingsw.controller;

import ingsw.ServerHandler;
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

    int getSizeOfPlayers() throws RemoteException;

    int getTimeSearch() throws RemoteException;

    int getPlayerTimeMove() throws RemoteException;

    ArrayList<WindowPattern> getWindowChosen() throws RemoteException;

    void addWindow(List<Cell> myWindow) throws RemoteException;

    String getCurrentPlayerName() throws RemoteException;

    void skip(String clientName) throws RemoteException;

    boolean useToolCard(String parameter) throws RemoteException;

    boolean waitForPlayers() throws RemoteException;

    boolean getOthersChoice() throws RemoteException;

    InitializerView initializeView() throws RemoteException;

    boolean login(String account) throws RemoteException;

    boolean takeDie(int index) throws RemoteException;

    boolean positionDie(int row, int column) throws RemoteException;

    boolean register(String account) throws RemoteException;

    boolean register(String account, ServerHandler serverHandler) throws RemoteException;

    //restituisce lo stato del client che lo richiede (usa toString dello oggetto di tipo ClientState)
    String getPlayerState(String clientName) throws RemoteException;

}