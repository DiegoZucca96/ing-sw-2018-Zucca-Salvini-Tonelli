package ingsw.model.toolcard;

import ingsw.model.Coordinate;
import ingsw.model.Die;
import ingsw.model.ObjectiveTool;
import ingsw.view.PlayGame;

public class Tool1 implements ToolStrategy {
    private String title;
    private String comment;
    private boolean alreadyUsed;
    private int idCard;
    private Die die;
    private int numTokenUsed;

    public Tool1(int idCard){
        this.title ="Pinza Sgrossatrice";
        this.comment = "Dopo aver scelto un dado,\n" + "aumenta o dominuisci il valore\n" + "del dado scelto di 1\n" + "Non puoi cambiare\n" + "un 6 in 1 o un 1 in 6";
        this.alreadyUsed=false;
        this.idCard=idCard;
    }
    //Modifica il numero del dado scelto di 1 aumentandolo o diminuendolo in base al valore di getUp()
    public boolean doOp(ObjectiveTool object){
        Coordinate c = object.getC1();
        die = object.getDp().getDie(c.getY());
        if(1 <= die.getNumber() && die.getNumber()<=6){
            die.setNumber(object.getDieModified());
            PlayGame.setUsingTool(false);
            return true;
        }
        else
            System.out.println("Non puoi usare questa carta su questo dado");
        PlayGame.setUsingTool(false);
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
