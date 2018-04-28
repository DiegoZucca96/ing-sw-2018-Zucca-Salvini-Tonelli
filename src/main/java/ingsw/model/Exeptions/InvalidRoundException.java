package ingsw.model.Exeptions;

public class InvalidRoundException extends Exception {

    private final String message;

    public InvalidRoundException(String message){
        super(message);
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
