package edu.pdx.cs410j.ljoseph;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_FLIGHT_CODE = 88;
    private ArrayAdapter<Airline> airlinesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        airlinesList = new ArrayAdapter<>(this, android.R.layout.simple_selectable_list_item);
        ListView listView = findViewById(R.id.airlineList);
        listView.setAdapter(airlinesList);


    }


    public void launchFindAirline(View view) {
        Intent intent = new Intent(MainActivity.this,FindAirline.class);
        startActivity(intent);
    }

    public void launchFindFlights(View view) {
        Intent intent = new Intent(MainActivity.this,FindFlights.class);
        startActivity(intent);
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

        }
    }

    private void addAirlineToList(Airline airline) {
        for(int i = 0; i < airlinesList.getCount(); i++){
            if(airlinesList.getItem(i).getName().equals(airline.getName())){
                Collection<Flight> flight = airlinesList.getItem(i).getFlights();
                airlinesList.getItem(i).addFlight(flight.iterator().next());
                return;
            }
        }
        airlinesList.add(airline);
    }
}