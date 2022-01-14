package edu.pdx.cs410J.ljoseph;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Flight} class.
 *
 * You'll need to update these unit tests as you build out you program.
 */
public class FlightTest {

  /**
   * This unit test will need to be modified (likely deleted) as you implement
   * your project.
   */
  @Test
  void getNumberNeedsToBeImplemented(){
    Flight flight = new Flight(42, "02/12/1989", null);
    assertThat(flight.getNumber(), equalTo(42));
  }
  @Test
  void flightNumberCanNotBeNegative(){
    try{
      Flight flight = new Flight(-1,null, null);
      fail("Should have caught an illegal argument expression");
    }catch(IllegalArgumentException ex){

    }
  }
  @Test
  void getArrivalStringNeedsToBeImplemented() {
    Flight flight = new Flight(42, "01/24/1989","09:10");
    assertThat(String.valueOf(flight.getArrivalString().equals(("10/24/1989 09:10"))),true);
  }
  @Test
  void incorrectFormatOfDateBackslashThrowsNotUsingException(){
    try {
      Flight flight = new Flight(42, "01-24-1989", "09:10");
      fail("Illegal Argument Exception should have been thrown");
    } catch(IllegalArgumentException ex){

      }
  }
 /*
  @Test
  void incorrectFormatOfDateNumericValuesThrowsNotUsingException(){
    try {
      Flight flight = new Flight(42, "24/24/1989", "09:10");
      fail("Illegal Argument Exception should have been thrown");
    } catch(IllegalArgumentException ex){

    }
  }


 /*
  @Test
  @Disabled
  void getArrivalStringNeedsToBeImplemented() {
    Flight flight = new Flight(42);
    assertThrows(UnsupportedOperationException.class, flight::getArrivalString);
  }

  /**
   * This unit test will need to be modified (likely deleted) as you implement
   * your project.
   */
/*
 @Test
  void initiallyAllFlightsHaveTheSameNumber() {
    Flight flight = new Flight(42);
    assertThat(flight.getNumber(), equalTo(42));
  }

  @Test
  @Disabled
  void forProject1ItIsOkayIfGetDepartureTimeReturnsNull() {
    Flight flight = new Flight(0);
    assertThat(flight.getDeparture(), is(nullValue()));
  }

 */

}
