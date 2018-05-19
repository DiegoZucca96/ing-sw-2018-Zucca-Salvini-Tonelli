package ingsw.controller;

import ingsw.Server;
import ingsw.model.InitializerView;
import ingsw.model.Match;
import ingsw.model.RandomGenerator;
import ingsw.model.WindowPFactory;
import ingsw.model.windowpattern.WindowPattern;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Controller extends UnicastRemoteObject implements RMIController {

    private Server server;
    private WindowPFactory wpFactory;
    private RandomGenerator rg;


    public Controller(Server server) throws RemoteException {
        super();
        this.server= server;
        wpFactory = new WindowPFactory();
        rg = new RandomGenerator(wpFactory.getNumOfWPs());
    }

    //Lista dei vari metodi invocabili da grafica che vanno a interagire con il model

    @Override
    public ArrayList<String> getListOfPlayers() {
        return server.getListOfPlayers();    }

    @Override
    public void addPlayers(String account){
        server.addPlayers(account);
    }

    @Override
    public void addAccount(String account){
        server.addAccount(account);
    }

    @Override
    public int getTimeSearch() throws RemoteException {
        return server.getTimeSearch();
    }

    @Override
    public int getPlayerTimeMove() throws RemoteException {
        return server.getPlayerTimeMove();
    }

    //aggiunge il nuovo account alla lista degli account del server, se l'account è già presente restituisce false
    /*@Override
    public synchronized boolean addAccount(String account){
        if (access(account)) return false;
        server.addAccount(account);
        return true;
    }*/
    @Override
    public boolean access(String account){
        //se esiste già il nome salvato nel server non puoi accedere
        if(server.getListOfClient().contains(account)){
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

    //Chiama il metodo inizializzatore del Match
    @Override
    public InitializerView initializeView() throws RemoteException {
        return Match.initialize();
    }
}
