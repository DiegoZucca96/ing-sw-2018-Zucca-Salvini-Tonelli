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
    private int phase;
    private int numTokenUsed;
    private Coordinate c1;
    private Coordinate d1;

    public Tool4(int idCard) {
        this.title ="Lathekin";
        this.comment = "Muovi esattamente due dadi,\n" + "rispettando tutte le restrizioni di\n" + "piazzamento";
        this.alreadyUsed=false;
        this.idCard=idCard;
    }
//Prende due dadi e li sposta rispettando le restrizioni, se non può torna tutto come prima
    public boolean doOp(ObjectiveTool object){
        window = object.getWindow();
        cellMatrix = window.getCellMatrix();
        phase = object.getPhase();
        if(phase==0){
            c1=object.getC1();
            d1=object.getD1();
            die1 = cellMatrix[c1.getX()][c1.getY()].takeDie();
            if(!(window.addDie(d1,die1,cellMatrix))) { //Qui sarebbe da sfruttare le exception per il tipo di violazione
                cellMatrix[c1.getX()][c1.getY()].insertDie(die1);
                System.out.print("Hai violato qualche restrizione, non puoi usare questa ToolCard");
                return false;
            }
        }else{
            die2 = cellMatrix[object.getC2().getX()][object.getC2().getY()].takeDie();
            if(!(window.addDie(object.getD2(),die2,cellMatrix))){
                window.removeDie(d1,cellMatrix);
                cellMatrix[c1.getX()][c1.getY()].insertDie(die1);
                cellMatrix[object.getC2().getX()][object.getC2().getY()].insertDie(die2);
                System.out.print("Hai violato qualche restrizione, non puoi usare questa ToolCard");
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
}
