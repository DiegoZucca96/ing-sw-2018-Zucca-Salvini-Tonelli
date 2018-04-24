package WindowPattern;

import ingsw.Cell;
import ingsw.Coordinate;
import ingsw.Die;

public abstract class WindowPattern {


    public abstract String getTitle();


    public abstract int getDifficulty();

    public abstract boolean isWpEmpty();

    public abstract Cell[][] getCellMatrix();



    public int countEmptyCells(Cell[][] cellMatrix){
        int count=0;
        for(int i=0; i<5; i++){
            for(int j=0; j<4; j++){
                if(cellMatrix[j][i].isEmpty()) count++;
            }
        }
        return count;
    }


    public boolean addDie(Coordinate destination, Die die, boolean wpEmpty, Cell[][] cellMatrix) {
        if(wpEmpty){
            if(destination.getX()==0 || destination.getX()==3 || destination.getY()==0 || destination.getY()==4){
                if (cellMatrix[destination.getX()][destination.getY()].isEmpty()){
                    if(verifyCellConstraint(destination, die, cellMatrix) && verifyDieConstraint(destination, die, cellMatrix)){
                        cellMatrix[destination.getX()][destination.getY()].setEmpty(false);
                        cellMatrix[destination.getX()][destination.getY()].insertDie(die);
                        wpEmpty=false;
                        return true;
                    }
                    else{
                        System.out.println("Non puoi inserire il dado in questa posizione");
                        return false;
                    }
                }else{
                    System.out.println("Posizione già occupata da un altro dado");
                    return false;
                }
            }else{
                System.out.println("Non puoi inserire il dado in questa posizione perchè non sei sul bordo");
                return false;
            }

        }else if (cellMatrix[destination.getX()][destination.getY()].isEmpty()){
            if(verifyPosition(destination, cellMatrix)) {
                if(verifyCellConstraint(destination, die, cellMatrix) && verifyDieConstraint(destination, die, cellMatrix)){
                    cellMatrix[destination.getX()][destination.getY()].setEmpty(false);
                    cellMatrix[destination.getX()][destination.getY()].insertDie(die);
                    return true;
                }
                else{
                    System.out.println("Non puoi inserire il dado in questa posizione");
                    return false;
                }
            }else{
                System.out.println("Il dado non tocca nessun altro dado");
                return false;
            }
        }else{
            System.out.println("Posizione già occupata da un altro dado");
            return false;
        }
    }


    public boolean removeDie(Coordinate destination, Cell[][] cellMatrix) {
        if (getCell(destination, cellMatrix).isEmpty()) {
            System.out.println("Cella vuota");
            return false;
        }else {
            getCell(destination, cellMatrix).takeDie();
            return true;
        }
    }



    public Cell getCell(Coordinate coordinate, Cell[][] cellMatrix) {
        return cellMatrix[coordinate.getX()][coordinate.getY()];
    }


    //VERIFICA CHE LA CELLA DI DESTINAZIONE ABBIA COLORE O NUMERO UGUALE AL DADO
    public boolean verifyCellConstraint(Coordinate destination, Die die, Cell[][] cellMatrix){
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
    public boolean verifyDieConstraint(Coordinate destination, Die die, Cell[][] cellMatrix){
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
    public boolean verifyPosition(Coordinate destination, Cell[][] cellMatrix){
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
