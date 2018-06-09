package ingsw.model;


import ingsw.model.toolcard.*;
import ingsw.observers.Observer;
import ingsw.observers.ToolCardsObserver;

import java.util.ArrayList;

public class ToolCard {

    private ToolStrategy toolStrategy;
    private boolean alreadyUsed;
    private Observer viewObserver;
    private int numToken;

    public ToolCard(int idCard){
        viewObserver = new ToolCardsObserver();
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
//Metodi che servono per utilizzare le ToolCard e permettere di creare nello switch case del Player i parametri di ObjectiveCard
    public int getIdCard() {
        return toolStrategy.getIdCard();
    }

    public void setAlreadyUsed(boolean alreadyUsed) {
        toolStrategy.setAlreadyUsed(alreadyUsed);
        notifyViewObserver();
    }

    public boolean isAlreadyUsed(){
        return toolStrategy.isAlreadyUsed();
    }

    public boolean doToolStrategy(ObjectiveTool objective){
        boolean b = toolStrategy.doOp(objective);
        notifyViewObserver();
        return b;
    }

    public static ArrayList<Integer> generateToolCard(ViewData init) {
        RandomGenerator rg = new RandomGenerator(10);
        ArrayList<Integer> numToolCards = new ArrayList<>();
        //Righe da decommentare se si vogliono provare le ToolCard
        //init.getToolCard().add("/Tool5.png");
        //numToolCards.add(5);
        init.getToolCard().add("/Tool8.png");
        numToolCards.add(8);
        for(int i=0;i<3;i++){
            int select = rg.random();
            switch(select){
                case 1:{
                    init.getToolCard().add("/Tool1.png");
                    numToolCards.add(select);
                    break;
                }
                case 2:{
                    init.getToolCard().add("/Tool2.png");
                    numToolCards.add(select);
                    break;
                }
                case 3:{
                    init.getToolCard().add("/Tool3.png");
                    numToolCards.add(select);
                    break;
                }
                case 4:{
                    init.getToolCard().add("/Tool4.png");
                    numToolCards.add(select);
                    break;
                }
                case 5:{
                    init.getToolCard().add("/Tool5.png");
                    numToolCards.add(select);
                    break;
                }
                case 6:{
                    init.getToolCard().add("/Tool6.png");
                    numToolCards.add(select);
                    break;
                }
                case 7:{
                    init.getToolCard().add("/Tool7.png");
                    numToolCards.add(select);
                    break;
                }
                case 8:{
                    init.getToolCard().add("/Tool8.png");
                    numToolCards.add(select);
                    break;
                }
                case 9:{
                    init.getToolCard().add("/Tool9.png");
                    numToolCards.add(select);
                    break;
                }
                case 10:{
                    init.getToolCard().add("/Tool10.png");
                    numToolCards.add(select);
                    break;
                }
                case 11:{
                    init.getToolCard().add("/Tool11.png");
                    numToolCards.add(select);
                    break;
                }
                case 12:{
                    init.getToolCard().add("/Tool12.png");
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
        return "Tool"+Integer.toString(getIdCard());
    }

    public void notifyViewObserver(){
        viewObserver.update(this, ViewData.instance());
    }
}
