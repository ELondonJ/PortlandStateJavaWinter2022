package edu.pdx.cs410J.ljoseph;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {

  public static void main(String[] args) {
    int flightNumber = -1;
    String src = null;
    String depart = "01/02/1989";
    String dtime = "01:59";
    String dest = null;
    String arrive = "01/02/1989";
    String atime = "01:59";
    boolean print = false;
    boolean readme = false;



    System.err.println("Missing command line arguments");

  try {
    Flight flight = new Flight(flightNumber, depart, dtime, arrive, atime);  // Refer to one of Dave's cglasses so that we can be sure it is on the classpath
  }catch(IllegalArgumentException ex){
    System.err.println(ex);
    System.exit(1);
  }
    System.exit(0);
  }

}