package ingsw.model.toolcard;

import ingsw.model.Cell;
import ingsw.model.Die;

//In UML
public class Tool2 implements ToolStrategy {
    private Die die;
    private Cell destinationCell;
    private String title;
    private String comment;
    private boolean alreadyUsed;

    public Tool2(){
        this.title ="Pennello per Eglomise";
        this.comment = "Muovi un qualsiasi dado nella tua\n" + "vetrata ignorando le restrizioni\n" + "di colore\n" + "Devi rispettare tutte le altre\n" + "restrizioni di piazzamento";
        this.alreadyUsed=false;
    }

    public void doOp(Die die){
        //System.out.println("Sono la carta 2");
        die = getCell(getInputCoordinate()).takeDie();
        //Come differenzio la scelta della cella? Serve una sorta di "wait selection"
        destinationCell = getCell(getInputCoordinate());
        positionDie(die, destinationCell.getCoordinate()); //Prova di import, però statico
    }

    public boolean isAlreadyUsed(){
        return alreadyUsed;
    }
}
