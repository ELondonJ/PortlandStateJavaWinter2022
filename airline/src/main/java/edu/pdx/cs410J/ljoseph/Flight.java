package edu.pdx.cs410J.ljoseph;

import edu.pdx.cs410J.AbstractFlight;

public class Flight extends AbstractFlight {

  public static final String FORMAT_DD_MM_YYYY = "Incorrect Date Format. dd/mm/yyyy";
  public static final String TIME_FORMAT_MM_HH = "Time format must be mm:hh";
  private int flightNumber;
  private String arrive;
  private String atime;
  private String depart;
  private String dtime;

  public Flight(int flightNumber,String depart, String dtime, String arrive, String atime) throws IllegalArgumentException {
    if(flightNumber < 0)
      throw new IllegalArgumentException("Flight number must be greater than zero");
    this.flightNumber = flightNumber;
    if(depart != null)
      this.arrive = validDate(depart.trim());
    else
      this.arrive = null;
    if(dtime != null)
      this.atime = getValidTime(dtime);
    else
      this.atime = null;
    if(arrive != null)
      this.arrive = validDate(arrive.trim());
    else
      this.arrive = null;
    if(atime != null)
      this.atime = getValidTime(atime);
    else
      this.atime = null;

  }

  public String getValidTime(String time) {
    if(time.length() != 5 || time.charAt(2) != ':')
      throw new IllegalArgumentException(TIME_FORMAT_MM_HH);

    for(int i = 0; i < time.length(); i++){
      if(i != 2)
        if(Character.getNumericValue(time.charAt(i)) > 9
                || Character.getNumericValue(time.charAt(i)) < 0)
          throw new IllegalArgumentException(TIME_FORMAT_MM_HH);
    }
    int[] numTime = getIntRepArray(time);
    for(int i = 0; i < time.length(); i++) {
      if (i != 2 && numTime[i] < 0 || numTime[i] > 9) //filters out everything but nums between 0-9,
        throw new IllegalArgumentException(TIME_FORMAT_MM_HH);
    }
    if(numTime[0] > 2)
      throw new IllegalArgumentException(TIME_FORMAT_MM_HH);
    if(numTime[0] == 2 && numTime[1] > 4)
      throw new IllegalArgumentException(TIME_FORMAT_MM_HH);
    if(numTime[3] > 5)
      throw new IllegalArgumentException(TIME_FORMAT_MM_HH);
    return time;
  }

  public int[] getIntRepArray(String date){
    int[] numRep = new int[date.length()];
    for(int i = 0; i < date.length(); i++){
        numRep[i] = Character.getNumericValue(date.charAt(i));
    }
    return numRep;

  }

  private String validDate(String arrive) throws IllegalArgumentException{

    if(arrive.length() != 10)
      throw new IllegalArgumentException(FORMAT_DD_MM_YYYY);
    if (arrive.charAt(2) != '/'|| arrive.charAt(5) != '/')
      throw new IllegalArgumentException(FORMAT_DD_MM_YYYY);

    int[] numDate = getIntRepArray(arrive);
    for(int i = 0; i < arrive.length(); i++){
      if(i != 5 && i != 2 && numDate[i] < 0 || numDate[i]>9) //filters out everything but nums between 0-9,
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

  public String getAtime() {
    return atime;
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
    return depart + " " + dtime;
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
