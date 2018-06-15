package ingsw.model;

import java.util.ArrayList;
import java.util.Random;

public class RandomGenerator {

    //Classe che genera numeri casuali tra 1 e n senza mai restituire due volte lo stesso numero

    private ArrayList<Integer> values;

    public RandomGenerator(int n){
        values = new ArrayList<Integer>();
        for(int i=1; i<=n; i++){
            values.add(i);
        }
    }

    public boolean add(Integer newValue){
        return values.add(newValue);
    }

    public boolean remove(Object value){
        return value == values.remove(values.indexOf(value));
    }

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
