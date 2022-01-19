package edu.pdx.cs410J.ljoseph;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * An integration test for the {@link Project1} main class.
 */
class Project1IT extends InvokeMainTestCase {

    public static final String STRINGS = "-print Portland International 1234 pdx 12/12/2002 12:13 slc 12/12/2002 14:20";

    /**
     * Invokes the main method of {@link Project1} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project1.class, args );
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
        MainMethodResult result = invokeMain("-print","Portland International","1234", "pdx", "12/12/2002","12:13","slc","12/12/2002", "14:20");
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), containsString("Flight 1234 departs pdx at 12/12/2002 12:13 arrives slc at 12/12/2002 14:20"));
    }


    @Test
    void testIncorrectCommandLineArgumentsThrowsError() {
        MainMethodResult result = invokeMain("Portland International","1234", "pdxx", "12/12/2002","12:13","slc","12/12/2002", "14:20");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Source and Destination represented by three-letter code. ie pdx"));
    }
    @Test
    void testIncorrectFlightNumberFormatArgumentsThrowsError() {
        MainMethodResult result = invokeMain("Portland International","aba", "pdx", "12/12/2002","12:13","slc","12/12/2002", "14:20");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Incorrect flight number format"));
    }
    @Test
    void testReadMeCommandLineArgument() {
        MainMethodResult result = invokeMain("-readme");
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), containsString("410J Project 1: Airline"));
    }

}