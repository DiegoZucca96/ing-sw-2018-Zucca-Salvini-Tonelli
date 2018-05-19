package ingsw;

public class DisableClient implements ClientState {

    private Server server;

    public DisableClient(){
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
