package ingsw.model.toolcard;

import ingsw.model.ObjectiveTool;

public class Tool9 implements ToolStrategy{
    private String title;
    private String comment;
    private boolean alreadyUsed;
    private int idCard;


    public Tool9(int idCard) {
        this.title ="Riga in Sughero";
        this.comment = "Dopo aver scelto un dado,\n" + "piazzalo in una casella che non\n" + "sia adiacente a un altro dado\n" + "Devi rispettare tutte le restrizioni\n" + "di piazzamento";
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
