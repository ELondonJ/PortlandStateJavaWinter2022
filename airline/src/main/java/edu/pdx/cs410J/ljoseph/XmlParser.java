package edu.pdx.cs410J.ljoseph;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.swing.plaf.IconUIResource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class XmlParser implements AirlineParser<Airline> {

    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;
    private AirlineXmlHelper helper;
    private File file;
    private Airline airline;
    private String[] flightArgs;

    public XmlParser(String filename) throws ParserConfigurationException {
        helper = new AirlineXmlHelper();
        factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        builder = factory.newDocumentBuilder();
        builder.setEntityResolver(helper);
        builder.setErrorHandler(helper);
        if(filename == null)
            throw new IllegalArgumentException("File is null");
        this.file = new File(filename);
        flightArgs = new String[9];
    }
    private String[] dateTimeParse(Node flightNode) throws ParserException {
        String day = null;
        String month = null;
        String year = null;
        String hr = null;
        String min = null;
        NodeList children = flightNode.getChildNodes();
        for(int i = 0; i < children.getLength(); i++ ){
            Node child = children.item(i);
            switch(child.getNodeName()){
                case "date":
                    NamedNodeMap attr = child.getAttributes();
                    for(int j = 0; j < attr.getLength(); j++){
                        Node a = attr.item(j);
                        switch(a.getNodeName()){
                            case "day":
                                day = a.getNodeValue();
                                continue;
                            case "month":
                                month = a.getNodeValue();
                                continue;
                            case "year":
                                year = a.getNodeValue();
                        }
                    }
                    continue;
                case "time":
                    NamedNodeMap timeAtt = child.getAttributes();
                    for(int j = 0; j < timeAtt.getLength(); j++) {
                        Node a = timeAtt.item(j);
                        switch (a.getNodeName()) {
                            case "hour":
                                hr = a.getNodeValue();
                                continue;
                            case "minute":
                                min = a.getNodeValue();
                        }
                    }
            }
        }
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day),Integer.parseInt(hr),
                Integer.parseInt(min));

        DateFormat outForm = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.getDefault());
        String dateString = outForm.format(cal.getTime());
        String[] splitLine = dateString.split("\\s+");  //Splits the date string into date, time, am/pm
        if(splitLine.length != 3)
            throw new ParserException("Incorrect file format while parsing airline text" );
        return new String[] {splitLine[0],splitLine[1],splitLine[2]};


    }
    private void createFlight(NodeList flightInfo) throws ParserException {
        int flightNumber = -1;
        String src = null;
        String dest = null;
        String[] departStr = null;
        String[] arriveStr = null;

        for(int j = 0; j < flightInfo.getLength(); j++){
            Node f = flightInfo.item(j);
            switch (f.getNodeName()){
                case "number":
                    flightNumber = Integer.parseInt(f.getFirstChild().getTextContent());
                    break;
                case "src" :
                    src = f.getFirstChild().getTextContent();
                    break;
                case "dest" :
                    dest = f.getFirstChild().getTextContent();
                    break;
                case "depart" :
                    departStr = dateTimeParse(f);
                    break;
                case "arrive" :
                    arriveStr = dateTimeParse(f);
                    break;
            }
        }
        try {
            Flight flight = new Flight(flightNumber, src, departStr[0], departStr[1], departStr[2], dest, arriveStr[0],
                    arriveStr[1], arriveStr[2]);
            airline.addFlight(flight);
            System.out.println(flight.toString());
        }catch (IllegalArgumentException | NullPointerException e) {
            throw new ParserException("While parsing " + file.getName() + ": " + e.getMessage());
        }
    }
    @Override
    public Airline parse() throws ParserException {
        try {
            InputStream input = new FileInputStream(file);
            Document doc = builder.parse(input);
            Node airline = doc.getElementsByTagName("airline").item(0);
            Element x = (Element)airline;
            NodeList child = x.getChildNodes();
            for(int i = 0; i < child.getLength(); i++){
                Node n = child.item(i);
                if(n.getNodeType()==Node.ELEMENT_NODE)
                    if(n.getNodeName().equals("name")){
                        String nname = n.getFirstChild().getTextContent();
                        this.airline = new Airline(nname);
                    }else if (n.getNodeName().equals("flight")){
                        NodeList flightInfo = n.getChildNodes();
                        createFlight(flightInfo);
                    }
            }
        } catch (SAXException e) {
            e.printStackTrace();
            throw new RuntimeException("Error occurred while parsing " + file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error occurred while parsing " + file);
        }
        return null;
    }
}
