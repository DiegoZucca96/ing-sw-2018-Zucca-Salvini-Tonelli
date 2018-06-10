package ingsw.model.toolcard;

import ingsw.model.*;
import ingsw.model.windowpattern.WindowPattern;
import ingsw.view.PlayGame;

public class Tool9 implements ToolStrategy{
    private String title;
    private String comment;
    private boolean alreadyUsed;
    private int idCard;
    private DraftPool dp;
    private WindowPattern window;
    private Coordinate d1;
    private Coordinate c1;


    public Tool9(int idCard) {
        this.title ="Riga in Sughero";
        this.comment = "Dopo aver scelto un dado,\n" + "piazzalo in una casella che non\n" + "sia adiacente a un altro dado\n" + "Devi rispettare tutte le restrizioni\n" + "di piazzamento";
        this.alreadyUsed=false;
        this.idCard=idCard;
    }

    public boolean doOp(ObjectiveTool object){
       dp = object.getDp();
       window = object.getWindow();
       d1 = object.getD1();
       c1 = object.getC1();
       Die die = dp.getDie(c1.getY());
       Cell[][] cellMatrix = window.getCellMatrix();
       if(window.verifyDieNumberConstraint(d1,die,cellMatrix) && window.verifyDieColorConstraint(d1,die,cellMatrix) && window.verifyCellNumberConstraint(d1,die,cellMatrix) && window.verifyCellColorConstraint(d1,die,cellMatrix)){
           if(window.isWpEmpty())
               window.setWpEmpty(false);
           PlayGame.setUsingTool(false);
           return cellMatrix[d1.getX()][d1.getY()].insertDie(dp.takeDie(c1.getY()));
       }
       else{
           System.out.print("Non puoi posizionare qui questo dado");
           PlayGame.setUsingTool(false);
           return false;
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
