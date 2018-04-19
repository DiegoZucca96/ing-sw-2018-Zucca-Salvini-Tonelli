package ingsw;

import java.util.ArrayList;
import java.util.Random;

public class RandomGenerator extends ArrayList<Integer>{

    private ArrayList<Integer> values;

    public RandomGenerator(int n){
        values = new ArrayList<>();
        for(int i=1; i<=n; i++){
            values.add(i);
        }
    }

    @Override
    public boolean add(Integer newValue){
        return values.add(newValue);
    }

    @Override
    public boolean remove(Object value){
        return value == values.remove(values.indexOf(value));
    }

    public int random(){
        Random r = new Random();
        int index = r.nextInt(values.size());
        int result=values.get(index-1);
        remove(result);
        return result;

    }
}
