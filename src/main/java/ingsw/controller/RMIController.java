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

/**
 * This interface contains all controller's methods.
 * To see the description of each one go to Controller class.
 *
 * @see Controller
 */

public interface RMIController extends Remote {

    ArrayList<String> getListOfPlayers() throws RemoteException;

    int getSizeOfPlayers() throws RemoteException;

    ArrayList<ViewWP> getWindowChosen() throws RemoteException;

    void addWindow(ViewWP wpmodel) throws RemoteException;

    String getCurrentPlayerName() throws RemoteException;

    void skip(String clientName) throws RemoteException;

    ClientState enableClient(String clientName) throws RemoteException;

    ClientState disableClient(String clientName) throws RemoteException;

    void disconnectClient(String clientName) throws RemoteException;

    boolean useToolCard(int i, ToolView parameter) throws RemoteException;

    boolean waitForPlayers() throws RemoteException;

    int getTimeRemaining() throws RemoteException;

    ViewData initializeView() throws RemoteException;

    boolean login(String account) throws RemoteException;

    HashMap<String, Integer> getHashPlayers() throws RemoteException;

    int getCoordinateSelectedY() throws RemoteException;

    boolean takeDie(int row, int column) throws RemoteException;

    boolean positionDie(int row, int column) throws RemoteException;

    boolean register(String account) throws RemoteException;

    boolean register(String account, ServerHandler serverHandler) throws RemoteException;

    boolean isFinish() throws RemoteException;

    String getPlayerState(String clientName) throws RemoteException;

    boolean takeWPDie(int row, int column) throws RemoteException;

    ArrayList<ViewWP> getRandomWPs() throws RemoteException;

    boolean readyToPlay() throws RemoteException;

    void createHash(int nameWindow, String nameClient) throws RemoteException;

    ViewData updateView() throws RemoteException;

    String getPVCard(String name) throws RemoteException;

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

    ViewData initializeViewCLI() throws RemoteException;

    boolean removerPlayer(String name) throws RemoteException;

    ArrayList<String> someoneLeftGame() throws RemoteException;

    ArrayList<String> someoneRejoinedGame() throws RemoteException;

    void stopTimer() throws RemoteException;
}