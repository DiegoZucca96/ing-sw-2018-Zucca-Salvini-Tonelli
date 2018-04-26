package ingsw.model.publiccard;

import ingsw.model.Cell;
import ingsw.model.Color;
import ingsw.model.Player;
import ingsw.model.windowpattern.WP1;
import ingsw.model.windowpattern.WindowPattern;

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

    public void doOp(Player p, WindowPattern window) {
        Cell [][] cellMatrix = window.getCellMatrix();
        for(int i=0;i<4;i++){
            int j;
            ArrayList<Color> list = new ArrayList<Color>();
            for(j=0;j<5;j++){
                Color color = cellMatrix[i][j].getDie().getColor();
                if(list.contains(color) || color==null)
                    j=8;           //Metto un valore alto in modo da differenziare il caso in cui esco perchè trovata la riga di colori diversi
                else
                    list.add(color);
            }
            if(j==5){
                i=4;
                p.addScore(points);
            }
        }
    }
}
