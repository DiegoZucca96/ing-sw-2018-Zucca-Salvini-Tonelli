package ingsw.model;


/**Author : Alessio Tonelli
 *
 *
 * FACTORY CLASS
 *
 * built wp depending by incoming parameter*/


import ingsw.model.windowpattern.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class WindowPFactory {

    private int numOfWPs;
    private ArrayList<String> wpList;

    public int getNumOfWPs() {
        return numOfWPs;
    }

    public WindowPFactory() throws IOException {
        try (Stream<Path> files = Files.list(Paths.get("src/main/java/ingsw/model/windowpattern/wpxml"))) {
            this.numOfWPs = (int) files.count();
        }
        wpList= new ArrayList<>();
        for(int i=0;i<numOfWPs; i++){
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

    //inutile poichè i file vengono aggiunti manualmente nella cartella e non dal programma
    //file vengono solo acceduti
    /*public void addNewWP(String pathname){

        int firstFreeIndex = this.getNumOfWPs();

        wpList.add(firstFreeIndex, pathname);

        this.numOfWPs = wpList.size();
    }*/
}
