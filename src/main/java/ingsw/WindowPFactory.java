package ingsw;


//FACTORY CLASS
//COSTRUISCE LE WINDOW PATTERN IN BASE AL PARAMETRO CHE RICEVE IN INGRESSO


public class WindowPFactory {

    public WindowPattern createWindowPattern(String WpType){

        if(WpType==null)
            return null;
        else if (WpType.equalsIgnoreCase("wp1"))
            return new WP1();
        else if(WpType.equalsIgnoreCase("wp2"))
            return new WP2();
        else if(WpType.equalsIgnoreCase("wp3"))
            return new WP3();
        else if(WpType.equalsIgnoreCase("wp4"))
            return new WP4();
        else if(WpType.equalsIgnoreCase("wp5"))
            return new WP5();
        else if(WpType.equalsIgnoreCase("wp6"))
            return new WP6();
        else if(WpType.equalsIgnoreCase("wp7"))
            return new WP7();
        else if(WpType.equalsIgnoreCase("wp8"))
            return new WP8();
        else if(WpType.equalsIgnoreCase("wp9"))
            return new WP9();
        else if(WpType.equalsIgnoreCase("wp10"))
            return new WP10();
        else if(WpType.equalsIgnoreCase("wp11"))
            return new WP11();
        else if(WpType.equalsIgnoreCase("wp12"))
            return new WP12();
        else if(WpType.equalsIgnoreCase("wp13"))
            return new WP13();
        else if(WpType.equalsIgnoreCase("wp14"))
            return new WP14();
        else if(WpType.equalsIgnoreCase("wp15"))
            return new WP15();
        else if(WpType.equalsIgnoreCase("wp16"))
            return new WP16();
        else if(WpType.equalsIgnoreCase("wp17"))
            return new WP17();
        else if(WpType.equalsIgnoreCase("wp18"))
            return new WP18();
        else if(WpType.equalsIgnoreCase("wp19"))
            return new WP19();
        else if(WpType.equalsIgnoreCase("wp20"))
            return new WP20();
        else if(WpType.equalsIgnoreCase("wp21"))
            return new WP21();
        else if(WpType.equalsIgnoreCase("wp22"))
            return new WP22();
        else if(WpType.equalsIgnoreCase("wp23"))
            return new WP23();
        else if(WpType.equalsIgnoreCase("wp24"))
            return new WP24();

    }
}
