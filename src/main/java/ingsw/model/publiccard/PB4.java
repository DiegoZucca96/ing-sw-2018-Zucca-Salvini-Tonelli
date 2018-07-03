package ingsw.model.publiccard;

import ingsw.model.Cell;
import ingsw.model.Player;
import java.util.ArrayList;

/**
 * Author : Diego Zucca
 *
 * Public card number 4
 */
public class PB4 implements PBStrategy {
    private String title;
    private String comment;
    private final int points;

    /**
     * Constructor
     */
    public PB4(){
        this.title = "Sfumature diverse - Colonna";
        this.comment = "Colonne senza sfumature ripetute";
        this.points = 4;
    }

    /**
     *This method assigns four points for every column without repeated numbers found in window.
     * @param p it is the player who is calculating his own score
     */
    public void doOp(Player p) {
        Cell [][] cellMatrix = p.getWindowPattern().getCellMatrix();
        for(int i=0;i<5;i++){
            int j;
            ArrayList<Integer> list = new ArrayList<>();
            for(j=0;j<4;j++){
                if(cellMatrix[j][i].getDie()==null)
                    j=6;
                else {
                    Integer num = cellMatrix[j][i].getDie().getNumber();
                    if(list.contains(num) || num==0)
                        j=8;           //I put a high value in order to differentiate the case in which I leave the "for" because I found the column of different numbers
                    else
                        list.add(num);
                }
            }
            if(j==4){
                p.addScore(points);
            }
        }
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
