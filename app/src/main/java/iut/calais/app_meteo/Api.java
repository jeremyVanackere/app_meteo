package iut.calais.app_meteo;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Api extends AsyncTask<String, Void, Void> {

    public String result = "rien";

    @Override
    protected Void doInBackground(String... strings) {

        try {
            result = "ok";

            OkHttpClient client = new OkHttpClient();

            Request req = new Request.Builder()
                    .url("http://api.openweathermap.org/data/2.5/weather?lat=51.03&lon=2.38&appid=d81df31027a2b337df5776d7cb7eb71e")
                    .build();

            Response response = null;
            result = "okl";

            response = client.newCall(req).execute();
            result = response.body().string();
            result = "okokoko";

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
