package ingsw.controller;

import com.sun.org.apache.regexp.internal.RE;
import ingsw.ServerHandler;
import ingsw.model.InitializerView;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMIController extends Remote {
    //Qua ci vanno tutti i metodi che il controller implementerà

    ArrayList<String> getListOfPlayers() throws RemoteException;

    int getSizeOfPlayers() throws RemoteException;

    int getTimeSearch() throws RemoteException;

    int getPlayerTimeMove() throws RemoteException;

    ArrayList<WPViewChoise> getWindowChosen() throws RemoteException;

    void addWindow(WPViewChoise wpmodel) throws RemoteException;

    String getCurrentPlayerName() throws RemoteException;

    void skip(String clientName) throws RemoteException;

    boolean useToolCard(String parameter) throws RemoteException;

    boolean waitForPlayers() throws RemoteException;

    int getTimeRemaining() throws RemoteException;

    ArrayList<WPViewChoise> getOthersChoice() throws RemoteException;

    InitializerView initializeView() throws RemoteException;

    boolean login(String account) throws RemoteException;

    boolean takeDie(int row, int column) throws RemoteException;

    boolean positionDie(int row, int column) throws RemoteException;

    boolean register(String account) throws RemoteException;

    boolean register(String account, ServerHandler serverHandler) throws RemoteException;

    //restituisce lo stato del client che lo richiede (usa toString dell'oggetto di tipo ClientState)
    String getPlayerState(String clientName) throws RemoteException;

    //prende un dado in posizione (row, column) dalla wp del giocatore corrente,
    //restituisce false se il dado non può essere preso (usa playerTakeWPDie in Match)
    boolean takeWPDie(int row, int column) throws RemoteException;

    //restituisce una matrice contenente stringhe rappresentanti le 4 wps tra cui il giocatore deve scegliere
    ArrayList<WPViewChoise> getRandomWPs() throws RemoteException;

    //restituisce true se tutti i giocatori hanno scelto
    boolean getOthersWP() throws RemoteException;

    void createHash(int nameWindow, String nameClient) throws RemoteException;
}