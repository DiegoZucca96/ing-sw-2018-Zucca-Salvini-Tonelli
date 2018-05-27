package ingsw.controller;

import ingsw.ClientState;
import ingsw.ServerHandler;
import ingsw.model.ViewData;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMIController extends Remote {

    //Qua ci vanno tutti i metodi che il controller implementerà
    ArrayList<String> getListOfPlayers() throws RemoteException;

    int getSizeOfPlayers() throws RemoteException;

    int getTimeSearch() throws RemoteException;

    int getPlayerTimeMove() throws RemoteException;

    ArrayList<ViewWP> getWindowChosen() throws RemoteException;

    //per associare ad un giocatore la sua wp è sufficiente una stringa, o un int
    void addWindow(ViewWP wpmodel) throws RemoteException;

    void addWindowName(String wpmodel) throws RemoteException;

    String getCurrentPlayerName() throws RemoteException;

    void skip(String clientName) throws RemoteException;

    //disabilita il client (il server ignora le sue richieste)
    ClientState enableClient(String clientName) throws RemoteException;

    //attiva il client (il server ascolta le sue richieste
    ClientState disableClient(String clientName) throws RemoteException;

    boolean useToolCard(String parameter) throws RemoteException;

    boolean waitForPlayers() throws RemoteException;

    int getTimeRemaining() throws RemoteException;

    //restituisce le wps scelte dai giocatori
    ArrayList<ViewWP> getPlayersWPs() throws RemoteException;

    //restituisce un oggetto contenete i dati relativi agli oggetti da rappresentare nella view
    ViewData initializeView() throws RemoteException;

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
    ArrayList<ViewWP> getRandomWPs() throws RemoteException;

    //restituisce true se tutti i giocatori hanno scelto
    boolean readyToPlay() throws RemoteException;

    void createHash(int nameWindow, String nameClient) throws RemoteException;

    //con il supporto degli observer mette nell'oggetto che restituisce gli ultimi cambiamenti del model
    ArrayList<ViewData> updateView() throws RemoteException;

    String getPVCard(String name) throws RemoteException;
}