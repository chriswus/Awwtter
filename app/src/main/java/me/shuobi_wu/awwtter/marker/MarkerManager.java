package me.shuobi_wu.awwtter.marker;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.shuobi_wu.awwtter.R;
import me.shuobi_wu.awwtter.marker.MarkerInfo;

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
    private List<Marker> mMarkers = new ArrayList<>();
    private Map<Marker, MarkerInfo> mMarkerInfoMap = new HashMap<>();
    private Map<String, Marker> mMarkerTags = new HashMap<>();

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
            mMarkers.add(abp);
            mMarkers.add(wean);
            mMarkers.add(scott);
            mMarkers.add(nsh);
        }
    }


    public void addMarker(Marker marker, MarkerInfo info) {
        if (mMarkers != null && mMarkerInfoMap != null && mMarkerTags != null &&
                marker != null && info != null) {
            if (!mMarkers.contains(marker) && !mMarkerInfoMap.containsKey(marker)) {
                mMarkers.add(marker);
                mMarkerInfoMap.put(marker, info);
                String tag = info.getTag();
                mMarkerTags.put(tag, marker);
            }
        }
    }

    public void removeMarker(Marker marker) {
        if (marker != null && isLegalManager()) {
            if (mMarkers.contains(marker)) {
                mMarkers.remove(marker);
                MarkerInfo info = mMarkerInfoMap.get(marker);
                String tag = info.getTag();
                mMarkerInfoMap.remove(marker);
                mMarkerTags.remove(tag);
                marker.remove();
            }
        }
    }

    public void replaceInfo(Marker marker, MarkerInfo info) {
        if (marker != null && isLegalManager()) {
            if (mMarkers.contains(marker)) {
                mMarkerInfoMap.put(marker, info);
            }
        }
    }

    public void replaceTag(Marker marker, String tag) {
        if (marker != null && isLegalManager()) {
            if (mMarkers.contains(marker)) {
                if (mMarkerTags.containsKey(tag)) {
                    MarkerInfo info = mMarkerInfoMap.get(marker);
                    info.setTag(tag);
                    mMarkerTags.put(tag, marker);
                }
                else {
                    System.out.println("This tag: " + tag + " already exists, pick a new tag");
                }
            }
        }
    }

    /**
     * check if a manager has legal data.
     * @return
     */
    public boolean isLegalManager() {
        if (mMarkers != null && mMarkerInfoMap != null && mMarkerTags != null) {
            if (mMarkers.size() == mMarkerInfoMap.size() && mMarkers.size() == mMarkerTags.size()) {
                Set<Marker> markerSet = new HashSet<Marker>(mMarkers);
                if (markerSet.size() != mMarkers.size()) return false;
                for (Marker marker: mMarkers) {
                    if (!mMarkerInfoMap.containsKey(marker)) {
                        return false;
                    }
                    if (!mMarkerTags.containsValue(marker)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public Marker getMarkerAt(int index) {
        if (mMarkers != null && index >= 0 && index < mMarkers.size()) {
            return mMarkers.get(index);
        }
        return null;
    }

    public Marker getMarkerWithTag(String tag) {
        if (mMarkers != null && tag != null && mMarkerTags != null) {
            return mMarkerTags.get(tag);
        }
        return null;
    }

    public List<Marker> getMarkers() {
        if (mMarkers != null) {
            return mMarkers;
        }
        return null;
    }

    public Collection<Marker> getAllTags() {
        if (mMarkerTags != null) {
            return mMarkerTags.values();
        }
        return null;
    }

}
