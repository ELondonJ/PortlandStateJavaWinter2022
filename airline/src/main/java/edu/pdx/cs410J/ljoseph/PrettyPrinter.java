package edu.pdx.cs410J.ljoseph;

import edu.pdx.cs410J.AirlineDumper;

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
        for(int i = 0; i < 80; i++)
            pw.print((char)g);
        pw.print((char)h);
        pw.println();
        pw.println(fmt2.format("%-30s%-40s%12s",(char)b, airlineName.toUpperCase(),(char)b));
        pw.print((char)d);
        for(int i = 0; i < 80; i++)
            pw.print((char)g);
        pw.print((char)j);
        pw.println();
        pw.print(fmt.format("%s%-16s%-10s%-21s%-10s%-23s%-100s", (char)b, " FlightNumber",
                        (char)f+ " SRC ", (char)f + " Departure", (char)f + " DEST", (char)f + " Arrival",(char)b));
        pw.println();
        pw.print((char)d);
        for(int i = 0; i < 80; i++)
            pw.print((char)g);
        pw.print((char)j);
        pw.println();
        fmt.flush();

    }
    public void footing(PrintWriter pw) {
        pw.print((char)k);
        for(int i = 0; i < 80; i++)
            pw.print((char)g);
        pw.println((char)l);
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
                pw.println(fmt.format("%s%-16s%-10s%-21s%-10s%-22s%-100s", (char)b + " ", flight.getNumber(),
                        flight.getSource(), flight.getDepartureStringPretty(), flight.getDestination(),
                        flight.getArrivalStringPretty(), (char)b));

            }

            footing(pw);
            pw.flush();
        }

    }
}
