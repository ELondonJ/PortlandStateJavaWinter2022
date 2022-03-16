package edu.pdx.cs410j.ljoseph;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AirlineListAdapter extends ArrayAdapter<Airline> {
    ArrayList<Airline> airlines;

    public AirlineListAdapter(@NonNull Context context,int resource){
        super(context,resource);
        this.airlines = new ArrayList<>();

    }
    public AirlineListAdapter(@NonNull Context context, int resource, @NonNull List<Airline> airlines) {
        super(context, resource, airlines);
        this.airlines = (ArrayList<Airline>) airlines;
    }

    @Override
    public void add(@Nullable Airline object) {
        super.add(object);
       // airlines.add(object);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        Collections.sort(airlines, new Comparator<Airline>() {
            @Override
            public int compare(Airline airline, Airline t1) {
                return airline.getName().compareTo(t1.getName());
            }
        });
    }

    @NonNull
    @Override
    public View getView(int i, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.airline_list_item,
                    parent, false);

        }
        Airline airline = airlines.get(i);
        TextView airlineName = convertView.findViewById(R.id.list_item_airline);
        TextView flightsNum = convertView.findViewById(R.id.list_item_flightNum);
        airlineName.setText(airline.getName());
        flightsNum.setText(String.valueOf(airline.getFlightCount()));
        if(airline.getName().length() > 10){
            airlineName.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        }



        return convertView;
    }

}
