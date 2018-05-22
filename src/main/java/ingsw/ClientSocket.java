package ingsw;

import ingsw.view.GUI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
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
            out = new PrintWriter(socket.getOutputStream());
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
            in.close();
            out.close();
            socket.close();
            System.out.println("Connection closed");
        }
    }

    private void setup() throws IOException {
        System.out.println("Client ready");
    }

    @Override
    public boolean login(String nickname) {
        out.print("login:" + nickname);
        if(in.nextLine().equals("ok")) return true;
        else return  false;
    }

    @Override
    public boolean register(String nickname) {
        name = nickname;
        out.print("register:" + nickname);
        if(in.nextLine().equals("ok")) return true;
        else return  false;
    }

    @Override
    public String getPlayerState() {
        out.print("getPlayerState:" + name);
        return in.nextLine();
    }

    @Override
    public void skip() {
        out.print("skip:" + name);
    }

    @Override
    public boolean takeDie(int index) {
        out.print("takeDie:" + index);
        if(in.nextLine().equals("ok")) return true;
        else return false;
    }

    @Override
    public boolean positionDie(int row, int column) {
        out.print("positionDie:" + row + "," + column);
        if(in.nextLine().equals("ok")) return true;
        else return false;
    }

    @Override
    public boolean waitForPlayers() {
        out.print("waitForPlayers:");
        if(in.nextLine().equals("ok")) return  true;
        else return false;
    }

    @Override
    public boolean getOthersChoice() {
        out.print("getOthersChoice:");
        if(in.nextLine().equals("ok")) return  true;
        else return false;
    }

    @Override
    public int getSizeOfPlayers() {
        out.print("getSizeOfPlayers:");
        return Integer.parseInt(in.nextLine());
    }

    @Override
    public int getTimeSearch() {
        out.print("getTimeSearch:");
        return Integer.parseInt(in.nextLine());
    }
}
