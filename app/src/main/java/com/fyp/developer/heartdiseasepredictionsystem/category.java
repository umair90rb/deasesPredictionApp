package com.fyp.developer.heartdiseasepredictionsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class category extends AppCompatActivity {

    ImageView img, img2;
    TextView img_txt, img2_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        img = findViewById(R.id.patient_img);
        img2 =findViewById(R.id.doctor_img);
        img_txt =findViewById(R.id.patient);
        img2_txt = findViewById(R.id.doctor);

        img_txt.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
                Intent intent = new Intent(getApplicationContext(), Patient.class);
                startActivity(intent);
            }
        });

        img2_txt.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
                Intent intent = new Intent(getApplicationContext(), doctor_sign_up.class);
                startActivity(intent);
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(img.isPressed()) {
                    finish();
                    Intent intent = new Intent(getApplicationContext(), Patient.class);
                    startActivity(intent);
                }
            }});

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(img2.isPressed()) {
                    finish();
                    Intent intent = new Intent(getApplicationContext(), doctor_sign_up.class);
                    startActivity(intent);
                }
            }});

    }
}
