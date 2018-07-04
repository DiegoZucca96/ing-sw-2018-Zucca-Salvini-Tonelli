package ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Author : Diego Zucca - Alessio Tonelli
 *
 * This class brings the graphic informations from model to view
 */
public class ViewData implements Serializable {

    private ArrayList<ViewWP> wps;
    private ArrayList<String> pbCards;
    private ArrayList<String> toolCards;

    /** Author: Elio Salvini; support for cli */
    /* this keeps the toolCards field unchanged in order to avoid malfunctions related
       to the manipulation of strings in the tool card observers*/
    private ArrayList<String> toolCardsCLI;
    private ArrayList<String> pbCardsCLI;
    /** end support for cli*/

    private ArrayList<String> draftPoolDice;
    private ArrayList<String> roundTrack;
    private static ViewData instance;

    /**
     * Constructor, is private because is a Singleton
     */
    private ViewData() {
        wps = new ArrayList<>();
        pbCards = new ArrayList<>();
        toolCards = new ArrayList<>();
        draftPoolDice = new ArrayList<>();
        roundTrack = new ArrayList<>();
    }

    /**
     * Creates an instance of ViewData or simply returns the instance
     * @return ViewData instance
     */
    public static ViewData instance() {
        if (instance == null) instance = new ViewData();
        return instance;
    }

    /**
     * Simply getter method
     * @return the list of windows of the match
     */
    public ArrayList<ViewWP> getWps() {
        return wps;
    }

    /**
     * Simply setter method
     * @param wps it is the list of the windows that has to be assign
     */
    public void setWps(ArrayList<ViewWP> wps) {
        this.wps = wps;
    }

    /**
     * Simply getter method
     * @return the list of public cards of the match
     */
    public ArrayList<String> getPbCard() {
        return pbCards;
    }

    /**
     * Simply getter method
     * @return the list of tool cards of the match
     */
    public ArrayList<String> getToolCard() {
        return toolCards;
    }

    /**
     * Simply getter method
     * @return the list of the DraftPool's dice of the match
     */
    public ArrayList<String>  getDraftPoolDice() {
        return draftPoolDice;
    }

    /**
     * Setter method, it updates the wp window
     * @param wp it is the window that has to be set
     */
    public void addWp(ViewWP wp){
        for(ViewWP x : wps){
            if(x.getNumberWP() == wp.getNumberWP())
                wps.set(wps.indexOf(x),wp);
        }
    }

    /**
     * Setter method, it updates the tool cards
     * @param toolCard it is the tool card that has to be set
     */
    public void setToolCard(String toolCard){
        String substring = toolCard.substring(0, toolCard.indexOf("+"));
        for(String tool : toolCards){
            String substringTool = tool.substring(0, tool.indexOf("+"));
            if(substring.equalsIgnoreCase(substringTool)){
                int index = toolCards.indexOf(tool);
                toolCards.set(index,toolCard);
            }
        }
    }

    /**
     * Setter method, it updates the DraftPool's dice
     * @param i it is the position to update
     * @param die it is the die that has to be set
     */
    public void addDPDie(int i, String die){
        draftPoolDice.set(i,die);
    }

    /**
     * Simply setter method
     * @param roundTrack it is the list of the RoundTrack's dice that has to be assign
     */
    public void setRoundTrack(ArrayList<String> roundTrack) {
        this.roundTrack = roundTrack;
    }

    /**
     * Simply getter method
     * @return the list of tool cards of the match
     */
    public ArrayList<String> getRoundTrack() {
        return roundTrack;
    }

    /**Author: Elio Salvini
     *
     * ???
     * cli support
     */
    public void setToolCardsCLI(ArrayList<String> toolCards) {
        this.toolCardsCLI = toolCards;
    }

    public void setPbCardsCLI(ArrayList<String> pbCards) {
        this.pbCardsCLI = pbCards;
    }

    public ArrayList<String> getToolCardsCLI() {
        return toolCardsCLI;
    }

    public ArrayList<String> getPbCardsCLI() {
        return pbCardsCLI;
    }
}
