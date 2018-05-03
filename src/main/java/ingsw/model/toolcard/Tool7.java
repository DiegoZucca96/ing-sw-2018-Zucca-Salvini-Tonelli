package ingsw.model.toolcard;

import ingsw.model.DiceBag;
import ingsw.model.Die;
import ingsw.model.DraftPool;
import ingsw.model.ObjectiveTool;

import java.util.ArrayList;
import java.util.Random;

public class Tool7 implements ToolStrategy {
    private String title;
    private String comment;
    private boolean alreadyUsed;
    private int idCard;
    private DraftPool dp;

    public Tool7(int idCard) {
        this.title ="Martelletto";
        this.comment = "Tira nuovamente\n" + "tutti i dadi della Riserva\n" + "Questa carta può essera usata\n" + "solo durante il tuo secondo turno,\n" + "prima di scegliere il secondo dado";
        this.alreadyUsed=false;
        this.idCard=idCard;
    }

    //La carta specifica prima di prendere il secondo dado, questo è ancora da gestire in qualche modo
    public void doOp(ObjectiveTool object){
       if(!(object.equals(null))){
           dp = object.getDp();
           dp.refreshDraftPool(); //Metodo che ritira i dadi
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
