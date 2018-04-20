package ingsw;

public class Tool1 implements ToolStrategy{
    @Override
    public void doOp(){
        //System.out.println("Sono la carta 1");
        if(1 <= DraftPool.takeDie(n).getNum() && DraftPool.takeDie(n).getNum()<=5)
            DraftPool.takeDie(n).setNum(getNum()+1);
        else
            System.out.println("Non puoi usare questa carta su questo dado");
    }
}
