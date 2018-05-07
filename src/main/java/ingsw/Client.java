package ingsw;

import ingsw.controller.RMIController;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.getRegistry("localhost",1080);
        RMIController controller = (RMIController) registry.lookup("controller");

        //In teoria qua si deve passare al metodo grafico il controller per far eseguire le varie operazioni
        //new StartGame(controller).start();

    }


}
