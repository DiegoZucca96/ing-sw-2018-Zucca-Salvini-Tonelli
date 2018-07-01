package ingsw.model.toolcard;

import ingsw.model.*;
import ingsw.model.windowpattern.WindowPattern;

/**
 * Author : Diego Zucca - Alessio Tonelli
 *
 * Tool card number 9
 */
public class Tool9 implements ToolStrategy{
    private String title;
    private String comment;
    private boolean alreadyUsed;
    private int numTokenUsed;
    private int idCard;
    private DraftPool dp;
    private WindowPattern window;
    private Coordinate d1;
    private Coordinate c1;

    /**
     * Constructor
     * @param idCard it is the number of the tool card
     */
    public Tool9(int idCard) {
        this.title ="Riga in Sughero";
        this.comment = "Dopo aver scelto un dado,\n" + "piazzalo in una casella che non\n" + "sia adiacente a un altro dado\n" + "Devi rispettare tutte le restrizioni\n" + "di piazzamento";
        this.alreadyUsed=false;
        this.idCard=idCard;
    }

    /**
     * It allows the player to place a dice in his window while ignoring the adjacency restriction.
     * However, the player must comply with all other placement restrictions.
     * @param object it contains all the informations to use correctly the tool card
     * @return true if everything goes right, otherwise false
     */
    public boolean doOp(ObjectiveTool object){
       dp = object.getDp();
       window = object.getWindow();
       d1 = object.getD1();
       c1 = object.getC1();
       Die die = dp.getDie(c1.getY());
       Cell[][] cellMatrix = window.getCellMatrix();
       if(window.verifyDieNumberConstraint(d1,die,cellMatrix) && window.verifyDieColorConstraint(d1,die,cellMatrix) && window.verifyCellNumberConstraint(d1,die,cellMatrix) && window.verifyCellColorConstraint(d1,die,cellMatrix)){
           if(window.isWpEmpty())
               window.setWpEmpty(false);
           return cellMatrix[d1.getX()][d1.getY()].insertDie(dp.takeDie(c1.getY()));
       }
       else{
           System.out.print("Non puoi posizionare qui questo dado");
           return false;
        }
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
