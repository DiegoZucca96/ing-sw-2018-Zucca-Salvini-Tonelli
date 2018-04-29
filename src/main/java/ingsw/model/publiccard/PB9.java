package ingsw.model.publiccard;

import ingsw.model.Player;

public class PB9 implements PBStrategy {
    private String title;
    private String comment;
    private final int points;

    public PB9(){
        this.title = "Diagonali Colorate";
        this.comment = "Numero di dadi dello stesso\n" + "colore diagonalmente adiacenti";
        this.points = 0;
    }

    public void doOp(Player p) {
        //Da implementare una visita a diagonale, escludendo ripetizioni
    }
}
