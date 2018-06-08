package ingsw.model.toolcard;

import ingsw.model.*;
import ingsw.model.windowpattern.WindowPattern;

public class Tool12 implements ToolStrategy {
    private String title;
    private String comment;
    private boolean alreadyUsed;
    private int idCard;
    private WindowPattern window;
    private Die die1;
    private Die die2;
    private Coordinate c1;
    private Coordinate c2;
    private Color color;

    public Tool12(int idCard) {
        this.title ="Taglierina Manuale";
        this.comment = "Muovi fino a due dadi dello\n" + "stesso colore di un solo dado sul\n" + "Tracciato dei Round\n" + "Devi rispettare tutte le restrizioni\n" + "di piazzamento";
        this.alreadyUsed=false;
        this.idCard=idCard;
    }

    public boolean doOp(ObjectiveTool object){
        color = object.getColor();
        window = object.getWindow();
        Cell[][] cellMatrix = window.getCellMatrix();
        c1 = object.getC1();
        c2 = object.getC2();
        if(c1 != null) {
            if (cellMatrix[c1.getX()][c1.getY()].getDie().getColor() == color) {
                die1 = cellMatrix[c1.getX()][c1.getY()].takeDie();
                if(!window.addDie(object.getDestination1(),die1,cellMatrix)){
                    System.out.print("Hai violato una restrizione di posizione");
                    window.addDie(c1,die1,cellMatrix);
                }
            }
            else
                System.out.print("Il dado selezionato non è dello stesso colore del RoundTrack");
        }
        if(c2 != null) {
            if (cellMatrix[c2.getX()][c2.getY()].getDie().getColor() == color) {
                die2 = cellMatrix[c2.getX()][c2.getY()].takeDie();
                if(!window.addDie(object.getDestination2(), die2, cellMatrix)){
                    System.out.print("Hai violato una restrizione di posizione");
                    window.addDie(c2,die2,cellMatrix);
                }
            }
            else
                System.out.print("Il dado selezionato non è dello stesso colore del RoundTrack");
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
