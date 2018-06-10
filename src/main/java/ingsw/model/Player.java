package ingsw.model;

import ingsw.model.windowpattern.WindowPattern;

import java.io.IOException;


public class Player {       //Classe che rappresenta un giocatore della partita

    private String name;
    private int nFavoriteTokens;
    private int score;
    private int pvScore;
    private WindowPattern windowPattern;
    private PVObjectiveCard pvObjectiveCard;
    private Die dieSelected;
    private Coordinate coordinateDieSelected;
    private boolean insertedDie;
    private boolean isTool8Used;

    public Player(String name, int wpType, Color pvColor){
        this.name = name;
        score = 0;
        pvScore = 0;
        try {
            windowPattern = new WindowPFactory().createWindowPattern(wpType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        nFavoriteTokens = windowPattern.getDifficulty();
        pvObjectiveCard = new PVObjectiveCard(pvColor);
        this.insertedDie = false;
        this.isTool8Used = false;
    }
    public Die getDieSelected() {
        return dieSelected;
    }

    public void setDieSelected(Die dieSelected) {
        this.dieSelected = dieSelected;
    }

    public Coordinate getCoordinateDieSelected() {
        return coordinateDieSelected;
    }

    public void setCoordinateDieSelected(Coordinate coordinateDieSelected) {
        this.coordinateDieSelected = coordinateDieSelected;
    }

    public PVObjectiveCard getPvObjectiveCard() {
        return pvObjectiveCard;
    }

    public int getScore(){
        return score;
    }

    public int getPVScore() {
        return pvScore;
    }

    public String getName(){
        return name;
    }

    public int getTokens(){
        return nFavoriteTokens;
    }

    public WindowPattern getWindowPattern() {
        return windowPattern;
    }

    public void setScore(int newScore){
        score = newScore;
    }

    //NB --> metodo ad uso esclusivo dei test
    public void setWindowPattern(WindowPattern windowPattern) {
        this.windowPattern = windowPattern;
    }

    //NB --> metodo ad uso esclusivo dei test
    public void setPvObjectiveCard(PVObjectiveCard pvObjectiveCard) {
        this.pvObjectiveCard = pvObjectiveCard;
    }


    //Servono per distinguere se il giocatore ha già inserito il dado o meno
    public boolean getInsertedDie() {
        return insertedDie;
    }

    public void setInsertedDie(boolean value) {
        this.insertedDie = value;
    }

    public boolean getTool8Used() {
        return isTool8Used;
    }

    public void setTool8Used(boolean tool8Used) {
        this.isTool8Used = tool8Used;
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
        if(tool.isAlreadyUsed()) nFavoriteTokens -= 2;
        else{
            nFavoriteTokens--;
            tool.setAlreadyUsed(true);
        }
    }

    //posiziona un dado sulla window pattern del giocatore
    public boolean positionDie(Die die, Coordinate coordinates){
        return windowPattern.addDie(coordinates, die, windowPattern.getCellMatrix());
    }


    //attribuisce al giocatore il suo punteggio indipendente dalle public objective card in tavola
    public void personalScore(){
        addScore(nFavoriteTokens);
        pvScore = windowPattern.countDie(pvObjectiveCard.getColor(), windowPattern.getCellMatrix());
        addScore(pvScore);
        subScore(windowPattern.countEmptyCells(windowPattern.getCellMatrix()));
    }
}
