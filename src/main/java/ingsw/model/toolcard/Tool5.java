package ingsw.model.toolcard;

import ingsw.model.*;

public class Tool5 implements ToolStrategy {
    private String title;
    private String comment;
    private boolean alreadyUsed;
    private int idCard;
    private Die die1;
    private Die die2;
    private RoundTrack rt;
    private DraftPool dp;
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
        rt = object.getRt();
        dp = object.getDp();
        die1 = dp.takeDie(dpCoordinate.getY());
        die2 = rt.replaceDie(object.getRound(),rtCoordinate.getY(),die1);
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
}
