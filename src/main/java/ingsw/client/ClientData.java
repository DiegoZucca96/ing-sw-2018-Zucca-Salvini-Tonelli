package ingsw.client;

import ingsw.controller.ClientState;
import ingsw.server.ServerHandler;

/**
 * Author: Elio Salvini
 */
public class ClientData {

    private String name;            //name of the account
    private ClientState state;      //account's state
    private ServerHandler handler;  //thread that communicates with client (only for socket)
    private String connection;      //connection's type

    public ClientData(String name){
        this.name = name;
        this.state = null;
        handler = null;
        connection = "RMI";
    }

    public ClientData(String name, ServerHandler handler){
        this.name = name;
        this.state = null;
        this.handler = handler;
        connection = "socket";
    }

    public ClientState getState() {
        return state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setState(ClientState state) {
        this.state = state;
    }


}
