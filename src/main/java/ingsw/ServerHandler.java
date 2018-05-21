package ingsw;

import ingsw.controller.Controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerHandler implements Runnable {

    private Server server;
    private Controller controller;
    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    private ObjectInputStream is;
    private ObjectOutputStream os;

    public ServerHandler(Socket socket){
        this.socket=socket;
        server = Server.instance(1080);
        controller = server.getController();
        try {
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
            os = new ObjectOutputStream(socket.getOutputStream());
            is = new ObjectInputStream(socket.getInputStream());
        }catch(IOException e){
            System.err.println(e.getMessage());
        }
    }

    public void run(){
        String request;
        String command;
        String parameter;
        try {
            setup();
            while(true){
                request = in.nextLine();
                command = request.substring(0,request.indexOf(':'));
                parameter = request.substring(request.indexOf(':')+1, request.length()-1);
                if(command.equals("close")) break;
                else if (command.equals("login")) login(parameter);
                else if (command.equals("register")) register(parameter);
                else if (command.equals("state")) getPlayerState(parameter);
                else if (command.equals("skip")) controller.skip(parameter);
                }
            socket.close();
        }
         catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void setup() throws IOException {
        //...
    }

    /*public void setClientState(ClientState state){
        out.print("Set state:" + state.toString());
    }*/

    public void login(String parameter){
        if(controller.login(parameter)) out.print("ok");
        else out.print("ko");
    }

    public void register(String parameter){
        if(controller.register(parameter, this)) out.print("ok");
        else out.print("ko");
    }

    public String getPlayerState(String clientName){
        return controller.getPlayerState(clientName);
    }

}
