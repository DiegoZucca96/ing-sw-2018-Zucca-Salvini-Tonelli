package ingsw.model;


/**Author : Alessio Tonelli
 *
 *
 * FACTORY CLASS
 *
 * built wp depending by incoming parameter*/


import ingsw.model.windowpattern.*;

import java.util.ArrayList;

public class WindowPFactory {

    private int numOfWPs;
    private ArrayList<String> wpList;

    public int getNumOfWPs() {
        return numOfWPs;
    }

    public WindowPFactory(){
        this.numOfWPs=24;
        wpList= new ArrayList<>();
        for(int i=0;i<=numOfWPs; i++){
            String index=Integer.toString(i+1);
            wpList.add(i, "wp"+index+".xml" );
        }
    }

    public WindowPattern createWindowPattern(int WpType){

        if(WpType==0 || WpType>numOfWPs)
            return null;
        else if (WpType==1)
            return new WP1();
        else if(WpType==2)
            return new WP2();
        else if(WpType==3)
            return new WP3();
        else if(WpType==4)
            return new WP4();
        else if(WpType==5)
            return new WP5();
        else if(WpType==6)
            return new WP6();
        else if(WpType==7)
            return new WP7();
        else if(WpType==8)
            return new WP8();
        else if(WpType==9)
            return new WP9();
        else if(WpType==10)
           return new WP10();
        else if(WpType==11)
            return new WP11();
        else if(WpType==12)
            return new WP12();
        else if(WpType==13)
            return new WP13();
        else if(WpType==14)
            return new WP14();
        else if(WpType==15)
            return new WP15();
        else if(WpType==16)
            return new WP16();
        else if(WpType==17)
            return new WP17();
        else if(WpType==18)
            return new WP18();
        else if(WpType==19)
            return new WP19();
        else if(WpType==20)
            return new WP20();
        else if(WpType==21)
            return new WP21();
        else if(WpType==22)
            return new WP22();
        else if(WpType==23)
            return new WP23();
        else if(WpType==24)
            return new WP24();
        return null;
    }


    public void addNewWP(String pathname){

        int firstFreeIndex = this.getNumOfWPs();

        wpList.add(firstFreeIndex, pathname);

        this.numOfWPs = wpList.size();
    }
}
