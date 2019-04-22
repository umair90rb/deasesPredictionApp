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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class login_screen extends AppCompatActivity implements View.OnClickListener {

    EditText email, pass;
    Button login_btn;
    TextView f_pass, sign_up_link;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        login_btn = findViewById(R.id.login_btn);
        f_pass = findViewById(R.id.f_pass);
        sign_up_link = findViewById(R.id.sign_up_link);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Doctor");


        login_btn.setOnClickListener(this);
        f_pass.setOnClickListener(this);
        sign_up_link.setOnClickListener(this);



        firebaseAuth= FirebaseAuth.getInstance();





    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.login_btn){

            loginUser();
        }
        if (v.getId()==R.id.sign_up_link){

            finish();
            startActivity(new Intent (login_screen.this, doctor_sign_up.class));
        }
        if(v.getId()==R.id.f_pass){
            finish();
            startActivity(new Intent(login_screen.this, forget_Password.class));
        }
    }


    private void loginUser() {
        String Email = email.getText().toString().trim();
        String password = pass.getText().toString().trim();

        if (Email.isEmpty()) {

            email.setError("Email is required");
            email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {

            email.setError("Enter a valid email");
            email.requestFocus();
            return;
        }

        if (password.isEmpty()) {

            pass.setError("Password is required");
            pass.requestFocus();
            return;
        }

        if (password.length() < 8) {

            pass.setError("Minimum length of password should be 8");
            pass.requestFocus();
            return;
        }

        final ProgressDialog progressDialog=ProgressDialog.show(login_screen.this,"Please wait",
                "processing",true);

        firebaseAuth.signInWithEmailAndPassword(Email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    startActivity(new Intent(login_screen.this,Doctor_dash.class));
                    Toast.makeText(login_screen.this,"pass",Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(login_screen.this,"failed",Toast.LENGTH_LONG).show();

            }
        });

    }
}

