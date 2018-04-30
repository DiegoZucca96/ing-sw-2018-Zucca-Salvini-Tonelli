package ingsw.model.toolcard;

import ingsw.model.ObjectiveTool;

public class Tool11 implements ToolStrategy {
    private String title;
    private String comment;
    private boolean alreadyUsed;
    private int idCard;


    public Tool11(int idCard) {
        this.title ="Diluente per Pasta Salda";
        this.comment = "Dopo aver scelto un dado, riponilo nel\n" + "Sacchetto, poi pescane uno dal Sacchetto\n" + "Scegli il valore del nuovo dado e\n" + "piazzalo, rispettando tutte le restrizioni di\n" + "piazzamento";
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
