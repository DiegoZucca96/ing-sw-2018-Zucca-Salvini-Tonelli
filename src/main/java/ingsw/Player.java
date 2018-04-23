package ingsw;

import WindowPattern.WindowPattern;

public class Player {       //Classe che rappresenta un giocatore della partita

    private String name;
    private int nFavoriteTokens;
    private int score;
    private WindowPattern windowPattern;
    private PVObjectiveCard pvObjectiveCard;

    public Player(String name, String wpType, Color pvColor){
        this.name = name;
        score = 0;
        windowPattern = new WindowPFactory().createWindowPattern(wpType);
        nFavoriteTokens = windowPattern.getDifficulty();
        pvObjectiveCard = new PVObjectiveCard(color);
    }

    public int getScore(){
        return score;
    }

    public String getName(){
        return name;
    }

    public int getTokens(){
        return nFavoriteTokens;
    }

    public void setScore(int newScore){
        score = newScore;
    }

    //somma additionalScore al punteggio attuale
    public void addScore(int additionalScore){
        score += additionalScore;
    }

    //sottrae additionl Score al punteggio attuale
    public void subScore(int surplusScore){
        score -= surplusScore;
    }

    //consuma un certo numero di favorite tokens in base alla tool card utilizzata
    public void useToken(ToolCard tool){
        if(tool.alreadyUsed()) nFavoriteTokens -= 2;
        else nFavoriteTokens--;
    }

    //posiziona un dado sulla window pattern del giocatore
    public boolean positionDie(Die die, Coordinate coordinates){
        return windowPattern.addDie(coordinates, die);
    }

    //attribuisce al giocatore il suo punteggio indipendente dalle public objective card in tavola
    public void pvScore(){
        addScore(nFavoriteTokens);
        addScore(windowPattern.countDie(pvObjectiveCard.getColor()));
        subScore(windowPattern.countEmptyCells());
    }
}
