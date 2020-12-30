package com.cookandroid.newnike;

import android.Manifest;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.DateFormat;
import android.icu.text.UnicodeSet;
import android.location.Location;
import android.net.http.HttpResponseCache;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NikeActivity2 extends AppCompatActivity implements OnMapReadyCallback, LocationListener,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    Button btn_stop, btn_restart;
    TextView run_away, run_time;
    SQLiteDatabase sqlDB;
    Db db;

    String tableName;


    //private static final LatLng DEFAULT_LOCATION = new LatLng(37.68283920812684, 126.76936206562506);
    private static final LatLng DEFAULT_LOCATION = new LatLng(37.68483920812684, 126.76936206562506);
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
        setContentView(R.layout.activity_nike2);
        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                        this /* OnConnectionFailedListener */)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        mGoogleApiClient.connect();



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Intent intent = getIntent();
        String selectedGu = intent.getStringExtra("result");
        btn_stop = findViewById(R.id.btn_stop);
        btn_restart = findViewById(R.id.btn_restart);
        run_away = findViewById(R.id.run_away);
        run_time = findViewById(R.id.run_time);
        Log.d(this.getClass().getName(),(String)run_time.getText());
        run_time.setText(selectedGu);

        db = new Db(this);
        tableName = "test1";
        sqlDB = db.getWritableDatabase();




        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input_run_away = run_away.getText().toString();
                String input_run_time = run_time.getText().toString();

                ContentValues contentValues = new ContentValues();

                long now = System.currentTimeMillis();
                Date mDate = new Date(now);
                SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd");
                String formatDate = sdfNow.format(mDate);

                contentValues.put("run_away", input_run_away);
                contentValues.put("run_time", input_run_time);
                contentValues.put("time", formatDate);

                Toast.makeText(NikeActivity2.this, "저장 완료!", Toast.LENGTH_SHORT).show();
//                    저장
                sqlDB.insert(tableName, null, contentValues);



         //       sqlDB = db.getWritableDatabase();
         //       Fragment run_num = null;
         //       String time = null;
         //       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
         //       Date date = new Date();
         //       ContentValues initialValues = new ContentValues();
          //      initialValues.put("date_created", dateFormat.format(date));
          //      sqlDB.execSQL("INSERT INTO test VALUES ( '"
          //              + null  + "' , '"
          //              + run_away.getText().toString() + "' , '"
         //               + run_time.getText().toString() + "' , '"
          //              + initialValues + "');");
          //      sqlDB.close();
                print("insert 성공!");

                Intent intent =new Intent(getApplicationContext(), NikeActivity.class);
                startActivity(intent);
            }
        });

        btn_restart.setOnClickListener(new View.OnClickListener() {

            // ========================================================================================4
            @Override
            public void onClick(View view) {
                Intent intent1 =new Intent(getApplicationContext(), NikeActivity1.class);
                intent1.putExtra("result1", (String)run_time.getText());
                String timeGoal = intent.getStringExtra("timeGoal");
                Log.d("2>>>>",""+timeGoal);
                intent1.putExtra("timeGoal", timeGoal);
                startActivity(intent1);
            }

        });
            // ========================================================================================4






    }

    public void setCurrentLocation(Location location, String markerTitle, String markerSnippet){
        if (currentMarker != null) currentMarker.remove();
        if (location != null){
            //현재위치의 위도 경도 가져옴
            LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(currentLocation);
            markerOptions.title(markerTitle);
            markerOptions.snippet(markerSnippet);
            markerOptions.draggable(true);

            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            currentMarker = this.mMap.addMarker(markerOptions);
            this.mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
            return;
        }

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(DEFAULT_LOCATION);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);
        mMap.addMarker(new MarkerOptions().position(DEFAULT_LOCATION).title("출발위치").snippet("출발"));
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        currentMarker = this.mMap.addMarker(markerOptions);
        this.mMap.moveCamera(CameraUpdateFactory.newLatLng(DEFAULT_LOCATION));
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }
    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }

        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

        if (mLocationPermissionGranted) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            mMap.setMyLocationEnabled(false);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            mLastKnownLocation = null;
        }
    }

    private void createLocationRequest(){
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        
    }





    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.mMap = googleMap;

        Log.e(TAG, "onMapReady :");

        mMap = googleMap;

       // setDefaultLocation(); // GPS를 찾지 못하는 장소에 있을 경우 지도의 초기 위치가 필요함.

       // getLocationPermission();

        updateLocationUI();

       // getDeviceLocation();


        // Add a marker in Sydney and move the camera
        LatLng ilsan1 = new LatLng(37.682148, 126.769251);
        LatLng ilsan2 = new LatLng(37.686291, 126.760110);
        double cur_lat = 37.682148;
        double cur_long = 126.769251;
        double bef_lat = 37.686291;
        double bef_long = 126.760110;
        CalDistance calDistance = new CalDistance(bef_lat, bef_long, cur_lat, cur_long); // 거리계산하는 클래스 호출
        double dist = calDistance.getDistance();
        dist = (int) (dist * 100) / 100.0; // 소수점 둘째 자리 계산
        sum_dist += dist;
        run_away.setText(sum_dist+"");
        mMap.addPolyline(new PolylineOptions().color(0xFFFF0000).width(10.0f).geodesic(true).add(ilsan2).add(ilsan1));
        //googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.addMarker(new MarkerOptions().position(ilsan1).title("출발위치").snippet("출발"));
        mMap.addMarker(new MarkerOptions().position(ilsan2).title("도착위치").snippet("도착"));
        mMap.addMarker(new MarkerOptions().position(DEFAULT_LOCATION).title("도착위치").snippet("도착"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.682148, 126.769251), 15));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                videoMark = new GroundOverlayOptions().image(
                        BitmapDescriptorFactory
                                .fromResource(R.drawable.presence_video_busy))
                        .position(point, 100f, 100f);
                mMap.addGroundOverlay(videoMark);
            }
        });
    }


  /*  @SuppressWarnings("MissingPermission")
    private void getDeviceLocation() {
        if(PermissionUtil.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            mLocationPermissionGranted = true;
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            LocationServices.FusedLocationApi.requestLocationUpdates(
                            mGoogleApiClient, mLocationRequest, this
            );
        }else{
            PermissionUtil.requestFineLocationPermissions(this);
        }
    }

    private void getLocationPermission() {
    }

    private void setDefaultLocation() {
    }

*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 1, 0, "위성 지도");
        menu.add(0, 2, 0, "일반 지도");
        menu.add(0, 3, 0, "월드컵 경기장 가기");
        SubMenu sMenu = menu.addSubMenu("유명장소 바로가기 >>");
        sMenu.add(0, 3, 0, "정동진");
        sMenu.add(0, 4, 0, "해운대");
        sMenu.add(0, 5, 0, "땅끝마을");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            case 2:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case 3:
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                        37.568256, 126.897240), 15));
                return true;
            case 4:
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                        35.159003, 129.161882), 15));
                videoMark = new GroundOverlayOptions().image(
                        BitmapDescriptorFactory
                                .fromResource(R.drawable.round1))
                        .position(new LatLng(
                                35.159003, 129.161882), 100f, 100f);
                mMap.addGroundOverlay(videoMark);
                return true;
            case 5:
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                        34.301472, 126.524048), 15));
                return true;
        }
        return false;
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

    @Override
    public void onLocationChanged(Location location) {
            mCurrentLocation = location;
    }

    private void print(String result) {
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
    }
}
