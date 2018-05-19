package ingsw.model;

/**Author : Alessio Tonelli
 *
 *
 * */

public class Die{

    private final Color color;
    private int number;

    public Die(int number, Color color){
        this.number=number;
        this.color=color;
    }

    public Color getColor() {
        return color;
    }

    public int getNumber(){
        return number;
    }

    public void setNumber(int number) { this.number=number; }

    @Override
    public boolean equals(Object die){

        if (die == this) return true;

        if (!(die instanceof Die)) return false;

        Die myDie = (Die) die;

        return (myDie.getNumber()==this.getNumber() && myDie.getColor()==this.getColor());

    }

}
