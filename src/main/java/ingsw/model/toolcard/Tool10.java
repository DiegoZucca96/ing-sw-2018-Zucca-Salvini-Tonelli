package ingsw.model.toolcard;

import ingsw.model.Die;
import ingsw.model.DraftPool;
import ingsw.model.ObjectiveTool;

/**
 * Tool card number 10
 */
public class Tool10 implements ToolStrategy {
    private String title;
    private String comment;
    private boolean alreadyUsed;
    private int idCard;
    private Die die;
    private DraftPool dp;
    private int numTokenUsed;

    /**
     * Constructor
     * @param idCard it is the number of the tool card
     */
    public Tool10(int idCard) {
        this.title ="Tampone Diamantato";
        this.comment = "Dopo aver scelto un dado, giralo\n" + "sulla faccia opposta\n" + "6 diventa 1, 5 diventa 2, 4\n" + "diventa 3 ecc.";
        this.alreadyUsed=false;
        this.idCard=idCard;
    }

    /**
     * This card turns the selected die of the player on the opposite side.
     * @param object it contains all the informations to use correctly the tool card
     * @return true if everything goes right, otherwise false
     */
    public boolean doOp(ObjectiveTool object){
        dp = object.getDp();
        die = dp.getDie(object.getC1().getY());
        switch(die.getNumber()){
            case 1:{
                die.setNumber(6);
                return true;
            }
            case 2:{
                die.setNumber(5);
                return true;
            }
            case 3:{
                die.setNumber(4);
                return true;
            }
            case 4:{
                die.setNumber(3);
                return true;
            }
            case 5:{
                die.setNumber(2);
                return true;
            }
            case 6:{
                die.setNumber(1);
                return true;
            }
            default:{
                System.out.print("Dado non valido");
                break;
            }
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