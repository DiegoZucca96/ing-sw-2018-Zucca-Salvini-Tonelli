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
    private String clientName;
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
            request = in.nextLine();
            command = request.substring(0,request.indexOf(':'));
            if(request.indexOf(':') != request.length()-1) parameter = request.substring(request.indexOf(':')+1, request.length());
            else parameter = null;
            if (command.equals("login")) login(parameter);
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
            else if (command.equals("addWP")) addWP();
            else if (command.equals("addWPName")) addWPName(parameter);
            else if (command.equals("getListOfPlayers")) getListOfPlayers();
            else if (command.equals("getPlayersWPs")) getPlayersWPs(parameter);
            else if (command.equals("waitForPlayers")) waitForPlayers();
            else if (command.equals("initializeView")) initializeView();
            else if (command.equals("readyToPlay")) readyToPlay();
            else if (command.equals("updateView")) updateView();
            else if (command.equals("getHashPlayers")) getHashPlayers();
            else if (command.equals("getPVCard")) getPVCard(parameter);
            else if (command.equals("getActive")) getActive();
            else if (command.equals("setActive")) setActive(parameter);
            else if (command.equals("rejoinedPlayer")) rejoinedPlayer(parameter);
            else if (command.equals("getTimeMove")) getTimeMove();
            else if (command.equals("getCurrentPlayer")) getCurrentPlayer();
            else if (command.equals("nullSelection")) nullSelection();
            else if (command.equals("getRound")) getRound();
            else if (command.equals("getCoordinateSelectedX")) getCoordinateSelectedX();
            else if (command.equals("getCoordinateSelectedY")) getCoordinateSelectedY();
            closeConnection();
        }
         catch (IOException e) {
            System.err.println(e.getMessage());
             try {
                 server.setClientState(clientName, new DisconnectedClient());
                 closeConnection();
             } catch (IOException e1) {
                 e1.printStackTrace();
             }
         }
    }

    private void setup() throws IOException {
        //...
    }

    private void closeConnection() throws IOException {
        socket.close();
        in.close();
        out.close();
        os.close();
        is.close();
    }
    /*public void setClientState(ClientState state){
        out.println("Set state:" + state.toString());
    }*/

    private void login(String parameter) throws RemoteException {
        out.println(controller.login(parameter));
    }

    private void register(String parameter) throws RemoteException {
        out.println(controller.register(parameter,this));
        clientName = parameter;
    }

    private void getPlayerState(String clientName) throws RemoteException {
         out.println(controller.getPlayerState(clientName));
    }

    private void takeDie(String parameter) throws RemoteException {
        out.println(controller.takeDie(Integer.parseInt(firstParameter(parameter)),Integer.parseInt(secondParameter(parameter))));
    }

    private void positionDie(String parameter) throws RemoteException {
        out.println(controller.positionDie(Integer.parseInt(firstParameter(parameter)),Integer.parseInt(secondParameter(parameter))));
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
        out.println(controller.takeWPDie(Integer.parseInt(firstParameter(parameter)), Integer.parseInt(secondParameter(parameter))));
    }

    private void getRandomWPs() throws RemoteException{
        try {
            os.writeObject( (ArrayList<ViewWP>) controller.getRandomWPs());
            os.flush();
            os.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createHash(String parameter) throws RemoteException {
        controller.createHash(Integer.parseInt(firstParameter(parameter)), secondParameter(parameter));
    }

    private void addWP() throws IOException {
        try {
            out.println("ok");
            ViewWP wp = (ViewWP) is.readObject();
            controller.addWindow(wp);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void addWPName(String parameter) throws RemoteException {
        controller.addWindowName(parameter);
    }

    private void getListOfPlayers() throws IOException {
        os.writeObject(controller.getListOfPlayers());
        os.flush();
        os.reset();
    }

    private void getPlayersWPs(String parameter) throws IOException {
        os.writeObject(controller.getPlayersWPs(parameter));
        os.flush();
        os.reset();
    }

    private void readyToPlay() throws RemoteException {
        out.println(controller.readyToPlay());
    }

    private void skip(String parameter) throws RemoteException {
        controller.skip(parameter);
    }

    private void waitForPlayers() throws RemoteException {
        out.println(controller.waitForPlayers());
    }

    private void initializeView() throws IOException {
        os.writeObject(controller.initializeView());
        os.flush();
        os.reset();
    }

    private void updateView() throws IOException {
        os.writeObject(controller.updateView());
        os.flush();
        os.reset();
    }

    private void getHashPlayers() throws RemoteException {
        controller.getHashPlayers();
    }

    private void getPVCard(String parameter) throws RemoteException {
        out.println(controller.getPVCard(parameter));
    }

    private void getActive() throws RemoteException {
        out.println(controller.getActive());
    }

    private  void setActive(String parameter) throws RemoteException {
        controller.setActive(Boolean.parseBoolean(parameter));
    }

    private void getTimeMove() throws RemoteException {
        out.println(controller.getTimeMove());
    }

    private void getCurrentPlayer() throws RemoteException {
        out.println(controller.getCurrentPlayerName());
    }

    private void getRound() throws RemoteException {
        out.println(controller.getRound());
    }

    private void rejoinedPlayer(String parameter) throws RemoteException {
        controller.rejoinedPlayer(parameter);
    }

    private void nullSelection() throws RemoteException {
        controller.setNullPlayer();
    }

    private void getCoordinateSelectedX() throws RemoteException {
        out.println(controller.getCoordinateSelectedX());
    }

    private void getCoordinateSelectedY() throws RemoteException {
        out.println(controller.getCoordinateSelectedY());
    }
}
