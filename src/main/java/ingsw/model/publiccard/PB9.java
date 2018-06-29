package ingsw.model.publiccard;

import ingsw.model.Cell;
import ingsw.model.Coordinate;
import ingsw.model.Player;
import java.util.ArrayList;

/**
 * Public card number 9
 */
public class PB9 implements PBStrategy {
    private String title;
    private String comment;
    private final int points;

    /**
     * Constructor
     */
    public PB9(){
        this.title = "Diagonali Colorate";
        this.comment = "Numero di dadi dello stesso\n" + "colore diagonalmente adiacenti";
        this.points = 0;
    }

    /**
     *This method assigns points based on the number of dice with the same color that are diagonally adjacent
     * @param p it is the player who is calculating his own score
     */
    public void doOp(Player p) {
        Cell[][] cellMatrix = p.getWindowPattern().getCellMatrix();
        ArrayList<Coordinate> countedDie = new ArrayList<Coordinate>();
        for(int i=0; i<4;i++){
            for(int j=0;j<5;j++){
                Coordinate c1 = new Coordinate(i,j);
                if(i+1<4 && j-1>0 && cellMatrix[i][j].getDie()!=null && cellMatrix[i+1][j-1].getDie()!=null && String.valueOf(cellMatrix[i][j].getDie().getColor()).equalsIgnoreCase(String.valueOf(cellMatrix[i+1][j-1].getDie().getColor()))){
                    if(!countedDie.contains(c1))
                        countedDie.add(c1);
                    Coordinate c2 = new Coordinate(i+1,j-1);
                    if(!countedDie.contains(c2))
                        countedDie.add(c2);
                }
                if(i+1<4 && j+1<5 && cellMatrix[i][j].getDie()!=null && cellMatrix[i+1][j+1].getDie()!=null && String.valueOf(cellMatrix[i][j].getDie().getColor()).equalsIgnoreCase(String.valueOf(cellMatrix[i+1][j+1].getDie().getColor()))){
                    if(!countedDie.contains(c1))
                        countedDie.add(c1);
                    Coordinate c2 = new Coordinate(i+1,j+1);
                    if(!countedDie.contains(c2))
                        countedDie.add(c2);
                }
                if(i-1>0 && j-1>0 && cellMatrix[i][j].getDie()!=null && cellMatrix[i-1][j-1].getDie()!=null && String.valueOf(cellMatrix[i][j].getDie().getColor()).equalsIgnoreCase(String.valueOf(cellMatrix[i-1][j-1].getDie().getColor()))){
                    if(!countedDie.contains(c1))
                        countedDie.add(c1);
                    Coordinate c2 = new Coordinate(i-1,j-1);
                    if(!countedDie.contains(c2))
                        countedDie.add(c2);
                }
                if(i-1>0 && j+1<5 && cellMatrix[i][j].getDie()!=null && cellMatrix[i-1][j+1].getDie()!=null && String.valueOf(cellMatrix[i][j].getDie().getColor()).equalsIgnoreCase(String.valueOf(cellMatrix[i-1][j+1].getDie().getColor()))){
                    if(!countedDie.contains(c1))
                        countedDie.add(c1);
                    Coordinate c2 = new Coordinate(i-1,j+1);
                    if(!countedDie.contains(c2))
                        countedDie.add(c2);
                }
            }
        }
        p.addScore(countedDie.size());
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
