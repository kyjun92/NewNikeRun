package com.cookandroid.newnike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class HomeMainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    byte timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        setTitle("메인화면");

        int loginedMemberId = getIntent().getIntExtra("loginedMemberId", 0);

        Member loginedMember = AppDatabase.findMember(loginedMemberId);

        TextView textViewLoginId = findViewById(R.id.textViewLoginMember);
        textViewLoginId.setText(loginedMember.getName()+"님 환영합니다!");
        timer = 0;

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (timer < 5) {
                    timer++;
                    handler.postDelayed(this, 300);
                }
                else {
                    finish();
                    Intent intent = new Intent(HomeMainActivity.this, NikeActivity.class);
                    startActivity(intent);
                }
            }
        }, 0);






    }
    @Override
    public void onMapReady(final GoogleMap googleMap) {

        mMap = googleMap;

        LatLng SEOUL = new LatLng(37.56, 126.97);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("서울");
        markerOptions.snippet("한국의 수도");
        mMap.addMarker(markerOptions);


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SEOUL, 10));


    }

}
