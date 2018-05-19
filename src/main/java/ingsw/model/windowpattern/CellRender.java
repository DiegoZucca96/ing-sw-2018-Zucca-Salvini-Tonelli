package ingsw.model.windowpattern;


/**Author : Alessio Tonelli
 *
 *
 * TAKE CELL FROM XML FILE AND SEND IT TO THE GUI AND MODEL
 * */
public class CellRender implements java.io.Serializable {

    private String row;
    private String column;
    private String number;
    private String color;

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


}
