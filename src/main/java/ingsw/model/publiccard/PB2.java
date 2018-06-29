package ingsw.model.publiccard;

import ingsw.model.Cell;
import ingsw.model.Color;
import ingsw.model.Player;
import java.util.ArrayList;

/**
 * Public card number 2
 */
public class PB2 implements PBStrategy {
    private String title;
    private String comment;
    private final int points;

    /**
     * Constructor
     */
    public PB2(){
        this.title = "Colori diversi - Colonna";
        this.comment = "Colonne senza colori ripetuti";
        this.points = 5;
    }

    /**
     *This method assigns five points for every column without repeated colors found in window.
     * @param p it is the player who is calculating his own score
     */
    public void doOp(Player p) {
        Cell [][] cellMatrix = p.getWindowPattern().getCellMatrix();
        for(int i=0;i<5;i++){
            int j;
            ArrayList<Color> list = new ArrayList<>();
            for(j=0;j<4;j++){
                if(cellMatrix[j][i].getDie()==null)
                    j=6;
                else {
                    Color color = cellMatrix[j][i].getDie().getColor();
                    if (color==null || list.contains(color))
                        j=8;        //I put a high value in order to differentiate the case in which I leave the "for" because I found the column of different colors
                    else
                        list.add(color);
                }
            }
            if(j==4)
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
