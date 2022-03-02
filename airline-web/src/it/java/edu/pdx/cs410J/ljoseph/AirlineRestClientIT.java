package edu.pdx.cs410J.ljoseph;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.HttpURLConnection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Integration test that tests the REST calls made by {@link AirlineRestClient}
 */
@TestMethodOrder(MethodName.class)
class AirlineRestClientIT {
  private static final String HOSTNAME = "localhost";
  private static final String PORT = System.getProperty("http.port", "8080");

  private AirlineRestClient newAirlineRestClient() {
    int port = Integer.parseInt(PORT);
    return new AirlineRestClient(HOSTNAME, port);
  }

  private Flight createFlight(int num) {
    return new Flight(1234, "pdx", "12/12/2002", "12:13", "am", "slc",
            "12/12/2002", "1:20", "am");
  }

  @Test
  void test0RemoveAllDictionaryEntries() throws IOException {
    AirlineRestClient client = newAirlineRestClient();
    client.removeAllAirlineEntries();
  }

  @Test
  void test2DefineOneWord() throws IOException, ParserException, ParserConfigurationException {
    AirlineRestClient client = newAirlineRestClient();
    Flight flight = createFlight(1);
    String airlineName = "test";
    client.addFlight(airlineName, flight);

    Airline airline = client.getAirline(airlineName);
    assertThat(airline.getName(), equalToIgnoringCase(airlineName));
  }

  @Test
  void test4EmptyWordThrowsException() {
    AirlineRestClient client = newAirlineRestClient();
    String emptyString = "";
    Flight flight = createFlight(1);

    HttpRequestHelper.RestException ex =
            assertThrows(HttpRequestHelper.RestException.class, () -> client.addFlight(emptyString, flight));
    assertThat(ex.getHttpStatusCode(), equalTo(HttpURLConnection.HTTP_PRECON_FAILED));
    assertThat(ex.getMessage(), equalTo(Messages.missingRequiredParameter("airline")));
  }
  @Test
  void test5EmptyWordThrowsException() throws ParserException, IOException, ParserConfigurationException {
    AirlineRestClient client = newAirlineRestClient();
    String emptyString = "test";
    try {
      Airline airline = client.getAirline("test");
      if(airline == null){
        System.out.println("pass");
      }
    }catch(IOException e){
      System.out.println(e.getMessage());
    }catch(HttpRequestHelper.RestException e){

      System.out.println(e.getMessage());
    }

    //assertThat(ex.getHttpStatusCode(), equalTo(HttpURLConnection.HTTP_PRECON_FAILED));
    // assertThat(ex.getMessage(), equalTo(Messages.missingRequiredParameter("airline")));
  }
}