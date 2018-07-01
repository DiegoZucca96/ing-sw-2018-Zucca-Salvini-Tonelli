package ingsw.model.toolcard;

import ingsw.model.Coordinate;
import ingsw.model.DraftPool;
import ingsw.model.ObjectiveTool;
import java.util.ArrayList;

/**
 * Author : Diego Zucca - Alessio Tonelli
 *
 * Tool card number 7
 */
public class Tool7 implements ToolStrategy {
    private String title;
    private String comment;
    private boolean alreadyUsed;
    private int idCard;
    private int numTokenUsed;

    /**
     * Constructor
     * @param idCard it is the number of the tool card
     */
    public Tool7(int idCard) {
        this.title ="Martelletto";
        this.comment = "Tira nuovamente\n" + "tutti i dadi della Riserva\n" + "Questa carta può essera usata\n" + "solo durante il tuo secondo turno,\n" + "prima di scegliere il secondo dado";
        this.alreadyUsed=false;
        this.idCard=idCard;
    }

    /**
     * This card changes all dice of the DraftPool.
     * @param object it contains all the informations to use correctly the tool card
     * @return true if everything goes right, otherwise false
     */
    public boolean doOp(ObjectiveTool object){
       if((object!=null)){
           ArrayList<Coordinate> coordinates = object.getListOfCoordinateY();
           DraftPool dp = object.getDp();
           dp.refreshDraftPool(coordinates);
           return true;
       }
        return false;
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
