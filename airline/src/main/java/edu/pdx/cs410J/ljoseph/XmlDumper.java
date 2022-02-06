package edu.pdx.cs410J.ljoseph;

import edu.pdx.cs410J.AirlineDumper;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 * XmlDumper creates xml file representation of an Airline object
 */
public class XmlDumper implements AirlineDumper<Airline> {
    public static final String AIRLINE_DTD = "http://www.cs.pdx.edu/~whitlock/dtds/airline.dtd";

    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;
    private AirlineXmlHelper helper;
    private String file;
    private Airline airline;

    /**
     * Creates an XmlDumper object with a String filename
     * @param filename name given for the new xml document
     * @throws ParserConfigurationException if newDocumentBuilder fails
     */
    public XmlDumper(String filename) throws ParserConfigurationException {
        helper = new AirlineXmlHelper();
        factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        builder = factory.newDocumentBuilder();
        builder.setEntityResolver(helper);
        builder.setErrorHandler(helper);
        if(filename == null)
            throw new IllegalArgumentException("File is null");
        this.file = filename;

    }

    /**
     * Adds arrival/departure date information to the document tree
     * @param date date Element created in dump method
     * @param aDate Date object from the current flights getArrive/getDepart methods
     * @param parent parent Element that date Element appends to arrival/departure
     * @param doc  Document object being built
     * @param flight flight Element that parent is appended to
     */
    private void setDate(Element date, Date aDate, Element parent, Document doc, Element flight){
        Calendar cal = Calendar.getInstance();
        cal.setTime(aDate);
        date.setAttribute("day", String.valueOf(cal.get(Calendar.DATE)));
        date.setAttribute("month", String.valueOf(cal.get(Calendar.MONTH)));
        date.setAttribute("year", String.valueOf(cal.get(Calendar.YEAR)));
        flight.appendChild(parent);
        parent.appendChild(date);
        Element time = doc.createElement("time");
        time.setAttribute("hour", String.valueOf(cal.get(Calendar.HOUR_OF_DAY)));
        time.setAttribute("minute",String.valueOf(cal.get(Calendar.MINUTE)));
        parent.appendChild(time);
    }
    /**
     * Dump primary method that writes an Airline object to xml file
     * @param airline Airline object to be written to xml file
     * @throws IOException if FileOutputStream fails
     */
    @Override
    public void dump(Airline airline) throws IOException {
        Document doc = builder.newDocument();
        Element root = doc.createElement("airline");
        Element name = doc.createElement("name");
        Text airlineName = doc.createTextNode(airline.getName());
        name.appendChild(airlineName);
        root.appendChild(name);
        Collection<Flight> flights = airline.getFlights();
        for(Flight flight : flights){
            //add current flight to doc tree
            Element aflight = doc.createElement("flight");
            root.appendChild(aflight);
            //add flight number to doc tree
            Element number = doc.createElement("number");
            Text num = doc.createTextNode(String.valueOf(flight.getNumber()));
            aflight.appendChild(number);
            number.appendChild(num);
           //add source code to doc tree
            Element source = doc.createElement("src");
            Text src = doc.createTextNode(flight.getSource());
            aflight.appendChild(source);
            source.appendChild(src);
            //add departure date to doc tree
            Element departure = doc.createElement("depart");
            Element date = doc.createElement("date");
            setDate(date,flight.getDepart(),departure,doc,aflight);
            //add destination code to doc tree
            Element destination = doc.createElement("dest");
            Text dest = doc.createTextNode(flight.getDestination());
            aflight.appendChild(destination);
            destination.appendChild(dest);
            //add arrival date to doc tree
            Element arrival = doc.createElement("arrive");
            Element aDate = doc.createElement("date");
            setDate(aDate,flight.getArrive(),arrival,doc,aflight);
        }
        doc.appendChild(root);
        try {FileOutputStream output =
                     new FileOutputStream(file);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,AIRLINE_DTD);
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(output);
            transformer.transform(source, result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
