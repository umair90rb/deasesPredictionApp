package com.fyp.developer.heartdiseasepredictionsystem;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class Doctor_Profile extends AppCompatActivity implements View.OnClickListener {
    EditText m_fullname, m_gender, m_city, m_cell_no;
    ImageView m_pro_image;
    public Uri imageuri;
    Button btn_save_m_detail;
    private FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    String city;
    String  no;
    String  gender;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor__profile);

            m_fullname = findViewById(R.id.m_full_name);
            m_gender = findViewById(R.id.m_gender);
            m_city = findViewById(R.id.m_city);
            m_cell_no = findViewById(R.id.m_cell_no);
            m_pro_image = findViewById(R.id.m_pro_image);
            btn_save_m_detail = findViewById(R.id.btn_save_m_detail);

            storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference();

            m_pro_image.setOnClickListener(this);
            btn_save_m_detail.setOnClickListener(this);

            mAuth = FirebaseAuth.getInstance();
            databaseReference= FirebaseDatabase.getInstance().getReference().child("Doctor");

        }

        private void logInUser() {
            name = m_fullname.getText().toString().trim();
            gender = m_gender.getText().toString().trim();
            city = m_city.getText().toString().trim();
            no = m_cell_no.getText().toString().trim();

            if (name.isEmpty()) {

                m_fullname.setError("Name required");
                m_fullname.requestFocus();
                return;
            }
            if (gender.isEmpty()) {

                m_gender.setError("Gender required");
                m_gender.requestFocus();
                return;
            }

            if (city.isEmpty()) {

                m_city.setError("City required");
                m_city.requestFocus();
                return;
            }

            if (no.isEmpty()) {

                m_cell_no.setError("Cell No required");
                m_cell_no.requestFocus();
                return;
            }

            final ProgressDialog progressdialog = ProgressDialog.show(Doctor_Profile.this,
                    "Please wait", "Processing", true);
            StorageReference refe=storageReference.child("images/"+ System.currentTimeMillis() + "." +
                    GetFileExtension(imageuri));

            refe.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    progressdialog.dismiss();

                    String userid =mAuth.getCurrentUser().getUid();

                    String uri=taskSnapshot.toString();

                    DatabaseReference current_user = databaseReference.child(userid);
                    current_user.child("id").setValue(userid);
                    current_user.child("city").setValue(city);
                    current_user.child("name").setValue(name);
                    current_user.child("gender").setValue(gender);
                    current_user.child("Cell").setValue(no);
                    current_user.child("ImageUri").setValue(uri);


                    Intent intent=new Intent(Doctor_Profile.this, Doctor_dash.class);
                    intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    Toast.makeText(Doctor_Profile.this, "Uploaded", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressdialog.dismiss();
                    Toast.makeText(Doctor_Profile.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressdialog.setMessage("Uploaded record with photo "+(int)progress+"%");
                        }
                    });

        }
        public void choose() {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 1);
        }

        @Override
        public void onActivityResult(int requestcode, int resultcode, Intent data) {
            super.onActivityResult(requestcode, resultcode, data);

            imageuri = data.getData();
            try {
                InputStream i = Doctor_Profile.this.getContentResolver().openInputStream(imageuri);
                final Bitmap bit = BitmapFactory.decodeStream(i);
                m_pro_image.setImageBitmap(bit);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        private String GetFileExtension(Uri imageUri) {

            ContentResolver contentResolver = Doctor_Profile.this.getContentResolver();

            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

            // Returning the file Extension.
            return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri)) ;


        }

        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.m_pro_image) {
                choose();
            }

            if (v.getId() == R.id.btn_save_m_detail) {
                logInUser();
            }

        }
    }

