package com.example.sriram.weatherapp;

public class Weather {
    private String mCity;
    private String mTemp;
    private String mCountry;
    private String mWeather;
    private String mWind;

    public Weather(String city, String temp, String country,String weather, String wind){
        mCity = city;
        mTemp = temp;
        mCountry = country;
        mWeather = weather;
        mWind = wind;
    }

    public String getCity(){return mCity;}
    public String getTemp(){return mTemp;}
    public String getCountry(){return mCountry;}
    public String getWeather(){return  mWeather;}
    public String getWind(){return mWind;}
}
