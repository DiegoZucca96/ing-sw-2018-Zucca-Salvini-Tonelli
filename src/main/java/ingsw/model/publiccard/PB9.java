package ingsw.model.publiccard;

import ingsw.model.Cell;
import ingsw.model.Die;
import ingsw.model.Player;
import java.util.ArrayList;

public class PB9 implements PBStrategy {
    private String title;
    private String comment;
    private final int points;

    public PB9(){
        this.title = "Diagonali Colorate";
        this.comment = "Numero di dadi dello stesso\n" + "colore diagonalmente adiacenti";
        this.points = 0;
    }

    public void doOp(Player p) {
        Cell[][] cellMatrix = p.getWindowPattern().getCellMatrix();
        ArrayList<Die> countedDie = new ArrayList<Die>();
        for(int i=0; i<4;i++){
            for(int j=0;j<5;j++){
                if(i+1<4 && j-1>0 && cellMatrix[i][j].getDie()!=null && cellMatrix[i+1][j-1].getDie()!=null && cellMatrix[i][j].getDie().getColor() == cellMatrix[i+1][j-1].getDie().getColor()){
                    if(!countedDie.contains(cellMatrix[i][j].getDie()))
                        countedDie.add(cellMatrix[i][j].getDie());
                    if(!countedDie.contains(cellMatrix[i+1][j-1].getDie()))
                        countedDie.add(cellMatrix[i+1][j-1].getDie());
                }
                if(i+1<4 && j+1<5 && cellMatrix[i][j].getDie()!=null && cellMatrix[i+1][j+1].getDie()!=null && cellMatrix[i][j].getDie().getColor() == cellMatrix[i+1][j+1].getDie().getColor()){
                    if(!countedDie.contains(cellMatrix[i][j].getDie()))
                        countedDie.add(cellMatrix[i][j].getDie());
                    if(!countedDie.contains(cellMatrix[i+1][j+1].getDie()))
                        countedDie.add(cellMatrix[i+1][j+1].getDie());
                }
                if(i-1>0 && j-1>0 && cellMatrix[i][j].getDie()!=null && cellMatrix[i-1][j-1].getDie()!=null && cellMatrix[i][j].getDie().getColor() == cellMatrix[i-1][j-1].getDie().getColor()){
                    if(!countedDie.contains(cellMatrix[i][j].getDie()))
                        countedDie.add(cellMatrix[i][j].getDie());
                    if(!countedDie.contains(cellMatrix[i-1][j-1].getDie()))
                        countedDie.add(cellMatrix[i-1][j-1].getDie());
                }
                if(i-1>0 && j+1<5 && cellMatrix[i][j].getDie()!=null && cellMatrix[i-1][j+1].getDie()!=null && cellMatrix[i][j].getDie().getColor() == cellMatrix[i-1][j+1].getDie().getColor()){
                    if(!countedDie.contains(cellMatrix[i][j].getDie()))
                        countedDie.add(cellMatrix[i][j].getDie());
                    if(!countedDie.contains(cellMatrix[i-1][j+1].getDie()))
                        countedDie.add(cellMatrix[i-1][j+1].getDie());
                }
            }
        }
        p.addScore(countedDie.size());
    }
}
