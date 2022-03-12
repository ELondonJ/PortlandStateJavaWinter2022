package edu.pdx.cs410J.ljoseph;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

public class TextParserTest {

  @Test
  void validTextFileCanBeParsed() throws ParserException {
    InputStream resource = getClass().getResourceAsStream("valid-airline.txt");
    assertThat(resource, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(resource));
    Airline airline = parser.parse();
    assertThat(airline.getName(), equalTo("Test Airline"));
  }

  @Test
  void invalidTextFileThrowsParserException() {
    InputStream resource = getClass().getResourceAsStream("empty-airline.txt");
    assertThat(resource, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(resource));
    assertThrows(ParserException.class, parser::parse);
  }
  @Test
  void invalidIllegalArgumentsInFileThrowsException(@TempDir File tempDir) throws IllegalArgumentException, ParserException, IOException {

    File testFile = new File("airtest3.txt");
    PrintWriter pw = new PrintWriter(testFile);
    pw.println("portland int");
    pw.println("2344 pdx 12/12/1212 12:12 am slc 12/12/1212 12:aa am");
    pw.close();
    try {
      TextParser parser = new TextParser(new FileReader(testFile));
      Airline read = parser.parse();
    } catch (IllegalArgumentException e){
      assertTrue(e.getMessage().contains("mm:hh"));
    } catch (ParserException p){
      //pass

    };
  }
  @Test
  void airlineNamesInFileAndCommandLineDontMatch() throws IllegalArgumentException, ParserException, IOException {

    File testFile = new File("airtest3.txt");
    PrintWriter pw = new PrintWriter(testFile);
    pw.println("portland int");
    pw.println("2344 pdx 12/12/1212 12:12 am slc 12/12/1212 12:33 am");
    pw.close();
    try {
      TextParser parser = new TextParser(new FileReader(testFile));
      Airline read = parser.parse();
    } catch (IllegalArgumentException e){
      assertTrue(e.getMessage().contains("mm:hh"));
    } catch (ParserException p){
      //pass

    };
  }
  public ArrayList<Airline> getList(){
    ArrayList<Airline> airlines = new ArrayList<>();
    for(int i = 0; i < 5; i++){
      Airline airline = new Airline("Air" + i);
      for(int j = 0; j < 3; j++){
        airline.addFlight(new Flight(i, "pdx", "12/12/2002", "11:11", "am",
                "slc", "12/12/2002", "11:11", "pm"));
      }
      airlines.add(airline);
    }
    return airlines;
  }
  @Test
  public void TextParserTest1() throws IOException {
    try{
      File file = new File("Airlinetest.txt");
      ArrayList<Airline> airlines = getList();

      PrintWriter pw = new PrintWriter(new FileWriter(file));
      TextDumper dumper = new TextDumper(pw);
      dumper.dumpMultiple(airlines);
      BufferedReader bf = new BufferedReader(new FileReader(file));
      TextParser parser = new TextParser(bf);
      dumper.dumpMultiple(airlines);
      ArrayList<Airline> temp = parser.parseMultiple();
      assertEquals(temp.size(),5);


    }catch(
            IOException | ParserException e) {
      String mess = e.getMessage();

    }
  }
}
