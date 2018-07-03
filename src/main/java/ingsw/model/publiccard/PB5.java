package ingsw.model.publiccard;

import ingsw.model.Cell;
import ingsw.model.Player;
import static java.lang.Math.min;

/**
 * Author : Diego Zucca
 *
 * Public card number 5
 */
public class PB5 implements PBStrategy {
    private String title;
    private String comment;
    private final int points;

    /**
     * Constructor
     */
    public PB5(){
        this.title = "Sfumature chiare";
        this.comment = "Set di 1 & 2 ovunque";
        this.points = 2;
    }

    /**
     *This method assigns two points for every couple "1-2" found in window
     * @param p it is the player who is calculating his own score
     */
    public void doOp(Player p) {
        Cell [][] cellMatrix = p.getWindowPattern().getCellMatrix();
        int num1=0,num2=0;
        for(int i=0;i<4;i++){
            for(int j=0;j<5;j++){
                if(!cellMatrix[i][j].isEmpty() && cellMatrix[i][j].getDie().getNumber() == 1)
                    num1++;
                if(!cellMatrix[i][j].isEmpty() && cellMatrix[i][j].getDie().getNumber() == 2)
                    num2++;
            }
        }
        p.addScore(points*min(num1,num2));

    }

    /**Author: Elio Salvini
     *
     * cli support
     */
    @Override
    public String toString() {
        return title + '\n' + comment + "\nPoints: " + points + '\n';
    }
}
