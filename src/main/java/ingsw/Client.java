package ingsw;

import ingsw.model.ViewWP;
import ingsw.model.ViewData;
import ingsw.view.GUI.ToolView;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Author: Elio Salvini
 */

public interface Client {

    /**
     * This method begins connection with server
     * @param hostAddress   server address
     * @throws IOException  exception due to connection error
     */
    void startClient(String hostAddress) throws IOException;

    /**
     * This method allows client to log in
     * @param nickname player's nickname (if he is registered)
     * @return false if player can't log in
     */
    boolean login(String nickname);

    /**
     * This method allows client to register
     * @param nickname  player's nickname
     * @return  false if nickname is already used
     */
    boolean register(String nickname);

    /**
     * This method allows player to see 4 window patterns among which the player has to choose his one
     * @return 4 window patterns to render view side
     */
    ArrayList<ViewWP> getRandomWps();

    /**
     * ???
     * @param wp
     */
    void addWP(ViewWP wp);

    /**
     *
     * @return player's nickname list
     */
    ArrayList<String> getListOfPlayers();

    /**
     * ???
     * @return
     */
    //Ritornano le coordinate cliccate dal giocatore
    int getCoordinateSelectedY();

    /**
     * This method allows player to take a die from the draft pool
     * @param row   ???
     * @param col   ???
     * @return  false if player can't take the requested die
     */
    boolean takeDie(int row, int col);

    /**
     * This method allows player to take a die from his window pattern
     * @param row   row in which the requested die is placed
     * @param column column in which requested die is placed
     * @return  false if die can't be taken
     */
    boolean takeWPDie(int row, int column);

    /**
     * This method allows player to place the previously taken die in his window pattern
     * @param row   row in which the requested die has to be placed
     * @param column column in which the requested die has to be placed
     * @return false if die can't be placed
     */
    boolean positionDie(int row, int column);

    /**
     * This method allow player to skip, or end, his turn
     */
    void skip();

    /**
     * This method allows player to use a tool card
     * @param i tool card number
     * @param parameter player's parameters to use the tool card
     * @return  false if the tool card can't be used
     */
    boolean useToolCard(int i, ToolView parameter);

    /**
     *
     * @return  player's state (during his turn the state is "enabled", if he's waiting for his turn the state
     *          is "disabled")
     */
    String getPlayerState();

    /**
     *
     * @return number of match players
     */
    int getNumberOfPlayers();

    /**
     *
     * @param name  ???
     * @return object used to render the windows pattern chosen by players
     */
    ArrayList<ViewWP> getPlayerWPs(String name);

    /**
     * This method put player on hold of others players
     * @return false until match starts
     */
    boolean waitForPlayers();

    /**
     *
     * @return an object containing all necessary data to render view GUI side
     */
    ViewData initializeView();

    /**
     *
     * @return an object containing all necessary data to render view CLI side
     */
    ViewData initializeViewCLI();

    /**
     *
     * @return an object containing all necessary data to update view GUI and CLI side
     */
    ViewData updateView();

    /**
     *
     * @return true if the match can be started (after that players has chosen their windows pattern)
     */
    boolean readyToPlay();

    /**
     *
     * @return client's name
     */
    String getName();

    /**
     *  ???
     * @param nameWindow
     * @param nameClient
     */
    //crea un hash di valori player-WP scelta (credo aggiunga la wp scelta all'hashmap del controller)
    void createHash(int nameWindow, String nameClient);

    /**
     *
     * @param name player's name
     * @return  player's private objective card
     */
    String getPVCard(String name);

    /**
     * ???
     * @param active
     */
    //serve per controllare che il client sia attivo
    void setActive(Boolean active);

    /**
     * this method allows client to understand if server considers him inactive due to the fact the timer finished
     * and players hasn't made any move during his turn
     * @return true if server considers him active
     */
    boolean getActive();

    /**
     *
     * @return seconds that player has to make a move
     */
    int getTimeMove();

    /**
     *
     * @return player who has the right to play in the moment of the call of this method
     */
    String getCurrentPlayer();

    /**
     * ???
     */
    //Setta i valori di selection player a null in caso di annullamento della mossa
    void nullSelection();

    /**
     *
     * @return current match round
     */
    int getRound();

    /**
     *
     * @return true if match is already been found
     */
    boolean matchFound();

    /**
     * This method allows client to render player's window pattern
     * @param userName player's nickname
     * @return  an object containing all necessary data to render player's window pattern
     */
    ViewWP getWP(String userName);

    /**
     *  ???
     */
    //Ordina le windowChosen
    void orderWPChoise();    //si scrive Choice...

    /**
     *
     * @return true if match is finished
     */
    //Va a controllare se la partita è giunta al termine
    boolean isFinish();

    /**
     * This method allows player to know his (and others players) 's score
     * @param name player's nickname
     * @return player's score
     */
    int getScore(String name);

    /**
     * This method launches the calculation of players scores model side
     */
    void calculateScore();

    /**
     *
     * @return match winner
     */
    String findWinner();

    /**
     * ???
     * @return
     */
    //Restituisce i nomi dei players (serve nella schermata finale di vittoria)
    ArrayList<String> getListOfMatchPlayers();

    /**
     * ???
     * @return
     */
    //i tre metodi sotto tengono conto del fatto che il giocatore abbia posizionato un dado o meno e se ha usato la tool8(per saltare il turno)
    boolean getInsertedDie();

    void setInsertedDie(boolean b);

    /**
     * This method allows client to notify server the use of tool card 8
     * @param isTool8Used true if toolCard 8 is used
     */
    void setTool8Used(boolean isTool8Used);

    /**
     * This method allows a player to understand if the turn he is playing is the first, or the second, of the round
     * @return true if turn's "way" is clockwise
     */
    boolean getClockwiseRound();

    /**
     *
     * @param name player's nickname
     * @return remaining player's tokens
     */
    int getTokenRemaining(String name);

    /**
     *
     * @return true if player who calls this method is the only one active in game
     */
    boolean iAmAlone();

    /**
     * ???
     * @return
     */
    int getStartTimeMove();

    /**
     * This method allows player to set his nickname
     * @param userName player's nickname
     */
    void setName(String userName);

    /**
     * This method handles connection errors RMI side
     */
    void handleConnectionError();

    /**
     * This method allows player to stop the research of other players
     * @param name ???
     * @return ???
     */
    //Per annullare la ricerca del giocatore
    boolean removePlayer(String name);

    /**
     * Method to handle player's reconnection
     * @return ???
     */
    //Per notificare se un giocatore è uscito o rientrato
    ArrayList<String> someoneLeftGame();

    /**
     * Method to handle player's reconnection
     * @return  ???
     */
    ArrayList<String> someoneRejoinedGame();

    /**
     * This method allows to stop player's turn timer
     */
    void stopTimer();
}
