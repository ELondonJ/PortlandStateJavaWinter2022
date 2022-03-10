package edu.pdx.cs410j.ljoseph;

import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AirportNames;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

/**
 * This class represents a flight and is and extension of
 * the abstract class AbstractFlight.  Each flight has a unique
 * flight number identifying it, a three-letter source code identifying
 * its departing airline, a departure date and time, a three-letter
 * destination code identifying its arrival airport, and an
 * arrival date and time.
 */
public class Flight extends AbstractFlight implements Comparable<Flight> {

  public static final String INVALID_DATE = "Incorrect Date Format. dd/mm/yyyy";
  public static final String INVALID_FLIGHT_NUM = "Flight number must be an integer greater than zero";
  private int flightNumber; //unique flight number
  private String src;       //three-letter source code
  private Date arrive;    //arrival date

  private Date depart;    //departure date
  private String dest;      //three-letter destination code
/**
 * Creates a new flight
 */
  public Flight(int flightNumber, String src, String depart, String dtime, String dAmPm, String dest,
                String arrive, String atime, String aAmPm)
          throws IllegalArgumentException {
    if(flightNumber < 0)
      throw new IllegalArgumentException(INVALID_FLIGHT_NUM);
    this.flightNumber = flightNumber;
    if(src == null ||depart == null  ||dtime == null || dest == null|| arrive == null ||
            atime == null || dAmPm == null || aAmPm ==null)
      throw new IllegalArgumentException("Missing Flight argument");
    this.src = validSrcDest(src.trim());
    this.depart = validDate(depart,dtime,dAmPm);
    this.dest = validSrcDest(dest.trim());
    this.arrive = validDate(arrive, atime,aAmPm);
    if(this.src.equals(this.dest))
      throw new IllegalArgumentException("The source code and destination code can not be the same" );
    validDatesDepartBeforeArrive();

  }

  public Date getDepart() {
    return depart;
  }

  public Date getArrive() {
    return arrive;
  }


  private void validDatesDepartBeforeArrive() {
    if(this.depart.after(this.arrive))
      throw new IllegalArgumentException("Flights arrival time can not be before the flights departure time");

  }
  public String getFlightDuration(){
    long duration = this.arrive.getTime() - this.depart.getTime();//as given

    long minutes = TimeUnit.MILLISECONDS.toMinutes(duration);
    return String.valueOf(minutes) + " min";

  }

  /**
   * Validates that Src and Dest are three-letter strings
   * Incorrect inputs throws exception
   */
  public String validSrcDest(String toValid) {
    if (!toValid.matches("^[a-zA-Z]{3}$"))
      throw new IllegalArgumentException("Source and Destination represented by three-letter code. ie pdx;");
    String airportName = AirportNames.getName(toValid.toUpperCase());
    if(airportName == null)
      throw new IllegalArgumentException("The code " + toValid + " does not represent a valid airport" );
    return toValid.toUpperCase();

  }
  /**
   * Validates that arrival and departure have the format mm/dd/yyyy
   * Invalid inputs throw exception
   */
  private Date validDate(String date, String time, String amPm) throws IllegalArgumentException{
    //if(date.trim().length() != 10)
//      throw new IllegalArgumentException(INVALID_DATE);
    StringJoiner sj = new StringJoiner(" ");
    sj.add(date);
    sj.add(time);
    sj.add(amPm);
    DateFormat inForm = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.getDefault());
    inForm.setLenient(false);
    Date aDate;
    try {
      aDate = inForm.parse(sj.toString().trim());
    } catch (ParseException e){
      throw new IllegalArgumentException(INVALID_DATE);
    }
    return aDate;
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
    DateFormat outForm = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.getDefault());
    return outForm.format(this.depart);
  }
  public String getDepartureStringPretty() {
    DateFormat outForm = new SimpleDateFormat("MM/dd/yy hh:mm a", Locale.getDefault());
    return outForm.format(this.depart);
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
    DateFormat outForm = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.getDefault());
    return outForm.format(this.arrive);
  }
  public String getArrivalStringPretty() {
    DateFormat outForm = new SimpleDateFormat("MM/dd/yy hh:mm a", Locale.getDefault());
    return outForm.format(this.arrive);
  }
  @Override
  public int compareTo(Flight o) {
   // return o.src.compareTo(this.src);
    if(this.src.compareTo(o.src) == 0){
     return this.depart.compareTo(o.depart);
    }
    return this.src.compareTo(o.src);
  }
}
