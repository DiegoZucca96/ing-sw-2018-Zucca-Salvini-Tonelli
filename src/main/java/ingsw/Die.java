package ingsw;

public class Die {

    private final Color color;
    private Cell cell;

    public Die(Cell cell, Color color){
        this.setCell(cell);
        this.color=color;
    }

    public Color getColor() {
        return color;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }
}
