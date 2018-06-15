package ingsw;

import ingsw.model.ViewWP;
import ingsw.model.ViewData;
import ingsw.view.GUI;
import ingsw.view.ToolView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
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

    private void setupConnection(){
        try {
            socket = new Socket(ip, port);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
            os = new ObjectOutputStream(socket.getOutputStream());
            is = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            //notify lost connection to player
            new GUI().display(new ClientSocket("127.0.0.1", 1080));
        }
        out.println(name);
    }

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
    public boolean skip() {
        setupConnection();
        out.println("skip:" + name);
        closeConnection();
        return false;
    }

    @Override
    public boolean useToolCard(int i, ToolView parameter) {
        setupConnection();
        out.println("uesToolCard:" + i);
        in.nextLine();
        try {
            os.writeObject(parameter);
            os.flush();
            os.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public boolean createHash(int nameWindow, String nameClient) {
        setupConnection();
        out.println("createHash:"+Integer.toString(nameWindow)+','+nameClient);
        closeConnection();
        //TODO
        return false;
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
    public boolean setActive(Boolean active) {
        setupConnection();
        out.println("setActive:"+Boolean.toString(active));
        closeConnection();
        //TODO
        return false;
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
        setupConnection();
        out.println("getTimeMove:");
        int response = Integer.parseInt(in.nextLine());
        closeConnection();
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
    public boolean nullSelection() {
        setupConnection();
        out.println("nullSelection:");
        closeConnection();
        //TODO
        return false;
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
    public boolean orderWPChoise(){
        setupConnection();
        out.println("orderWPChoise:");
        closeConnection();
        //TODO
        return false;
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
    public boolean calculateScore() {
        setupConnection();
        out.println("calculateScore:");
        closeConnection();
        //TODO
        return false;
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
    public boolean setInsertedDie(boolean b) {
        setupConnection();
        out.println("setInsertedDie" + b);
        closeConnection();
        //TODO
        return false;
    }

    @Override
    public boolean setTool8Used(boolean isTool8Used) {
        setupConnection();
        out.println("setTool8Used:" + isTool8Used);
        closeConnection();
        //TODO
        return false;
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
        //TODO
        return false;
    }

    @Override
    public int getStartTimeMove() {
        //TODO
        return -1;
    }

    @Override
    public boolean setName(String userName) {
        //TODO
        return false;
    }

    @Override
    public void handleConnectionError() {
        //Da non usare
    }

    @Override
    public boolean removePlayer(String name) {
        //TODO
        return false;
    }

    @Override
    public ArrayList<String> someoneLeftGame() {
        //TODO
        return null;
    }

    @Override
    public ArrayList<String> someoneRejoinedGame() {
        //TODO
        return null;
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
    public boolean addWP(ViewWP wp) {
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
        //TODO
        return false;
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

    public void closeConnection() {
        try {
            in.close();
            out.close();
            os.close();
            is.close();
            socket.close();
        } catch (IOException e) {
            //notify lost connection to player
            new GUI().display(new ClientSocket("127.0.0.1", 1080));
        }
    }

}
