package ingsw.model;


import ingsw.model.toolcard.*;

public class ToolCard {

    private ToolStrategy toolStrategy;
    private boolean alreadyUsed;

    public ToolCard(int idCard){
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
    }

    public boolean isAlreadyUsed(){
        return toolStrategy.isAlreadyUsed();
    }

    public void doToolStrategy(ObjectiveTool objective){
        toolStrategy.doOp(objective);
    }

    public static void generateToolCard(ViewData init) {
        RandomGenerator rg = new RandomGenerator(10);
        for(int i=0;i<3;i++){
            int select = rg.random();
            switch(select){
                case 1:{
                    init.getToolCard().add("/Tool1.png");
                    break;
                }
                case 2:{
                    init.getToolCard().add("/Tool2.png");
                    break;
                }
                case 3:{
                    init.getToolCard().add("/Tool3.png");
                    break;
                }
                case 4:{
                    init.getToolCard().add("/Tool4.png");
                    break;
                }
                case 5:{
                    init.getToolCard().add("/Tool5.png");
                    break;
                }
                case 6:{
                    init.getToolCard().add("/Tool6.png");
                    break;
                }
                case 7:{
                    init.getToolCard().add("/Tool7.png");
                    break;
                }
                case 8:{
                    init.getToolCard().add("/Tool8.png");
                    break;
                }
                case 9:{
                    init.getToolCard().add("/Tool9.png");
                    break;
                }
                case 10:{
                    init.getToolCard().add("/Tool10.png");
                    break;
                }
                case 11:{
                    init.getToolCard().add("/Tool11.png");
                    break;
                }
                case 12:{
                    init.getToolCard().add("/Tool12.png");
                    break;
                }
                default:{
                    break;
                }
            }
        }
    }
}
