package com.kenzanboo.mapper;

import android.graphics.Camera;
import android.location.Location;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.*;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private LatLng thinkfulLatLng = new LatLng(40.72493, -73.996599);
    private float defaultZoom = (float) 18.2;
    private int animateDelay = 3000;
    private int animateDuration = 3000;
    private GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;

    private ArrayList<Location> mMockedLocations = new ArrayList<Location>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        initializeFakeLocations();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //stop location updates
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }
    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            setUpMapIfNeeded();    // <-from previous tutorial
            startLocationUpdates();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }
    @Override
    public void onConnected(Bundle connectionHint) {
        Location mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        showLocation(mCurrentLocation);
        startLocationUpdates();
    }
    @Override
    public void onConnectionSuspended(int i) {
    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }
    @Override
    public void onLocationChanged(Location location) {
        if (mMockedLocations.size() > 0) {
            Location nextLocation  = mMockedLocations.remove(0);
            showLocation(nextLocation);
        }
    }


    protected void showLocation(Location mCurrentLocation) {

        if (mCurrentLocation != null) {
            Log.i("Where am I?", "Latitude: " + mCurrentLocation.getLatitude() + ", Longitude:" + mCurrentLocation.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()), 15));
            if (mLastLocation !=null) {
                PolylineOptions polyLineOptions = new PolylineOptions()
                        .add(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()))
                        .add(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
                mMap.addPolyline(polyLineOptions);
            }
            mLastLocation = mCurrentLocation;
        }
    }
    protected void startLocationUpdates() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
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
//        final Marker thinkfulMarker = mMap.addMarker(new MarkerOptions()
//                        .position(thinkfulLatLng)
//                        .title("Thinkful Headquarters")
//                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.thinkful))
//                        .snippet("On a mission to think thoughtful thoughts")
//        );
//
//        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//
//        CameraUpdate thinkfulUpdate = CameraUpdateFactory.newLatLngZoom(thinkfulLatLng, 10);
//        final CameraUpdate zoom = CameraUpdateFactory.zoomTo(defaultZoom);
//        mMap.moveCamera(thinkfulUpdate);
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            GoogleMap.CancelableCallback showThinkfulInfo = new GoogleMap.CancelableCallback() {
//                @Override
//                public void onFinish() {
//                    thinkfulMarker.showInfoWindow();
//                }
//                @Override
//                public void onCancel() {}
//            };
//            @Override
//            public void run() {
//                mMap.animateCamera(zoom, animateDuration, showThinkfulInfo);
//            }
//        }, animateDelay);

        mMap.setMyLocationEnabled(true);
    }

    private void initializeFakeLocations() {
        Location mockedCurrentLocation = new Location("location");
        mockedCurrentLocation.setLatitude(37.789045);
        mockedCurrentLocation.setLongitude(-122.401923);
        mMockedLocations.add(mockedCurrentLocation);

        Location mockedCurrentLocation1 = new Location("location");
        mockedCurrentLocation1.setLatitude(37.799045);
        mockedCurrentLocation1.setLongitude(-122.417923);
        mMockedLocations.add(mockedCurrentLocation1);

        Location mockedCurrentLocation2 = new Location("location");
        mockedCurrentLocation2.setLatitude(37.809045);
        mockedCurrentLocation2.setLongitude(-122.421923);
        mMockedLocations.add(mockedCurrentLocation2);
    }
}
