package ingsw.model;

/**
 * Author : Diego Zucca
 *
 * This class creates in a random way the private cards of every player
 */
public class PVObjectiveCard {

    private String comment;
    private String title;
    private Color color;

    /**
     * Constructor
     * @param color it is the color of the private card
     */
    public PVObjectiveCard (Color color){
        switch(color){
            case RED:{
                this.title= "Sfumature Rosse • Privata" ;
                this.comment = "Somma dei valori\n" + "su tutti i dadi rossi";
                break;
            }case BLUE:{
                this.title= "Sfumature Blu • Privata" ;
                this.comment = "Somma dei valori\n" + "su tutti i dadi blu";
                break;
            }case GREEN:{
                this.title= "Sfumature Verdi • Privata" ;
                this.comment = "Somma dei valori\n" + "su tutti i dadi verdi";
                break;
            }case VIOLET:{
                this.title= "Sfumature Viola • Privata" ;
                this.comment = "Somma dei valori\n" + "su tutti i dadi viola";
                break;
            }case YELLOW:{
                this.title= "Sfumature Gialle • Privata" ;
                this.comment = "Somma dei valori\n" + "su tutti i dadi gialli";
                break;
            }default: break;
        }
        this.color = color;
    }

    /**
     * Simply getter method
     * @param select it is the selected color
     * @return the name of the private card
     */
    public static String getPVCard(Color select) {
        switch(select){
            case RED:{
                return "/Private1.png";
            }
            case YELLOW:{
                return "/Private2.png";
            }
            case GREEN:{
                return "/Private3.png";
            }
            case BLUE:{
                return "/Private4.png";
            }
            case VIOLET:{
                return "/Private5.png";
            }
            default:{
                return null;
            }
        }
    }

    /**
     * Simply getter method
     * @return the title of the private card
     */
    public String getTitle() {
        return title;
    }

    /**
     * Simply getter method
     * @return the comment of the private card
     */
    public String getComment() {
        return comment;
    }

    /**
     * Simply getter method
     * @return the color of the private card
     */
    public Color getColor() {
        return color;
    }
}
