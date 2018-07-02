package ingsw.view.GUI;

import ingsw.Client;
import ingsw.model.ViewWP;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.ArrayList;

/**Author : Alessio Tonelli
 *
 * CHOOSE WINDOW PATTERN
 *
 * This is the class which makes rendering of the window pattern from XML file by using an other class: PaneView.
 *
 * Each window is a GridPane makes by 20 Button.
 *
 * @see PaneView
 * */

public class WPRendering {

    private Client client;
    private static final String styleSheet = "-fx-text-fill: goldenrod; -fx-font: italic 15 \"serif\"; -fx-padding: 0 0 20 0; -fx-text-alignment: center";

    /**Method displays the stage with windows, their names and difficulties.
     *
     * @param displayWindow are four windows, including the one client must choose.
     * @param c
     */
    public void display(ArrayList<ViewWP> displayWindow, Client c) {

        this.client=c;
        GridPane root = new GridPane();
        root.setPadding(new Insets(20, 0, 0, 40));
        root.setHgap(40);
        root.setVgap(20);
        Scene s2= new Scene(root);
        Stage windowPattern = new Stage();
        windowPattern.setWidth(1260);
        windowPattern.setHeight(300);

        //WP 1
        GridPane grid1 = new GridPane();
        grid1.setVgap(1.5);
        grid1.setHgap(1.5);
        for(int i=0; i<4; i++){
            for( int j=0; j<5; j++){
                String numCell = Integer.toString(displayWindow.get(0).getWp()[i][j].getNum());
                String colorCell = String.valueOf(displayWindow.get(0).getWp()[i][j].getColor());
                PaneView paneView = new PaneView(numCell, colorCell, 0);
                grid1.add(paneView, j, i);       //ricorda che il primo è colonna, il secondo riga
            }
        }
        root.add(grid1, 0, 0);

        String namePath1 = displayWindow.get(0).getName();
        String diffPath1 = displayWindow.get(0).getDifficulty();
        final Label label1 = new Label();
        label1.setText("                "+namePath1+"   "+diffPath1);
        label1.setStyle(styleSheet);
        root.add(label1, 0, 1);

        label1.setOnMousePressed(event -> {
            ViewWP myWindow= displayWindow.get(0);
            client.createHash(myWindow.getNumberWP(), client.getName());
            client.addWP(myWindow);
            windowPattern.close();
            new Loading(client).display(new Stage(), "WAITING FOR PLAYERS", myWindow);
        });

        label1.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                label1.setScaleX(1.5);
                label1.setScaleY(1.5);
            }
        });

        label1.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                label1.setScaleX(1);
                label1.setScaleY(1);
            }
        });

        //WP 2
        GridPane grid2 = new GridPane();
        grid2.setVgap(1.5);
        grid2.setHgap(1.5);
        for(int i=0; i<4; i++){
            for( int j=0; j<5; j++){
                String numCell = Integer.toString(displayWindow.get(1).getWp()[i][j].getNum());
                String colorCell = String.valueOf(displayWindow.get(1).getWp()[i][j].getColor());
                PaneView paneView = new PaneView(numCell, colorCell, 0);
                grid2.add(paneView, j, i);
            }
        }
        root.add(grid2, 1, 0);

        String namePath2 = displayWindow.get(1).getName();
        String diffPath2 = displayWindow.get(1).getDifficulty();
        final Label label2 = new Label();
        label2.setText("                "+namePath2+"   "+diffPath2);
        label2.setStyle(styleSheet);
        root.add(label2, 1, 1);

        label2.setOnMousePressed(event -> {
            ViewWP myWindow= displayWindow.get(1);
            client.createHash(myWindow.getNumberWP(), client.getName());
            client.addWP(myWindow);
            windowPattern.close();
            new Loading(client).display(new Stage(), "WAITING FOR PLAYERS", myWindow);
        });

        label2.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                label2.setScaleX(1.5);
                label2.setScaleY(1.5);
            }
        });

        label2.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                label2.setScaleX(1);
                label2.setScaleY(1);
            }
        });

        //WP 3
        GridPane grid3 = new GridPane();
        grid3.setVgap(1.5);
        grid3.setHgap(1.5);
        for(int i=0; i<4; i++){
            for( int j=0; j<5; j++){
                String numCell = Integer.toString(displayWindow.get(2).getWp()[i][j].getNum());
                String colorCell = String.valueOf(displayWindow.get(2).getWp()[i][j].getColor());
                PaneView paneView = new PaneView(numCell, colorCell, 0);
                grid3.add(paneView, j, i);
            }
        }
        root.add(grid3, 2, 0);

        String namePath3 = displayWindow.get(2).getName();
        String diffPath3 = displayWindow.get(2).getDifficulty();
        final Label label3 = new Label();
        label3.setText("                "+namePath3+"   "+diffPath3);
        label3.setStyle(styleSheet);
        root.add(label3, 2, 1);

        label3.setOnMousePressed(event -> {
            ViewWP myWindow= displayWindow.get(2);
            client.createHash(myWindow.getNumberWP(), client.getName());
            client.addWP(myWindow);
            windowPattern.close();
            new Loading(client).display(new Stage(), "WAITING FOR PLAYERS", myWindow);
        });

        label3.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                label3.setScaleX(1.5);
                label3.setScaleY(1.5);
            }
        });

        label3.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                label3.setScaleX(1);
                label3.setScaleY(1);
            }
        });

        //WP 4
        GridPane grid4 = new GridPane();
        grid4.setVgap(1.5);
        grid4.setHgap(1.5);
        for(int i=0; i<4; i++){
            for( int j=0; j<5; j++){
                String numCell = Integer.toString(displayWindow.get(3).getWp()[i][j].getNum());
                String colorCell = String.valueOf(displayWindow.get(3).getWp()[i][j].getColor());
                PaneView paneView = new PaneView(numCell, colorCell, 0);
                grid4.add(paneView, j, i);
            }
        }
        root.add(grid4, 3, 0);

        String namePath4 = displayWindow.get(3).getName();
        String diffPath4 = displayWindow.get(3).getDifficulty();
        final Label label4 = new Label();
        label4.setText("                "+namePath4+"   "+diffPath4);
        label4.setStyle(styleSheet);
        root.add(label4, 3, 1);

        label4.setOnMousePressed(event -> {
            ViewWP myWindow= displayWindow.get(3);
            client.createHash(myWindow.getNumberWP(), client.getName());
            client.addWP(myWindow);
            windowPattern.close();
            new Loading(client).display(new Stage(), "WAITING FOR PLAYERS", myWindow);
        });

        label4.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                label4.setScaleX(1.5);
                label4.setScaleY(1.5);
            }
        });

        label4.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                label4.setScaleX(1);
                label4.setScaleY(1);
            }
        });

        windowPattern.setOnCloseRequest(event -> event.consume());
        windowPattern.setTitle("Choose window pattern - "+client.getName());
        windowPattern.setScene(s2);
        Platform.setImplicitExit(false);
        windowPattern.setOnCloseRequest(event -> event.consume());
        windowPattern.resizableProperty().setValue(Boolean.FALSE);
        windowPattern.show();
    }

    /**
     * Why static? Since we need a method to make rendering of dice even in classes invoked during game is running,
     * this method must be reached by them without any association between classes.
     *
     * Parameters are input which determinate the Image of dice
     *
     * ATTENTION: some controls are completely useless since PaneView has been added. In fact, the ones which are regarded to cells
     * won't be never reached.
     * @param number
     * @param color
     * @return path of Image
     */
    public static String path(String number, String color) {
        if (number == null || color == null)
            return null;
        else if (number.equalsIgnoreCase("1") && color.equalsIgnoreCase("RED"))
            return "/1red.png";
        else if (number.equalsIgnoreCase("2")&& color.equalsIgnoreCase("RED"))
            return "/2red.png";
        else if (number.equalsIgnoreCase("3")&& color.equalsIgnoreCase("RED"))
            return "/3red.png";
        else if (number.equalsIgnoreCase("4")&& color.equalsIgnoreCase("RED"))
            return "/4red.png";
        else if (number.equalsIgnoreCase("5")&& color.equalsIgnoreCase("RED"))
            return "/5red.png";
        else if (number.equalsIgnoreCase("6")&& color.equalsIgnoreCase("RED"))
            return "/6red.png";
        else if (number.equalsIgnoreCase("1") && color.equalsIgnoreCase("YELLOW"))
            return "/1yellow.png";
        else if (number.equalsIgnoreCase("2")&& color.equalsIgnoreCase("YELLOW"))
            return "/2yellow.png";
        else if (number.equalsIgnoreCase("3")&& color.equalsIgnoreCase("YELLOW"))
            return "/3yellow.png";
        else if (number.equalsIgnoreCase("4")&& color.equalsIgnoreCase("YELLOW"))
            return "/4yellow.png";
        else if (number.equalsIgnoreCase("5")&& color.equalsIgnoreCase("YELLOW"))
            return "/5yellow.png";
        else if (number.equalsIgnoreCase("6")&& color.equalsIgnoreCase("YELLOW"))
            return "/6yellow.png";
        else if (number.equalsIgnoreCase("1") && color.equalsIgnoreCase("BLUE"))
            return "/1blue.png";
        else if (number.equalsIgnoreCase("2")&& color.equalsIgnoreCase("BLUE"))
            return "/2blue.png";
        else if (number.equalsIgnoreCase("3")&& color.equalsIgnoreCase("BLUE"))
            return "/3blue.png";
        else if (number.equalsIgnoreCase("4")&& color.equalsIgnoreCase("BLUE"))
            return "/4blue.png";
        else if (number.equalsIgnoreCase("5")&& color.equalsIgnoreCase("BLUE"))
            return "/5blue.png";
        else if (number.equalsIgnoreCase("6")&& color.equalsIgnoreCase("BLUE"))
            return "/6blue.png";
        else if (number.equalsIgnoreCase("1") && color.equalsIgnoreCase("VIOLET"))
            return "/1violet.png";
        else if (number.equalsIgnoreCase("2")&& color.equalsIgnoreCase("VIOLET"))
            return "/2violet.png";
        else if (number.equalsIgnoreCase("3")&& color.equalsIgnoreCase("VIOLET"))
            return "/3violet.png";
        else if (number.equalsIgnoreCase("4")&& color.equalsIgnoreCase("VIOLET"))
            return "/4violet.png";
        else if (number.equalsIgnoreCase("5")&& color.equalsIgnoreCase("VIOLET"))
            return "/5violet.png";
        else if (number.equalsIgnoreCase("6")&& color.equalsIgnoreCase("VIOLET"))
            return "/6violet.png";
        else if (number.equalsIgnoreCase("1") && color.equalsIgnoreCase("GREEN"))
            return "/1green.png";
        else if (number.equalsIgnoreCase("2")&& color.equalsIgnoreCase("GREEN"))
            return "/2green.png";
        else if (number.equalsIgnoreCase("3")&& color.equalsIgnoreCase("GREEN"))
            return "/3green.png";
        else if (number.equalsIgnoreCase("4")&& color.equalsIgnoreCase("GREEN"))
            return "/4green.png";
        else if (number.equalsIgnoreCase("5")&& color.equalsIgnoreCase("GREEN"))
            return "/5green.png";
        else if (number.equalsIgnoreCase("6")&& color.equalsIgnoreCase("GREEN"))
            return "/6green.png";
        else if (number.equalsIgnoreCase("1"))
            return "/1.png";
        else if (number.equalsIgnoreCase("2"))
            return "/2.png";
        else if (number.equalsIgnoreCase("3"))
            return "/3.png";
        else if (number.equalsIgnoreCase("4"))
            return "/4.png";
        else if (number.equalsIgnoreCase("5"))
            return "/5.png";
        else if (number.equalsIgnoreCase("6"))
            return "/6.png";
        else if (color.equalsIgnoreCase("WHITE"))
            return "/white.png";
        else if (color.equalsIgnoreCase("RED"))
            return "/red.png";
        else if (color.equalsIgnoreCase("VIOLET"))
            return "/violet.png";
        else if (color.equalsIgnoreCase("GREEN"))
            return "/green.png";
        else if (color.equalsIgnoreCase("BLUE"))
            return "/blue.png";
        else if (color.equalsIgnoreCase("YELLOW"))
            return "/yellow.png";
        return null;
    }
}
