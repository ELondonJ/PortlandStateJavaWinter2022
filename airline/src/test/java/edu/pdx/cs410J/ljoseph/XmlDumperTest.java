package edu.pdx.cs410J.ljoseph;

import org.junit.jupiter.api.Test;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class XmlDumperTest {
    private Airline testAirline = new Airline("Test International");
    private Flight testFlight = new Flight(1234, "pdx", "12/12/2020",
            "12:12","am", "slc", "12/12/2020", "12:12", "am");

    private Airline addFlightToTestAirline(int flightsToEnter) {
        for(int i = 0; i < flightsToEnter; i++)
            this.testAirline.addFlight(testFlight);
        return this.testAirline;


    }

    @Test
    void writeAirlineNameToXml() throws ParserConfigurationException, IOException {
        addFlightToTestAirline(3);
        try {
            XmlDumper dumper = new XmlDumper("newTest.xml");

            dumper.dump(testAirline);
        }catch(ParserConfigurationException | IOException ex){
            System.out.println(ex);
        }
    }



}