package ingsw.model.toolcard;

import ingsw.model.*;
import ingsw.view.PlayGame;

public class Tool8 implements ToolStrategy {
    private String title;
    private String comment;
    private boolean alreadyUsed;
    private int idCard;


    public Tool8(int idCard) {
        this.title ="Tenaglia a Rotelle";
        this.comment = "Dopo il tuo primo turno scegli\n" + "immediatamente un altro dado\n" + "Salta il tuo secondo turno in\n" + "questo round";
        this.alreadyUsed=false;
        this.idCard=idCard;
    }

    public boolean doOp(ObjectiveTool object){
        object.getPlayer().setInsertedDie(false);
        PlayGame.setUsingTool(false);
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
}