package ingsw;

import ingsw.view.StartGame;

public class Client {
    public static void main(String[] args) throws Exception {
        //In teoria qua si deve passare al metodo grafico il controller per far eseguire le varie operazioni
        new StartGame().main();
    }
}
