package iut.calais.app_meteo.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Set;
import java.util.TreeSet;

import iut.calais.app_meteo.beans.Favoris;

import static iut.calais.app_meteo.sqlite.MeteoSQLite.COL_ID;
import static iut.calais.app_meteo.sqlite.MeteoSQLite.COL_LIBELLE;
import static iut.calais.app_meteo.sqlite.MeteoSQLite.COL_LATITUDE;
import static iut.calais.app_meteo.sqlite.MeteoSQLite.COL_LONGITUDE;

/**
 * Cette classe permet d'enregistrer et de récupérer les favoris dans la base de données
 */

public class MeteoFavorisService {

    private SQLiteDatabase bdd;

    private MeteoSQLite meteoSQLite;

    /**
     * Constructeur
     *
     * @param context
     */

    public MeteoFavorisService(final Context context) {
        //On crée la BDD et sa table
        meteoSQLite = new MeteoSQLite(context, MeteoSQLite.DATABASE_NOM, null, MeteoSQLite.DATABASE_VERSION);
    }

    /**
     * Ouverture d'une connexion
     */
    public void open() {
        //on ouvre la BDD en écriture
        bdd = meteoSQLite.getWritableDatabase();
    }

    /**
     * Fermeture de la connexion
     */
    public void close() {
        //on ferme l'accès à la BDD
        bdd.close();
    }

    /**
     * Permet d'ajouter un résultat en BDD
     *
     * @param favoris a ajouter
     * @return long, l'id du favoris
     */

    public long insert(final Favoris favoris) {
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(MeteoSQLite.COL_LATITUDE, favoris.getLatitude());
        values.put(MeteoSQLite.COL_LONGITUDE, favoris.getLongitude());
        values.put(MeteoSQLite.COL_LIBELLE, favoris.getLibelle());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(MeteoSQLite.TABLE_FAVORIS, null, values);

    }

    public long update(Long id, final Favoris favoris) {
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)

        values.put(MeteoSQLite.COL_LATITUDE, favoris.getLatitude());
        values.put(MeteoSQLite.COL_LONGITUDE, favoris.getLongitude());
        values.put(MeteoSQLite.COL_LIBELLE, favoris.getLibelle());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.update(MeteoSQLite.TABLE_FAVORIS, values, COL_ID + " = ?", new String[] {id.toString()});
    }

    public long delete(Long id  ) {

        //on insère l'objet dans la BDD via le ContentValues
        return bdd.delete(MeteoSQLite.TABLE_FAVORIS, COL_ID + " = ?", new String[] {id.toString()});
    }

    public Favoris GetByLibelle(String nomVille)
    {
        Favoris fav = null;
        final Cursor c = bdd.query(MeteoSQLite.TABLE_FAVORIS, new String[]{COL_ID, COL_LATITUDE, COL_LONGITUDE, COL_LIBELLE}, MeteoSQLite.COL_LIBELLE+"=?", new String[] { nomVille }, null, null, null);
        try {
            while (c.moveToNext()) {

                fav = new Favoris(c.getLong(c.getColumnIndex(COL_ID)), c.getString(c.getColumnIndex(COL_LATITUDE)), c.getString(c.getColumnIndex(COL_LONGITUDE)), c.getString(c.getColumnIndex(COL_LIBELLE)));
            }
        } finally {
            c.close();
        }
        return fav;
    }


    /**
     * Retourne tous les résultats
     *
     * @return Set
     */
    public Set<Favoris> findAll() {

        final Set<Favoris> favoris = new TreeSet<>();

        //Récupère dans un Cursor les valeurs correspondant à un favoris contenu dans la BDD (ici on sélectionne le favoris grâce à sa longitude, latitude et libelle)
        final Cursor c = bdd.query(MeteoSQLite.TABLE_FAVORIS, new String[]{COL_ID, COL_LATITUDE, COL_LONGITUDE, COL_LIBELLE}, null, null, null, null, null);

        try {
            while (c.moveToNext()) {

                final Favoris fav = new Favoris(c.getLong(c.getColumnIndex(COL_ID)), c.getString(c.getColumnIndex(COL_LATITUDE)), c.getString(c.getColumnIndex(COL_LONGITUDE)), c.getString(c.getColumnIndex(COL_LIBELLE)));
                favoris.add(fav);
            }
        } finally {
            c.close();
        }

        return favoris;

    }
}
