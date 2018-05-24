package ingsw.model.windowpattern;

import ingsw.model.Cell;
import ingsw.model.Color;
import ingsw.model.Coordinate;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**Author : Alessio Tonelli
 *
 * CLASS PARSES XML FILE
 *
 * there are two methods
 *
 * readConfig  parses all the cells belonging to the window pattern
 *
 * readInfo  parses name and difficulty belonging to the window pattern
 *
 * */

public class SAXParser {

    private static final String NAME ="name";
    private static final String DIFF = "difficulty";
    private static final String COLUMN = "column";
    private static final String CELL = "cell";
    private static final String COLOR = "color";
    private static final String ROW = "row";
    private static final String NUMBER = "num";


    /**THIS METHOD EXTRACTS ELEMENT FROM EACH CELL OF THE WINDOW*/
    @SuppressWarnings({ "unchecked", "null" })
    public List<Cell> readConfig(String configFile){
        List<Cell> cells = new ArrayList<Cell>();

        try {
            // First, create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            // Setup a new eventReader
            InputStream in = new FileInputStream(configFile);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            // read the XML document
            Cell cell = null;
            int column=0;
            int row=0;
            int number=0;
            Color color =null;

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                // create first cell when cell is read from XML file
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    if (startElement.getName().getLocalPart().equals(CELL)) {

                    }


                    // row is the first element of each node
                    if (event.isStartElement()) {
                        if (event.asStartElement().getName().getLocalPart().equals(ROW)) {
                            event = eventReader.nextEvent();
                            row = Integer.parseInt(event.asCharacters().getData());
                            continue;
                        }
                    }

                    if (event.asStartElement().getName().getLocalPart().equals(COLUMN)) {
                        event = eventReader.nextEvent();
                        column = Integer.parseInt(event.asCharacters().getData());
                        continue;
                    }

                    if (event.asStartElement().getName().getLocalPart()
                            .equals(NUMBER)) {
                        event = eventReader.nextEvent();
                        number=Integer.parseInt(event.asCharacters().getData());
                        continue;
                    }

                    if (event.asStartElement().getName().getLocalPart()
                            .equals(COLOR)) {
                        event = eventReader.nextEvent();
                        color= Color.valueOf(event.asCharacters().getData());
                        continue;
                    }
                }
                // If we reach the end of a cell element, we add it to the list
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals(CELL)) {
                        cell = new Cell(number, color, new Coordinate(row, column));
                        cells.add(cell);
                    }
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return cells;
    }

    /** THIS METHOD EXTTRACTS INFORMATION SUCH AS NAME AND DIFFICULTY*/
    public InfoWindow readInfo(String configFile) {
        InfoWindow infoWindow = new InfoWindow();
        try {
            // First, create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            // Setup a new eventReader
            InputStream in = new FileInputStream(configFile);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    if (startElement.getName().getLocalPart().equals(NAME)) {
                        event = eventReader.nextEvent();
                        infoWindow.setName(event.asCharacters().getData());
                        continue;
                    }


                    if (event.asStartElement().getName().getLocalPart().equals(DIFF)) {
                        event = eventReader.nextEvent();
                        infoWindow.setDifficulty(event.asCharacters().getData());
                        continue;
                    }


                }
                // If we reach the end of an item element, we add it to the list
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals(DIFF)) {
                        //nothing
                    }
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return infoWindow;

    }
}
