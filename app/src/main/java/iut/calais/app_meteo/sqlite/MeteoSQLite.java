package iut.calais.app_meteo.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Permet la création et la mise à jour du schéma de la base de données SQLite de l'appli.
 */
public class MeteoSQLite extends SQLiteOpenHelper {

    public static final String DATABASE_NOM = "meteo.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_FAVORIS = "t_favoris_fav";
    public static final String COL_ID = "fav_id";
    public static final String COL_LIBELLE = "fav_libelle";
    public static final String COL_LATITUDE = "fav_latitude";
    public static final String COL_LONGITUDE = "fav_longitude";


    /**
     * Requete de création de la table des favoris.
     */
    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_FAVORIS + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_LIBELLE + " VARCHAR NOT NULL, "
            + COL_LATITUDE + " VARCHAR NOT NULL, " + COL_LONGITUDE + " VARCHAR NOT NULL);";

    /**
     * Constructeur
     *
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public MeteoSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * Creation de la base
     *
     * @param db
     */
    @Override
    public void onCreate(final SQLiteDatabase db) {
        //on crée la table à partir de la requête écrite dans la variable CREATE_BDD
        db.execSQL(CREATE_BDD);
    }

    /**
     * Upgrade de la base de données
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        //On peut faire ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
        //comme ça lorsque je change la version les id repartent de 0
        db.execSQL("DROP TABLE " + TABLE_FAVORIS + ";");
        onCreate(db);
    }
}
