package ingsw.ConnectionTest;

import ingsw.ClientSocket;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

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
            assertTrue(clientSocket.register("Zaga"));
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
            clientSocket.setName("Norma");
            assertEquals("disabled",clientSocket.getPlayerState());
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

    @Test
    public void stringTest(){
        String s = "Norma";
        System.out.println("a è in posizione:"+s.indexOf('a'));
        System.out.println("N è in posizione:"+s.indexOf('N'));
        System.out.println("r è in posizione:"+s.indexOf('r'));
        System.out.println("lunghezza:"+s.length());
    }

    @Test
    public void ConnectionException() throws InterruptedException {
        Thread.sleep(10000);
        clientSocket.register("Norma");
    }

}
