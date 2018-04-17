package ingsw;

import java.util.ArrayList;
import java.util.Random;

public class RandomGenerator extends ArrayList<Integer>{

    private ArrayList<Integer> values;

    public RandomGenerator(ArrayList<Integer> possibleValues){

        this.values = possibleValues;
    }

    @Override
    public boolean add(Integer newValue){
        return values.add(newValue);
    }

    @Override
    public boolean remove(Object value){
        return value == values.remove(values.indexOf(value));
    }

    public int random(int n){
        if (n!=values.size()) return 0;
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(n);
        int result=values.get(index-1);
        remove(index-1);
        return result;

    }
}
