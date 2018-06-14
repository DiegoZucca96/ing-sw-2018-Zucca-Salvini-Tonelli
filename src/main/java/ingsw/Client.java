package ingsw;

import ingsw.model.ViewWP;
import ingsw.model.ViewData;
import ingsw.view.ToolView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Author: Elio Salvini*/

public interface Client {

    //avvia il client,fa setup della connessione
    void startClient() throws IOException;

    //permette al gicatore di fare login, restituisce false se l'utente non è registrato
    boolean login(String nickname);

    //permette al giocatore di registrarsi, restituisce false se il nickname è già usato
    boolean register(String nickname);

    //Metodo che crea le 4 WP da scegliere ritornando le informazioni per creare il background dei bottoni
    ArrayList<ViewWP> getRandomWps() throws IOException;

    void addWPName(String wp);

    void addWP(ViewWP wp);

    //Restituisce la lista dei nomi dei giocatori
    ArrayList<String> getListOfPlayers();

    //Ritornano le coordinate cliccate dal giocatore
    int getCoordinateSelectedX();
    int getCoordinateSelectedY();


    //prende dalla draftpool il dado in posizione (row, column)
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
    boolean useToolCard(int i, ToolView parameter);

    //restituisce lo stato del giocatore, "enabled" -> attivo. "disabled" -> disattivo (non è il suo turno)
    String getPlayerState();

    //Restituisce il numero dei giocatori della partita
    int getNumberOfPlayers();

    //Restituisce il timer rimanente del server durante la ricerca
    int getTimeSearch();

    //restituisce arraylist della scelta di tutti i giocatori
    ArrayList<ViewWP> getPlayerWPs(String name);

    //mette il giocatore in attesa di nuovi giocatori
    boolean waitForPlayers();

    //restituisce una lista di stringhe rappresentanti gli oggetti da inizializzare nella view
    ViewData initializeView();

    //restituisce una lista di stringhe rappresentanti gli oggetti da aggiornare nella view
    ViewData updateView();

    //restituisce true se tutti hanno scelto la propria wp e si avvia la partita
    boolean readyToPlay();

    //restituisce il nome del client che viene chiamato
    String getName();

    //crea un hash di valori player-WP scelta (credo aggiunga la wp scelta all'hashmap del controller)
    void createHash(int nameWindow, String nameClient);

    //Restituisce l'hashmap per la view
    HashMap<String, Integer> getHashPlayers();

    String getPVCard(String name);

    //serve per controllare che il client sia attivo
    void setActive(Boolean active);

    //attivo o no?
    boolean getActive();

    //rejoin client to list of Player
    void rejoinedPlayer(String name);

    //tempo per fare una mossa
    int getTimeMove();

    //il turno di?
    String getCurrentPlayer();

    //Setta i valori di selection player a null in caso di annullamento della mossa
    void nullSelection();

    //Restituisce il round
    int getRound();

    //Dice se il match è già stato trovato o meno
    boolean matchFound();

    //Restituisce la lista di giocatori inattivi/disconnessi
    ArrayList<String> getInactiveList();

    //Restituisce la WP che era scelta dal player
    ViewWP getWP(String userName);

    //Ordina le windowChosen
    void orderWPChoise();

    //Va a controllare se la partita è giunta al termine
    boolean isFinish();

    //Restituisce il punteggio del giocatore
    int getScore(String name);

    //Avvia il calcolo dei punteggi giocatore
    void calculateScore();

    //Trova il vincitore
    String findWinner();

    //Restituisce i nomi dei players (serve nella schermata finale di vittoria)
    ArrayList<String> getListOfMatchPlayers();

    //imposta lo stato del client a disconnected
    void disconnectClient();

    //i quattro metodi sotto tengono conto del fatto che il giocatore abbia posizionato un dado o meno e se ha usato la tool8(per saltare il turno)
    boolean getInsertedDie();

    void setInsertedDie(boolean b);

    boolean getTool8Used();

    void setTool8Used(boolean isTool8Used);

    //Stabilire il turno in cui ci si trova
    boolean getClockwiseRound();

    //Indica quanti token rimangono al giocatore
    int getTokenRemaining(String name);

    //Restituisce un valore true se sono l'unico giocatore attivo al momento dello skip
    boolean iAmAlone();

    /*
    "windowpattern(name(ciao),difficulty(4),cell(row(0),column(0),number(0),color(RED),Die(number(5),color(RED)),cell(..."

    vecchio modo --> "oggetto:attributo,attributo,..."
    nuovo modo --> "oggetto(attributo,attributo,...)"
    */
}
