package ingsw.model.publiccard;

import ingsw.model.Cell;
import ingsw.model.Player;
import static java.lang.Math.min;

public class PB7 implements PBStrategy {
    private String title;
    private String comment;
    private final int points;

    public PB7(){
        this.title = "Sfumature scure";
        this.comment = "Set di 5 & 6 ovunque";
        this.points = 2;
    }

    public void doOp(Player p) {
        Cell [][] cellMatrix = p.getWindowPattern().getCellMatrix();
        int num5=0,num6=0;
        for(int i=0;i<4;i++){
            for(int j=0;j<5;j++){
                if(!cellMatrix[i][j].isEmpty() && cellMatrix[i][j].getDie().getNumber() == 5)
                    num5++;
                if(!cellMatrix[i][j].isEmpty() && cellMatrix[i][j].getDie().getNumber() == 6)
                    num6++;
            }
        }
        p.addScore(points*min(num5,num6));

    }
}
