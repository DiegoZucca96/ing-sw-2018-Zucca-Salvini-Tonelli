package ingsw.model.toolcard;

import ingsw.model.Cell;
import ingsw.model.Coordinate;
import ingsw.model.Die;
import ingsw.model.ObjectiveTool;
import ingsw.model.windowpattern.WindowPattern;

public class Tool4 implements ToolStrategy {
    private String title;
    private String comment;
    private boolean alreadyUsed;
    private int idCard;
    private WindowPattern window;
    private Cell[][] cellMatrix;
    private Die die1;
    private Die die2;

    public Tool4(int idCard) {
        this.title ="Lathekin";
        this.comment = "Muovi esattamente due dadi,\n" + "rispettando tutte le restrizioni di\n" + "piazzamento";
        this.alreadyUsed=false;
        this.idCard=idCard;
    }
//Prende due dadi e li sposta rispettando le restrizioni, se non può torna tutto come prima
    public void doOp(ObjectiveTool object){
        window = object.getWindow();
        cellMatrix = window.getCellMatrix();
        die1 = cellMatrix[object.getC1().getX()][object.getC1().getY()].takeDie();
        if(!(window.addDie(object.getDestination1(),die1,cellMatrix))) { //Qui sarebbe da sfruttare le exception per il tipo di violazione
            cellMatrix[object.getC1().getX()][object.getC1().getY()].insertDie(die1);
            System.out.print("Hai violato qualche restrizione, non puoi usare questa ToolCard");
        }else{
            die2 = cellMatrix[object.getC2().getX()][object.getC2().getY()].takeDie();
            if(!(window.addDie(object.getDestination2(),die2,cellMatrix))){
                window.removeDie(object.getDestination1(),cellMatrix);
                cellMatrix[object.getC1().getX()][object.getC1().getY()].insertDie(die1);
                cellMatrix[object.getC2().getX()][object.getC2().getY()].insertDie(die2);
                System.out.print("Hai violato qualche restrizione, non puoi usare questa ToolCard");
            }
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
}
