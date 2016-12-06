package me.shuobi_wu.awwtter.activity;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Criteria;
import android.location.Location;
import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import me.shuobi_wu.awwtter.helper.LatLngInterpolator;
import me.shuobi_wu.awwtter.fragment.MenuFragment;
import me.shuobi_wu.awwtter.R;
import me.shuobi_wu.awwtter.model.GameModel;

import static me.shuobi_wu.awwtter.helper.MarkerAnimation.animateMarkerToGB;



//TODO: define marker info with item type
//TODO: collection view
//TODO: Fix Camera Focus
//TODO: Marker Spawner
//TODO: Custom navigation
public class BasicMapDemoActivity extends FragmentActivity implements
        OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnCameraMoveStartedListener {

    //key for our bundle
    private static final String GAME_MODEL_KEY = "GAME_DATA";

    //Constants
    private static final LatLng ABP = new LatLng(40.444205, -79.942117);
    private static final LatLng WEAN = new LatLng(40.442851, -79.945763);
    private static final LatLng SCOTT = new LatLng(40.443098, -79.946762);
    private static final LatLng NSH = new LatLng(40.443608, -79.945603);
    private static final LatLng HOMETEST = new LatLng(40.438080, -79.927693);

    //Views
    private TextView mClamCountView;
    private TextView mEnergyCountView;
    private ImageButton mMenuButton;

    //Models
    private Marker mCharacter;
    private GameModel mGameModel;
    private int mClamCount = 0;
    private int mEnergyCount = 0;

    private LocationManager mLocationManager;
    private GoogleMap mMap;
    private String mBestProvider;
    private Criteria mCriteria;
    private Location mLocation;
    private List<Marker> markers = new ArrayList<>();

    //Flags
    private int mMenuFlag = 0;

    /**
     * initiate the map layout, set up a map fragment,
     * and connect it to a location manager that could detect my location
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Here's how we restore the activity's state to what it was before android destroyed it
        if (savedInstanceState != null) {
            mGameModel = savedInstanceState.getParcelable(GAME_MODEL_KEY);
        }

        // Error handling in case our saveInstanceState parcelable is null or we were just created
        if (mGameModel == null) {
            mGameModel = new GameModel();
        }


            FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
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
            // Define a listener that responds to location updates
            LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    // Called when a new location is found by the network location provider.
                    double lat = location.getLatitude();
                    double lng = location.getLongitude();
                    LatLng newPosition = new LatLng(lat, lng);
                    animateMarkerToGB(mCharacter, newPosition, new LatLngInterpolator.Linear());
                    mGameModel.addEnergy(5);
                    mCharacter.setPosition(newPosition);
                }

                public void onStatusChanged(String provider, int status, Bundle extras) {}

                public void onProviderEnabled(String provider) {}

                public void onProviderDisabled(String provider) {}
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        initUIs();

    }

    /**
     * method to get the current location and focus the camera to my current location
     * and to set up a map
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                Log.e("Style", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("Style", "Can't find style. Error: ", e);
        }
        // Position the map's camera near Sydney, Australia.
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(-34, 151)));

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
            if (ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mLocationManager.requestLocationUpdates(mBestProvider, 1000, 0,
                    (LocationListener) this);
        }
        setupMap();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(GAME_MODEL_KEY, mGameModel);
    }

    /**
     * method to initialize the UI elements
     */
    private void initUIs() {

        mClamCountView = (TextView) findViewById(R.id.clam_counter);
        updateClamCount();
        mEnergyCountView = (TextView) findViewById(R.id.energy_counter);
        updateEnergyCount();
        mMenuButton = (ImageButton) findViewById(R.id.menu_button);
        mMenuButton.setBackground(null);
        setMenuListener();

    }

    public void setMenuListener() {
        mMenuButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                View mapView = getSupportFragmentManager().findFragmentById(R.id.map).getView();
                if (mMenuFlag == 0) {
                    mMenuFlag = 1;
                    AlphaAnimation fadeAnim = new AlphaAnimation(1.0f, 0.2f);
                    fadeAnim.setDuration(1000); //1 second
                    fadeAnim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }
                        @Override
                        public void onAnimationEnd(Animation animation) {
                            openMenu();
                        }
                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });
                    mapView.startAnimation(fadeAnim);

                }
                else {
                    AlphaAnimation alphaAnim = new AlphaAnimation(0.2f, 1.0f);
                    alphaAnim.setDuration(1000); //3 second
                    mapView.startAnimation(alphaAnim);
                    mMenuFlag = 0;
                }

            }

        });
    }

    /**
     * helper method to open a menu
     */
    private void openMenu() {
        if (mMenuFlag == 1) {
            MenuFragment menuFrag = new MenuFragment();

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.map_view, menuFrag, "menu fragment");
            transaction.addToBackStack("menu fragment");
            transaction.commit();
        }
    }

    /**
     * method to initialize a map and its UIs
     */
    private void setupMap() {

        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.getUiSettings().setIndoorLevelPickerEnabled(false);
        mMap.setBuildingsEnabled(false);
        mMap.setIndoorEnabled(true);
        initMarkersToMap();
        mMap.setOnMarkerClickListener(this);
    }


    /**
     * method to check if location is enabled on a user's device
     * @param context
     * @return
     */
    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(),
                        Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        }else{
            locationProviders = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }


    }

    /**
     * Trigger animation and destroying the marker when a marker is clicked
     * @param marker
     * @return
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker != null && markers != null && markers.contains(marker)) {
            //pass the syntactic checks
            LatLng myLocation = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
            if (haversineDistance(myLocation, marker.getPosition()) <= 1) {
                if (marker.getTitle().equals("gift")) {
                    displayImgDialog("pick up", marker);
                }
                else {
                    mGameModel.addClam(2);
                    updateClamCount();
                }
                markers.remove(marker);
                marker.remove();
            }
            else {
                startNavigation(myLocation, marker.getPosition());
            }
        }
        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }


    /**
     * add markers to the map
     */
    private void initMarkersToMap() {
        //marker of my current location. Might end up as a tile overlay
        //current location is not updated
        mCharacter = mMap.addMarker(new MarkerOptions()
                .title("Me")
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.gift_box))
                .anchor(0.5f, 0.5f)
                .position(
                        new LatLng(mLocation.getLatitude(),
                                mLocation.getLongitude())));
        Log.e("LOCATION", mLocation.getLatitude() + ", " + mLocation.getLongitude());

        Marker abp = mMap.addMarker(new MarkerOptions()
                .position(ABP)
                .title("ABP")
                .snippet("Clam: 1")
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.gift_box)));

        Marker wean = mMap.addMarker(new MarkerOptions()
                .position(HOMETEST)
                .title("Wean")
                .snippet("Clam: 2")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.fish)));

        Marker scott = mMap.addMarker(new MarkerOptions()
                .position(SCOTT)
                .title("Scott Hall")
                .snippet("Ball: 1")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ball)));

        Marker nsh = mMap.addMarker(new MarkerOptions()
                .position(NSH)
                .title("Newell Simon")
                .snippet("Clam: 2")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.fish2)));

        markers.add(abp);
        markers.add(wean);
        markers.add(scott);
        markers.add(nsh);
    }

    /**
     * a custom method to display image when clicking on a marker
     */

    //TODO: decide if this already exists
    public void displayImgDialog(String tag, Marker marker) {
        final View background =(View)findViewById(R.id.shadow);
        background.setVisibility(View.VISIBLE);

        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.pickup_item_dialog);
        dialog.setTitle(R.string.collect);

        TextView text = (TextView) dialog.findViewById(R.id.collect_item);
        ImageView itemPic = (ImageView) dialog.findViewById(R.id.item_image);
        decideImageToDisplay(itemPic, marker);

        Button ok = (Button) dialog.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                background.setVisibility(View.INVISIBLE);
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    /**
     * helper method to determine image to display on the popup window
     * @param marker
     */
    //TODO: finish this
    private void decideImageToDisplay(ImageView itemView, Marker marker) {
        itemView.setImageDrawable(getResources().getDrawable(R.drawable.otter_shy));
    }

    /**
     * update the number of clams collected in the view
     */
    private void updateClamCount() {
        if (mClamCountView == null) {
            return;
        }

        mClamCountView.setText(getString(R.string.clam_counter, mGameModel.getClamCount()));
    }

    /**
     * update the number of energy collected in the view
     */
    private void updateEnergyCount() {
        if (mEnergyCountView == null) {
            return;
        }

        mEnergyCountView.setText(getString(R.string.energy_counter, mGameModel.getEnergyCount()));
    }

    private void startNavigation(LatLng source, LatLng dest) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(
                "http://maps.google.com/maps?" + "saddr="+ source.latitude + "," + source.longitude
                        + "&daddr=" + dest.latitude + "," + dest.longitude));
        intent.setClassName("com.google.android.apps.maps","com.google.android.maps.MapsActivity");
        startActivity(intent);
    }


    /**
     * obtain the haversine distance of two location points
     * @param loc1
     * @param loc2
     * @return
     */
    public static double haversineDistance(LatLng loc1, LatLng loc2) {
        double R = 6372.8; // In kilometers
        double lat1 = loc1.latitude;
        double lon1 = loc1.longitude;
        double lat2 = loc2.latitude;
        double lon2 = loc2.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }

    @Override
    public void onCameraMoveStarted(int i) {

    }
}