package ingsw.model.publiccard;

import ingsw.model.windowpattern.WP1;
import ingsw.model.Cell;
import ingsw.model.Player;
import ingsw.model.windowpattern.WindowPattern;

import static java.lang.Math.min;

public class PB5 implements PBStrategy {
    private String title;
    private String comment;
    private final int points;

    public PB5(){
        this.title = "Sfumature chiare";
        this.comment = "Set di 1 & 2 ovunque";
        this.points = 2;
    }

    public void doOp(Player p, WindowPattern window) {
        Cell[][] cellMatrix = window.getCellMatrix();
        int num1=0,num2=0;
        for(int i=0;i<4;i++){
            for(int j=0;j<5;j++){
                if(!cellMatrix[i][j].isEmpty() && cellMatrix[i][j].getDie().getNumber() == 1)
                    num1++;
                if(!cellMatrix[i][j].isEmpty() && cellMatrix[i][j].getDie().getNumber() == 2)
                    num2++;
            }
        }
        p.addScore(points*min(num1,num2));

    }
}
