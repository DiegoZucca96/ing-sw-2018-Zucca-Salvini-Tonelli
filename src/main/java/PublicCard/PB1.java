package PublicCard;

import ingsw.Color;
import ingsw.Player;

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

    @Override
    public void doOp() {
        for(int i=0;i<4;i++){
            int j;
            ArrayList<Color> list = new ArrayList<>();
            for(j=0;j<5;j++){
                Color color = cellMatrix[i][j].getDie().getColor(); //fare riferimento con la WP corrispondente
                if(list.contains(color) || color==null)
                    j=8;           //Metto un valore alto in modo da differenziare il caso in cui esco perchè trovata la riga di colori diversi
                else
                    list.add(color);
            }
            if(j==5){
                i=4;
                Player.setScore(points);
            }
        }
    }
}
