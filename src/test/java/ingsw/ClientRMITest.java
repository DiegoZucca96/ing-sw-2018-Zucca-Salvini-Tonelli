package ingsw;

import ingsw.controller.RMIController;
import org.junit.Before;
import org.junit.Test;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ClientRMITest {

        private ClientRMI clientRMI;
        private RMIController controller;

        @Before
        public void setUpTests() throws RemoteException {

            //System.setProperty("java.security.policy", "stupid.policy");
            //System.setSecurityManager(new SecurityManager());

            clientRMI = new ClientRMI();
            Registry registry = LocateRegistry.getRegistry("localhost",1081);
            try {
                controller = (RMIController) registry.lookup("controller");
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
            clientRMI.setController(controller);
            clientRMI.setName("Norma");
        }

        @Test
        public void registerTest() {
            assertTrue(clientRMI.register("Norma"));
        }

        @Test
        public void loginTest() {
            assertTrue(clientRMI.login("Norma"));
        }

        @Test
        public void getPlayerStateTest(){
            assertEquals("disabled",clientRMI.getPlayerState());
        }

        @Test
        public void getListOfPlayersTest(){
            ArrayList<String> players = new ArrayList<String>();
            players.add("Norma");

            assertEquals(players,clientRMI.getListOfPlayers());
        }




    }
