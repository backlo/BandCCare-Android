package com.example.hansung.band_cctv.activity;

import android.Manifest;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hansung.band_cctv.R;
import com.example.hansung.band_cctv.Retrofit.Model.Response_Location;
import com.example.hansung.band_cctv.Retrofit.RetroCallback;
import com.example.hansung.band_cctv.Retrofit.RetroClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.content.Context.LOCATION_SERVICE;

public class LocationFragment extends Fragment implements OnMapReadyCallback{
    private static LocationFragment instance;

    RetroClient retroClient;

    private MapView mapView = null;
    private LatLng myPhoneLocation;
    private LatLng banduserLocation;
    private GoogleMap gMap;
    private Location location;
    private HashMap<String, Double> locationmap;
    private Marker currentLocationMaker;
    private Marker userLocationMarker;
    boolean askPermissionOnceAgain = false;
    public static boolean state2 = true;

    double longitude;
    double latitude;
    Button find_location;

    private FusedLocationProviderClient gFusedLocationClient;
    private static final int RC_LOCATION = 1;

    public static LocationFragment getInstance() {
        if (instance == null)
            instance = new LocationFragment();
        return instance;
    }

    public LocationFragment() {

    }

    @SuppressWarnings("MissingPermission")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("location map","Location onCreateView()");
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        retroClient = RetroClient.getInstance().createBaseApi();
        locationmap = new HashMap<>();
        mapView = (MapView) view.findViewById(R.id.map);
        //mapView.getMapAsync(this);

        atLocationChange();


        find_location = (Button) view.findViewById(R.id.band_location_btn);
        find_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != currentLocationMaker) {
                    currentLocationMaker.remove();
                    currentLocationMaker = null;
                }
                if (currentLocationMaker == null) {
                    currentLocationMaker = gMap.addMarker(new MarkerOptions().position(banduserLocation).title("착용자 위치").alpha(0.8f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                    gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(banduserLocation, 16));
                    currentLocationMaker.showInfoWindow();
                }
            }
        });
        return view;
    }

    @SuppressWarnings("MissingPermission")
    @AfterPermissionGranted(RC_LOCATION)
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        gMap = googleMap;
        Log.e("map", "onmapready");
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            gFusedLocationClient.getLastLocation().addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    if (task.isSuccessful() && task.getResult() != null) {
                        location = task.getResult();
                        myPhoneLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        Log.e("location info ->", "" + myPhoneLocation);
                        userLocationMarker = googleMap.addMarker(new MarkerOptions().position(myPhoneLocation).title("나의위치").alpha(0.7f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPhoneLocation, 14));
                        googleMap.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);
                        userLocationMarker.showInfoWindow();
                    }
                }
            });
        } else {
        }
    }

    @Override
    public void onStart() {
        Log.e("location fragment","Location onStart()");
        LocationThread thread = new LocationThread();
        thread.setDaemon(true);
        thread.start();
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        Log.e("location fragment","Location onStop()");
        state2 = false;
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        Log.e("location fragment","Location onResume()");
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        Log.e("location fragment","Location onPause()");
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        Log.e("location fragment","Location onDestroy()");
        super.onDestroy();
        mapView.onLowMemory();
    }
    @Override
    public void onDestroyView() {
        Log.e("location fragment", "Location onDestroyView");
        state2 = false;
        super.onDestroyView();
    }

    @SuppressWarnings("MissingPermission")
    public void atLocationChange() {
        final LocationManager locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
        final LocationListener mLocationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                Log.e("map", "atlocationchange");
                String provider = location.getProvider();
                Log.e("test", "위치정보 : " + provider + "\n위도 : " + longitude + "\n경도:" + latitude);
                //    userLocationMarker.remove();
                if(myPhoneLocation != null){
                    userLocationMarker.remove();
                }
                myPhoneLocation = new LatLng(location.getLatitude(), location.getLongitude());
                Log.e("location info ->", "" + myPhoneLocation);
                userLocationMarker = gMap.addMarker(new MarkerOptions().position(myPhoneLocation).title("나의위치").alpha(0.7f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPhoneLocation, 16));
                userLocationMarker.showInfoWindow();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }

        };
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 300, 1, mLocationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 300, 1, mLocationListener);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
        }
        mapView = (MapView) mapView.findViewById(R.id.map);
        gFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        mapView.getMapAsync(this);
    }

    public void getLocation() {
        retroClient.Get_Location(new RetroCallback() {
            @Override
            public void onError(Throwable t){}
            @Override
            public void onSuccess(int code, Object receivedData) {
                ArrayList<Response_Location> data = (ArrayList<Response_Location>) receivedData;
                Log.e("BandUserGPS: ", "" + data.get(0).getLatitude() + "," + data.get(0).getLongitude());
                banduserLocation = new LatLng(data.get(0).getLatitude(), data.get(0).getLongitude());
            }
            @Override
            public void onFailure(int code) {
                Log.e("location fail", "onfail");
            }
        });
    }

    public void stopThread(){
        state2 = false;
    }



    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 7) {
                getLocation();
            }
        }
    };

    class LocationThread extends Thread {
        @Override
        public void run() {
            while (state2 == true) {
                handler.sendEmptyMessage(7);
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

