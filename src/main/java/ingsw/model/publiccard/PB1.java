package ingsw.model.publiccard;

import ingsw.model.Cell;
import ingsw.model.Color;
import ingsw.model.Player;
import java.util.ArrayList;

/**
 * Public card number 1
 */
public class PB1 implements PBStrategy {
    private String title;
    private String comment;
    private final int points;

    /**
     * Constructor
     */
    public PB1(){
        this.title = "Colori diversi - Riga";
        this.comment = "Righe senza colori ripetuti";
        this.points = 6;
    }

    /**
     *This method assigns six points for every row without repeated colors found in window.
     * @param p it is the player who is calculating his own score
     */
    public void doOp(Player p) {
        Cell [][] cellMatrix = p.getWindowPattern().getCellMatrix();
        for(int i=0;i<4;i++){
            int j;
            ArrayList<Color> list = new ArrayList<>();
            for(j=0;j<5;j++){
                if(cellMatrix[i][j].getDie()==null)
                    j=6;
                else {
                    Color color = cellMatrix[i][j].getDie().getColor();
                    if (color == null || list.contains(color))
                        j = 8;   //I put a high value in order to differentiate the case in which I leave the "for" because I found the line of different colors
                    else
                        list.add(color);
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
