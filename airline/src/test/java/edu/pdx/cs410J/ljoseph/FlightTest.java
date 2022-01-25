package edu.pdx.cs410J.ljoseph;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;

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
    Flight flight = new Flight(42,"slc" , "02/12/1989", "09:10","slc" ,"02/12/1989", "09:10");
    assertThat(flight.getNumber(), equalTo(42));
  }
  @Test
  void flightNumberCanNotBeNegative(){
    try{
      Flight flight = new Flight(-1,"slc" ,"02/12/2002", "09:10","pdx" ,"02/12/2002", "09:10");
      fail("Should have caught an illegal argument expression");
    }catch(IllegalArgumentException ex){
      //pass
    }
  }
  @Test
  void getArrivalStringNeedsToBeImplemented() {
    Flight flight = new Flight(42,  "slc" ,"01/24/1989","09:10","slc" ,"01/24/1989","09:10");
    assertThat(String.valueOf(flight.getArrivalString().equals(("10/24/1989 09:10"))),true);
  }
  @Test
  void getDepartureStringNeedsToBeImplemented() {
    Flight flight = new Flight(42, "slc" , "01/24/1989","09:10","slc" ,"01/24/1989","09:10");
    assertThat(String.valueOf(flight.getDepartureString().equals(("10/24/1989 09:10"))),true);
  }
  @Test
  void invalidDateThrowsException() {
    try {
      Flight flight = new Flight(42, "slc", "02/30/1989", "09:10", "01/24/1989", "slc", "09:10");
      fail("Illegal Argument Exception should have been thrown");
    } catch (IllegalArgumentException ex) {
      //pass
    }

  }
  @Test
  void incorrectFormatOfDateBackslashThrowsException(){
    try {
      Flight flight = new Flight(42,"slc" ,  "01-24-1989", "09:10","slc" ,"01-24-1989", "09:10");
      fail("Illegal Argument Exception should have been thrown");
    } catch(IllegalArgumentException ex){
      //pass
      }
  }
  @Test
  @Disabled
  void numRepresentationOfDateCreated(){
    Flight flight = new Flight(42,"slc" , "12/24/1989", "09:10","slc" , "12/24/1989", "09:10");
    int[] array = new int[]{1,2,-1,2,4,-1,1,9,8,9};
    int[] check = flight.getIntRepArray("12/24/1989");
    assertThat(String.valueOf(Arrays.equals(array,check)), true);
  }
  @Test
  void incorrectFormatDateIllegalCharactersThrowsException(){
    try {
      Flight flight = new Flight(42,"slc" ,  "aa/01/1998", "09:10","slc" ,"aa/01/1998", "09:10");
      fail("Illegal Argument Exception should have been thrown");
    } catch(IllegalArgumentException ex){
      //pass
    }
  }
  @Test
  void incorrectFormatDateTooLongOrShortThrowsException(){
    try {
      Flight flight = new Flight(42,"slc" ,  "12/01/199", "09:10","slc" ,"12/01/199", "09:10");
      fail("Illegal Argument Exception should have been thrown");
    } catch(IllegalArgumentException ex){
      //pass
    }
  }
  @Test
  void incorrectFormatOfDateNumericValuesThrowsException(){
    try {
      Flight flight = new Flight(42,"pdx" ,  "12/34/1989", "09:10","slc" ,"12/34/1989", "09:10");
      fail("Illegal Argument Exception should have been thrown");
    } catch(IllegalArgumentException ex){
      //pass
    }
  }

  @Test
  void invalidInputForTimeNegatives() {
    try {
      Flight flight = new Flight(42,"slc" ,  "12/01/1989", "-10:30","slc" ,"12/01/1989", "09/10");
      fail("Illegal Argument Exception should have been thrown");
    } catch (IllegalArgumentException ex) {
      //pass
    }
  }
  @Test
  void incorrectFormatOfTimeWrongSymbol() {
    try {
      Flight flight = new Flight(42,"slc" ,  "12/01/1989", "09/10","slc" ,"12/01/1989", "09/10");
      fail("Illegal Argument Exception should have been thrown");
    } catch (IllegalArgumentException ex) {
      //pass
    }
  }
    @Test
    void incorrectFormatOfNumbersOutOfBounds() {
      try {
        Flight flight = new Flight(42,"slc" ,  "12/01/1989", "aa:10","slc" ,"12/01/1989", "aa:10");
        fail("Illegal Argument Exception should have been thrown");
      } catch (IllegalArgumentException ex) {
        //pass
      }
    }
    @Test
    void implementGetSource() {
      Flight flight = new Flight(42,"pdx" ,  "12/01/1989", "12:10","slc" ,"12/01/1989", "12:10");
      String src = flight.getSource();
      assertEquals(src,"pdx");
  }
  @Test
  void implementGetDestination() {
    Flight flight = new Flight(42, "pdx", "12/01/1989", "12:10", "pdx", "12/01/1989", "12:10");
    String dest = flight.getDestination();
    assertEquals(dest, "pdx");
  }
  @Test
  void validDestinationIsThreeLetters() {
    try {
      Flight flight = new Flight(42,"pdx" ,  "12/01/1989", "12:10","124" ,"12/01/1989", "12:10");
      fail("Illegal Argument Exception should have been thrown");
    } catch (IllegalArgumentException ex) {
      //pass
    }
  }
  @Test
  void implementAirlineAddFlight() {
      Flight flight = new Flight(42,"pdx" ,  "12/01/1989", "12:10","pdx" ,"12/01/1989", "12:10");
      Airline airline = new Airline("portland");
      airline.addFlight(flight);
      assertEquals(airline.getName(),  "portland");
  }
  @Test
  void implementAirlineGetFlight() {
    Flight flight = new Flight(42,"pdx" ,  "12/01/1989", "12:10","pdx" ,"12/01/1989", "12:10");
    Airline airline = new Airline("portland");
    airline.addFlight(flight);
    Collection<Flight> flights = airline.getFlights();
    assertEquals(flights, airline.getFlights());
  }
  @Test
  void implementAirlineGetFlightCorrectInfo() {
    Flight flight = new Flight(42,"pdx" ,  "12/01/1989", "12:10","pdx" ,"12/01/1989", "12:10");
    Airline airline = new Airline("portland");
    airline.addFlight(flight);
    Collection<Flight> flights = airline.getFlights();
    assertEquals(flights.size(), 1);
  }

}
