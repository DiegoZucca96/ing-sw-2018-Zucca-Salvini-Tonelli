package ingsw.model.publiccard;

import ingsw.model.Cell;
import ingsw.model.Player;
import ingsw.model.Color;

public class PB10 implements PBStrategy {
    private String title;
    private String comment;
    private final int points;

    public PB10(){
        this.title = "Varietà di colore";
        this.comment = "Set di dadi di ogni colore ovunque";
        this.points = 4;
    }

    public void doOp(Player p) {
        Cell [][] cellMatrix = p.getWindowPattern().getCellMatrix();
        int values[] = new int[]{0,0,0,0,0};
        for(int i=0;i<4;i++){
            for(int j=0;j<5;j++){
                if(!cellMatrix[i][j].isEmpty() && cellMatrix[i][j].getDie().getColor() == Color.BLUE)
                    values[0]++;
                if(!cellMatrix[i][j].isEmpty() && cellMatrix[i][j].getDie().getColor() == Color.VIOLET)
                    values[1]++;
                if(!cellMatrix[i][j].isEmpty() && cellMatrix[i][j].getDie().getColor() == Color.GREEN)
                    values[2]++;
                if(!cellMatrix[i][j].isEmpty() && cellMatrix[i][j].getDie().getColor() == Color.RED)
                    values[3]++;
                if(!cellMatrix[i][j].isEmpty() && cellMatrix[i][j].getDie().getColor() == Color.YELLOW)
                    values[4]++;
            }
        }
        int min = values[0];
        for(int i=1; i<6; i++){
            if (min>values[i])
                min = values[i];
        }
        p.addScore(points*min);

    }
}
