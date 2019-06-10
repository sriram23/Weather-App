package com.example.sriram.weatherapp;

import android.os.AsyncTask;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.example.sriram.weatherapp.R.color.colorCool;

public class WeatherAsyncTask extends AsyncTask<String, Integer, List<Weather>> {
    List<Weather> W = new ArrayList<>();
    URL url;
    String jsonResp;
    HttpURLConnection connection;
    InputStream stream;
    @Override
    protected List<Weather> doInBackground(String... strings) {
//        String finalJSR[] = new String[100];
        List<Weather> weather = new ArrayList<>();
        try {
            for(int i=0;i< strings.length;i++) {
                url = new URL(strings[i]);
                jsonResp = makeconnection(url);
//                finalJSR[i] = JsonParse(jsonResp);
                weather=JsonParse(jsonResp);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return weather;
    }

    private List<Weather> JsonParse(String jsonResp) {
        String finalTemp = null;
        JSONObject base = null;

        try {
            base = new JSONObject(jsonResp);
            JSONObject main = base.getJSONObject("main");
            JSONObject sys = base.getJSONObject("sys");
            JSONArray weatherarray = base.getJSONArray("weather");
            JSONObject weatherObj = weatherarray.getJSONObject(0);
            String weatherTxt = weatherObj.getString("main");
            JSONObject wind = base.getJSONObject("wind");
            float speed = (float) wind.getDouble("speed");
            speed = (float) (speed * (3600/1609.344));
            float deg = (float) wind.getDouble("deg");
            String windSpeed = String.valueOf(speed)+" mph - "+getDirection(deg);

            int temp = main.getInt("temp");
            String city = base.getString("name");
            String country = sys.getString("country");
            finalTemp = String.valueOf(temp);
            Weather weather = new Weather(city, finalTemp, country,weatherTxt,windSpeed);
            W.add(weather);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return W;
    }

    private String getDirection(float deg) {
        if(deg<=11.25 || deg>=326.25)
            return "North";
        if(deg>33.75 && deg<=56.25)
            return "North East";
        if(deg>56.25 && deg<=101.25)
            return "East";
        if(deg>101.25 && deg<=146.25)
            return "South East";
        if(deg>146.25 && deg<=191.25)
            return "South";
        if(deg>191.25 && deg<=236.25)
            return "South West";
        if(deg>236.25 && deg<=281.25)
            return "West";
        if(deg>281.25 && deg<326.25)
            return "North East";
        return "";
    }

    private String makeconnection(URL url) {
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.connect();

            stream = connection.getInputStream();
            StringBuilder output = new StringBuilder();
            InputStreamReader reader = new InputStreamReader(stream, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(reader);
            String line = br.readLine();
            while (line != null) {
                output.append(line);
                line = br.readLine();
            }
            jsonResp = output.toString();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResp;
    }

    @Override
    protected void onPreExecute() {
//        super.onPreExecute();
        MainActivity.progressBar.setProgress(0);
//        MainActivity.per.setText("0%");
        MainActivity.time.setVisibility(View.INVISIBLE);
        MainActivity.progressBar.setVisibility(View.VISIBLE);
        MainActivity.per.setVisibility(View.VISIBLE);
        MainActivity.weatherlistview.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
//        super.onProgressUpdate(values);
        MainActivity.progressBar.setProgress(values[0]);
        MainActivity.per.setText(values[0].toString()+"%");
    }

    @Override
    protected void onPostExecute(List<Weather> s) {
//        super.onPostExecute(s);
        MainActivity.time.setVisibility(View.VISIBLE);
        MainActivity.progressBar.setVisibility(View.GONE);
        MainActivity.per.setVisibility(View.GONE);
        MainActivity.weatherlistview.setVisibility(View.VISIBLE);
//        MainActivity.degree.append(R.string.app_name);
//        MainActivity.temp = s;
        MainActivity.currWeather = s;
        DateTimeFormatter dtf = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            MainActivity.Time = dtf.format(now);
            MainActivity.time.setText("Last updated on: "+MainActivity.Time);
            if(MainActivity.currWeather!=null && !MainActivity.currWeather.isEmpty())
               MainActivity.madapter.addAll(MainActivity.currWeather);
        }
    }
}
