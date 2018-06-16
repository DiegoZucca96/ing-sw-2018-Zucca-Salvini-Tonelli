package ingsw.model.toolcard;

import ingsw.model.*;

public class Tool8 implements ToolStrategy {
    private String title;
    private String comment;
    private boolean alreadyUsed;
    private int idCard;
    private int numTokenUsed;


    public Tool8(int idCard) {
        this.title ="Tenaglia a Rotelle";
        this.comment = "Dopo il tuo primo turno scegli\n" + "immediatamente un altro dado\n" + "Salta il tuo secondo turno in\n" + "questo round";
        this.alreadyUsed=false;
        this.idCard=idCard;
    }

    public boolean doOp(ObjectiveTool object){
        object.getPlayer().setInsertedDie(false);
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