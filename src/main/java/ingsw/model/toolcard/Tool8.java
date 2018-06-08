package ingsw.model.toolcard;

import ingsw.model.Die;
import ingsw.model.DraftPool;
import ingsw.model.ObjectiveTool;
import ingsw.model.windowpattern.WindowPattern;

public class Tool8 implements ToolStrategy {
    private String title;
    private String comment;
    private boolean alreadyUsed;
    private int idCard;
    private Die die;
    private WindowPattern window;
    private DraftPool dp;

    public Tool8(int idCard) {
        this.title ="Tenaglia a Rotelle";
        this.comment = "Dopo il tuo primo turno scegli\n" + "immediatamente un altro dado\n" + "Salta il tuo secondo turno in\n" + "questo round";
        this.alreadyUsed=false;
        this.idCard=idCard;
    }

    public boolean doOp(ObjectiveTool object){
       int indexDie = 0;
       dp = object.getDp();
       die = dp.takeDie(indexDie); //Stesso problema della ToolCard 5, mi serve una sorta di indice passato dal client
       window = object.getWindow();
       window.addDie(object.getDestination1(),die,window.getCellMatrix());
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