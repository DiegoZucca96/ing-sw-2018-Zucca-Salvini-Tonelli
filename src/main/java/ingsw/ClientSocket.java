package ingsw;

import ingsw.model.ViewWP;
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
    }

    public static void main(String[] args) throws Exception{
        ClientSocket clientSocket = new ClientSocket("127.0.0.1", 1080);
        clientSocket.startClient();
    }


    @Override
    public void startClient() throws IOException {
        //...
    }

    private void setup(){
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

    @Override
    public boolean login(String nickname) {
        setup();
        name = nickname;
        out.println("login:" + nickname);
        boolean response = Boolean.parseBoolean(in.nextLine());
        closeConnection();
        return response;
    }

    @Override
    public boolean register(String nickname) {
        setup();
        out.println("register:" + nickname);
        boolean response = Boolean.parseBoolean(in.nextLine());
        closeConnection();
        return response;
    }

    @Override
    public String getPlayerState() {
        setup();
        out.println("getPlayerState:" + name);
        String response = in.nextLine();
        closeConnection();
        return response;
    }

    @Override
    public void skip() {
        setup();
        out.println("skip:" + name);
        closeConnection();
    }

    @Override
    public boolean useToolCard(String parameter) {
        setup();
        closeConnection();
        return false;
    }

    @Override
    public boolean takeDie(int row, int column) {
        setup();
        out.println("takeDie:" + row + "," + column);
        boolean response = Boolean.parseBoolean(in.nextLine());
        closeConnection();
        return response;
    }

    @Override
    public boolean positionDie(int row, int column) {
        setup();
        out.println("positionDie:" + row + "," + column);
        boolean response = Boolean.parseBoolean(in.nextLine());
        closeConnection();
        return response;
    }

    @Override
    public boolean waitForPlayers() {
        setup();
        out.println("waitForPlayers:");
        boolean response = Boolean.parseBoolean(in.nextLine());
        closeConnection();
        return response;
    }

    @Override
    public ViewData initializeView() {
        setup();
        out.println("initializeView:");
        try {
            ViewData response =  (ViewData) is.readObject();
            closeConnection();
            return response;
        } catch (ClassNotFoundException e) {
            closeConnection();
            return null;
        } catch (IOException e) {
            closeConnection();
            return null;
        }
    }

    @Override
    public ViewData updateView() {
        setup();
        out.println("updateView:");
        try {
            ViewData response = (ViewData) is.readObject();
            closeConnection();
            return response;
        } catch (ClassNotFoundException e) {
            closeConnection();
            return null;
        } catch (IOException e){
            closeConnection();
            return null;
        }
    }

    @Override
    public boolean readyToPlay() {
        setup();
        out.println("readyToPlay:");
        boolean response = Boolean.parseBoolean(in.nextLine());
        closeConnection();
        return response;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void createHash(int nameWindow, String nameClient) {
        setup();
        out.println("createHash:"+Integer.toString(nameWindow)+','+nameClient);
        closeConnection();
    }

    @Override
    public HashMap<String,Integer> getHashPlayers() {
        setup();
        out.println("getHashPlayers:");
        try {
            HashMap response = (HashMap<String, Integer>) is.readObject();
            closeConnection();
            return response;
        } catch (IOException e) {
            closeConnection();
            return null;
        } catch (ClassNotFoundException e) {
            closeConnection();
            return  null;
        }
    }

    @Override
    public String getPVCard(String name) {
        setup();
        out.println("getPVCard:"+name);
        String response = in.nextLine();
        closeConnection();
        return response;
    }

    @Override
    public void setActive(Boolean active) {
        setup();
        out.println("setActive:"+Boolean.toString(active));
        closeConnection();
    }

    @Override
    public boolean getActive() {
        setup();
        out.println("getActive:");
        boolean response = Boolean.parseBoolean(in.nextLine());
        closeConnection();
        return response;
    }

    @Override
    public void rejoinedPlayer(String name) {
        setup();
        out.println("rejoinedPlayer:"+name);
        closeConnection();
    }

    @Override
    public int getTimeMove() {
        setup();
        out.println("getTimeMove:");
        int response = Integer.parseInt(in.nextLine());
        closeConnection();
        return response;
    }

    @Override
    public String getCurrentPlayer() {
        setup();
        out.println("getCurrentPlayer:");
        String response = in.nextLine();
        closeConnection();
        return response;
    }

    @Override
    public void nullSelection() {
        setup();
        out.println("nullSelection:");
        closeConnection();
    }

    @Override
    public int getRound() {
        setup();
        out.println("getRound");
        int response = Integer.parseInt(in.nextLine());
        closeConnection();
        return response;
    }

    @Override
    public boolean matchFound() {
        //da impl

        return false;
    }

    @Override
    public boolean iAmBanned(String userName) {
        //da impl

        return false;
    }

    @Override
    public ViewWP getWP(String userName) {
        //da impl

        return null;
    }

    @Override
    public void orderWPChoise() {
        //da impl

    }


    @Override
    public ArrayList<ViewWP> getPlayerWPs(String name){
        setup();
        out.println("getPlayersWPs:"+name);
        try {
            ArrayList<ViewWP> response = (ArrayList<ViewWP>) is.readObject();
            closeConnection();
            return response;
        } catch (ClassNotFoundException e) {
            closeConnection();
            return null;
        } catch (IOException e){
            closeConnection();
            return null;
        }
    }

    @Override
    public int getNumberOfPlayers() {
        setup();
        out.println("getNumberOfPlayers:");
        int response = Integer.parseInt(in.nextLine());
        closeConnection();
        return response;
    }

    @Override
    public int getTimeSearch() {
        setup();
        out.println("getTimeSearch:");
        int response = Integer.parseInt(in.nextLine());
        closeConnection();
        return response;
    }

    @Override
    public boolean takeWPDie(int row, int column) {
        setup();
        out.println("takeWPDie:" + row + "," + column);
        boolean response = Boolean.parseBoolean(in.nextLine());
        closeConnection();
        return response;
    }

    @Override
    public ArrayList<ViewWP> getRandomWps() {
        setup();
        out.println("getRandomWPs:");
        try {
            ArrayList<ViewWP> response = (ArrayList<ViewWP>) is.readObject();
            closeConnection();
            return response;
        } catch (ClassNotFoundException e) {
            closeConnection();
            return null;
        } catch (IOException e){
            closeConnection();
            return null;
        }
    }

    @Override
    public void addWPName(String wp) {
        setup();
        out.println("addWPName:" + wp);
        closeConnection();
    }

    @Override
    public void addWP(ViewWP wp) {
        setup();
        out.println("addWP:");
        try {
            in.nextLine();
            os.writeObject(wp);
            os.flush();
            os.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    @Override
    public ArrayList<String> getListOfPlayers() {
        setup();
        out.println("getListOfPlayers:");
        try {
            ArrayList<String> response = (ArrayList<String>) is.readObject();
            closeConnection();
            return response;
        } catch (IOException e) {
            closeConnection();
            return null;
        } catch (ClassNotFoundException e) {
            closeConnection();
            return null;
        }
    }

    @Override
    public int getCoordinateSelectedX() {
        setup();
        out.println("getCoordinateSelectedX:");
        int response = Integer.parseInt(in.nextLine());
        closeConnection();
        return response;
    }

    @Override
    public int getCoordinateSelectedY() {
        setup();
        out.println("getCoordinateSelectedY:");
        int response = Integer.parseInt(in.nextLine());
        closeConnection();
        return response;
    }

    public void closeConnection() {
        try {
            in.close();
            out.close();
            os.close();
            is.close();
            socket.close();
        } catch (IOException e) {
            //gestione errore di connessione..
        }
    }

}
