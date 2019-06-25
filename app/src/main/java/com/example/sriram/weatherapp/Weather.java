package com.example.sriram.weatherapp;

public class Weather {
    private String mCity;
    private String mTemp;
    private String mCountry;
    private String mWeather;
    private String mWind;
    private String mSunrise;
    private String mSunset;
    private String mTz;

    public Weather(String city, String temp, String country,String weather, String wind, String sunrise, String sunset){
        mCity = city;
        mTemp = temp;
        mCountry = country;
        mWeather = weather;
        mWind = wind;
        mSunrise = sunrise;
        mSunset = sunset;
//        mTz = tz;
    }

    public String getCity(){return mCity;}
    public String getTemp(){return mTemp;}
    public String getCountry(){return mCountry;}
    public String getWeather(){return  mWeather;}
    public String getWind(){return mWind;}
    public String getSunrise(){return mSunrise;}
    public String getSunset(){return mSunset;}
    public String getTZ(){return mTz;}
}
