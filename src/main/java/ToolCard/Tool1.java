package ToolCard;

import ingsw.DraftPool;
import ingsw.Die;
import java.util.ArrayList;

public class Tool1 implements ToolStrategy {
    private String title;
    private String comment;

    public Tool1(){
        this.title ="Pinza Sgrossatrice";
        this.comment = "Dopo aver scelto un dado,\n" + "aumenta o dominuisci il valore\n" + "del dado scelto di 1\n" + "Non puoi cambiare\n" + "un 6 in 1 o un 1 in 6";
    }

    @Override //Il metodo useToolCard avrà come parametro la Tool e il Die selezionato, poi chiamerà doOp(Die)
    public void doOp(Die die){  //Modifico il numero dell'oggetto Die passato
        //System.out.println("Sono la carta 1");
        if(1 <= die.getNumber() && die.getNumber()<=5 && upDie()) {
            die.setNumber(die.getNumber() + 1);                    //upDie e downDie sono boolean e dicono se il player vuole
        }                                                          //aumentare o diminuire il valore
        else  if(2 <= die.getNumber() && die.getNumber()<=6 && downDie()) {
            die.setNumber(die.getNumber() - 1);
        }
        else
            System.out.println("Non puoi usare questa carta su questo dado");
    }
}
