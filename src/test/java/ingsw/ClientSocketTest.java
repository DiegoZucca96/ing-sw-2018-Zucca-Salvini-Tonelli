package ingsw;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ClientSocketTest {

    private ClientSocket clientSocket;

    @Before
    public void setUpTests() throws Exception {
        clientSocket = new ClientSocket("127.0.0.1", 1080);
    }

    @Test
    public void registerTest() throws IOException {
        try{
            assertTrue(clientSocket.register("Norma"));            //...
        }catch(NoSuchElementException e){
            System.err.println(e.getMessage());
        }
        finally{
            clientSocket.closeConnection();
            System.out.println("Connection closed");
        }
    }

    @Test
    public void getPlayerStateTest() throws IOException {
        try{
            assertEquals("enabled",clientSocket.getPlayerState());
        } catch(NoSuchElementException e){
            System.err.println(e.getMessage());
        }
        finally {
            clientSocket.closeConnection();
            System.out.println("Connection closed");
        }
    }

    @Test
    public void getListOfPlayersTest() throws IOException {
        ArrayList<String> players = new ArrayList<String>();
        players.add("Elio");
        players.add("Alessio");
        players.add("Diego");
        players.add("Norma");
        try{
            assertEquals(players,clientSocket.getListOfPlayers());
        } catch(NoSuchElementException e){
            System.err.println(e.getMessage());
        }
        finally {
            clientSocket.closeConnection();
            System.out.println("Connection closed");
        }
    }


}
