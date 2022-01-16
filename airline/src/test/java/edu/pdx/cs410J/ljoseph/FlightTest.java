package edu.pdx.cs410J.ljoseph;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

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
    Flight flight = new Flight(42,null , "02/12/1989", null,null ,"02/12/1989", null);
    assertThat(flight.getNumber(), equalTo(42));
  }
  @Test
  void flightNumberCanNotBeNegative(){
    try{
      Flight flight = new Flight(-1,null ,null, null,null ,null, null);
      fail("Should have caught an illegal argument expression");
    }catch(IllegalArgumentException ex){

    }
  }
  @Test
  void getArrivalStringNeedsToBeImplemented() {
    Flight flight = new Flight(42,  null ,"01/24/1989","09:10",null ,"01/24/1989","09:10");
    assertThat(String.valueOf(flight.getArrivalString().equals(("10/24/1989 09:10"))),true);
  }
  @Test
  void getDepartureStringNeedsToBeImplemented() {
    Flight flight = new Flight(42, null , "01/24/1989","09:10",null ,"01/24/1989","09:10");
    assertThat(String.valueOf(flight.getDepartureString().equals(("10/24/1989 09:10"))),true);
  }
  @Test
  void invalidDateThrowsException(){
    try {
      Flight flight = new Flight(42,null ,  "02/30/1989", "09:10","01/24/1989", null ,"09:10");
      fail("Illegal Argument Exception should have been thrown");
    } catch(IllegalArgumentException ex){

    }
  }
  @Test
  void incorrectFormatOfDateBackslashThrowsException(){
    try {
      Flight flight = new Flight(42,null ,  "01-24-1989", "09:10",null ,"01-24-1989", "09:10");
      fail("Illegal Argument Exception should have been thrown");
    } catch(IllegalArgumentException ex){

      }
  }
  @Test
  void numRepresentationOfDateCreated(){
    Flight flight = new Flight(42,null , "12/24/1989", "09:10",null , "12/24/1989", "09:10");
    int[] array = new int[]{1,2,-1,2,4,-1,1,9,8,9};
    int[] check = flight.getIntRepArray("12/24/1989");
    assertThat(String.valueOf(Arrays.equals(array,check)), true);

  }
  @Test
  void incorrectFormatDateIllegalCharactersThrowsException(){
    try {
      Flight flight = new Flight(42,null ,  "aa/01/1998", "09:10",null ,"aa/01/1998", "09:10");
      fail("Illegal Argument Exception should have been thrown");
    } catch(IllegalArgumentException ex){

    }
  }
  @Test
  void incorrectFormatDateTooLongOrShortThrowsException(){
    try {
      Flight flight = new Flight(42,null ,  "12/01/199", "09:10",null ,"12/01/199", "09:10");
      fail("Illegal Argument Exception should have been thrown");
    } catch(IllegalArgumentException ex){

    }
  }
  @Test
  void incorrectFormatOfDateNumericValuesThrowsException(){
    try {
      Flight flight = new Flight(42,null ,  "12/34/1989", "09:10",null ,"12/34/1989", "09:10");
      fail("Illegal Argument Exception should have been thrown");
    } catch(IllegalArgumentException ex){

    }
  }

  @Test
  void incorrectFormatOfTimeWrongSymbol() {
    try {
      Flight flight = new Flight(42,null ,  "12/01/1989", "09/10",null ,"12/01/1989", "09/10");
      fail("Illegal Argument Exception should have been thrown");
    } catch (IllegalArgumentException ex) {

    }
  }
    @Test
    void incorrectFormatOfNumbersOutOfBounds() {
      try {
        Flight flight = new Flight(42,null ,  "12/01/1989", "aa:10",null ,"12/01/1989", "aa:10");
        fail("Illegal Argument Exception should have been thrown");
      } catch (IllegalArgumentException ex) {
      }
    }
    @Test
    void implementGetSource() {
      Flight flight = new Flight(42,"pdx" ,  "12/01/1989", "12:10",null ,"12/01/1989", "12:10");
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
      Flight flight = new Flight(42,"pdxx" ,  "12/01/1989", "12:10","124" ,"12/01/1989", "12:10");
      fail("Illegal Argument Exception should have been thrown");
    } catch (IllegalArgumentException ex) {
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
