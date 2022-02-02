README

CS410J Project 2: Airline

Authors: London Joseph, David Whitlock
Version: 01.19.2022

Description:
This is the second project for Advanced Java Programing(CS410P) at Portland State University. Project 2 builds
from Project 1 and consists of five classes, Main, Flight, Airline, TextDumper and TextParser.

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
The TextDumper class extends the AbstractTextDumper contained in pdx.edu.cs410J.  A TextDumper writes the contents of
    airline objects out to a specified text file and is created with one argument: Writer writer. Writer is a FileWriter
    that is created in Main with the specified File to be written.  TexDumper has one method dump() which takes an
    airline object and writes airline name and flights out to a text file.  The format of the file created is as such:
    airline name is the first line of the file and each subsequent line represents one flight belonging to that airline,
    with each argument for the flight seperated by white space. Only one airline is represented in a text file.
The TextParser class extends the AbstractTextParser class contained in pdx.edu.cs410J. A TextParser reads from a text
    file, parses its contents and returns an airline object representation of the file. TextParser is created with one
    argument:Reader reader. Reader is a FileReader that is created in Main with the specified File to be read.
    A TextParser has one method parse() which reads the first line of the file and creates a new airline with the name
    and then reads each subsequent line, creating a flight from it contents and adding each flight to the airline. The
    airline object is then returned to calling routine once the end of the file is reached. TextParser requires the same
    text file format described in the TextDumper class.

usage: java edu.pdx.cs410J.<login-id>.Project1 [options] <args>
    args are (in this order):
    airline             The name of the airline
    flightNumber        The flight number
    src                 Three-letter code of departure airport
    depart              Departure date and time (am/pm)
    dest                Three-letter code of arrival airport
    arrive              Arrival date and time (am/pm)
    options are (options may appear in any order):
    -pretty file        Pretty print the airline’s flights to
                        a text file or standard out (file -)
    -textFile file      Where to read/write the airline info
    -print              Prints a description of the new flight
    -README             Prints a README for this project
    (departure/arrive should be in format: 01/02/2022 9:16 pm)
