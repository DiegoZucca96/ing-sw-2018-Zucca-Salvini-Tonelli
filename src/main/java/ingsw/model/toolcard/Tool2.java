package ingsw.model.toolcard;

import ingsw.model.Cell;
import ingsw.model.Die;
import ingsw.model.ObjectiveTool;
import ingsw.model.windowpattern.WindowPattern;

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
