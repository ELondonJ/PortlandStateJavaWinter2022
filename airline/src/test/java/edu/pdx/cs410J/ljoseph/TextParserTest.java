package edu.pdx.cs410J.ljoseph;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
  @Disabled
  void invalidIllegalArgumentsInFileThrowsException(@TempDir File tempDir) throws IllegalArgumentException, ParserException, IOException {

    File testFile = new File("airtest3.txt");
    PrintWriter pw = new PrintWriter(testFile);
    pw.println("portland int");
    pw.println("2344 pdx 12/12/1212 12:12 slc 12/12/1212 12:aa");
    pw.close();

    TextParser parser = new TextParser(new FileReader(testFile));
    Airline read = parser.parse();
    assertThrows(IllegalArgumentException.class, parser::parse);
    assertThrows(ParserException.class, parser::parse);
  }
}
