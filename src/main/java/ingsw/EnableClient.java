package ingsw;

public class EnableClient implements ClientState {

    private Server server;

    public EnableClient(){
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
