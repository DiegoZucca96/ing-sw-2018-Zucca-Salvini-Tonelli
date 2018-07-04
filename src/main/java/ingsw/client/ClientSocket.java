package ingsw.client;

import ingsw.model.ViewWP;
import ingsw.model.ViewData;
import ingsw.view.gui.ToolView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Author: Elio Salvini
 *
 * Class that realizes socket connection with server
 */
public class ClientSocket implements Client {

    private String name;            //player's nickname
    private String ip;              //server IP address
    private int port;               //server port
    private Scanner in;             //scanner for socket connection
    private PrintWriter out;        //printWriter for socket connection
    private ObjectInputStream is;   //it is used to receive objects from server
    private ObjectOutputStream os;  //it is used to send objects from server
    private Socket socket;          //server socket

    /**
     * constructor
     * @param ip server ip address
     * @param port server port
     */
    public ClientSocket(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    /**
     * Method that set up the connection with server
     * @return  false in case of connection errors
     */
    private boolean setupConnection(){
        try {
            socket = new Socket(ip, port);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
            os = new ObjectOutputStream(socket.getOutputStream());
            is = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        out.println(name);
        return true;
    }

    //deprecated
    private void handleSetupConnectionError(){
        boolean setupError = true;
        while(setupError){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setupError = false;
            try{
                socket = new Socket(ip, port);
                in = new Scanner(socket.getInputStream());
                out = new PrintWriter(socket.getOutputStream(), true);
                os = new ObjectOutputStream(socket.getOutputStream());
                is = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e){
                setupError = true;
            }
        }
    }

    @Override
    public void startClient(String hostAddress) throws IOException {
        System.out.print("Client ready");
    }

    @Override
    public boolean login(String nickname) {
        setupConnection();
        name = nickname;
        out.println("login:" + nickname);
        boolean response = Boolean.parseBoolean(in.nextLine());
        closeConnection();
        return response;
    }

    @Override
    public boolean register(String nickname) {
        setupConnection();
        out.println("register:" + nickname);
        boolean response = Boolean.parseBoolean(in.nextLine());
        closeConnection();
        return response;
    }

    @Override
    public String getPlayerState() {
        setupConnection();
        out.println("getPlayerState:" + name);
        String response = in.nextLine();
        closeConnection();
        return response;
    }

    @Override
    public void skip() {
        setupConnection();
        out.println("skip:" + name);
        closeConnection();
    }

    @Override
    public boolean useToolCard(int i, ToolView parameter) {
        setupConnection();
        out.println("useToolCard:" + i);
        if (parameter != null){
            out.println("not null");
            try {
                in.nextLine();
                os.writeObject(parameter);
                os.flush();
                os.reset();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else out.println("null");
        boolean response = Boolean.parseBoolean(in.nextLine());
        closeConnection();
        return response;
    }

    @Override
    public boolean takeDie(int row, int column) {
        setupConnection();
        out.println("takeDie:" + row + "," + column);
        boolean response = Boolean.parseBoolean(in.nextLine());
        closeConnection();
        return response;
    }

    @Override
    public boolean positionDie(int row, int column) {
        setupConnection();
        out.println("positionDie:" + row + "," + column);
        boolean response = Boolean.parseBoolean(in.nextLine());
        closeConnection();
        return response;
    }

    @Override
    public boolean waitForPlayers() {
        setupConnection();
        out.println("waitForPlayers:");
        boolean response = Boolean.parseBoolean(in.nextLine());
        closeConnection();
        return response;
    }

    @Override
    public ViewData initializeView() {
        setupConnection();
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
    public ViewData initializeViewCLI() {
        setupConnection();
        out.println("initializeViewCLI:");
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
        setupConnection();
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
        setupConnection();
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
        setupConnection();
        out.println("createHash:"+Integer.toString(nameWindow)+','+nameClient);
        closeConnection();
    }

    @Override
    public String getPVCard(String name) {
        setupConnection();
        out.println("getPVCard:"+name);
        String response = in.nextLine();
        closeConnection();
        return response;
    }

    @Override
    public void setActive(Boolean active) {
        setupConnection();
        out.println("setActive:"+Boolean.toString(active));
        closeConnection();
    }

    @Override
    public boolean getActive() {
        setupConnection();
        out.println("getActive:");
        boolean response = Boolean.parseBoolean(in.nextLine());
        closeConnection();
        return response;
    }

    @Override
    public int getTimeMove() {
        if(!setupConnection()) return -1000;
        out.println("getTimeMove:");
        int response = Integer.parseInt(in.nextLine());
        if (!closeConnection()) return -1000;
        return response;
    }

    @Override
    public String getCurrentPlayer() {
        setupConnection();
        out.println("getCurrentPlayer:");
        String response = in.nextLine();
        closeConnection();
        return response;
    }

    @Override
    public void nullSelection() {
        setupConnection();
        out.println("nullSelection:");
        closeConnection();
    }

    @Override
    public int getRound() {
        setupConnection();
        out.println("getRound:");
        int response = Integer.parseInt(in.nextLine());
        closeConnection();
        return response;
    }

    @Override
    public boolean matchFound() {
        setupConnection();
        out.println("matchFound:");
        boolean response = Boolean.parseBoolean(in.nextLine());
        closeConnection();
        return response;
    }

    @Override
    public ViewWP getWP(String userName) {
        setupConnection();
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
    public void orderWPChoise(){ //--> Choice
        setupConnection();
        out.println("orderWPChoice:");
        closeConnection();
    }

    @Override
    public boolean isFinish() {
        setupConnection();
        out.println("isFinish:");
        boolean response = Boolean.parseBoolean(in.nextLine());
        closeConnection();
        return response;
    }

    @Override
    public int getScore(String name) {
        setupConnection();
        out.println("getScore:" + name);
        int response = Integer.parseInt(in.nextLine());
        closeConnection();
        return response;
    }

    @Override
    public void calculateScore() {
        setupConnection();
        out.println("calculateScore:");
        closeConnection();
    }

    @Override
    public String findWinner() {
        setupConnection();
        out.println("findWinner:");
        String response = in.nextLine();
        closeConnection();
        return response;
    }

    @Override
    public ArrayList<String> getListOfMatchPlayers() {
        setupConnection();
        out.println("getListOfMatchPlayers:");
        ArrayList<String> response = null;
        try {
            response = (ArrayList<String>) is.readObject();
        } catch (IOException e) {
            closeConnection();
            return null;
        } catch (ClassNotFoundException e) {
            closeConnection();
            return null;
        }
        closeConnection();
        return response;
    }

    @Override
    public boolean getInsertedDie() {
        setupConnection();
        out.println("getInsertedDie:");
        boolean response = Boolean.parseBoolean(in.nextLine());
        closeConnection();
        return response;
    }

    @Override
    public void setInsertedDie(boolean b) {
        setupConnection();
        out.println("setInsertedDie" + b);
        closeConnection();
    }

    @Override
    public void setTool8Used(boolean isTool8Used) {
        setupConnection();
        out.println("setTool8Used:" + isTool8Used);
        closeConnection();
    }

    @Override
    public boolean getClockwiseRound() {
        setupConnection();
        out.println("getClockwiseRound:");
        boolean response = Boolean.parseBoolean(in.nextLine());
        closeConnection();
        return response;
    }

    @Override
    public int getTokenRemaining(String name) {
        setupConnection();
        out.println("getTokenRemaining:" + name);
        int response = Integer.parseInt(in.nextLine());
        closeConnection();
        return response;
    }

    @Override
    public boolean iAmAlone() {
        setupConnection();
        out.println("iAmAlone:");
        boolean response = in.nextBoolean();
        closeConnection();
        return response;
    }

    @Override
    public int getStartTimeMove() {
        setupConnection();
        out.println("getStartTimeMove:");
        int response = in.nextInt();
        closeConnection();
        return response;
    }

    @Override
    public void setName(String userName) {
        this.name = userName;
    }

    @Override
    public void handleConnectionError() {
        //Da non usare
    }

    @Override
    public boolean removePlayer(String name) {
        setupConnection();
        out.println("removePlayer:" + name);
        boolean response = in.nextBoolean();
        closeConnection();
        return response;
    }

    @Override
    public ArrayList<String> someoneLeftGame() {
        setupConnection();
        out.println("someoneLeftGame:");
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
    public ArrayList<String> someoneRejoinedGame() {
        setupConnection();
        out.println("someoneRejoinedGame:");
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
    public void stopTimer() {
        setupConnection();
        out.println("stopTimer:");
        closeConnection();
    }

    @Override
    public ArrayList<ViewWP> getPlayerWPs(String name){
        setupConnection();
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
        setupConnection();
        out.println("getNumberOfPlayers:");
        int response = Integer.parseInt(in.nextLine());
        closeConnection();
        return response;
    }

    @Override
    public boolean takeWPDie(int row, int column) {
        setupConnection();
        out.println("takeWPDie:" + row + "," + column);
        boolean response = Boolean.parseBoolean(in.nextLine());
        closeConnection();
        return response;
    }

    @Override
    public ArrayList<ViewWP> getRandomWps() {
        setupConnection();
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
    public void addWP(ViewWP wp) {
        setupConnection();
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
        setupConnection();
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
    public int getCoordinateSelectedY() {
        setupConnection();
        out.println("getCoordinateSelectedY:");
        int response = Integer.parseInt(in.nextLine());
        closeConnection();
        return response;
    }

    /**
     * Method used to close the connection with server
     * @return false in case of connection errors
     */
    public boolean closeConnection() {
        try {
            in.close();
            out.close();
            os.close();
            is.close();
            socket.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
