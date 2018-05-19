package ingsw;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerHandler implements Runnable {

    private Server server;
    private Socket socket;
    private Scanner in;
    private PrintWriter out;

    public ServerHandler(Socket socket){
        this.socket=socket;
        server = Server.instance(1080);
    }

    public void run(){
        try {
            setup();
            //...
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void setup() throws IOException {
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);
        out.print("Get name");
        server.addAccount(in.nextLine(), this);
    }

    public void setClientState(ClientState state){
        out.print("Set state:" + state.toString());
    }

}
