package edu.pdx.cs410J.ljoseph;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.UncaughtExceptionInMain;
import edu.pdx.cs410J.web.HttpRequestHelper.RestException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.net.HttpURLConnection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.MethodOrderer.MethodName;

/**
 * An integration test for {@link Project5} that invokes its main method with
 * various arguments
 */
@TestMethodOrder(MethodName.class)
class Project5IT extends InvokeMainTestCase {
    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "8080");

    @Test
    void test0RemoveAllMappings() throws IOException {
        AirlineRestClient client = new AirlineRestClient(HOSTNAME, Integer.parseInt(PORT));
        client.removeAllAirlineEntries();
    }

    @Test
    void test1NoCommandLineArguments() {
        MainMethodResult result = invokeMain( Project5.class );
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project5.MISSING_ARGS));
    }

    @Test
    void test2EmptyServer() {
        MainMethodResult result = invokeMain( Project5.class,"-host", HOSTNAME, "-port", PORT );
        assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(1));
        String out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString(""));
    }
    @Test
    void test3AddToAirline() {
        MainMethodResult result = invokeMain( Project5.class,"-host", HOSTNAME, "-port", PORT,"test", "123"," PDX",
                        "07/19/2022","1:02","pm","ORD","07/19/2022","6:22","pm" );
        assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(0));
        String out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString(""));
    }
    @Test
    void test4BadFlightArgsThrowsExecption() {
        MainMethodResult result = invokeMain( Project5.class,"-host", HOSTNAME, "-port", PORT,"test", "123"," PDX",
                "07/19/2022","1:02","pm","LLL","07/19/2022","6:22","pm" );
        assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(1));
        String out = result.getTextWrittenToStandardError();
        assertThat(out, out, containsString("usage"));
    }
    @Test
    void test5SearchFlagsNoArgs() {
        MainMethodResult result = invokeMain( Project5.class,"-host", HOSTNAME, "-port", PORT, "-search");
        assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(1));
        String out = result.getTextWrittenToStandardError();
        assertThat(out, out, containsString("Error"));
    }
    @Test
    void test6HostFlagsNoArgs() {
        MainMethodResult result = invokeMain( Project5.class,"-host");
        assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(1));
        String out = result.getTextWrittenToStandardError();
        assertThat(out, out, containsString("Error"));
    }
    @Test
    void test7PortFlagsNoArgs() {
        MainMethodResult result = invokeMain( Project5.class,"-port");
        assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(1));
        String out = result.getTextWrittenToStandardError();
        assertThat(out, out, containsString("Error"));
    }
    @Test
    void test7UnknownFlag() {
        MainMethodResult result = invokeMain( Project5.class,"-fred");
        assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(1));
        String out = result.getTextWrittenToStandardError();
        assertThat(out, out, containsString("Error"));
    }
    @Test
    void test8TooManyArgsError() {
        MainMethodResult result = invokeMain( Project5.class,"-host", HOSTNAME, "-port", PORT,"test", "123"," PDX",
                "07/19/2022","1:02","pm","LLL","07/19/2022","6:22","pm", "fred" );
        assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(1));
        String out = result.getTextWrittenToStandardError();
        assertThat(out, out, containsString("Extraneous"));
    }
    @Test
    void test9SearchFlagsInvalidAirportCode() {
        MainMethodResult result = invokeMain( Project5.class,"-host", HOSTNAME, "-port", PORT, "-search","delta"
        ,"LLL", "slc");
        assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(1));
        String out = result.getTextWrittenToStandardError();
        assertThat(out, out, containsString("usage"));
    }
    @Test
    void test10SearchAirportNotOnServer() {
        MainMethodResult result = invokeMain( Project5.class,"-host", HOSTNAME, "-port", PORT, "-search","delta"
                ,"pdx", "slc");
        assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(0));
        String out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString("server"));
    }
    @Test
    void test11AirportNotOnServer() {
        MainMethodResult result = invokeMain( Project5.class,"-host", HOSTNAME, "-port", PORT, "test");
        assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(0));
        String out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString("server"));
    }
    @Test
    void test12PortNoHost() {
        MainMethodResult result = invokeMain( Project5.class,"-port",PORT);
        assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(1));
        String out = result.getTextWrittenToStandardError();
        assertThat(out, out, containsString("usage"));
    }
    @Test
    void test13HostNoPort() {
        MainMethodResult result = invokeMain( Project5.class,"-host",HOSTNAME);
        assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(1));
        String out = result.getTextWrittenToStandardError();
        assertThat(out, out, containsString("usage"));
    }
    @Test
    void test14PortNotInteger() {
        MainMethodResult result = invokeMain( Project5.class,"-host",HOSTNAME, "-port", "abc");
        assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(1));
        String out = result.getTextWrittenToStandardError();
        assertThat(out, out, containsString("usage"));
    }
    @Test
    void test15ReadMe() {
        MainMethodResult result = invokeMain( Project5.class,"-host",HOSTNAME, "-port", PORT,"-readme");
        assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(0));
        String out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString("README"));
    }
    @Test
    void test16PrintFlagPrintsFlight() {
        MainMethodResult result = invokeMain( Project5.class,"-host", HOSTNAME, "-port", PORT, "-print","test",
                "123"," PDX", "07/19/2022","1:02","pm","SLC","07/19/2022","6:22","pm");
        assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(0));
        String out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString("flight 123 departs"));
    }
    @Test
    void test17PortConnectionFail() {
        MainMethodResult result = invokeMain( Project5.class,"-host", HOSTNAME, "-port", "1234", "-print","test",
                "123"," PDX", "07/19/2022","1:02","pm","SLC","07/19/2022","6:22","pm");
        assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(1));
        String out = result.getTextWrittenToStandardError();
        assertThat(out, out, containsString("Connection refused"));
    }

    @Test
    @Disabled
    void test3threeArgsPrintsAirline() {
        String word = "test";
        try {
            invokeMain(Project5.class,"-host", HOSTNAME,"-port", PORT, word);
            fail("Should have thrown a RestException");

        } catch (UncaughtExceptionInMain ex) {
            RestException cause = (RestException) ex.getCause();
            assertThat(cause.getHttpStatusCode(), equalTo(HttpURLConnection.HTTP_NOT_FOUND));
        }
    }

    @Test
    @Disabled
    void test4AddDefinition() {
        String word = "WORD";
        String definition = "DEFINITION";

        MainMethodResult result = invokeMain( Project5.class, HOSTNAME, PORT, word, definition );
        assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(0));
        String out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString(Messages.definedWordAs(word, definition)));

        result = invokeMain( Project5.class, HOSTNAME, PORT, word );
        out = result.getTextWrittenToStandardOut();
        //  assertThat(out, out, containsString(PrettyPrinter.formatDictionaryEntry(word, definition)));

        result = invokeMain( Project5.class, HOSTNAME, PORT );
        out = result.getTextWrittenToStandardOut();
        //  assertThat(out, out, containsString(PrettyPrinter.formatDictionaryEntry(word, definition)));
    }
}