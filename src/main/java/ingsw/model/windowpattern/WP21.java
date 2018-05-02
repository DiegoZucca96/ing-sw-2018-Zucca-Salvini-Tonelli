package ingsw.model.windowpattern;

import ingsw.model.Cell;
import ingsw.model.Color;
import ingsw.model.Coordinate;
import ingsw.model.Die;

public class WP21 extends WindowPattern {

    private String title = "Chromatic Splendor";

    private int difficulty;

    private final Cell[][] cellMatrix;

    private boolean wpEmpty;



    public WP21(){

        cellMatrix = new Cell[][]{
                {new Cell(0, null, new Coordinate(0,0)), new Cell(0, null, new Coordinate(0,1)), new Cell(0, Color.GREEN, new Coordinate(0,2)), new Cell(0, null, new Coordinate(0,3)), new Cell(0, null, new Coordinate(0,4))},
                {new Cell(2, null, new Coordinate(1,0)), new Cell(0, Color.YELLOW, new Coordinate(1,1)), new Cell(5, null, new Coordinate(1,2)), new Cell(0, Color.BLUE, new Coordinate(1,3)), new Cell(1, null, new Coordinate(1,4))},
                {new Cell(0, null, new Coordinate(2,0)), new Cell(0, Color.RED, new Coordinate(2,1)), new Cell(3, null, new Coordinate(2,2)), new Cell(0, Color.VIOLET, new Coordinate(2,3)), new Cell(0, null, new Coordinate(2,4))},
                {new Cell(1, null, new Coordinate(3,0)), new Cell(0, null, new Coordinate(3,1)), new Cell(6, null, new Coordinate(3,2)), new Cell(0, null, new Coordinate(3,3)), new Cell(4, null, new Coordinate(3,4))}
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

    @Override
    public void setWpEmpty (boolean wpEmpty) { this.wpEmpty = wpEmpty;}


    public int countEmptyCells(){
        return super.countEmptyCells(getCellMatrix());
    }


    public boolean addDie (Coordinate destination, Die die){
        return super.addDie(destination, die, getCellMatrix());
    }

    public boolean removeDie(Coordinate destination) {
        return super.removeDie(destination, getCellMatrix());
    }


    public Cell getCell(Coordinate coordinate) {
        return super.getCell(coordinate, cellMatrix);
    }


    public boolean verifyCellColorConstraint(Coordinate destination, Die die){
        return super.verifyCellColorConstraint(destination, die, cellMatrix);
    }

    public boolean verifyCellNumberConstraint(Coordinate destination, Die die){
        return super.verifyCellNumberConstraint(destination, die, cellMatrix);
    }


    public boolean verifyDieColorConstraint(Coordinate destination, Die die){
        return super.verifyDieColorConstraint( destination, die, cellMatrix);
    }


    public boolean verifyDieNumberConstraint(Coordinate destination, Die die){
        return super.verifyDieColorConstraint( destination, die, cellMatrix);
    }

    public boolean verifyPosition(Coordinate destination){
        return super.verifyPosition(destination, cellMatrix);
    }

    public boolean verifyOnBoard(Coordinate destination) { return super.verifyOnBoard(destination); }

}
