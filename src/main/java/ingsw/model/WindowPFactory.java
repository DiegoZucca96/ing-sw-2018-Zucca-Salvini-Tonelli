package ingsw.model;


/**Author : Alessio Tonelli
 *
 *
 * FACTORY CLASS
 *
 * built wp depending by incoming parameter*/


import ingsw.model.windowpattern.*;

import java.util.ArrayList;
import java.util.List;

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

    public WindowPattern createWindowPattern(int wpType){

        //INITIALIZE THE PARSER
        SAXParser reader = new SAXParser();

        if(wpType==0 || wpType>numOfWPs)
            return null;
        else {
           return new WindowPattern(reader, wpType);
        }
    }


    public void addNewWP(String pathname){

        int firstFreeIndex = this.getNumOfWPs();

        wpList.add(firstFreeIndex, pathname);

        this.numOfWPs = wpList.size();
    }
}
