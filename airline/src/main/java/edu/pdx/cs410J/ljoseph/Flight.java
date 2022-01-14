package edu.pdx.cs410J.ljoseph;

import edu.pdx.cs410J.AbstractFlight;

public class Flight extends AbstractFlight {

  private int flightNumber;
  private String arrive;
  private String atime;

  public Flight(int flightNumber, String arrive, String atime) throws IllegalArgumentException {
    if(flightNumber < 0)
      throw new IllegalArgumentException("Flight number must be greater than zero");
    this.flightNumber = flightNumber;
    this.arrive = getArrive(arrive);
    if(atime != null)
      this.atime = atime.trim();

  }

  private String getArrive(String arrive) throws IllegalArgumentException{
    String Message;
   if(arrive.charAt(2) != '/' || arrive.charAt(5) != '/')
     throw new IllegalArgumentException("Incorrect Date Format. dd/mm/yyyy");
   if(Character.getNumericValue(arrive.charAt(0)) < 2 ||Character.getNumericValue(arrive.charAt(0))  > 0) {
     if (Character.getNumericValue(arrive.charAt(0)) == 1 && Character.getNumericValue(arrive.charAt(1)) > 2)
       throw new IllegalArgumentException("Incorrect Date Format. dd/mm/yyyy");
   }else{
     throw new IllegalArgumentException("Incorrect Date Format. dd/mm/yyyy");

   }

//     if(Character.getNumericValue(arrive.charAt(1))> 9 ||Character.getNumericValue(arrive.charAt(1))< 0)


    return arrive;
  }


  @Override
  public int getNumber() {
    return flightNumber;
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
    return arrive + " " + atime;
  }
}
