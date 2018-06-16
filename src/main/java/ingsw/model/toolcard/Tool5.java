package ingsw.model.toolcard;

import ingsw.model.*;

public class Tool5 implements ToolStrategy {
    private String title;
    private String comment;
    private boolean alreadyUsed;
    private int idCard;
    private int numTokenUsed;

    public Tool5(int idCard) {
        this.title ="Taglierina circolare";
        this.comment = "Dopo aver scelto un dado,\n" + "scambia quel dado con un dado\n" + "sul Tracciato dei Round";
        this.alreadyUsed=false;
        this.idCard=idCard;
    }

    //Va a scambiare un dado dalla riserva con uno presente nel RoundTrack, indipendentemente dal round
    public boolean doOp(ObjectiveTool object){
        Coordinate dpCoordinate = object.getC1();
        Coordinate rtCoordinate = object.getD1();
        RoundTrack rt = object.getRt();
        DraftPool dp = object.getDp();
        Die die1 = dp.takeDie(dpCoordinate.getY());
        Die die2 = rt.replaceDie(object.getRound(),rtCoordinate.getY(),die1);
        dp.setDie(dpCoordinate.getY(),die2);
        return true;
    }

    public int getIdCard() {
        return idCard;
    }

    public boolean isAlreadyUsed(){
        return alreadyUsed;
    }

    public void setAlreadyUsed(boolean alreadyUsed) {
        this.alreadyUsed = alreadyUsed;
    }

    public void setNumTokenUsed(int token) {
        this.numTokenUsed=token;
    }

    public int getNumTokenUsed() {
        return numTokenUsed;
    }

    /** Author: Elio Salvini
     *
     * CLI support
     */
    @Override
    public String toString() {
        return title +'\n' + comment + '\n';
    }
}
