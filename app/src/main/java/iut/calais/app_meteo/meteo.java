package iut.calais.app_meteo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;

import iut.calais.app_meteo.bean.Coordonnee;
import iut.calais.app_meteo.bean.Main;
import iut.calais.app_meteo.bean.Rep;
import iut.calais.app_meteo.beans.Favoris;
import iut.calais.app_meteo.sqlite.MeteoFavorisService;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class meteo extends AppCompatActivity {
    public String imageURL = "http://openweathermap.org/drawable.img/w/";


    private ImageView imgIcon;
    private TextView cloud;
    private TextView  cityName;
    private TextView temp;
    private TextView humidity;
    private TextView pressure;
    private TextView wind;
    private TextView sunrise;
    private TextView sunset;
    public  String message = "";
    private RelativeLayout relMeteo;
    public String fond="";
    private TextView jours;
    private TabHost tabHost;
    private FloatingActionButton BtnFav;

    private Favoris fav, favori; // favoris contien l'information du favori s'il existe deja
    private MeteoFavorisService meteoFavorisService;
    private boolean boolFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meteo);

        boolFav = false;

        BtnFav = findViewById(R.id.floatingActionButton);
        imgIcon=(ImageView) findViewById(R.id.imgIcon);
        cloud=(TextView) findViewById(R.id.textCloud);
        cityName=(TextView) findViewById(R.id.textVille);
        temp=(TextView) findViewById(R.id.textGrados);
        humidity=(TextView) findViewById(R.id.textHumidity);
        pressure = (TextView) findViewById(R.id.textPressure);
        wind = (TextView) findViewById(R.id.textWind);
        sunrise = (TextView) findViewById(R.id.textSunrise);
        sunset = (TextView) findViewById(R.id.textSunset);
        jours = (TextView) findViewById(R.id.textJours);
        relMeteo = (RelativeLayout) findViewById(R.id.relativeMeteo);
        tabHost = (TabHost) findViewById(R.id.tabshost);
        tabHost.setup();

        //pestaña uno
        TabHost.TabSpec spec = tabHost.newTabSpec("lun");
        spec.setContent(R.id.tab1);
        spec.setIndicator("lun");
        tabHost.addTab(spec);

        //pestaña dos
        spec = tabHost.newTabSpec("mart");
        spec.setContent(R.id.tab1);
        spec.setIndicator("mart");
        tabHost.addTab(spec);

        //vue trois
        spec = tabHost.newTabSpec("merc");
        spec.setContent(R.id.tab1);
        spec.setIndicator("merc");
        tabHost.addTab(spec);

        meteoFavorisService = new MeteoFavorisService(this);

        double latitude = this.getIntent().getDoubleExtra("latitude",0);
        double longitude = this.getIntent().getDoubleExtra("longitude",0);
        //textLat.setText("latitude : "+latitude+" / longitude :  "+longitude);
        RecupMeteo recupMeteo = new RecupMeteo(latitude,longitude);
        Data data = new Data();

        recupMeteo.recupererMeteo().enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                message = "erreur de connexion à l'API";
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException { // ici réponse réussite
                String result = response.body().string();
                data.setData(result);

                String sunriseDate = DateFormat.getTimeInstance(DateFormat.SHORT).format(data.getSunrise());
                String sunseteDate = DateFormat.getTimeInstance(DateFormat.SHORT).format(data.getSunset());
                // String sunriseDate = df.format( new Date(data.getSunrise()));
                // String sunsetDate = df.format(new Date(data.getSunset()));

                // ici traitement...
                String tempe = data.getTemperature()+" C° ";
                String vill=data.getVille();
                String press= "pression :"+"\n"+data.getPression();
                String hum="humidite :"+"\n"+data.getHumidite();
                String des= "desc :"+"\n"+data.getTemps()+"("+data.getDescription()+")";
                String wi= "wind : "+"\n"+data.getSpeed()+"mps";
                String sunr= "sunrise :"+"\n"+sunriseDate;
                String suns= "sunsete :"+"\n"+sunseteDate;
                String icon=data.getIcon();

                // On crée un favori au cas ou on l'ajoute au favori
                fav = new Favoris((long)0,""+latitude,""+longitude,vill);

                // On affiche
                AfficherTexte(temp, tempe);
                AfficherTexte(cityName, vill);
                AfficherTexte(pressure, press);
                AfficherTexte(humidity, hum);
                AfficherTexte(cloud, des);
                AfficherTexte(wind, wi);
                AfficherTexte(sunrise, sunr);
                AfficherTexte(sunset, suns);
                //new LoadImage(imgIcon).execute(imageURL+icon+".png")
                AfficherIcon(imgIcon,icon);
                fond=fond+data.getDescription();
                //AfficherTexte(imgIcon, icon)

                AfficherImage(relMeteo, fond);

                // Gestion étioile favori
                TextView ville = findViewById(R.id.textVille);
                meteoFavorisService.open();
                favori = meteoFavorisService.GetByLibelle(vill);
                meteoFavorisService.close();
                if(favori != null)
                {
                    BtnFav.setImageResource(R.drawable.etoile_pleine);
                    boolFav = true;
                }

            }
        });

        BtnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meteoFavorisService.open();
                if(!boolFav) {
                    BtnFav.setImageResource(R.drawable.etoile_pleine);
                    meteoFavorisService.insert(fav);
                    boolFav = true;
                }else{
                    BtnFav.setImageResource(R.drawable.etoile_vide);
                    Long t =  meteoFavorisService.delete(favori.getId());
                    boolFav = false;
                }
                meteoFavorisService.close();

            }
        });

    }


    public void AfficherTexte(TextView textView, String texte)
    {
        meteo.this.runOnUiThread(() -> textView.setText(texte));

    }

    public void AfficherImage(RelativeLayout relativeLayout, String texte)
    {
        if(texte.equals("few clouds")) {
            meteo.this.runOnUiThread(() -> relativeLayout.setBackgroundResource(R.drawable.clearskyday));
        }else{
            meteo.this.runOnUiThread(() -> relativeLayout.setBackgroundResource(R.drawable.nublado));
        }
    }

    public void AfficherIcon(ImageView img, String icon){
        String url = "http://openweathermap.org/img/w/"+icon+".png";
        meteo.this.runOnUiThread(() ->Picasso.get().load(url).into(img));

    }




    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.fav){
            Intent favo = new Intent(meteo.this,favorite.class);
            startActivity(favo);
        }
        return super.onOptionsItemSelected(item);
    }
}
