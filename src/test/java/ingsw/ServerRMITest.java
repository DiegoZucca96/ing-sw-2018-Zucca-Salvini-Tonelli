package ingsw;

import ingsw.controller.RMIController;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;



public class ServerRMITest {

    //NB  -->   in.nextLnt() in Server() non funziona con junit, commentare tali istruzioni prima dei test;
    //          mockito non funziona con RMI, quindi questa classe testa anche il controller

    private RMIController controller;

    @Before
    public void setUpTests() throws RemoteException {

        System.setProperty("java.security.policy", "src/policy/sagrada.policy");
        System.setSecurityManager(new SecurityManager());

        Server server = Server.instance(1080);
        Registry registry = LocateRegistry.createRegistry(1081);
        registry.rebind("controller", server.getController());


        server.startServerSocket();
    }

    //Questo metodo permette di lanciare il server per fare i test
    @Test
    public void runServerTest() throws RemoteException {
        System.out.println("End test");
    }

}
