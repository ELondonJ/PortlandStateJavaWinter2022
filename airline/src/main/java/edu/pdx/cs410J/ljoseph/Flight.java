package edu.pdx.cs410J.ljoseph;

import edu.pdx.cs410J.AbstractFlight;

public class Flight extends AbstractFlight {

  public static final String INVALID_DATE = "Incorrect Date Format. dd/mm/yyyy";
  public static final String INVALID_TIME = "Time format must be mm:hh";
  private int flightNumber;
  private String src;
  private String arrive;
  private String atime;
  private String depart;
  private String dtime;
  private String dest;

  public Flight(int flightNumber,String src,String depart, String dtime, String dest, String arrive, String atime) throws IllegalArgumentException {
    if(flightNumber < 0)
      throw new IllegalArgumentException("Flight number must be greater than zero");
    this.flightNumber = flightNumber;
    this.src = validSrcDest(src);
    this.depart = validDate(depart.trim());
    this.dtime = getValidTime(dtime);
    this.dest = validSrcDest(dest);
    this.arrive = validDate(arrive.trim());
    this.atime = getValidTime(atime);

  }

  public String validSrcDest(String toValid) {
    if (!toValid.matches("\\b\\w{3}[a-z][A-Z]\\b"))
      throw new IllegalArgumentException("Source and Destination represented by three-letter code. ie pdx;");
    return toValid;

  }
  public String getValidTime(String time) {

    if (!time.matches("^\\d{2}:\\d{2}$"))
      throw new IllegalArgumentException(INVALID_DATE);

    for(int i = 0; i < time.length(); i++){
      if(i != 2)
        if(Character.getNumericValue(time.charAt(i)) > 9
                || Character.getNumericValue(time.charAt(i)) < 0)
          throw new IllegalArgumentException(INVALID_TIME);
    }
    int[] numTime = getIntRepArray(time);
    for(int i = 0; i < time.length(); i++) {
      if (i != 2 && numTime[i] < 0 || numTime[i] > 9) //filters out everything but nums between 0-9,
        throw new IllegalArgumentException(INVALID_TIME);
    }
    if(numTime[0] > 2)
      throw new IllegalArgumentException(INVALID_TIME);
    if(numTime[0] == 2 && numTime[1] > 4)
      throw new IllegalArgumentException(INVALID_TIME);
    if(numTime[3] > 5)
      throw new IllegalArgumentException(INVALID_TIME);
    return time;
  }

  public int[] getIntRepArray(String date){
    int[] numRep = new int[date.length()];
    for(int i = 0; i < date.length(); i++){
        numRep[i] = Character.getNumericValue(date.charAt(i));
    }
    return numRep;

  }

  private String validDate(String date) throws IllegalArgumentException{

    if (!date.matches("^\\d{2}/\\d{2}/\\d{4}$"))
      throw new IllegalArgumentException(INVALID_DATE);

    int[] numDate = getIntRepArray(date);
    for(int i = 0; i < date.length(); i++){
      if(i != 5 && i != 2 && numDate[i] < 0 || numDate[i]>9) //filters out everything but nums between 0-9,
        throw new IllegalArgumentException(INVALID_DATE);
    }
    if (numDate[0] > 1 || numDate[3] > 3)
      throw new IllegalArgumentException(INVALID_DATE);
    if (numDate[0] == 1 && numDate[1] > 2)
      throw new IllegalArgumentException(INVALID_DATE);
    if(numDate[3] == 3 && numDate[4] > 1)
      throw new IllegalArgumentException(INVALID_DATE);
    if (numDate[0]== 0 && numDate[1]== 2)
      if(numDate[3] > 2 || (numDate[3] == 2 && numDate[4] > 8))
        throw new IllegalArgumentException(INVALID_DATE);
    return date;
  }

  @Override
  public int getNumber() {
    return flightNumber;
  }

  @Override
  public String getSource() {
    return src;
  }

  @Override
  public String getDepartureString() {
    return depart + " " + dtime;
  }

  @Override
  public String getDestination() {
    return dest;
  }

  @Override
  public String getArrivalString() {
    return arrive + " " + atime;
  }
}
