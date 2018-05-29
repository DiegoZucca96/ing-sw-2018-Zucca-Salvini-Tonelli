package ingsw;

import ingsw.controller.RMIController;
import ingsw.model.ViewWP;
import ingsw.model.ViewData;
import ingsw.view.GUI;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;

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

    public void setController(RMIController controller) {
        this.controller = controller;
    }

    public void setName(String name) {
        this.name = name;
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
    public boolean takeDie(int row, int column) {
        try {
            return controller.takeDie(row, column);
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
    public ViewData initializeView() {
        try {
            return controller.initializeView();
        } catch (RemoteException e) {
            return null;
        }
    }

    @Override
    public ViewData updateView() {
        return null;
    }

    @Override
    public boolean readyToPlay() {
        try {
            return controller.readyToPlay();
        }catch (RemoteException e){
            return false;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void createHash(int numberWP, String nameClient) {
        try {
            controller.createHash(numberWP,nameClient);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public HashMap<String, Integer> getHashPlayers() {
        try {
            return controller.getHashPlayers();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getPVCard(String name) {
        try {
            return controller.getPVCard(name);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setActive() {
        try {
            controller.setActive();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean getActive() {
        try {
            return controller.getActive();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void rejoinedPlayer(String name) {
        try {
            controller.rejoinedPlayer(name);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getTimeMove() {
        try {
            return controller.getTimeMove();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public String getCurrentPlayer()  {
        try {
            return controller.getCurrentPlayerName();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<ViewWP> getPlayerWPs(String name) {
        try {
            return controller.getPlayersWPs(name);
        } catch (RemoteException e) {
            return null;
        }
    }

    @Override
    public int getNumberOfPlayers() {
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
    public ArrayList<ViewWP> getRandomWps() {
        try {
            return controller.getRandomWPs();
        } catch (RemoteException e) {
            return null;
        }
    }

    @Override
    public void addWP(ViewWP wp){
        try {
            controller.addWindow(wp);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addWPName(String wp){
        try {
            controller.addWindowName(wp);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<String> getListOfPlayers() {
        try {
            return controller.getListOfPlayers();
        } catch (RemoteException e) {
            return null;
        }
    }
}
