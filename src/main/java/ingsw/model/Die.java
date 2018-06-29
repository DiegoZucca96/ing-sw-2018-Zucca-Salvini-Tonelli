package ingsw.model;

/**Author : Alessio Tonelli
 *
 * This class contains the informations about a generic die
 * */

public class Die{

    private final Color color;
    private int number;

    /**
     * Constructor
     * @param number it is the die's number
     * @param color it is the die's color
     */
    public Die(int number, Color color){
        this.number=number;
        this.color=color;
    }

    /**
     * Simply getter method
     * @return the die's color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Simply getter method
     * @return the die's number
     */
    public int getNumber(){
        return number;
    }

    /**
     * Simply setter method
     * @param number it is the number that has to be set
     */
    public void setNumber(int number) { this.number=number; }

    @Override
    public boolean equals(Object die){
        if (die == this) return true;
        if (!(die instanceof Die)) return false;
        Die myDie = (Die) die;
        return (myDie.getNumber()==this.getNumber() && myDie.getColor()==this.getColor());
    }

    @Override
    public String toString() {
        return "die("+number+","+color + ")";
    }
}
