package ingsw.controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Controller extends UnicastRemoteObject implements RMIController {

    public Controller() throws RemoteException {
        super();
    }

    //Lista dei vari metodi invocabili da grafica che vanno a interagire con il model
}
