package edu.pdx.cs410j.ljoseph;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import edu.pdx.cs410J.AirportNames;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class FlightListAdapter extends ArrayAdapter<Flight> {
    private ArrayList<Flight> flightList;
    private Activity activity;

    public FlightListAdapter(@NonNull Context context, int resource, ArrayList<Flight> flightList) {
        super(context, resource);
        this.flightList = flightList;
    }

    @NonNull
    @Override
    public View getView(int i, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.flight_list_item,
                    parent, false);

        }
        Flight flight = flightList.get(i);
        TextView flightNumber = convertView.findViewById(R.id.flyitem_flight);
        TextView src = convertView.findViewById(R.id.flyitem_src);
        TextView ddate = convertView.findViewById(R.id.flyitem_src_date);
        TextView dtime= convertView.findViewById(R.id.flyitem_src_time);
        TextView dest = convertView.findViewById(R.id.flyitem_dest);
        TextView adate= convertView.findViewById(R.id.flyitem_dest_date);
        TextView atime = convertView.findViewById(R.id.flyitem_dest_time);
        TextView dCity = convertView.findViewById(R.id.flyitem_src_city);
        TextView aCity = convertView.findViewById(R.id.flyitem_dest_city);
        String[] ddateSplit = flightList.get(i).getDepartureString().split(" ");
        String[] adateSplit = flightList.get(i).getArrivalString().split(" ");
        String srcCity = AirportNames.getName(flight.getSource().toUpperCase());
        String destCity = AirportNames.getName(flight.getDestination().toUpperCase());
        if(srcCity.length() > 9){
            srcCity = srcCity.substring(0,9);
        }
        if(destCity.length() > 9){
            destCity = destCity.substring(0,9);
        }

        flightNumber.setText(String.valueOf(flight.getNumber()));
        src.setText(flight.getSource());
        ddate.setText(ddateSplit[0]);
        dtime.setText(ddateSplit[1] + " " + ddateSplit[2]);
        dest.setText(flight.getDestination());
        adate.setText(adateSplit[0]);
        atime.setText(adateSplit[1] + " " + adateSplit[2]);
        dCity.setText(srcCity);
        aCity.setText(destCity);


        return convertView;
    }
}
