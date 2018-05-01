package ingsw.model.toolcard;

import ingsw.model.Die;
import ingsw.model.DraftPool;
import ingsw.model.ObjectiveTool;

public class Tool10 implements ToolStrategy {
    private String title;
    private String comment;
    private boolean alreadyUsed;
    private int idCard;
    private Die die;
    private DraftPool dp;

    public Tool10(int idCard) {
        this.title ="Tampone Diamantato";
        this.comment = "Dopo aver scelto un dado, giralo\n" + "sulla faccia opposta\n" + "6 diventa 1, 5 diventa 2, 4\n" + "diventa 3 ecc.";
        this.alreadyUsed=false;
        this.idCard=idCard;
    }

    public void doOp(ObjectiveTool object){
        int turn = object.getTurnDie();
        die = object.getDie1();
        dp = object.getDp();
        if (turn == 1){ //In effetti se voglio usare questa carta per forza vale uno, forse si può togliere come parametro
            switch(die.getNumber()){
                case 1:{
                    die.setNumber(6);
                    break;
                }
                case 2:{
                    die.setNumber(5);
                    break;
                }
                case 3:{
                    die.setNumber(4);
                    break;
                }
                case 4:{
                    die.setNumber(3);
                    break;
                }
                case 5:{
                    die.setNumber(2);
                    break;
                }
                case 6:{
                    die.setNumber(1);
                    break;
                }
            }
            dp.addDie(die);
        }
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