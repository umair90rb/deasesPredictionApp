package com.fyp.developer.heartdiseasepredictionsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Pridiction_Results extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pridiction__results);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("key");
            //The key argument here must match that used in the other activity
//            textView.setText("probability    "+value);
        }
    }
}
