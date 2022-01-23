package edu.pdx.cs410J.ljoseph;

import edu.pdx.cs410J.AirlineDumper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collection;

public class TextDumper implements AirlineDumper<Airline> {
  private final Writer writer;

  public TextDumper(Writer writer) {
    this.writer = writer;
  }

  @Override
  public void dump(Airline airline) {
    try (
      PrintWriter pw = new PrintWriter(this.writer)
      ) {
      pw.println(airline.getName());
      Collection<Flight> flights = airline.getFlights();
      for(Flight flight: flights){
        pw.print((flight.getNumber()) + " ");
        pw.print(flight.getSource() + " ");
        pw.print(flight.getDepartureString() + " ");
        pw.print(flight.getDestination() + " ");
        pw.println(flight.getArrivalString() + " ");
      }

      pw.flush();
    }
  }
}
