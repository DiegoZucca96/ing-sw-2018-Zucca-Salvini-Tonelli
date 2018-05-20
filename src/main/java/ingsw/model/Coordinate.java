package ingsw.model;

import java.io.Serializable;

/**Author : Alessio Tonelli
 *
 *
 * */

public class Coordinate implements Serializable {

    private final int x;
    private final int y;

    public Coordinate(int x, int y){
        this.x=x;
        this.y=y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
