package ingsw;

import ingsw.model.RandomGenerator;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class RandomGeneratorTest extends TestCase {

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
