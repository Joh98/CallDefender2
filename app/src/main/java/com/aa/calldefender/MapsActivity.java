package com.aa.calldefender;

import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String num;
    List result;
    TextView number_display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        num = getIntent().getExtras().getString("area_code");

        number_display = (TextView)findViewById(R.id.phone_number);
        number_display.setText(num);
        mapFragment.getMapAsync(this);
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
        new BackgroundReadForMap(this).execute(num);

    }

    public void show_Map(List data)
    {
        result = data;

        String area_code = (String) result.get(0);
        Float lo = Float.parseFloat((String) result.get(1));
        Float la = Float.parseFloat((String) result.get(2));
        String title = (String) result.get(3);

        LatLng location = new LatLng(la, lo);
        mMap.addMarker(new MarkerOptions().position(location).title("Approx location is " + title + " (" + area_code +")")).showInfoWindow();;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 8.0f));
    }
}
