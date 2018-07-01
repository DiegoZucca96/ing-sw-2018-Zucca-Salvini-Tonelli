package ingsw;

/**
 * Author: Elio Salvini
 */
public class ClientData {

    private String name;            //nome dell'account
    private ClientState state;      //stato del client
    private ServerHandler handler;  //thread che comunica con il client (solo pe socket)
    private String connection;      //tipo di connessione

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
