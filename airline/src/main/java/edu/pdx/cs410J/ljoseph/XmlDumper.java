package edu.pdx.cs410J.ljoseph;

import edu.pdx.cs410J.AirlineDumper;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

public class XmlDumper implements AirlineDumper<Airline> {

    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;
    private AirlineXmlHelper helper;

    public XmlDumper() throws ParserConfigurationException {
        helper = new AirlineXmlHelper();
        factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        builder = factory.newDocumentBuilder();
        builder.setEntityResolver(helper);
        builder.setErrorHandler(helper);

    }

    @Override
    public void dump(Airline airline) throws IOException {
        Document doc = builder.newDocument();
        Element root = doc.createElement("Airline");
        Element name = doc.createElement("name");
        Text airlineName = doc.createTextNode(airline.getName());
        name.appendChild(airlineName);
        root.appendChild(name);
        Collection<Flight> flights = airline.getFlights();
        for(Flight flight : flights){
            Element aflight = doc.createElement("flight");
            Element number = doc.createElement("number");
            Text num = doc.createTextNode(String.valueOf(flight.getNumber()));
            root.appendChild(aflight);
            aflight.appendChild(number);
            number.appendChild(num);
            Element source = doc.createElement("src");
            Text src = doc.createTextNode(flight.getSource());
            aflight.appendChild(source);
            source.appendChild(src);
            Calendar cal = Calendar.getInstance();
            cal.setTime(flight.getDepart());
            Element departure = doc.createElement("depart");
            Element date = doc.createElement("date");
            date.setAttribute("day", String.valueOf(cal.get(Calendar.DATE)));
            date.setAttribute("month", String.valueOf(cal.get(Calendar.MONTH)));
            date.setAttribute("year", String.valueOf(cal.get(Calendar.YEAR)));
            aflight.appendChild(departure);
            departure.appendChild(date);
            Element time = doc.createElement("time");
            time.setAttribute("hour", String.valueOf(cal.get(Calendar.HOUR_OF_DAY)));
            time.setAttribute("minute",String.valueOf(cal.get(Calendar.MINUTE)));
            departure.appendChild(time);
            Element destination = doc.createElement("dest");
            Text dest = doc.createTextNode(flight.getDestination());
            aflight.appendChild(destination);
            destination.appendChild(dest);
            cal.setTime(flight.getArrive());
            Element arrival = doc.createElement("arrive");
            Element aDate = doc.createElement("date");
            aDate.setAttribute("day", String.valueOf(cal.get(Calendar.DATE)));
            aDate.setAttribute("month", String.valueOf(cal.get(Calendar.MONTH)));
            aDate.setAttribute("year", String.valueOf(cal.get(Calendar.YEAR)));
            aflight.appendChild(arrival);
            arrival.appendChild(aDate);
            Element aTime = doc.createElement("time");
            aTime.setAttribute("hour", String.valueOf(cal.get(Calendar.HOUR_OF_DAY)));
            aTime.setAttribute("minute",String.valueOf(cal.get(Calendar.MINUTE)));
            arrival.appendChild(aTime);


        }
        doc.appendChild(root);
        try {FileOutputStream output =
                     new FileOutputStream("text.xml");
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
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
