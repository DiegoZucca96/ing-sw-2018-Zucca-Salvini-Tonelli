package ToolCard;

import ingsw.DraftPool;
import ingsw.Die;
import java.util.ArrayList;

public class Tool1 implements ToolStrategy {
    private String title;
    private String comment;
    private ArrayList<Die> diceList;
    private Die die;

    public Tool1(){
        this.title ="Pinza Sgrossatrice";
        this.comment = "Dopo aver scelto un dado,\n" + "aumenta o dominuisci il valore\n" + "del dado scelto di 1\n" + "Non puoi cambiare\n" + "un 6 in 1 o un 1 in 6";
    }
    @Override
    public void doOp(){
        //System.out.println("Sono la carta 1");
        diceList= getDiceList();   //Serve un metodo che passi l'attuale diceList alla ToolCard e la posizione del dado da modificare
        if(1 <= diceList.getDie(n).getNumber() && diceList.getDie(n).getNumber()<=5) {  //Chiamo il metodo getDie della DraftPool
            die=DraftPool.takeDie(n);
            die.setNumber(die.getNumber() + 1);
            diceList.add(die);
        }
        else
            System.out.println("Non puoi usare questa carta su questo dado");
    }
}
