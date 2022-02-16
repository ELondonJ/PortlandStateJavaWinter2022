package edu.pdx.cs410J.ljoseph;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class XmlParserTest {
    private Airline testAirline = new Airline("Test International");
    private Flight testFlight = new Flight(1234, "pdx", "12/12/2020",
            "12:12","am", "slc", "12/12/2020", "12:12", "am");
    private Airline addFlightToTestAirline(int flightsToEnter) {
        for (int i = 0; i < flightsToEnter; i++)
            this.testAirline.addFlight(testFlight);
        return this.testAirline;
    }
    @Test
    void canParseValidXmlFile() throws ParserConfigurationException, IOException, SAXException, ParserException {
        try {
            Airline airline = addFlightToTestAirline(2) ;
            XmlDumper dumper = new XmlDumper("newTest.xml");

            dumper.dump(testAirline);
            File atest = new File("newTest.xml");
            XmlParser p = new XmlParser(atest.getName());
            Airline test = p.parse();
            assertTrue(airline.getName().equals(test.getName()));
            atest.delete();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }


    }

}