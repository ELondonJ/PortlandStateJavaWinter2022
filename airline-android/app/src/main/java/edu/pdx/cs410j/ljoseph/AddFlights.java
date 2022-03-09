package edu.pdx.cs410j.ljoseph;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddFlights extends AppCompatActivity {

    private Flight flight;
    private Airline airline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flights);

        Button addFlight = findViewById(R.id.add_flight_btn);

        addFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createFlight();

            }
        });
    }

    private void createFlight() {
        EditText airName = findViewById(R.id.airlineName);
        String airlineName = airName.getText().toString();
        int flightNumber;
        try {
            flightNumber = Integer.parseInt(((EditText) findViewById(R.id.flightNumber)).getText().toString());
        }catch(NumberFormatException e){
            flightNumber = -1;
        }
        String src = ((EditText)findViewById(R.id.srcCode)).getText().toString();
        String departDate = ((EditText)findViewById(R.id.departDate)).getText().toString();
        String  departTime = ((EditText)findViewById(R.id.departTime)).getText().toString();
        String  dest = ((EditText)findViewById(R.id.destCode)).getText().toString();
        String  arriveDate = ((EditText)findViewById(R.id.arriveDate)).getText().toString();
        String  arriveTime = ((EditText)findViewById(R.id.arriveTime)).getText().toString();
        try {
            airline = new Airline(airlineName);
            flight = new Flight(flightNumber,src,departDate,departTime,"am",dest,
                    arriveDate,arriveTime,"am");
            airline.addFlight(flight);
            Toast.makeText(this, airline.toString(),Toast.LENGTH_LONG).show();
        }catch(IllegalArgumentException e){
          Toast.makeText(this, e.getMessage(),Toast.LENGTH_LONG).show();
        }




    }
}