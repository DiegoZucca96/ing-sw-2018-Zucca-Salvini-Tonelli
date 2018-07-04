package ingsw.view.cli;

import ingsw.model.InfoCell;
import ingsw.model.ViewWP;

/**
 * Author: Elio Salvini
 *
 * Class used to convert game's objects to strings
 */
public class ToString {

    //ANSI codes used to change color and background of console's output
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    /**
     * Method used to obtain a colored string
     * @param color     ANSI code that refers to wanted color
     * @param message   message you want to print
     * @return          colored string
     */
    public static String printColored(String color, String message){
        return color + message + ANSI_RESET;
    }

    /**
     * Method that converts viewWP's data referring to windows patterns to a string
     * @param viewWP    object containing windows pattern data
     * @return          a string representing the window pattern
     *
     * @see ViewWP
     */
    public static String viewWPToString(ViewWP viewWP){
        String result = viewWP.getName() + "\n" + "Difficulty: " + viewWP.getDifficulty() + "\n";
        InfoCell[][] wp = viewWP.getWp();
        for(int i=0; i<4; i++){
            result = result + " | ";
            for (int j=0; j<5; j++){
                result = result + infoCellToString(wp[i][j]) + " | ";
            }
            result = result + "\n";
        }
        return result;
    }

    /**
     * Method that converts infoCell's data referring to windows patterns to a string
     * @param infoCell  object containing window pattern's cell data
     * @return          a string representing the window pattern's cell
     *
     * @see InfoCell
     */
    public static String infoCellToString(InfoCell infoCell){
        if(infoCell.getDie() != null) return viewDieToString(infoCell.getDie());
        String result = "";
        switch (infoCell.getNum()){
            case 1: result = " 1 "; break;
            case 2: result = " 2 "; break;
            case 3: result = " 3 "; break;
            case 4: result = " 4 "; break;
            case 5: result = " 5 "; break;
            case 6: result = " 6 "; break;
            default:
                switch (infoCell.getColor()){
                    case GREEN: result = printColored(ANSI_GREEN," G "); break;
                    case BLUE: result = printColored(ANSI_CYAN," B "); break;
                    case RED: result = printColored(ANSI_RED," R "); break;
                    case YELLOW: result = printColored(ANSI_YELLOW," Y "); break;
                    case VIOLET: result = printColored(ANSI_PURPLE," V "); break;
                    case WHITE: result = printColored(ANSI_WHITE," W "); break;
            }
        }

        return result;
    }

    /**
     * Method that converts view die's data to a string
     * @param die   die to convert in a string for the CLI
     * @return      a string representing the die
     *
     * @see ingsw.model.ViewData
     */
    public static String viewDieToString(String die){
        if (die.substring(die.indexOf('(')+1, die.indexOf(',')).equals("0")) return "empty";
        String result = "";
        switch (die.substring(die.indexOf(',')+1, die.indexOf(')'))){
            case "RED": result = result + ANSI_RED_BACKGROUND + ANSI_BLACK; break;
            case "GREEN": result = result + ANSI_GREEN_BACKGROUND + ANSI_BLACK; break;
            case "YELLOW": result = result + ANSI_YELLOW_BACKGROUND + ANSI_BLACK; break;
            case "VIOLET": result = result + ANSI_PURPLE_BACKGROUND + ANSI_BLACK; break;
            case "BLUE": result = result + ANSI_CYAN_BACKGROUND + ANSI_BLACK; break;
            default: break;
        }
        return result + " " + die.substring(die.indexOf('(')+1, die.indexOf(',')) + " " + ANSI_RESET;
    }

    /**
     * Method that converts view private objective cards's data referring to windows patterns to a string
     * @param card  private card from viewData
     * @return      a string representing the private cards
     *
     * @see ingsw.model.ViewData
     */
    public static String viewPVCardToString(String card){
        switch(card){
            case "/Private1.png": return printColored(ANSI_RED,"RED");
            case "/Private2.png": return printColored(ANSI_YELLOW,"YELLOW");
            case "/Private3.png": return printColored(ANSI_GREEN,"GREEN");
            case "/Private4.png": return printColored(ANSI_CYAN,"BLUE");
            case "/Private5.png": return printColored(ANSI_PURPLE,"VIOLET");
            default: return "Card not found";
        }
    }
}
