package WindowPattern;

import ingsw.*;

public class WP3 implements WindowPattern {

    private String title = "Fractal Drops";

    private int difficulty;

    private final Cell[][] cellMatrix;

    private boolean wpEmpty;

    public WP3(){

        cellMatrix = new Cell[][]{
                {new Cell(0, null, new Coordinate(0,0)), new Cell(4, null, new Coordinate(0,1)), new Cell(0, null, new Coordinate(0,2)), new Cell(0, Color.YELLOW, new Coordinate(0,3)), new Cell(6, null, new Coordinate(0,4))},
                {new Cell(0, Color.RED, new Coordinate(1,0)), new Cell(0, null, new Coordinate(1,1)), new Cell(2, null, new Coordinate(1,2)), new Cell(0, null, new Coordinate(1,3)), new Cell(0, null, new Coordinate(1,4))},
                {new Cell(0, null, new Coordinate(2,0)), new Cell(0, null, new Coordinate(2,1)), new Cell(0, Color.RED, new Coordinate(2,2)), new Cell(0, Color.VIOLET, new Coordinate(2,3)), new Cell(1, null, new Coordinate(2,4))},
                {new Cell(0, Color.BLUE, new Coordinate(3,0)), new Cell(0, Color.YELLOW, new Coordinate(3,1)), new Cell(0, null, new Coordinate(3,2)), new Cell(0, null, new Coordinate(3,3)), new Cell(0, null, new Coordinate(3,4))}
        };
        this.difficulty=3;
        this.wpEmpty=true;
    }

    public boolean isWpEmpty() {
        return wpEmpty;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public void addDie(Coordinate destination, Die die) {
        if(wpEmpty){
            if(destination.getX()==0 || destination.getX()==3 || destination.getY()==0 || destination.getY()==4){
                if (cellMatrix[destination.getX()][destination.getY()].isEmpty()){
                    if(verifyPosition(destination)) {
                        if(verifyCellConstraint(destination, die) && verifyDieConstraint(destination, die)){
                            cellMatrix[destination.getX()][destination.getY()].setEmpty(false);
                            cellMatrix[destination.getX()][destination.getY()].insertDie(die);
                            this.wpEmpty=false;
                        }
                        else
                            System.out.println("Non puoi inserire il dado in questa posizione");
                    }else
                        System.out.println("Il dado non tocca nessun altro dado");
                }else
                    System.out.println("Posizione già occupata da un altro dado");

            }else
                System.out.println("Non puoi inserire il dado in questa posizione perchè non sei sul bordo");

        }else if (cellMatrix[destination.getX()][destination.getY()].isEmpty()){
            if(verifyPosition(destination)) {
                if(verifyCellConstraint(destination, die) && verifyDieConstraint(destination, die)){
                    cellMatrix[destination.getX()][destination.getY()].setEmpty(false);
                    cellMatrix[destination.getX()][destination.getY()].insertDie(die);
                }
                else
                    System.out.println("Non puoi inserire il dado in questa posizione");
            }else
                System.out.println("Il dado non tocca nessun altro dado");
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
    public boolean verifyCellConstraint(Coordinate destination, Die die){
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
    public boolean verifyDieConstraint(Coordinate destination, Die die){

        if(cellMatrix[destination.getX()+1][destination.getY()].getDie().getColor()==die.getColor() || cellMatrix[destination.getX()][destination.getY()+1].getDie().getColor()==die.getColor() || cellMatrix[destination.getX()-1][destination.getY()].getDie().getColor()==die.getColor() || cellMatrix[destination.getX()][destination.getY()-1].getDie().getColor()==die.getColor())
            return false;
        else if(cellMatrix[destination.getX()+1][destination.getY()].getDie().getNumber()==die.getNumber() || cellMatrix[destination.getX()][destination.getY()+1].getDie().getNumber()==die.getNumber() || cellMatrix[destination.getX()-1][destination.getY()].getDie().getNumber()==die.getNumber() || cellMatrix[destination.getX()][destination.getY()-1].getDie().getNumber()==die.getNumber())
            return false;
        else
            return true;
    }

    //VERIFICA CHE IL DADO CHE STAI INSERENDO TOCCA ALMENO UN DADO ATTORNO; caso base i vertici della finestra
    public boolean verifyPosition(Coordinate destination){
        if(destination.getX()==0 && destination.getY()==0){
            if(!cellMatrix[1][0].isEmpty() || !cellMatrix[0][1].isEmpty() || !cellMatrix[1][1].isEmpty()) return true;
        }else if(destination.getX()==3 && destination.getY()==0){
            if(!cellMatrix[2][0].isEmpty() || !cellMatrix[2][1].isEmpty() || !cellMatrix[3][1].isEmpty()) return true;
        }else if(destination.getX()==0 && destination.getY()==4){
            if(!cellMatrix[0][3].isEmpty() || !cellMatrix[1][3].isEmpty() || !cellMatrix[1][4].isEmpty()) return true;
        }else if(destination.getX()==3 && destination.getY()==4){
            if(!cellMatrix[2][4].isEmpty() || !cellMatrix[2][3].isEmpty() || !cellMatrix[3][3].isEmpty()) return true;
        }else if(destination.getX()==0){
            if(!cellMatrix[0][destination.getY()-1].isEmpty() || !cellMatrix[0][destination.getY()+1].isEmpty() || !cellMatrix[1][destination.getY()].isEmpty() || !cellMatrix[1][destination.getY()+1].isEmpty() || !cellMatrix[1][destination.getY()-1].isEmpty()) return true;
        }else if(destination.getX()==3){
            if(!cellMatrix[3][destination.getY()-1].isEmpty() || !cellMatrix[3][destination.getY()+1].isEmpty() || !cellMatrix[2][destination.getY()].isEmpty() || !cellMatrix[2][destination.getY()+1].isEmpty() || !cellMatrix[2][destination.getY()-1].isEmpty()) return true;
        }else if(destination.getY()==0){
            if(!cellMatrix[destination.getX()-1][0].isEmpty() || !cellMatrix[destination.getX()+1][0].isEmpty() || !cellMatrix[destination.getX()][1].isEmpty() || !cellMatrix[destination.getX()+1][1].isEmpty() || !cellMatrix[destination.getX()-1][1].isEmpty()) return true;
        }else if(destination.getY()==4){
            if(!cellMatrix[destination.getX()-1][4].isEmpty() || !cellMatrix[destination.getX()+1][4].isEmpty() || !cellMatrix[destination.getX()][3].isEmpty() || !cellMatrix[destination.getX()+1][3].isEmpty() || !cellMatrix[destination.getX()-1][3].isEmpty()) return true;
        }else
        if(!cellMatrix[destination.getX()+1][destination.getY()].isEmpty() || !cellMatrix[destination.getX()+1][destination.getY()-1].isEmpty() || !cellMatrix[destination.getX()][destination.getY()-1].isEmpty() || !cellMatrix[destination.getX()-1][destination.getY()-1].isEmpty() ||
                !cellMatrix[destination.getX()-1][destination.getY()].isEmpty() || !cellMatrix[destination.getX()-1][destination.getY()+1].isEmpty() || !cellMatrix[destination.getX()][destination.getY()+1].isEmpty() || !cellMatrix[destination.getX()+1][destination.getY()+1].isEmpty())
            return true;
        return false;
    }

}
