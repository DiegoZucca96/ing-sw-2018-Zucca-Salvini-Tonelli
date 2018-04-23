package WindowPattern;

import ingsw.*;

public class WP1 implements WindowPattern {

    private String title = "Kaleidoscopic Dream";

    private int difficulty;

    private final Cell[][] cellMatrix;

    private boolean wpEmpty;

    public WP1(){

        cellMatrix = new Cell[][]{
                {new Cell(0, Color.YELLOW, new Coordinate(0,0)), new Cell(0, Color.BLUE, new Coordinate(0,1)), new Cell(0, null, new Coordinate(0,2)), new Cell(0, null, new Coordinate(0,3)), new Cell(1, null, new Coordinate(0,4))},
                {new Cell(0, Color.GREEN, new Coordinate(1,0)), new Cell(0, null, new Coordinate(1,1)), new Cell(5, null, new Coordinate(1,2)), new Cell(0, null, new Coordinate(1,3)), new Cell(4, null, new Coordinate(1,4))},
                {new Cell(3, null, new Coordinate(2,0)), new Cell(0, null, new Coordinate(2,1)), new Cell(0, Color.RED, new Coordinate(2,2)), new Cell(0, null, new Coordinate(2,3)), new Cell(0, Color.GREEN, new Coordinate(2,4))},
                {new Cell(2, null, new Coordinate(3,0)), new Cell(0, null, new Coordinate(3,1)), new Cell(0, null, new Coordinate(3,2)), new Cell(0, Color.BLUE, new Coordinate(3,3)), new Cell(0, Color.YELLOW, new Coordinate(3,4))}
        };
        this.difficulty=4;
        this.wpEmpty=true;
    }

    public Cell[][] getCellMatrix() {
        return cellMatrix;
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
                    if(verifyCellConstraint(destination, die) && verifyDieConstraint(destination, die)){
                        cellMatrix[destination.getX()][destination.getY()].setEmpty(false);
                        cellMatrix[destination.getX()][destination.getY()].insertDie(die);
                        this.wpEmpty=false;
                    }
                    else
                        System.out.println("Non puoi inserire il dado in questa posizione");
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
        int x =destination.getX();
        int y =destination.getY();
        if(cellMatrix[x][y].getColor()==null &&cellMatrix[x][y].getNumber()==0)
            return true;
        else if (cellMatrix[x][y].getColor()== die.getColor() && cellMatrix[x][y].getNumber()== 0) {
            return true;
        } else if (cellMatrix[x][y].getColor()== null && cellMatrix[x][y].getNumber()== die.getNumber())
            return true;
        else
            return false;
    }

    //VERIFICA CHE LE CELLE ORTOGONALMENTE ADIACENTI AL DADO CHE SI VUOLE POSIZIONARE NON ABBIANO DADI CON LO STESSO COLORE O CON LO STESSO NUMERO
    public boolean verifyDieConstraint(Coordinate destination, Die die){
        int x =destination.getX();
        int y =destination.getY();
        if((x+1<4 && cellMatrix[x+1][y].getDie()!=null && cellMatrix[x+1][y].getDie().getColor()==die.getColor()) || (y+1<4 && cellMatrix[x][y+1].getDie()!=null && cellMatrix[x][y+1].getDie().getColor()==die.getColor()) || (x-1>0 && cellMatrix[x-1][y].getDie()!=null && cellMatrix[x-1][y].getDie().getColor()==die.getColor())|| (y-1>0 && cellMatrix[x][y-1].getDie()!=null && cellMatrix[x][y-1].getDie().getColor()==die.getColor()))
            return false;
        else if((x+1<4 && cellMatrix[x+1][y].getDie()!=null && cellMatrix[x+1][y].getDie().getNumber()==die.getNumber()) || (y+1<4 && cellMatrix[x][y+1].getDie()!=null && cellMatrix[x][y+1].getDie().getNumber()==die.getNumber()) || (x-1>0 && cellMatrix[x-1][y].getDie()!=null && cellMatrix[x-1][y].getDie().getNumber()==die.getNumber())|| (y-1>0 && cellMatrix[x][y-1].getDie()!=null && cellMatrix[x][y-1].getDie().getNumber()==die.getNumber()))
            return false;
        else
            return true;
    }


    //VERIFICA CHE IL DADO CHE STAI INSERENDO TOCCA ALMENO UN DADO ATTORNO; caso base i vertici della finestra
    public boolean verifyPosition(Coordinate destination){
        int x =destination.getX();
        int y =destination.getY();
        if((x==0 && y==0) && (!cellMatrix[1][0].isEmpty() || !cellMatrix[0][1].isEmpty() || !cellMatrix[1][1].isEmpty())) return true;
        else if((x==3 && y==0) && (!cellMatrix[2][0].isEmpty() || !cellMatrix[2][1].isEmpty() || !cellMatrix[3][1].isEmpty())) return true;
        else if((x==0 && y==4) && (!cellMatrix[0][3].isEmpty() || !cellMatrix[1][3].isEmpty() || !cellMatrix[1][4].isEmpty())) return true;
        else if((x==3 && y==4) && (!cellMatrix[2][4].isEmpty() || !cellMatrix[2][3].isEmpty() || !cellMatrix[3][3].isEmpty())) return true;
        else if((x==0)&& (!cellMatrix[0][y-1].isEmpty() || !cellMatrix[0][y+1].isEmpty() || !cellMatrix[1][y].isEmpty() || !cellMatrix[1][y+1].isEmpty() || !cellMatrix[1][y-1].isEmpty())) return true;
        else if((x==3) && (!cellMatrix[3][y-1].isEmpty() || !cellMatrix[3][y+1].isEmpty() || !cellMatrix[2][y].isEmpty() || !cellMatrix[2][y+1].isEmpty() || !cellMatrix[2][y-1].isEmpty())) return true;
        else if((y==0) && (!cellMatrix[x-1][0].isEmpty() || !cellMatrix[x+1][0].isEmpty() || !cellMatrix[x][1].isEmpty() || !cellMatrix[x+1][1].isEmpty() || !cellMatrix[x-1][1].isEmpty())) return true;
        else if((y==4) && (!cellMatrix[x-1][4].isEmpty() || !cellMatrix[x+1][4].isEmpty() || !cellMatrix[x][3].isEmpty() || !cellMatrix[x+1][3].isEmpty() || !cellMatrix[x-1][3].isEmpty())) return true;
        else if(!cellMatrix[x+1][y].isEmpty() || !cellMatrix[x+1][y-1].isEmpty() || !cellMatrix[x][y-1].isEmpty() || !cellMatrix[x-1][y-1].isEmpty() || !cellMatrix[x-1][y].isEmpty() || !cellMatrix[x-1][y+1].isEmpty() || !cellMatrix[x][y+1].isEmpty() || !cellMatrix[x+1][y+1].isEmpty())
            return true;
        else
            return false;
    }

}