package com.fyp.developer.heartdiseasepredictionsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class doctor_sign_up extends AppCompatActivity implements View.OnClickListener {

    EditText set_email, set_pass, set_c_pass;
    Button signup_btn;
    TextView login_link;
    DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_sign_up);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Doctor");
        progressDialog = new ProgressDialog(this);
        set_email = findViewById(R.id.set_email);
        set_pass = findViewById(R.id.set_pass);
        set_c_pass = findViewById(R.id.set_c_pass);
        signup_btn = findViewById(R.id.signup_btn);
        login_link = findViewById(R.id.login_link);

        signup_btn.setOnClickListener(this);
        login_link.setOnClickListener(this);

    }

    private void registerUser() {

        final String email = set_email.getText().toString().trim();
        final String password = set_pass.getText().toString().trim();
        String con_password = set_c_pass.getText().toString().trim();

        if (email.isEmpty()) {

            set_email.setError("Email is required");
            set_email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            set_email.setError("Enter a valid email");
            set_email.requestFocus();
            return;
        }

        if (password.isEmpty()) {

            set_pass.setError("Password is required");
            set_pass.requestFocus();
            return;
        }

        if (password.length() < 8) {

            set_pass.setError("Minimum length of password should be 8");
            set_pass.requestFocus();
            return;
        }
        if (con_password.isEmpty()) {

            set_c_pass.setError("Password is required");
            set_c_pass.requestFocus();
            return;
        }

        if (con_password.length() < 8) {

            set_c_pass.setError("Minimum length of password should be 8");
            set_c_pass.requestFocus();
            return;
        }

        if (!password.equals(con_password)) {
            Toast.makeText(doctor_sign_up.this, "Both password fields must be identical", Toast.LENGTH_SHORT).show();
            return;
        }

// we will show progressbar

        progressDialog.setMessage("Please wait");
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressDialog.dismiss();
                mAuth = FirebaseAuth.getInstance();

                String id = mAuth.getCurrentUser().getUid();

                DatabaseReference user = databaseReference.child(id);
                user.child("email").setValue(email);
                user.child("Password").setValue(password);

                Toast.makeText(doctor_sign_up.this, "User Register Successful", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(doctor_sign_up.this, Doctor_Profile.class ));

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(doctor_sign_up.this, "You are already register", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_link) {
            finish();
            startActivity(new Intent(doctor_sign_up.this, login_screen.class));

        }

        if (v.getId() == R.id.signup_btn) {
            registerUser();
        }
    }
}
