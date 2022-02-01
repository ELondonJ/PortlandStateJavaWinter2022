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
    int c = 0x2554;
    int b = 0x2551;
    int d = 0x2560;
    int f = 0x2502;
    int g = 0x2550;
    int h = 0x2557;
    int j = 0x2563;
    int k = 0x255A;
    int l = 0x255D;

    public PrettyPrinter(Writer writer) {
        this.writer = writer;
    }
    public void heading(String airlineName, PrintWriter pw){
        Formatter fmt = new Formatter();
        Formatter fmt2 = new Formatter();
        pw.println();
        String s = Character.toString((char)c);
        pw.print(s);
        longLine(pw);
        pw.print((char)h);
        pw.println();
        pw.println(fmt2.format("%-35s%-72s%12s",(char)b, airlineName.toUpperCase(),(char)b));
        pw.print((char)d);
        longLine(pw);
        pw.print((char)j);
        pw.println();
        pw.print(fmt.format("%s%-13s%-6s%-18s%-21s%-7s%-19s%-21s%-11s%s", (char)b, " FlightNumber ",
                        (char)f+ " SRC ", (char)f + " Departure City", (char)f + " Departure", (char)f + " DEST",
                        (char)f + " Arrival City",(char)f + " Arrival", (char)f + " Duration", (char)b));
        pw.println();
        pw.print((char)d);
        longLine(pw);
        pw.print((char)j);
        pw.println();
        fmt.flush();

    }
    public void footing(PrintWriter pw) {
        pw.print((char)k);
        longLine(pw);
        pw.println((char)l);
    }

    private void longLine(PrintWriter pw) {
        for(int i = 0; i < 117; i++)
            pw.print((char)g);
    }

    @Override
    public void dump(Airline airline) throws IOException {
        try (
                PrintWriter pw = new PrintWriter(this.writer)
        ) {
            heading(airline.getName(), pw);

            Collection<Flight> flights = airline.getFlights();
            for(Flight flight: flights){
                Formatter fmt = new Formatter();
                String dCity = AirportNames.getName(flight.getSource());
                String aCity = AirportNames.getName(flight.getDestination());
                if(dCity.length() > 15)
                    dCity = dCity.substring(0,15);
                if(aCity.length() > 15)
                    aCity = aCity.substring(0,15);
                pw.println(fmt.format("%s%-15s%-6s%-18s%-21s%-7s%-19s%-21s%-9s%s", (char)b + " ", flight.getNumber(),
                        flight.getSource(),
                        dCity, flight.getDepartureStringPretty(), flight.getDestination(),
                        aCity,
                        flight.getArrivalStringPretty(), flight.getFlightDuration(), (char)b));

            }

            footing(pw);
            pw.flush();
        }

    }
}
