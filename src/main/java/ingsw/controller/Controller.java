package ingsw.controller;

import ingsw.*;
import ingsw.model.*;
import ingsw.model.windowpattern.WindowPattern;
import ingsw.view.ToolView;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

public class Controller extends UnicastRemoteObject implements RMIController {

    private Timer timer;
    private ControllerTimer controllerTimer;
    private Server server;
    private WindowPFactory wpFactory;
    private RandomGenerator rg;
    private static Match match = null;
    private int timeSearch;
    private int playerMoveTime;
    private static int access=0;
    private static int startTimer = 0;
    private ArrayList<ViewWP> windowChosen;
    private ArrayList<String> nameWPChosen;
    private HashMap<String,Integer> hashPlayers; //Associa ogni player alla sua WP selezionata (usata nella costruzione di match)
    private boolean active;
    private int turn;
    private boolean isFinish;
    //private boolean doubleTurn;
    //private boolean ableNext;
    //private String skipPlayerDueTo8 = null;

    //NB -->    tutti i metodi del controller devono essere boolean, per un motivo o per l'altro
    //          non sempre possono fare l'azione richiesta, in quel caso restituiscono false

    public Controller(Server server) throws RemoteException {
        super();
        this.server= server;
        try {
            this.wpFactory = new WindowPFactory();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.rg = new RandomGenerator(wpFactory.getNumOfWPs());
        this.windowChosen = new ArrayList<>();
        this.nameWPChosen = new ArrayList<>();
        this.hashPlayers = new HashMap<>();
        this.timeSearch = server.getTimeSearch();
        this.playerMoveTime = server.getPlayerTimeMove();
        this.timer = new Timer();
        this.controllerTimer = new ControllerTimer(timeSearch,playerMoveTime);
        this.active=false;
        this.turn=0;
        this.isFinish=false;
    }

    //Lista dei vari metodi invocabili da grafica che vanno a interagire con il model

    @Override
    public ArrayList<String> getListOfPlayers() throws RemoteException{
        return server.getListOfPlayers();    }

    @Override
    public int getSizeOfPlayers() throws RemoteException {
        return getListOfPlayers().size();
    }

    @Override
    public int getTimeSearch() throws RemoteException {
        return server.getTimeSearch();
    }

    @Override
    public int getPlayerTimeMove() throws RemoteException {
        return server.getPlayerTimeMove();
    }

    @Override
    public ArrayList<ViewWP> getWindowChosen() throws RemoteException {
        return windowChosen;
    }

    @Override
    public void setWindowChosen(ArrayList<ViewWP> windowChosen) {
        this.windowChosen = windowChosen;
    }

    @Override
    public HashMap<String, Integer> getHashPlayers() {
        return hashPlayers;
    }

    @Override
    public void setHashPlayers(HashMap<String, Integer> hashPlayers) {
        this.hashPlayers = hashPlayers;
    }

    @Override
    public int getCoordinateSelectedX(){
        return match.getCurrentPlayer().getCoordinateDieSelected().getX();
    }

    @Override
    public int getCoordinateSelectedY(){
        if(match.getCurrentPlayer().getCoordinateDieSelected()==null)
            return -1;
        else
            return match.getCurrentPlayer().getCoordinateDieSelected().getY();
    }

    @Override
    public boolean takeDie(int row, int column) throws RemoteException{
        if(match.getCurrentPlayer().getDieSelected()==null && !match.getCurrentPlayer().getInsertedDie()){
            int index = 3*row + column;
            Coordinate c = new Coordinate(row,column);
            match.getCurrentPlayer().setCoordinateDieSelected(c);
            match.getCurrentPlayer().setDieSelected(match.playerTakeDie(index));
            return true;
        }
        return false;
    }

    @Override
    public boolean takeWPDie(int row, int column) throws RemoteException {
        if(match.getCurrentPlayer().getCoordinateDieSelected()==null){
                Coordinate c = new Coordinate(row,column);
                match.getCurrentPlayer().setCoordinateDieSelected(c);
                match.getCurrentPlayer().setDieSelected(match.getCurrentPlayer().getWindowPattern().getDie(c));
                return true;
            }
            return false;
    }

    @Override
    public boolean positionDie(int row, int column) throws RemoteException{
        if(match.getCurrentPlayer().getDieSelected()!=null && !match.getCurrentPlayer().getInsertedDie()){
            Coordinate coordinate = new Coordinate(row,column);
            if(match.positionDie(match.getCurrentPlayer().getDieSelected(),coordinate)){
                match.draftpoolRemoveDie(match.getCurrentPlayer().getCoordinateDieSelected().getY());
                match.getCurrentPlayer().setCoordinateDieSelected(null);
                match.getCurrentPlayer().setDieSelected(null);
                match.getCurrentPlayer().setInsertedDie(true);
                return true;
            }
        }
        return false;
    }


    @Override
    public synchronized boolean register(String account) throws RemoteException{
        if (server.getListOfClient().contains(account))
            return false;
        else{
            server.addAccount(account);
            return true;
        }
    }

    @Override
    public synchronized boolean register(String account, ServerHandler serverHandler) throws RemoteException{
        if (server.getListOfClient().contains(account))
            return false;
        else{
            server.addAccount(account,serverHandler);
            return true;
        }
    }

    //Inserisce l'account tra i giocatori disabilitandolo, verrà attivato solo il primo entrato
    @Override
    public synchronized boolean login(String account) throws RemoteException {
        if(access(account)){
            disableClient(account);
            return true;
        }
        else
            return false;
    }

    private boolean access(String account){
        //se esiste già il nome salvato nel server non puoi accedere
        if(server.getListOfClient().contains(account)){
            if(!server.getListOfPlayers().contains(account) && server.getListOfPlayers().size()<4){
                server.addPlayers(account);
                return true;
            }
            else{
                try {
                    if(getPlayerState(account).equalsIgnoreCase("disconnected") && server.getListOfPlayers().size()<=4){
                        windowChosen = updateView().getWps();
                        return true;
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public synchronized ArrayList<ViewWP> getRandomWPs(){
        ArrayList<ViewWP> wps = new ArrayList<>();
        for(int i=0; i<4; i++){
            ViewWP wpobject = new ViewWP();
            int x = rg.random();
            WindowPattern wp = wpFactory.createWindowPattern(x);
            wpobject.setName(wp.getTitle());
            wpobject.setDifficulty(Integer.toString(wp.getDifficulty()));
            wpobject.setWps(wp.toMatrix());
            wpobject.setNumberWP(x);
            wps.add(wpobject);
        }
        return wps;
    }

    @Override
    public boolean readyToPlay() throws RemoteException {
        if(windowChosen.size()<getSizeOfPlayers())
            return false;
        else return true;
    }

    //Crea una corrispondenza Player-WP sfruttata nel model per assegnare la giusta WP
    @Override
    public void createHash(int numberWP, String nameClient) {
        hashPlayers.put(nameClient,numberWP);
    }

    //Deve aggiornare il tutto, da fare con gli observer
    @Override
    public ViewData updateView() {
        return ViewData.instance();
    }

    //Aggiunge la finestra scelta
    @Override
    public void addWindow(ViewWP wpmodel){
        windowChosen.add(wpmodel);
    }
    //Aggiunge il nome della finestra scelta
    @Override
    public void addWindowName (String wpmodel){
        nameWPChosen.add(wpmodel);
    }

    @Override
    public String getCurrentPlayerName(){
        return match.getCurrentPlayer().getName();
    }

    //Salta volontariamente il turno oppure forzatamente dalla fine del timer de giocatore
    @Override
    public void skip(String clientName) throws RemoteException{
        if(getPlayerState(clientName).equals("enabled")) {
            if (!active) {
                timeout(clientName);
            }
            timer.cancel();
            disableClient(getCurrentPlayerName());
            turn++;
            if (turn == getSizeOfPlayers() * 2) {
                if (match.getRound() == 10) isFinish = true;
                else {
                    setInsertedDie(false);
                    match.endRound();
                    turn = 0;
                }
            } else {
                setInsertedDie(false);
                match.nextTurn();
            }
            if (!isFinish) {
                while ((getPlayerState(getCurrentPlayerName()).equalsIgnoreCase("disconnected") && (turn < getSizeOfPlayers() * 2)) || (getTool8Used() && (turn < getSizeOfPlayers() * 2))) {
                    turn++;
                    setTool8Used(false);
                    if (turn == getSizeOfPlayers() * 2) {
                        match.endRound();
                        if (match.getRound() == 10) isFinish = true;
                    }
                    else{
                        match.nextTurn();
                    }
                }
                if (turn == getSizeOfPlayers() * 2) {
                    turn = 0;
                }
                if(!isFinish){
                    enableClient(getCurrentPlayerName());
                    controllerTimer.setTimeMoveRemaining(playerMoveTime);
                    timer = new Timer();
                    controllerTimer.startPlayerTimer(this, timer);
                }
            }
        }
    }

    @Override
    public boolean isFinish() {
        return isFinish;
    }

    //Serve a segnalare il giocatore inattivo oppure uscito dalla partita
    private void timeout(String clientName) {
        server.addInactivePlayers(clientName);
    }

    @Override
    public String getPlayerState(String clientName) throws RemoteException{
        return server.getClientState(clientName).toString();
    }

    //disabilita il client (il server ignora le sue richieste)
    @Override
    public ClientState enableClient(String clientName) throws RemoteException {
        return new EnableClient().setState(clientName);
    }

    //attiva il client (il server ascolta le sue richieste
    @Override
    public ClientState disableClient(String clientName) throws RemoteException {
        return new DisableClient().setState(clientName);
    }

    @Override
    public void disconnectClient(String clientName) throws RemoteException {
         new DisconnectedClient().setState(clientName);
    }

    //Utilizza la ToolCard, ancora da implementare (forse serve anche quale tool è stata scelta come parametro)
    @Override
    public boolean useToolCard(int idCard, ToolView toolView){
        PlayerToolParameter pt = null;
        switch(idCard){
            case 1: {
                pt = new PlayerToolParameter(new Coordinate(toolView.getStartRow1(), toolView.getStartCol1()), toolView.getDieModified());
                break;
            }case 2:{
                pt = new PlayerToolParameter(new Coordinate(toolView.getStartRow1(), toolView.getStartCol1()),new Coordinate(toolView.getEndRow1(), toolView.getEndCol1()));
                break;
            }case 3:{
                pt = new PlayerToolParameter(new Coordinate(toolView.getStartRow1(), toolView.getStartCol1()),new Coordinate(toolView.getEndRow1(), toolView.getEndCol1()));
                break;/*
            }case 4:{
                pt = new PlayerToolParameter();
                break;*/
            }case 5:{
                pt = new PlayerToolParameter(new Coordinate(toolView.getStartRow1(), toolView.getStartCol1()),new Coordinate(toolView.getEndRow1(), toolView.getEndCol1()),toolView.getRound());
                break;
            }case 6:{
                pt = new PlayerToolParameter(new Coordinate(toolView.getStartRow1(), toolView.getStartCol1()));
                break;
            }case 7:{
                pt = new PlayerToolParameter(toolView.getListOfCoordinateY());
                break;
            }case 8:{
                pt = new PlayerToolParameter();
                break;
            }case 9:{
                pt = new PlayerToolParameter(new Coordinate(toolView.getStartRow1(), toolView.getStartCol1()), new Coordinate(toolView.getEndRow1(), toolView.getEndCol1()));
                break;
            }case 10:{
                pt = new PlayerToolParameter(new Coordinate(toolView.getStartRow1(), toolView.getStartCol1()));
                break;/*
            }case 11:{
                pt = new PlayerToolParameter();
                break;
            }case 12:{
                pt = new PlayerToolParameter();
                break;
            */}default:{
                break;
            }
        }
        if(match.playerUseTool(idCard,pt))
            return true;
        else
            return false;
    }

    @Override
    public synchronized boolean waitForPlayers() throws RemoteException{
        if(getSizeOfPlayers()==1 && access == 0){ //Fa qualcosa mentre aspetta
            access++;
        }
        if(getSizeOfPlayers()==2 && access == 1){
            controllerTimer.search(this,timer);
            access++;
        }
        if(getTimeRemaining()<=0){
            timer.cancel();
            return true;
        }
        else
            return false;
    }

    @Override
    public int getTimeRemaining() {
        return controllerTimer.getTimeRemaining();
    }


    @Override
    public synchronized ArrayList<ViewWP> getPlayersWPs(String name) throws RemoteException{
        ArrayList<ViewWP> enemyWPs = new ArrayList<>();
        for(ViewWP wp : getWindowChosen()){
            if(getHashPlayers().get(name) != wp.getNumberWP()) {
                enemyWPs.add(wp);
            }
        }
        return enemyWPs;
    }

    @Override
    public void setActive(Boolean active) {
        this.active=active;
    }

    @Override
    public boolean getActive() throws RemoteException {
        return active;
    }

    @Override
    public void rejoinedPlayer(String name) throws RemoteException {
        if(server.getBannedPlayer().contains(name)){
            server.getBannedPlayer().remove(name);
        }
    }

    @Override
    public int getTimeMove() throws RemoteException {
        return controllerTimer.getTimeMoveRemaining();
    }

    @Override
    public void setNullPlayer() throws RemoteException {
        match.getCurrentPlayer().setDieSelected(null);
        match.getCurrentPlayer().setCoordinateDieSelected(null);
    }

    @Override
    public int getRound() throws RemoteException {
        return match.getRound();
    }

    @Override
    public ArrayList<String> getBannedList() {
        return server.getBannedPlayer();
    }

    @Override
    public ViewWP getWP(String userName) throws RemoteException {
        String nameWP = null;
        for(Player p : match.getPlayers()){
            if(p.getName().equals(userName)){
                nameWP = p.getWindowPattern().getTitle();
            }
        }
        for(int i=0; i<windowChosen.size(); i++){
            String nameWC = windowChosen.get(i).getName();
            if(nameWC.equalsIgnoreCase(nameWP)){
                return windowChosen.get(i);
            }
        }
        return null;
    }

    @Override
    public synchronized void orderWPChoise() throws RemoteException {
        if(access==2){
            ArrayList<ViewWP> windowOrdered = new ArrayList<>();
            ArrayList<String> players = server.getListOfPlayers();
            for(String p : players){
                int num = hashPlayers.get(p);
                for(ViewWP w: windowChosen){
                    if(num==w.getNumberWP())
                        windowOrdered.add(w);
                }
            }
            windowChosen = windowOrdered;
            access++;
        }
    }

    @Override
    public Integer getScore(String name) throws RemoteException {
        int indexPlayer = getListofMatchPlayers().indexOf(name);
        return match.getPlayers().get(indexPlayer).getScore();
    }

    @Override
    public void calculateScore() throws RemoteException {
        match.playersScore();
    }

    @Override
    public String findWinner() throws RemoteException {
        return match.findWinner().getName();
    }

    @Override
    public ArrayList<String> getListofMatchPlayers() throws RemoteException {
        ArrayList<String> nameMatchPlayers = new ArrayList<>();
        for(int i=0; i<getListOfPlayers().size();i++){
            String name = match.getPlayers().get(i).getName();
            nameMatchPlayers.add(name);
        }
        return nameMatchPlayers;
    }

    @Override
    public boolean getInsertedDie() throws RemoteException {
        return match.getCurrentPlayer().getInsertedDie();
    }

    @Override
    public void setInsertedDie(boolean b) throws RemoteException {
        match.getCurrentPlayer().setInsertedDie(b);
    }

    @Override
    public boolean getTool8Used() throws RemoteException {
        return match.getCurrentPlayer().getTool8Used();
    }

    @Override
    public void setTool8Used(boolean b) throws RemoteException {
        match.getCurrentPlayer().setTool8Used(b);
    }

    @Override
    public boolean getClockwiseRound() throws RemoteException {
        return match.getClockwiseRound();
    }


    @Override
    public String getPVCard(String name) throws RemoteException {
        String colorPV = null;
        ArrayList<Player> playersModel = match.getPlayers();
        for(Player p : playersModel){
            if(name.equals(p.getName())){
                colorPV = PVObjectiveCard.getPVCard(p.getPvObjectiveCard().getColor());
            }
        }
        return colorPV;
    }

    //Chiama il metodo inizializzatore del Match, restituisce le WP grafiche, le PBCard e le ToolCard (PVCard da prendere a parte dopo)
    @Override
    public synchronized ViewData initializeView() throws RemoteException {
        match = istanceMatch();
        ViewData init = match.getInit();
        init.setWps(windowChosen);
        enableClient(getCurrentPlayerName());
        if(startTimer==0){
            timer = new Timer();
            controllerTimer.startPlayerTimer(this,timer);
            startTimer++;
        }
        return init;
    }


    private Match istanceMatch() throws RemoteException {
        if(match == null)
            match = new Match(1,getListOfPlayers(),reorderWPandPlayer());
        return match;
    }

    private ArrayList<Integer> reorderWPandPlayer() throws RemoteException {
        ArrayList<Integer> ordinateWPs = new ArrayList<>();
        for(String player : getListOfPlayers()){
           ordinateWPs.add(hashPlayers.get(player));
        }
        return ordinateWPs;
    }
}
