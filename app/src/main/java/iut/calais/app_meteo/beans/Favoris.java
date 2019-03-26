package iut.calais.app_meteo.beans;

/**
 * Représente un favoris enregistré en base de données
 */
public class Favoris implements Comparable<Favoris> {


    private Long id;
    private String longitude;
    private String latitude;
    private String libelle;

    public Favoris(Long id, String longitude, String latitude, String libelle) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.libelle = libelle;
    }

    /**
     * Constructeur
     */
    public Favoris(String longitude, String latitude, String libelle) {

        this.longitude = longitude;
        this.latitude = latitude;
        this.libelle = libelle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setlibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public int compareTo(Favoris o) {
        return id.compareTo(o.getId());
    }
}
