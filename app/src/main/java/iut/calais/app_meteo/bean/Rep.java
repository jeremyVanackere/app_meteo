package iut.calais.app_meteo.bean;

public class Rep {
    Coordonnee coord;
    Weather[] weather;
    Main main;
    String name;

    public Coordonnee getCoord() {
        return coord;
    }

    public void setCoord(Coordonnee coord) {
        this.coord = coord;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }
}
