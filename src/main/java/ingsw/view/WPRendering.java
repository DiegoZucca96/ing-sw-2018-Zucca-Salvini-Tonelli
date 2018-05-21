package ingsw.view;


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

    private static final String styleSheet = "-fx-text-fill: goldenrod; -fx-font: italic 15 \"serif\"; -fx-padding: 0 0 20 0; -fx-text-alignment: center";

    public static void display(ArrayList<ArrayList<String>> init) {

        GridPane root = new GridPane();
        root.setPadding(new Insets(20, 0, 0, 40));
        root.setHgap(40);
        root.setVgap(20);
        Scene s2= new Scene(root);
        Stage windowPattern = new Stage();
        windowPattern.setWidth(1260);
        windowPattern.setHeight(300);


        //Con queste due operazioni ottengo tutti i parametri che sono stati generati dal model (Problema di sincronizzarli con quelli salvati nel model)
        //ArrayList<InfoWindow> infos = init.getInfo();
       //ArrayList<Cell> cells = init.getImages();


        //WP 1
        GridPane grid1 = new GridPane();
        int k=2;
        for(int i=0; i<4; i++){
            for( int j=0; j<5; j++){
                Button btnCell = new Button();
                btnCell.setPrefSize(50, 50);
                String cell = init.get(0).get(k);
                String numCell = cell.substring(cell.indexOf(':')+1,cell.indexOf(',')-1);
                String colorCell = cell.substring(cell.indexOf(",")+1);
                String pathCell = pathCell(numCell, colorCell);
                Image myImage = new Image(pathCell, 50, 50, false, false);
                BackgroundImage myBI= new BackgroundImage(myImage,
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                        BackgroundSize.DEFAULT);
                btnCell.setBackground(new Background(myBI));
                grid1.add(btnCell, j, i);
                k++;
            }
        }

        root.add(grid1, 0, 0);

        String namePath1 = init.get(0).get(0);
        String diffPath1 = init.get(0).get(1);
        final Label label1 = new Label();
        label1.setText("               "+namePath1+diffPath1);
        label1.setStyle(styleSheet);
        root.add(label1, 0, 1);

        label1.setOnMousePressed(event -> {
            ArrayList<String> myWindow= init.get(0);
            Loading.display(new Stage(), "WAITING FOR PLAYERS", 2, myWindow);
            windowPattern.close();
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
        k=2;
        for(int i=0; i<4; i++){
            for( int j=0; j<5; j++){
                Button btnCell = new Button();
                btnCell.setPrefSize(50, 50);
                String cell = init.get(1).get(k);
                String numCell = cell.substring(cell.indexOf(':')+1,cell.indexOf(',')-1);
                String colorCell = cell.substring(cell.indexOf(",")+1);
                String pathCell = pathCell(numCell, colorCell);
                Image myImage = new Image(pathCell, 50, 50, false, false);
                BackgroundImage myBI= new BackgroundImage(myImage,
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                        BackgroundSize.DEFAULT);
                btnCell.setBackground(new Background(myBI));
                grid2.add(btnCell, j, i);
                k++;
            }
        }

        root.add(grid2, 1, 0);

        String namePath2 = init.get(1).get(0);
        String diffPath2 = init.get(1).get(1);
        final Label label2 = new Label();
        label2.setText("               "+namePath2+diffPath2);
        label2.setStyle(styleSheet);
        root.add(label2, 1, 1);

        label2.setOnMousePressed(event -> {
            ArrayList<String> myWindow= init.get(1);
            Loading.display(new Stage(), "WAITING FOR PLAYERS", 2, myWindow);
            windowPattern.close();
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
        k=2;
        for(int i=0; i<4; i++){
            for( int j=0; j<5; j++){
                Button btnCell = new Button();
                btnCell.setPrefSize(50, 50);
                String cell = init.get(2).get(k);
                String numCell = cell.substring(cell.indexOf(':')+1,cell.indexOf(',')-1);
                String colorCell = cell.substring(cell.indexOf(",")+1);
                String pathCell = pathCell(numCell, colorCell);
                Image myImage = new Image(pathCell, 50, 50, false, false);
                BackgroundImage myBI= new BackgroundImage(myImage,
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                        BackgroundSize.DEFAULT);
                btnCell.setBackground(new Background(myBI));
                grid3.add(btnCell, j, i);
                k++;
            }
        }
        root.add(grid3, 2, 0);

        String namePath3 = init.get(2).get(0);
        String diffPath3 = init.get(2).get(1);
        final Label label3 = new Label();
        label3.setText("               "+namePath3+diffPath3);
        label3.setStyle(styleSheet);
        root.add(label3, 2, 1);

        label3.setOnMousePressed(event -> {
            ArrayList<String> myWindow= init.get(2);
            Loading.display(new Stage(), "WAITING FOR PLAYERS", 2, myWindow);
            windowPattern.close();
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
        k=2;
        for(int i=0; i<4; i++){
            for( int j=0; j<5; j++){
                Button btnCell = new Button();
                btnCell.setPrefSize(50, 50);
                String cell = init.get(3).get(k);
                String numCell = cell.substring(cell.indexOf(':')+1,cell.indexOf(',')-1);
                String colorCell = cell.substring(cell.indexOf(",")+1);
                String pathCell = pathCell(numCell, colorCell);
                Image myImage = new Image(pathCell, 50, 50, false, false);
                BackgroundImage myBI= new BackgroundImage(myImage,
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                        BackgroundSize.DEFAULT);
                btnCell.setBackground(new Background(myBI));
                grid4.add(btnCell, j, i);
                k++;
            }
        }

        root.add(grid4, 3, 0);

        String namePath4 = init.get(3).get(0);
        String diffPath4 = init.get(3).get(0);
        final Label label4 = new Label();
        label4.setText("               "+namePath4+diffPath4);
        label4.setStyle(styleSheet);
        root.add(label4, 3, 1);

        label4.setOnMousePressed(event -> {
            ArrayList<String> myWindow= init.get(3);
            Loading.display(new Stage(), "WAITING FOR PLAYERS", 2, myWindow);
            windowPattern.close();
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

        windowPattern.setTitle("Choose window pattern");
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


}
