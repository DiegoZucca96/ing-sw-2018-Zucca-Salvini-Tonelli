package ingsw;

public class Die {

    private final Color color;
    private Cell cell;
    private int number;

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

    public int getNumber(){
        return number;
    }

    public int setNumber(int number) {
        switch (number) {
            case 1: {
                this.number=6;
                break;;
            }case 2: {
                this.number=5;
                break;;
            }case 3: {
                this.number=4;
                break;;
            }case 4: {
                this.number=3;
                break;;
            }case 5: {
                this.number=2;
                break;;
            }case 6: {
                this.number=1;
                break;;
            }
            default: break;
        }

        return this.number;
    }


}
