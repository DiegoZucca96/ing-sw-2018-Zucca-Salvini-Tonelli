package WindowPattern;

import ingsw.*;

public class WP1 extends WindowPattern{

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

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public int getDifficulty() {
        return this.difficulty;
    }

    @Override
    public boolean isWpEmpty() {
        return this.wpEmpty;
    }

    @Override
    public Cell[][] getCellMatrix() {
        return this.cellMatrix;
    }


    public int countEmptyCells(){
        return super.countEmptyCells(getCellMatrix());
    }


    public boolean addDie (Coordinate destination, Die die){
        return super.addDie(destination, die, isWpEmpty(), getCellMatrix());
    }

    public boolean removeDie(Coordinate destination) {
        return super.removeDie(destination, getCellMatrix());
    }


    public Cell getCell(Coordinate coordinate) {
        return super.getCell(coordinate, cellMatrix);
    }


    public boolean verifyCellConstraint(Coordinate destination, Die die){
        return super.verifyCellConstraint(destination, die, cellMatrix);
    }


    public boolean verifyDieConstraint(Coordinate destination, Die die){
        return super.verifyDieConstraint( destination, die, cellMatrix);
    }



    public boolean verifyPosition(Coordinate destination){
        return super.verifyPosition(destination, cellMatrix);
    }


}