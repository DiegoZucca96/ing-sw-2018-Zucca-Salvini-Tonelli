package ingsw;

public class ClientData {

    private String name;            //nome dell'account
    private ClientState state;      //stato del client
    private ServerHandler handler;  //thread che comunica con il client

    public ClientData(String name, ClientState state){
        this.name = name;
        this.state = state;
        handler = null;
    }

    public ClientData(String name, ClientState state, ServerHandler handler){
        this.name = name;
        this.state = state;
        this.handler = handler;
    }

    public ClientState getState() {
        return state;
    }

    public String getName() {
        return name;
    }

    public ServerHandler getHandler() {
        return handler;
    }

    public void setHandler(ServerHandler handler) {
        this.handler = handler;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setState(ClientState state) {
        this.state = state;
    }


}
