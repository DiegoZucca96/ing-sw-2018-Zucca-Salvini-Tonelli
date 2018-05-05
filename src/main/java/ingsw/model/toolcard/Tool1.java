package ingsw.model.toolcard;

import ingsw.model.Die;
import ingsw.model.ObjectiveTool;

public class Tool1 implements ToolStrategy {
    private String title;
    private String comment;
    private boolean alreadyUsed;
    private int idCard;
    private Die die;

    public Tool1(int idCard){
        this.title ="Pinza Sgrossatrice";
        this.comment = "Dopo aver scelto un dado,\n" + "aumenta o dominuisci il valore\n" + "del dado scelto di 1\n" + "Non puoi cambiare\n" + "un 6 in 1 o un 1 in 6";
        this.alreadyUsed=false;
        this.idCard=idCard;
    }
    //Modifica il numero del dado scelto di 1 aumentandolo o diminuendolo in base al valore di getUp()
    public void doOp(ObjectiveTool object){
        die = object.getDie1();
        if(1 <= die.getNumber() && die.getNumber()<=5 && object.getUp()== 1)
            die.setNumber(die.getNumber() + 1);
        else if(2 <= die.getNumber() && die.getNumber()<=6 && object.getUp()== -1)
            die.setNumber(die.getNumber() - 1);
        else
            System.out.println("Non puoi usare questa carta su questo dado");
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
