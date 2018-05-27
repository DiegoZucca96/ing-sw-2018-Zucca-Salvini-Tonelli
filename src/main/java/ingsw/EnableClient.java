package ingsw;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class EnableClient extends UnicastRemoteObject implements ClientState {

    private Server server;

    public EnableClient() throws RemoteException {
        super();
        server = Server.instance(1080);
    }

    @Override
    public ClientState setState(String clientName) {
        server.setClientState(clientName, this);
        return this;
    }

    public String toString(){
        return "enabled";
    }
}
