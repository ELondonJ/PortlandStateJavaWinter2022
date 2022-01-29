package edu.pdx.cs410J.ljoseph;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * An integration test for the {@link Project2} main class.
 */
class Project2IT extends InvokeMainTestCase {

    public static final String STRINGS = "-print Portland International 1234 pdx 12/12/2002 12:13 slc 12/12/2002 14:20";

    /**
     * Invokes the main method of {@link Project2} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project2.class, args );
    }

  /**
   * Tests that invoking the main method with no arguments issues an error
   */
  @Test
  void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain();
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
  }
    @Test
    void testCorrectCommandLineArguments() {
        MainMethodResult result = invokeMain("-print","Portland International","1234", "pdx",
                "12/12/2002","12:13","am", "slc","12/12/2002", "11:20","am");
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), containsString("Flight 1234 departs pdx at" +
                " 12/12/2002 12:13 AM arrives slc at 12/12/2002 11:20"));
    }


    @Test
    void testIncorrectCommandLineArgumentsThrowsError() {
        MainMethodResult result = invokeMain("Portland International","1234", "pdxx", "12/12/2002","12:13","am",
                "slc","12/12/2002", "14:20","am");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Source and Destination represented" +
                " by three-letter code. ie pdx"));
    }
    @Test
    void testIncorrectFlightNumberFormatArgumentsThrowsError() {
        MainMethodResult result = invokeMain("Portland International","aba", "pdx", "12/12/2002","12:13","am",
                "slc","12/12/2002", "14:20","am");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Incorrect flight number format"));
    }
    @Test
    void testReadMeCommandLineArgument() {
        MainMethodResult result = invokeMain("-readme");
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), containsString("410J Project 2: Airline"));
    }
    @Test
    void testTextFileFlagWithNoFileCommandLineArgument() {
        MainMethodResult result = invokeMain("-textFile");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("A file name"));
    }
    @Test
    void toManyCommandLineArgsCommandLineArgument() {
        MainMethodResult result = invokeMain("Portland International","345", "pdx", "12/12/2002","12:13","am","slc",
                "12/12/2002", "14:20","am", "extra");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Too many command"));
    }
    @Test
    void airlineNamesDontMatchFileAndCommandLineArgument() throws FileNotFoundException {
        File testFile = new File("airtest3.txt");
        PrintWriter pw = new PrintWriter(testFile);
        pw.println("portland int");
        pw.println("2344 pdx 12/12/2022 12:12 am slc 12/12/2022 12:33 am");
        pw.close();
        MainMethodResult result = invokeMain("-textFile", "airtest3.txt","Salt Lake","345", "pdx", "12/12/2002",
                "12:13","am","slc","12/12/2002", "11:20","am");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Airline can not be updated"));
        pw.close();
        testFile.delete();
    }
    @Test
    void dumperCreatesFileCommandLineArgument() throws FileNotFoundException {
        File testFile = new File("testFile");
        assertFalse(testFile.exists());
        MainMethodResult result = invokeMain("-textFile", "testFile","Salt Lake","345", "pdx", "12/12/2002",
                "12:13","am","slc","12/12/2002", "11:20","am");
        assertThat(result.getExitCode(), equalTo(0));
        assertTrue(testFile.exists());
        testFile.delete();
    }
    @Test
    void dumperInvokedInMain() throws IOException {
        MainMethodResult result = invokeMain("-textFile", "airtest4.txt", "Salt Lake", "345", "pdx",
                "12/12/2002", "12:13","am", "slc", "12/12/2002", "11:20","am");
        File testFile = new File("airtest4.txt");
        BufferedReader br = new BufferedReader(new FileReader(testFile));
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(br.readLine(), containsString("Salt Lake"));
        br.close();
        testFile.deleteOnExit();
    }


}