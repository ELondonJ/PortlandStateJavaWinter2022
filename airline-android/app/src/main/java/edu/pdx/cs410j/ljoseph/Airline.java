package edu.pdx.cs410j.ljoseph;

import edu.pdx.cs410J.AbstractAirline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * This class represents an airline and is and extension of
 * the abstract class AbstractAirline.  Each airline has a name
 * and an array list of <code>Flight</code> objects.
 */
public class Airline extends AbstractAirline<Flight> implements Comparator<Airline> {
  private final String name;
  private ArrayList<Flight> flights;

  /**
   * Creates a new airline
   */
  public Airline(String name) {
    this.name = name.toUpperCase();
    flights = new ArrayList<>();
  }

  /**
   * returns airline name
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * Adds and <code>Flight</code> object ot the flights list
   */
  @Override
  public void addFlight(Flight flight) {
    this.flights.add(flight);
    Collections.sort(flights);
  }

  public int getFlightCount(){
    return flights.size();
  }

  /**
   * returns an array list of <code>Flight</code> objects
   */
  @Override
  public Collection<Flight> getFlights() {
    return flights;
  }

  @Override
  public int compare(Airline airline, Airline t1) {
    return airline.name.compareTo(t1.name);
  }

}

