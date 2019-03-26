package iut.calais.app_meteo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.gson.Gson;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meteo);

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
                AfficherTexte(temp, tempe);
                AfficherTexte(cityName, vill);
                AfficherTexte(pressure, press);
                AfficherTexte(humidity, hum);
                AfficherTexte(cloud, des);
                AfficherTexte(wind, wi);
                AfficherTexte(sunrise, sunr);
                AfficherTexte(sunset, suns);
                new LoadImage(imgIcon).execute(imageURL+icon+".png");
                fond=fond+data.getDescription();
                //AfficherTexte(imgIcon, icon)

                AfficherImage(relMeteo, fond);

            }
        });

    }

    //descargar imagen

    class LoadImage extends AsyncTask<String, Void, Bitmap> {

        private final WeakReference<ImageView> imageViewReference;

        public LoadImage(ImageView imageView) {
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                return downloadBitmap(params[0]);
            } catch (Exception e) {
                Log.e("LoadImage class", "doInBackground() " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }

            if (imageViewReference != null) {
                ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    if (bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                    }
                }
            }
        }

        private Bitmap downloadBitmap(String url) {
            HttpURLConnection urlConnection = null;
            try {
                URL uri = new URL(url);
                urlConnection = (HttpURLConnection) uri.openConnection();
                int statusCode = urlConnection.getResponseCode();
                if (statusCode != HttpURLConnection.HTTP_OK) {
                    return null;
                }

                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream != null) {
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    return bitmap;
                }
            } catch (Exception e) {
                urlConnection.disconnect();
                Log.e("LoadImage class", "Descargando imagen desde url: " + url);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }
    }
//hasta aqui la descarga



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




    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.changeVille){
        }
        return super.onOptionsItemSelected(item);
    }
}
