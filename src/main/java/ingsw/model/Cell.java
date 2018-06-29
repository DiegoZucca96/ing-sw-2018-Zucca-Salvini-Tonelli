package ingsw.model;

import java.io.Serializable;

/**Author : Alessio Tonelli
 *
 * This class contains the information about the characteristic of a cell of the window
 */

public class Cell implements Serializable {
    private Color color;
    private int number;
    private boolean empty;
    private Die die;
    private final Coordinate coordinate;

    /**
     * Constructor
     * @param number it is the restriction number of the cell
     * @param color it is the restriction color of the cell
     * @param coordinate it is the position of the cell
     */
    public Cell(int number, Color color, Coordinate coordinate){
        this.number= number;
        this.color= color;
        this.coordinate=coordinate;
        insertDie(null);
        this.empty=true;
    }

    /**
     * Simply setter method
     * @param empty it is the boolean value to assign
     */
    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    /**
     * Simply getter method
     * @return true if the cell is empty, otherwise false
     */
    public boolean isEmpty() {

        if(this.die==null)
            empty=true;
        else  empty=false;

        return empty;
    }

    /**
     * This method insert a die into this cell
     * @param die it is the die that has to be inserted
     * @return true if everything goes well, otherwise false
     */
    public boolean insertDie(Die die){
        this.die=die;
        return true;
    }

    /**
     * Simply getter method
     * @return the die contained in this cell
     */
    public Die getDie() {
        return die;
    }

    /**
     * It removes the die from this cell and returns it
     * @return the die contained in this cell
     */
    public Die takeDie(){
        Die newDie = this.die;
        this.die=null;
        return newDie;
    }

    /**
     * Simply getter method
     * @return the coordinate of this cell
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    /**
     * Simply getter method
     * @return the restriction number of the cell
     */
    public int getNumber() {
        return number;
    }

    /**
     * Simply getter method
     * @return the restriction color of the cell
     */
    public Color getColor(){
        return color;
    }

    @Override
    public String toString() {
        return "cell:" + number + "," + color;
    }
}
