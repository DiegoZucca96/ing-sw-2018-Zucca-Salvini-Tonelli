package ingsw;

import ingsw.controller.RMIController;
import ingsw.model.ViewWP;

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
    private RMIController controller;
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
                if(request.indexOf(':') != request.length()-1) parameter = request.substring(request.indexOf(':')+1, request.length());
                else parameter = null;
                if(command.equals("close")) break;
                else if (command.equals("login")) login(parameter);
                else if (command.equals("register")) register(parameter);
                else if (command.equals("getPlayerState")) getPlayerState(parameter);
                else if (command.equals("skip")) skip(parameter);
                else if (command.equals("takeDie")) takeDie(parameter);
                else if (command.equals("positionDie")) positionDie(parameter);
                else if (command.equals("getNumberOfPlayers")) getNumberOfPlayers();
                else if (command.equals("getTimeSearch")) getTimeSearch();
                else if (command.equals("takeWPDie")) takeWPDie(parameter);
                else if (command.equals("getRandomWPs")) getRandomWPs();
                else if (command.equals("createHash")) createHash(parameter);
                else if (command.equals("addWP")) addWP(parameter);
                else if (command.equals("getListOfPlayers")) getListOfPlayers();
                else if (command.equals("getPlayersWPs")) getPlayersWPs(parameter);
                else if (command.equals("waitForPlayers")) waitForPlayers();
                else if (command.equals("initializeView")) initializeView();
                else if (command.equals("readyToPlay")) readyToPlay();
                else if (command.equals("updateView")) updateView();
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
        out.println("Set state:" + state.toString());
    }*/

    private void login(String parameter) throws RemoteException {
        if(controller.login(parameter)) out.println("ok");
        else out.println("ko");
    }

    private void register(String parameter) throws RemoteException {
        if(controller.register(parameter, this)) out.println("ok");
        else out.println("ko");
    }

    private void getPlayerState(String clientName) throws RemoteException {
         out.println(controller.getPlayerState(clientName));
    }

    private void takeDie(String parameter) throws RemoteException {
        if(controller.takeDie(Integer.parseInt(firstParameter(parameter)),Integer.parseInt(secondParameter(parameter))))out.println("ok");
        else out.println("ko");
    }

    private void positionDie(String parameter) throws RemoteException {
        if(controller.positionDie(Integer.parseInt(firstParameter(parameter)),Integer.parseInt(secondParameter(parameter)))) out.println("ok");
        else out.println("ko");
    }

    private String firstParameter(String parameter){
        return parameter.substring(0,parameter.indexOf(','));
    }

    private String secondParameter(String parameter){
        return parameter.substring(parameter.indexOf(',')+1,parameter.length());
    }

    private void getNumberOfPlayers() throws RemoteException {
        out.println(controller.getSizeOfPlayers());
    }

    private void getTimeSearch() throws RemoteException {
        out.println(controller.getTimeSearch());
    }

    private void takeWPDie(String parameter) throws RemoteException {
        if(controller.takeWPDie(Integer.parseInt(firstParameter(parameter)), Integer.parseInt(secondParameter(parameter)))) out.println("ok");
        else out.println("ko");
    }

    private void getRandomWPs() throws RemoteException{
        try {
            os.writeObject( (ArrayList<ViewWP>) controller.getRandomWPs());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createHash(String parameter) throws RemoteException {
        controller.createHash(Integer.parseInt(firstParameter(parameter)), secondParameter(parameter));
    }

    private void addWP(String parameter) throws RemoteException {
        controller.addWindowName(parameter);
    }

    private void getListOfPlayers() throws IOException {
        os.writeObject(controller.getListOfPlayers());
    }

    private void getPlayersWPs(String name) throws IOException {
        os.writeObject(controller.getPlayersWPs(name));
    }

    private void readyToPlay() throws RemoteException {
        if(controller.readyToPlay()) out.println("ok");
        else out.println("ko");
    }

    private void skip(String parameter) throws RemoteException {
        controller.skip(parameter);
    }

    private void waitForPlayers() throws RemoteException {
        controller.waitForPlayers();
    }

    private void initializeView() throws IOException {
        os.writeObject(controller.initializeView());
    }

    private void updateView() throws IOException {
        os.writeObject(controller.updateView());
    }
}
