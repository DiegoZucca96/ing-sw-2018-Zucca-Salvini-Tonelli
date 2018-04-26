package ingsw.model.toolcard;

import ingsw.model.Cell;
import ingsw.model.Die;
import ingsw.model.ObjectiveTool;

//In UML
public class Tool2 implements ToolStrategy {
    private Die die;
    private Cell destinationCell;
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

    public void doOp(ObjectiveTool object){
        //System.out.println("Sono la carta 2");
        die = getCell(getInputCoordinate()).takeDie();
        //Come differenzio la scelta della cella? Serve una sorta di "wait selection"
        destinationCell = getCell(getInputCoordinate());
        positionDie(die, destinationCell.getCoordinate()); //Prova di import, però statico
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
