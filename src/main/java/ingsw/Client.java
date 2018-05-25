package ingsw;

import ingsw.controller.WPViewChoise;

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

    //NB: -> Metodo che crea le 4 WP da scegliere ritornando le informazioni per creare il background dei bottoni
    ArrayList<WPViewChoise> getRandomWps() throws IOException;

    //Restituisce la lista dei nomi dei giocatori
    ArrayList<String> getListOfPlayers();

    //prende dalla draftpool il dado in posizione index
    boolean takeDie(int row, int col);

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

    //restituisce arraylist della scelta di tutti i giocatori
    ArrayList<WPViewChoise> getOthersChoice();

    //mette il giocatore in attesa di nuovi giocatori
    boolean waitForPlayers();

    //restituisce una lista di stringhe rappresentanti gli oggetti da inizializzare nella view
    ArrayList<String> initializeView();

    //restituisce una lista di stringhe rappresentanti gli oggetti da aggiornare nella view
    ArrayList<String> updateView();

    //restituisce true se tutti hanno scelto la propria wp e si avvia la partita
    boolean getOthersWP();

    String getName();

    /*
    "windowpattern(name(ciao),difficulty(4),cell(row(0),column(0),number(0),color(RED),Die(number(5),color(RED)),cell(..."

    vecchio modo --> "oggetto:attributo,attributo,..."
    nuovo modo --> "oggetto(attributo,attributo,...)"
    */
}
