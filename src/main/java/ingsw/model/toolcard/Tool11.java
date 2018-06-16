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
    private Coordinate c1;
    private int numTokenUsed;

    public Tool11(int idCard) {
        this.title ="Diluente per Pasta Salda";
        this.comment = "Dopo aver scelto un dado, riponilo nel\n" + "Sacchetto, poi pescane uno dal Sacchetto\n" + "Scegli il valore del nuovo dado e\n" + "piazzalo, rispettando tutte le restrizioni di\n" + "piazzamento";
        this.alreadyUsed=false;
        this.idCard=idCard;
    }

    public boolean doOp(ObjectiveTool object){
        if(object.getPhase() == 0){
            c1 = object.getC1();
            dp = object.getDp();
            diceBag = object.getDiceBag();
            die = dp.getDie(c1.getY());
            diceBag.insertDie(die);  //Metodo che sistema la randomicità con cui escono i dadi, reinserisco il dado nella borsa
            dp.takeDie(c1.getY());
            die = dp.reThrowsDie(c1.getY());
            return true;
        }else if(object.getPhase() == 1){
            die.setNumber(object.getDieModified());  //Serve inserire un numero scelto dal player "in diretta", in base al colore che esce ovviamente cambia
            return true;
        }else if(object.getPhase() == 2){
            window = object.getWindow();
            window.addDie(object.getC1(),die,window.getCellMatrix());  //Manca da passare inputDest in qualche modo come inputNumber
            die = dp.takeDie(c1.getY());
            return true;
        }
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

    public void setNumTokenUsed(int token) {
        this.numTokenUsed=token;
    }

    public int getNumTokenUsed() {
        return numTokenUsed;
    }

    /** Author: Elio Salvini
     *
     * CLI support
     */
    @Override
    public String toString() {
        return title +'\n' + comment + '\n';
    }

}
