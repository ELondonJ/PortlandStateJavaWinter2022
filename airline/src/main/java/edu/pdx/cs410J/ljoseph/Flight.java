package edu.pdx.cs410J.ljoseph;

import edu.pdx.cs410J.AbstractFlight;

public class Flight extends AbstractFlight {

  private String flightNumber;
  private String src;
  private String depart;
  private String dtime;
  private String dest;
  private String arrive;
  private String atime;

  public Flight(String flightNumber, String src, String depart, String dtime, String dest,
                String arrive, String atime) {
    this.flightNumber = flightNumber;
    this.src = src;
    this.depart = depart;
    this.dtime = dtime;
    this.dest = dest;
    this.arrive = arrive;
    this.atime = atime;
  }

  public void setFlightNumber(String flightNumber) {
    this.flightNumber = flightNumber;
  }

  public void setSrc(String src) {
    this.src = src;
  }

  public void setDepart(String depart) {
    this.depart = depart;
  }

  public void setDtime(String dtime) {
    this.dtime = dtime;
  }

  public void setDest(String dest) {
    this.dest = dest;
  }

  public void setArrive(String arrive) {
    this.arrive = arrive;
  }

  public void setAtime(String atime) {
    this.atime = atime;
  }

  @Override
  public int getNumber() {
    return 42;
  }

  @Override
  public String getSource() {
    throw new UnsupportedOperationException("This method is not implemented yet");
  }

  @Override
  public String getDepartureString() {
    throw new UnsupportedOperationException("This method is not implemented yet");
  }

  @Override
  public String getDestination() {
    throw new UnsupportedOperationException("This method is not implemented yet");
  }

  @Override
  public String getArrivalString() {
    throw new UnsupportedOperationException("This method is not implemented yet");
  }
}
