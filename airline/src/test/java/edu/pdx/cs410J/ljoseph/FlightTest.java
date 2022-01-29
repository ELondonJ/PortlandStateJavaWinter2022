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
    Flight flight = new Flight(42,"slc" , "02/12/1989", "09:10","am","slc" ,"02/12/1989",
            "09:10","am");
    assertThat(flight.getNumber(), equalTo(42));
  }
  @Test
  void flightNumberCanNotBeNegative(){
    try{
      Flight flight = new Flight(-1,"slc" ,"02/12/2002", "09:10","am","pdx" ,"02/12/2002",
              "09:10","am");
      fail("Should have caught an illegal argument expression");
    }catch(IllegalArgumentException ex){
      //pass
    }
  }
  @Test
  void getArrivalStringNeedsToBeImplemented() {
    Flight flight = new Flight(42,  "slc" ,"01/24/1989","09:10","am","slc" ,"01/24/1989",
            "09:10","am");
    assertThat(String.valueOf(flight.getArrivalString().equals(("10/24/1989 09:10"))),true);
  }
  @Test
  void getDepartureStringNeedsToBeImplemented() {
    Flight flight = new Flight(42, "slc" , "01/24/1989","09:10","am","slc" ,"01/24/1989",
            "09:10","am");
    assertThat(String.valueOf(flight.getDepartureString().equals(("10/24/1989 09:10"))),true);
  }
  @Test
  void invalidDateThrowsException() {
    try {
      Flight flight = new Flight(42, "slc", "02/30/1989", "09:10","am", "01/24/1989", "slc",
              "09:10","am");
      fail("Illegal Argument Exception should have been thrown");
    } catch (IllegalArgumentException ex) {
      //pass
    }

  }
  @Test
  void incorrectFormatOfDateBackslashThrowsException(){
    try {
      Flight flight = new Flight(42,"slc" ,  "01-24-1989", "09:10","am","slc" ,"01-24-1989",
              "09:10","am");
      fail("Illegal Argument Exception should have been thrown");
    } catch(IllegalArgumentException ex){
      //pass
      }
  }
  @Test
  void incorrectFormatDateIllegalCharactersThrowsException(){
    try {
      Flight flight = new Flight(42,"slc" ,  "aa/01/1998", "09:10","am","slc" ,"aa/01/1998",
              "09:10","am");
      fail("Illegal Argument Exception should have been thrown");
    } catch(IllegalArgumentException ex){
      //pass
    }
  }
  @Test
  void incorrectFormatDateTooLongOrShortThrowsException(){
    try {
      Flight flight = new Flight(42,"slc" ,  "12/01/19999", "09:10","am","slc" ,"12/01/199",
              "09:10","am");
      System.out.println(flight.toString());
      fail("Illegal Argument Exception should have been thrown");
    } catch(IllegalArgumentException ex){
      //pass
    }
  }
  @Test
  void incorrectFormatOfDateNumericValuesThrowsException(){
    try {
      Flight flight = new Flight(42,"pdx" ,  "12/34/1989", "09:10","am","slc" ,"12/34/1989",
              "09:10","am");
      fail("Illegal Argument Exception should have been thrown");
    } catch(IllegalArgumentException ex){
      //pass
    }
  }

  @Test
  void invalidInputForTimeNegatives() {
    try {
      Flight flight = new Flight(42,"slc" ,  "12/01/1989", "-10:30","am","slc" ,"12/01/1989",
              "09/10","am");
      fail("Illegal Argument Exception should have been thrown");
    } catch (IllegalArgumentException ex) {
      //pass
    }
  }
  @Test
  void incorrectFormatOfTimeWrongSymbol() {
    try {
      Flight flight = new Flight(42,"slc" ,  "12/01/1989", "09/10","am","slc" ,"12/01/1989",
              "09/10","am");
      fail("Illegal Argument Exception should have been thrown");
    } catch (IllegalArgumentException ex) {
      //pass
    }
  }
    @Test
    void incorrectFormatOfNumbersOutOfBounds() {
      try {
        Flight flight = new Flight(42,"slc" ,  "12/01/1989", "aa:10","am","slc" ,"12/01/1989",
                "aa:10","am");
        fail("Illegal Argument Exception should have been thrown");
      } catch (IllegalArgumentException ex) {
        //pass
      }
    }
    @Test
    void implementGetSource() {
      Flight flight = new Flight(42,"pdx" ,  "12/01/1989", "12:10","am","slc" ,"12/01/1989",
              "12:10","am");
      String src = flight.getSource();
      assertEquals(src,"PDX");
  }
  @Test
  void implementGetDestination() {
    Flight flight = new Flight(42, "pdx", "12/01/1989", "12:10","am", "pdx", "12/01/1989",
            "12:10","am");
    String dest = flight.getDestination();
    assertEquals(dest, "PDX");
  }
  @Test
  void validDestinationIsThreeLetters() {
    try {
      Flight flight = new Flight(42,"pdx" ,  "12/01/1989", "12:10","am","124" ,"12/01/1989",
              "12:10","am");
      fail("Illegal Argument Exception should have been thrown");
    } catch (IllegalArgumentException ex) {
      //pass
    }
  }
  @Test
  void validDestinationIsValidAirport() {
    try {
      Flight flight = new Flight(42,"pdx" ,  "12/01/1989", "12:10","am","LLL" ,"12/01/1989",
              "12:10","am");
      fail("Illegal Argument Exception should have been thrown");
    } catch (IllegalArgumentException ex) {
      //pass
    }
  }
  @Test
  void validSourceIsValidAirport() {
    try {
      Flight flight = new Flight(42,"lll" ,  "12/01/1989", "12:10","am","pdx" ,"12/01/1989",
              "12:10","am");
      fail("Illegal Argument Exception should have been thrown");
    } catch (IllegalArgumentException ex) {
      //pass
    }
  }
  @Test
  void implementAirlineAddFlight() {
      Flight flight = new Flight(42,"pdx" ,  "12/01/1989", "12:10","am","pdx" ,"12/01/1989",
              "12:10","am");
      Airline airline = new Airline("portland");
      airline.addFlight(flight);
      assertEquals(airline.getName(),  "portland");
  }
  @Test
  void implementAirlineGetFlight() {
    Flight flight = new Flight(42,"pdx" ,  "12/01/1989", "12:10","am","pdx" ,"12/01/1989",
            "12:10","am");
    Airline airline = new Airline("portland");
    airline.addFlight(flight);
    Collection<Flight> flights = airline.getFlights();
    assertEquals(flights, airline.getFlights());
  }
  @Test
  void implementAirlineGetFlightCorrectInfo() {
    Flight flight = new Flight(42,"pdx" ,  "12/01/1989", "12:10","am","pdx" ,"12/01/1989",
            "12:10","am");
    Airline airline = new Airline("portland");
    airline.addFlight(flight);
    Collection<Flight> flights = airline.getFlights();
    assertEquals(flights.size(), 1);
  }

}
