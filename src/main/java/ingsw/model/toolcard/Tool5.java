package ingsw.model.toolcard;

import ingsw.model.Die;
import ingsw.model.DraftPool;
import ingsw.model.ObjectiveTool;
import ingsw.model.RoundTrack;

public class Tool5 implements ToolStrategy {
    private String title;
    private String comment;
    private boolean alreadyUsed;
    private int idCard;
    private Die die1;
    private Die die2;
    private RoundTrack rt;
    private DraftPool dp;

    public Tool5(int idCard) {
        this.title ="Taglierina circolare";
        this.comment = "Dopo aver scelto un dado,\n" + "scambia quel dado con un dado\n" + "sul Tracciato dei Round";
        this.alreadyUsed=false;
        this.idCard=idCard;
    }

    //Va a scambiare un dado dalla riserva con uno presente nel RoundTrack, indipendentemente dal round
    public void doOp(ObjectiveTool object){
        int indexRound =0, indexDieRound=0;
        die1 = object.getDie1();
        //die2 = object.getDie2(); //Sono inutili i dadi passati, qua mi servono gli indici passati dalla grafica per selezione
        rt = object.getRt();
        dp = object.getDp();
        die2 = rt.takeDie(indexRound,indexDieRound); //Non serve passare il dado perchè comunque devo rimuoverlo dalla RoundTrack
        dp.addDie(die2);
        rt.addDie(die1,indexRound);
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
}
