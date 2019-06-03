package com.example.sriram.weatherapp;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.example.sriram.weatherapp.R.color.colorCool;

public class MainActivity extends AppCompatActivity {
    static String Time;
    EditText citytext;
    static WeatherAdapter madapter;
    WeatherAsyncTask task = new WeatherAsyncTask();

    String Link = "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&apikey=7727eb7a7ad3adf1d307938860eca01b";
    String City[] = {"Delhi","Mumbai","Chennai","Kolkata","Bangalore","Hyderabad","Jammu","Srinagar","Kargil","Leh","Dehradun","Shimla","Amritsar","Jalandhar","Chandigarh","Gorakhpur","Meerut","Jaipur","Jodhpur","Gwalior","Bhopal","Indore","Varanasi","Patna","Ranchi","Jamshedpur","Guwahati","Imphal","Aizawl","Kohima","Darjeeling","Shillong","Bhopal","Raipur","Ahmedabad","Surat","Indore","Bhubaneshwar","Nagpur","Pune","Vishakhapatnam","Vijayawada","Mangalore","Mysore","Tirupati","Calicut","Cochin","Thiruvananthapuram","Coimbatore","Madurai","Salem","Trichy","Thanjavur","Vellore","Ooty","Theni","Nagercoil","Tirunelveli","Ramanthapuram","Rameswaram","Puducherry","Kavaratti","Port Blair"};

    String cityLink[] = new String[City.length];
//    static String CITY[] = {"http://api.openweathermap.org/data/2.5/weather?q=Coimbatore&units=metric&apikey=7727eb7a7ad3adf1d307938860eca01b","http://api.openweathermap.org/data/2.5/weather?q=Chennai&units=metric&apikey=7727eb7a7ad3adf1d307938860eca01b","http://api.openweathermap.org/data/2.5/weather?q=Madurai&units=metric&apikey=7727eb7a7ad3adf1d307938860eca01b","http://api.openweathermap.org/data/2.5/weather?q=Bangalore&units=metric&apikey=7727eb7a7ad3adf1d307938860eca01b"};
//    static final String JSONStr = "{\"coord\":{\"lon\":76.96,\"lat\":11},\"weather\":[{\"id\":721,\"main\":\"Haze\",\"description\":\"haze\",\"icon\":\"50d\"}],\"base\":\"stations\",\"main\":{\"temp\":30,\"pressure\":1010,\"humidity\":66,\"temp_min\":30,\"temp_max\":30},\"visibility\":5000,\"wind\":{\"speed\":2.6,\"deg\":180},\"clouds\":{\"all\":20},\"dt\":1558931859,\"sys\":{\"type\":1,\"id\":9206,\"message\":0.0049,\"country\":\"IN\",\"sunrise\":1558916908,\"sunset\":1558962622},\"timezone\":19800,\"id\":1273865,\"name\":\"Coimbatore\",\"cod\":200}";
    static TextView time; //city1, city2, city3, city4, degree1, degree2, degree3, degree4,time;
    static ProgressBar progressBar;
    static TextView per;
    static ListView weatherlistview;
    static List<Weather> currWeather;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(int i=0;i<City.length;i++){
            cityLink[i] = String.format(Link,City[i]);
        }

        weatherlistview = (ListView) findViewById(R.id.list);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        per = (TextView)findViewById(R.id.percent);
        time = (TextView)findViewById(R.id.updateTime);
        madapter = new WeatherAdapter(this, new ArrayList<Weather>());
        weatherlistview.setAdapter(madapter);
        WeatherAsyncTask task = new WeatherAsyncTask();
        task.execute(cityLink);


    }


    @Override
    protected void onResume() {
        super.onResume();
//        madapter.clear();
//        WeatherAsyncTask task = new WeatherAsyncTask();
//        task.execute(cityLink);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.weather_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        task.execute(CITY);
        madapter.clear();
        WeatherAsyncTask task = new WeatherAsyncTask();
        task.execute(cityLink);
        return true;
    }
}
