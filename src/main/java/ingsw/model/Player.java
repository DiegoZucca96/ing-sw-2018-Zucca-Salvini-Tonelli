package ingsw.model;

import ingsw.model.windowpattern.WindowPattern;
import ingsw.model.toolcard.*;
import ingsw.model.ToolCard;


public class Player {       //Classe che rappresenta un giocatore della partita

    private String name;
    private int nFavoriteTokens;
    private int score;
    private WindowPattern windowPattern;
    private PVObjectiveCard pvObjectiveCard;
    private int myRound;

    public Player(String name, String wpType, Color pvColor){
        this.name = name;
        score = 0;
        windowPattern = new WindowPFactory().createWindowPattern(wpType);
        nFavoriteTokens = windowPattern.getDifficulty();
        pvObjectiveCard = new PVObjectiveCard(pvColor);
        this.myRound = 1;
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

    public WindowPattern getWindowPattern() {
        return windowPattern;
    }

    public void setScore(int newScore){
        score = newScore;
    }

    public int getMyRound() {
        return myRound;
    }

    public void setMyRound(int myRound) {
        this.myRound = myRound;
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

    //A partire dall'ID della carta vado a creare l'object con solo i parametri interessati (Problema di come ottenere dati in input)
    public void useToolCard(ToolCard toolCard){
        int idCard = toolCard.getIdCard();
        ObjectiveTool object;
        boolean allow = true;  //Serve a dare il consenso all'uso della ToolCard o meno
        switch(idCard){
            case 1:{
                if(upDie()) //upDie funzione che se true porta ad aumentare il valore, se false abbassare
                    object = new ObjectiveTool(die1,null,null,1,null,null,null,null, null, draftpool,0,null);
                else
                    object = new ObjectiveTool(die1,null,null,-1,null,null,null,null, null, draftpool,0,null);
                break;
            }case 2:{
                object = new ObjectiveTool(null,null,window,0,coordinateDie,null,destination,null, null, null,0,null);
                break;
            }case 3:{
                object = new ObjectiveTool(null,null,window,0,coordinateDie,null,destination,null, null, null,0,null);
                break;
            }case 4:{
                object = new ObjectiveTool(null,null,window,0,coordinateDie1,coordinateDie2 ,destination1,destination2, null, null,0,null);
                break;
            }case 5:{
                object = new ObjectiveTool(die1,die2,null,0,null,null,null,null, roundtrack, draftpool,0,null);
                break;
            }case 6:{
                object = new ObjectiveTool(die1,null,window,0,null,null,null,null, null, draftpool,0,null);
                break;
            }case 7:{
                if(getMyRound()==2)
                    object = new ObjectiveTool(null,null,null,0,null,null,null,null, null, draftpool,0,dicebag);
                else
                    allow = false;
                break;
            }case 8:{

                object = new ObjectiveTool(null,null,window,0,null,null,destination,null, null, draftpool,0,null);
                break;
            }case 9:{
                object = new ObjectiveTool(die1,null,window,0,null,null,destination,null, null, null,0,null);
                break;
            }case 10:{
                object = new ObjectiveTool(die1,null,null,0,null,null,null,null, null, draftpool,1,null);
                break;
            }case 11:{
                object = new ObjectiveTool(die1,null,window,0,null,null,null,null, null, null,0,dicebag);
                break;
            }case 12:{
                object = new ObjectiveTool(null,null,window,0,coordinateDie1,coordinateDie2,destination1,destination2, roundtrack, null,0,null);
                break;
            }default: break;
        }
        if(allow) {
            useToken(toolCard);         //Uso i token prima della carta, nel caso non potessi usarla allora l'eccezione che ne uscirà gestirà la
            toolCard.doToolStrategy(object);//restituzione dei token al giocatore
        }else
            System.out.print("Non puoi usare questa carta");
    }

    //attribuisce al giocatore il suo punteggio indipendente dalle public objective card in tavola
    public void pvScore(){
        addScore(nFavoriteTokens);
        addScore(windowPattern.countDie(pvObjectiveCard.getColor(), windowPattern.getCellMatrix()));
        subScore(windowPattern.countEmptyCells(windowPattern.getCellMatrix()));
    }
}
