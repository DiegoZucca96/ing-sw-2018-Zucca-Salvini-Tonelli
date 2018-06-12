package ingsw.model.toolcard;

import ingsw.model.*;
import ingsw.model.windowpattern.WindowPattern;
import ingsw.view.PlayGame;

import java.util.Random;

public class Tool6 implements ToolStrategy {
    private String title;
    private String comment;
    private boolean alreadyUsed;
    private int idCard;
    private Die die;
    private DraftPool dp;
    private int numTokenUsed;

    public Tool6(int idCard) {
        this.title ="Pennello per Pasta Salda";
        this.comment = "Dopo aver scelto un dado, tira\n" + "nuovamente quel dado\n" + "Se non puoi piazzarlo, riponilo\n" + "nella Riserva";
        this.alreadyUsed=false;
        this.idCard=idCard;
    }
//Tiro di nuovo un dado, poi verifico se non posso inserirlo allora lo rimetto nella riserva
    public boolean doOp(ObjectiveTool object){
        dp = object.getDp();
        die = dp.getDie(object.getC1().getY());
        Random r = new Random();
        die.setNumber(r.nextInt(6)+1);
        PlayGame.setUsingTool(false);
        return true;
        /*if(!isInsertable(object.getWindow(),die)){  //Metodo che controlla se ci sono possibili inserimenti
            dp = object.getDp();
            //dp.addDie(die);
        }
        //Manca un pezzo in cui si chiede al player dove vuole inserire il dado se può inserirlo
        return false;*/
    }

    public boolean isInsertable(WindowPattern window, Die die){
        for(int i=0; i<4;i++){
            for(int j=0; j<5; j++){
                Coordinate coordinate = new Coordinate(i,j);
                if( window.getCellMatrix()[i][j].isEmpty() && window.verifyDieColorConstraint(coordinate,die,window.getCellMatrix()) && window.verifyDieNumberConstraint(coordinate,die,window.getCellMatrix()) &&window.verifyCellColorConstraint(coordinate,die,window.getCellMatrix()) && window.verifyCellNumberConstraint(coordinate,die,window.getCellMatrix()) && window.verifyPosition(coordinate,window.getCellMatrix())){
                    return true;
                }
            }
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

    public void setNumTokenUsed(int token) {
        this.numTokenUsed=token;
    }

    public int getNumTokenUsed() {
        return numTokenUsed;
    }
}
