package edu.pdx.cs410J.ljoseph;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {

  public static void main(String[] args) {
    String flightNumber = null;
    String src = null;
    String depart = null;
    String dtime = null;
    String dest = null;
    String arrive = null;
    String atime = null;
    boolean print = false;
    boolean readme = false;


    System.err.println("Missing command line arguments");
    for (String arg : args) {
      ;
    }
    Flight flight = new Flight(flightNumber, src, depart, dtime, dest, arrive, atime);  // Refer to one of Dave's classes so that we can be sure it is on the classpath
    System.exit(1);
  }

}