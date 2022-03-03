package edu.pdx.cs410J.ljoseph;

import edu.pdx.cs410J.AirlineDumper;
import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.AirportNames;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;
import jdk.jshell.spi.ExecutionControl;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.rmi.ServerError;
import java.util.Map;

/**
 * The main class that parses the command line and communicates with the
 * Airline server using REST.
 */
public class Project5 {

    public static final String MISSING_ARGS = "Missing command line arguments";
    public static final int LIST_AIRLINE_ARG_LENGTH = 5;

    public static void main(String... args) {

        String hostName = null;
        String portString = null;
        Airline airline = null;
        Flight flight = null;
        String airlineName = null;
        int flightNumber = -1;
        String src = null;
        String depart = null;
        String dtime = null;
        String dAmPm = null;
        String dest = null;
        String arrive = null;
        String atime = null;
        String aAmPm = null;
        boolean print = false;
        boolean search = false;
        boolean listAirline = false;


        //loop parses saves each arg in appropriate variable, sets option flags as needed
        for (int i = 0; i < args.length; i++) {
            if (args[i].charAt(0) == '-') {
                if (args[i].equalsIgnoreCase("-print"))
                    print = true;
                else if (args[i].equalsIgnoreCase("-readme")) {
                    try {
                    InputStream readme = Project5.class.getResourceAsStream("README.txt");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
                    String line = null;
                        line = reader.readLine();
                    do {
                        System.out.println(line);
                        line = reader.readLine();
                    } while (line != null);
                    } catch (IOException | NullPointerException e) {
                        error("While accessing README.txt: " + e);
                    }
                    System.exit(0);
                } else if (args[i].equalsIgnoreCase("-search")) {
                    if (args.length != 8) {
                        usage("Error! Search flag usage: -search <airline name> <source code> <destination code>");
                    }
                    search =true;
                } else if (args[i].equalsIgnoreCase("-host")) {
                    if (++i >= args.length) {
                        System.err.println("Error! A host name must be provided with -host flag");
                        System.exit(1);
                    }
                    hostName = args[i];
                } else if (args[i].equalsIgnoreCase("-port")) {
                    if (++i >= args.length) {
                        System.err.println("Error! A port number must be provided with -port flag");
                        System.exit(1);
                    }
                    portString = args[i];
                } else {
                    System.err.println("Error! Unknown flag ");
                    System.exit(1);
                }
            } else if (airlineName == null) {
                airlineName = args[i];
                if(args.length == LIST_AIRLINE_ARG_LENGTH){
                    listAirline = true;
                }
            } else if (flightNumber == -1 && !search) {
                try {
                    flightNumber = Integer.parseInt(args[i]);
                } catch (NumberFormatException e) {
                    usage(e.getMessage() + " Incorrect flight number format");
                }
            } else if (src == null)
                src = args[i];
            else if (depart == null && !search)
                depart = args[i];
            else if (dtime == null && !search)
                dtime = args[i];
            else if (dAmPm == null && !search)
                dAmPm = args[i];
            else if (dest == null)
                dest = args[i];
            else if (arrive == null && !search)
                arrive = args[i];
            else if (atime == null && !search)
                atime = args[i];
            else if (aAmPm == null && !search)
                aAmPm = args[i];
            else {
                usage("Extraneous command line argument: " + args[i]);
            }
        }
        if((hostName == null && portString != null) || (hostName != null && portString == null)){
            usage("-host flag and port flag must be used together");
        }
        //checks src and dest are formatted correctly and valid airports
        if(search) {
            String[] threeCode = {src, dest};
            for (int i = 0; i < threeCode.length; i++) {
                if (!threeCode[i].matches("^[a-zA-Z]{3}$"))
                    usage("Source and Destination represented by three-letter code. ie pdx;");
                String ValidName = AirportNames.getName(threeCode[i].toUpperCase());
                if (ValidName == null)
                    usage("The code " + threeCode[i] + " does not represent a valid airport");
            }
        }

        if (hostName == null) {
            usage(MISSING_ARGS + ": host ");
            return;
        }
        if ( portString == null) {
            usage(MISSING_ARGS  + ": port" );
            return;
        }

        int port;
        try {
            port = Integer.parseInt( portString );

        } catch (NumberFormatException ex) {
            usage("Port \"" + portString + "\" must be an integer");
            return;
        }

        AirlineRestClient client = new AirlineRestClient(hostName, port);
        PrettyPrinter pPrinter = new PrettyPrinter(new BufferedWriter(new OutputStreamWriter(System.out)));
        try {
            Airline air = null;
            if(search){
                try {
                    air = client.searchAirline(airlineName,src,dest);
                }catch (HttpRequestHelper.RestException e){
                    System.out.println("Airline: " + airlineName + " does not exist on the server");
                    System.exit(0);
                }
                if(air == null){
                    System.out.println(airlineName +" has no direct flights from " + src + " to " + dest);
                }
                else if(air.getFlights().isEmpty()){
                    System.out.println(airlineName +" has no direct flights from " + src + " to " + dest);
                }
                else
                    pPrinter.dump(client.searchAirline(airlineName,src,dest));
            }
            else if (listAirline) {
                try {
                    air = client.getAirline(airlineName);
                }catch (HttpRequestHelper.RestException e){
                    System.out.println("Airline: " + airlineName + " does not exist on the server");
                    System.exit(0);
                }
                if(air == null){
                    System.out.println("The airline: " + airlineName + " does not exist on the server");
                }
                else
                    pPrinter.dump(air);
            } else {
                try {
                    flight = new Flight(flightNumber, src, depart, dtime, dAmPm, dest, arrive, atime, aAmPm);
                    client.addFlight(airlineName, flight);
                    if(print)
                        System.out.println(airlineName + " " + flight.toString().toLowerCase());
                }catch(IllegalArgumentException ex){
                    usage(ex.getMessage());
                }
            }

        } catch (IOException | ParserException | ParserConfigurationException | HttpRequestHelper.RestException ex) {
            error("While contacting server: " +  ex.getMessage());
        }
        System.exit(0);
    }

    private static void error( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);

        System.exit(1);
    }

    /**
     * Prints usage information for this program and exits
     * @param message An error message to print
     */
    private static void usage( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
        try {getUsage();}
        catch (IOException e) {
            System.err.println("While reading usage from readme file: " + e.getMessage());
        }

        System.exit(1);
    }
    /**
     * Print usages from the ReadMe resource file
     * @throws IOException
     */
    private static void getUsage() throws IOException {
        InputStream readme = Project5.class.getResourceAsStream("README.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
        String line = reader.readLine();
        while(!line.contains("usage"))
            line = reader.readLine();
        do {
            System.err.println(line);
            line = reader.readLine();
        } while (line != null);
    }

}