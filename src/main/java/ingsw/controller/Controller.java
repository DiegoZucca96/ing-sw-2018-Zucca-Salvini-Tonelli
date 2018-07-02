package ingsw.controller;

import ingsw.*;
import ingsw.model.*;
import ingsw.model.windowpattern.WindowPattern;
import ingsw.server.Server;
import ingsw.view.GUI.ToolView;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

/**
 * Author : Diego Zucca
 *
 * This class makes possible the interaction between a generic client and the model of the game.
 * It contains all necessary methods to control if an operation is allowed or not and to change the model of the game.
 * Controller has a lot of parameters, in order to control everything in a correct and simple way.
 *
 * @see ControllerTimer
 * @see WindowPFactory
 * @see Match
 */

public class Controller extends UnicastRemoteObject implements RMIController {

    private Timer timer;
    private ControllerTimer controllerTimer;
    private Server server;
    private WindowPFactory wpFactory;
    private RandomGenerator rg;
    private static Match match = null;
    private int timeSearch;
    private int playerMoveTime;
    private static int access=0;  //Parameter used to avoid multiple run of the same method during the beginning of the match
    private static int startTimer = 0;  //Parameter used to start the timer of each player
    private ArrayList<ViewWP> windowChosen;
    private HashMap<String,Integer> hashPlayers; //Contains a player and his WP chosen
    private boolean active;
    private int turn;
    private boolean isFinish;
    private ArrayList<String> playersLeft;  //Contains all players that had left the game
    private int playerNotifiedExit = 0;     //Parameter used to notify all players about an exit player
    private int playerNotifiedEntry = 0;    //Parameter used to notify all players about an entry player

