package ingsw;

import ingsw.controller.RMIController;
import ingsw.model.ViewWP;
import ingsw.model.ViewData;
import ingsw.view.GUI;
import ingsw.view.ToolView;
import ingsw.view.Warning;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ScheduledExecutorService;

public class ClientRMI implements Client {

    private String name;
    private RMIController controller;
    private ScheduledExecutorService executorService = null;

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

    @Override
    public boolean setName(String name) {
        this.name = name;
        return true;
    }

    @Override
    public boolean login(String nickname) {
        try {
            return controller.login(nickname);
        } catch (RemoteException e) {
            new Warning(this);
            return  false;
        }
    }

    @Override
    public boolean register(String nickname) {
        name = nickname;
        try {
            return controller.register(nickname);
        } catch (RemoteException e) {
            new Warning(this);
            return false;
        }
    }

    @Override
    public String getPlayerState() {
        try {
            return controller.getPlayerState(name);
        } catch (RemoteException e) {
            handleConnectionError();
            return null;
        }
    }

    @Override
    public boolean skip() {
        try {
            controller.skip(name);
            return true;
        } catch (RemoteException e) {
            handleConnectionError();
            return false;
        }
    }

    @Override
    public boolean useToolCard(int i, ToolView toolView) {
        try {
            return controller.useToolCard(i,toolView);
        } catch (RemoteException e) {
            handleConnectionError();
            return false;
        }
    }

    @Override
    public boolean takeDie(int row, int column) {
        try {
            return controller.takeDie(row, column);
        } catch (RemoteException e) {
            handleConnectionError();
            return false;
        }
    }

    @Override
    public boolean positionDie(int row, int column) {
        try {
            return controller.positionDie(row,column);
        } catch (RemoteException e) {
            handleConnectionError();
            return  false;
        }
    }

    @Override
    public boolean waitForPlayers() {
        try {
            return controller.waitForPlayers();
        } catch (RemoteException e) {
            handleConnectionError();
            return false;
        }
    }

    @Override
    public ViewData initializeView() {
        try {
            return controller.initializeView();
        } catch (RemoteException e) {
            handleConnectionError();
            return null;
        }
    }

    @Override
    public ViewData updateView() {
        try {
            return controller.updateView();
        } catch (RemoteException e) {
            handleConnectionError();
            return null;
        }
    }

