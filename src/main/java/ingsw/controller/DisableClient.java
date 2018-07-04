package ingsw.controller;

import ingsw.server.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Author: Elio Salvini
 *
 * This class is used to set the player's state to disable
 */
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
