package com.example.sriram.weatherapp;

public class Weather {
    private String mCity;
    private String mTemp;

    public Weather(String city, String temp){
        mCity = city;
        mTemp = temp;
    }

    public String getCity(){return mCity;}
    public String getTemp(){return mTemp;}
}
