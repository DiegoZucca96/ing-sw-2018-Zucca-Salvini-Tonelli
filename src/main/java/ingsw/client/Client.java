package ingsw.client;

import ingsw.model.ViewWP;
import ingsw.model.ViewData;
import ingsw.view.gui.ToolView;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Author: Elio Salvini - Diego Zucca
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
     * It adds the window chosen by the client into a Controller's list
     * @param wp it is the window chosen
     */
    void addWP(ViewWP wp);

    /**
     * Simply getter method
     * @return player's nickname list
     */
    ArrayList<String> getListOfPlayers();

    /**
     * Simply getter method
     * @return the Y coordinate selected by the player
     */
    int getCoordinateSelectedY();

    /**
     * This method allows player to take a die from the draft pool
     * @param row it is the X coordinate of the DraftPool
     * @param col it is the Y coordinate of the DraftPool
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
     * Simply getter method
     * @return  player's state (during his turn the state is "enabled", if he's waiting for his turn the state
     *          is "disabled")
     */
    String getPlayerState();

    /**
     * Simply getter method
     * @return number of match players
     */
    int getNumberOfPlayers();

    /**
     * Simply getter method
     * @param name  it is the name of the player that wants to know the enemy's windows
     * @return object used to render the windows pattern chosen by the other players
     */
    ArrayList<ViewWP> getPlayerWPs(String name);

    /**
     * This method put player on hold of others players
     * @return false until match starts
     */
    boolean waitForPlayers();

    /**
     * Simply getter method
     * @return an object containing all necessary data to render view gui side
     */
    ViewData initializeView();

    /**
     * Simply getter method
     * @return an object containing all necessary data to render view cli side
     */
    ViewData initializeViewCLI();

    /**
     * Simply getter method
     * @return an object containing all necessary data to update view gui and cli side
     */
    ViewData updateView();

    /**
     * Simply getter method
     * @return true if the match can be started (after that players has chosen their windows pattern)
     */
    boolean readyToPlay();

    /**
     * Simply getter method
     * @return client's name
     */
    String getName();

    /**
     * It creates an HashMap with the name of client as key and the number of the window chosen by him as value.
     * @param nameWindow it is the number associated to the window
     * @param nameClient it is the name of the client
     */
    void createHash(int nameWindow, String nameClient);

    /**
     * Simply getter method
     * @param name player's name
     * @return  player's private objective card
     */
    String getPVCard(String name);

    /**
     * Simply setter method
     * @param active it is the boolean value to set
     */
    void setActive(Boolean active);

    /**
     * this method allows client to understand if server considers him inactive due to the fact the timer finished
     * and players hasn't made any move during his turn
     * @return true if server considers him active
     */
    boolean getActive();

    /**
     * Simply getter method
     * @return seconds that player has to make a move
     */
    int getTimeMove();

    /**
     * Simply getter method
     * @return player who has the right to play in the moment of the call of this method
     */
    String getCurrentPlayer();

    /**
     * Simply setter method, it cancels the informations about the die and the coordinate selected by the player
     */
    void nullSelection();

    /**
     * Simply getter method
     * @return current match round
     */
    int getRound();

    /**
     * Simply getter method
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
     * It orders the windows chosen by the players during the creation of the match
     */
    void orderWPChoise();

    /**
     * Simply getter method
     * @return true if match is finished
     */
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
     * Simply getter method
     * @return match's winner
     */
    String findWinner();

    /**
     * Simply getter method
     * @return a list of players ordered by their turn
     */
    ArrayList<String> getListOfMatchPlayers();

    /**
     * Simply getter method
     * @return true if the player has already inserted a die in his turn
     */
    boolean getInsertedDie();

    /**
     * Simply setter method
     * @param b it is the value to be assigned
     */
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
     * Simply getter method
     * @param name player's nickname
     * @return remaining player's tokens
     */
    int getTokenRemaining(String name);

    /**
     * Simply getter method
     * @return true if player who calls this method is the only one active in game
     */
    boolean iAmAlone();

    /**
     * Simply getter method
     * @return the time that a player has to make his turn
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
     * @param name it is the name of the client
     * @return true if the operation is done correctly
     */
    boolean removePlayer(String name);

    /**
     * This method is used to notify the other players if someone has left the game, for any reason
     * @return a list of the player's name that left the game
     */
    ArrayList<String> someoneLeftGame();

    /**
     * This method is used to notify the other players if someone has rejoined the game
     * @return a list of the player's name that has rejoined into the match
     */
    ArrayList<String> someoneRejoinedGame();

    /**
     * This method allows to stop player's turn timer (used to interrupt the timeline)
     */
    void stopTimer();
}
