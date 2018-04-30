package ingsw.model.toolcard;

import ingsw.model.ObjectiveTool;

public class Tool12 implements ToolStrategy {
    private String title;
    private String comment;
    private boolean alreadyUsed;
    private int idCard;


    public Tool12(int idCard) {
        this.title ="Taglierina Manuale";
        this.comment = "Muovi fino a due dadi dello\n" + "stesso colore di un solo dado sul\n" + "Tracciato dei Round\n" + "Devi rispettare tutte le restrizioni\n" + "di piazzamento";
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
