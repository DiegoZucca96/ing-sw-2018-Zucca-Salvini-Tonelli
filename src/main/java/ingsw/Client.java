package ingsw;

import java.io.IOException;
import java.util.ArrayList;

public interface Client {

    //avvia il client,fa setup della connessione
    void startClient() throws IOException;
    /*
    //permette al gicatore di fare login, restituisce false se l'utente non è registrato
    boolean login(String nickname);

    //permette al giocatore di registrarsi, restituisce false se il nickname è già usato
    boolean register(String nickname);

    //restituisce un array contenente i dati per renderizzare le 4 wp
    ArrayList<Integer>[][] getRandomWps();
*/


}
