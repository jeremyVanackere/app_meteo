package iut.calais.app_meteo;

import com.google.gson.Gson;

import iut.calais.app_meteo.bean.Main;
import iut.calais.app_meteo.bean.Rep;
import iut.calais.app_meteo.bean.Sys;
import iut.calais.app_meteo.bean.Weather;
import iut.calais.app_meteo.bean.Wind;

public class Data {

    private String ville;
    private float temperature;
    private Rep reponseData;
    private float pression;
    private float Humidite;
    private String temps;
    private String description;
    private String icon;
    private float speed;
    private float deg;
    private long sunrise;
    private long sunset;

    public void setData(String JsonData)
    {
        final Gson gson = new Gson();
        Rep rep = gson.fromJson(JsonData, Rep.class);
        Main main = rep.getMain();
        Wind wind = rep.getWind();
        Sys sys = rep.getSys();
        Weather weather = rep.getWeather()[0];

        setTemperature(main.getTemp());
        setVille(rep.getName());
        setPression(main.getPressure());
        setHumidite(main.getHumidity());
        setTemps(weather.getMain());
        setDescription(weather.getDescription());
        setIcon(weather.getIcon());
        setSpeed(wind.getSpeed());
        setDeg(wind.getDeg());
        setSunrise(sys.getSunrise());
        setSunset(sys.getSunset());
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public Rep getReponseData() {
        return reponseData;
    }

    public void setReponseData(Rep reponseData) {
        this.reponseData = reponseData;
    }

    public float getPression() {
        return pression;
    }

    public void setPression(float pression) {
        this.pression = pression;
    }

    public float getHumidite() {
        return Humidite;
    }

    public void setHumidite(float humidite) {
        Humidite = humidite;
    }

    public String getTemps() {
        return temps;
    }

    public void setTemps(String temps) {
        this.temps = temps;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getDeg() {
        return deg;
    }

    public void setDeg(float deg) {
        this.deg = deg;
    }

    public long getSunrise() {
        return sunrise;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }
}
