package ingsw.model.toolcard;

import ingsw.model.*;
import ingsw.model.windowpattern.WindowPattern;

public class Tool11 implements ToolStrategy {
    private String title;
    private String comment;
    private boolean alreadyUsed;
    private int idCard;
    private Die die;
    private DraftPool dp;
    private WindowPattern window;
    private DiceBag diceBag;
    private Coordinate inputDest;

    public Tool11(int idCard) {
        this.title ="Diluente per Pasta Salda";
        this.comment = "Dopo aver scelto un dado, riponilo nel\n" + "Sacchetto, poi pescane uno dal Sacchetto\n" + "Scegli il valore del nuovo dado e\n" + "piazzalo, rispettando tutte le restrizioni di\n" + "piazzamento";
        this.alreadyUsed=false;
        this.idCard=idCard;
    }

    public void doOp(ObjectiveTool object){
        die = object.getDie1();
        dp = object.getDp();
        diceBag = object.getDiceBag();
        // diceBag.insertDie(die);  //Metodo che sistema la randomicità con cui escono i dadi, reinserisco il dado nella borsa
        dp.throwsDice(1);
        //die = dp.takeDie(dp.getDiceList().size() -1);  //Anche qua servirebbe la diceList
        //die.setNumber(inputNumber)  //Serve inserire un numero scelto dal player "in diretta", in base al colore che esce ovviamente cambia
        window.addDie(inputDest,die,window.getCellMatrix());  //Manca da passare inputDest in qualche modo come inputNumber
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
