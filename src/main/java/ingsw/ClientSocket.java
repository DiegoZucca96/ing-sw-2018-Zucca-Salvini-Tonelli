package ingsw;

import ingsw.model.ViewWP;
import ingsw.model.ViewData;
import ingsw.view.ToolView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
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
    public boolean useToolCard(int i, ToolView parameter) {
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
        setup();
        out.println("matchFound:");
        boolean response = Boolean.parseBoolean(in.nextLine());
        closeConnection();
        return response;
    }

    @Override
    public boolean iAmBanned(String userName) {
        setup();
        out.println("iAmLegend:"+ userName);
        boolean response = Boolean.parseBoolean(in.nextLine());
        closeConnection();
        return response;
    }

    @Override
    public ViewWP getWP(String userName) {
        setup();
        out.println("getWP:" + userName);
        try {
            ViewWP response = (ViewWP) is.readObject();
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
    public void orderWPChoise(){
        setup();
        out.println("orderWPChoise:");
        closeConnection();
    }

    @Override
    public boolean isFinish() {
        //da impl

        return false;
    }

    @Override
    public Integer getScore(String name) {
        //da impl

        return null;
    }

    @Override
    public void calculateScore() {
        //da impl

    }

    @Override
    public String findWinner() {
        //da impl

        return null;
    }

    @Override
    public ArrayList<String> getListOfMatchPlayers() {
        //da impl

        return null;
    }

    @Override
    public void disconnectClient() {
        //da impl

    }

    @Override
    public boolean getInsertedDie() {
        //da impl

        return false;
    }

    @Override
    public void setInsertedDie(boolean b) {
        //da impl

    }

    @Override
    public boolean getTool8Used() {
        //da impl

        return false;
    }

    @Override
    public void setTool8Used(boolean isTool8Used) {
        //da impl
    }

    @Override
    public boolean getClockwiseRound() {
        //da impl

        return false;
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
