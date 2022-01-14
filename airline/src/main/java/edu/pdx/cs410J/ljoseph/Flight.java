package edu.pdx.cs410J.ljoseph;

import edu.pdx.cs410J.AbstractFlight;

public class Flight extends AbstractFlight {

  public static final String FORMAT_DD_MM_YYYY = "Incorrect Date Format. dd/mm/yyyy";
  private int flightNumber;
  private String arrive;
  private String atime;

  public Flight(int flightNumber, String arrive, String atime) throws IllegalArgumentException {
    if(flightNumber < 0)
      throw new IllegalArgumentException("Flight number must be greater than zero");
    this.flightNumber = flightNumber;
    this.arrive = validArrive(arrive.trim());
    if(atime != null)
      this.atime = getValidAtime(atime);

  }

  public String getValidAtime(String time) {
    if(time.length() != 5 || time.charAt(2) != ':')
      throw new IllegalArgumentException("Time format must be mm:hh");

    return time;
  }

  public int[] getNumDate(String date){
    int[] numRep = new int[date.length()];
    for(int i = 0; i < date.length(); i++){
      if(i == 2 || i == 5) {
        if (date.charAt(i) != '/')
          throw new IllegalArgumentException(FORMAT_DD_MM_YYYY);
        else
          numRep[i] = -1;
      }
      else {
        numRep[i] = Character.getNumericValue(date.charAt(i));
      }
    }
    return numRep;

  }

  private String validArrive(String arrive) throws IllegalArgumentException{
    String Message;
    if(arrive.length() != 10)
      throw new IllegalArgumentException(FORMAT_DD_MM_YYYY);
    int[] numDate = getNumDate(arrive);
    for(int i = 0; i < arrive.length(); i++){
      if(i != 5 && i != 2 && numDate[i] < 0 || numDate[i]>9) //date nums between 0-9
        throw new IllegalArgumentException(FORMAT_DD_MM_YYYY);
    }
    if (numDate[0] > 1 || numDate[3] > 3)
      throw new IllegalArgumentException(FORMAT_DD_MM_YYYY);
    if (numDate[0] == 1 && numDate[1] > 2)
      throw new IllegalArgumentException(FORMAT_DD_MM_YYYY);
    if(numDate[3] == 3 && numDate[4] > 1)
      throw new IllegalArgumentException(FORMAT_DD_MM_YYYY);
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
