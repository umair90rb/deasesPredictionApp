package com.fyp.developer.heartdiseasepredictionsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forget_Password extends AppCompatActivity implements View.OnClickListener {

    EditText emails;

    Button fp_btn_back,fp_btn_chng_pass;

//    LinearLayout forget_password;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget__password);

        fp_btn_chng_pass = findViewById(R.id.fp_btn_chang_pass);
        fp_btn_back = findViewById(R.id.fp_btn_back);
        emails=findViewById(R.id.enter_email);
        fp_btn_chng_pass.setOnClickListener(this);
        fp_btn_back.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
    }


    @Override
    public void onClick(View v) {

        if (v.getId()== R.id.fp_btn_back){
            startActivity(new Intent(forget_Password.this,login_screen.class));
        }
        if (v.getId()==R.id.fp_btn_chang_pass)
        {
            resetPassword(fp_btn_chng_pass.getText().toString());
        }

    }

    private void resetPassword( final String email) {
        String e_email=emails.getText().toString();
        auth.sendPasswordResetEmail(e_email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(forget_Password.this,"Password has been sent", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(forget_Password.this,"Failed to send password", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }


}


