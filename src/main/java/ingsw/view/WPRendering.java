package ingsw.view;


import ingsw.Client;
import ingsw.model.ViewWP;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.ArrayList;

/**Author : Alessio Tonelli
 *
 *
 * CHOOSE WINDOW PATTERN
 *
 * this is the class which makes rendering of the window pattern from XML file
 *
 * Each window is a GridPane makes by 20 Button
 * */

public class WPRendering {

    private Client client;
    private static final String styleSheet = "-fx-text-fill: goldenrod; -fx-font: italic 15 \"serif\"; -fx-padding: 0 0 20 0; -fx-text-alignment: center";

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

        for(int i=0; i<4; i++){
            for( int j=0; j<5; j++){
                Button btnCell = new Button();
                btnCell.setPrefSize(50, 50);
                String numCell = displayWindow.get(0).getWps()[i][j].getNumCol().get(0);
                String colorCell = displayWindow.get(0).getWps()[i][j].getNumCol().get(1);
                String pathCell = pathCell(numCell, colorCell);
                Image myImage = new Image(pathCell, 50, 50, false, false);
                BackgroundImage myBI= new BackgroundImage(myImage,
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                        BackgroundSize.DEFAULT);
                btnCell.setBackground(new Background(myBI));
                grid1.add(btnCell, j, i);       //ricorda che il primo è colonna, il secondo riga

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
            //new Private().display(client);
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

        for(int i=0; i<4; i++){
            for( int j=0; j<5; j++){
                Button btnCell = new Button();
                btnCell.setPrefSize(50, 50);
                String numCell = displayWindow.get(1).getWps()[i][j].getNumCol().get(0);
                String colorCell = displayWindow.get(1).getWps()[i][j].getNumCol().get(1);
                String pathCell = pathCell(numCell, colorCell);
                Image myImage = new Image(pathCell, 50, 50, false, false);
                BackgroundImage myBI= new BackgroundImage(myImage,
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                        BackgroundSize.DEFAULT);
                btnCell.setBackground(new Background(myBI));
                grid2.add(btnCell, j, i);

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
            //new Private().display(client);
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

        for(int i=0; i<4; i++){
            for( int j=0; j<5; j++){
                Button btnCell = new Button();
                btnCell.setPrefSize(50, 50);
                String numCell = displayWindow.get(2).getWps()[i][j].getNumCol().get(0);
                String colorCell = displayWindow.get(2).getWps()[i][j].getNumCol().get(1);
                String pathCell = pathCell(numCell, colorCell);
                Image myImage = new Image(pathCell, 50, 50, false, false);
                BackgroundImage myBI= new BackgroundImage(myImage,
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                        BackgroundSize.DEFAULT);
                btnCell.setBackground(new Background(myBI));
                grid3.add(btnCell, j, i);

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
            //new Private().display(client);
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

        for(int i=0; i<4; i++){
            for( int j=0; j<5; j++){
                Button btnCell = new Button();
                btnCell.setPrefSize(50, 50);
                String numCell = displayWindow.get(3).getWps()[i][j].getNumCol().get(0);
                String colorCell = displayWindow.get(3).getWps()[i][j].getNumCol().get(1);
                String pathCell = pathCell(numCell, colorCell);
                Image myImage = new Image(pathCell, 50, 50, false, false);
                BackgroundImage myBI= new BackgroundImage(myImage,
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                        BackgroundSize.DEFAULT);
                btnCell.setBackground(new Background(myBI));
                grid4.add(btnCell, j, i);

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
            //new Private().display(client);
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

    public static String pathCell(String number, String color) {

        if (number == null || color == null)
            return null;
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


    public static String pathDie(String number, String color){

        if (number == null || color == null)
            return null;
        else if (number.equalsIgnoreCase("1") && color.equalsIgnoreCase(",RED"))
            return "/1red.png";
        else if (number.equalsIgnoreCase("2")&& color.equalsIgnoreCase(",RED"))
            return "/2red.png";
        else if (number.equalsIgnoreCase("3")&& color.equalsIgnoreCase(",RED"))
            return "/3red.png";
        else if (number.equalsIgnoreCase("4")&& color.equalsIgnoreCase(",RED"))
            return "/4red.png";
        else if (number.equalsIgnoreCase("5")&& color.equalsIgnoreCase(",RED"))
            return "/5red.png";
        else if (number.equalsIgnoreCase("6")&& color.equalsIgnoreCase(",RED"))
            return "/6red.png";
        else if (number.equalsIgnoreCase("1") && color.equalsIgnoreCase(",YELLOW"))
            return "/1yellow.png";
        else if (number.equalsIgnoreCase("2")&& color.equalsIgnoreCase(",YELLOW"))
            return "/2yellow.png";
        else if (number.equalsIgnoreCase("3")&& color.equalsIgnoreCase(",YELLOW"))
            return "/3yellow.png";
        else if (number.equalsIgnoreCase("4")&& color.equalsIgnoreCase(",YELLOW"))
            return "/4yellow.png";
        else if (number.equalsIgnoreCase("5")&& color.equalsIgnoreCase(",YELLOW"))
            return "/5yellow.png";
        else if (number.equalsIgnoreCase("6")&& color.equalsIgnoreCase(",YELLOW"))
            return "/6yellow.png";
        else if (number.equalsIgnoreCase("1") && color.equalsIgnoreCase(",BLUE"))
            return "/1blue.png";
        else if (number.equalsIgnoreCase("2")&& color.equalsIgnoreCase(",BLUE"))
            return "/2blue.png";
        else if (number.equalsIgnoreCase("3")&& color.equalsIgnoreCase(",BLUE"))
            return "/3blue.png";
        else if (number.equalsIgnoreCase("4")&& color.equalsIgnoreCase(",BLUE"))
            return "/4blue.png";
        else if (number.equalsIgnoreCase("5")&& color.equalsIgnoreCase(",BLUE"))
            return "/5blue.png";
        else if (number.equalsIgnoreCase("6")&& color.equalsIgnoreCase(",BLUE"))
            return "/6blue.png";
        else if (number.equalsIgnoreCase("1") && color.equalsIgnoreCase(",VIOLET"))
            return "/1violet.png";
        else if (number.equalsIgnoreCase("2")&& color.equalsIgnoreCase(",VIOLET"))
            return "/2violet.png";
        else if (number.equalsIgnoreCase("3")&& color.equalsIgnoreCase(",VIOLET"))
            return "/3violet.png";
        else if (number.equalsIgnoreCase("4")&& color.equalsIgnoreCase(",VIOLET"))
            return "/4violet.png";
        else if (number.equalsIgnoreCase("5")&& color.equalsIgnoreCase(",VIOLET"))
            return "/5violet.png";
        else if (number.equalsIgnoreCase("6")&& color.equalsIgnoreCase(",VIOLET"))
            return "/6violet.png";
        else if (number.equalsIgnoreCase("1") && color.equalsIgnoreCase(",GREEN"))
            return "/1green.png";
        else if (number.equalsIgnoreCase("2")&& color.equalsIgnoreCase(",GREEN"))
            return "/2green.png";
        else if (number.equalsIgnoreCase("3")&& color.equalsIgnoreCase(",GREEN"))
            return "/3green.png";
        else if (number.equalsIgnoreCase("4")&& color.equalsIgnoreCase(",GREEN"))
            return "/4green.png";
        else if (number.equalsIgnoreCase("5")&& color.equalsIgnoreCase(",GREEN"))
            return "/5green.png";
        else if (number.equalsIgnoreCase("6")&& color.equalsIgnoreCase(",GREEN"))
            return "/6green.png";
        return null;
    }

}
