package edu.pdx.cs410J.ljoseph;

import edu.pdx.cs410J.AbstractAirline;

import java.util.ArrayList;
import java.util.Collection;

public class Airline extends AbstractAirline<Flight> {
  private final String name;
  private Collection<Flight> flights;

  public Airline(String name) {
    this.name = name;
    flights = new ArrayList<>();
  }


  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void addFlight(Flight flight) {
    flights.add(flight);
  }

  @Override
  public Collection<Flight> getFlights() {
    return flights;
  }
}
