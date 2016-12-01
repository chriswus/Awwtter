package me.shuobi_wu.awwtter;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class BasicMapDemoActivity extends FragmentActivity implements
        OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    //hard code all test locations for now
    private static final LatLng ABP = new LatLng(40.444205, -79.942117);
    private static final LatLng WEAN = new LatLng(40.442851, -79.945763);
    private static final LatLng SCOTT = new LatLng(40.443098, -79.946762);
    private static final LatLng NSH = new LatLng(40.443608, -79.945603);


    private LocationManager mLocationManager;
    private GoogleMap mMap;
    private String mBestProvider;
    private Criteria mCriteria;
    private Location mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        try {
            mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(myIntent);
                overridePendingTransition(R.anim.push_up_in,
                        R.anim.push_up_out);
            } else {
                mapFragment.getMapAsync(this);
                overridePendingTransition(R.anim.push_up_out,
                        R.anim.push_up_in);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (isLocationEnabled(this)) {
            mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            mCriteria = new Criteria();
            mBestProvider = String.valueOf(mLocationManager.getBestProvider(mCriteria, true));

            mLocation = mLocationManager.getLastKnownLocation(mBestProvider);
            if (mLocation != null) {
                Log.e("TAG", "GPS is on");
                final double currentLatitude = mLocation.getLatitude();
                final double currentLongitude = mLocation.getLongitude();
                LatLng loc1 = new LatLng(currentLatitude, currentLongitude);
                Log.e("TAG", currentLatitude + ", " + currentLongitude);
                mMap.addMarker(new MarkerOptions().position(loc1).title("Your Current Location"));
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(currentLatitude, currentLongitude))
                        .zoom(18)
                        .tilt(67.5f)
                        .bearing(314)
                        .build();
                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        } else {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mLocationManager.requestLocationUpdates(mBestProvider, 1000, 0, (LocationListener) this);
        }
        setupMap();
    }

    private void setupMap() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setIndoorLevelPickerEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.setIndoorEnabled(true);
        addMarkersToMap();
        mMap.setOnMarkerClickListener(this);
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        }else{
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }


    }


    private void addMarkersToMap() {
        //marker of my current location. Might end up as a tile overlay
        mMap.addMarker(new MarkerOptions()
                .flat(true)
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.otter_orig))
                .anchor(0.5f, 0.5f)
                .position(
                        new LatLng(mLocation.getLatitude(),
                                mLocation.getLongitude())));

        mMap.addMarker(new MarkerOptions()
                .position(ABP)
                .title("Brisbane")
                .snippet("Population: 2,074,200"));

        mMap.addMarker(new MarkerOptions()
                .position(WEAN)
                .title("Sydney")
                .snippet("Population: 4,627,300")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ball)));


        mMap.addMarker(new MarkerOptions()
                .position(SCOTT)
                .title("Melbourne")
                .snippet("Population: 4,137,400")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.fish)));

        mMap.addMarker(new MarkerOptions()
                .position(NSH)
                .title("Perth")
                .snippet("Population: 1,738,800")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.fish2)));
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();

        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            Toast.makeText(this,
                    marker.getTitle() +
                            " has been clicked " + clickCount + " times.",
                    Toast.LENGTH_SHORT).show();
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }
}