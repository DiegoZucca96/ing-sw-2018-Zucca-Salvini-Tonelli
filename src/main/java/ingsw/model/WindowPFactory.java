package ingsw.model;

import ingsw.model.windowpattern.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

/**Author : Alessio Tonelli
 *
 * FACTORY CLASS
 *
 * built wp depending by incoming parameter*/
public class WindowPFactory {

    private int numOfWPs;
    private ArrayList<String> wpList;

    /**
     * Constructor
     * @throws IOException when the path is not correct or when the stream didn't work
     */
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

    /**
     * This method creates windows using a SAXParser reader
     * @param wpType it is the number of the window that has to be generated
     * @return a model window
     * @see SAXParser
     */
    public WindowPattern createWindowPattern(int wpType){

        //INITIALIZE THE PARSER
        SAXParser reader = new SAXParser();

        if(wpType==0 || wpType>numOfWPs)
            return null;
        else {
           return new WindowPattern(reader, wpType);
        }
    }

    /**
     * Simply getter method
     * @return the number of the existing windows
     */
    public int getNumOfWPs() {
        return numOfWPs;
    }
}
