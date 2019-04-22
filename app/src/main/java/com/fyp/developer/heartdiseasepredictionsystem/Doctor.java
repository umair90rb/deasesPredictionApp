package com.fyp.developer.heartdiseasepredictionsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Doctor extends AppCompatActivity implements View.OnClickListener {

    TextView disp_name,disp_address,disp_email,disp_gender,disp_No;
    ImageView m_s_image;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    private FirebaseStorage storage;
    StorageReference storageReference;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);


        disp_name = findViewById(R.id.disp_name);
        disp_address = findViewById(R.id.disp_address);
        disp_gender = findViewById(R.id.disp_gender);
        disp_No =findViewById(R.id.disp_No);
        m_s_image = findViewById(R.id.m_s_image);
        back =findViewById(R.id.back);

        back.setOnClickListener(this);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        mAuth = FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Doctor");

        Profile();
    }

    public void Profile(){

        final ProgressDialog progressdialog = ProgressDialog.show(Doctor.this,
                "Please wait", "Processing", true);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Doctor");

        String userid =mAuth.getCurrentUser().getUid();

        DatabaseReference current_user = databaseReference.child(userid);

        current_user.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressdialog.dismiss();

                String M_name = (String) dataSnapshot.child("name").getValue();
                String M_city = (String) dataSnapshot.child("city").getValue();
                String M_cell = (String) dataSnapshot.child("Cell").getValue();
                String M_image = (String) dataSnapshot.child("ImageUri").getValue();
                String M_gender = (String) dataSnapshot.child("gender").getValue();


                Glide.with(Doctor.this).load(M_image).into(m_s_image);
                disp_No.setText(M_cell);
                disp_name.setText(M_name);
                disp_address.setText(M_city);
                disp_gender.setText(M_gender);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(Doctor.this,"Network Error. Please check your connection",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.back){
            Intent intent=new Intent(Doctor.this, Doctor_dash.class);
            intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    }
}
