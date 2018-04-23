package PublicCard;

import WindowPattern.WP1;
import ingsw.Cell;
import ingsw.Player;


public class PB8 implements PBStrategy {
    private String title;
    private String comment;
    private final int points;

    public PB8(){
        this.title = "Sfumature diverse";
        this.comment = "Set di dadi di ogni valore ovunque";
        this.points = 5;
    }

    @Override
    public void doOp(Player p, WP1 window) {
        Cell[][] cellMatrix = window.getCellMatrix();
        int values[] = new int[]{0,0,0,0,0,0};
        for(int i=0;i<4;i++){
            for(int j=0;j<5;j++){
                if(!cellMatrix[i][j].isEmpty() && cellMatrix[i][j].getDie().getNumber() == 1)
                    values[0]++;
                if(!cellMatrix[i][j].isEmpty() && cellMatrix[i][j].getDie().getNumber() == 2)
                    values[1]++;
                if(!cellMatrix[i][j].isEmpty() && cellMatrix[i][j].getDie().getNumber() == 3)
                    values[2]++;
                if(!cellMatrix[i][j].isEmpty() && cellMatrix[i][j].getDie().getNumber() == 4)
                    values[3]++;
                if(!cellMatrix[i][j].isEmpty() && cellMatrix[i][j].getDie().getNumber() == 5)
                    values[4]++;
                if(!cellMatrix[i][j].isEmpty() && cellMatrix[i][j].getDie().getNumber() == 6)
                    values[5]++;
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
