package ingsw;
//In UML
public class Tool2 implements ToolStrategy {
    private Die die;
    private Cell cell;
    @Override
    public void doOp(){
        //System.out.println("Sono la carta 2");
        die = getCell(getInputCoordinate()).takeDie();
        //Come differenzio la scelta della cella? Serve una sorta di "wait selection"
        cell = getCell(getInputCoordinate());
        if(verifyDieConstraint( cell.getCoordinate()) == true)
            Player.positionDie(die, cell.getCoordinate());
    }
}
