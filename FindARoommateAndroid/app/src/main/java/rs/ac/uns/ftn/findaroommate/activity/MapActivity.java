package rs.ac.uns.ftn.findaroommate.activity;

import android.app.Dialog;
import android.graphics.Camera;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Map;

import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.directionhelpers.FetchURL;
import rs.ac.uns.ftn.findaroommate.directionhelpers.TaskLoadedCallback;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {

    GoogleMap map;
    Button btnGetDirection;

    MarkerOptions adPlace, myPlace;
    LatLng adLocation;

    /*private static final String TAG = "MainActivity";

    private static final int ERROR_DIALOG_REQUEST = 9001;*/

    Polyline currenPolyline;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
      //  btnGetDirection = findViewById(R.id.btnGetDirection);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFrag);

        mapFragment.getMapAsync(this);

        Bundle extras = getIntent().getExtras();
        Float longitude = extras.getFloat("longitude");
        Float latitude = extras.getFloat("latitude");

        adPlace = new MarkerOptions().position(new LatLng(longitude, latitude)).title("Ad location");
        //place2 = new MarkerOptions().position(new LatLng(45.245720, 19.851118)).title("Location 2");
        adLocation = new LatLng(longitude, latitude);

       /* btnGetDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = getUrl(place1.getPosition(), place2.getPosition(), "driving");
                new FetchURL(MapActivity.this).execute(url, "driving");
            }
        });*/

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.addMarker(adPlace);
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(adLocation, 15.0f);
        map.animateCamera(location);

/*        map.addMarker(place2);*/
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_API_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if(currenPolyline!=null)
            currenPolyline.remove();
        currenPolyline = map.addPolyline((PolylineOptions) values[0]);
    }
}
