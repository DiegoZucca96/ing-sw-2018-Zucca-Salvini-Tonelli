package ingsw.controller;

import ingsw.server.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Author: Elio Salvini
 */
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
