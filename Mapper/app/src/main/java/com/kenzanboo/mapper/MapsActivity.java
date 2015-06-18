package com.kenzanboo.mapper;

import android.graphics.Camera;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private LatLng thinkfulLatLng = new LatLng(40.72493, -73.996599);
    private float defaultZoom = (float) 18.2;
    private int animateDelay = 3000;
    private int animateDuration = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        final Marker thinkfulMarker = mMap.addMarker(new MarkerOptions()
                        .position(thinkfulLatLng)
                        .title("Thinkful Headquarters")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.thinkful))
                        .snippet("On a mission to think thoughtful thoughts")
        );

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        CameraUpdate thinkfulUpdate = CameraUpdateFactory.newLatLngZoom(thinkfulLatLng, 10);
        final CameraUpdate zoom = CameraUpdateFactory.zoomTo(defaultZoom);
        mMap.moveCamera(thinkfulUpdate);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            GoogleMap.CancelableCallback showThinkfulInfo = new GoogleMap.CancelableCallback() {
                @Override
                public void onFinish() {
                    thinkfulMarker.showInfoWindow();
                }
                @Override
                public void onCancel() {}
            };
            @Override
            public void run() {
                mMap.animateCamera(zoom, animateDuration, showThinkfulInfo);
            }
        }, animateDelay);
    }
}
