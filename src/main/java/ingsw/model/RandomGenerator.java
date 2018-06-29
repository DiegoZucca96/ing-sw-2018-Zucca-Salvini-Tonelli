package ingsw.model;

import java.util.ArrayList;
import java.util.Random;


/**
 * This class generates random numbers.
 * Author: Elio Salvini
 */
public class RandomGenerator {

    private ArrayList<Integer> values;  //list of available numbers

    /**
     * Constructor
     * @param n it is the number of random values to generate
     */
    public RandomGenerator(int n){
        values = new ArrayList<>();
        for(int i=1; i<=n; i++){
            values.add(i);
        }
    }

    /**
     * This method allow you to add a new number among the available that can be returned
     * @param newValue the number added
     * @return true if the operation is correctly completed
     */
    public boolean add(Integer newValue){
        return values.add(newValue);
    }

    /**
     * This method allow you to remove a number among the available that can be returned
     * @param value number you want to be removed
     * @return true if the operation is correctly completed
     */
    public boolean remove(Object value){
        return value == values.remove(values.indexOf(value));
    }

    /**
     * It generates a random number
     * @return  random number. It never returns two times the same number
     */
    public int random(){
        if(values.isEmpty())
            return 0;
        Random r = new Random();
        int index = r.nextInt(values.size());
        int result=values.get(index);
        remove(result);
        return result;
    }
}
