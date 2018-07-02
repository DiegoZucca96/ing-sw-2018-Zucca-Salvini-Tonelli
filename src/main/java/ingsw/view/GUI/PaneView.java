package ingsw.view.GUI;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/** Author : Alessio Tonelli
 *
 * This is how we manage rendering.
 *
 * Since in PlayGame, class regarded to create the table on which we play, windows are a grid made of 20 panes on which
 * we create an other grid made of buttons, we create a pane for each cell with a specific background.
 *
 * This background is made of a color and a set of circles disposed like in a real die.
 * When pane is created, two dimensions are available. This is because a distinction between me and my enemies in
 * the game when Playgame is invoked.
 */
public class PaneView extends Pane {

    /**
     * Create pane with a dimension.
     * @param number , value of cell.
     * @param color , value of cell.
     * @param me , 0 if me, 1 if others.
     */
    PaneView(String number, String color, int me){
        if(me==0)
            this.setPrefSize(50, 50);
        else
            this.setPrefSize(40, 40);
        createCell(number, color,me);
    }

    /**
     * Set background to pane and call method to set circles
     * @param number
     * @param color
     * @param me
     */
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

    /**Create circleNum circles in two different way.
     * It depends on me.
     *
     * @param circleNum , number of circle to create.
     * @param me
     */
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
