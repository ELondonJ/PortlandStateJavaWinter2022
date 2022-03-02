package edu.pdx.cs410J.ljoseph;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send dictionary entries.
 */
public class AirlineRestClient extends HttpRequestHelper
{
    private static final String WEB_APP = "airline";
    private static final String SERVLET = "flights";

    private final String url;


    /**
     * Creates a client to the airline REST service running on the given host and port
     * @param hostName The name of the host
     * @param port The port
     */
    public AirlineRestClient( String hostName, int port )
    {
        this.url = String.format( "http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET );
    }
    /**
     * Returns the Airline with given airline name
     */
    public Airline getAirline(String airlineName) throws IOException, ParserException, ParserConfigurationException {
        Response response = get(this.url, Map.of("airline", airlineName));
        throwExceptionIfNotOkayHttpStatus(response);
        String content = response.getContent();

        XmlParser parser = new XmlParser(new StringReader(content));
        return parser.parse();
    }

    /**
     * Returns an Airline with given airline name contains all flights from src airport to dest airport
     */
    public Airline searchAirline(String airlineName, String src, String dest) throws IOException, ParserException,
            ParserConfigurationException {
        Response response = get(this.url, Map.of("airline", airlineName,"src", src,"dest",dest));
        throwExceptionIfNotOkayHttpStatus(response);
        String content = response.getContent();

        XmlParser parser = new XmlParser(new StringReader(content));
        return parser.parse();
    }
    /**
     * Adds new flight to airline with given name
     */
    public void addFlight(String airlineName, Flight fl) throws IOException {
        Response response = post(this.url, Map.of("airline",airlineName, "flightNumber", String.valueOf(fl.getNumber()),
                "src", fl.getSource(),"departure", fl.getDepartureString(),"dest",fl.getDestination(),
                "arrival", fl.getArrivalString()));
        throwExceptionIfNotOkayHttpStatus(response);
    }

    /**
     * Deletes all airline entries
     */
    public void removeAllAirlineEntries() throws IOException {
        Response response = delete(this.url, Map.of());
        throwExceptionIfNotOkayHttpStatus(response);
    }

    /**
     * Checks if request is successful
     */
    private void throwExceptionIfNotOkayHttpStatus(Response response) {
        int code = response.getCode();
        if (code != HTTP_OK) {
            String message = response.getContent();
            if(message.equals(""))
                message = "Airline does not exist";
            throw new RestException(code, message);
        }
    }

}