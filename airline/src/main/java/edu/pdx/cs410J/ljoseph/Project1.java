package edu.pdx.cs410J.ljoseph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {
  /**
   * Main program that parses the command line and creates a flight object
   * and airline object.
   */
  public static void main(String[] args) throws IOException {
    if(args.length < 1 ){
      System.err.println("Missing command line arguments.");
      System.err.println("Enter \"-readme\" at the command prompt for argument requirements.");
      System.exit(1);
    }

    Airline airline = null;
    Flight flight = null;
    String airlineName = null;
    int flightNumber = -1;
    String src = null;
    String depart = null;
    String dtime = null;
    String dest = null;
    String arrive = null;
    String atime = null;
    boolean print = false;

    //loop parses saves each arg in appropriate variable
    for (String arg : args) {
      if (arg.charAt(0) == '-') {
        if (arg.equalsIgnoreCase("-print"))
          print = true;
        else if (arg.equalsIgnoreCase("-readme")) {
          InputStream readme = Project1.class.getResourceAsStream("README.txt");
          BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
          String line = reader.readLine();
          do{
            System.out.println(line);
            line = reader.readLine();
          }while(line != null);
        } else
          System.err.println("Error! Unknown flag " + arg);
      } else if (airlineName == null)
        airlineName = arg;
      else if (flightNumber == -1) {
        try {
          flightNumber = Integer.parseInt(arg);
        } catch (NumberFormatException e) {
          System.err.println("Incorrect flight number format");
          System.exit(1);
        }
      } else if (src == null)
        src = arg;
      else if (depart == null)
        depart = arg;
      else if (dtime == null)
        dtime = arg;
      else if (dest == null)
        dest = arg;
      else if (arrive == null)
        arrive = arg;
      else if (atime == null)
        atime = arg;
    }
    //creates flight object with parsed arguments
    try {
      flight = new Flight(flightNumber,src, depart, dtime, dest, arrive, atime);
    } catch(IllegalArgumentException ex){
      System.err.println(ex);
      System.exit(1);
    }
    try {
      airline = new Airline(airlineName);
    }catch(IllegalArgumentException ex){
      System.err.println(ex);
      System.exit(1);
    }
    airline.addFlight(flight);
    //print flight info if print flags present
    if(print){
      System.out.println(flight);
    }
    System.exit(0);
  }
}
