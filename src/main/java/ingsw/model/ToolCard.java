package ingsw.model;

import ingsw.model.toolcard.*;
import ingsw.observers.Observer;
import ingsw.observers.ToolCardsObserver;
import java.util.ArrayList;

/**
 * This class creates in a random way the tool cards of the match and it implements the pattern Strategy
 */
public class ToolCard {

    private ToolStrategy toolStrategy;
    private boolean alreadyUsed;
    private Observer viewObserver;
    private int numTokenUsed;

    /**
     * Constructor, it uses the Strategy pattern
     * @param idCard it is the number of the tool card
     */
    public ToolCard(int idCard){
        viewObserver = new ToolCardsObserver();
        numTokenUsed=0;
        switch(idCard){
            case 1 : {
                toolStrategy = new Tool1(idCard);
                break;
            }case 2 : {
                toolStrategy = new Tool2(idCard);
                break;
            }case 3 : {
                toolStrategy = new Tool3(idCard);
                break;
            }case 4 : {
                toolStrategy = new Tool4(idCard);
                break;
            }case 5 : {
                toolStrategy = new Tool5(idCard);
                break;
            }case 6 : {
                toolStrategy = new Tool6(idCard);
                break;
            }case 7 : {
                toolStrategy = new Tool7(idCard);
                break;
            }case 8 : {
                toolStrategy = new Tool8(idCard);
                break;
            }case 9 : {
                toolStrategy = new Tool9(idCard);
                break;
            }case 10 : {
                toolStrategy = new Tool10(idCard);
                break;
            }case 11 : {
                toolStrategy = new Tool11(idCard);
                break;
            }case 12 : {
                toolStrategy = new Tool12(idCard);
                break;
            } default :
                System.out.print("Invalid number, launch exception");
                break;
        }
    }

    /**
     * It runs the tool card's method
     * @param objective it is the object that contains all the information to use correctly the tool card
     */
    public boolean doToolStrategy(ObjectiveTool objective){
        boolean b = toolStrategy.doOp(objective);
        notifyViewObserver();
        return b;
    }

    /**
     * This method creates in a random way the tool cards of the match
     * @param init it is the VIewData object that will contains the informations about the default match
     * @return the list of the number of tool cards created
     */
    public static ArrayList<Integer> generateToolCard(ViewData init) {
        RandomGenerator rg = new RandomGenerator(10);
        ArrayList<Integer> numToolCards = new ArrayList<>();
        //uncomment these lines of code and change the "i" value from 0 to 3 to have specific tool cards (for testing)
        //init.getToolCard().add("/Tool10+0.png");
        //numToolCards.add(10);
        //init.getToolCard().add("/Tool11+0.png");
        //numToolCards.add(11);
        //init.getToolCard().add("/Tool12+0.png");
        //numToolCards.add(12);
        for(int i=0;i<3;i++){
            int select = rg.random();
            switch(select){
                case 1:{
                    init.getToolCard().add("/Tool1+0.png");
                    numToolCards.add(select);
                    break;
                }
                case 2:{
                    init.getToolCard().add("/Tool2+0.png");
                    numToolCards.add(select);
                    break;
                }
                case 3:{
                    init.getToolCard().add("/Tool3+0.png");
                    numToolCards.add(select);
                    break;
                }
                case 4:{
                    init.getToolCard().add("/Tool4+0.png");
                    numToolCards.add(select);
                    break;
                }
                case 5:{
                    init.getToolCard().add("/Tool5+0.png");
                    numToolCards.add(select);
                    break;
                }
                case 6:{
                    init.getToolCard().add("/Tool6+0.png");
                    numToolCards.add(select);
                    break;
                }
                case 7:{
                    init.getToolCard().add("/Tool7+0.png");
                    numToolCards.add(select);
                    break;
                }
                case 8:{
                    init.getToolCard().add("/Tool8+0.png");
                    numToolCards.add(select);
                    break;
                }
                case 9:{
                    init.getToolCard().add("/Tool9+0.png");
                    numToolCards.add(select);
                    break;
                }
                case 10:{
                    init.getToolCard().add("/Tool10+0.png");
                    numToolCards.add(select);
                    break;
                }
                case 11:{
                    init.getToolCard().add("/Tool11+0.png");
                    numToolCards.add(select);
                    break;
                }
                case 12:{
                    init.getToolCard().add("/Tool12+0.png");
                    numToolCards.add(select);
                    break;
                }
                default:{
                    break;
                }
            }
        }
        return numToolCards;
    }

    @Override
    public String toString() {
        return "/Tool"+getIdCard()+"+"+getNumTokenUsed()+".png";
    }

    public int getIdCard() {
        return toolStrategy.getIdCard();
    }

    public void setAlreadyUsed(boolean alreadyUsed) {
        toolStrategy.setAlreadyUsed(alreadyUsed);
    }

    public boolean isAlreadyUsed(){
        return toolStrategy.isAlreadyUsed();
    }

    public int getNumTokenUsed() {
        return toolStrategy.getNumTokenUsed();
    }

    public void setNumTokenUsed(int numTokenUsed) {
       toolStrategy.setNumTokenUsed(numTokenUsed);
       notifyViewObserver();
    }

    /**
     * This method notifies changes in this object to viewObserver
     */
    public void notifyViewObserver(){
        viewObserver.update(this, ViewData.instance());
    }

    /** Author: Elio Salvini
     *
     * CLI support
     */
    public String toStringCLI(){
        return toolStrategy.toString();
    }
}
