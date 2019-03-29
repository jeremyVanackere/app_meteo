package iut.calais.app_meteo;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Set;

import iut.calais.app_meteo.beans.Favoris;
import iut.calais.app_meteo.sqlite.MeteoFavorisService;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        /*MeteoFavorisService meteoFavorisService = new MeteoFavorisService(this);
        meteoFavorisService.open();
        Favoris f = new Favoris("L","l","Label");
        Favoris f1 = new Favoris("L2","l2","Label2");
        Favoris f2 = new Favoris("L3","l3","Label3");
        long insert = meteoFavorisService.insert(f);
        f.setId(insert);
        long insert1 = meteoFavorisService.insert(f1);
        f1.setId(insert1);
        long insert2 = meteoFavorisService.insert(f2);
        f2.setId(insert2);
        Set<Favoris> all = meteoFavorisService.findAll();
        long delete = meteoFavorisService.delete(f.getId());
        Set<Favoris> allAfterDelete = meteoFavorisService.findAll();
        long update = meteoFavorisService.update(f1.getId(),f2);
        Set<Favoris> all2 = meteoFavorisService.findAll();

        meteoFavorisService.close();*/
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(51, 2);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
               double latitude = latLng.latitude;
               double longitude = latLng.longitude;

                Intent meteoActivity = new Intent(MapsActivity.this, meteo.class);
                meteoActivity.putExtra("latitude",latitude);
                meteoActivity.putExtra("longitude",longitude);
                startActivity(meteoActivity);

            }
        });
    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.fav){
            Intent favo = new Intent(MapsActivity.this,favorite.class);
            startActivity(favo);
        }
        return super.onOptionsItemSelected(item);
    }

}
