package ingsw.controller;

import ingsw.*;
import ingsw.model.*;
import ingsw.model.windowpattern.WindowPattern;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class Controller extends UnicastRemoteObject implements RMIController {

    private Server server;
    private WindowPFactory wpFactory;
    private RandomGenerator rg;
    private Match match;
    private static int access=0;

    //NB -->    tutti i metodi del controller devono essere boolean, per un motivo o per l'altro
    //          non sempre possono fare l'azione richiesta, in quel caso restituiscono false

    public Controller(Server server) throws RemoteException {
        super();
        this.server= server;
        wpFactory = new WindowPFactory();
        rg = new RandomGenerator(wpFactory.getNumOfWPs());
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
    public boolean takeDie(int index) throws RemoteException{
        if(match.getCurrentPlayer().getDieSelected()==null){
            match.playerTakeDie(index);
            return true;
        }
        else
            return false;
    }

    @Override
    public boolean positionDie(int row, int column) throws RemoteException{
        Coordinate coordinate = new Coordinate(row,column);
        boolean result = match.positionDie(match.getCurrentPlayer().getDieSelected(),coordinate);
        skip(getCurrentPlayerName());
        return result;
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

    public synchronized ArrayList<WindowPattern> getRandomWPs(){
        ArrayList<WindowPattern> wps = new ArrayList<>();
        wps.add(wpFactory.createWindowPattern(rg.random()));
        wps.add(wpFactory.createWindowPattern(rg.random()));
        wps.add(wpFactory.createWindowPattern(rg.random()));
        wps.add(wpFactory.createWindowPattern(rg.random()));
        return wps;
    }

    @Override
    public String getCurrentPlayerName(){
        return match.getCurrentPlayer().getName();
    }

    //Salta volontariamente il turno oppure forzatamente dalla fine del timer de giocatore
    @Override
    public void skip(String clientName) throws RemoteException{
        if(getPlayerState(clientName).equals("enabled")){
            disableClient(getCurrentPlayerName());
            match.nextTurn();
            enableClient(getCurrentPlayerName());
            server.startPlayerTimer();
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
    public boolean waitForPlayers() throws RemoteException{
        if(getSizeOfPlayers()==1 && access == 0){ //Fa qualcosa mentre aspetta
            access++;
        }
        if(getSizeOfPlayers()==2 && access == 1){
            server.search();
            access++;
        }
        if(getTimeSearch()==0)
            return true;
        else
            return false;
    }

    @Override
    public boolean getOthersChoice() throws RemoteException{
        if(server.getWindowChosen().size()< getSizeOfPlayers())
            return false;
        else
            return true;
    }
    //Chiama il metodo inizializzatore del Match
    @Override
    public InitializerView initializeView() throws RemoteException {
        return Match.initialize();
    }

}
