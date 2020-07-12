package com.easy2learn.game;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ContactUsActivity extends FragmentActivity implements OnMapReadyCallback {

    private FloatingActionButton fabCall;
    private FloatingActionButton fabEmail;

    GoogleMap mapAPI;
    SupportMapFragment mapFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        fabCall = findViewById(R.id.fab_call);
        fabCall.setOnClickListener(v -> {
            call();
        });

        fabEmail = findViewById(R.id.fab_email);
        fabEmail.setOnClickListener(v -> {
            email();
        });

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapAPI);
        mapFragment.getMapAsync(this);

    }

    private void call() {

        String callPhonePermission = Manifest.permission.CALL_PHONE;
        String[] array = {callPhonePermission};
        if (ActivityCompat.checkSelfPermission(this, callPhonePermission) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, array, 0);
            return;
        }

        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:+972586783201"));
        startActivity(intent);

    }

    private void email(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL , new String[]{"zjamaozja@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Test easy2learn email");
        intent.putExtra(Intent.EXTRA_TEXT , "Test succeeded");
        try {
            startActivity(Intent.createChooser(intent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "No email client configured. Please check.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mapAPI = googleMap;
        LatLng office = new LatLng(32.4691071,34.9503135);
        mapAPI.addMarker(new MarkerOptions().position(office).title("Easy 2 Learn"));
        mapAPI.moveCamera(CameraUpdateFactory.newLatLngZoom(office, 16));

    }
}
