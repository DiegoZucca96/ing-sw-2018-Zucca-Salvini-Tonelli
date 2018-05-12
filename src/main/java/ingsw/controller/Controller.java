package ingsw.controller;

import ingsw.Server;
import ingsw.model.InitializerView;
import ingsw.model.Match;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Controller extends UnicastRemoteObject implements RMIController {

    ArrayList<String> listOfClient ;
    Server server;

    public Controller(Server server) throws RemoteException {
        super();
        listOfClient=new ArrayList<>();
        this.server= server;
    }

    //Lista dei vari metodi invocabili da grafica che vanno a interagire con il model

    @Override
    public ArrayList<String> getListOfClient() {
        return listOfClient;
    }

    @Override
    public void addAccount(String account){
        listOfClient.add(account);
        server.addClient(account);
    }
    @Override
    public boolean access(String account){
        //System.out.println(account);
        //se esiste già il nome salvato nel server non puoi accedere
        if(server.getListOfClient().contains(account)){
            return false;
        }
        return true;
    }


    //Chiama il metodo inizializzatore del Match
    @Override
    public InitializerView initializeView() throws RemoteException {
        return Match.initialize();
    }
}
