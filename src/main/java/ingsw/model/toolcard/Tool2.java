package ingsw.model.toolcard;

import ingsw.model.Cell;
import ingsw.model.Die;
import ingsw.model.ObjectiveTool;
import ingsw.model.windowpattern.WindowPattern;
import ingsw.view.PlayGame;

//In UML
public class Tool2 implements ToolStrategy {
    private Die possibledie;
    private Die die;
    private WindowPattern window;
    private Cell[][] cellMatrix;
    private String title;
    private String comment;
    private boolean alreadyUsed;
    private int idCard;

    public Tool2(int idCard){
        this.title ="Pennello per Eglomise";
        this.comment = "Muovi un qualsiasi dado nella tua\n" + "vetrata ignorando le restrizioni\n" + "di colore\n" + "Devi rispettare tutte le altre\n" + "restrizioni di piazzamento";
        this.alreadyUsed=false;
        this.idCard=idCard;
    }

    //Prima di estrarre davvero il dado, ne prendo il riferimento, verifico che posso metterlo in destination1 e poi lo inserisco rimuovendolo
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
}
