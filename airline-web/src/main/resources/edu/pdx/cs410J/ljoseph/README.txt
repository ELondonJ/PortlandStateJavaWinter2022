README

CS410J Project 5: Airline

Authors: London Joseph, David Whitlock
Version: 02.16.2022

Description:
This is the fourth project for Advanced Java Programing(CS410P) at Portland State University. Project 5 builds
from Project 4 and consists of the following classes:

The Main class reads arguments from the command-line, parses them and creates an airline and flight object with
    the arguments. Main will print the flight objects information with the -print flag. Main will print this readme.txt
    file if -readme flag is present. Main will read/write from a file if -textFile flag is present.
The Flight class extends the AbstractFlight class contained in pdx.edu.cs410J.  A flight object is created with seven
    arguments: int flightNumber,String src,String depart, String dtime, String dest, String arrive, String atime.
    Flight has a toString method() that is called from Main and returns a String containing the flight information.
    The Flight class manages argument validation within it's methods and will throw exceptions to the calling routine
    in instances of illegal or improperly formatted arguments.
The Airline class extends the AbstractFlight contained in pdx.edu.cs410J.  An airline object is created with one
    argument: String name. Airline has a name and a list of Flight objects. Flights are added to the Airline with
    the addFlight() method and a String with a description of the flight is returned by the toSting() method.
The PrettyPrint class extends the AbstractTextDumper contained in pdx.edu.cs410J.  A Pretty print has the same
    constructor args and public methods as TextDumper but PrettyPrint prints a table version of an airline object.
The XmlDumper class implements the AirlineDumper interface. XmlDumper dumps a representation of an airline to an XML
    file that conforms to the given DTD.
The XmlParser class implements the AirlineParser interface. XmlParser reads the contents of an XML file and creates an
    Airline from its contents.
The Airline Servlet provides a REST api for working with airlines.
The AirlineRestClient A helper class for accessing the rest client.  Note that this class provides

usage: java edu.pdx.cs410J.<login-id>.Project1 [options] <args>
    args are (in this order):
    airline             The name of the airline
    flightNumber        The flight number
    src                 Three-letter code of departure airport
    depart              Departure date and time (am/pm)
    dest                Three-letter code of arrival airport
    arrive              Arrival date and time (am/pm)
    options are (options may appear in any order):
     -host hostname     Host computer on which the server runs
     -port portPort     on which the server is listening
     -search            Search for flights
     -print             Prints a description of the new flight
     -README            Prints a README for this project and exits

    The -host and -post flags are required.
    (departure/arrive should be in format: 01/02/2022 9:16 pm)
