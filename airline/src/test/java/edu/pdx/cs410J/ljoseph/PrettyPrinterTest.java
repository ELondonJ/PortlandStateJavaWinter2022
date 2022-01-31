package edu.pdx.cs410J.ljoseph;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

class PrettyPrinterTest {

    private Airline testAirline = new Airline("Testssss ");
    private Flight testFlight = new Flight(1234444, "pdx", "12/12/1212",
            "12:12","am", "slc", "12/12/1212", "10:12", "am");

    private Airline addFlightToTestAirline(int flightsToEnter) {
        for (int i = 0; i < flightsToEnter; i++)
            this.testAirline.addFlight(testFlight);
        return this.testAirline;
    }

    @Test
    @Disabled
    void airlineNameIsDumpedInTextFormat() throws IOException {
        String airlineName = "Portland";
        Airline airline = new Airline(airlineName);

        StringWriter sw = new StringWriter();
        PrettyPrinter pretty = new PrettyPrinter(sw);
        pretty.dump(airline);

        String text = sw.toString();
        assertThat(text, containsString(airlineName));
    }

    @Test
    void canParseTextWrittenByPrettyPrinterFlightsHaveBeenWrittenToFIle(@TempDir File tempDir) throws IOException, ParserException {
        Airline airline = addFlightToTestAirline(3);

        File textFile = new File("airline.txt");
        PrettyPrinter pretty = new PrettyPrinter(new FileWriter(textFile));
        pretty.dump(airline);


    }
    @Test
    void canParseTextWrittenByPrettyPrinterFlightsHaveBeenWrittenToSTDout(@TempDir File tempDir) throws IOException, ParserException {
        Airline airline = addFlightToTestAirline(8);

        File textFile = new File("airline.txt");
        PrettyPrinter pretty = new PrettyPrinter(new BufferedWriter(new OutputStreamWriter(System.out)));
        pretty.dump(airline);


    }

}