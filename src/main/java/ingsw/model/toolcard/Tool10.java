package ingsw.model.toolcard;

import ingsw.model.ObjectiveTool;

public class Tool10 implements ToolStrategy {
    private String title;
    private String comment;
    private boolean alreadyUsed;
    private int idCard;


    public Tool10(int idCard) {
        this.title ="Tampone Diamantato";
        this.comment = "Dopo aver scelto un dado, giralo\n" + "sulla faccia opposta\n" + "6 diventa 1, 5 diventa 2, 4\n" + "diventa 3 ecc.";
        this.alreadyUsed=false;
        this.idCard=idCard;
    }

    public void doOp(ObjectiveTool object){

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