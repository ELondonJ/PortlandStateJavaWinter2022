package edu.pdx.cs410J.ljoseph;

import edu.pdx.cs410J.AirlineDumper;
import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

/**
 * Converter converts a textfile of airline information into a xml file
 * of the airline information
 */
public class Converter {

    public static final String USAGE = "usage: java edu.pdx.cs410J.<login-id>.Converter textFile xmlFile";

    public static void main(String args[]){

        if(args.length != 2){
            System.err.println("Invalid number of arguments");
            System.err.println(USAGE);
            System.exit(1);
        }
        String textfile = args[0];
        String xmlfile = args[1];
        Airline airline;
        XmlDumper dumper;
        TextParser parser;
        File textFile;
        textFile = new File(textfile);
            try {
                parser = new TextParser(new FileReader(textFile));
                airline = parser.parse();

                //write Airline to file with textDumper object
                dumper  = new XmlDumper(xmlfile);
                dumper.dump(airline);
            } catch (ParserException | ParserConfigurationException e) {
                System.err.println(e.getMessage());
                System.exit(1);
            } catch (FileNotFoundException e) {
                System.err.println(e.getMessage());
                System.exit(1);
            } catch (IOException e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
            System.exit(0);
    }
}
