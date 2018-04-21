package ToolCard;

import ingsw.Cell;
import ingsw.Die;
import ingsw.ToolCard;
import ingsw.ToolStrategy;

import static ingsw.Player.*;

//In UML
public class Tool2 implements ToolStrategy {
    private Die die;
    private Cell destinationCell;
    private String title;
    private String comment;

    public Tool2(){
        this.title ="Pennello per Eglomise";
        this.comment = "Muovi un qualsiasi dado nella tua\n" + "vetrata ignorando le restrizioni\n" + "di colore\n" + "Devi rispettare tutte le altre\n" + "restrizioni di piazzamento"
    }
    @Override
    public void doOp(){
        //System.out.println("Sono la carta 2");
        die = getCell(getInputCoordinate()).takeDie();
        //Come differenzio la scelta della cella? Serve una sorta di "wait selection"
        destinationCell = getCell(getInputCoordinate());
        positionDie(die, destinationCell.getCoordinate()); //Prova di import, però statico
    }
}
