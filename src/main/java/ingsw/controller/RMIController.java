package ingsw.controller;

import ingsw.ClientState;
import ingsw.ServerHandler;
import ingsw.model.ViewData;
import ingsw.model.ViewWP;
import ingsw.view.ToolView;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

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

    //disabilita il client in caso di disconnessione
    void disconnectClient(String clientName) throws RemoteException;

    boolean useToolCard(int i, ToolView parameter) throws RemoteException;

    boolean waitForPlayers() throws RemoteException;

    int getTimeRemaining() throws RemoteException;

    //restituisce un oggetto contenete i dati relativi agli oggetti da rappresentare nella view
    ViewData initializeView() throws RemoteException;

    boolean login(String account) throws RemoteException;

    void setWindowChosen(ArrayList<ViewWP> windowChosen) throws RemoteException;

    HashMap<String, Integer> getHashPlayers() throws RemoteException;

    void setHashPlayers(HashMap<String, Integer> hashPlayers) throws RemoteException;

    int getCoordinateSelectedY() throws RemoteException;

    int getCoordinateSelectedX() throws RemoteException;

    boolean takeDie(int row, int column) throws RemoteException;

    boolean positionDie(int row, int column) throws RemoteException;

    boolean register(String account) throws RemoteException;

    boolean register(String account, ServerHandler serverHandler) throws RemoteException;

    boolean isFinish() throws RemoteException;

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
    ViewData updateView() throws RemoteException;

    String getPVCard(String name) throws RemoteException;

    //Restituisce le wps scelte dai giocatori
    ArrayList<ViewWP> getPlayersWPs(String name) throws RemoteException;

    void setActive(Boolean active) throws RemoteException;

    boolean getActive() throws RemoteException;

    void rejoinedPlayer(String name) throws RemoteException;

    int getTimeMove() throws RemoteException;

    void setNullPlayer() throws RemoteException;

    int getRound() throws RemoteException;

    ArrayList<String> getInactiveList() throws RemoteException;

    ViewWP getWP(String userName) throws RemoteException;

    void orderWPChoise() throws RemoteException;

    Integer getScore(String name) throws RemoteException;

    void calculateScore() throws RemoteException;

    String findWinner() throws RemoteException;

    ArrayList<String> getListofMatchPlayers() throws RemoteException;

    boolean getInsertedDie() throws RemoteException;

    void setInsertedDie(boolean b) throws RemoteException;

    boolean getTool8Used() throws RemoteException;

    void setTool8Used(boolean num) throws RemoteException;

    boolean getClockwiseRound() throws RemoteException;

    int getTokenRemaining(String name) throws RemoteException;

    boolean iAmAlone() throws RemoteException;

    int getStartTimeMove() throws RemoteException;

    boolean removerPlayer(String name) throws RemoteException;

    ArrayList<String> someoneLeftGame()throws RemoteException;

    ArrayList<String> someoneRejoinedGame()throws RemoteException;
}