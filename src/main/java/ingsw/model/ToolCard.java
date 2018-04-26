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

}
