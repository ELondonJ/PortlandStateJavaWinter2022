package edu.pdx.cs410J.ljoseph;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {

  public static void main(String[] args) {
    if(args.length < 5){
      System.err.println("Missing command line Arguments ");
      System.exit(1);
    }
    Flight flight = null;
    int flightNumber = -1;
    String src = null;
    String depart = null;
    String dtime = null;
    String dest = null;
    String arrive = null;
    String atime = null;
    boolean print = false;
    boolean readme = false;

    for(int i = 0; i < args.length; i++ ) {
      if (args[i].charAt(0) == '-') {
        if (args[i].equalsIgnoreCase("-print"))
          print = true;
        else if (args[i].equalsIgnoreCase("-readme"))
          readme = true;
        else
          System.err.println("Unknown flag " + args[i]);
      }
      else if (flightNumber == -1) {
        try {
          flightNumber = Integer.parseInt(args[i]);
        } catch (NumberFormatException e) {
          System.err.println("Incorrect flight number format");
          System.exit(1);
        }
      }
      else if (src == null)
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
    try {
      flight = new Flight(flightNumber,src, depart, dtime, dest, arrive, atime);
    } catch(IllegalArgumentException ex){
    System.err.println(ex);
    System.exit(1);
    }
    if(print)
      System.out.println(flight);

    System.exit(0);
  }

}