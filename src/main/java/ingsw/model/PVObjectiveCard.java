package ingsw.model;

// A partire dal creare la PVCard, passo il parametro randomico per restituire al Player la PVCard corrispondente
public class PVObjectiveCard {

    private String comment;
    private String title;
    private Color color;

    public String getTitle() {
        return title;
    }

    public String getComment() {
        return comment;
    }

    public Color getColor() {
        return color;
    }


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
}
