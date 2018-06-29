package ingsw.model.toolcard;

import ingsw.model.*;
import ingsw.model.windowpattern.WindowPattern;

/**
 * Tool card number 11
 */
public class Tool11 implements ToolStrategy {
    private String title;
    private String comment;
    private boolean alreadyUsed;
    private int idCard;
    private Die die;
    private DraftPool dp;
    private WindowPattern window;
    private DiceBag diceBag;
    private Coordinate c1;
    private int numTokenUsed;

    /**
     * Constructor
     * @param idCard it is the number of the tool card
     */
    public Tool11(int idCard) {
        this.title ="Diluente per Pasta Salda";
        this.comment = "Dopo aver scelto un dado, riponilo nel\n" + "Sacchetto, poi pescane uno dal Sacchetto\n" + "Scegli il valore del nuovo dado e\n" + "piazzalo, rispettando tutte le restrizioni di\n" + "piazzamento";
        this.alreadyUsed=false;
        this.idCard=idCard;
    }

    /**
     * This card allows the player to change his selected die with another one taken by DiceBag.
     * After that he can also change the number of the new die, before placing it comply with all placement restrictions.
     * @param object it contains all the informations to use correctly the tool card
     * @return true if everything goes right, otherwise false
     */
    public boolean doOp(ObjectiveTool object){
        if(object.getPhase() == 0){
            c1 = object.getC1();
            dp = object.getDp();
            diceBag = object.getDiceBag();
            die = dp.getDie(c1.getY());
            diceBag.insertDie(die);  //Method that adjusts the randomness with which the dice come out
            dp.takeDie(c1.getY());
            die = dp.reThrowsDie(c1.getY());
            return true;
        }else if(object.getPhase() == 1){
            die.setNumber(object.getDieModified());
            return true;
        }else if(object.getPhase() == 2){
            window = object.getWindow();
            window.addDie(object.getC1(),die,window.getCellMatrix());
            die = dp.takeDie(c1.getY());
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
