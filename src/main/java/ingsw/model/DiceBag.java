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
        if(randomNumber<=36) return new Die(new RandomGenerator(6).random(), VIOLET);
        if(randomNumber<=54) return new Die(new RandomGenerator(6).random(), GREEN);
        if(randomNumber<=72) return new Die(new RandomGenerator(6).random(), RED);
        if(randomNumber<=90) return new Die(new RandomGenerator(6).random(), YELLOW);
        return null;
    }

    //Metodo che sistema la randomicità con cui escono i dadi, reinserisco il dado nella borsa
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