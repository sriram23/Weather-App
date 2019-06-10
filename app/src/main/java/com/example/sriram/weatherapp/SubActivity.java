package com.example.sriram.weatherapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;

class SubActivity extends AppCompatActivity {


    static TextView Pressure;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        Pressure = (TextView) findViewById(R.id.subPress);
        String Maincity = null;
        try {
            Maincity = String.valueOf(Intent.getIntent("city"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String WeatherLink = "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&apikey=7727eb7a7ad3adf1d307938860eca01b";
        String link = String.format(WeatherLink, Maincity);

//        SubAsyncTask task = new SubAsyncTask();
//        task.execute(link);
    }

}

class SubAsyncTask extends AsyncTask<String, Integer, String[]> {

    URL url;
    String jsonResp;
    HttpURLConnection connection;
    InputStream stream;
    @Override
    protected String[] doInBackground(String... strings) {
        try {
            url = new URL(strings[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        jsonResp = makeconnection(url);
        String[] result = JsonParse(jsonResp);
        return result;
    }


    @Override
    protected void onPostExecute(String[] strings) {
//        super.onPostExecute(strings);
        SubActivity.Pressure.setText(strings[2]);
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

    private String[] JsonParse(String jsonResp) {
        String finalTemp = null;
        String finalPress = null;
        JSONObject base = null;
        String[] res = new String[10];
        try {
            base = new JSONObject(jsonResp);
            JSONObject main = base.getJSONObject("main");
            int temp = main.getInt("temp");
            int press = main.getInt("pressure");
            String city = base.getString("name");
            finalTemp = String.valueOf(temp);
            finalPress = String.valueOf(press);
            res[0] = city;
            res[1] = finalTemp;
            res[2] = finalPress;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }
}