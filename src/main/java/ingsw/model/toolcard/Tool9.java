package ingsw.model.toolcard;

import ingsw.model.Cell;
import ingsw.model.Coordinate;
import ingsw.model.Die;
import ingsw.model.ObjectiveTool;
import ingsw.model.windowpattern.WindowPattern;

public class Tool9 implements ToolStrategy{
    private String title;
    private String comment;
    private boolean alreadyUsed;
    private int idCard;
    private Die die;
    private WindowPattern window;
    private Coordinate d1;

    public Tool9(int idCard) {
        this.title ="Riga in Sughero";
        this.comment = "Dopo aver scelto un dado,\n" + "piazzalo in una casella che non\n" + "sia adiacente a un altro dado\n" + "Devi rispettare tutte le restrizioni\n" + "di piazzamento";
        this.alreadyUsed=false;
        this.idCard=idCard;
    }

    public void doOp(ObjectiveTool object){
       die = object.getDie1();
       window = object.getWindow();
       d1 = object.getDestination1();
       Cell[][] cellMatrix = window.getCellMatrix();
       if(window.verifyDieNumberConstraint(d1,die,cellMatrix) && window.verifyDieColorConstraint(d1,die,cellMatrix) && window.verifyCellNumberConstraint(d1,die,cellMatrix) && window.verifyCellColorConstraint(d1,die,cellMatrix))
           cellMatrix[d1.getX()][d1.getY()].insertDie(die);
       else
           System.out.print("Non puoi posizionare qui questo dado");
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
