package iut.calais.app_meteo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import iut.calais.app_meteo.bean.Coordonnee;
import iut.calais.app_meteo.bean.Rep;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class meteo extends AppCompatActivity {

    public static TextView textLat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meteo);

        double latitude = this.getIntent().getDoubleExtra("latitude",0);
        double longitude = this.getIntent().getDoubleExtra("longitude",0);

        textLat = findViewById(R.id.textView);

        textLat.setText("latitude : "+latitude+" / longitude :  "+longitude);

        OkHttpClient okHttpClient = new OkHttpClient();

        Request req = new Request.Builder()
                .url("http://api.openweathermap.org/data/2.5/weather?lat=51.03&lon=2.38&appid=d81df31027a2b337df5776d7cb7eb71e")
                .build();

        Response response = null;

        okHttpClient.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                textLat.setText("erreur");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();

                meteo.this.runOnUiThread(() -> textLat.setText(result));

                final Gson gson = new Gson();
                Rep rep = gson.fromJson(result, Rep.class);
                Coordonnee coord = rep.getCoord();
                coord.getLon();
            }
        });
    }
}
