package ingsw.model;
import static ingsw.model.Color.*;

public class DiceBag{           //classe che tiene traccia dei dati relativi alle probabilità di uscita dei dadi.

    private int totDice;
    private RandomGenerator rg;

    public DiceBag(){
     totDice = 90;
     rg = new RandomGenerator(totDice);
    }

    public Die randomDice() {   //estrae un dado a caso tra quelli disponibili, senza reinseririlo nella dice bag.
        int randomNumber = rg.random();
        if(randomNumber<=18) return new Die(new RandomGenerator(6).random(), BLUE);
        if(randomNumber>18 && randomNumber<=36) return new Die(new RandomGenerator(6).random(), VIOLET);
        if(randomNumber>36 && randomNumber<=54) return new Die(new RandomGenerator(6).random(), GREEN);
        if(randomNumber>54 && randomNumber<=72) return new Die(new RandomGenerator(6).random(), RED);
        if(randomNumber>72 && randomNumber<=90) return new Die(new RandomGenerator(6).random(), YELLOW);
        return null;
    }
}