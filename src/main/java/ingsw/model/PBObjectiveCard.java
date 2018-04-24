package ingsw.model;

import ingsw.model.publiccard.*;
import ingsw.model.windowpattern.WP1;

public class PBObjectiveCard {
    private PBStrategy pbStrategy;
    private int points;

    public PBObjectiveCard(int idCard){
        switch(idCard){
            case 1 : {
                this.pbStrategy = new PB1();
                break;
            }case 2 : {
                this.pbStrategy = new PB2();
                break;
            }case 3 : {
                this.pbStrategy = new PB3();
                break;
            }case 4 : {
                this.pbStrategy = new PB4();
                break;
            }case 5 : {
                this.pbStrategy = new PB5();
                break;
            }case 6 : {
                this.pbStrategy = new PB6();
                break;
            }case 7 : {
                this.pbStrategy = new PB7();
                break;
            }case 8 : {
                this.pbStrategy = new PB8();
                break;
            }case 9 : {
                this.pbStrategy = new PB9();
                break;
            }case 10 : {
                this.pbStrategy = new PB10();
                break;
            } default :
                System.out.print("Invalid number, launch exception");
                break;
        }
    }

    public void doPBStrategy(Player p, WP1 window){
        pbStrategy.doOp( p , window);
    }

}
