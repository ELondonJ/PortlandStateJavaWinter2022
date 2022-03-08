package edu.pdx.cs410j.ljoseph;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        startActivity(intent);
    }
}