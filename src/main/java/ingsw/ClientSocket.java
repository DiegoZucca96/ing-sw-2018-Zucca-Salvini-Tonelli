package ingsw;

import ingsw.controller.ViewWP;
import ingsw.model.ViewData;
import ingsw.view.GUI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientSocket implements Client {

    private String name;
    private String ip;
    private int port;
    private Scanner in;
    private PrintWriter out;
    private ObjectInputStream is;
    private ObjectOutputStream os;

    private Socket socket;

    public ClientSocket(String ip, int port) {
        this.ip = ip;
        this.port = port;
        try {
            socket = new Socket(ip, port);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
            os = new ObjectOutputStream(socket.getOutputStream());
            is = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception{
        ClientSocket clientSocket = new ClientSocket("127.0.0.1", 1080);
        clientSocket.startClient();
    }


        @Override
    public void startClient() throws IOException {
        try{
            setup();
            //...
            new GUI().display(this);
            //...
        }catch(NoSuchElementException e){
            System.err.println(e.getMessage());
        }
        finally{
            closeConnection();
            System.out.println("Connection closed");
        }
    }

    private void setup() throws IOException {
        System.out.println("Client ready");
    }

    @Override
    public boolean login(String nickname) {
        out.println("login:" + nickname);
        if(in.nextLine().equals("ok")) return true;
        else return  false;
    }

    @Override
    public boolean register(String nickname) {
        name = nickname;
        out.println("register:" + nickname);
        if(in.nextLine().equals("ok")) return true;
        else return  false;
    }

    @Override
    public String getPlayerState() {
        out.println("getPlayerState:" + name);
        return in.nextLine();
    }

    @Override
    public void skip() {
        out.println("skip:" + name);
    }

    @Override
    public boolean useToolCard(String parameter) {
        return false;
    }

    @Override
    public boolean takeDie(int row, int column) {
        out.println("takeDie:" + row + "," + column);
        if(in.nextLine().equals("ok")) return true;
        else return false;
    }

    @Override
    public boolean positionDie(int row, int column) {
        out.println("positionDie:" + row + "," + column);
        if(in.nextLine().equals("ok")) return true;
        else return false;
    }

    @Override
    public boolean waitForPlayers() {
        out.println("waitForPlayers:");
        if(in.nextLine().equals("ok")) return  true;
        else return false;
    }

    @Override
    public ViewData initializeView() {
        return null;
    }

    @Override
    public ViewData updateView() {
        return null;
    }

    @Override
    public boolean readyToPlay() {
        out.println("readyToPlay:");
        if(in.nextLine().equals("ok")) return  true;
        else return false;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void createHash(int nameWindow, String nameClient) {
        out.println("createHash:");
    }

    @Override
    public HashMap<String, Integer> getHashPlayers() {

        //Da implementare
        return null;
    }

    @Override
    public String getPVCard(String name) {
        //da implementare
        return null;
    }

    @Override
    public void setActive() {
        //da implementare
    }


    @Override
    public ArrayList<ViewWP> getPlayerWPs(String name){
        out.println("getPlayersWPs:");
        try {
            return (ArrayList<ViewWP>) is.readObject();
        } catch (ClassNotFoundException e) {
            return null;
        } catch (IOException e){
            return null;
        }
    }

    @Override
    public int getNumberOfPlayers() {
        out.println("getNumberOfPlayers:");
        return Integer.parseInt(in.nextLine());
    }

    @Override
    public int getTimeSearch() {
        out.println("getTimeSearch:");
        return Integer.parseInt(in.nextLine());
    }

    @Override
    public boolean takeWPDie(int row, int column) {
        out.println("takeWPDie:" + row + "," + column);
        if(in.nextLine().equals(("ok"))) return  true;
        else return false;
    }

    @Override
    public ArrayList<ViewWP> getRandomWps() {
        out.println("getRandomWPs:");
        try {
            return (ArrayList<ViewWP>) is.readObject();
        } catch (ClassNotFoundException e) {
            return null;
        } catch (IOException e){
            return null;
        }
    }

    @Override
    public void addWPName(String wp) {
        out.println("addWP:" + wp);
    }

    @Override
    public void addWP(ViewWP wp) {
        //Da implementare se serve
    }

    @Override
    public ArrayList<String> getListOfPlayers() {
        out.println("getListOfPlayers:");
        try {
            return (ArrayList<String>) is.readObject();
        } catch (IOException e) {
            return null;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public void closeConnection() throws IOException {
        in.close();
        out.close();
        socket.close();
    }

}
