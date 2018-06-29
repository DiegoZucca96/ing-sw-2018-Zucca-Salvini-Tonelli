package ingsw.model;

import java.io.Serializable;

/**
 * Class that contains the info of a cell
 */
public class InfoCell  implements Serializable {

    private int num;
    private Color color;
    private String die;

    /**
     * Constructor
     * @param num it is the restriction number of the cell
     * @param color it is the restriction color of the cell
     * @param die it is the die contained into the cell
     */
    public InfoCell(int num, Color color, String die){
        this.num=num;
        this.color=color;
        this.die=die;
    }

    /**
     * Simply getter method
     * @return the restriction number of the cell
     */
    public int getNum() {
        return num;
    }

    /**
     * Simply getter method
     * @return the restriction color of the cell
     */
    public Color getColor() {
        return color;
    }

    /**
     * Simply getter method
     * @return the die contained in this cell
     */
    public String getDie() {
        return die;
    }

}
