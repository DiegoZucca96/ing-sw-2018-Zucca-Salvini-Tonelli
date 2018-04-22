package PublicCard;

import ingsw.Cell;
import ingsw.Color;
import ingsw.Player;
import WindowPattern.WP1;
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

    @Override
    public void doOp(Player p, WP1 window) {
        Cell [][] cellMatrix = window.getCellMatrix();
        for(int i=0;i<5;i++){
            int j;
            ArrayList<Color> list = new ArrayList<>();
            for(j=0;j<4;j++){
                Color color = cellMatrix[j][i].getDie().getColor(); //fare riferimento con la WP corrispondente
                if(list.contains(color) || color==null)
                    j=7;           //Metto un valore alto in modo da differenziare il caso in cui esco perchè trovata la colonna di colori diversi
                else
                    list.add(color);
            }
            if(j==4){
                i=5;
                p.setScore(points);
            }
        }

    }
}
