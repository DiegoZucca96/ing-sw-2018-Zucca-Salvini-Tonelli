package PublicCard;

import ingsw.Cell;
import ingsw.Player;
import WindowPattern.WP1;
import java.util.ArrayList;

public class PB4 implements PBStrategy {
    private String title;
    private String comment;
    private final int points;

    public PB4(){
        this.title = "Sfumature diverse - Colonna";
        this.comment = "Colonne senza sfumature ripetute";
        this.points = 4;
    }

    @Override
    public void doOp(Player p, WP1 window) {
        Cell [][] cellMatrix = window.getCellMatrix();
        for(int i=0;i<5;i++){
            int j;
            ArrayList<Integer> list = new ArrayList<>();
            for(j=0;j<4;j++){
                Integer num = cellMatrix[j][i].getDie().getNumber();
                if(list.contains(num) || num==0)
                    j=7;           //Metto un valore alto in modo da differenziare il caso in cui esco perchè trovata la colonna di colori diversi
                else
                    list.add(num);
            }
            if(j==4){
                i=5;
                p.addScore(points);
            }
        }
    }
}
