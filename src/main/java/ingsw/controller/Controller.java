package ingsw.controller;

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


    public ArrayList<String> getListOfClient() {
        return listOfClient;
    }

    public void addAccount(String account){
        listOfClient.add(account);
    }


}
