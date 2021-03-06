package edu.pdx.cs410J.ljoseph;

import com.google.common.annotations.VisibleForTesting;
import org.apache.groovy.json.internal.IO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>Airline</code>.
 */
public class AirlineServlet extends HttpServlet {
    public static final String SRC_PARAMETER = "src";
    public static final String DEST_PARAMETER = "dest";
    public static final String DEPARTURE_PARAMETER = "departure";
    public static final String ARRIVAL_PARAMETER = "arrival";
    static final String AIRLINE_NAME_PARAMETER = "airline";
    static final String FLIGHT_NUMBER_PARAMETER = "flightNumber";

    private final Map<String, Airline> airlines = new HashMap<>();

    /**
     * Handles an HTTP GET request from a client by writing the contents of the
     * airline specified in the "airline" HTTP parameter to the HTTP response. Or
     * writes the contents of a temp airline with specified src and dest codes if
     * src and dest parameters are present
     */
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws IOException
    {
        response.setContentType( "text/plain" );

        String airlineName = getParameter( AIRLINE_NAME_PARAMETER, request );
        String src = getParameter(SRC_PARAMETER , request );
        String dest = getParameter(DEST_PARAMETER, request );
        if (airlineName != null) {
            if(src == null && dest == null) {
                try {
                    dumpAirline(airlineName, response);
                } catch (ParserConfigurationException e) {
                    response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, e.getMessage());
                }
            } else if(src != null && dest != null){
                Airline temp = findFlights(airlineName, src, dest);
                try {
                    dumpAirline(temp, response);
                } catch (ParserConfigurationException e) {
                    response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, e.getMessage());
                    return;
                }

            }else if(src == null)
                missingRequiredParameter(response,SRC_PARAMETER);
            else
                missingRequiredParameter(response,DEST_PARAMETER);


        } else {
            missingRequiredParameter(response,AIRLINE_NAME_PARAMETER);
        }
    }

    /**
     * Populates an Airline with the airlineName and all flights that have the
     * required src and dest airport codes
     */
    private Airline findFlights(String airlineName, String src, String dest) {
        Airline airline = this.airlines.get(airlineName);
        if(airline == null)
            return null;
        Airline temp = new Airline(airlineName);
        for(Flight flight: airline.getFlights()){
            if(flight.getSource().equals(src) && flight.getDestination().equals(dest)){
                temp.addFlight(flight);
            }
        }
        return temp;
    }

    /**
     * Handles an HTTP POST request by storing the airline entry for the
     * "Airline" and flight request parameters.  It writes the airline
     * entry to the HTTP response.
     */
    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws IOException
    {
        response.setContentType( "text/plain" );

        String airlineName = getParameter(AIRLINE_NAME_PARAMETER, request );
        if (airlineName == null) {
            missingRequiredParameter(response, AIRLINE_NAME_PARAMETER);
            return;
        }

        String flightNumber = getParameter(FLIGHT_NUMBER_PARAMETER, request );
        if ( flightNumber == null) {
            missingRequiredParameter( response, FLIGHT_NUMBER_PARAMETER);
            return;
        }
        String src = getParameter(SRC_PARAMETER, request );
        if (src == null) {
            missingRequiredParameter(response, SRC_PARAMETER);
            return;
        }
        String dest = getParameter(DEST_PARAMETER, request );
        if (dest == null) {
            missingRequiredParameter(response, DEST_PARAMETER);
            return;
        }
        String departure = getParameter(DEPARTURE_PARAMETER, request );
        if (departure == null) {
            missingRequiredParameter(response, DEPARTURE_PARAMETER);
            return;
        }
        String arrival = getParameter(ARRIVAL_PARAMETER, request );
        if (arrival == null) {
            missingRequiredParameter(response, ARRIVAL_PARAMETER);
            return;
        }
        String[] departureArgs = departure.split(" ");
        String[] arrivalArgs = arrival.split(" ");
        if(arrivalArgs.length != 3 || departureArgs.length != 3){
            response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED,"Incorrect Date Format");
        }
        Airline airline = getOrCreateAirline(airlineName);
        try {
            airline.addFlight(new Flight(Integer.parseInt(flightNumber), src, departureArgs[0], departureArgs[1], departureArgs[2],
                    dest, arrivalArgs[0], arrivalArgs[1], arrivalArgs[2]));
        }catch (IllegalArgumentException e){
            if(e instanceof NumberFormatException)
                response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED,"Incorrect flight number " + e.getMessage());
            else
                response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED,e.getMessage());
            return;
        }

        this.airlines.put(airlineName, airline);

        // PrintWriter pw = response.getWriter();
        // pw.println(Messages.definedWordAs(word, definition));
        // pw.flush();

        response.setStatus( HttpServletResponse.SC_OK);
    }

    /**
     * Handles an HTTP DELETE request by removing all Airline entries.  This
     * behavior is exposed for testing purposes only.  It's probably not
     * something that you'd want a real application to expose.
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");

        this.airlines.clear();

        PrintWriter pw = response.getWriter();
        pw.println(Messages.allAirlineEntriesDeleted());
        pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);

    }

    /**
     * Writes an error message about a missing parameter to the HTTP response.
     *
     * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
     */
    private void missingRequiredParameter( HttpServletResponse response, String parameterName )
            throws IOException
    {
        String message = Messages.missingRequiredParameter(parameterName);
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }
    /**
     * Writes the contents of the given airline to the HTTP response.
     * This overloaded dumpAirline takes a temp airline containing only flights with
     * specific src and dest airport codes
     * The text of the message is formatted with {@link XmlDumper}
     */
    private void dumpAirline(Airline airline, HttpServletResponse response) throws IOException, ParserConfigurationException {

        if (airline == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);

        } else {
            PrintWriter pw = response.getWriter();
            XmlDumper dumper = new XmlDumper(pw);
            dumper.dump(airline);
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    /**
     * Writes the contents of the given airline to the HTTP response.
     *
     * The text of the message is formatted with {@link XmlDumper}
     */
    private void dumpAirline(String airlineName, HttpServletResponse response) throws IOException, ParserConfigurationException {
        Airline airline = this.airlines.get(airlineName);

        if (airline == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);

        } else {
            PrintWriter pw = response.getWriter();
            XmlDumper dumper = new XmlDumper(pw);
            dumper.dump(airline);
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    /**
     * Returns the value of the HTTP request parameter with the given name.
     *
     * @return <code>null</code> if the value of the parameter is
     *         <code>null</code> or is the empty string
     */
    private String getParameter(String name, HttpServletRequest request) {
        String value = request.getParameter(name);
        if (value == null || "".equals(value)) {
            return null;

        } else {
            return value.toUpperCase();
        }
    }

    @VisibleForTesting
    Airline getOrCreateAirline(String airlineName) {
        if(this.airlines.get(airlineName) == null){
            Airline airline = new Airline(airlineName);
            airlines.put(airlineName,airline);
            return airline;
        }
        return this.airlines.get(airlineName);
    }
}