package ingsw.controller;

import ingsw.server.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Author: Diego Zucca
 *
 * This class is used to set the player's state to disconnected
 */
public class DisconnectedClient extends UnicastRemoteObject implements ClientState {

    private Server server;

    public DisconnectedClient() throws RemoteException {
        super();
        server = Server.instance(1080);
    }

    @Override
    public ClientState setState(String clientName) {
        server.setClientState(clientName, this);
        return this;
    }

    public String toString(){
        return "disconnected";
    }
}
