package ingsw;

public interface WindowPattern {
    void removeDie(int x, int y);
    void addDie(int x, int y);
    Cell getCell(Coordinate coordinate);
}
