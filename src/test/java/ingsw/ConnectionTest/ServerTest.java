package ingsw.ConnectionTest;

import ingsw.server.Server;
import ingsw.server.ServerHandler;
import ingsw.controller.Controller;
import ingsw.controller.RMIController;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServerTest {

    //NB --> in.nextLnt() in Server() non funziona con junit, commentare tali istruzioni prima dei test

    private RMIController controller;

    @Before
    public void setUpTests() throws RemoteException {

        System.setProperty("java.security.policy", "src/policy/sagrada.policy");
        System.setSecurityManager(new SecurityManager());

        controller = mock(Controller.class);
        Server server = Server.instance(1080);
        server.setController(controller);
        Registry registry = LocateRegistry.createRegistry(1081);
        registry.rebind("controller", server.getController());

        //simulo i metodi del controller
        when(controller.register( any(String.class), any(ServerHandler.class))).thenReturn(true);
        when(controller.getPlayerState(any(String.class))).thenReturn("enabled");
        ArrayList<String> players = new ArrayList<String>();
        players.add("Elio");
        players.add("Alessio");
        players.add("Diego");
        players.add("Norma");
        when(controller.getListOfPlayers()).thenReturn(players);

        server.startServerSocket();
    }

    //Questo metodo permette di lanciare il server per fare i test
    @Test
    public void runServerTest() throws RemoteException {
        System.out.println("End test");
    }

}
