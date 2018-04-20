package ingsw;

public class WP1 implements WindowPattern {

    private String title = "Kaleidoscopic Dream";

    private int difficulty;

    private final Cell[][] cellMatrix;


    private int x;

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
    public void addDie(Coordinate coordinate, Die die) {
        if(verifyCellConstraint(coordinate) && verifyDieConstraint(coordinate)){
            cellMatrix[coordinate.getX()][coordinate.getY()].setEmpty(false);
            cellMatrix[coordinate.getX()][coordinate.getY()].insertDie(die);
        }
        else {
            System.out.println("Non puoi inserire il dado in questa posizione");
        }
    }

    @Override
    public void removeDie(Coordinate coordinate) {

    }

    @Override
    public Cell getCell(Coordinate coordinate) {
        return this.cellMatrix[coordinate.getX()][coordinate.getY()];
    }



    private boolean verifyCellConstraint(Coordinate coordinate){
        if (cellMatrix[coordinate.getX()][coordinate.getY()].isEmpty()) {


            return true;
        }
        else
            return false;
    }

    private boolean verifyDieConstraint(Coordinate coordinate){
        if
    }
}
