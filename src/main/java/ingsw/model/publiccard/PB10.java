package ingsw.model.publiccard;

import ingsw.model.Cell;
import ingsw.model.Player;
import ingsw.model.Color;

/**
 * Author : Diego Zucca
 *
 * Public card number 10
 */
public class PB10 implements PBStrategy {
    private String title;
    private String comment;
    private final int points;

    /**
     * Constructor
     */
    public PB10(){
        this.title = "Varietà di colore";
        this.comment = "Set di dadi di ogni colore ovunque";
        this.points = 4;
    }

    /**
     *This method assigns four points for every set of dice of every color found in window
     * @param p it is the player who is calculating his own score
     */
    public void doOp(Player p) {
        Cell [][] cellMatrix = p.getWindowPattern().getCellMatrix();
        int values[] = new int[]{0,0,0,0,0};
        for(int i=0;i<4;i++){
            for(int j=0;j<5;j++){
                if(!cellMatrix[i][j].isEmpty() && String.valueOf(cellMatrix[i][j].getDie().getColor()).equalsIgnoreCase(String.valueOf(Color.BLUE)))
                    values[0]++;
                if(!cellMatrix[i][j].isEmpty() && String.valueOf(cellMatrix[i][j].getDie().getColor()).equalsIgnoreCase(String.valueOf(Color.VIOLET)))
                    values[1]++;
                if(!cellMatrix[i][j].isEmpty() && String.valueOf(cellMatrix[i][j].getDie().getColor()).equalsIgnoreCase(String.valueOf(Color.GREEN)))
                    values[2]++;
                if(!cellMatrix[i][j].isEmpty() && String.valueOf(cellMatrix[i][j].getDie().getColor()).equalsIgnoreCase(String.valueOf(Color.RED)))
                    values[3]++;
                if(!cellMatrix[i][j].isEmpty() && String.valueOf(cellMatrix[i][j].getDie().getColor()).equalsIgnoreCase(String.valueOf(Color.YELLOW)))
                    values[4]++;
            }
        }
        int min = values[0];
        for(int i=1; i<5; i++){
            if (min>values[i])
                min = values[i];
        }
        p.addScore(points*min);

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
