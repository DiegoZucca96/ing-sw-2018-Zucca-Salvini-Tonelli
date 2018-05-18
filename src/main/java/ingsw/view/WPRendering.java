package ingsw.view;




/**
import ingsw.model.InitializerView;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.ArrayList;

//SCELTA DELLE WINDOW PATTERN

public class WPRendering {

    public static void display(InitializerView init){

        GridPane gp= new GridPane();
        gp.setHgap(10);
        gp.setVgap(10);
        gp.setPadding(new Insets(0, 10, 0, 10));
        Scene s2= new Scene(gp);
        Stage windowPattern = new Stage();
        windowPattern.setWidth(1260);
        windowPattern.setHeight(320);

        //Con queste due operazioni ottengo tutti i parametri che sono stati generati dal model (Problema di sincronizzarli con quelli salvati nel model)
        ArrayList<String> images = init.getImages();

        final ImageView wp1 = new ImageView();
        String imagePath1 = images.get(0);
        Image image1 = new Image(imagePath1, 300, 300, false, false);
        wp1.setImage(image1);
        gp.add(wp1, 0,0);

        final ImageView wp2 = new ImageView();
        String imagePath2 = images.get(1);
        Image image2 = new Image(imagePath2, 300, 300, false, false);
        wp2.setImage(image2);
        gp.add(wp2, 1,0);

        final ImageView wp3 = new ImageView();
        String imagePath3 = images.get(2);
        Image image3 = new Image(imagePath3, 300, 300, false, false);
        wp3.setImage(image3);
        gp.add(wp3, 2,0);

        final ImageView wp4 = new ImageView();
        String imagePath4 = images.get(3);
        Image image4 = new Image(imagePath4, 300, 300, false, false);
        wp4.setImage(image4);
        gp.add(wp4, 3,0);

        wp1.setOnMouseClicked(event -> {
            //Play.display(imagePath1,init);
            Loading.display(new Stage(), init, "WAITING FOR PLAYERS", 2, imagePath1);
            windowPattern.close();
        });
        wp2.setOnMouseClicked(event -> {
            Loading.display(new Stage(), init, "WAITING FOR PLAYERS", 2, imagePath2);
            //Play.display(imagePath2,init);
            windowPattern.close();
        });
        wp3.setOnMouseClicked(event -> {
            //Play.display(imagePath3,init);
            Loading.display(new Stage(), init, "WAITING FOR PLAYERS", 2, imagePath3);
            windowPattern.close();
        });
        wp4.setOnMouseClicked(event -> {
            //Play.display(imagePath4,init);
            Loading.display(new Stage(), init, "WAITING FOR PLAYERS", 2, imagePath4);
            windowPattern.close();
        });

        windowPattern.setOnCloseRequest(event -> event.consume());

        windowPattern.setTitle("Choose window pattern");
        windowPattern.setScene(s2);

        Platform.setImplicitExit(false);

        windowPattern.setOnCloseRequest(event -> event.consume());
        windowPattern.resizableProperty().setValue(Boolean.FALSE);
        windowPattern.show();
    }


}
*/


import ingsw.model.InitializerView;
import ingsw.model.windowpattern.CellRender;
import ingsw.model.windowpattern.InfoWindow;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;

/**CHOOSE WINDOW PATTERN
 *
 * IN THIS CLASS YOU CAN SEE HOW EACH GRID IS BUILT BY BUTTONS WHICH MAKE RENDERING OF THE WINDOW
 * */

public class WPRendering {


    public static void display(InitializerView init){

        GridPane root = new GridPane();
        root.setHgap(20);
        root.setVgap(20);
        Scene s2= new Scene(root);
        Stage windowPattern = new Stage();
        windowPattern.setWidth(1060);
        windowPattern.setHeight(300);


        //Con queste due operazioni ottengo tutti i parametri che sono stati generati dal model (Problema di sincronizzarli con quelli salvati nel model)
        ArrayList<InfoWindow> infos = init.getInfo();
        ArrayList<CellRender> cells = init.getImages();

        //WP 1
        GridPane grid1 = new GridPane();
        int k=0;
        for(int i=0; i<4; i++){
            for( int j=0; j<5; j++){
                Button btnCell = new Button();
                btnCell.setPrefSize(50, 50);
                String numCell = cells.get(k).getNumber();
                String colorCell = cells.get(k).getColor();
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

        String namePath1 = infos.get(0).getName();
        String diffPath1 = infos.get(0).getDifficulty();
        final Label label1 = new Label();
        label1.setText("               "+namePath1+diffPath1);
        label1.setTextFill(Color.RED);
        root.add(label1, 0, 1);

        label1.setOnMousePressed(event -> {
            Loading.display(new Stage(), init, "WAITING FOR PLAYERS", 2, grid1);
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

        for(int i=0; i<4; i++){
            for( int j=0; j<5; j++){
                Button btnCell = new Button();
                btnCell.setPrefSize(50, 50);
                String numCell = cells.get(k).getNumber();
                String colorCell = cells.get(k).getColor();
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

        String imagePath2 = infos.get(1).getName()+infos.get(1).getDifficulty();
        final Label label2 = new Label();
        label2.setText("               "+imagePath2);
        label2.setTextFill(Color.RED);
        root.add(label2, 1, 1);

        label2.setOnMousePressed(event -> {
            Loading.display(new Stage(), init, "WAITING FOR PLAYERS", 2, grid2);
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

        for(int i=0; i<4; i++){
            for( int j=0; j<5; j++){
                Button btnCell = new Button();
                btnCell.setPrefSize(50, 50);
                String numCell = cells.get(k).getNumber();
                String colorCell = cells.get(k).getColor();
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

        String imagePath3 = infos.get(2).getName()+infos.get(2).getDifficulty();
        final Label label3 = new Label();
        label3.setText("               "+imagePath3);
        label3.setTextFill(Color.RED);
        root.add(label3, 2, 1);

        label3.setOnMousePressed(event -> {
            Loading.display(new Stage(), init, "WAITING FOR PLAYERS", 3, grid3);
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

        for(int i=0; i<4; i++){
            for( int j=0; j<5; j++){
                Button btnCell = new Button();
                btnCell.setPrefSize(50, 50);
                String numCell = cells.get(k).getNumber();
                String colorCell = cells.get(k).getColor();
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

        String imagePath4 = infos.get(3).getName()+infos.get(3).getDifficulty();
        final Label label4 = new Label();
        label4.setText("               "+imagePath4);
        label4.setTextFill(Color.RED);
        root.add(label4, 3, 1);

        label4.setOnMousePressed(event -> {
            Loading.display(new Stage(), init, "WAITING FOR PLAYERS", 2, grid4);
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

    private static String pathCell(String number, String color) {

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
