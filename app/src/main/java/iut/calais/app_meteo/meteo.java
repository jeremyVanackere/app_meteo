package iut.calais.app_meteo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class meteo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meteo);

        double latitude = this.getIntent().getDoubleExtra("latitude",0);
        double longitude = this.getIntent().getDoubleExtra("longitude",0);

        TextView textLat = findViewById(R.id.textView);

        textLat.setText("latitude : "+latitude+" / longitude :  "+longitude);
    }
}
