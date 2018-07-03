package ingsw.model.publiccard;

import ingsw.model.Cell;
import ingsw.model.Player;
import static java.lang.Math.min;

/**
 * Author : Diego Zucca
 *
 * Public card number 6
 */
public class PB6 implements PBStrategy {
    private String title;
    private String comment;
    private final int points;

    /**
     * Constructor
     */
    public PB6(){
        this.title = "Sfumature medie";
        this.comment = "Set di 3 & 4 ovunque";
        this.points = 2;
    }

    /**
     *This method assigns two points for every couple "3-4" found in window
     * @param p it is the player who is calculating his own score
     */
    public void doOp(Player p) {
        Cell [][] cellMatrix = p.getWindowPattern().getCellMatrix();
        int num3=0,num4=0;
        for(int i=0;i<4;i++){
            for(int j=0;j<5;j++){
                if(!cellMatrix[i][j].isEmpty() && cellMatrix[i][j].getDie().getNumber() == 3)
                    num3++;
                if(!cellMatrix[i][j].isEmpty() && cellMatrix[i][j].getDie().getNumber() == 4)
                    num4++;
            }
        }
        p.addScore(points*min(num3,num4));

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
