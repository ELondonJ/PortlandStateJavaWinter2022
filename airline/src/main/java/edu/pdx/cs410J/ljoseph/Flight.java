package edu.pdx.cs410J.ljoseph;

import edu.pdx.cs410J.AbstractFlight;
/**
 * This class represents a flight and is and extension of
 * the abstract class AbstractFlight.  Each flight has a unique
 * flight number identifying it, a three-letter source code identifying
 * its departing airline, a departure date and time, a three-letter
 * destination code identifying its arrival airport, and an
 * arrival date and time.
 */
public class Flight extends AbstractFlight {

  public static final String INVALID_DATE = "Incorrect Date Format. dd/mm/yyyy";
  public static final String INVALID_TIME = "Time format must be mm:hh";
  public static final String INVALID_FLIGHT_NUM = "Flight number must be an integer greater than zero";
  private int flightNumber; //unique flight number
  private String src;       //three-letter source code
  private String arrive;    //arrival date
  private String atime;     //atime
  private String depart;    //departure date
  private String dtime;     //departure time
  private String dest;      //three-letter destination code
/**
 * Creates a new flight
 */
  public Flight(int flightNumber,String src,String depart, String dtime, String dest, String arrive, String atime)
          throws IllegalArgumentException {
    if(flightNumber < 0)
      throw new IllegalArgumentException(INVALID_FLIGHT_NUM);
    this.flightNumber = flightNumber;
    if(src != null)
      this.src = validSrcDest(src.trim());
    if(depart != null)
      this.depart = validDate(depart.trim());
    if(dtime != null)
      this.dtime = getValidTime(dtime.trim());
    if(dest != null)
      this.dest = validSrcDest(dest.trim());
    if(arrive != null)
      this.arrive = validDate(arrive.trim());
    if(atime != null)
      this.atime = getValidTime(atime.trim());
  }
  /**
   * Validates that Src and Dest are three-letter strings
   * Incorrect inputs throws exception
   */
  public String validSrcDest(String toValid) {
    if (!toValid.matches("^[a-zA-Z]{3}$"))
      throw new IllegalArgumentException("Source and Destination represented by three-letter code. ie pdx;");
    return toValid;

  }
  /**
   * Validates that aTime and dTime have the format mm:hh
   * Invalid inputs throw exception
   */
  public String getValidTime(String time) {

    if (!time.matches("^\\d{2}:\\d{2}$")) //pattern matching mm:hh
      throw new IllegalArgumentException(INVALID_TIME);

    int[] numTime = getIntRepArray(time);

    for(int i = 0; i < time.length(); i++) {
      if (i != 2 && numTime[i] < 0 || numTime[i] > 9) //filters out everything but nums between 0-9,
        throw new IllegalArgumentException(INVALID_TIME);
    }

    if((numTime[0] > 2) || (numTime[0] == 2 && numTime[1] > 4) ||(numTime[3] > 5))
      throw new IllegalArgumentException(INVALID_TIME);
    return time;
  }
  /**
   * Returns the integer representation of the String stringRep
   */
  public int[] getIntRepArray(String stringRep){
    int[] numRep = new int[stringRep.length()];
    for(int i = 0; i < stringRep.length(); i++){
        numRep[i] = Character.getNumericValue(stringRep.charAt(i));
    }
    return numRep;
  }
  /**
   * Validates that arrival and departure have the format mm/dd/yyyy
   * Invalid inputs throw exception
   */
  private String validDate(String date) throws IllegalArgumentException{

    if (!date.matches("^\\d{2}/\\d{2}/\\d{4}$"))      //pattern matching mm/dd/yyyy
      throw new IllegalArgumentException(INVALID_DATE + " " + date);

    int[] numDate = getIntRepArray(date);                   //Int rep of date to check ints against ints for simplicity
    for(int i = 0; i < date.length(); i++){
      if(i != 5 && i != 2 && numDate[i] < 0 || numDate[i]>9) //filters out everything but nums between 0-9,
        throw new IllegalArgumentException(INVALID_DATE );
    }
    //checking month is not greater than 12 and day is not greater than 31
    if ((numDate[0] > 1 || numDate[3] > 3) ||(numDate[0] == 1 && numDate[1] > 2)
            ||(numDate[3] == 3 && numDate[4] > 1))
      throw new IllegalArgumentException(INVALID_DATE);
    //if month is 02 than day not greater than 28
    if (numDate[0]== 0 && numDate[1]== 2)
      if(numDate[3] > 2 || (numDate[3] == 2 && numDate[4] > 8))
        throw new IllegalArgumentException(INVALID_DATE);
    return date;
  }
  /**
   * returns this flight unique flight number
   */
  @Override
  public int getNumber() {
    return flightNumber;
  }
  /**
   * Returns the three-letter code of this flights source airport
   */
  @Override
  public String getSource() {
    return src;
  }

  /**
   * Creates and returns a String with this flight departure date and time
   */
  @Override
  public String getDepartureString() {
    return depart + " " + dtime;
  }

  /**
   * Returns the three-letter code of this flights destination airport
   */
  @Override
  public String getDestination() {
    return dest;
  }

  /**
   * Creates and returns a String with this flight arrival date and time
   */
  @Override
  public String getArrivalString() {
    return arrive + " " + atime;
  }
}
