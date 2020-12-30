package com.cookandroid.newnike;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class NikeActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    Button btn_setting, btn_start,btn_recode;
    TextView textView6;
    EditText time_out_sec, time_out_min;
    InputMethodManager imm;

    private static final LatLng DEFAULT_LOCATION = new LatLng(37.68283920812684, 126.76936206562506);
    private static final String TAG ="google_example";
    private static final int GPS_ENABLE_REQUEST_CODE =2001;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION =2002;
    private static final int UPDATE_INTERVAL_MS = 15000;
    private static final int FASTEST_UPDATE_INTERVAL_MS =15000;
    boolean needRequest = false;

    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION };

    Location mCurrentLocation;
    LatLng currentPosition;

    FusedLocationProviderClient mFusedLocationClient;
    LocationRequest locationRequest;
    Location location;
    View mLayout;

    private GoogleMap mMap =null;
    private MapView mapView = null;
    private GoogleApiClient googleApiClient =null;
    private Marker currentMarker = null;

    private final static int MAXENTRIES =5;
    private String[] LikelyPlaceNames = null;
    private String[] LikelyAddresses = null;
    private String[] LikelyAttributions = null;
    private LatLng[] LikelyLatLngs = null;
    private double sum_dist; // 총 라이딩 거리


    //private GoogleMap googleMap;
    GroundOverlayOptions videoMark;
    MapFragment mapFrag;
    private boolean mLocationPermissionGranted;
    private Object mLastKnownLocation;
    private GoogleApiClient mGoogleApiClient;
    private LatLng mDefaultLocation;
    private LatLng mDefaultLocatio;
    private Object PermissionUtil;
    private LocationRequest mLocationRequest;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nike);

        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                        this /* OnConnectionFailedListener */)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        mGoogleApiClient.connect();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

// ============================================================================================== 1
        Intent intent1 = getIntent();
        String selectedGu = intent1.getStringExtra("result");

        btn_setting = findViewById(R.id.btn_setting);
        btn_start = findViewById(R.id.btn_start);
        btn_recode = findViewById(R.id.btn_recode);
        textView6 = findViewById(R.id.textView6);
        time_out_sec = findViewById(R.id.time_out_sec);
        time_out_min = findViewById(R.id.time_out_min);




        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeGoalMin = time_out_min.getText().toString();
                String timeGoalSec = time_out_sec.getText().toString();
                int timeGoal = Integer.parseInt(timeGoalMin) * 60 * 1000
                            + Integer.parseInt(timeGoalSec) * 1000;
                String timeGoal1 = timeGoal+"";
                Intent intent = new Intent(getApplicationContext(), NikeActivity1.class);
                Log.d("Timegoal>>>>>", ""+timeGoal);
                intent.putExtra("timeGoal", timeGoal1);
                startActivity(intent);
                //"result"  , 보낼값
            }
        });
// =============================================================================================== 1

        btn_recode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NikeActivity3.class);
                startActivity(intent);
            }
        });



    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.mMap = googleMap;
        Log.e(TAG, "onMapReady :");
        mMap = googleMap;
        LatLng ilsan1 = new LatLng(37.682148, 126.769251);
        mMap.addMarker(new MarkerOptions().position(ilsan1).title("현재위치").snippet("출발"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.682148, 126.769251), 15));
        mMap.getUiSettings().setZoomControlsEnabled(true);

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


}
