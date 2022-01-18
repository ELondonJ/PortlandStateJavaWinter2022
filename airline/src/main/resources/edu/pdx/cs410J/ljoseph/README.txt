README

CS410J Project 1: Airline

Authors: London Joseph, David Whitlock
Version: 01.19.2022

Description:
This is the first project for Advanced Java Programing(CS410P) at Portland State University. Project 1
consists of three classes, Flight, Airline and Main.

The Main class reads arguments from the command-line, parses them and creates an airline and flight object with
    the arguments. Main will print the flight objects information with the -print flag. Main will print this readme.txt
    file if -readme flag is present.
The Flight class extends the AbstractFlight class contained in pdx.edu.cs410J.  A flight object is created with seven
    arguments: int flightNumber,String src,String depart, String dtime, String dest, String arrive, String atime.
    Flight has a toString method() that is called from Main and returns a String containing the flight information.
    The Flight class manages argument validation within it's methods and will throw exceptions to the calling routine
    in instances of illegal or improperly formatted arguments.
The Airline class extends the AbstractFlight contained in pdx.edu.cs410J.  An airline object is created with one
    argument: String name. Airline has a name and a list of Flight objects. Flights are added to the Airline with
    the addFlight() method and a String with a description of the flight is returned by the toSting() method.

usage: java edu.pdx.cs410J.<login-id>.Project1 [options] <args>
    args are (in this order):
    airline             The name of the airline
    flightNumber        The flight number
    src                 Three-letter code of departure airport
    depart              Departure date and time (24-hour time)
    dest                Three-letter code of arrival airport
    arrive              Arrival date and time (24-hour time)
    options are (options may appear in any order):
    -print              Prints a description of the new flight
    -README             Prints a README for this project
