package ingsw;

public interface WindowPattern {
    void removeDie(Coordinate coordinate);
    void addDie(Coordinate coordinate, Die die);
    Cell getCell(Coordinate coordinate);
}
