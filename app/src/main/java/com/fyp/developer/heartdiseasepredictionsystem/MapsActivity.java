package com.fyp.developer.heartdiseasepredictionsystem;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    String value;
    double latitu;
    double longitu;
    String lat,lang;
    DatabaseReference databaseReference;
    private GoogleMap mMap;
    private FirebaseAuth mAuth;
    Button save22;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        save22 = findViewById(R.id.save22);
        mAuth = FirebaseAuth.getInstance();


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Profile");
        editText = findViewById(R.id.editText);
        value = editText.getText().toString().trim();

        save22.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {


                String userid = mAuth.getCurrentUser().getUid();
                DatabaseReference current_user = databaseReference.child(userid);
                current_user.child("id").setValue(userid);
                current_user.child("latitude").setValue(lat);
                current_user.child("longitude").setValue(lang);
                current_user.child("Hostel_Location").setValue(value);

                finish();
                startActivity(new Intent(MapsActivity.this, Doctor_dash.class));
                Toast.makeText(MapsActivity.this, "Location Update", Toast.LENGTH_SHORT).show();
            }
        });


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

        //        // Add a marker in Sydney and move the camera
        //        LatLng sydney = new LatLng(-34, 151);
        //        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    public void onMapSearch(View view) {
        value= editText.getText().toString();
        List<Address> addressList = null;
        if (value != null || !value.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(value, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(latLng).snippet("Clinic Location")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)).title("Set"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.2f));

            latitu =address.getLatitude();
            longitu = address.getLongitude();

            lat = Double.toString(latitu);
            lang = Double.toString(longitu);


        }
    }
}
