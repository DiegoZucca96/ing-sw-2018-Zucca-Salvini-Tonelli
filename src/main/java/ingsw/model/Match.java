package ingsw.model;

import ingsw.observers.*;
import java.util.ArrayList;

/**
 * Class that represent a match in Sagrada, it links all components of the game and players. This class allow
 * communication between model classes and also provides several main functionalities of the game
 * (like the turns management or score calculation and research of winner)
 * Author: Elio Salvini - Diego Zucca
 */
public class Match {
    private int id;                                 // identifier of the current match
    private int nPlayers;                           // number of players
    private Player currentPlayer;                   // player who has the right to take the next move
    private boolean clockwiseRound;                 /* true if the next turn is of the next player in the list players
                                                       false if the next turn goes to the previous player in the list*/
    private ArrayList<Player> players;              //list of player of this match
    private ArrayList<ToolCard> tools;              //tool cards of this match
    private ArrayList<PBObjectiveCard> pbCards;     //public objective cards of this match
    private RoundTrack roundTrack;                  //reference to round track of this match
    private DraftPool draftPool;                    //reference to draft pool of this match
    private ViewData init;                          //object that contains the informations about the default match
    private Observer viewObserver = null;           //observer of the state of the match, related to view
    private boolean calculatedScore;                //used to avoid multiple scoring calculations

    /**
     * Constructor that creates all the necessary objects for playing a match
     * @param id            identifier of the match
     * @param playersNames  list of players names
     * @param playersWP     list of players windows pattern
     */
    public Match(int id, ArrayList<String> playersNames, ArrayList<Integer> playersWP) {
        this.id = id;

        //Initialization of players
        players = new ArrayList<>();
        RandomGenerator rg = new RandomGenerator(4);
        for (int i = 0; i < playersNames.size(); i++) {
            Player player = new Player(playersNames.get(i), playersWP.get(i), Color.values()[rg.random()]);
            players.add(player);
        }
        nPlayers = players.size();
        clockwiseRound = true;
        currentPlayer = players.get(0);

        //Initialization of game's components model side and of necessary components for view
        tools = new ArrayList<>();
        pbCards = new ArrayList<>();
        roundTrack = new RoundTrack();
        draftPool = new DraftPool(roundTrack);
        init = ViewData.instance();

        ArrayList<Integer> numToolCards = ToolCard.generateToolCard(init);
        tools.add(new ToolCard(numToolCards.get(0)));
        tools.add(new ToolCard(numToolCards.get(1)));
        tools.add(new ToolCard(numToolCards.get(2)));

        ArrayList<Integer> numPBCards = PBObjectiveCard.generatePBCard(init);
        pbCards.add(new PBObjectiveCard(numPBCards.get(0)));
        pbCards.add(new PBObjectiveCard(numPBCards.get(1)));
        pbCards.add(new PBObjectiveCard(numPBCards.get(2)));

        ArrayList<Die> dicelist = startRound();
        for(Die die : dicelist)
            init.getDraftPoolDice().add(die.toString());
    }

    /**
     * Simply getter method
     * @return an instance of init (used by view to create the graphic elements)
     */
    public ViewData getInit(){
        return init;
    }

    /**
     * Simply getter method
     * @return true if the turn is clockwise, otherwise false
     */
    public boolean getClockwiseRound() {
        return clockwiseRound;
    }

    /**
     * Simply getter method
     * @return the number of players
     */
    public int getNumOfPlayers() {
        return nPlayers;
    }

    /**
     * Simply getter method
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Simply getter method
     * @return the list of players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Simply setter method, exclusively used for testing
     * @param draftPool it is the draftpool that has to be assign
     */
    public void setDraftPool(DraftPool draftPool) {
        this.draftPool = draftPool;
    }

    /**
     * Simply setter method, exclusively used for testing
     * @param roundTrack it is the roundTrack that has to be assign
     */
    public void setRoundTrack(RoundTrack roundTrack) {
        this.roundTrack = roundTrack;
    }

