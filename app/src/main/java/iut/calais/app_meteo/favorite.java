package iut.calais.app_meteo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Set;

import iut.calais.app_meteo.beans.Favoris;
import iut.calais.app_meteo.sqlite.MeteoFavorisService;

public class favorite extends AppCompatActivity {

    private TextView favo;
    private MeteoFavorisService meteoFavorisService;
    private Set<Favoris> all;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        favo = findViewById(R.id.favo);

        meteoFavorisService = new MeteoFavorisService(this);

        meteoFavorisService.open();

        all = meteoFavorisService.findAll();




    }
}
