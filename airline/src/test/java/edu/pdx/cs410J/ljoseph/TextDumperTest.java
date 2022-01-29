package edu.pdx.cs410J.ljoseph;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextDumperTest {

  private Airline testAirline = new Airline("Test International");
  private Flight testFlight = new Flight(1234, "pdx", "12/12/1212",
          "12:12","am", "pdx", "12/12/1212", "12:12", "am");

  private Airline addFlightToTestAirline(int flightsToEnter) {
    for(int i = 0; i < flightsToEnter; i++)
      this.testAirline.addFlight(testFlight);
    return this.testAirline;


  }

  @Test
  void airlineNameIsDumpedInTextFormat() {
    String airlineName = "Test Airline";
    Airline airline = new Airline(airlineName);

    StringWriter sw = new StringWriter();
    TextDumper dumper = new TextDumper(sw);
    dumper.dump(airline);

    String text = sw.toString();
    assertThat(text, containsString(airlineName));
  }

  @Test
  void canParseTextWrittenByTextDumper(@TempDir File tempDir) throws IOException, ParserException {
    String airlineName = "Test Airline";
    Airline airline = new Airline(airlineName);

    File textFile = new File(tempDir, "airline.txt");
    TextDumper dumper = new TextDumper(new FileWriter(textFile));
    dumper.dump(airline);

    TextParser parser = new TextParser(new FileReader(textFile));
    Airline read = parser.parse();
    assertThat(read.getName(), equalTo(airlineName));
  }

  @Test
  void canParseTextWrittenByTextDumperFlightsHaveBeenWritten(@TempDir File tempDir) throws IOException, ParserException {
    Airline airline = addFlightToTestAirline(3);

    File textFile = new File("airline.txt");
    TextDumper dumper = new TextDumper(new FileWriter(textFile));
    dumper.dump(airline);

    TextParser parser = new TextParser(new FileReader(textFile));
    Airline read = parser.parse();
    Collection<Flight> flights = airline.getFlights();
    assertEquals(flights.size(), airline.getFlights().size());
    textFile.deleteOnExit();
  }
}
