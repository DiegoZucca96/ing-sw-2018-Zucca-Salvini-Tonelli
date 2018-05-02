package ingsw;

import ingsw.model.RandomGenerator;
import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;

public class RandomGeneratorTest{

    @Test
    public void testRandomDifferentNumbers(){
        ArrayList<Integer> allNumbers = new ArrayList<Integer>();
        RandomGenerator rg = new RandomGenerator(23);
        for(int i=0; i<23; i++){
            allNumbers.add(rg.random());
        }
        for(int i=0; i<23; i++){
            for(int j=i+1; j<23; j++){
                assertFalse(allNumbers.get(i).equals(allNumbers.get(j)));
            }
        }
    }
}
