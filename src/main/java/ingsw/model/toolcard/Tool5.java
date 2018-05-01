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
    //Il dado dalla roundtrack è già stato tolto ancora prima di chiamare l'uso della ToolCard, è passato come die2
    //Va a scambiare un dado dalla riserva con uno presente nel RoundTrack, indipendentemente dal round
    public void doOp(ObjectiveTool object){
        die1 = object.getDie1();
        die2 = object.getDie2();
        rt = object.getRt();
        dp = object.getDp();
       // rt.addDie(die1,indexRound); //Manca il riferimento al round da dove è stato preso il dado (forse aggiungere un attributo indice nel OT?)
       // dp.insertDie(die2);
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
