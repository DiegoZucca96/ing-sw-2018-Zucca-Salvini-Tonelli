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

   /* public PVObjectiveCard createPVCard (int idCard){
        switch (idCard){
            case 1:{
                return new PVObjectiveCard(RED);
                break;
            }
            case 2:{
                return new PVObjectiveCard(YELLOW);
                break;
            }
            case 3:{
                return new PVObjectiveCard(GREEN);
                break;
            }
            case 4:{
                return new PVObjectiveCard(BLUE);
                break;
            }
            case 5:{
                return new PVObjectiveCard(VIOLET);
                break;
            }
            default:{
                System.out.print("Invalid numer, launch exception");
                return null;
                break;
            }
        }
    }*/

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


}
