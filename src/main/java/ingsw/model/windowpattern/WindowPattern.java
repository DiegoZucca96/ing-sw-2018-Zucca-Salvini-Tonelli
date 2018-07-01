package ingsw.model.windowpattern;

import ingsw.model.InfoCell;
import ingsw.model.*;
import ingsw.observers.Observer;
import ingsw.observers.WindowPatternObserver;
import java.io.Serializable;
import java.util.List;

/**Author : Alessio Tonelli - Diego Zucca
 *
 * Create and Constraint of Window Pattern
 *
 * */

public class WindowPattern implements Serializable {

    private String title ;
    private Integer difficulty;
    private int id;
    private final Cell[][] cellMatrix;
    private boolean wpEmpty;
    private Observer viewObserver;

    /**
     * Constructor
     * @param parser it contains all informations about the window
     * @param select it is the number of the window
     */
    public WindowPattern(SAXParser parser, int select ){

        this.id=select;
        List<Cell> readConfig = parser.readConfig("src/main/java/ingsw/model/windowpattern/wpxml/wp"+select+".xml");
        InfoWindow readInfo = parser.readInfo("src/main/java/ingsw/model/windowpattern/wpxml/wp"+select+".xml");
        cellMatrix= new Cell[4][5];

        int count=0;
        for(int row = 0; row < 4; row++){
            for ( int column = 0; column < 5; column ++ ){
                cellMatrix[row][column]= readConfig.get(count);
                count++;
            }
        }
        this.title=readInfo.getName();
        this.difficulty=Integer.parseInt(readInfo.getDifficulty());
        this.wpEmpty=true;
        viewObserver = new WindowPatternObserver();
    }

    /**
     * Simply getter method
     * @return the title of the window
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Simply getter method
     * @return the number of the window
     */
    public int getId() {
        return id;
    }

    /**
     * Simply getter method
     * @return the difficulty of the window
     */
    public int getDifficulty() {
        return this.difficulty;
    }

    /**
     * Simply getter method
     * @return true if the window is empty, otherwise false
     */
    public boolean isWpEmpty() {
        return this.wpEmpty;
    }

    /**
     * Simply getter method
     * @return the window's matrix of cells
     */
    public Cell[][] getCellMatrix() {
        return this.cellMatrix;
    }

    /**
     * Simply setter method
     * @param wpEmpty it is the boolean value to assign
     */
    public void setWpEmpty (boolean wpEmpty) { this.wpEmpty = wpEmpty;}

    /**
     * This method is used to count the empty cells
     * @param cellMatrix it is the window's matrix of cells
     * @return the number of empty cells
     */
    public int countEmptyCells(Cell[][] cellMatrix){
        int count=0;
        for(int i=0; i<5; i++){
            for(int j=0; j<4; j++){
                if(cellMatrix[j][i].isEmpty()) count++;
            }
        }
        return count;
    }

    /**
     * This method is used to obtain the sum of the dice which have the same color of the private card
     * @param color it is the color of the private card
     * @param cellMatrix it is the window's matrix of cells
     * @return the sum obtained
     */
    public int countDie(Color color, Cell[][] cellMatrix){
        int count =0;
        for(int i=0; i<5; i++){
            for(int j=0; j<4; j++){
                if(cellMatrix[j][i].getDie()!=null){
                    if(String.valueOf(cellMatrix[j][i].getDie().getColor()).equalsIgnoreCase(String.valueOf(color)))
                        count = count + cellMatrix[j][i].getDie().getNumber();
                }
            }
        }
        return count;
    }

