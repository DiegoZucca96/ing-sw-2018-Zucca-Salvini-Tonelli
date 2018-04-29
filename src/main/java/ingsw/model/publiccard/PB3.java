package ingsw.model.publiccard;

import ingsw.model.Cell;
import ingsw.model.Player;

import java.util.ArrayList;

public class PB3 implements PBStrategy {
    private String title;
    private String comment;
    private final int points;

    public PB3(){
        this.title = "Sfumature diverse - Riga";
        this.comment = "Righe senza sfumature ripetute";
        this.points = 5;
    }

    public void doOp(Player p) {
        Cell [][] cellMatrix = p.getWindowPattern().getCellMatrix();
        for(int i=0;i<4;i++){
            int j;
            ArrayList<Integer> list = new ArrayList<Integer>();
            for(j=0;j<5;j++){
                Integer num = cellMatrix[i][j].getDie().getNumber();
                if(list.contains(num) || num==0)
                    j=8;           //Metto un valore alto in modo da differenziare il caso in cui esco perchè trovata la colonna di colori diversi
                else
                    list.add(num);
            }
            if(j==5){
                i=4;
                p.addScore(points);
            }
        }
    }
}
