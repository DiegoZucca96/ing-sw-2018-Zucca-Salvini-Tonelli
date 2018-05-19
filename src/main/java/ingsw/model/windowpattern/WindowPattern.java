package ingsw.model.windowpattern;



import ingsw.model.*;

import java.util.List;


/**Author : Alessio Tonelli _ Diego Zucca
 *
 * Create and Constraint of Window Pattern
 *
 * */

public class WindowPattern {


    private String title ;

    private int difficulty;

    private final Cell[][] cellMatrix;

    private boolean wpEmpty;

    public WindowPattern(){
        cellMatrix= new Cell[5][4];
    }

    public String getTitle() {
        return this.title;
    }


    public int getDifficulty() {
        return this.difficulty;
    }


    public boolean isWpEmpty() {
        return this.wpEmpty;
    }


    public Cell[][] getCellMatrix() {
        return this.cellMatrix;
    }


    public void setWpEmpty (boolean wpEmpty) { this.wpEmpty = wpEmpty;}



    public int countEmptyCells(Cell[][] cellMatrix){
        int count=0;
        for(int i=0; i<5; i++){
            for(int j=0; j<4; j++){
                if(cellMatrix[j][i].isEmpty()) count++;
            }
        }
        return count;
    }

    public int countDie(Color color, Cell[][] cellMatrix){ // Si potrebbe fare un unico metodo per entrambi? E' ripetitivo
        int count =0;
        for(int i=0; i<5; i++){
            for(int j=0; j<4; j++){
                if(cellMatrix[j][i].getColor() == color) count++;
            }
        }
        return count;

    }


