package me.shuobi_wu.awwtter;

import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A manager that supports storing and mapping markers
 */

public class MarkerManager {


    //test marker
    private static final LatLng ABP = new LatLng(40.444205, -79.942117);
    private static final LatLng WEAN = new LatLng(40.442851, -79.945763);
    private static final LatLng SCOTT = new LatLng(40.443098, -79.946762);
    private static final LatLng NSH = new LatLng(40.443608, -79.945603);
    private static final LatLng HOMETEST = new LatLng(40.438080, -79.927693);

    //manager logistics
    private GoogleMap mMap;
    private List<Marker> markers = new ArrayList<>();
    private Map<Marker, MarkerInfo> markerInfoMap = new HashMap<>();
    private Map<String, Marker> markerTags = new HashMap<>();

    /**
     * constructor
     */
    public MarkerManager() {
        this.mMap = null;
    }

    /**
     * constructor
     */
    public MarkerManager(GoogleMap map) {
        this.mMap = map;
    }

    /**
     * add markers to the map
     */
    private void initMarkersToMap() {
        //marker of my current location. Might end up as a tile overlay
        //current location is not updated
        if (mMap != null) {
//            Marker myMarker = mMap.addMarker(new MarkerOptions()
//                    .flat(true)
//                    .icon(BitmapDescriptorFactory
//                            .fromResource(R.drawable.otter_orig))
//                    .anchor(0.5f, 0.5f)
//                    .position(
//                            new LatLng(mLocation.getLatitude(),
//                                    mLocation.getLongitude())));
//            Log.e("LOCATION", mLocation.getLatitude() + ", " + mLocation.getLongitude());

            Marker abp = mMap.addMarker(new MarkerOptions()
                    .position(ABP)
                    .title("ABP")
                    .snippet("Clam: 1"));

            Marker wean = mMap.addMarker(new MarkerOptions()
                    .position(HOMETEST)
                    .title("Wean")
                    .snippet("Population: 4,627,300")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.fish)));

            Marker scott = mMap.addMarker(new MarkerOptions()
                    .position(SCOTT)
                    .title("Scott Hall")
                    .snippet("Population: 4,137,400")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ball)));

            Marker nsh = mMap.addMarker(new MarkerOptions()
                    .position(NSH)
                    .title("Newell Simon")
                    .snippet("Population: 1,738,800")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.fish2)));

            //markers.add(myMarker);
            markers.add(abp);
            markers.add(wean);
            markers.add(scott);
            markers.add(nsh);
        }
    }


    public void addMarker(Marker marker, MarkerInfo info) {

    }

    public Marker getMarkerAt(int index) {
        if (markers != null && index >= 0 && index < markers.size()) {
            return markers.get(index);
        }
        return null;
    }

    public Marker getMarkerWithTag(String tag) {
        if (markers != null && tag != null && markerTags != null) {
            return markerTags.get(tag);
        }
        return null;
    }




}
