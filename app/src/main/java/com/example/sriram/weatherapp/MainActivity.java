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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.example.sriram.weatherapp.R.color.colorCool;

public class MainActivity extends AppCompatActivity {

    static TextView tv;

    static String Time;
    EditText citytext;
    static WeatherAdapter madapter;
    WeatherAsyncTask task = new WeatherAsyncTask();
    EditText et;
    Button btn;
    String searchCity;

    String Link = "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&apikey=7727eb7a7ad3adf1d307938860eca01b";
    String[] Ind = {"Mumbai"};
    String[] Tn = {"Ariyalur","Chennai","Coimbatore","Cuddalore","Dharmapuri","Dindigul","Erode","Kanchipuram","Kanyakumari","Karur","Krishnagiri","Madurai","Nagapattinam","Namakkal","Ooty","Perambalur","Pudukottai","Ramanathapuram","Salem,IN","Sivaganga","Thanjavur","Theni","Tirunelveli","Thiruvallur","Tiruvannamalai","Thiruvarur","Tiruppur","Tiruchchirappalli","Thoothukudi","Vellore","Villupuram","Virudhunagar"};
    String[] Wor = {"London"};
//    String City[] = {"Delhi,IN","Mumbai,IN","Chennai,IN","Kolkata,IN","Bangalore,IN","Hyderabad,IN","Jammu,IN","Srinagar,IN","Kargil,IN","Leh,IN","Dehradun,IN","Shimla,IN","Amritsar,IN","Jalandhar,IN","Chandigarh,IN","Gorakhpur,IN","Meerut,IN","Jaipur,IN","Jodhpur,IN","Gwalior,IN","Bhopal,IN","Indore,IN","Varanasi,IN","Patna,IN","Ranchi,IN","Jamshedpur,IN","Guwahati,IN","Imphal,IN","Aizawl,IN","Kohima,IN","Darjeeling,IN","Shillong,IN","Bhopal,IN","Raipur,IN","Ahmedabad,IN","Surat,IN","Indore,IN","Bhubaneshwar,IN","Nagpur,IN","Pune,IN","Vishakhapatnam,IN","Vijayawada,IN","Mangalore,IN","Mysore,IN","Tirupati,IN","Calicut,IN","Cochin,IN","Thiruvananthapuram,IN","Coimbatore,IN","Madurai,IN","Salem,IN","Trichy,IN","Thanjavur,IN","Vellore,IN","Ooty,IN","Theni,IN","Nagercoil,IN","Tirunelveli,IN","Ramanthapuram,IN","Rameswaram,IN","Puducherry,IN","Kavaratti,IN","Port Blair,IN","London,GB","Greenwich,GB","New%20York,US"};
    String City[] = Wor;
    String cityLink[] = new String[1000];
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
        cityLink = format(City);
//        tv = (TextView) findViewById(R.id.test);

        weatherlistview = (ListView) findViewById(R.id.list);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        per = (TextView)findViewById(R.id.percent);
        time = (TextView)findViewById(R.id.updateTime);
        madapter = new WeatherAdapter(this, new ArrayList<Weather>());
        weatherlistview.setAdapter(madapter);
        WeatherAsyncTask task = new WeatherAsyncTask();
        task.execute(cityLink);


        weatherlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                LinearLayout linearLayoutParent = (LinearLayout) arg1;

                // Getting the inner Linear Layout
                LinearLayout linearLayoutChild0 = (LinearLayout) linearLayoutParent.getChildAt(0);
                TextView temp_lt = (TextView) linearLayoutChild0.getChildAt(1);

                LinearLayout linearLayoutChild = (LinearLayout) linearLayoutParent.getChildAt(1);

                // Getting the Country TextView
                LinearLayout Country_lt = (LinearLayout) linearLayoutChild.getChildAt(0);
                LinearLayout Climate_lt = (LinearLayout) linearLayoutChild.getChildAt(1);
                LinearLayout wind_lt = (LinearLayout) linearLayoutChild.getChildAt(2);
                LinearLayout sunrise_lt = (LinearLayout) linearLayoutChild.getChildAt(3);
                LinearLayout sunset_lt = (LinearLayout) linearLayoutChild.getChildAt(4);
                if(Country_lt.getVisibility() == View.VISIBLE) {
                    Country_lt.setVisibility(View.GONE);
                    Climate_lt.setVisibility(View.GONE);
                    wind_lt.setVisibility(View.GONE);
                    sunrise_lt.setVisibility(View.GONE);
                    sunset_lt.setVisibility(View.GONE);
                    temp_lt.getLayoutParams().width = 30;
                }
                else{
                    Country_lt.setVisibility(View.VISIBLE);
                    Climate_lt.setVisibility(View.VISIBLE);
                    wind_lt.setVisibility(View.VISIBLE);
                    sunrise_lt.setVisibility(View.VISIBLE);
                    sunset_lt.setVisibility(View.VISIBLE);
                    temp_lt.getLayoutParams().width = 50;
                }
            }
        });

    }

    String[] format(String[] city) {
        String link[] = new String[100];
        int k = 0;
        for (int i = 0; i < city.length; i++) {
            link[k] = String.format(Link, city[i]);
            k++;
        }
        return link;
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
        int id = item.getItemId();
        WeatherAsyncTask task;
        madapter.clear();
        switch (id) {
            case R.id.India:

                task = new WeatherAsyncTask();
                cityLink = format(Ind);
                task.execute(cityLink);
//                getActionBar().setTitle("Weather App - India");
                return true;
            case R.id.World:
//                madapter.clear();
                task = new WeatherAsyncTask();
                cityLink = format(Wor);
                task.execute(cityLink);
//                getActionBar().setTitle("Weather App - World");
                return true;
            case R.id.TamilNadu:
//                madapter.clear();
                task = new WeatherAsyncTask();
                cityLink = format(Tn);
                task.execute(cityLink);
//                getActionBar().setTitle("Weather App - TamilNadu");
                return true;
            case R.id.refresh:
//                madapter.clear();
                task = new WeatherAsyncTask();
                task.execute(cityLink);
                return true;
        }
        return true;
    }
}
