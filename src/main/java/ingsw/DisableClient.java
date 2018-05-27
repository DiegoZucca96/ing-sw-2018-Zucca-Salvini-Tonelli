package ingsw;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DisableClient extends UnicastRemoteObject implements ClientState {

    private Server server;

    public DisableClient() throws RemoteException {
        super();
        server = Server.instance(1080);
    }

    @Override
    public ClientState setState(String clientName) {
        server.setClientState(clientName, this);
        return this;
    }

    public String toString(){
        return "disabled";
    }
}
