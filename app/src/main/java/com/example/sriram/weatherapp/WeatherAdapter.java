    package com.example.sriram.weatherapp;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
        tempView.setText(currentWeather.getTemp()+"°C");
        GradientDrawable tempCircle = (GradientDrawable) tempView.getBackground();
        int tempColor = getMagnitudeColor(currentWeather.getTemp());
        tempCircle.setColor(tempColor);
        TextView country = (TextView) listItemView.findViewById(R.id.country);
        country.setText(currentWeather.getCountry());
        ImageView con = (ImageView) listItemView.findViewById(R.id.flag);
        if(country.getText().toString().equals("IN")){
            con.setImageResource(R.drawable.indiaflagicon64);
        }
        if(country.getText().toString().equals("GB")){
            con.setImageResource(R.drawable.uk);
        }
        if(country.getText().toString().equals("US")){
            con.setImageResource(R.drawable.usa);
        }

        TextView weather = (TextView) listItemView.findViewById(R.id.weather);
        weather.setText(currentWeather.getWeather());
        ImageView climate = (ImageView) listItemView.findViewById(R.id.sym);
        climate.setImageResource(getClimate(weather.getText().toString()));
        TextView windView = (TextView) listItemView.findViewById(R.id.wind);
        TextView sunriseView = (TextView) listItemView.findViewById(R.id.sunrise);
        sunriseView.setText(currentWeather.getSunrise());
        TextView sunsetView = (TextView) listItemView.findViewById(R.id.sunset);
        sunsetView.setText(currentWeather.getSunset());
        windView.setText(currentWeather.getWind());

//        TextView timezoneView = (TextView) listItemView.findViewById(R.id.TZ);
//        timezoneView.setText(currentWeather.getTZ());

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
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
    int getClimate(String w){
        int ResId = 0;
        if(w.equals("Haze"))
            ResId = R.drawable.haze;
        if(w.equals("Clouds"))
            ResId = R.drawable.cloud;
        if(w.equals("Clear"))
            ResId = R.drawable.clear;
        if(w.equals("Rain"))
            ResId = R.drawable.rainy;
        if(w.equals("Thunderstorm"))
            ResId = R.drawable.storm;
        if(w.equals("Smoke"))
            ResId = R.drawable.misty;
        if(w.equals("Drizzle"))
            ResId = R.drawable.drizzle;
        if(w.equals("Mist"))
            ResId = R.drawable.snow;
        return ResId;
    }
}
