package ingsw.model;

import java.io.Serializable;

/**Author : Alessio Tonelli
 *
 * This class contains the coordinate X and Y used to select the correct objects
 */
public class Coordinate implements Serializable {

    private final int x;
    private final int y;

    /**
     * Constructor
     * @param x it is the horizontal coordinate
     * @param y it is the vertical coordinate
     */
    public Coordinate(int x, int y){
        this.x=x;
        this.y=y;
    }

    /**
     * Simply getter method
     * @return the value of x
     */
    public int getX() {
        return x;
    }

    /**
     * Simply getter method
     * @return the value of y
     */
    public int getY() {
        return y;
    }
}
