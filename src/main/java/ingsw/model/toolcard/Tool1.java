package ingsw.model.toolcard;

import ingsw.model.Coordinate;
import ingsw.model.Die;
import ingsw.model.ObjectiveTool;

/**
 * Tool card number 1
 */
public class Tool1 implements ToolStrategy {
    private String title;
    private String comment;
    private boolean alreadyUsed;
    private int idCard;
    private int numTokenUsed;

    /**
     * Constructor
     * @param idCard it is the number of the tool card
     */
    public Tool1(int idCard){
        this.title ="Pinza Sgrossatrice";
        this.comment = "Dopo aver scelto un dado,\n" + "aumenta o dominuisci il valore\n" + "del dado scelto di 1\n" + "Non puoi cambiare\n" + "un 6 in 1 o un 1 in 6";
        this.alreadyUsed=false;
        this.idCard=idCard;
    }

    /**
     * It modifies the selected die by increasing or decreasing its number by one
     * @param object it contains all the informations to use correctly the tool card
     * @return true if everything goes right, otherwise false
     */
    public boolean doOp(ObjectiveTool object){
        Coordinate c = object.getC1();
        Die die = object.getDp().getDie(c.getY());
        if(1 <= die.getNumber() && die.getNumber()<=6){
            die.setNumber(object.getDieModified());
            return true;
        }
        else
            System.out.println("Non puoi usare questa carta su questo dado");
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
