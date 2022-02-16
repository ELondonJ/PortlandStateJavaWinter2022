package edu.pdx.cs410J.ljoseph;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ConverterTest extends InvokeMainTestCase {

    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Converter.class, args );
    }
    @Test
    void testNoCommandLineArguments() {
        MainMethodResult result = invokeMain("1", "2","3");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid"));
    }
    @Test
    void xmlFileCreatedFromTextFile() throws FileNotFoundException {
        File testFile = new File("test.txt");
        PrintWriter pw = new PrintWriter(testFile);
        pw.println("delta");
        pw.println("2344 pdx 12/12/2022 12:12 am slc 12/12/2022 12:33 am");
        pw.close();
        MainMethodResult result = invokeMain("test.txt", "xmltest.txt");
        assertThat(result.getExitCode(), equalTo(0));
        File xml = new File("xmltest.txt");
        assertThat(String.valueOf(xml.exists()), true);
        pw.close();
        testFile.delete();
    }
    @Test
    void incorrectFormatTextFile() throws FileNotFoundException {
        File testFile = new File("test.txt");
        PrintWriter pw = new PrintWriter(testFile);
        pw.println("delta");
        pw.println("2344 pdx 12/12/2022 12:12 slc 12/12/2022 12:33 am");
        pw.close();
        MainMethodResult result = invokeMain("test.txt", "xmltest.txt");
        assertThat(result.getExitCode(), equalTo(1));
        File xml = new File("xmltest.txt");
        assertThat(result.getTextWrittenToStandardError(), containsString("Incorrect"));
        pw.close();
        testFile.delete();
    }
    @Test
    void textFileDoesNotExist() throws FileNotFoundException {
        MainMethodResult result = invokeMain("test.txt", "xmltest.txt");
        assertThat(result.getExitCode(), equalTo(1));
        File xml = new File("xmltest.txt");
        assertThat(result.getTextWrittenToStandardError(), containsString("cannot find the file"));
        System.out.println(result.getTextWrittenToStandardError());

    }
    @Test
    void incorrectXmlFileFormat() throws FileNotFoundException {
        File testFile = new File("test.txt");
        PrintWriter pw = new PrintWriter(testFile);
        pw.println("delta");
        pw.println("2344 pdx 12/12/2022 12:12 slc 12/12/2022 12:33 am");
        pw.close();
        MainMethodResult result = invokeMain("test.txt", "xmltest.txt");
        assertThat(result.getExitCode(), equalTo(1));
        File xml = new File("xmltest.txt");
        assertThat(result.getTextWrittenToStandardError(), containsString("Incorrect"));
        pw.close();
        testFile.delete();
    }

}