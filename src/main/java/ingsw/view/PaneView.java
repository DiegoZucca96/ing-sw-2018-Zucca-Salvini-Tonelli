package ingsw.view;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PaneView extends Pane {

    PaneView(String number, String color, int me){
        if(me==0)
            this.setPrefSize(50, 50);
        else
            this.setPrefSize(40, 40);
        createCell(number, color,me);
    }

    private void createCell(String number, String color, int me) {
        if(number.equalsIgnoreCase("0") && color.equalsIgnoreCase("WHITE"))
            this.setStyle("-fx-background-color: white");
        else if(color.equalsIgnoreCase("WHITE")){
            this.setStyle("-fx-background-color: black");
            setCircle(Integer.parseInt(number), me);
        }else if(color.equalsIgnoreCase("RED")){
            this.setStyle("-fx-background-color: red");
            setCircle(Integer.parseInt(number), me);
        }else if(color.equalsIgnoreCase("BLUE")){
            this.setStyle("-fx-background-color: blue");
            setCircle(Integer.parseInt(number), me);
        }else if(color.equalsIgnoreCase("YELLOW")){
            this.setStyle("-fx-background-color: yellow");
            setCircle(Integer.parseInt(number), me);
        }else if(color.equalsIgnoreCase("VIOLET")){
            this.setStyle("-fx-background-color: violet");
            setCircle(Integer.parseInt(number), me);
        }else if(color.equalsIgnoreCase("GREEN")){
            this.setStyle("-fx-background-color: green");
            setCircle(Integer.parseInt(number), me);
        }
    }

    private void setCircle(int circleNum, int me) {
        if(me == 0){
            switch (circleNum) {
                case 1: {
                    Circle circle = new Circle(3, Color.WHITE);
                    circle.setCenterX(25);
                    circle.setCenterY(25);
                    getChildren().add(circle);
                    break;
                }
                case 2: {
                    Circle circle1 = new Circle(3, Color.WHITE);
                    Circle circle2 = new Circle(3, Color.WHITE);
                    circle1.setCenterX(10);
                    circle1.setCenterY(40);
                    circle2.setCenterX(40);
                    circle2.setCenterY(10);
                    getChildren().addAll(circle1, circle2);
                    break;
                }
                case 3: {
                    Circle circle1 = new Circle(3, Color.WHITE);
                    Circle circle2 = new Circle(3, Color.WHITE);
                    Circle circle3 = new Circle(3, Color.WHITE);
                    circle1.setCenterX(10);
                    circle1.setCenterY(40);
                    circle2.setCenterX(40);
                    circle2.setCenterY(10);
                    circle3.setCenterX(25);
                    circle3.setCenterY(25);
                    getChildren().addAll(circle1, circle2, circle3);
                    break;
                }
                case 4: {
                    Circle circle1 = new Circle(3, Color.WHITE);
                    Circle circle2 = new Circle(3, Color.WHITE);
                    Circle circle3 = new Circle(3, Color.WHITE);
                    Circle circle4 = new Circle(3, Color.WHITE);
                    circle1.setCenterX(10);
                    circle1.setCenterY(40);
                    circle2.setCenterX(40);
                    circle2.setCenterY(10);
                    circle3.setCenterX(10);
                    circle3.setCenterY(10);
                    circle4.setCenterX(40);
                    circle4.setCenterY(40);
                    getChildren().addAll(circle1, circle2, circle3, circle4);
                    break;
                }
                case 5: {
                    Circle circle1 = new Circle(3, Color.WHITE);
                    Circle circle2 = new Circle(3, Color.WHITE);
                    Circle circle3 = new Circle(3, Color.WHITE);
                    Circle circle4 = new Circle(3, Color.WHITE);
                    Circle circle5 = new Circle(3, Color.WHITE);
                    circle1.setCenterX(10);
                    circle1.setCenterY(40);
                    circle2.setCenterX(40);
                    circle2.setCenterY(10);
                    circle3.setCenterX(10);
                    circle3.setCenterY(10);
                    circle4.setCenterX(40);
                    circle4.setCenterY(40);
                    circle5.setCenterX(25);
                    circle5.setCenterY(25);
                    getChildren().addAll(circle1, circle2, circle3, circle4, circle5);
                    break;
                }
                case 6: {
                    Circle circle1 = new Circle(3, Color.WHITE);
                    Circle circle2 = new Circle(3, Color.WHITE);
                    Circle circle3 = new Circle(3, Color.WHITE);
                    Circle circle4 = new Circle(3, Color.WHITE);
                    Circle circle5 = new Circle(3, Color.WHITE);
                    Circle circle6 = new Circle(3, Color.WHITE);
                    circle1.setCenterX(10);
                    circle1.setCenterY(10);
                    circle2.setCenterX(10);
                    circle2.setCenterY(25);
                    circle3.setCenterX(10);
                    circle3.setCenterY(40);
                    circle4.setCenterX(40);
                    circle4.setCenterY(10);
                    circle5.setCenterX(40);
                    circle5.setCenterY(25);
                    circle6.setCenterX(40);
                    circle6.setCenterY(40);
                    getChildren().addAll(circle1, circle2, circle3, circle4, circle5, circle6);
                    break;
                }
                default:
                    break;
            }
        }else{
            switch (circleNum) {
                case 1: {
                    Circle circle = new Circle(3, Color.WHITE);
                    circle.setCenterX(20);
                    circle.setCenterY(20);
                    getChildren().add(circle);
                    break;
                }
                case 2: {
                    Circle circle1 = new Circle(3, Color.WHITE);
                    Circle circle2 = new Circle(3, Color.WHITE);
                    circle1.setCenterX(10);
                    circle1.setCenterY(30);
                    circle2.setCenterX(30);
                    circle2.setCenterY(10);
                    getChildren().addAll(circle1, circle2);
                    break;
                }
                case 3: {
                    Circle circle1 = new Circle(3, Color.WHITE);
                    Circle circle2 = new Circle(3, Color.WHITE);
                    Circle circle3 = new Circle(3, Color.WHITE);
                    circle1.setCenterX(10);
                    circle1.setCenterY(30);
                    circle2.setCenterX(30);
                    circle2.setCenterY(10);
                    circle3.setCenterX(20);
                    circle3.setCenterY(20);
                    getChildren().addAll(circle1, circle2, circle3);
                    break;
                }
                case 4: {
                    Circle circle1 = new Circle(3, Color.WHITE);
                    Circle circle2 = new Circle(3, Color.WHITE);
                    Circle circle3 = new Circle(3, Color.WHITE);
                    Circle circle4 = new Circle(3, Color.WHITE);
                    circle1.setCenterX(10);
                    circle1.setCenterY(30);
                    circle2.setCenterX(30);
                    circle2.setCenterY(10);
                    circle3.setCenterX(10);
                    circle3.setCenterY(10);
                    circle4.setCenterX(30);
                    circle4.setCenterY(30);
                    getChildren().addAll(circle1, circle2, circle3, circle4);
                    break;
                }
                case 5: {
                    Circle circle1 = new Circle(3, Color.WHITE);
                    Circle circle2 = new Circle(3, Color.WHITE);
                    Circle circle3 = new Circle(3, Color.WHITE);
                    Circle circle4 = new Circle(3, Color.WHITE);
                    Circle circle5 = new Circle(3, Color.WHITE);
                    circle1.setCenterX(10);
                    circle1.setCenterY(30);
                    circle2.setCenterX(30);
                    circle2.setCenterY(10);
                    circle3.setCenterX(10);
                    circle3.setCenterY(10);
                    circle4.setCenterX(30);
                    circle4.setCenterY(30);
                    circle5.setCenterX(20);
                    circle5.setCenterY(20);
                    getChildren().addAll(circle1, circle2, circle3, circle4, circle5);
                    break;
                }
                case 6: {
                    Circle circle1 = new Circle(3, Color.WHITE);
                    Circle circle2 = new Circle(3, Color.WHITE);
                    Circle circle3 = new Circle(3, Color.WHITE);
                    Circle circle4 = new Circle(3, Color.WHITE);
                    Circle circle5 = new Circle(3, Color.WHITE);
                    Circle circle6 = new Circle(3, Color.WHITE);
                    circle1.setCenterX(10);
                    circle1.setCenterY(10);
                    circle2.setCenterX(10);
                    circle2.setCenterY(20);
                    circle3.setCenterX(10);
                    circle3.setCenterY(30);
                    circle4.setCenterX(30);
                    circle4.setCenterY(10);
                    circle5.setCenterX(30);
                    circle5.setCenterY(20);
                    circle6.setCenterX(30);
                    circle6.setCenterY(30);
                    getChildren().addAll(circle1, circle2, circle3, circle4, circle5, circle6);
                    break;
                }
                default:
                    break;
            }
        }
    }
}
