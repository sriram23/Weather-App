package com.example.sriram.weatherapp;

import android.os.AsyncTask;
import android.view.View;

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
            int temp = main.getInt("temp");
            String city = base.getString("name");
            finalTemp = String.valueOf(temp);
            Weather weather = new Weather(city, finalTemp);
            W.add(weather);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return W;
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
