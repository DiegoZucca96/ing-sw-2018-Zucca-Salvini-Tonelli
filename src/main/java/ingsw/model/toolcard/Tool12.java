package ingsw.model.toolcard;

import ingsw.model.*;
import ingsw.model.windowpattern.WindowPattern;

/**
 * Author : Diego Zucca - Alessio Tonelli
 *
 * Tool card number 12
 */
public class Tool12 implements ToolStrategy {
    private String title;
    private String comment;
    private boolean alreadyUsed;
    private int idCard;
    private RoundTrack roundTrack;
    private WindowPattern window;
    private Die die1;
    private Die die2;
    private Coordinate c1;
    private Coordinate c2;
    private Color color;
    private int round;
    private Die die;
    private int numTokenUsed;

    /**
     * Constructor
     * @param idCard it is the number of the tool card
     */
    public Tool12(int idCard) {
        this.title ="Taglierina Manuale";
        this.comment = "Muovi fino a due dadi dello\n" + "stesso colore di un solo dado sul\n" + "Tracciato dei Round\n" + "Devi rispettare tutte le restrizioni\n" + "di piazzamento";
        this.alreadyUsed=false;
        this.idCard=idCard;
    }

    /**
     * It allows the player to move up to two dice in his window, but they must have the same color as a dice on the RoundTrack.
     * However, the player must comply with all placement restrictions.
     * If something goes wrong, both dice will be repositioned in their original positions.
     * @param object it contains all the informations to use correctly the tool card
     * @return true if everything goes right, otherwise false
     */
    public boolean doOp(ObjectiveTool object){
        if(object.getPhase() == 0) {
            window = object.getWindow();
            round = object.getRound();
            roundTrack = object.getRt();
            die = roundTrack.getDie(round, object.getC1().getY());
            color = die.getColor();
        }
        if(object.getPhase() == 1) {
            c1 = object.getC1();
            if (window.getCellMatrix()[c1.getX()][c1.getY()].getDie().getColor() == color) {
                die1 = window.getCellMatrix()[c1.getX()][c1.getY()].takeDie();
                if(!window.addDie(object.getD1(),die1,window.getCellMatrix())){
                    window.getCellMatrix()[object.getC1().getX()][object.getC1().getY()].insertDie(die1);
                    System.out.print("Hai violato una restrizione di posizione");
                    return false;
                }
            }
            else{
                System.out.print("Il dado selezionato non è dello stesso colore del RoundTrack");
                return false;
            }
        }
        if(object.getPhase() == 2){
            c2 = object.getC1();
            if (window.getCellMatrix()[c2.getX()][c2.getY()].getDie().getColor() == color) {
                die2 = window.getCellMatrix()[c2.getX()][c2.getY()].takeDie();
                if(!window.addDie(object.getD1(), die2, window.getCellMatrix())){
                    window.getCellMatrix()[c2.getX()][c2.getY()].insertDie(die2);
                    System.out.print("Hai violato una restrizione di posizione");
                    return false;
                }
            }
            else{
                System.out.print("Il dado selezionato non è dello stesso colore del RoundTrack");
                return false;
            }
        }
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
