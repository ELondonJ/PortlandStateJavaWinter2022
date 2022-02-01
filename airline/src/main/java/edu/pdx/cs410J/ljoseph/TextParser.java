package edu.pdx.cs410J.ljoseph;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;

import javax.swing.text.html.parser.Parser;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Reads from a specified text file containing airline information
 * creats a new airline object from the file
 */
public class TextParser implements AirlineParser<Airline> {
  private final Reader reader;

  /**
   * Creates a TextParser with a <code>File Reader</code>
   * @param reader File Reader containing the name of the text file to be parsed
   */
  public TextParser(Reader reader) {
    this.reader = reader;
  }

  /**
   * Creates a new <code>Airline</code> object from the information in
   * the text file specified in the File Reader
   * @return <code>airline</code>
   * @throws ParserException
   */
  @Override
  public Airline parse() throws ParserException {
    try (BufferedReader br = new BufferedReader(this.reader)) {
      String[] fArgs = new String[9];       //Holds the parsed args to create a flight
      String airlineName = br.readLine();
      String line;
      Flight flight = null;
      if (airlineName == null) {
        throw new ParserException("Missing airline name");
      }
      Airline airline = new Airline(airlineName);       //Airline name will be the first line in the text file
      while((line = br.readLine()) != null){            //Each line after will have all info for one flight
        String[] splitLine = line.split("\\s+");  //Splits the string in text file into String[] of
        if(splitLine.length != fArgs.length)            //args for flight
          throw new ParserException("Incorrect file format while parsing airline text" );
        for(int i = 0; i < fArgs.length; i++){
          fArgs[i] = splitLine[i];
        }
        try {
          flight = new Flight(Integer.parseInt(fArgs[0]), fArgs[1], fArgs[2], fArgs[3], fArgs[4], fArgs[5],
                  fArgs[6], fArgs[7], fArgs[8]);
          airline.addFlight(flight);
        }catch(IllegalArgumentException e){
          throw new ParserException("Illegal FLight argument while parsing airline text: " + e.getMessage());
        }
      }
      br.close();
      return airline;

    } catch (IOException e) {
      throw new ParserException("While parsing airline text", e);
    }
  }
}
