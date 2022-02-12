package edu.pdx.cs410J.ljoseph;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class XmlParserTest {

    @Test
    void canParseValidXmlFile() throws ParserConfigurationException, IOException, SAXException, ParserException {
        try {

            XmlParser p = new XmlParser("newTest.xml");
            p.parse();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

}