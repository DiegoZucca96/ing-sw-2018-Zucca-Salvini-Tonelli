package ingsw.model.toolcard;

import ingsw.model.*;
import ingsw.model.windowpattern.WindowPattern;

import java.util.Random;

public class Tool6 implements ToolStrategy {
    private String title;
    private String comment;
    private boolean alreadyUsed;
    private int idCard;
    private Die die;
    private DraftPool dp;
    private int numTokenUsed;

    public Tool6(int idCard) {
        this.title ="Pennello per Pasta Salda";
        this.comment = "Dopo aver scelto un dado, tira\n" + "nuovamente quel dado\n" + "Se non puoi piazzarlo, riponilo\n" + "nella Riserva";
        this.alreadyUsed=false;
        this.idCard=idCard;
    }
//Tiro di nuovo un dado, poi verifico se non posso inserirlo allora lo rimetto nella riserva
    public boolean doOp(ObjectiveTool object){
        dp = object.getDp();
        die = dp.getDie(object.getC1().getY());
        Random r = new Random();
        die.setNumber(r.nextInt(6)+1);
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