    /**
     * Constructor of Controller, it initialized every parameter
     * @param server is the istance of the server
     * @throws RemoteException if there is a problem to get "wpFactory" parameter
     */
    public Controller(Server server) throws RemoteException {
        super();
        this.server= server;
        try {
            this.wpFactory = new WindowPFactory();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.rg = new RandomGenerator(wpFactory.getNumOfWPs());
        this.windowChosen = new ArrayList<>();
        this.hashPlayers = new HashMap<>();
        this.timeSearch = server.getTimeSearch();
        this.playerMoveTime = server.getPlayerTimeMove();
        this.timer = new Timer();
        this.controllerTimer = new ControllerTimer(timeSearch,playerMoveTime);
        this.active=false;
        this.turn=0;
        this.isFinish=false;
        this.playersLeft = new ArrayList<>();
    }

    /**
     * Simple getter method
     * @return list of active players that are playing the match
     * @throws RemoteException if the server is not reachable
     */
    @Override
    public ArrayList<String> getListOfPlayers() throws RemoteException{
        return server.getListOfPlayers();    }

    /**
     * Simple getter method
     * @return size of the list of active players that are playing the match
     * @throws RemoteException if the server is not reachable
     */
    @Override
    public int getSizeOfPlayers() throws RemoteException {
        return getListOfPlayers().size();
    }

    /**
     * Simple getter method
     * @return a list of window chosen by all players
     * @throws RemoteException if the controller is not usable, maybe because of lost connection
     */
    @Override
    public ArrayList<ViewWP> getWindowChosen() throws RemoteException {
        return windowChosen;
    }

    /**
     * Simple getter method
     * @return the HashMap with association player - window chosen
     */
    @Override
    public HashMap<String, Integer> getHashPlayers() {
        return hashPlayers;
    }

    /**
     * Getter method, it tries to get coordinate about the selected die of current player
     * It returns only Y coordinate because in our model the X coordinate is always zero in DraftPool.
     * @return -1 if player didn't select any die, otherwise the Y coordinate
     * @see DraftPool
     */
    @Override
    public int getCoordinateSelectedY(){
        if(match.getCurrentPlayer().getCoordinateDieSelected()==null)
            return -1;
        else
            return match.getCurrentPlayer().getCoordinateDieSelected().getY();
    }

    /**
     * It allows a player to select a die from DraftPool after a control, to avoid that he could select more than one.
     * After that it sets the value of die and coordinate selected by the player
     * @param row it is the X coordinate
     * @param column it is the Y coordinate
     * @return true if the player can make this action, otherwise false
     * @throws RemoteException if the controller is not usable, maybe because of lost connection
     */
    @Override
    public boolean takeDie(int row, int column) throws RemoteException{
        if(match.getCurrentPlayer().getDieSelected()==null && !match.getCurrentPlayer().getInsertedDie()){
            int index = 3*row + column;
            Coordinate c = new Coordinate(row,column);
            match.getCurrentPlayer().setCoordinateDieSelected(c);
            match.getCurrentPlayer().setDieSelected(match.playerTakeDie(index));
            return true;
        }
        return false;
    }

    /**
     * It allows a player to select a die from the window of the player after a control, to avoid that he could select more than one.
     * After that it sets the value of die and coordinate selected by the player.
     * This method is called only when a tool card is used.
     * @param row it is the X coordinate
     * @param column it is the Y coordinate
     * @return true if the player can make this action, otherwise false
     * @throws RemoteException if the controller is not usable, maybe because of lost connection
     */
    @Override
    public boolean takeWPDie(int row, int column) throws RemoteException {
        if(match.getCurrentPlayer().getCoordinateDieSelected()==null){
                Coordinate c = new Coordinate(row,column);
                match.getCurrentPlayer().setCoordinateDieSelected(c);
                match.getCurrentPlayer().setDieSelected(match.getCurrentPlayer().getWindowPattern().getDie(c));
                return true;
            }
            return false;
    }

    /**
     * This method allows player to place his selected die into his window, only if all restriction is verified.
     * It also verifies that only one die can be inserted and that the selected die is not null.
     * To remove the selected die from DraftPool it will be set on white color and zero number
     * @param row it is the X destination coordinate
     * @param column it is the destination Y coordinate
     * @return true if the player can make this action, otherwise false
     * @throws RemoteException if the controller is not usable, maybe because of lost connection
     */
    @Override
    public boolean positionDie(int row, int column) throws RemoteException{
        if(match.getCurrentPlayer().getDieSelected()!=null && !match.getCurrentPlayer().getInsertedDie()){
            Coordinate coordinate = new Coordinate(row,column);
            if(match.positionDie(match.getCurrentPlayer().getDieSelected(),coordinate)){
                match.draftpoolRemoveDie(match.getCurrentPlayer().getCoordinateDieSelected().getY());
                match.getCurrentPlayer().setCoordinateDieSelected(null);
                match.getCurrentPlayer().setDieSelected(null);
                match.getCurrentPlayer().setInsertedDie(true);
                return true;
            }
        }
        return false;
    }

    /**
     * This method is synchronized in order to avoid players with same nickname registered.
     * It controls if the input nickname is already registered into the server, if not the registration will be done.
     * @param account it is the input nickname of the new player
     * @return true if player can use this nickname, otherwise false
     * @throws RemoteException if the controller is not usable, maybe because of lost connection, or if server is not reachable
     * @see Server
     */
    @Override
    public synchronized boolean register(String account) throws RemoteException{
        if (server.getListOfClient().contains(account))
            return false;
        else{
            server.addAccount(account);
            return true;
        }
    }

    /**
     * This method is synchronized in order to avoid players with same nickname registered.
     * It controls if the input nickname is already registered into the server, if not the registration will be done.
     * It is used with Socket connection
     * @param account it is the input nickname of the new player
     * @param serverHandler it is used in Socket connection to manage all messages exchanged between client and controller
     * @return true if player can use this nickname, otherwise false
     * @throws RemoteException if the controller is not usable, maybe because of lost connection, or if server is not reachable
     * @see ServerHandler
     */
    @Override
    public synchronized boolean register(String account, ServerHandler serverHandler) throws RemoteException{
        if (server.getListOfClient().contains(account))
            return false;
        else{
            server.addAccount(account,serverHandler);
            return true;
        }
    }

    /**
     * This method is synchronized in order to avoid players with same nickname in the same match.
     * It used the private method access to verify if a player can join the game or not.
     * Every player who joins the game is disabled (as Client state).
     * @param account it is the input nickname of the player
     * @return true if player can join the game, otherwise false
     * @throws RemoteException if the controller is not usable, maybe because of lost connection, or if server is not reachable
     */
    @Override
    public synchronized boolean login(String account) throws RemoteException {
        if(access(account)){
            disableClient(account);
            return true;
        }
        else
            return false;
    }

    /**
     * It controls if the nickname is already used or if is registered in the server.
     * It allows also to players that has left the game (because of inactivity, voluntary exit or connection lost) to rejoin the match.
     * @param account it is the input nickname of the player
     * @return true if player can join the game, otherwise false
     */
    private boolean access(String account){
        if(server.getListOfClient().contains(account)){
            if(!server.getListOfPlayers().contains(account) && server.getListOfPlayers().size()<4 && getTimeRemaining()>0){
                server.addPlayers(account);
                return true;
            }
            else{
                try {
                    if(server.getClientState(account)==null)
                        return false;
                    if(getPlayerState(account).equalsIgnoreCase("disconnected") && server.getInactivePlayer().contains(account) && !isFinish){
                        windowChosen = updateView().getWps();
                        rejoinedPlayer(account);
                        return true;
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * This method create four random windows to show to the players.
     * The synchronized method is used to ensure that every player will have different and no repeated window.
     * @return a list of four windows
     */
    public synchronized ArrayList<ViewWP> getRandomWPs(){
        ArrayList<ViewWP> wps = new ArrayList<>();
        for(int i=0; i<4; i++){
            ViewWP wpobject = new ViewWP();
            int x = rg.random();
            WindowPattern wp = wpFactory.createWindowPattern(x);
            wpobject.setName(wp.getTitle());
            wpobject.setDifficulty(Integer.toString(wp.getDifficulty()));
            wpobject.setWps(wp.toMatrix());
            wpobject.setNumberWP(x);
            wps.add(wpobject);
        }
        return wps;
    }

    /**
     * It is used to start the match when every player has made his choice about the choice of the window
     * @return true if all players is ready, otherwise false
     * @throws RemoteException if the controller is not usable, maybe because of lost connection
     */
    @Override
    public boolean readyToPlay() throws RemoteException {
        if(windowChosen.size()<getSizeOfPlayers())
            return false;
        else return true;
    }

    /**
     * It creates an HashMap with the name of client as key and the number of the window chosen by him as value.
     * @param numberWP it is the number associated to the window
     * @param nameClient it is the name of the client
     */
    @Override
    public void createHash(int numberWP, String nameClient) {
        hashPlayers.put(nameClient,numberWP);
    }

    /**
     * This method simply return an istance of ViewData that contains all modified objects.
     * It is used to update all players.
     * @return a ViewData instance
     * @see ViewData
     */
    @Override
    public ViewData updateView() {
        return ViewData.instance();
    }

    /**
     * It simply add the window chosen by the client into a list
     * @param wpmodel it is the window chosen
     */
    @Override
    public void addWindow(ViewWP wpmodel){
        windowChosen.add(wpmodel);
    }

    /**
     * Simply getter method
     * @return the name of the current player in the game
     */
    @Override
    public String getCurrentPlayerName(){
        return match.getCurrentPlayer().getName();
    }

    /**
     * This method is used when the player's turn is over and the next player will play.
     * It is used also when a player leaves the game (voluntary or not) or when the skip button is pressed.
     * If a player cannot play (maybe because he used the tool card number 8 or because is disconnected or inactive) he will be
     * skipped automatically, and that will be repeated since an "enabled" player will be found.
     * It also manages the disconnection of a player and controls if the match is over or not.
     * @param clientName it is the name of the "enabled" client
     * @throws RemoteException if the controller is not usable, maybe because of lost connection, or if server is not reachable
     */
    @Override
    public void skip(String clientName) throws RemoteException{
        if(iAmAlone()){
            isFinish=true;
            controllerTimer.setTimeSearch(timeSearch);
        }
        if(getPlayerState(clientName).equals("enabled")) {
            setNullPlayer();
            if (!active) {
                if(!getInactiveList().contains(clientName)){
                    timeout(clientName);
                }
                disconnectClient(clientName);
            }
            if(getPlayerState(clientName).equalsIgnoreCase("enabled"))
                disableClient(getCurrentPlayerName());
            turn++;
            active=false;
            if (turn == getSizeOfPlayers() * 2) {
                if (match.getRound() == 10) isFinish = true;
                else {
                    setInsertedDie(false);
                    match.endRound();
                    turn = 0;
                }
            } else {
                setInsertedDie(false);
                match.nextTurn();
            }
            if (!isFinish) {
                while ((getPlayerState(getCurrentPlayerName()).equalsIgnoreCase("disconnected") && (turn < getSizeOfPlayers() * 2)) || (getTool8Used() && (turn < getSizeOfPlayers() * 2))) {
                    turn++;
                    setTool8Used(false);
                    if (turn == getSizeOfPlayers() * 2) {
                        match.endRound();
                        if (match.getRound() == 10) isFinish = true;
                    }
                    else{
                        match.nextTurn();
                    }
                }
                if (turn == getSizeOfPlayers() * 2) {
                    turn = 0;
                }
                if(!isFinish){
                    timer.cancel();
                    enableClient(getCurrentPlayerName());
                    controllerTimer.setTimeMoveRemaining(playerMoveTime);
                    timer = new Timer();
                    controllerTimer.startPlayerTimer(this, timer);
                }
            }
        }else{
            if (!active) {
                if(!getInactiveList().contains(clientName))
                    timeout(clientName);
                disconnectClient(clientName);
            }
        }
    }

    /**
     * It controls if there is only one player remaining in the match.
     * @return true if it is alone, otherwise false
     * @throws RemoteException if the controller is not usable, maybe because of lost connection, or if server is not reachable
     */
    @Override
    public boolean iAmAlone() throws RemoteException{
        if(getSizeOfPlayers()-1 == getInactiveList().size())
            return true;
        else
            return false;
    }

    /**
     * Simply getter method
     * @return the time that a player has to make his turn
     * @throws RemoteException if the controller is not usable, maybe because of lost connection
     */
    @Override
    public int getStartTimeMove() throws RemoteException {
        return playerMoveTime;
    }

    /**
     * It removes a player from the server's list
     * @param name it is the name of player that has to be removed
     * @return true if no exception has thrown
     * @throws RemoteException if the controller is not usable, maybe because of lost connection, or if server is not reachable
     */
    @Override
    public boolean removerPlayer(String name) throws RemoteException {
        if(server.removePlayer(name))
            return true;
        else
            return false;
    }

    /**
     * This method is used to notify the other players if someone has left the game, for any reason
     * @return a list of the player's name that left the game
     * @throws RemoteException if the controller is not usable, maybe because of lost connection, or if server is not reachable
     */
    @Override
    public ArrayList<String> someoneLeftGame() throws RemoteException {
        ArrayList<String> serverInactive = server.getInactivePlayer();
        ArrayList<String> playerOut = new ArrayList<>();
        if(serverInactive.isEmpty())
            return playerOut;
        else{
            for(String s : serverInactive){
                if (!playersLeft.contains(s)){
                    playerOut.add(s);
                }
            }
            playerNotifiedExit++;
            if(playerNotifiedExit < getSizeOfPlayers()-serverInactive.size()){
                return playerOut;
            }
            else{
                playerNotifiedExit =0;
                for(String s : serverInactive){
                    if (!playersLeft.contains(s)){
                        playersLeft.add(s);
                    }
                }
                return playerOut;
            }
        }
    }

    /**
     * This method is used to notify the other players if someone has rejoined the game
     * @return a list of the player's name that has rejoined into the match
     * @throws RemoteException if the controller is not usable, maybe because of lost connection, or if server is not reachable
     */
    @Override
    public ArrayList<String> someoneRejoinedGame() throws RemoteException {
        ArrayList<String> serverInactive = server.getInactivePlayer();
        ArrayList<String> playerIn = new ArrayList<>();
        for(String s : playersLeft){
            if (!serverInactive.contains(s)){
                playerIn.add(s);
            }
        }
        if(playerIn.isEmpty())
            return playerIn;
        playerNotifiedEntry++;
        if(playerNotifiedEntry < getSizeOfPlayers()-serverInactive.size()){
            return playerIn;
        }
        else{
            playerNotifiedEntry=0;
            for(int i=0; i< playersLeft.size();i++){
                if (!serverInactive.contains(playersLeft.get(i))){
                    playersLeft.remove(i);
                }
            }
            return playerIn;
        }
    }

    /**
     * This method stops the timer of the match, when it is over
     * @throws RemoteException if the controller is not usable, maybe because of lost connection
     */
    @Override
    public void stopTimer() throws RemoteException {
        timer.cancel();
    }

    /**
     * Simply getter method, it says if the match is over or not
     * @return true if the match is over, otherwise false
     */
    @Override
    public boolean isFinish() {
        return isFinish;
    }

    /**
     * Private method used by skip method, it adds the inactive player in a server's list of inactive players
     * @param clientName it is the name of the inactive player
     */
    private void timeout(String clientName) {
        server.addInactivePlayers(clientName);
    }

    /**
     * Simply getter method
     * @param clientName it is the name of the player i want to know the state
     * @return the state of this player
     * @throws RemoteException if the controller is not usable, maybe because of lost connection, or if server is not reachable
     * @see ClientState
     */
    @Override
    public String getPlayerState(String clientName) throws RemoteException{
        return server.getClientState(clientName).toString();
    }

    /**
     * This method makes a player enabled, so he can play his turn.
     * @param clientName it is the name of the player that will be "enabled"
     * @return a new enabled client with the same name that he has before
     * @throws RemoteException if the controller is not usable, maybe because of lost connection, or if server is not reachable
     * @see EnableClient
     */
    @Override
    public ClientState enableClient(String clientName) throws RemoteException {
        return new EnableClient().setState(clientName);
    }

    /**
     * This method makes a player disabled, he can't make any kind of move except leave the game.
     * @param clientName it is the name of the player that will be "disabled"
     * @return a new disabled client with the same name that he has before
     * @throws RemoteException if the controller is not usable, maybe because of lost connection, or if server is not reachable
     * @see DisableClient
     */
    @Override
    public ClientState disableClient(String clientName) throws RemoteException {
        return new DisableClient().setState(clientName);
    }

    /**
     * This method makes a player disconnected, this happens when that player leaves the game or loses the connection.
     * @param clientName it is the name of the player that will be "disconnected"
     * @throws RemoteException if the controller is not usable, maybe because of lost connection, or if server is not reachable
     * @see DisconnectedClient
     */
    @Override
    public void disconnectClient(String clientName) throws RemoteException {
         new DisconnectedClient().setState(clientName);
    }

    /**
     * This method makes two main things.
     * The first one, it consumes the player's token if he can use that tool card.
     * The second one, it takes a ToolView object that contains some inputs from the player and converts it into a model object.
     * Each tool card needs different inputs, to manage this we used a switch on the number of the tool card selected.
     * When everything is ready this object is passed to match method that will execute the tool card method
     * @param idCard it is the number of tool card selected
     * @param toolView it is an object that contains all input form the player
     * @return true if everything goes well, otherwise false
     */
    @Override
    public boolean useToolCard(int idCard, ToolView toolView){
        PlayerToolParameter pt = null;
        //This "if" is used to consume tokens when a tool card is clicked, only if it is usable
        if(idCard!=8){
            if(toolView==null){
                if(match.playerUseTool(idCard,null))
                    return true;
                else
                    return false;
            }
        }
        pt = new PlayerToolParameter();
        switch(idCard){
            case 1: {
                pt.setC1(new Coordinate(toolView.getStartRow1(), toolView.getStartCol1()));
                pt.setDieModified(toolView.getDieModified());
                break;
            }case 2:{
                pt.setC1(new Coordinate(toolView.getStartRow1(), toolView.getStartCol1()));
                pt.setD1(new Coordinate(toolView.getEndRow1(), toolView.getEndCol1()));
                break;
            }case 3:{
                pt.setC1(new Coordinate(toolView.getStartRow1(), toolView.getStartCol1()));
                pt.setD1(new Coordinate(toolView.getEndRow1(), toolView.getEndCol1()));
                break;
            }case 4:{
                if(toolView.getPhase()==0) {
                    pt.setC1(new Coordinate(toolView.getStartRow1(), toolView.getStartCol1()));
                    pt.setD1(new Coordinate(toolView.getEndRow1(), toolView.getEndCol1()));
                    pt.setPhase(toolView.getPhase());
                }else {
                    pt.setC2(new Coordinate(toolView.getStartRow2(), toolView.getStartCol2()));
                    pt.setD2(new Coordinate(toolView.getEndRow2(), toolView.getEndCol2()));
                    pt.setPhase(toolView.getPhase());
                }break;
            }case 5:{
                pt.setC1(new Coordinate(toolView.getStartRow1(), toolView.getStartCol1()));
                pt.setD1(new Coordinate(toolView.getEndRow1(), toolView.getEndCol1()));
                pt.setRound(toolView.getRound());
                break;
            }case 6:{
                pt.setC1(new Coordinate(toolView.getStartRow1(), toolView.getStartCol1()));
                break;
            }case 7:{
                pt.setListOfCoordinateY(toolView.getListOfCoordinateY());
                break;
            }case 8:{
                pt=null;
                break;
            }case 9:{
                pt.setC1(new Coordinate(toolView.getStartRow1(), toolView.getStartCol1()));
                pt.setD1(new Coordinate(toolView.getEndRow1(), toolView.getEndCol1()));
                break;
            }case 10:{
                pt.setC1(new Coordinate(toolView.getStartRow1(), toolView.getStartCol1()));
                break;
            }case 11:{
                if(toolView.getPhase()==0){
                    pt.setC1(new Coordinate(toolView.getStartRow1(), toolView.getStartCol1()));
                    pt.setPhase(toolView.getPhase());
                }
                if(toolView.getPhase()==1){
                    pt.setDieModified(toolView.getDieModified());
                    pt.setPhase(toolView.getPhase());
                }
                if(toolView.getPhase()==2){
                    pt.setC1(new Coordinate(toolView.getStartRow1(), toolView.getStartCol1()));
                    pt.setPhase(toolView.getPhase());
                }
                break;
            }case 12:{
                if(toolView.getPhase() == 0){
                    pt.setC1(new Coordinate(toolView.getStartRow1(), toolView.getStartCol1()));
                    pt.setPhase(toolView.getPhase());
                    pt.setRound(toolView.getRound());
                }
                if(toolView.getPhase()==1){
                    pt.setC1(new Coordinate(toolView.getStartRow1(), toolView.getStartCol1()));
                    pt.setD1(new Coordinate(toolView.getEndRow1(), toolView.getEndCol1()));
                    pt.setPhase(toolView.getPhase());
                }
                if(toolView.getPhase()==2){
                    pt.setC1(new Coordinate(toolView.getStartRow2(), toolView.getStartCol2()));
                    pt.setD1(new Coordinate(toolView.getEndRow2(), toolView.getEndCol2()));
                    pt.setPhase(toolView.getPhase());
                }
                break;
            }default:{
                break;
            }
        }
        if(match.playerUseTool(idCard,pt))
            return true;
        else
            return false;
    }

    /**
     * This method is synchronized in order to avoid multiple access.
     * It starts the research timer only if the client that calls this method is the first one, this restriction is made with
     * the "access" parameter.
     * This parameter is used to prevent many timers from starting at the same time
     * @return true when the timer is over and at least two players are found, otherwise false
     * @throws RemoteException if the controller is not usable, maybe because of lost connection, or if server is not reachable
     */
    @Override
    public synchronized boolean waitForPlayers() throws RemoteException{
        if(access == 0){
            access++;
            controllerTimer.search(this,timer);
        }
        if(getSizeOfPlayers()==0){
            timer.cancel();
            access--;
            return false;
        }
        if(getTimeRemaining()<=0){
            timer.cancel();
            return true;
        }
        else
            return false;
    }

    /**
     * Simply getter method
     * @return the research time remaining
     */
    @Override
    public int getTimeRemaining() {
        return controllerTimer.getTimeRemaining();
    }

    /**
     * This method return the enemy's windows using the HashMap
     * @param name it is the name of the player that wants to know the enemy's windows
     * @return a list of the enemy's windows
     * @throws RemoteException if the controller is not usable, maybe because of lost connection, or if server is not reachable
     */
    @Override
    public synchronized ArrayList<ViewWP> getPlayersWPs(String name) throws RemoteException{
        ArrayList<ViewWP> enemyWPs = new ArrayList<>();
        for(ViewWP wp : getWindowChosen()){
            if(getHashPlayers().get(name) != wp.getNumberWP()) {
                enemyWPs.add(wp);
            }
        }
        return enemyWPs;
    }

    /**
     * Simply setter method
     * @param active it is the boolean value to set
     */
    @Override
    public void setActive(Boolean active) {
        this.active=active;
    }

    /**
     * Simply getter method, it says if the player is active or not
     * @return true if the player is active, otherwise false
     * @throws RemoteException if the controller is not usable, maybe because of lost connection
     */
    @Override
    public boolean getActive() throws RemoteException {
        return active;
    }

    /**
     * This method removes a player that rejoined in the game from the server's list of inactive player
     * @param name it is the name of the player that rejoins in the game
     * @throws RemoteException if the controller is not usable, maybe because of lost connection, or if server is not reachable
     */
    @Override
    public void rejoinedPlayer(String name) throws RemoteException {
        if(server.getInactivePlayer().contains(name)){
            server.getInactivePlayer().remove(name);
        }
    }

    /**
     * Simply getter method
     * @return the time remaining to player to make a move
     * @throws RemoteException if the controller is not usable, maybe because of lost connection
     */
    @Override
    public int getTimeMove() throws RemoteException {
        return controllerTimer.getTimeMoveRemaining();
    }

    /**
     * Simply setter method, it cancels the informations about the die and the coordinate selected by the player
     * @throws RemoteException if the controller is not usable, maybe because of lost connection
     */
    @Override
    public void setNullPlayer() throws RemoteException {
        match.getCurrentPlayer().setDieSelected(null);
        match.getCurrentPlayer().setCoordinateDieSelected(null);
    }

    /**
     * Simply getter method
     * @return the round of the match
     * @throws RemoteException if the controller is not usable, maybe because of lost connection
     */
    @Override
    public int getRound() throws RemoteException {
        return match.getRound();
    }

    /**
     * Simply getter method
     * @return a list of inactive players contained into the server
     * @throws RemoteException if the controller is not usable, maybe because of lost connection, or if server is not reachable
     */
    @Override
    public ArrayList<String> getInactiveList() {
        return server.getInactivePlayer();
    }

    /**
     * Simply getter method
     * @param userName it is the name of the player
     * @return the window chosen by the player required by userName
     * @throws RemoteException if the controller is not usable, maybe because of lost connection, or if server is not reachable
     */
    @Override
    public ViewWP getWP(String userName) throws RemoteException {
        String nameWP = null;
        for(Player p : match.getPlayers()){
            if(p.getName().equals(userName)){
                nameWP = p.getWindowPattern().getTitle();
            }
        }
        for(int i=0; i<windowChosen.size(); i++){
            String nameWC = windowChosen.get(i).getName();
            if(nameWC.equalsIgnoreCase(nameWP)){
                return windowChosen.get(i);
            }
        }
        return null;
    }

    /**
     * This method is used during the creation of the match.
     * It orders the windows chosen by the players, following the order of server's list of players
     * @throws RemoteException if the controller is not usable, maybe because of lost connection, or if server is not reachable
     */
    @Override
    public synchronized void orderWPChoise() throws RemoteException {
        if(access==1){
            ArrayList<ViewWP> windowOrdered = new ArrayList<>();
            ArrayList<String> players = server.getListOfPlayers();
            for(String p : players){
                int num = hashPlayers.get(p);
                for(ViewWP w: windowChosen){
                    if(num==w.getNumberWP())
                        windowOrdered.add(w);
                }
            }
            windowChosen = windowOrdered;
            access++;
        }
    }

    /**
     * Simply getter method
     * @param name it is the name of the player
     * @return the score made by the player
     * @throws RemoteException if the controller is not usable, maybe because of lost connection
     */
    @Override
    public Integer getScore(String name) throws RemoteException {
        int indexPlayer = getListofMatchPlayers().indexOf(name);
        return match.getPlayers().get(indexPlayer).getScore();
    }

    /**
     * This method calls a match method to calculate players' score
     * @throws RemoteException if the controller is not usable, maybe because of lost connection, or if server is not reachable
     */
    @Override
    public synchronized void calculateScore() throws RemoteException {
        match.playersScore();
    }

    /**
     * This method calls a match method to find the winner of the match
     * @throws RemoteException if the controller is not usable, maybe because of lost connection, or if server is not reachable
     */
    @Override
    public String findWinner() throws RemoteException {
        return match.findWinner().getName();
    }

    /**
     * Simply getter method, is different from the "getListOfPlayers" method because the match list is different based on the round
     * @return a list of players ordered by their turn
     * @throws RemoteException if the controller is not usable, maybe because of lost connection, or if server is not reachable
     */
    @Override
    public ArrayList<String> getListofMatchPlayers() throws RemoteException {
        ArrayList<String> nameMatchPlayers = new ArrayList<>();
        for(int i=0; i<getListOfPlayers().size();i++){
            String name = match.getPlayers().get(i).getName();
            nameMatchPlayers.add(name);
        }
        return nameMatchPlayers;
    }

    /**
     * Simply getter method
     * @return true if the player has already inserted a die in his turn
     * @throws RemoteException if the controller is not usable, maybe because of lost connection
     */
    @Override
    public boolean getInsertedDie() throws RemoteException {
        return match.getCurrentPlayer().getInsertedDie();
    }

    /**
     * Simply setter method
     * @param b it is the value to be assigned
     * @throws RemoteException if the controller is not usable, maybe because of lost connection
     */
    @Override
    public void setInsertedDie(boolean b) throws RemoteException {
        match.getCurrentPlayer().setInsertedDie(b);
    }

    /**
     * Simply getter method
     * @return true if the player has used the tool card number 8
     * @throws RemoteException if the controller is not usable, maybe because of lost connection
     */
    @Override
    public boolean getTool8Used() throws RemoteException {
        return match.getCurrentPlayer().getTool8Used();
    }

    /**
     * Simply setter method
     * @param b it is the value to be assigned
     * @throws RemoteException if the controller is not usable, maybe because of lost connection
     */
    @Override
    public void setTool8Used(boolean b) throws RemoteException {
        match.getCurrentPlayer().setTool8Used(b);
    }

    /**
     * Simply getter method
     * @return true if the turn is clockwise, otherwise false
     * @throws RemoteException if the controller is not usable, maybe because of lost connection
     */
    @Override
    public boolean getClockwiseRound() throws RemoteException {
        return match.getClockwiseRound();
    }

    /**
     * Simply getter method
     * @param name it is the name of the player
     * @return the number of token remaining at that player
     * @throws RemoteException if the controller is not usable, maybe because of lost connection
     */
    @Override
    public int getTokenRemaining(String name) throws RemoteException {
        int index = getListofMatchPlayers().indexOf(name);
        return match.getPlayers().get(index).getTokens();
    }

    /**
     * Simply getter method
     * @param name it is the name of the player
     * @return the color of the private card of that player
     * @throws RemoteException if the controller is not usable, maybe because of lost connection
     */
    @Override
    public String getPVCard(String name) throws RemoteException {
        String colorPV = null;
        ArrayList<Player> playersModel = match.getPlayers();
        for(Player p : playersModel){
            if(name.equals(p.getName())){
                colorPV = PVObjectiveCard.getPVCard(p.getPvObjectiveCard().getColor());
            }
        }
        return colorPV;
    }

    /**
     * This method creates the match, with windows chosen, public card and tool card, and the instance of ViewData.
     * @return the ViewData object that will be used from the view to shows everything correctly
     * @throws RemoteException if the controller is not usable, maybe because of lost connection
     */
    @Override
    public synchronized ViewData initializeView() throws RemoteException {
        match = instanceMatch();
        ViewData init = match.getInit();
        init.setWps(windowChosen);
        enableClient(getCurrentPlayerName());
        if(startTimer==0){
            timer = new Timer();
            controllerTimer.startPlayerTimer(this,timer);
            startTimer++;
        }
        return init;
    }

    /**Author: Elio Salvini
     *
     *CLI support
     *
     * ???
     */
    @Override
    public synchronized ViewData initializeViewCLI() throws RemoteException {
        match = instanceMatch();
        ViewData init = match.getInit();
        init.setWps(windowChosen);
        //new lines
        init.setToolCardsCLI(match.toolCardsToString());
        init.setPbCards(match.pbCardsToString());
        //end new lines
        enableClient(getCurrentPlayerName());
        if(startTimer==0){
            timer = new Timer();
            controllerTimer.startPlayerTimer(this,timer);
            startTimer++;
        }
        return init;
    }
    /**End CLI support*/

    /**
     * Simple method that creates the instance of the match only if it not exists, otherwise it returns that instance
     * @return the instance of the match
     * @throws RemoteException if the controller is not usable, maybe because of lost connection
     */
    private Match instanceMatch() throws RemoteException {
        if(match == null)
            match = new Match(1,getListOfPlayers(),reorderWPandPlayer());
        return match;
    }

    /**
     * This method is used to associate the correct window to his real chooser
     * @return an ordered list with the number of windows chosen
     * @throws RemoteException if the controller is not usable, maybe because of lost connection
     */
    private ArrayList<Integer> reorderWPandPlayer() throws RemoteException {
        ArrayList<Integer> ordinateWPs = new ArrayList<>();
        for(String player : getListOfPlayers()){
           ordinateWPs.add(hashPlayers.get(player));
        }
        return ordinateWPs;
    }
}
