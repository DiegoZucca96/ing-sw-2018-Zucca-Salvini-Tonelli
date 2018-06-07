package ingsw.model.publiccard;

import ingsw.model.Cell;
import ingsw.model.Color;
import ingsw.model.Player;

import java.util.ArrayList;

public class PB2 implements PBStrategy {
    private String title;
    private String comment;
    private final int points;

    public PB2(){
        this.title = "Colori diversi - Colonna";
        this.comment = "Colonne senza colori ripetuti";
        this.points = 5;
    }

    public void doOp(Player p) {
        Cell [][] cellMatrix = p.getWindowPattern().getCellMatrix();
        for(int i=0;i<5;i++){
            int j;
            ArrayList<Color> list = new ArrayList<Color>();
            for(j=0;j<4;j++){
                if(cellMatrix[j][i].getDie()==null)
                    j=6;
                else {
                    Color color = cellMatrix[j][i].getDie().getColor();
                    if (color==null || list.contains(color))
                        j=8;           //Metto un valore alto in modo da differenziare il caso in cui esco perchè trovata la colonna di colori diversi
                    else
                        list.add(color);
                }
            }
            if(j==4)
                p.addScore(points);
        }
    }
}
