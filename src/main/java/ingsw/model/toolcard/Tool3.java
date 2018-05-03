package ingsw.model.toolcard;

import ingsw.model.Cell;
import ingsw.model.Die;
import ingsw.model.ObjectiveTool;
import ingsw.model.windowpattern.WindowPattern;

public class Tool3 implements ToolStrategy {
    private Die possibledie;
    private Die die;
    private WindowPattern window;
    private Cell[][] cellMatrix;
    private String title;
    private String comment;
    private boolean alreadyUsed;
    private int idCard;

    public Tool3(int idCard) {
        this.title ="Alesatore per lamina di rame";
        this.comment = "Muovi un qualsiasi dado nella tua\n" + "vetrata ignorando le restrizioni\n" + "di valore\n" + "Devi rispettare tutte le altre\n" + "restrizioni di piazzamento";
        this.alreadyUsed=false;
        this.idCard=idCard;
    }
    //Stessa identica cosa per la Tool2 solo che qua passo oltre la restrizione del numero e non del colore
    public void doOp(ObjectiveTool object){
        window = object.getWindow();
        cellMatrix = window.getCellMatrix();
        possibledie = cellMatrix[object.getC1().getX()][object.getC1().getY()].getDie();
        if(window.verifyDieColorConstraint(object.getDestination1(),possibledie,cellMatrix) && window.verifyDieNumberConstraint(object.getDestination1(),possibledie,cellMatrix) && window.verifyPosition(object.getDestination1(),cellMatrix)){
            die = cellMatrix[object.getC1().getX()][object.getC1().getY()].takeDie();
            cellMatrix[object.getDestination1().getX()][object.getDestination1().getY()].insertDie(die);
        }
        else
            System.out.println("Posizione di destinazione non corretta");
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
}
