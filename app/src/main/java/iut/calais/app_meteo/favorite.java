package iut.calais.app_meteo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Set;

import iut.calais.app_meteo.beans.Favoris;
import iut.calais.app_meteo.sqlite.MeteoFavorisService;

public class favorite extends AppCompatActivity {

    private LinearLayout favo;
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
        meteoFavorisService.close();

        int nbFav = all.size();
        Listener[] listeners = new Listener[nbFav];

        int pass = 0;

        for (Favoris favoris : all) {
            Button button = new Button(this);
            button.setText(favoris.getLibelle());
            listeners[pass] = new Listener(this);
            button.setOnClickListener(listeners[pass]);
            favo.addView(button);
            pass++;
        }



        View.OnClickListener myhandler ;
        myhandler = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }

        };

    }
}
