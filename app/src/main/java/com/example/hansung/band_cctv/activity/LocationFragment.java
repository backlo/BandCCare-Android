package com.example.hansung.band_cctv.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
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

public class LocationFragment extends Fragment implements OnMapReadyCallback {
    private static LocationFragment instance;

    RetroClient retroClient;

    private MapView mapView = null;
    private LatLng myPhoneLocation;
    private GoogleMap gMap;
    private Location location;
    private HashMap<String, Double> locationmap;
    private Marker currentLocationMaker;

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

    public LocationFragment(){

    }

    @SuppressWarnings("MissingPermission")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        retroClient = RetroClient.getInstance().createBaseApi();
        locationmap = new HashMap<>();
        mapView = (MapView)view.findViewById(R.id.map);
        //mapView.getMapAsync(this);
        atLocationChange();
//        LocationThread thread = new LocationThread();
//        thread.setDaemon(true);
//        thread.start();
        find_location = (Button)view.findViewById(R.id.band_location_btn);

        find_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retroClient.Get_Location(new RetroCallback() {
                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onSuccess(int code, Object receivedData) {
                        ArrayList<Response_Location> data = (ArrayList<Response_Location>)receivedData;
                        Log.e("DB에서 받은좌표",""+data.get(0).getLongitude()+","+data.get(0).getLatitude());
                    }

                    @Override
                    public void onFailure(int code) {

                    }
                });
            }
        });


        return view;
    }

    @SuppressWarnings("MissingPermission")
    @AfterPermissionGranted(RC_LOCATION)
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        gMap = googleMap;
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            gFusedLocationClient.getLastLocation().addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    if (task.isSuccessful() && task.getResult() != null) {
                        location = task.getResult();

                        myPhoneLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        Log.e("location info ->",""+myPhoneLocation);
                        googleMap.addMarker(new MarkerOptions().position(myPhoneLocation).title("나의위치").alpha(0.7f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))).showInfoWindow();
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPhoneLocation, 14));
                        googleMap.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);
                    }
                }
            });
        } else{}
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
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
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
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
        super.onDestroy();
        mapView.onLowMemory();
    }

    public void atLocationChange() {
        final LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        final LocationListener mLocationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();


                String provider = location.getProvider();
                Log.e("test", "위치정보 : " + provider + "\n위도 : " + longitude  + "\n경도:" + latitude);
                gMap.clear();
                myPhoneLocation = new LatLng(location.getLatitude(), location.getLongitude());
                Log.e("location info ->",""+myPhoneLocation);
                gMap.addMarker(new MarkerOptions().position(myPhoneLocation).title("나의위치").alpha(0.7f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))).showInfoWindow();
                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPhoneLocation, 16));

                mapUpdate();
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
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 300, 1, mLocationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 300, 1, mLocationListener);
    }

    public String getLocationinfo(){
        return String.valueOf(longitude+","+latitude);
    }

    public void mapUpdate(){
        locationmap.put("latitude",latitude);
        locationmap.put("longitude",longitude);
        retroClient.Put_Location(locationmap, new RetroCallback() {
            @Override
            public void onError(Throwable t) {
            }
            @Override
            public void onSuccess(int code, Object receivedData) {
            }
            @Override
            public void onFailure(int code) {
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(mapView != null){
            mapView.onCreate(savedInstanceState);
        }
        mapView = (MapView) mapView.findViewById(R.id.map);
        gFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        mapView.getMapAsync(this);
    }
//
//    Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            if(msg.what == 0 ){
//                getLocationinfo();
//                mapUpdate();
//            }
//        }
//    };
//    class LocationThread extends Thread{
//        @Override
//        public void run() {
//            while(true){
//                handler.sendEmptyMessage(0);
//                try{
//                    Thread.sleep(3500);
//                }catch (InterruptedException e){
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
}

