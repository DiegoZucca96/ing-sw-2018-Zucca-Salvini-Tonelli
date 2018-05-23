package ingsw;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Author: Elio Salvini*/

public interface Client {

    //avvia il client,fa setup della connessione
    void startClient() throws IOException;

    //permette al gicatore di fare login, restituisce false se l'utente non è registrato
    boolean login(String nickname);

    //permette al giocatore di registrarsi, restituisce false se il nickname è già usato
    boolean register(String nickname);

    //restituisce un array contenente stringh rappresentanti le 4 wps tra cui il giocatore deve scegliere
    ArrayList<String> getRandomWps() throws IOException;

    //prende dalla draftpool il dado in posizione index
    boolean takeDie(int index);

    //prende un dado in posizione (row, column) dalla wp del giocatore corrente,
    //restituisce false se il dado non può essere preso
    boolean takeWPDie(int row, int column);

    //posiziona il dado nelle coordinate row, column della wp del giocatore
    //restitusce false se si violano le restrizioni
    boolean positionDie(int row, int column);

    //passa il turno
    void skip();

    //utilizza la tool card, parameter è una stringa che serve per creare l'oggetto di tipo PlayerUseTool
    //restituisce false se la carta non può essere usata
    boolean useToolCard(String parameter);

    //restituisce lo stato del giocatore, "enabled" -> attivo. "disabled" -> disattivo (non è il suo turno)
    String getPlayerState();

    //Restituisce il numero dei giocatori della partita
    int getSizeOfPlayers();

    //Restituisce il timer rimanente del server durante la ricerca
    int getTimeSearch();

    //restituisce true se hanno scelto tutti la wp e si avvia la partita
    boolean getOthersChoice();

    //mette il giocatore in attesa di nuovi giocatori
    boolean waitForPlayers();

}
