package ingsw.model.publiccard;

import ingsw.model.Cell;
import ingsw.model.Color;
import ingsw.model.Player;
import java.util.ArrayList;

public class PB1 implements PBStrategy {
    private String title;
    private String comment;
    private final int points;

    public PB1(){
        this.title = "Colori diversi - Riga";
        this.comment = "Righe senza colori ripetuti";
        this.points = 6;
    }

    public void doOp(Player p) {
        Cell [][] cellMatrix = p.getWindowPattern().getCellMatrix();
        for(int i=0;i<4;i++){
            int j;
            ArrayList<Color> list = new ArrayList<Color>();
            for(j=0;j<5;j++){
                if(cellMatrix[i][j].getDie()==null)
                    j=6;
                else {
                    Color color = cellMatrix[i][j].getDie().getColor();
                    if (color == null || list.contains(color))
                        j = 8;           //Metto un valore alto in modo da differenziare il caso in cui esco perchè trovata la riga di colori diversi
                    else
                        list.add(color);
                }
            }
            if(j==5)
                p.addScore(points);
        }
    }
}
