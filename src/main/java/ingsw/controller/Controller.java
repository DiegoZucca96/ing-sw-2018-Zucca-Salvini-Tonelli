package ingsw.controller;

import ingsw.Server;
import ingsw.model.InitializerView;
import ingsw.model.Match;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Controller extends UnicastRemoteObject implements RMIController {

    Server server;

    public Controller(Server server) throws RemoteException {
        super();
        this.server= server;
    }

    //Lista dei vari metodi invocabili da grafica che vanno a interagire con il model

    @Override
    public ArrayList<String> getListOfPlayers() {
        return server.getListOfPlayers();    }

    @Override
    public void addClient(String account){
        server.addClient(account);
    }
    @Override
    public void addAccount(String account){
        server.addAccount(account);
    }

    @Override
    public boolean access(String account){
        //se esiste già il nome salvato nel server non puoi accedere
        if(server.getListOfClient().contains(account)){
            return true;
        }
        return false;
    }


    //Chiama il metodo inizializzatore del Match
    @Override
    public InitializerView initializeView() throws RemoteException {
        return Match.initialize();
    }
}
