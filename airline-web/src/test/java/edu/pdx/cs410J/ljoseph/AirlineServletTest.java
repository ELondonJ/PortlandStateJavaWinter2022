package edu.pdx.cs410J.ljoseph;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link AirlineServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
class AirlineServletTest {

  @Test
  void missingAirlineNameReturnsPreconditionFailedStatus() throws IOException {
    AirlineServlet servlet = new AirlineServlet();

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    PrintWriter pw = mock(PrintWriter.class);

    when(response.getWriter()).thenReturn(pw);

    servlet.doGet(request, response);

    verify(response).sendError(HttpServletResponse.SC_PRECONDITION_FAILED, Messages.missingRequiredParameter(AirlineServlet.AIRLINE_NAME_PARAMETER));
  }

  @Test
  void addOneFlightToAnAirline() throws IOException {
    AirlineServlet servlet = new AirlineServlet();

    String airlineName = "Test Airlines";
    int flightNumber = 123;
    String src = "PDX";
    String dest = "SLC";
    String departure = "12/12/2002 10:00 AM";
    String arrival = "12/12/2002 11:00 AM";


    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(AirlineServlet.AIRLINE_NAME_PARAMETER)).thenReturn(airlineName);
    when(request.getParameter(AirlineServlet.FLIGHT_NUMBER_PARAMETER)).thenReturn(String.valueOf(flightNumber));
    when(request.getParameter(AirlineServlet.SRC_PARAMETER)).thenReturn(src);
    when(request.getParameter(AirlineServlet.DEST_PARAMETER)).thenReturn(dest);
    when(request.getParameter(AirlineServlet.DEPARTURE_PARAMETER)).thenReturn(departure);
    when(request.getParameter(AirlineServlet.ARRIVAL_PARAMETER)).thenReturn(arrival);

    HttpServletResponse response = mock(HttpServletResponse.class);

    servlet.doPost(request, response);

    verify(response).setStatus(eq(HttpServletResponse.SC_OK));

    Airline airline = servlet.getOrCreateAirline(airlineName);
    assertThat(airline, notNullValue());
    Collection<Flight> flights = airline.getFlights();
    assertThat(flights, hasSize(1));
    Flight flight = flights.iterator().next();
    assertThat(flight.getNumber(), equalTo(flightNumber));
  }
  @Test
  void addOneFlightWithBadArgsSendsError() throws IOException {
    AirlineServlet servlet = new AirlineServlet();

    String airlineName = "Test Airlines";
    int flightNumber = 123;
    String src = "PDX";
    String dest = "LLL";
    String departure = "12/12/2002 10:00 AM";
    String arrival = "12/12/2002 11:00 AM";


    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(AirlineServlet.AIRLINE_NAME_PARAMETER)).thenReturn(airlineName);
    when(request.getParameter(AirlineServlet.FLIGHT_NUMBER_PARAMETER)).thenReturn(String.valueOf(flightNumber));
    when(request.getParameter(AirlineServlet.SRC_PARAMETER)).thenReturn(src);
    when(request.getParameter(AirlineServlet.DEST_PARAMETER)).thenReturn(dest);
    when(request.getParameter(AirlineServlet.DEPARTURE_PARAMETER)).thenReturn(departure);
    when(request.getParameter(AirlineServlet.ARRIVAL_PARAMETER)).thenReturn(arrival);

    HttpServletResponse response = mock(HttpServletResponse.class);

    servlet.doPost(request, response);
    verify(response).sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "The code LLL does not represent a valid airport");

//    Airline airline = servlet.getAirline(airlineName);
//    assertThat(airline, notNullValue());
//    Collection<Flight> flights = airline.getFlights();
//    assertThat(flights, hasSize(0));
  }
    @Test
    void returnTextRepresentationOfAirline() throws IOException, ParserException, ParserConfigurationException, ParserException {
      String airlineName = "Test Airlines";
      int flightNumber = 123;
      String src = "PDX";
      String dest = "SLC";
      String departure = "12/12/2002 10:00 AM";
      String arrival = "12/12/2002 11:00 AM";
      String[] dArgs = departure.split(" ");
      String[] aArgs = arrival.split(" ");


      AirlineServlet servlet = new AirlineServlet();
      Airline airline = servlet.getOrCreateAirline(airlineName);
      airline.addFlight(new Flight(flightNumber,src,dArgs[0],dArgs[1],dArgs[2],dest,aArgs[0],aArgs[1],aArgs[2]));

      HttpServletRequest request = mock(HttpServletRequest.class);
      when(request.getParameter(AirlineServlet.AIRLINE_NAME_PARAMETER)).thenReturn(airlineName);

      HttpServletResponse response = mock(HttpServletResponse.class);
      StringWriter sw = new StringWriter();
      when(response.getWriter()).thenReturn(new PrintWriter(sw, true));

      servlet.doGet(request, response);

      verify(response).setStatus(eq(HttpServletResponse.SC_OK));

      String text = sw.toString();
      Airline airline2 = new XmlParser(new StringReader(text)).parse();

      assertThat(airline2.getName(), equalTo(airlineName));
      Collection<Flight> flights = airline2.getFlights();
      assertThat(flights, hasSize(1));
      Flight flight = flights.iterator().next();
      assertThat(flight.getNumber(), equalTo(flightNumber));
    }

}
