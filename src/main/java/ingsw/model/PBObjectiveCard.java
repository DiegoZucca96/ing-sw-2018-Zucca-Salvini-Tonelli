package ingsw.model;

import ingsw.model.publiccard.*;
import ingsw.model.publiccard.PB9;
import java.util.ArrayList;

/**
 * Author : Diego Zucca
 *
 * This class creates in a random way the public cards of the match and it implements the pattern Strategy
 */
public class PBObjectiveCard {
    private PBStrategy pbStrategy;

    /**
     * Constructor, it uses the Strategy pattern
     * @param idCard it is the number of the public card
     */
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

    /**
     * It runs the public card's method
     * @param p it is the player that has called this method
     */
    public void doPBStrategy(Player p){
        pbStrategy.doOp(p);
    }

    /**
     * This method creates in a random way the public cards of the match
     * @param init it is the VIewData object that will contains the informations about the default match
     * @return the list of the number of public cards created
     */
    public static ArrayList<Integer> generatePBCard(ViewData init) {
        RandomGenerator rg = new RandomGenerator(10);
        ArrayList<Integer> numPBcards = new ArrayList<>();
        for(int i=0;i<3;i++){
            int select = rg.random();
            switch(select){
                case 1:{
                    init.getPbCard().add("/Public1.png");
                    numPBcards.add(select);
                    break;
                }
                case 2:{
                    init.getPbCard().add("/Public2.png");
                    numPBcards.add(select);
                    break;
                }
                case 3:{
                    init.getPbCard().add("/Public3.png");
                    numPBcards.add(select);
                    break;
                }
                case 4:{
                    init.getPbCard().add("/Public4.png");
                    numPBcards.add(select);
                    break;
                }
                case 5:{
                    init.getPbCard().add("/Public5.png");
                    numPBcards.add(select);
                    break;
                }
                case 6:{
                    init.getPbCard().add("/Public6.png");
                    numPBcards.add(select);
                    break;
                }
                case 7:{
                    init.getPbCard().add("/Public7.png");
                    numPBcards.add(select);
                    break;
                }
                case 8:{
                    init.getPbCard().add("/Public8.png");
                    numPBcards.add(select);
                    break;
                }
                case 9:{
                    init.getPbCard().add("/Public9.png");
                    numPBcards.add(select);
                    break;
                }
                case 10:{
                    init.getPbCard().add("/Public10.png");
                    numPBcards.add(select);
                    break;
                }
                default:{
                    break;
                }
            }
        }
        return numPBcards;
    }

    @Override
    public String toString() {
        return pbStrategy.toString();
    }
}
