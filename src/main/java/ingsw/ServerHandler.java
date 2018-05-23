package ingsw;

import ingsw.controller.Controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
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
                else if (command.equals("getPlayerState")) getPlayerState(parameter);
                else if (command.equals("skip")) controller.skip(parameter);
                else if (command.equals("takeDie")) takeDie(parameter);
                else if (command.equals("positionDie")) positionDie(parameter);
                else if (command.equals("getSizeOfPlayers")) getSizeOfPlayers();
                else if (command.equals("getTimeSearch")) getTimeSearch();
                else if (command.equals("takeWPDie")) takeWPDie(parameter);
                else if (command.equals("getRandomWPs")) getRandomWPs();
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

    private void login(String parameter) throws RemoteException {
        if(controller.login(parameter)) out.print("ok");
        else out.print("ko");
    }

    private void register(String parameter) throws RemoteException {
        if(controller.register(parameter, this)) out.print("ok");
        else out.print("ko");
    }

    private void getPlayerState(String clientName) throws RemoteException {
         out.print(controller.getPlayerState(clientName));
    }

    private void takeDie(String parameter) throws RemoteException {
        if(controller.takeDie(Integer.parseInt(parameter)))out.print("ok");
        else out.print("ko");
    }

    private void positionDie(String parameter) throws RemoteException {
        if(controller.positionDie(Integer.parseInt(firstParameter(parameter)),Integer.parseInt(secondParameter(parameter)))) out.print("ok");
        else out.print("ko");
    }

    private String firstParameter(String parameter){
        return parameter.substring(0,parameter.indexOf(',')-1);
    }

    private String secondParameter(String parameter){
        return parameter.substring(parameter.indexOf(',')+1,parameter.length()-1);
    }

    private void getSizeOfPlayers() throws RemoteException {
        out.print(controller.getSizeOfPlayers());
    }

    private void getTimeSearch() throws RemoteException {
        out.print(controller.getTimeSearch());
    }

    private void takeWPDie(String parameter) throws RemoteException {
        if(controller.takeWPDie(Integer.parseInt(firstParameter(parameter)), Integer.parseInt(secondParameter(parameter)))) out.print("ok");
        else out.print("ko");
    }

    private void getRandomWPs() throws RemoteException{
        os.writeObject( (ArrayList<String>) controller.getRandomWPs());
    }

}
