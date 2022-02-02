package edu.pdx.cs410J.ljoseph;

import edu.pdx.cs410J.ParserException;
//import jdk.incubator.vector.VectorOperators;

import java.io.*;

/**
 * The main class for the CS410J airline Project
 */
public class Project3 {

  /**
   * Print usages from the ReadMe resource file
   * @throws IOException
   */
  private static void getUsage() throws IOException {
    InputStream readme = Project3.class.getResourceAsStream("README.txt");
    BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
    String line = reader.readLine();
    while(!line.contains("usage"))
      line = reader.readLine();
    do {
      System.err.println(line);
      line = reader.readLine();
    } while (line != null);
  }

  /**
   * Main program that parses the command line and creates a flight object
   * and airline object.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) throws IOException {
    if (args.length < 1) {
      System.err.println("Missing command line arguments.");
      try {getUsage();}
      catch (IOException e) {
        System.err.println("While reading usage from readme file: " + e.getMessage());
      }
      System.exit(1);
    }
    Airline airline = null;
    Flight flight = null;
    String airlineName = null;
    int flightNumber = -1;
    String src = null;
    String depart = null;
    String dtime = null;
    String dAmPm = null;
    String dest = null;
    String arrive = null;
    String atime = null;
    String aAmPm = null;
    boolean print = false;
    boolean fileFlag = false;
    boolean prettyPrint = false;
    TextDumper dumper;
    TextParser parser;
    String textFileName = null;
    String prettyFile = null;


    //loop parses saves each arg in appropriate variable, sets option flags as needed
    for (int i = 0; i < args.length; i++) {
      if (args[i].charAt(0) == '-') {
        if (args[i].equalsIgnoreCase("-print"))
          print = true;
        else if (args[i].equalsIgnoreCase("-readme")) {
          InputStream readme = Project3.class.getResourceAsStream("README.txt");
          BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
          String line = reader.readLine();
          do {
            System.out.println(line);
            line = reader.readLine();
          } while (line != null);
          System.exit(0);
        } else if (args[i].equalsIgnoreCase("-textFile")) {
          if (++i >= args.length) {
            System.err.println("A file name is required with -textFile flag");
            System.exit(1);
          }
          fileFlag = true;
          textFileName = args[i];
        } else if (args[i].equalsIgnoreCase("-pretty")) {
          if (++i >= args.length) {
            System.err.println("A file name is required if you'd like to pretty print to a file" +
                    "you can also enter \"-\" if you'd like to pretty print to the screen");
            System.exit(1);
          }
          prettyPrint = true;
          prettyFile = args[i];

        } else {
          System.err.println("Error! Unknown flag ");
          System.exit(1);
        }
      } else if (airlineName == null) {
        airlineName = args[i];
      } else if (flightNumber == -1) {
        try {
          flightNumber = Integer.parseInt(args[i]);
        } catch (NumberFormatException e) {
          System.err.println(e.getMessage() + " Incorrect flight number format");
          System.exit(1);
        }
      } else if (src == null)
        src = args[i];
      else if (depart == null)
        depart = args[i];
      else if (dtime == null)
        dtime = args[i];
      else if (dAmPm == null)
        dAmPm = args[i];
      else if (dest == null)
        dest = args[i];
      else if (arrive == null)
        arrive = args[i];
      else if (atime == null)
        atime = args[i];
      else if (aAmPm == null)
        aAmPm = args[i];
      else {
        System.err.println("Too many command line arguments");
        try {getUsage();}
        catch (IOException e) {
          System.err.println("While reading usage: " + e.getMessage());
        }
        System.exit(1);
      }
    }

    //creates flight object with parsed arguments
    try {
      flight = new Flight(flightNumber, src, depart, dtime, dAmPm, dest, arrive, atime, aAmPm);
    } catch (IllegalArgumentException ex) {
      System.err.println(ex.getMessage());
      System.exit(1);
    }
    //print a parsable file representing an airline
    if (fileFlag) {
      File textFile = new File(textFileName);
      try {
        //if file already exists first parse into an Airline object
        if (textFile.exists()) {
          parser = new TextParser(new FileReader(textFile));
          airline = parser.parse();
          if (!airline.getName().equalsIgnoreCase(airlineName)) {
            System.err.println("Airline can not be updated. Airline Name in " + textFileName + " does not match airline name" +
                    " entered at the command line.");
            System.err.println("Airline name on command line: \"" + airlineName
                    + "\"\nAirline name in file: \"" + airline.getName());
            System.exit(1);
          }
          //if file does not exist create new Airline from command line and write its contents
        } else {
          airline = new Airline(airlineName);
        }
        //write Airline to file with textDumper object
        dumper = new TextDumper(new FileWriter(textFile));
        airline.addFlight(flight);
        dumper.dump(airline);

      } catch (IllegalArgumentException | ParserException ex) {
        System.err.println(ex.getMessage());
        System.exit(1);
      }
    } else {
      airline = new Airline(airlineName);
      airline.addFlight(flight);
    }

    //print flight info if print flags present
    if (print) {
      System.out.println(flight);
    }
    //prettyPrint Airline if prettyPrint flag is present
    if (prettyPrint) {
      Airline aAirline;
      //pretty print the airline in the textFile if fileFlag is present
      if (fileFlag) {
        File parseFile = new File(textFileName);
        parser = new TextParser(new FileReader(parseFile));
        try {
          aAirline = parser.parse();
        } catch (IllegalArgumentException | ParserException ex) {
          System.err.println(ex.getMessage());
          System.exit(1);
        }
        //pretty print a new airline on command line
      } else {
        aAirline = new Airline(airlineName);
        aAirline.addFlight(flight);
      }
      PrettyPrinter pPrinter;
      //print to standard out
      if (prettyFile.equals("-"))
        pPrinter = new PrettyPrinter(new BufferedWriter(new OutputStreamWriter(System.out)));
      //print to file indicated with -pretty flag
      else {
        File filePretty = new File(prettyFile);
        pPrinter = new PrettyPrinter(new FileWriter(filePretty));
      }
      pPrinter.dump(airline);
    }
      System.exit(0);
    }

}
