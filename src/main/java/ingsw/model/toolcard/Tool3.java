package ingsw.model.toolcard;

import ingsw.model.Cell;
import ingsw.model.Die;
import ingsw.model.ObjectiveTool;
import ingsw.model.windowpattern.WindowPattern;
import ingsw.view.PlayGame;

public class Tool3 implements ToolStrategy {
    private Die possibledie;
    private Die die;
    private WindowPattern window;
    private Cell[][] cellMatrix;
    private String title;
    private String comment;
    private boolean alreadyUsed;
    private int idCard;
    private int numTokenUsed;

    public Tool3(int idCard) {
        this.title ="Alesatore per lamina di rame";
        this.comment = "Muovi un qualsiasi dado nella tua\n" + "vetrata ignorando le restrizioni\n" + "di valore\n" + "Devi rispettare tutte le altre\n" + "restrizioni di piazzamento";
        this.alreadyUsed=false;
        this.idCard=idCard;
    }
    //Stessa identica cosa per la Tool2 solo che qua passo oltre la restrizione del numero e non del colore
    public boolean doOp(ObjectiveTool object){
        window = object.getWindow();
        cellMatrix = window.getCellMatrix();
        possibledie = cellMatrix[object.getC1().getX()][object.getC1().getY()].takeDie();
        if(window.verifyDieColorConstraint(object.getD1(),possibledie,cellMatrix) && window.verifyDieNumberConstraint(object.getD1(),possibledie,cellMatrix) && window.verifyPosition(object.getD1(),cellMatrix)){
            cellMatrix[object.getD1().getX()][object.getD1().getY()].insertDie(possibledie);
            PlayGame.setUsingTool(false);
            return true;
        }
        else{
            cellMatrix[object.getC1().getX()][object.getC1().getY()].insertDie(possibledie);
            System.out.println("Posizione di destinazione non corretta");
            PlayGame.setUsingTool(false);
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
}
