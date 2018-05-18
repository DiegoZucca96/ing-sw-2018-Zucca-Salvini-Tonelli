package ingsw;

import ingsw.view.GUI;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {

    private String ip;
    private int port;
    private Scanner in;
    private PrintWriter out;
    private Socket socket;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        Client client = new Client("127.0.0.1",1080);
        //In teoria qua si deve passare al metodo grafico il controller per far eseguire le varie operazioni
        new GUI().main();
        try{
            client.startClientSocket();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    public void startClientSocket() throws IOException {
        try{
            setup();
            //...
        }catch(NoSuchElementException e){
            System.err.println(e.getMessage());
        }
        finally{
            in.close();
            out.close();
            socket.close();
        }
    }

    private void setup() throws IOException {
        socket = new Socket(ip, port);
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream());
    }

}
