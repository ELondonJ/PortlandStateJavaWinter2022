package edu.pdx.cs410J.ljoseph;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {

  public static void main(String[] args) {
    int flightNumber = -1;
    String src = null;
    String depart = null;
    String dtime = null;
    String dest = null;
    String arrive = null;
    String atime = null;
    boolean print = false;
    boolean readme = false;



    System.err.println("Missing command line arguments");


    Flight flight = new Flight(flightNumber, null , null);  // Refer to one of Dave's cglasses so that we can be sure it is on the classpath
    System.exit(1);
  }

}