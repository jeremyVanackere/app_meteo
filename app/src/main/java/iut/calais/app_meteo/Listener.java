package iut.calais.app_meteo;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import iut.calais.app_meteo.beans.Favoris;
import iut.calais.app_meteo.sqlite.MeteoFavorisService;

public class Listener implements View.OnClickListener {

    private Favoris fav;
    private MeteoFavorisService meteoFavorisService;
    private Context favorit;

    public Listener(Context f)
    {
        favorit = f; // On récupére le contexte Favorite
    }

    @Override
    public void onClick(View v) {
        meteoFavorisService = new MeteoFavorisService(favorit); // Ouvre une connexion vers SQLite
        meteoFavorisService.open();

        Button btn = (Button)v; // On récupere le bouton cliquer
        String name = (String) btn.getText(); // ON récupére le texte du bouton ( la ville )
        Favoris fav = meteoFavorisService.GetByLibelle(name); // On va chercher le favoris de cette ville dans SQLite

        meteoFavorisService.close(); // on ferme la connexion

        Intent meteoActivity = new Intent(favorit,meteo.class);  // on Crée un Intent pour revenir sur l'activité meteo
        meteoActivity.putExtra("latitude",Double.parseDouble(fav.getLatitude())); // On envoie les coordonnées à l'activité
        meteoActivity.putExtra("longitude",Double.parseDouble(fav.getLongitude()));
        favorit.startActivity(meteoActivity); // On start l'activité
    }
}
