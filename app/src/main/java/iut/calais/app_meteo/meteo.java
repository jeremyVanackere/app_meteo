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
import iut.calais.app_meteo.bean.Main;
import iut.calais.app_meteo.bean.Rep;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class meteo extends AppCompatActivity {

    public static TextView textLat;
    public  String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meteo);

        // On récupère les extras de l'activité latitude et la longitude
        double latitude = this.getIntent().getDoubleExtra("latitude",0);
        double longitude = this.getIntent().getDoubleExtra("longitude",0);

        /// Ici les View
        textLat = findViewById(R.id.textView);

        RecupMeteo recupMeteo = new RecupMeteo(latitude,longitude); // Objet pour faire la requete
        Data data = new Data(); // Objet qui contien les datas de l'api

        recupMeteo.recupererMeteo().enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                message = "erreur de connexion à l'API";
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException { // ici réponse réussite
                String result = response.body().string();
                data.setData(result);

                // ici traitement...
                String info = "Vladi Il fait "+data.getTemperature()+" C° à "+data.getVille()+"\n";
                info += "pression : "+data.getPression()+", humidite : "+ data.getHumidite() +" \n";
                info += "description : "+data.getDescription()+", Icon : "+data.getIcon();
                AfficherTexte(textLat, info);
            }
        });
    }

    /// Permet d'afficher sur le thread principale d'affichage depuis un autre thread
    public void AfficherTexte(TextView textView, String texte)
    {
        meteo.this.runOnUiThread(() -> textView.setText(texte));
    }
}
