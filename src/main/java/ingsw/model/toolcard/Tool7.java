package ingsw.model.toolcard;

import ingsw.model.Coordinate;
import ingsw.model.DraftPool;
import ingsw.model.ObjectiveTool;
import java.util.ArrayList;

public class Tool7 implements ToolStrategy {
    private String title;
    private String comment;
    private boolean alreadyUsed;
    private int idCard;
    private int numTokenUsed;

    public Tool7(int idCard) {
        this.title ="Martelletto";
        this.comment = "Tira nuovamente\n" + "tutti i dadi della Riserva\n" + "Questa carta può essera usata\n" + "solo durante il tuo secondo turno,\n" + "prima di scegliere il secondo dado";
        this.alreadyUsed=false;
        this.idCard=idCard;
    }

    //La carta specifica prima di prendere il secondo dado, questo è ancora da gestire in qualche modo
    public boolean doOp(ObjectiveTool object){
       if((object!=null)){
           ArrayList<Coordinate> coordinates = object.getListOfCoordinateY();
           DraftPool dp = object.getDp();
           dp.refreshDraftPool(coordinates); //Metodo che ritira i dadi
           return true;
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
