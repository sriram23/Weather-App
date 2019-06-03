    package com.example.sriram.weatherapp;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class WeatherAdapter extends ArrayAdapter<Weather> {

    public WeatherAdapter(Context context, List<Weather> weathers) {
        super(context, 0,weathers);
    }

    public View getView(int pos, View view, ViewGroup parent){
        View listItemView = view;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.weather_list,parent,false);
        }
        Weather currentWeather = getItem(pos);
        TextView cityView = (TextView) listItemView.findViewById(R.id.City);
        cityView.setText(currentWeather.getCity());
        TextView tempView = (TextView) listItemView.findViewById(R.id.Temp);
        tempView.setText(currentWeather.getTemp());
        GradientDrawable tempCircle = (GradientDrawable) tempView.getBackground();
        int tempColor = getMagnitudeColor(currentWeather.getTemp());
        tempCircle.setColor(tempColor);
        return listItemView;
    }
    int getMagnitudeColor(String magnitude){
        int magnitudeColorResourceId=0;
        int magnitudeFloor = Integer.parseInt(magnitude);
        if(magnitudeFloor<27)
            magnitudeColorResourceId = R.color.colorCool;
        if(magnitudeFloor>=27 && magnitudeFloor<30)
            magnitudeColorResourceId = R.color.colorMild;
        if(magnitudeFloor>=30 && magnitudeFloor<33)
            magnitudeColorResourceId = R.color.colorWarm;
        if(magnitudeFloor>=33 && magnitudeFloor<35)
            magnitudeColorResourceId = R.color.colorHot;
        if(magnitudeFloor>=35)
            magnitudeColorResourceId = R.color.ColorVHot;
//        magnitudeColorResourceId = R.color.ColorVHot;
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}
