package edu.pdx.cs410J.ljoseph;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

public class TextParser implements AirlineParser<Airline> {
  private final Reader reader;

  public TextParser(Reader reader) {
    this.reader = reader;
  }

  @Override
  public Airline parse() throws ParserException {
    try (BufferedReader br = new BufferedReader(this.reader)) {
      String[] fArgs = new String[7];
      String airlineName = br.readLine();
      String line;
      Flight flight = null;
      if (airlineName == null) {
        throw new ParserException("Missing airline name");
      }
      Airline airline = new Airline(airlineName);
      while((line = br.readLine()) != null){
        String[] splitLine = line.split("\\s+");
        for(int i = 0; i < fArgs.length; i++){
          fArgs[i] = splitLine[i];
        }
        try {
          flight = new Flight(Integer.parseInt(fArgs[0]), fArgs[1], fArgs[2], fArgs[3], fArgs[4], fArgs[5], fArgs[6]);
          airline.addFlight(flight);
        }catch(IllegalArgumentException e){
          throw new ParserException("While parsing airline text", e);
        }
      }
      br.close();
      return airline;

    } catch (IOException e) {
      throw new ParserException("While parsing airline text", e);
    }
  }
}
