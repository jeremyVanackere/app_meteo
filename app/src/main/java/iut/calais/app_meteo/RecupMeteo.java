package iut.calais.app_meteo;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecupMeteo {

    private double latitude;
    private double longitude;
    private OkHttpClient okHttpClient;

    public RecupMeteo(double lat, double longe){
        latitude = lat;
        longitude = longe;
        okHttpClient = new OkHttpClient();
    }

    public Call recupererMeteo()
    {
        Request req = new Request.Builder()
                .url("http://api.openweathermap.org/data/2.5/weather?lat="+ getLatitude() +"&lon="+ getLongitude() +"&units=metric&appid=d81df31027a2b337df5776d7cb7eb71e")
                .build();

        Response response = null;

        return okHttpClient.newCall(req);

    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
