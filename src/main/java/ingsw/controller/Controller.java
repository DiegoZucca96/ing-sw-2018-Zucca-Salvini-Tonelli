package ingsw.controller;

import ingsw.*;
import ingsw.model.*;
import ingsw.model.windowpattern.WindowPattern;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class Controller extends UnicastRemoteObject implements RMIController {

    private Timer timer;
    private ControllerTimer controllerTimer;
    private Server server;
    private WindowPFactory wpFactory;
    private RandomGenerator rg;
    private Match match;
    private int timeSearch;
    private int playerMoveTime;
    private static int access=0;

    //NB -->    tutti i metodi del controller devono essere boolean, per un motivo o per l'altro
    //          non sempre possono fare l'azione richiesta, in quel caso restituiscono false

    public Controller(Server server) throws RemoteException {
        super();
        this.server= server;
        try {
            this.wpFactory = new WindowPFactory();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.rg = new RandomGenerator(wpFactory.getNumOfWPs());
        this.timeSearch = server.getTimeSearch();
        this.playerMoveTime = server.getPlayerTimeMove();
        this.timer = new Timer();
        this.controllerTimer = new ControllerTimer(timeSearch,playerMoveTime);
    }

    //Lista dei vari metodi invocabili da grafica che vanno a interagire con il model

    @Override
    public ArrayList<String> getListOfPlayers() throws RemoteException{
        return server.getListOfPlayers();    }

    @Override
    public int getSizeOfPlayers() throws RemoteException {
        return getListOfPlayers().size();
    }

    @Override
    public int getTimeSearch() throws RemoteException {
        return server.getTimeSearch();
    }

    @Override
    public int getPlayerTimeMove() throws RemoteException {
        return server.getPlayerTimeMove();
    }

    @Override
    public ArrayList<WindowPattern> getWindowChosen() throws RemoteException {
        return server.getWindowChosen();
    }

    @Override
    public void addWindow(List<Cell> myWindow) throws RemoteException {
        server.addWindow(myWindow);
    }

    @Override
    public boolean takeDie(int row, int column) throws RemoteException{
        if(match.getCurrentPlayer().getDieSelected()==null){
            int index = 3*row + column;
            match.getCurrentPlayer().setDieSelected(match.playerTakeDie(index));
            return true;
        }
        return false;
    }

    @Override
    public boolean takeWPDie(int row, int column) throws RemoteException {
        if(match.getCurrentPlayer().getCoordinateDieSelected()==null){
            Coordinate c = new Coordinate(row,column);
            Cell [][] matrix = match.getCurrentPlayer().getWindowPattern().getCellMatrix();
            match.getCurrentPlayer().setCoordinateDieSelected(c);
            match.getCurrentPlayer().setDieSelected(match.getCurrentPlayer().getWindowPattern().getCell(c,matrix).getDie());
            return match.getCurrentPlayer().getWindowPattern().removeDie(c,matrix);
        }
        return false;
    }

    @Override
    public boolean positionDie(int row, int column) throws RemoteException{
        if(match.getCurrentPlayer().getDieSelected()!=null){
            Coordinate coordinate = new Coordinate(row,column);
            if(match.positionDie(match.getCurrentPlayer().getDieSelected(),coordinate)){
                match.getCurrentPlayer().setCoordinateDieSelected(null);
                match.getCurrentPlayer().setDieSelected(null);
                return true;
            }
        }
        return false;
    }

    //aggiunge il nuovo account alla lista degli account del server, se l'account è già presente restituisce false
    /*@Override
    public synchronized boolean addAccount(String account){
        if (access(account)) return false;
        server.addAccount(account);
        return true;
    }*/

    @Override
    public synchronized boolean register(String account) throws RemoteException{
        if (server.getListOfClient().contains(account))
            return false;
        else{
            server.addAccount(account);
            return true;
        }
    }

    @Override
    public synchronized boolean register(String account, ServerHandler serverHandler) throws RemoteException{
        if (server.getListOfClient().contains(account))
            return false;
        else{
            server.addAccount(account,serverHandler);
            return true;
        }
    }

    //Inserisce l'account tra i giocatori disabilitandolo, verrà attivato solo il primo entrato
    @Override
    public synchronized boolean login(String account) throws RemoteException {
        if(access(account)){
            disableClient(account);
            return true;
        }
        else
            return false;
    }

    private boolean access(String account){
        //se esiste già il nome salvato nel server non puoi accedere
        if(server.getListOfClient().contains(account) && !server.getListOfPlayers().contains(account)){
            server.addPlayers(account);
            return true;
        }
        return false;
    }

    public synchronized ArrayList<WPViewChoise> getRandomWPs(){
        ArrayList<WPViewChoise> wps = new ArrayList<>();
        WPViewChoise wpobject = new WPViewChoise();
        WindowPattern wp1 = wpFactory.createWindowPattern(rg.random());
        wpobject.setName(wp1.getTitle());
        wpobject.setDifficulty(Integer.toString(wp1.getDifficulty()));
        wpobject.setWps(wp1.toMatrix());
        wps.add(wpobject);

        WPViewChoise wpobject2 = new WPViewChoise();
        WindowPattern wp2 = wpFactory.createWindowPattern(rg.random());
        wpobject2.setName(wp2.getTitle());
        wpobject2.setDifficulty(Integer.toString(wp2.getDifficulty()));
        wpobject2.setWps(wp2.toMatrix());
        wps.add(wpobject2);

        WPViewChoise wpobject3 = new WPViewChoise();
        WindowPattern wp3 = wpFactory.createWindowPattern(rg.random());
        wpobject3.setName(wp3.getTitle());
        wpobject3.setDifficulty(Integer.toString(wp3.getDifficulty()));
        wpobject3.setWps(wp3.toMatrix());
        wps.add(wpobject3);

        WPViewChoise wpobject4 = new WPViewChoise();
        WindowPattern wp4 = wpFactory.createWindowPattern(rg.random());
        wpobject4.setName(wp4.getTitle());
        wpobject4.setDifficulty(Integer.toString(wp4.getDifficulty()));
        wpobject4.setWps(wp4.toMatrix());
        wps.add(wpobject4);
        return wps;
    }

    @Override
    public boolean getOthersWP() throws RemoteException {
        if(server.getWindowChosen().size()<getSizeOfPlayers())
            return false;
        else return true;
    }

    @Override
    public String getCurrentPlayerName(){
        return match.getCurrentPlayer().getName();
    }

    //Salta volontariamente il turno oppure forzatamente dalla fine del timer de giocatore
    @Override
    public void skip(String clientName) throws RemoteException{
        if(getPlayerState(clientName).equals("enabled")){
            timer.cancel();
            disableClient(getCurrentPlayerName());
            match.nextTurn();
            controllerTimer.setTimeMoveRemaining(playerMoveTime);
            enableClient(getCurrentPlayerName());
            timer = new Timer();
            controllerTimer.startPlayerTimer(this,timer);
        }
    }

    @Override
    public String getPlayerState(String clientName) throws RemoteException{
        return server.getClientState(clientName).toString();
    }

    //disabilita il client (il server ignora le sue richieste)
    public ClientState enableClient(String clientName){
        return new EnableClient().setState(clientName);
    }

    //attiva il client (il server ascolta le sue richieste
    public ClientState disableClient(String clientName){
        return new DisableClient().setState(clientName);
    }

    //Utilizza la ToolCard, ancora da implementare (forse serve anche quale tool è stata scelta come parametro)
    @Override
    public boolean useToolCard(String parameter){
        return false;
    }

    @Override
    public synchronized boolean waitForPlayers() throws RemoteException{
        if(getSizeOfPlayers()==1 && access == 0){ //Fa qualcosa mentre aspetta
            access++;
        }
        if(getSizeOfPlayers()==2 && access == 1){
            controllerTimer.search(this,timer);
            access++;
        }
        if(getTimeRemaining()<=0){
            timer.cancel();
            return true;
        }
        else
            return false;
    }

    @Override
    public int getTimeRemaining() {
        return controllerTimer.getTimeRemaining();
    }

    @Override
    public ArrayList<WPViewChoise> getOthersChoice() throws RemoteException{
        ArrayList<WPViewChoise> enemyWPs = new ArrayList<>();
        for(WindowPattern windowPattern: server.getWindowChosen()){
            WPViewChoise enemyWp = new WPViewChoise();
            InfoCell [][] infoCell = windowPattern.toMatrix();
            enemyWp.setWps(infoCell);
            String name = windowPattern.getTitle();
            enemyWp.setName(name);
            String difficulty = Integer.toString(windowPattern.getDifficulty());
            enemyWp.setDifficulty(difficulty);
            enemyWPs.add(enemyWp);
        }
        return enemyWPs;

    }
    //Chiama il metodo inizializzatore del Match
    @Override
    public InitializerView initializeView() throws RemoteException {
        return Match.initialize();
    }

}
