package ingsw;

import junit.framework.TestCase;
import org.junit.jupiter.api.*;

class DiceBagTest extends TestCase{
    @Test
   public void testRandomDice() {
        int b = 0, g = 0, y = 0, r = 0, v = 0;
        DiceBag db = new DiceBag();
        Die die;
        for (int i = 0; i < 90; i++) {
            die = db.randomDice();
            switch (die.getColor()) {
                case BLUE: {
                    b++;
                    break;
                }
                case GREEN: {
                    g++;
                    break;
                }
                case YELLOW: {
                    y++;
                    break;
                }
                case RED: {
                    r++;
                    break;
                }
                case VIOLET: {
                    v++;
                    break;
                }
                default:
                    break;
            }
        }
        assertEquals(18,b);
        assertEquals(18,y);
        assertEquals(18,g);
        assertEquals(18,r);
        assertEquals(18,v);
    }
}