package ingsw.model.toolcard;

import ingsw.model.Cell;
import ingsw.model.Die;
import ingsw.model.ObjectiveTool;
import ingsw.model.windowpattern.WindowPattern;

/**
 * Author : Diego Zucca - Alessio Tonelli
 *
 * Tool card number 2
 */
public class Tool2 implements ToolStrategy {
    private String title;
    private String comment;
    private boolean alreadyUsed;
    private int idCard;
    private int numTokenUsed;

    /**
     * Constructor
     * @param idCard it is the number of the tool card
     */
    public Tool2(int idCard){
        this.title ="Pennello per Eglomise";
        this.comment = "Muovi un qualsiasi dado nella tua\n" + "vetrata ignorando le restrizioni\n" + "di colore\n" + "Devi rispettare tutte le altre\n" + "restrizioni di piazzamento";
        this.alreadyUsed=false;
        this.idCard=idCard;
    }

    /**
     * It allows the player to move any dice in his window while ignoring the color restrictions.
     * However, the player must comply with all other placement restrictions.
     * If something goes wrong, the die will be repositioned in its original position
     * @param object it contains all the informations to use correctly the tool card
     * @return true if everything goes right, otherwise false
     */
    public boolean doOp(ObjectiveTool object){
        WindowPattern window = object.getWindow();
        Cell [][] cellMatrix = window.getCellMatrix();
        Die possibleDie = cellMatrix[object.getC1().getX()][object.getC1().getY()].takeDie();
        if(window.verifyDieColorConstraint(object.getD1(),possibleDie,cellMatrix) && window.verifyDieNumberConstraint(object.getD1(),possibleDie,cellMatrix) && window.verifyPosition(object.getD1(),cellMatrix)){
            cellMatrix[object.getD1().getX()][object.getD1().getY()].insertDie(possibleDie);
            return true;
        }
        else{
            cellMatrix[object.getC1().getX()][object.getC1().getY()].insertDie(possibleDie);
            System.out.println("Posizione di destinazione non corretta");
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
     * cli support
     */
    @Override
    public String toString() {
        return title +'\n' + comment + '\n';
    }
}
