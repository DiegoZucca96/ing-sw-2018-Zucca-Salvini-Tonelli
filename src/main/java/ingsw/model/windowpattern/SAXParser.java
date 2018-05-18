package ingsw.model.windowpattern;

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

/**CLASS WHICH PARSES XML FILE*/

public class SAXParser {

    static final String NAME ="name";
    static final String DIFF = "difficulty";
    static final String COLUMN = "column";
    static final String CELL = "cell";
    static final String COLOR = "color";
    static final String ROW = "row";
    static final String NUMBER = "num";


    /**THIS METHOD EXTRACTS ELEMENT FROM EACH CELL OF THE WINDOW*/
    @SuppressWarnings({ "unchecked", "null" })
    public List<CellRender> readConfig(String configFile){
        List<CellRender> cells = new ArrayList<CellRender>();
        try {
            // First, create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            // Setup a new eventReader
            InputStream in = new FileInputStream(configFile);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            // read the XML document
            CellRender cellRender = null;

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                // create first cell when cell is read from XML file
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    if (startElement.getName().getLocalPart().equals(CELL)) {
                        cellRender = new CellRender();
                    }


                    // row is the first element of each node
                    if (event.isStartElement()) {
                        if (event.asStartElement().getName().getLocalPart().equals(ROW)) {
                            event = eventReader.nextEvent();
                            cellRender.setRow(event.asCharacters().getData());
                            continue;
                        }
                    }

                    if (event.asStartElement().getName().getLocalPart().equals(COLUMN)) {
                        event = eventReader.nextEvent();
                        cellRender.setColumn(event.asCharacters().getData());
                        continue;
                    }

                    if (event.asStartElement().getName().getLocalPart()
                            .equals(NUMBER)) {
                        event = eventReader.nextEvent();
                        cellRender.setNumber(event.asCharacters().getData());
                        continue;
                    }

                    if (event.asStartElement().getName().getLocalPart()
                            .equals(COLOR)) {
                        event = eventReader.nextEvent();
                        cellRender.setColor(event.asCharacters().getData());
                        continue;
                    }
                }
                // If we reach the end of a cell element, we add it to the list
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals(CELL)) {
                        cells.add(cellRender);
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
    public List<InfoWindow> readInfo(String configFile) {
        List<InfoWindow> info = new ArrayList<>();
        try {
            // First, create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            // Setup a new eventReader
            InputStream in = new FileInputStream(configFile);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            // read the XML document
            InfoWindow infoWindow = new InfoWindow();

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
                        info.add(infoWindow);
                    }
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return info;

    }
}
