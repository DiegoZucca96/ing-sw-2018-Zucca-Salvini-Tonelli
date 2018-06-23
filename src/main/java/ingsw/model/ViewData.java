package ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

//Oggetto che porta le informazioni grafiche da model a view
public class ViewData implements Serializable {

    private ArrayList<ViewWP> wps;
    private ArrayList<String> pbCards;
    private ArrayList<String> toolCards;

    /** Author: Elio Salvini; support for CLI */
    private ArrayList<String> toolCardsCLI;     //per mantenere inalterato il campo toolCards al fine di evitare malfunzionameni
                                                //legati alla manipolazione di stringhe negli observer delle tool card
    /** end support for CLI*/

    private ArrayList<String> draftPoolDice;
    private ArrayList<String> roundTrack;
    private static ViewData instance;


    private ViewData() {
        wps = new ArrayList<>();
        pbCards = new ArrayList<>();
        toolCards = new ArrayList<>();
        draftPoolDice = new ArrayList<>();
        roundTrack = new ArrayList<>();
    }

    public static ViewData instance() {
        if (instance == null) instance = new ViewData();
        return instance;
    }

    public ArrayList<ViewWP> getWps() {
        return wps;
    }

    public void setWps(ArrayList<ViewWP> wps) {
        this.wps = wps;
    }

    public ArrayList<String> getPbCard() {
        return pbCards;
    }

    public ArrayList<String> getToolCard() {
        return toolCards;
    }

    public ArrayList<String>  getDraftPoolDice() {
        return draftPoolDice;
    }

    public void addWp(ViewWP wp){
        for(ViewWP x : wps){
            if(x.getNumberWP() == wp.getNumberWP())
                wps.set(wps.indexOf(x),wp);
        }
    }

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

    public void addDPDie(int i, String die){
        draftPoolDice.set(i,die);
    }

    public void setRoundTrack(ArrayList<String> roundTrack) {
        this.roundTrack = roundTrack;
    }

    public ArrayList<String> getRoundTrack() {
        return roundTrack;
    }

    /**Author: Elio Salvini
     *
     * CLI support
     */
    public void setToolCardsCLI(ArrayList<String> toolCards) {
        this.toolCardsCLI = toolCards;
    }

    public void setPbCards(ArrayList<String> pbCards) {
        this.pbCards = pbCards;
    }

    public ArrayList<String> getToolCardsCLI() {
        return toolCardsCLI;
    }
}
