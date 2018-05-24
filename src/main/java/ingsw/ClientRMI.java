package ingsw;

import ingsw.controller.RMIController;
import ingsw.controller.WPViewChoise;
import ingsw.view.GUI;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class ClientRMI implements Client {

    private String name;
    private RMIController controller;

    public void startClient() throws IOException{
        Registry registry = LocateRegistry.getRegistry("localhost",1081);
        try {
            controller = (RMIController) registry.lookup("controller");
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        new GUI().display(this);
    }

    @Override
    public boolean login(String nickname) {
        try {
            return controller.login(nickname);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean register(String nickname) {
        name = nickname;
        try {
            return controller.register(nickname);
        } catch (RemoteException e) {
            return false;
        }
    }

    @Override
    public String getPlayerState() {
        try {
            return controller.getPlayerState(name);
        } catch (RemoteException e) {
            return null;
        }
    }

    @Override
    public void skip() {
        try {
            controller.skip(name);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean useToolCard(String parameter) {
        return false;
    }

    @Override
    public boolean takeDie(int index, int index2) {
        try {
            return controller.takeDie(index, index2);
        } catch (RemoteException e) {
            return false;
        }
    }

    @Override
    public boolean positionDie(int row, int column) {
        try {
            return controller.positionDie(row,column);
        } catch (RemoteException e) {
            return  false;
        }
    }

    @Override
    public boolean waitForPlayers() {
        try {
            return controller.waitForPlayers();
        } catch (RemoteException e) {
            return false;
        }
    }

    @Override
    public ArrayList<String> initializeView() {
        return null;
    }

    @Override
    public ArrayList<String> updateView() {
        return null;
    }

    @Override
    public boolean getOthersChoice() {
        try {
            return controller.getOthersChoice();
        } catch (RemoteException e) {
            return false;
        }
    }

    @Override
    public int getSizeOfPlayers() {
        try {
            return controller.getSizeOfPlayers();
        } catch (RemoteException e) {
            return -1;
        }
    }

    @Override
    public int getTimeSearch() {
        try {
            return controller.getTimeSearch();
        } catch (RemoteException e) {
            return -1;
        }
    }

    @Override
    public boolean takeWPDie(int row, int column) {
        try {
            return controller.takeWPDie(row,column);
        } catch (RemoteException e) {
            return false;
        }
    }

    @Override
    public ArrayList<WPViewChoise> getRandomWps() {
        try {
            return controller.getRandomWPs();
        } catch (RemoteException e) {
            return null;
        }
    }

    @Override
    public ArrayList<String> getListOfPlayers() {
        try {
            return controller.getListOfPlayers();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
}