    /**
     * This is the method that controls all the placement restrictions.
     * If they are all respected the die will be placed at its destination.
     * @param destination it is the destination's coordinate
     * @param die it is the die that has to be placed
     * @param cellMatrix it is the window's matrix of cells
     * @return true if the die can be placed, otherwise false
     */
    public boolean addDie(Coordinate destination, Die die, Cell[][] cellMatrix) {
        if(isWpEmpty()){
            if(verifyOnBoard(destination)){
                if (cellMatrix[destination.getX()][destination.getY()].isEmpty()){
                    if(verifyCellColorConstraint(destination, die, cellMatrix) && verifyCellNumberConstraint(destination, die, cellMatrix)){
                        cellMatrix[destination.getX()][destination.getY()].setEmpty(false);
                        cellMatrix[destination.getX()][destination.getY()].insertDie(die);
                        setWpEmpty(false);
                        notifyViewObserver();
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
                    notifyViewObserver();
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

    /**
     * It removes a die from a specific cell.
     * @param destination it is the destination's coordinate
     * @param cellMatrix it is the window's matrix of cells
     * @return true if everything goes well, otherwise false
     */
    public boolean removeDie(Coordinate destination, Cell[][] cellMatrix) {
        if (getCell(destination, cellMatrix).isEmpty()) {
            System.out.println("Cella vuota");
            return false;
        }else {
            getCell(destination, cellMatrix).takeDie();
            notifyViewObserver();
            return true;
        }
    }

    /**
     * Simply getter method
     * @param diePosition it is the position of the returned die
     * @return a die
     */
    public Die getDie(Coordinate diePosition){
        return getCell(diePosition, cellMatrix).getDie();
    }

    /**
     * Simply getter method
     * @param coordinate it is the coordinate of the cell
     * @param cellMatrix it is the window's matrix of cells
     * @return a cell
     */
    public Cell getCell(Coordinate coordinate, Cell[][] cellMatrix) {
        return cellMatrix[coordinate.getX()][coordinate.getY()];
    }

    /**
     * This is the cell color restriction, it verifies that the destination cell has the same color as the selected die
     * @param destination it is the destination's coordinate
     * @param die it is the die that has to be placed
     * @param cellMatrix it is the window's matrix of cells
     * @return true if the restriction is respected, otherwise false
     */
    public boolean verifyCellColorConstraint(Coordinate destination, Die die, Cell[][] cellMatrix){
        int x =destination.getX();
        int y =destination.getY();
        if(String.valueOf(cellMatrix[x][y].getColor()).equalsIgnoreCase("WHITE"))
            return true;
        else if (cellMatrix[x][y].getColor()== die.getColor()) {
            return true;
        }
        else
            return false;
    }

    /**
     * This is the cell number restriction, it verifies that the destination cell has the same number as the selected die
     * @param destination it is the destination's coordinate
     * @param die it is the die that has to be placed
     * @param cellMatrix it is the window's matrix of cells
     * @return true if the restriction is respected, otherwise false
     */
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

    /**
     * This is the die color restriction.
     * It verifies that the orthogonally adjacent cells at the destination do not have dice with the same color
     * @param destination it is the destination's coordinate
     * @param die it is the die that has to be placed
     * @param cellMatrix it is the window's matrix of cells
     * @return true if the restriction is respected, otherwise false
     */
    public boolean verifyDieColorConstraint(Coordinate destination, Die die, Cell[][] cellMatrix){
        int x =destination.getX();
        int y =destination.getY();
        if((x+1<4 && cellMatrix[x+1][y].getDie()!=null && String.valueOf(cellMatrix[x+1][y].getDie().getColor()).equalsIgnoreCase(String.valueOf(die.getColor()))))
            return false;
        if (y+1<5 && cellMatrix[x][y+1].getDie()!=null && String.valueOf(cellMatrix[x][y+1].getDie().getColor()).equalsIgnoreCase(String.valueOf(die.getColor())))
            return false;
        if(x-1>=0 && cellMatrix[x-1][y].getDie()!=null && String.valueOf(cellMatrix[x-1][y].getDie().getColor()).equalsIgnoreCase(String.valueOf(die.getColor())))
            return false;
        if(y-1>=0 && cellMatrix[x][y-1].getDie()!=null && String.valueOf(cellMatrix[x][y-1].getDie().getColor()).equalsIgnoreCase(String.valueOf(die.getColor())))
            return false;
        return true;
    }

    /**
     * This is the die number restriction.
     * It verifies that the orthogonally adjacent cells at the destination do not have dice with the same number
     * @param destination it is the destination's coordinate
     * @param die it is the die that has to be placed
     * @param cellMatrix it is the window's matrix of cells
     * @return true if the restriction is respected, otherwise false
     */
    public boolean verifyDieNumberConstraint(Coordinate destination, Die die, Cell[][] cellMatrix){
        int x =destination.getX();
        int y =destination.getY();
        if((x+1<4 && cellMatrix[x+1][y].getDie()!=null && cellMatrix[x+1][y].getDie().getNumber()==die.getNumber()) || (y+1<5 && cellMatrix[x][y+1].getDie()!=null && cellMatrix[x][y+1].getDie().getNumber()==die.getNumber()) || (x-1>=0 && cellMatrix[x-1][y].getDie()!=null && cellMatrix[x-1][y].getDie().getNumber()==die.getNumber())|| (y-1>=0 && cellMatrix[x][y-1].getDie()!=null && cellMatrix[x][y-1].getDie().getNumber()==die.getNumber()))
            return false;
        else return true;
    }

    /**
     * This is the position restriction.
     * It verifies that the destination cell has at least an other cell that touches the first one orthogonally or diagonally,
     * and this cell is not empty.
     * @param destination it is the destination's coordinate
     * @param cellMatrix it is the window's matrix of cells
     * @return true if the restriction is respected, otherwise false
     */
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

    /**
     * It verifies that the destination cell is on the board of the window
     * @param destination it is the destination's coordinate
     * @return true if the restriction is respected, otherwise false
     */
    public boolean verifyOnBoard(Coordinate destination) {
        if(destination.getX()==0 || destination.getX()==3 || destination.getY()==0 || destination.getY()==4) return true;
        else return false;
    }

    /**
     * This method convert a window into an unique string that contains all its informations
     * @return a string
     */
    @Override
    public String toString() {
        String result="windowpattern:";
        result.concat("name:"+getTitle()+",");
        result.concat("diff:"+getDifficulty());
        for(int row=0; row < 4; row ++){
            for(int col=0; col<5; col++){
                result.concat(",cell:row"+row+",column:"+col+",number:"+getCell(new Coordinate(row, col), getCellMatrix()).getNumber()+",color"+getCell(new Coordinate(row, col), getCellMatrix()).getColor());
            }
        }
        return result;
    }

    /**
     * This method convert a window into an InfoCell object
     * @return an InfoCell object
     */
    public InfoCell [][] toMatrix(){
        InfoCell[][] matrix = new InfoCell[4][5];
        for(int row = 0; row < 4; row++) {
            for (int column = 0; column < 5; column++) {
                Cell cell = getCell(new Coordinate(row, column), getCellMatrix());
                Die die = cell.getDie();
                String dieStr;
                if(die == null) dieStr=null;
                else dieStr=die.toString();
                InfoCell x = new InfoCell(cell.getNumber(), cell.getColor(), dieStr);
                matrix[row][column] = x;
            }
        }
        return matrix;
    }

    /**
     * This method updates changes in this object into the ViewData instance
     */
    public ViewWP toViewWP(){
        ViewWP viewWP = new ViewWP();
        viewWP.setDifficulty(Integer.toString(getDifficulty()));
        viewWP.setName(getTitle());
        viewWP.setNumberWP(getId());
        viewWP.setWps(toMatrix());
        return viewWP;
    }

    /**
     * This method notifies changes in this object to viewObserver
     */
    public void notifyViewObserver(){
        viewObserver.update(this, ViewData.instance());
    }

}
