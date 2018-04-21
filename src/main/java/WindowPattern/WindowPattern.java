package WindowPattern;

import ingsw.Cell;
import ingsw.Coordinate;
import ingsw.Die;

public interface WindowPattern {
    void removeDie(Coordinate coordinate);
    void addDie(Coordinate coordinate, Die die);
    Cell getCell(Coordinate coordinate);
}
