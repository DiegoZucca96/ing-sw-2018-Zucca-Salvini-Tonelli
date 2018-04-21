package ingsw;

import java.util.ArrayList;

public class PB4 implements PBStrategy{
    private String title;
    private String comment;
    private final int points;

    public PB4(){
        this.title = "Sfumature diverse - Colonna";
        this.comment = "Colonne senza sfumature ripetute";
        this.points = 4;
    }

    @Override
    public void doOp() {
        for(int i=0;i<5;i++){
            int j;
            ArrayList<Integer> list = new ArrayList<>();
            for(j=0;j<4;j++){
                Integer num = cellMatrix[j][i].getDie().getNumber(); //fare riferimento con la WP corrispondente
                if(list.contains(num) || num==0)
                    j=7;           //Metto un valore alto in modo da differenziare il caso in cui esco perchè trovata la colonna di colori diversi
                else
                    list.add(num);
            }
            if(j==4){
                i=5;
                Player.setScore(points);
            }
        }
    }
}
