package ingsw.model.toolcard;

import ingsw.model.*;
import java.util.Random;

/**
 * Tool card number 6
 */
public class Tool6 implements ToolStrategy {
    private String title;
    private String comment;
    private boolean alreadyUsed;
    private int idCard;
    private int numTokenUsed;

    /**
     * Constructor
     * @param idCard it is the number of the tool card
     */
    public Tool6(int idCard) {
        this.title ="Pennello per Pasta Salda";
        this.comment = "Dopo aver scelto un dado, tira\n" + "nuovamente quel dado\n" + "Se non puoi piazzarlo, riponilo\n" + "nella Riserva";
        this.alreadyUsed=false;
        this.idCard=idCard;
    }

    /**
     * This card changes the number of the selected die of the player.
     * @param object it contains all the informations to use correctly the tool card
     * @return true if everything goes right, otherwise false
     */
    public boolean doOp(ObjectiveTool object){
        DraftPool dp = object.getDp();
        Die die = dp.getDie(object.getC1().getY());
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

    /** Author: Elio Salvini
     *
     * CLI support
     */
    @Override
    public String toString() {
        return title +'\n' + comment + '\n';
    }
}
