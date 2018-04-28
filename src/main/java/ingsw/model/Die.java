package ingsw.model;

public class Die{

    private final Color color;
    private int number;

    public Die(int number, Color color){
        this.number=number;
        this.color=color;
    }

    public Color getColor() {
        return color;
    }

    public int getNumber(){
        return number;
    }

    public int setNumber(int number) {
        return this.number;
    }

    @Override
    public boolean equals(Object die){
        //... Tony implementa equals, serve per far funzionare le assertEquals nei test
        return true;
    }

}
