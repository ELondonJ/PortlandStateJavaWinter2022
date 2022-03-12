package edu.pdx.cs410j.ljoseph;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

import edu.pdx.cs410J.ParserException;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_FLIGHT_CODE = 88;
    public static final String AIRLINE_MAIN = "AIRLINE_MAIN";
    private ArrayAdapter<Airline> airlinesList;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File file = new File(getFilesDir(), "AirlinesFile.txt");
        try(BufferedReader bf = new BufferedReader(new FileReader(file))){
            TextParser parser = new TextParser(bf);
            ArrayList<Airline> airlines = parser.parseMultiple();
            airlinesList = new ArrayAdapter<>(this, android.R.layout.simple_selectable_list_item);
            airlinesList.addAll(airlines);
            listView = findViewById(R.id.airlineList);
            listView.setAdapter(airlinesList);
        }catch(IOException e){
            String mess = e.getMessage();

        }catch (ParserException | IllegalArgumentException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
            airlinesList = new ArrayAdapter<>(this, android.R.layout.simple_selectable_list_item);
        }



    }


    public void launchViewAirline(View view) {
        Intent intent = new Intent(MainActivity.this,FindAirline.class);
        startActivity(intent);
    }

    public void launchFindFlights(View view) {
        Airline airline = null;
        String airlineName = ((EditText)findViewById(R.id.airlineSearch)).getText().toString();
        String src = ((EditText)findViewById(R.id.srcSearch)).getText().toString();
        String dest = ((EditText)findViewById(R.id.destSearch)).getText().toString();
        if(src != null && dest != null && airlineName != null && !src.equals("")
                && dest != "" && airlineName != ""){
          for(int i = 0; i < airlinesList.getCount() && airline == null; i++){
              if(airlinesList.getItem(i).getName().equalsIgnoreCase(airlineName)){
                  airline = airlinesList.getItem(i);
                  findFlights(airline,src,dest);
                  return;
              }
          }
          if(airline == null)
            Toast.makeText(this, "No airline named " + airlineName,Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this, "All search fields are required",Toast.LENGTH_LONG).show();

    }

    private void findFlights(Airline airline, String src, String dest) {

        Airline temp = new Airline(airline.getName());
        for(Flight flight: airline.getFlights()){
            if(flight.getSource().equalsIgnoreCase(src) &&
                    flight.getDestination().equalsIgnoreCase(dest)){
                temp.addFlight(flight);
            }
        }
        if(temp.getFlightCount() != 0){
            Intent intent = new Intent(MainActivity.this,PrintAirline.class);
            intent.putExtra(AIRLINE_MAIN,temp);
            startActivity(intent);
        }
        else
            Toast.makeText(this, "No direct flights from " + src + " to " + dest,
                    Toast.LENGTH_LONG).show();
    }

    public void launchAddFlights(View view) {
        Intent intent = new Intent(MainActivity.this,AddFlights.class);
        startActivityForResult(intent,ADD_FLIGHT_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_FLIGHT_CODE && resultCode == RESULT_OK && data != null){
            Airline airline = (Airline)data.getSerializableExtra(AddFlights.AIRLINE);
            addAirlineToList(airline);
            writeAirlinesToDisk();

        }
    }

    private void addAirlineToList(Airline airline) {
        boolean found = false;
        for(int i = 0; i < airlinesList.getCount(); i++){
            Airline air = airlinesList.getItem(i);
            if(air.getName().equals(airline.getName())){
                Collection<Flight> flight = airline.getFlights();
                air.addFlight(flight.iterator().next());
                found = true;
                break;
            }
        }
        if(!found)
            airlinesList.add(airline);
        return;

    }
    private void writeAirlinesToDisk(){
        File file = new File(this.getFilesDir(),"AirlinesFile.txt");
        try(PrintWriter pw = new PrintWriter(new FileWriter(file))){
            TextDumper dumper = new TextDumper(pw);
            for(int i = 0; i < airlinesList.getCount(); i++){
                dumper.dump(airlinesList.getItem(i));
            }
            pw.flush();


        }catch(IOException e){
            Toast.makeText(this,e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}