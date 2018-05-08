package ingsw;

import ingsw.controller.RMIController;
import ingsw.view.GuiCaller;
import ingsw.view.StartGame;
import javafx.stage.Stage;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Client {
    public static void main(String[] args) throws Exception {
        String[] a={};

        //In teoria qua si deve passare al metodo grafico il controller per far eseguire le varie operazioni
        new StartGame().main(a);

    }



}
