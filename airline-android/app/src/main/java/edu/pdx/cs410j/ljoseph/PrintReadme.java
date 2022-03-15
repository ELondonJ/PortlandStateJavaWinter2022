package edu.pdx.cs410j.ljoseph;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PrintReadme extends AppCompatActivity {

    TextView readme;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        else
            return super.onOptionsItemSelected(item);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_readme);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        readme = findViewById(R.id.readme_tv);

        readme.setText("CS410J Project 5: Airline\n" +
                "Authors: London Joseph, David Whitlock\n\n" +
                "Version: 02.16.2022\n\n" +
                "Description:\n" +
                "This is the sixth project for Advanced Java Programing(CS410P) at Portland State " +
                "University. Project 5 builds " +
                "from Previous projects, creating a full functioning android app" +
                "\n\nThe home screen offers 4 ways to interact with the Airline Tracker app.  \n" +
                "\n-Add Flights button.  This button redirects the user to a new screen to add" +
                "a flight and its corresponding airline. The new activity has a form where the " +
                "flight information is entered. Date and time are entered with Date and Time " +
                "Pickers to ensure correct formatting. Once the flight information has been entered " +
                "the user will need to click the “add click” button to finalized the entry. " +
                " Once the flight has been finalized it will be added to the airline line list and " +
                "will return to the home screen.\n" +
                "\n-List of Airlines: The list in the center of the home screen displays all the " +
                "airlines that" +
                " have been entered into the system.  Each Airline tab in the list also includes" +
                " the number of flights each airline has.  Each airline tab in the list is " +
                "clickable and once clicked will redirect the user to a new screen that displays " +
                "the airline name and a detailed list of all of the flights corresponding to that " +
                "airline.\n" +
                "\n-Search bar:  The search bar takes an airline, source airport code and destination" +
                " code.  The search button at the end of the bar can be clicked to activate a " +
                "search for flights matching the entered criteria. The user will be redirect to an" +
                " activity displaying all of the matching flights\n");

    }
}