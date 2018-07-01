package ingsw.model.publiccard;

import ingsw.model.Cell;
import ingsw.model.Player;
import java.util.ArrayList;

/**
 * Author : Diego Zucca
 *
 * Public card number 3
 */
public class PB3 implements PBStrategy {
    private String title;
    private String comment;
    private final int points;

    /**
     * Constructor
     */
    public PB3(){
        this.title = "Sfumature diverse - Riga";
        this.comment = "Righe senza sfumature ripetute";
        this.points = 5;
    }

    /**
     *This method assigns five points for every row without repeated numbers found in window.
     * @param p it is the player who is calculating his own score
     */
    public void doOp(Player p) {
        Cell [][] cellMatrix = p.getWindowPattern().getCellMatrix();
        for(int i=0;i<4;i++){
            int j;
            ArrayList<Integer> list = new ArrayList<>();
            for(j=0;j<5;j++){
                if(cellMatrix[i][j].getDie()==null)
                    j=6;
                else {
                    Integer num = cellMatrix[i][j].getDie().getNumber();
                    if(num == 0 || list.contains(num))
                        j=8;           //I put a high value in order to differentiate the case in which I leave the "for" because I found the line of different numbers
                    else
                        list.add(num);
                }
            }
            if(j==5)
                p.addScore(points);
        }
    }

    /**Author: Elio Salvini
     *
     * CLI support
     */
    @Override
    public String toString() {
        return title + '\n' + comment + "\nPoints: " + points + '\n';
    }
}