    public boolean addDie(Coordinate destination, Die die, Cell[][] cellMatrix) {

        if(isWpEmpty()){
            if(verifyOnBoard(destination)){
                if (cellMatrix[destination.getX()][destination.getY()].isEmpty()){
                    if(verifyCellColorConstraint(destination, die, cellMatrix) && verifyCellNumberConstraint(destination, die, cellMatrix) &&verifyDieColorConstraint(destination, die, cellMatrix) && verifyDieNumberConstraint(destination, die, cellMatrix)){
                        cellMatrix[destination.getX()][destination.getY()].setEmpty(false);
                        cellMatrix[destination.getX()][destination.getY()].insertDie(die);
                        setWpEmpty(false);
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
                if(verifyCellColorConstraint(destination, die, cellMatrix) && verifyCellNumberConstraint(destination, die, cellMatrix) &&verifyDieColorConstraint(destination, die, cellMatrix) && verifyDieNumberConstraint(destination, die, cellMatrix)){
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


    //VERIFICA CHE LA CELLA DI DESTINAZIONE ABBIA COLORE UGUALE AL DADO
    public boolean verifyCellColorConstraint(Coordinate destination, Die die, Cell[][] cellMatrix){
        int x =destination.getX();
        int y =destination.getY();
        if(cellMatrix[x][y].getColor()==null)
            return true;
        else if (cellMatrix[x][y].getColor()== die.getColor()) {
            return true;
        }
        else
            return false;
    }

    //VERIFICA CHE LA CELLA DI DESTINAZIONE ABBIA NUMERO UGUALE AL DADO
    public boolean verifyCellNumberConstraint(Coordinate destination, Die die, Cell[][] cellMatrix){
        int x =destination.getX();
        int y =destination.getY();
        if(cellMatrix[x][y].getNumber()==0)
            return true;
        else if (cellMatrix[x][y].getNumber()== die.getNumber())
            return true;
        else
            return false;
    }





    //VERIFICA CHE LE CELLE ORTOGONALMENTE ADIACENTI AL DADO CHE SI VUOLE POSIZIONARE NON ABBIANO DADI CON LO STESSO COLORE
    public boolean verifyDieColorConstraint(Coordinate destination, Die die, Cell[][] cellMatrix){
        int x =destination.getX();
        int y =destination.getY();
        if((x+1<4 && cellMatrix[x+1][y].getDie()!=null && cellMatrix[x+1][y].getDie().getColor()==die.getColor()) || (y+1<4 && cellMatrix[x][y+1].getDie()!=null && cellMatrix[x][y+1].getDie().getColor()==die.getColor()) || (x-1>0 && cellMatrix[x-1][y].getDie()!=null && cellMatrix[x-1][y].getDie().getColor()==die.getColor())|| (y-1>0 && cellMatrix[x][y-1].getDie()!=null && cellMatrix[x][y-1].getDie().getColor()==die.getColor()))
            return false;
        else
            return true;
    }


    //VERIFICA CHE LE CELLE ORTOGONALMENTE ADIACENTI AL DADO CHE SI VUOLE POSIZIONARE NON ABBIANO DADI CON LO STESSO NUMERO
    public boolean verifyDieNumberConstraint(Coordinate destination, Die die, Cell[][] cellMatrix){
        int x =destination.getX();
        int y =destination.getY();
        if((x+1<4 && cellMatrix[x+1][y].getDie()!=null && cellMatrix[x+1][y].getDie().getNumber()==die.getNumber()) || (y+1<4 && cellMatrix[x][y+1].getDie()!=null && cellMatrix[x][y+1].getDie().getNumber()==die.getNumber()) || (x-1>0 && cellMatrix[x-1][y].getDie()!=null && cellMatrix[x-1][y].getDie().getNumber()==die.getNumber())|| (y-1>0 && cellMatrix[x][y-1].getDie()!=null && cellMatrix[x][y-1].getDie().getNumber()==die.getNumber()))
            return false;
        else return true;
    }


    //VERIFICA CHE IL DADO CHE STAI INSERENDO TOCCA ALMENO UN DADO ATTORNO; caso base i vertici della finestra
    public boolean verifyPosition(Coordinate destination, Cell[][] cellMatrix) {
        int x = destination.getX();
        int y = destination.getY();
        if (x==0 && y==0) {
            if (!cellMatrix[1][0].isEmpty() || !cellMatrix[0][1].isEmpty() || !cellMatrix[1][1].isEmpty()) return true;
            else return false;
        } else if (x==3 && y==0) {
            if (!cellMatrix[2][0].isEmpty() || !cellMatrix[2][1].isEmpty() || !cellMatrix[3][1].isEmpty()) return true;
            else return false;
        } else if (x==0 && y==4) {
            if (!cellMatrix[0][3].isEmpty() || !cellMatrix[1][3].isEmpty() || !cellMatrix[1][4].isEmpty()) return true;
            else return false;
        } else if (x==3 && y==4) {
            if (!cellMatrix[2][4].isEmpty() || !cellMatrix[2][3].isEmpty() || !cellMatrix[3][3].isEmpty()) return true;
            else return false;
        }else if (x==0) {
            if (!cellMatrix[0][y-1].isEmpty() || !cellMatrix[0][y+1].isEmpty() || !cellMatrix[1][y].isEmpty() || !cellMatrix[1][y+1].isEmpty() || !cellMatrix[1][y-1].isEmpty()) return true;
            else return false;
        }else if (x==3) {
            if (!cellMatrix[3][y-1].isEmpty() || !cellMatrix[3][y+1].isEmpty() || !cellMatrix[2][y].isEmpty() || !cellMatrix[2][y+1].isEmpty() || !cellMatrix[2][y-1].isEmpty()) return true;
            else return false;
        }else if (y==0) {
            if (!cellMatrix[x-1][0].isEmpty() || !cellMatrix[x+1][0].isEmpty() || !cellMatrix[x][1].isEmpty() || !cellMatrix[x+1][1].isEmpty() || !cellMatrix[x-1][1].isEmpty()) return true;
            else return false;
        }else if (y==4) {
            if (!cellMatrix[x-1][4].isEmpty() || !cellMatrix[x+1][4].isEmpty() || !cellMatrix[x][3].isEmpty() || !cellMatrix[x+1][3].isEmpty() || !cellMatrix[x-1][3].isEmpty()) return true;
            else return false;
        }else if (!cellMatrix[x+1][y].isEmpty() || !cellMatrix[x+1][y-1].isEmpty() || !cellMatrix[x][y-1].isEmpty() || !cellMatrix[x-1][y-1].isEmpty() || !cellMatrix[x-1][y].isEmpty() || !cellMatrix[x-1][y+1].isEmpty() || !cellMatrix[x][y+1].isEmpty() || !cellMatrix[x+1][y+1].isEmpty())
            return true;
        else
            return false;
    }

    public boolean verifyOnBoard(Coordinate destination) {
        if(destination.getX()==0 || destination.getX()==3 || destination.getY()==0 || destination.getY()==4) return true;
        else return false;
    }


    public static void generateWindowPattern(InitializerView init){
        RandomGenerator rg = new RandomGenerator(24);

        //INITIALIZE THE PARSER
        SAXParser read = new SAXParser();

        for(int i=0;i<4;i++){
            int select = rg.random();

            //READ FROM FILE
            List<CellRender> readConfig = read.readConfig("src/main/java/ingsw/model/windowpattern/wp"+select+".xml");

            List<InfoWindow> readInfo = read.readInfo("src/main/java/ingsw/model/windowpattern/wp"+select+".xml");

            for (CellRender cell : readConfig) {

                //CREAT CELL IN THE VIEW
                init.getImages().add(cell);

                //CREAT CELL IN THE MODEL
                Cell cellWp = new Cell(Integer.parseInt(cell.getNumber()), Color.valueOf(cell.getColor()), new Coordinate(Integer.parseInt(cell.getRow()), Integer.parseInt(cell.getColumn())));

            }
            //SEND INFO (NAME AND DIFFICULTY) TO VIEW
            for(InfoWindow info : readInfo){
                init.getInfo().add(info);
            }
        }
    }
}
