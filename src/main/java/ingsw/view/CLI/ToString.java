package ingsw.view.CLI;

import ingsw.controller.InfoCell;
import ingsw.model.ViewWP;

public class ToString {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static String printColored(String color, String message){
        return color + message + ANSI_RESET;
    }

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

    public static String infoCellToString(InfoCell infoCell){
        if(infoCell.getDie() != null) return viewDieToString(infoCell.getDie());
        String result = "";
        switch (infoCell.getNum()){
            case 1: result = "1"; break;
            case 2: result = "2"; break;
            case 3: result = "3"; break;
            case 4: result = "4"; break;
            case 5: result = "5"; break;
            case 6: result = "6"; break;
            default:
                switch (infoCell.getColor()){
                    case GREEN: result = printColored(ANSI_GREEN,"G"); break;
                    case BLUE: result = printColored(ANSI_CYAN,"B"); break;
                    case RED: result = printColored(ANSI_RED,"R"); break;
                    case YELLOW: result = printColored(ANSI_YELLOW,"Y"); break;
                    case VIOLET: result = printColored(ANSI_PURPLE,"V"); break;
                    case WHITE: result = printColored(ANSI_WHITE,"W"); break;
            }
        }

        return result;
    }

    public static String viewDieToString(String die){
        if (die.substring(die.indexOf('(')+1, die.indexOf(',')).equals("0")) return "empty";
        String result = "";
        switch (die.substring(die.indexOf(',')+1, die.indexOf(')'))){
            case "RED": result = result + ANSI_RED; break;
            case "GREEN": result = result + ANSI_GREEN; break;
            case "YELLOW": result = result + ANSI_YELLOW; break;
            case "VIOLET": result = result + ANSI_PURPLE; break;
            case "BLUE": result = result + ANSI_CYAN; break;
            default: break;
        }
        return result + die.substring(die.indexOf('(')+1, die.indexOf(',')) + ANSI_RESET;
    }

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
