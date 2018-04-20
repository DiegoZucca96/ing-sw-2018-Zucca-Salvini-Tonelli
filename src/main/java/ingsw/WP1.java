package ingsw;

public class WP1 implements WindowPattern {

    private String title = "Kaleidoscopic Dream";

    private int difficulty;

    private final Cell[][] cellMatrix;


    public WP1(){

        cellMatrix = new Cell[][]{
                {new Cell(0, Color.YELLOW, new Coordinate(0,0)), new Cell(0, Color.BLUE, new Coordinate(0,1)), new Cell(0, null, new Coordinate(0,2)), new Cell(0, null, new Coordinate(0,3)), new Cell(1, null, new Coordinate(0,4))},
                {new Cell(0, Color.GREEN, new Coordinate(1,0)), new Cell(0, null, new Coordinate(1,1)), new Cell(5, null, new Coordinate(1,2)), new Cell(0, null, new Coordinate(1,3)), new Cell(4, null, new Coordinate(1,4))},
                {new Cell(3, null, new Coordinate(2,0)), new Cell(0, null, new Coordinate(2,1)), new Cell(0, Color.RED, new Coordinate(2,2)), new Cell(0, null, new Coordinate(2,3)), new Cell(1, Color.GREEN, new Coordinate(2,4))},
                {new Cell(2, null, new Coordinate(3,0)), new Cell(0, null, new Coordinate(3,1)), new Cell(0, null, new Coordinate(3,3)), new Cell(0, Color.BLUE, new Coordinate(3,3)), new Cell(1, Color.YELLOW, new Coordinate(3,4))}
        };
        this.difficulty=4;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public void addDie(Coordinate destination, Die die) {
        if (cellMatrix[destination.getX()][destination.getY()].isEmpty()){
            if(verifyCellConstraint(destination, die) && verifyDieConstraint(destination, die)){
                cellMatrix[destination.getX()][destination.getY()].setEmpty(false);
                cellMatrix[destination.getX()][destination.getY()].insertDie(die);
            }
            else {
                System.out.println("Non puoi inserire il dado in questa posizione");
            }
        }else
            System.out.println("Posizione già occupata da un altro dado");

    }

    @Override
    public void removeDie(Coordinate destination) {
        if (cellMatrix[destination.getX()][destination.getY()].isEmpty()) {
            System.out.println("Cella vuota");
        }else cellMatrix[destination.getX()][destination.getY()].takeDie();
    }


    @Override
    public Cell getCell(Coordinate coordinate) {
        return this.cellMatrix[coordinate.getX()][coordinate.getY()];
    }


//VERIFICA CHE LA CELLA DI DESTINAZIONE ABBIA COLORE O NUMERO UGUALE AL DADO
    private boolean verifyCellConstraint(Coordinate destination, Die die){
        if(cellMatrix[destination.getX()][destination.getY()].getColor()==null &&cellMatrix[destination.getX()][destination.getY()].getNumber()==0)
            return true;
        else if (cellMatrix[destination.getX()][destination.getY()].getColor()== die.getColor() && cellMatrix[destination.getX()][destination.getY()].getNumber()== 0) {
            return true;
        } else if (cellMatrix[destination.getX()][destination.getY()].getColor()== null && cellMatrix[destination.getX()][destination.getY()].getNumber()== die.getNumber())
            return true;
        else
            return false;
    }

//VERIFICA CHE LE CELLE ORTOGONALMENTE ADIACENTI AL DADO CHE SI VUOLE POSIZIONARE NON ABBIANO DADI CON LO STESSO COLORE O CON LO STESSO NUMERO
    private boolean verifyDieConstraint(Coordinate destination, Die die){

        if(cellMatrix[destination.getX()+1][destination.getY()].getDie().getColor()==die.getColor() || cellMatrix[destination.getX()][destination.getY()+1].getDie().getColor()==die.getColor() || cellMatrix[destination.getX()-1][destination.getY()].getDie().getColor()==die.getColor() || cellMatrix[destination.getX()][destination.getY()-1].getDie().getColor()==die.getColor())
            return false;
        else if(cellMatrix[destination.getX()+1][destination.getY()].getDie().getNumber()==die.getNumber() || cellMatrix[destination.getX()][destination.getY()+1].getDie().getNumber()==die.getNumber() || cellMatrix[destination.getX()-1][destination.getY()].getDie().getNumber()==die.getNumber() || cellMatrix[destination.getX()][destination.getY()-1].getDie().getNumber()==die.getNumber())
            return false;
        else
            return true;
    }
}
