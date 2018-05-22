package ingsw;

import ingsw.controller.RMIController;
import ingsw.view.GUI;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientRMI implements Client {

    private String name;
    private RMIController controller;

    public void startClient() throws IOException{
        Registry registry = LocateRegistry.getRegistry();
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
    public boolean takeDie(int index) {
        try {
            return controller.takeDie(index);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean positionDie(int row, int column) {
        try {
            return controller.positionDie(row,column);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
