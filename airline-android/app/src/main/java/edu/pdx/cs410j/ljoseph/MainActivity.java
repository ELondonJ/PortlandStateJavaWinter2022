package edu.pdx.cs410j.ljoseph;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

import edu.pdx.cs410J.AirportNames;
import edu.pdx.cs410J.ParserException;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_FLIGHT_CODE = 88;
    public static final String AIRLINE_MAIN = "AIRLINE_MAIN";
    public static final String AIRLINES_FILE_TXT = "AirlinesFile.txt";
    public static final String SOURCE = "source";
    public static final String DEST = "destination";
    private AirlineListAdapter airlinesList;
    private ListView listView;
    private Spinner spinner;
    ArrayAdapter<String> menuAd;
    private ImageView searchBtn;
    private LinearLayout addButton;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.help_icon){
            printReadme();
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        File file = new File(getFilesDir(), AIRLINES_FILE_TXT);
        if(file.exists()) {
            try (BufferedReader bf = new BufferedReader(new FileReader(file))) {
                TextParser parser = new TextParser(bf);
                ArrayList<Airline> airlines = parser.parseMultiple();
                this.airlinesList = new AirlineListAdapter(this, android.R.layout.simple_selectable_list_item, airlines);
            } catch (IOException e) {
                String mess = e.getMessage();

            } catch (ParserException | IllegalArgumentException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                this.airlinesList = new AirlineListAdapter(this, android.R.layout.simple_selectable_list_item,
                        new ArrayList<Airline>());
            }
        }
        else
            this.airlinesList = new AirlineListAdapter(this, android.R.layout.simple_selectable_list_item,
                    new ArrayList<Airline>());
        listView = findViewById(R.id.airlineList);
        listView.setAdapter(this.airlinesList);
        listView.setDividerHeight(10);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                Airline airline = (Airline)MainActivity.this.airlinesList.getItem(index);
                Intent intent = new Intent(MainActivity.this,PrintAirline.class);
                intent.putExtra(AIRLINE_MAIN,airline);
                startActivity(intent);
            }
        });
    }


    public void printReadme() {
        Intent intent = new Intent(MainActivity.this,PrintReadme.class);
        startActivity(intent);
    }

    public void launchFindFlights(View view) {
        Airline airline = null;
        String airlineName = ((EditText)findViewById(R.id.airlineSearch)).getText().toString();
        String src = ((EditText)findViewById(R.id.srcSearch)).getText().toString();
        String dest = ((EditText)findViewById(R.id.destSearch)).getText().toString();
        if(src != null && dest != null && airlineName != null && !src.equals("")
                && dest != "" && airlineName != "") {
            String srcCheck = AirportNames.getName(src.toUpperCase());
            String destCheck = AirportNames.getName(dest.toUpperCase());
            if (srcCheck == null) {
                Toast toast = Toast.makeText(this, src + " is not a valid airport",
                        Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP,0,350);
                toast.show();
            } else if (destCheck == null) {
                Toast.makeText(this, dest + " is not a valid airport", Toast.LENGTH_LONG).show();
            } else {
                for (int i = 0; i < airlinesList.getCount() && airline == null; i++) {
                    if (airlinesList.getItem(i).getName().equalsIgnoreCase(airlineName)) {
                        airline = airlinesList.getItem(i);
                        findFlights(airline, src, dest);
                        EditText edScr = findViewById(R.id.srcSearch);
                        edScr.setText("");
                        EditText edDest = findViewById(R.id.destSearch);
                        edDest.setText("");
                        EditText edAir = findViewById(R.id.airlineSearch);
                        edAir.setText("");
                    }
                }
                if (airline == null)
                    Toast.makeText(this, "No airline named " + airlineName, Toast.LENGTH_LONG).show();
            }
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
      //  if(temp.getFlightCount() != 0){
            Intent intent = new Intent(MainActivity.this,PrintAirline.class);
            intent.putExtra(AIRLINE_MAIN,temp);
            intent.putExtra(SOURCE,src);
            intent.putExtra(DEST,dest);
            startActivity(intent);
       // }
       // else
       //     Toast.makeText(this, "No direct flights from " + src + " to " + dest,
      //              Toast.LENGTH_LONG).show();
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
        for(int i = 0; i < airlinesList.getCount() && !found; i++){
            Airline air = airlinesList.getItem(i);
            if(air.getName().equals(airline.getName())){
                Collection<Flight> flight = airline.getFlights();
                air.addFlight(flight.iterator().next());
                airlinesList.notifyDataSetChanged();
                found = true;
            }
        }
        if(!found)
            airlinesList.add(airline);
        return;

    }
    private void writeAirlinesToDisk(){
        File file = new File(this.getFilesDir(),AIRLINES_FILE_TXT);
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