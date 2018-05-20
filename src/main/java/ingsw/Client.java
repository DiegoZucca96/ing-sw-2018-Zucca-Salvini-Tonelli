package ingsw;

import java.io.IOException;
import java.util.ArrayList;

public interface Client {

    //avvia il client,fa setup della connessione
    void startClient() throws IOException;

    //permette al gicatore di fare login, restituisce false se l'utente non è registrato
    boolean login(String nickname);

    //permette al giocatore di registrarsi, restituisce false se il nickname è già usato
    boolean register(String nickname);

    //restituisce un array contenente i dati per renderizzare le 4 wp
   // ArrayList<ArrayList<Integer>[][]> getRandomWps();

    //prende dalla draftpool il dado in posizione index, e lo mette nella wp in posizione (row, column)
    //restitusce false se si violano le restrizioni
    boolean moveDie(int index, int row, int column);

    //passa il turno
    void skip();

    //utilizza la tool card, parameter è una stringa che serve per creare l'oggetto di tipo PlayerUseTool
    //restituisce false se la carta non può essere usata
    boolean useToolCard(String parameter);

    //restituisce lo stato del giocatore, "enabled" -> attivo. "disabled" -> disattivo (non è il suo turno)
    String getPlayerState();

}
