package edu.pdx.cs410j.ljoseph;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class PrintAirline extends AppCompatActivity {

    public static final String AIRLINE_MAIN = "AIRLINE_MAIN";
    public static final String SOURCE = "source";
    public static final String DEST = "destination";
    Airline airline;
    String src;
    String dest;
    FlightListAdapter flightsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_airline);
        Intent intent = getIntent();
        ListView listView;

        if(intent.hasExtra(AIRLINE_MAIN) && intent.hasExtra(SOURCE)
                && intent.hasExtra(DEST)){
            airline = (Airline)intent.getSerializableExtra(AIRLINE_MAIN);
            src = intent.getExtras().getString(SOURCE);
            dest = intent.getExtras().getString(DEST);
            LinearLayout lay = findViewById(R.id.lo_src_dest);
            lay.setVisibility(View.VISIBLE);
            TextView source = findViewById(R.id.lo_src);
            TextView destination = findViewById(R.id.lo_dest);
            source.setText(src.toUpperCase());
            destination.setText(dest.toUpperCase());

        }
        else if(intent.hasExtra(AIRLINE_MAIN)){
            airline = (Airline)intent.getSerializableExtra(AIRLINE_MAIN);
        }
        else
            return;
        TextView textView = findViewById(R.id.tv_printFlight_AirHead);
        textView.setText(airline.getName());

        flightsList = new FlightListAdapter(this, android.R.layout.simple_selectable_list_item,
                (ArrayList<Flight>) airline.getFlights());
        flightsList.addAll(airline.getFlights());
        listView = findViewById(R.id.lv_printFlights);
        listView.setAdapter(flightsList);
    }
}