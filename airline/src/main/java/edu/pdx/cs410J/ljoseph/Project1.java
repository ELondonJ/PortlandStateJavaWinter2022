package edu.pdx.cs410J.ljoseph;

import java.io.*;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {
  public static final String ARGS_INFO = "Enter \"-readme\" at the command prompt for argument requirements.";

  /**
   * Main program that parses the command line and creates a flight object
   * and airline object.
   */
  public static void main(String[] args) throws IOException {
    if(args.length < 0){
      System.err.println("Missing command line arguments.");
      System.err.println(ARGS_INFO);
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
    boolean fileFlag = false;
    TextDumper dumper = null;

    //loop parses saves each arg in appropriate variable
    for (int i = 0; i<args.length; i++) {
      if (args[i].charAt(0) == '-') {
        if (args[i].equalsIgnoreCase("-print"))
          print = true;
        else if (args[i].equalsIgnoreCase("-readme")) {
          InputStream readme = Project1.class.getResourceAsStream("README.txt");
          BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
          String line = reader.readLine();
          do {
            System.out.println(line);
            line = reader.readLine();
          } while (line != null);
          System.exit(0);
        } else if (args[i].equalsIgnoreCase("-textFile")) {
          if(++i> args.length){
            System.out.println("A file name is required with -textFile flag");
            System.err.println(1);
          }
          fileFlag = true;
          File textFile = new File(args[i]);
          dumper = new TextDumper(new FileWriter(textFile));


      }
        else {
          System.err.println("Error! Unknown flag ");
          System.exit(1);
        }
      } else if (airlineName == null) {
        airlineName = args[i];
      }
      else if (flightNumber == -1) {
        try {
          flightNumber = Integer.parseInt(args[i]);
        } catch (NumberFormatException e) {
          System.err.println( e + "Incorrect flight number format");
          System.exit(1);
        }
      } else if (src == null)
        src = args[i];
      else if (depart == null)
        depart = args[i];
      else if (dtime == null)
        dtime = args[i];
      else if (dest == null)
        dest = args[i];
      else if (arrive == null)
        arrive = args[i];
      else if (atime == null)
        atime = args[i];
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
    if(fileFlag)
      dumper.dump(airline);

      //print flight info if print flags present
    if(print){
      System.out.println(flight);
    }
    System.exit(0);
  }
}