    /**
     * Simply setter method, exclusively used for testing
     * @param players it is the list of players that has to be assign
     */
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    /**
     * Simply setter method, exclusively used for testing
     * @param currentPlayer it is the current player that has to be assign
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Simply setter method, exclusively used for testing
     * @param pbCards it is the list of public cards that has to be assign
     */
    public void setPbCards(ArrayList<PBObjectiveCard> pbCards) {
        this.pbCards = pbCards;
    }

    /**
     * Simply setter method, exclusively used for testing
     * @param tools it is the list of tool card that has to be assign
     */
    public void setTools(ArrayList<ToolCard> tools) {
        this.tools = tools;
    }

    /**
     * This method begins round throwing dice
     * @return thrown dice
     */
    public ArrayList<Die> startRound() {
        return draftPool.throwsDice(nPlayers * 2 + 1);
    }

    /**
     * This method ends a round and it changes players order
     */
    public void endRound() {
        draftPool.cleanDraftPool();
        roundTrack.nextRound();
        clockwiseRound = true;
        //first player is put at the end of the list, he becomes the last player
        players.remove(currentPlayer);
        players.add(currentPlayer);
        currentPlayer = players.get(0);
        draftPool.throwsDice(getNumOfPlayers()*2 + 1);
    }

    /**
     * This method gives the turn to the next player who has the right to play.
     * This method has to be called seven times in a round with 4 players.
     */
    public void nextTurn() {
        if (currentPlayer == players.get(players.size()-1) && clockwiseRound) {
            clockwiseRound = false;
        } else {
            try {
                //clockwiseRound is used to recognise if this is the first or the second turn of player in this round
                if (clockwiseRound) {
                    currentPlayer = players.get(players.indexOf(currentPlayer) + 1);
                } else {
                    currentPlayer = players.get(players.indexOf(currentPlayer) - 1);
                }
            } catch (IndexOutOfBoundsException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * Method that allow a player tot take a die from the draft pool
     * @param dieIndex  sequential position of the requested die
     * @return  die in position dieIndex in draft pool
     */
    public Die playerTakeDie(int dieIndex) {
        return draftPool.getDie(dieIndex);
    }

    /**
     * Method that puts an "empty die" in order to remove a die from the draft pool and let its dimension unchanged
     * @param dieIndex  sequential position of the die that has to be removed
     * @return  removed die
     */
    public Die draftpoolRemoveDie(int dieIndex) {
        return draftPool.takeDie(dieIndex);
    }

    /**
     * This method allows to put a die in current player's window pattern
     * @param die   die you want to put in the window pattern
     * @param destination   coordinates where you want to put the die
     * @return true if die can be placed in the specified destination
     */
    public boolean positionDie(Die die, Coordinate destination){
        return currentPlayer.positionDie(die, destination);
    }

    /**
     * This method takes all necessary data for using a tool card
     * @param idCard    tool card you want to use
     * @param pTParameter   parameter that contains players indications for using the specified tool card
     * @return  an object that contains all necessary data for using the specified tool card;
     *          if the tool card can't be used it returns null
     */
    private ObjectiveTool createToolParameter(int idCard, PlayerToolParameter pTParameter) {
        ObjectiveTool toolParameter = new ObjectiveTool();
        switch (idCard) {
            case 1: {
                toolParameter.setC1(pTParameter.getC1());
                toolParameter.setDieModified(pTParameter.getDieModified());
                toolParameter.setDp(draftPool);
                break;
            }
            case 2: {
                toolParameter.setWindow(currentPlayer.getWindowPattern());
                toolParameter.setC1(pTParameter.getC1());
                toolParameter.setD1(pTParameter.getD1());
                break;
            }
            case 3: {
                toolParameter.setWindow(currentPlayer.getWindowPattern());
                toolParameter.setC1(pTParameter.getC1());
                toolParameter.setD1(pTParameter.getD1());
                break;
            }
            case 4:{
                if(pTParameter.getPhase()==0){
                    toolParameter.setWindow(currentPlayer.getWindowPattern());
                    toolParameter.setC1(pTParameter.getC1());
                    toolParameter.setD1(pTParameter.getD1());
                    toolParameter.setPhase(pTParameter.getPhase());
                }
                else{
                    toolParameter.setWindow(currentPlayer.getWindowPattern());
                    toolParameter.setC2(pTParameter.getC2());
                    toolParameter.setD2(pTParameter.getD2());
                    toolParameter.setPhase(pTParameter.getPhase());
                }
                break;
            }
            case 5: {
                toolParameter.setC1(pTParameter.getC1());
                toolParameter.setD1(pTParameter.getD1());
                toolParameter.setRt(roundTrack);
                toolParameter.setDp(draftPool);
                toolParameter.setRound(pTParameter.getRound());
                break;
            }
            case 6: {
                toolParameter.setC1(pTParameter.getC1());
                toolParameter.setDp(draftPool);
                break;
            }
            case 7: {
                toolParameter.setListOfCoordinateY(pTParameter.getListOfCoordinateY());
                toolParameter.setDp(draftPool);
                break;
            }
            case 8: {
                toolParameter.setPlayer(currentPlayer);
                break;
            }
            case 9: {
                toolParameter.setWindow(currentPlayer.getWindowPattern());
                toolParameter.setC1(pTParameter.getC1());
                toolParameter.setD1(pTParameter.getD1());
                toolParameter.setDp(draftPool);
                break;
            }
            case 10: {
                toolParameter.setC1(pTParameter.getC1());
                toolParameter.setDp(draftPool);
                break;
            }
            case 11: {
                if(pTParameter.getPhase()==0){
                    toolParameter.setC1(pTParameter.getC1());
                    toolParameter.setWindow(currentPlayer.getWindowPattern());
                    toolParameter.setDp(draftPool);
                    toolParameter.setDiceBag(draftPool.getDiceBag());
                    toolParameter.setPhase(pTParameter.getPhase());
                }
                else if(pTParameter.getPhase() == 1){
                    toolParameter.setPhase(pTParameter.getPhase());
                    toolParameter.setDieModified(pTParameter.getDieModified());
                    toolParameter.setDp(draftPool);
                }
                else{
                    toolParameter.setC1(pTParameter.getC1());
                    toolParameter.setWindow(currentPlayer.getWindowPattern());
                    toolParameter.setDp(draftPool);
                    toolParameter.setPhase(pTParameter.getPhase());
                }
                break;
            }
            case 12: {
                if(pTParameter.getPhase() == 0){
                    toolParameter.setWindow(currentPlayer.getWindowPattern());
                    toolParameter.setRound(pTParameter.getRound());
                    toolParameter.setC1(pTParameter.getC1());
                    toolParameter.setRt(roundTrack);
                    toolParameter.setPhase(pTParameter.getPhase());
                }
                else if(pTParameter.getPhase()==1){
                    toolParameter.setWindow(currentPlayer.getWindowPattern());
                    toolParameter.setC1(pTParameter.getC1());
                    toolParameter.setD1(pTParameter.getD1());
                    toolParameter.setPhase(pTParameter.getPhase());
                }
                else{
                    toolParameter.setC1(pTParameter.getC1());
                    toolParameter.setD1(pTParameter.getD1());
                    toolParameter.setPhase(pTParameter.getPhase());
                    toolParameter.setWindow(currentPlayer.getWindowPattern());
                }
                break;
            }
            default:
                return null;
        }
        return toolParameter;
    }

    /**
     * This method allows the using of a match tool card
     * @param tool  identifier of the match tool card you want to use
     * @param pTParameter object that contains all necessary data for using the specified tool card
     * @return false if the tool card can't be used
     */
    public boolean playerUseTool(int tool, PlayerToolParameter pTParameter) {
        if(pTParameter==null){
            for(ToolCard t : tools) {
                if (t.getIdCard() == tool) {
                    if (t.isAlreadyUsed()) {
                        if(currentPlayer.getTokens()>1) {
                            if(t.getIdCard()==8){
                                pTParameter = new PlayerToolParameter();
                                ObjectiveTool toolParameter = createToolParameter(tool,pTParameter);
                                t.doToolStrategy(toolParameter);
                            }
                            currentPlayer.useToken(t);
                            t.setNumTokenUsed(t.getNumTokenUsed() + 2);
                        }
                        else
                            return false;
                    }else{
                        if(t.getIdCard()==8){
                            pTParameter = new PlayerToolParameter();
                            ObjectiveTool toolParameter = createToolParameter(tool,pTParameter);
                            t.doToolStrategy(toolParameter);
                        }
                        currentPlayer.useToken(t);
                        t.setNumTokenUsed(t.getNumTokenUsed() + 1);
                    }
                }
            }
            notifyViewObserver();
            return true;
        }
        ObjectiveTool toolParameter = createToolParameter(tool,pTParameter);
        if (toolParameter == null) return false;
        for(ToolCard t : tools){
            if(t.getIdCard()==tool){
                boolean b = t.doToolStrategy(toolParameter);
                notifyViewObserver();
                return b;
            }
        }
        return false;
    }

    /**
     * This method notifies all last changes in match to an observer for view side
     */
    public void notifyViewObserver() {
        viewObserver = new DraftPoolObserver();
        viewObserver.update(draftPool, ViewData.instance());
        viewObserver = new WindowPatternObserver();
        viewObserver.update(currentPlayer.getWindowPattern(), ViewData.instance());
        viewObserver = new RoundTrackObserver();
        viewObserver.update(roundTrack, ViewData.instance());
        viewObserver = new ToolCardsObserver();
        for(ToolCard tool : tools)
            viewObserver.update(tool,ViewData.instance());
        viewObserver = null;
    }

    /**
     * This method calculates and memorizes the score of all players
     */
    public void playersScore() {
        if(!calculatedScore){
            for (Player p : players) {
                pbCards.get(0).doPBStrategy(p);
                pbCards.get(1).doPBStrategy(p);
                pbCards.get(2).doPBStrategy(p);
                p.personalScore();
                if(p.getScore()<0) p.setScore(0);   //score can't be negative
            }
            calculatedScore=true;
        }
    }

    /**
     * This method finds a winner of the match
     * @return name of the winner
     */
    public Player findWinner() {
        ArrayList<Player> ties = new ArrayList<>();
        ArrayList<Player> tiesTmp = new ArrayList<>();
        Player winner = players.get(0);

        //search for player with most high score
        for (Player p : players) {
            if (p.getScore() > winner.getScore()) winner = p;
        }
        for (Player p : players) {
            if (p.getScore() == winner.getScore()) ties.add(p);
        }
        if (ties.size() == 1) return winner;

        //search for player in tie with most high score due to private objective cards
        for (Player p : ties) {
            if (p.getPVScore() > winner.getPVScore()) winner = p;
        }
        for (Player p : ties) {
            if (p.getPVScore() == winner.getPVScore()) tiesTmp.add(p);
        }
        if (ties.size() == 1) return winner;

        //search for player in tie with the greatest number of token
        for (Player p: tiesTmp){
            if (p.getTokens() > winner.getTokens()) winner = p;
        }
        ties = new ArrayList<>();
        for (Player p : tiesTmp) {
            if (p.getTokens() == winner.getTokens()) ties.add(p);
        }
        if (ties.size() == 1) return winner;

        //search for player in tie in lower position
        for (Player p: ties){
            if(players.indexOf(p) < players.indexOf(winner)) winner = p;
        }
        return winner;
    }

    /**
     * Simply getter method
     * @return the current round
     */
    public int getRound() {
        return roundTrack.getRound();
    }

    /**Author: Elio Salvini
     *
     * cli support
     */

    /**
     * @return list of strings that represents match public objective cards
     */
    public ArrayList<String> pbCardsToString(){
        ArrayList<String> cards = new ArrayList<>();
        for (PBObjectiveCard card: pbCards){
            cards.add(card.toString());
        }
        return cards;
    }

    /**
     * @return list of strings that represents match tool cards
     */
    public ArrayList<String> toolCardsToString(){
        ArrayList<String> cards = new ArrayList<>();
        for (ToolCard card: tools){
            cards.add(card.toStringCLI());
        }
        return cards;
    }
}
