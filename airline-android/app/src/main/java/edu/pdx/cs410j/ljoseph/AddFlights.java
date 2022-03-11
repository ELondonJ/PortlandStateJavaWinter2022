package edu.pdx.cs410j.ljoseph;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddFlights extends AppCompatActivity{

    private static final String FLIGHT = "FLIGHT";
    public static final String AIRLINE = "AIRLINE";
    private Flight flight;
    private Airline airline;
    private EditText departDateBtn;
    private EditText arriveDateBtn;
    private EditText departTimeBtn;
    private EditText arriveTimeBtn;
    private Button addFlight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flights);

        departDateBtn = findViewById(R.id.departDate);
        arriveDateBtn = findViewById(R.id.arriveDate);
        departTimeBtn = findViewById(R.id.departTime);
        arriveTimeBtn = findViewById(R.id.arriveTime);

        departDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerPopUp(departDateBtn);
            }
        });
        arriveDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerPopUp(arriveDateBtn);
            }
        });
        departTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerPopUp(departTimeBtn);
            }
        });
        arriveTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerPopUp(arriveTimeBtn);
            }
        });


        addFlight = findViewById(R.id.add_flight_btn);
        addFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createFlight();
                if(flight != null && airline != null){
                    sendFlightToMain();
                }

            }
        });
    }

    private void timePickerPopUp(EditText text) {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int min) {
                Calendar cal = Calendar.getInstance();
                cal.set((Calendar.HOUR_OF_DAY),hourOfDay);
                cal.set((Calendar.MINUTE),min);

                DateFormat outForm = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                String dateString = outForm.format(cal.getTime());

                text.setText(dateString);

            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,timeSetListener,
                12,0,
                false);
        timePickerDialog.show();
    }

    private void sendFlightToMain() {
        Intent intent = new Intent();
        intent.putExtra(AIRLINE,this.airline);
        setResult(RESULT_OK,intent);
        finish();
    }

    private void datePickerPopUp(EditText text) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                String temp = String.valueOf(i1 + 1) + "/" + String.valueOf(i2) +"/" + String.valueOf(i);
                text.setText(temp);
            }

        };
        DatePickerDialog datePickerDialogD = new DatePickerDialog(this,dateSetListener,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialogD.show();

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
        String[] departSplit = departTime.split(" ");
        String  dest = ((EditText)findViewById(R.id.destCode)).getText().toString();
        String  arriveDate = ((EditText)findViewById(R.id.arriveDate)).getText().toString();
        String  arriveTime = ((EditText)findViewById(R.id.arriveTime)).getText().toString();
        String[] arriveSplit = arriveTime.split(" ");
        try {
            airline = new Airline(airlineName);
            flight = new Flight(flightNumber,src,departDate,departSplit[0],departSplit[1],dest,
                    arriveDate,arriveSplit[0], arriveSplit[1]);
            airline.addFlight(flight);
            Toast.makeText(this, airline.toString(),Toast.LENGTH_LONG).show();
        }catch(IllegalArgumentException e){
          Toast.makeText(this, e.getMessage(),Toast.LENGTH_LONG).show();
        }




    }
}