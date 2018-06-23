package ingsw.view.CLI;

import ingsw.view.ToolView;

public class ToolCard {


    public static void toolCard1(PlayGame playGame) {
        playGame.getClient().useToolCard(1,null);
        int selectedDie = playGame.selectDie();
        ToolView toolView = new ToolView();
        toolView.setStartRow1(0);
        toolView.setStartCol1(selectedDie);
        System.out.println("Select an operation:\n1 - Increase die's value\n2 - Decrease die's value");
        int operation = Main.validateIntegerInput(1,2);
        if (operation == 1) toolView.setDieModified(playGame.dpDieValue(selectedDie)+1);
        else toolView.setDieModified(playGame.dpDieValue(selectedDie)-1);
        if (toolView.getDieModified() < 1 || toolView.getDieModified() >6) System.out.println(ToString.printColored(ToString.ANSI_RED,"Forbidden operation"));
        else if(playGame.getClient().useToolCard(10,toolView)) {
                System.out.println("Tool card successfully used");
                playGame.setToolCardAlreadyUsed(true);
            } else System.out.println("Tool card not available");
    }

    public static void toolCard2() {

    }

    public static void toolCard3() {

    }

    public static void toolCard4() {

    }

    public static void toolCard5() {

    }

    public static void toolCard6() {

    }

    public static void toolCard7() {

    }

    public static void toolCard8() {

    }

    public static void toolCard9() {

    }

    public static void toolCard10(PlayGame playGame) {
        int selectedDie = playGame.selectDie();
        ToolView toolView = new ToolView();
        toolView.setStartRow1(0);
        toolView.setStartCol1(selectedDie);
        if(playGame.getClient().useToolCard(10,toolView)) {
            System.out.println("Tool card successfully used");
            playGame.setToolCardAlreadyUsed(true);
        }
        else System.out.println("Tool card not available");
    }

    public static void toolCard11() {

    }

    public static void toolCard12() {

    }
}