    @Override
    public boolean readyToPlay() {
        try {
            return controller.readyToPlay();
        }catch (RemoteException e){
            handleConnectionError();
            return false;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean createHash(int numberWP, String nameClient) {
        try {
            controller.createHash(numberWP,nameClient);
           return true;
        } catch (RemoteException e) {
            handleConnectionError();
            return false;
        }
    }

    @Override
    public String getPVCard(String name) {
        try {
            return controller.getPVCard(name);
        } catch (RemoteException e) {
            handleConnectionError();
        }
        return null;
    }

    @Override
    public boolean setActive(Boolean active) {
        try {
            controller.setActive(active);
            return true;
        } catch (RemoteException e) {
            handleConnectionError();
            return false;
        }
    }

    @Override
    public boolean getActive() {
        try {
            return controller.getActive();
        } catch (RemoteException e) {
            handleConnectionError();
        }
        return false;
    }

    @Override
    public int getTimeMove() {
        try {
            return controller.getTimeMove();
        } catch (RemoteException e) {
            return -1000;
        }
    }

    @Override
    public String getCurrentPlayer()  {
        try {
            return controller.getCurrentPlayerName();
        } catch (RemoteException e) {
            handleConnectionError();
        }
        return null;
    }

    @Override
    public boolean nullSelection() {
        try {
            controller.setNullPlayer();
            return true;
        } catch (RemoteException e) {
            handleConnectionError();
            return false;
        }
    }

    @Override
    public int getRound() {
        try {
            return controller.getRound();
        } catch (RemoteException e) {
            handleConnectionError();
        }
        return -1;
    }

    @Override
    public boolean matchFound() {
        try {
            if(controller.getTimeRemaining()>0)
                return false;
            else
                return true;
        } catch (RemoteException e) {
            handleConnectionError();
            return true;
        }
    }

    //Da implementare la equals di questo metodo
    @Override
    public ViewWP getWP(String userName) {
        try {
            return controller.getWP(userName);
        } catch (RemoteException e1) {
            handleConnectionError();
        }
        return null;
    }

    @Override
    public boolean orderWPChoise() {
        try {
            controller.orderWPChoise();
            return true;
        } catch (RemoteException e) {
            handleConnectionError();
            return false;
        }
    }

    @Override
    public boolean isFinish() {
        try {
            return controller.isFinish();
        } catch (RemoteException e) {
            return false;
        }
    }

    @Override
    public int getScore(String name) {
        try {
            return controller.getScore(name);
        } catch (RemoteException e) {
            handleConnectionError();
            return 0;
        }
    }

    @Override
    public boolean calculateScore() {
        try {
            controller.calculateScore();
            return true;
        } catch (RemoteException e) {
            handleConnectionError();
            return false;
        }
    }

    @Override
    public String findWinner() {
        try {
            return controller.findWinner();
        } catch (RemoteException e) {
            handleConnectionError();
            return null;
        }
    }

    @Override
    public ArrayList<String> getListOfMatchPlayers() {
        try {
            return controller.getListofMatchPlayers();
        } catch (RemoteException e) {
            handleConnectionError();
            return null;
        }
    }

    @Override
    public boolean getInsertedDie() {
        try {
            return controller.getInsertedDie();
        } catch (RemoteException e) {
            handleConnectionError();
            return false;
        }
    }

    @Override
    public boolean setInsertedDie(boolean b) {
        try {
            controller.setInsertedDie(b);
            return true;
        } catch (RemoteException e) {
            handleConnectionError();
            return false;
        }
    }

    @Override
    public boolean setTool8Used(boolean b) {
        try {
            controller.setTool8Used(b);
            return true;
        } catch (RemoteException e) {
            handleConnectionError();
            return false;
        }
    }

    @Override
    public boolean getClockwiseRound() {
        try {
            return controller.getClockwiseRound();
        } catch (RemoteException e) {
            handleConnectionError();
            return false;
        }
    }

    @Override
    public int getTokenRemaining(String name) {
        try {
            return controller.getTokenRemaining(name);
        } catch (RemoteException e) {
            handleConnectionError();
            return -1;
        }
    }

    @Override
    public boolean iAmAlone() {
        try {
            return controller.iAmAlone();
        } catch (RemoteException e) {
            handleConnectionError();
            return false;
        }
    }

    @Override
    public int getStartTimeMove() {
        try {
            return controller.getStartTimeMove();
        } catch (RemoteException e) {
            handleConnectionError();
            return -1;
        }
    }

    @Override
    public ArrayList<ViewWP> getPlayerWPs(String name) {
        try {
            return controller.getPlayersWPs(name);
        } catch (RemoteException e) {
            handleConnectionError();
            return null;
        }
    }

    @Override
    public int getNumberOfPlayers() {
        try {
            return controller.getSizeOfPlayers();
        } catch (RemoteException e) {
            handleConnectionError();
            return -1;
        }
    }

    @Override
    public boolean takeWPDie(int row, int column) {
        try {
            return controller.takeWPDie(row,column);
        } catch (RemoteException e) {
            handleConnectionError();
            return false;
        }
    }

    @Override
    public ArrayList<ViewWP> getRandomWps() {
        try {
            return controller.getRandomWPs();
        } catch (RemoteException e) {
            handleConnectionError();
            return null;
        }
    }

    @Override
    public boolean addWP(ViewWP wp){
        try {
            controller.addWindow(wp);
            return true;
        } catch (RemoteException e) {
            handleConnectionError();
            return false;
        }
    }

    @Override
    public ArrayList<String> getListOfPlayers() {
        try {
            return controller.getListOfPlayers();
        } catch (RemoteException e) {
            handleConnectionError();
            return null;
        }
    }

    @Override
    public int getCoordinateSelectedY() {
        try {
            return controller.getCoordinateSelectedY();
        } catch (RemoteException e) {
            handleConnectionError();
        }
        return -1;
    }

    @Override
    public boolean removePlayer(String name) {
        try {
            return controller.removerPlayer(name);
        } catch (RemoteException e) {
            handleConnectionError();
            return false;
        }
    }

    @Override
    public ArrayList<String> someoneLeftGame() {
        try {
            return controller.someoneLeftGame();
        } catch (RemoteException e) {
            return null;
        }
    }

    @Override
    public ArrayList<String> someoneRejoinedGame() {
        try {
            return controller.someoneRejoinedGame();
        } catch (RemoteException e) {
            return null;
        }
    }

    @Override
    public void handleConnectionError(){
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry("localhost", 1081);
            if(registry!=null)
                controller = (RMIController) registry.lookup("controller");
            Warning.setStop(true);
        } catch (Exception e) {
            registry=null;
        }
    }

}
