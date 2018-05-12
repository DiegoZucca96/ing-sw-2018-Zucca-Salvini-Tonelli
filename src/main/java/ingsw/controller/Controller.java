package ingsw.controller;

import ingsw.model.InitializerView;
import ingsw.model.Match;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Controller extends UnicastRemoteObject implements RMIController {

    ArrayList<String> listOfClient ;

    public Controller() throws RemoteException {
        super();
        //listOfClient.add("ale");
        listOfClient=new ArrayList<>();
    }

    //Lista dei vari metodi invocabili da grafica che vanno a interagire con il model

    @Override
    public ArrayList<String> getListOfClient() {
        return listOfClient;
    }

    @Override
    public void addAccount(String account){
        listOfClient.add(account);
    }

    //Chiama il metodo inizializzatore del Match
    @Override
    public InitializerView initializeView() throws RemoteException {
        return Match.initialize();
    }
}
