package ingsw.model;
import static ingsw.model.Color.*;

/**
 * Class that contains data linked to the probability of dice draw
 * Author: Elio Salvini
 * @see RandomGenerator
 */
public class DiceBag{

    private int totDice;            //number of dice in the bag
    private RandomGenerator rg;

    /**
     * Constructor
     */
    public DiceBag(){
     totDice = 90;
     rg = new RandomGenerator(totDice);
    }

    /**
     * This method draws a random die among the available without replacement
     * @return drawn die
     */
    public Die randomDice() {
        int randomNumber = rg.random();
        if(randomNumber<=18) return new Die(new RandomGenerator(6).random(), BLUE);
        if(randomNumber<=36) return new Die(new RandomGenerator(6).random(), VIOLET);
        if(randomNumber<=54) return new Die(new RandomGenerator(6).random(), GREEN);
        if(randomNumber<=72) return new Die(new RandomGenerator(6).random(), RED);
        if(randomNumber<=90) return new Die(new RandomGenerator(6).random(), YELLOW);
        return null;
    }

    /**
     * This method allows to reinsert a die in the bag
     * @param die  it is the die you want to reinsert
     */
    public void insertDie (Die die){
        switch (die.getColor()){
            case BLUE: {
                rg.add(10);
                break;
            }
            case VIOLET: {
                rg.add(30);
                break;
            }
            case GREEN: {
                rg.add(50);
                break;
            }
            case RED: {
                rg.add(65);
                break;
            }
            case YELLOW: {
                rg.add(80);
                break;
            }
            default: {
                break;
            }
        }
    }

}