package edu.pdx.cs410J.ljoseph;

import edu.pdx.cs410J.AirlineDumper;
import edu.pdx.cs410J.AirportNames;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Formatter;

public class PrettyPrinter implements AirlineDumper<Airline> {
    Writer writer;
    //box drawing unicode for table format
    int c = 0x2554;
    int b = 0x2551;
    int d = 0x2560;
    int f = 0x2502;
    int g = 0x2550;
    int h = 0x2557;
    int j = 0x2563;
    int k = 0x255A;
    int l = 0x255D;
    int airlineNameSpace = 68;

    /**
     * Creates PrettyPrinter with writer
     * @param writer file writer from calling routine
     */
    public PrettyPrinter(Writer writer) {
        this.writer = writer;
    }

    /**
     * Prints formatted heading w/ airline name and flight attributes
     * @param airlineName name of airline to be written
     * @param pw Print Writer
     */
    public void heading(String airlineName, PrintWriter pw){
        Formatter fmt = new Formatter();
        Formatter fmt2 = new Formatter();
        pw.println();
        pw.print(((char)c));
        longLine(pw);
        pw.println((char)h);
        getCenteredAirlineName(airlineName);
        pw.println(fmt2.format("%-20s%-" +airlineNameSpace+ "s%27s",(char)b, getCenteredAirlineName(airlineName),
                (char)b));
        pw.print((char)d);
        longLine(pw);
        pw.println((char)j);
        pw.println(fmt.format("%s%-10s%-6s%-18s%-21s%-7s%-19s%-21s%-11s%s", (char)b, " Flight# ",
                        (char)f+ " SRC ", (char)f + " Departure City", (char)f + " Departure", (char)f + " DEST",
                        (char)f + " Arrival City",(char)f + " Arrival", (char)f + " Duration", (char)b));
        pw.print((char)d);
        longLine(pw);
        pw.println((char)j);
        fmt.flush();
    }

    private String getCenteredAirlineName(String airlineName) {
        StringBuilder sb = new StringBuilder();
        int spacing = (airlineNameSpace - airlineName.length())/2;
        for(int i = 0; i < spacing; i++)
            sb.append(" ");
        sb.append(airlineName);
        return sb.toString();
    }

    /**
     * Prints the bottom of the table
     * @param pw PrintWriter
     */
    public void footing(PrintWriter pw) {
        pw.print((char)k);
        longLine(pw);
        pw.println((char)l);
    }

    /**
     * Prints long line of unicode used in table building
     * @param pw PrintWriter
     */
    private void longLine(PrintWriter pw) {
        for(int i = 0; i < 113; i++)
            pw.print((char)g);
    }

    /**
     * Writes the contents of an airline out to the file within writer.
     * Airline and flight are written in a table format by using box
     * drawing unicode
     * @param airline Airline to be pretty printed
     * @throws IOException if initialization of PrintWriter fails
     */
    @Override
    public void dump(Airline airline) throws IOException {
        try (
                PrintWriter pw = new PrintWriter(this.writer)
        ) {
            heading(airline.getName().toUpperCase(), pw);

            Collection<Flight> flights = airline.getFlights();
            for(Flight flight: flights){
                Formatter fmt = new Formatter();
                String dCity = AirportNames.getName(flight.getSource());
                String aCity = AirportNames.getName(flight.getDestination());
                //creates substrings of airports with long names to fit in formatted table
                if(dCity.length() > 15)
                    dCity = dCity.substring(0,15);
                if(aCity.length() > 15)
                    aCity = aCity.substring(0,15);
                pw.println(fmt.format("%s%-11s%-6s%-18s%-21s%-7s%-19s%-21s%-9s%s", (char)b + " ", flight.getNumber(),
                        flight.getSource(), dCity, flight.getDepartureStringPretty(), flight.getDestination(),
                        aCity, flight.getArrivalStringPretty(), flight.getFlightDuration(), (char)b));
            }
            footing(pw);
            pw.flush();
        }
    }
}
