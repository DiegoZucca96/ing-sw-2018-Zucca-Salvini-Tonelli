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
    ArrayList<ViewWP> getRandomWps();

    boolean addWP(ViewWP wp);

    //Restituisce la lista dei nomi dei giocatori
    ArrayList<String> getListOfPlayers();

    //Ritornano le coordinate cliccate dal giocatore
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
    boolean skip();

    //utilizza la tool card, parameter è una stringa che serve per creare l'oggetto di tipo PlayerUseTool
    //restituisce false se la carta non può essere usata
    boolean useToolCard(int i, ToolView parameter);

    //restituisce lo stato del giocatore, "enabled" -> attivo. "disabled" -> disattivo (non è il suo turno)
    String getPlayerState();

    //Restituisce il numero dei giocatori della partita
    int getNumberOfPlayers();

    //restituisce arraylist della scelta di tutti i giocatori
    ArrayList<ViewWP> getPlayerWPs(String name);

    //mette il giocatore in attesa di nuovi giocatori
    boolean waitForPlayers();

    //restituisce una lista di stringhe rappresentanti gli oggetti da inizializzare nella view
    ViewData initializeView();

    ViewData initializeViewCLI();

    //restituisce una lista di stringhe rappresentanti gli oggetti da aggiornare nella view
    ViewData updateView();

    //restituisce true se tutti hanno scelto la propria wp e si avvia la partita
    boolean readyToPlay();

    //restituisce il nome del client che viene chiamato
    String getName();

    //crea un hash di valori player-WP scelta (credo aggiunga la wp scelta all'hashmap del controller)
    boolean createHash(int nameWindow, String nameClient);

    String getPVCard(String name);

    //serve per controllare che il client sia attivo
    boolean setActive(Boolean active);

    //attivo o no?
    boolean getActive();

    //tempo per fare una mossa
    int getTimeMove();

    //il turno di?
    String getCurrentPlayer();

    //Setta i valori di selection player a null in caso di annullamento della mossa
    boolean nullSelection();

    //Restituisce il round
    int getRound();

    //Dice se il match è già stato trovato o meno
    boolean matchFound();

    //Restituisce la WP che era scelta dal player
    ViewWP getWP(String userName);

    //Ordina le windowChosen
    boolean orderWPChoise();    //si scrive Choice...

    //Va a controllare se la partita è giunta al termine
    boolean isFinish();

    //Restituisce il punteggio del giocatore
    int getScore(String name);

    //Avvia il calcolo dei punteggi giocatore
    boolean calculateScore();

    //Trova il vincitore
    String findWinner();

    //Restituisce i nomi dei players (serve nella schermata finale di vittoria)
    ArrayList<String> getListOfMatchPlayers();

    //i tre metodi sotto tengono conto del fatto che il giocatore abbia posizionato un dado o meno e se ha usato la tool8(per saltare il turno)
    boolean getInsertedDie();

    boolean setInsertedDie(boolean b);

    boolean setTool8Used(boolean isTool8Used);

    //Stabilire il turno in cui ci si trova
    boolean getClockwiseRound();

    //Indica quanti token rimangono al giocatore
    int getTokenRemaining(String name);

    //Restituisce un valore true se sono l'unico giocatore attivo al momento dello skip
    boolean iAmAlone();

    int getStartTimeMove();

    boolean setName(String userName);

    //Da usare solo lato RMI
    void handleConnectionError();

    //Per annullare la ricerca del giocatore
    boolean removePlayer(String name);

    //Per notificare se un giocatore è uscito o rientrato
    ArrayList<String> someoneLeftGame();

    ArrayList<String> someoneRejoinedGame();
}
