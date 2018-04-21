package ingsw;

public class ToolCard {

    private ToolStrategy toolStrategy;
    private String constraint;
    private boolean alreadyUsed;

    public ToolCard(int idCard){
        switch(idCard){
            case 1 : {
                this.toolStrategy = new ToolCard.Tool1();
                break;
            }case 2 : {
                this.toolStrategy = new ToolCard.Tool2();
                break;
            }case 3 : {
                toolStrategy = new Tool3;
                break;
            }case 4 : {
                toolStrategy = new Tool4;
                break;
            }case 5 : {
                toolStrategy = new Tool5;
                break;
            }case 6 : {
                toolStrategy = new Tool6;
                break;
            }case 7 : {
                toolStrategy = new Tool7;
                break;
            }case 8 : {
                toolStrategy = new Tool8;
                break;
            }case 9 : {
                toolStrategy = new Tool9;
                break;
            }case 10 : {
                toolStrategy = new Tool10;
                break;
            }case 11 : {
                toolStrategy = new Tool11;
                break;
            }case 12 : {
                toolStrategy = new Tool12;
                break;
            } default :
                System.out.print("Invalid number, launch exception");
                break;
        }
    }

    public void doToolStrategy(){
        toolStrategy.doOp();
    }

}
