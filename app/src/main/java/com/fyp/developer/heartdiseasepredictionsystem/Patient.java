package com.fyp.developer.heartdiseasepredictionsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Patient extends AppCompatActivity {


    TextView questionLabel, questionCountLabel, scoreLabel;
    EditText answerEdt;
    Button submitButton, n_submitButton;
    ProgressBar progressBar;
    ArrayList<questionaire> questionModelArraylist;

    public int q_Probability = 0;
    String value;
    int currentPosition = 0;
    int numberOfCorrectAnswer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);


        questionCountLabel = findViewById(R.id.noQuestion);
        questionLabel = findViewById(R.id.question);
        scoreLabel = findViewById(R.id.score);

        answerEdt = findViewById(R.id.answer);
        submitButton = findViewById(R.id.submit);
        n_submitButton = findViewById(R.id.n_submit);
        progressBar = findViewById(R.id.progress);

        questionModelArraylist = new ArrayList<>();

        setUpQuestion();

        setData();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkAnswer();
            }
        });

        answerEdt.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                Log.e("event.getAction()",event.getAction()+"");
                Log.e("event.keyCode()",keyCode+"");
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    checkAnswer();
                    return true;
                }
                return false;
            }
        });

        n_submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                n_checkAnswer();
            }
        });
    }

    public void checkAnswer(){
        String answerString  = answerEdt.getText().toString().trim();

        if(answerString.equalsIgnoreCase(questionModelArraylist.get(currentPosition).getAnswer())){
            numberOfCorrectAnswer ++;
            q_Probability = q_Probability+5;
            scoreLabel.setText("probability :" + q_Probability);
            currentPosition ++;
            setData();
            answerEdt.setText("");

        }else {

            q_Probability = q_Probability+5;
            scoreLabel.setText("probability :" + q_Probability);

//            scoreLabel.setText("probability :" + numberOfCorrectAnswer + "/" + questionModelArraylist.size());
            currentPosition ++;
            setData();
            answerEdt.setText("");
        }

        int x = ((currentPosition+1) * 100) / questionModelArraylist.size();
        progressBar.setProgress(x);
    }

    public void n_checkAnswer(){
        String answerString  = answerEdt.getText().toString().trim();

        if(answerString.equalsIgnoreCase(questionModelArraylist.get(currentPosition).getAnswer())){
            currentPosition ++;
            setData();
            answerEdt.setText("");

        }else {
            currentPosition ++;
            setData();
            answerEdt.setText("");
        }

        int x = ((currentPosition+1) * 100) / questionModelArraylist.size();
        progressBar.setProgress(x);
    }


    public void setUpQuestion(){


        questionModelArraylist.add(new questionaire("Are you over age 55","Yes"));
        questionModelArraylist.add(new questionaire("Are you overweight?","Yes"));
        questionModelArraylist.add(new questionaire("Do you exercise 30 minutes daily?","Yes"));
        questionModelArraylist.add(new questionaire("Does your diet consist of high-fat foods?","Yes"));
        questionModelArraylist.add(new questionaire("Do you currently smoke? ? ","Yes"));
        questionModelArraylist.add(new questionaire("Is your blood pressure greater than 140/90 or higher?","Yes"));
        questionModelArraylist.add(new questionaire("Would you think that the stress levels in your life as high?","Yes"));
        questionModelArraylist.add(new questionaire("Are you exposed to second-hand cigarette smoke?","Yes"));
        questionModelArraylist.add(new questionaire("Have you gone for years without regular medical check-ups?","Yes"));
        questionModelArraylist.add(new questionaire("In recent years, have you fainted?","Yes"));
        questionModelArraylist.add(new questionaire("Do you have diabetes?","Yes"));
        questionModelArraylist.add(new questionaire("Have you ever had a stroke?","Yes"));
        questionModelArraylist.add(new questionaire("If you previously smoked, has it been less than five years since you quit?","Yes"));
        questionModelArraylist.add(new questionaire("Have you ever experienced discomfort in your chest, arm, jaw and pain around the shoulder blades?","Yes"));
        questionModelArraylist.add(new questionaire("Averaged, do you have the equivalent of more than one drink of alcohol per day?","Yes"));
        questionModelArraylist.add(new questionaire("Do you have an immediate family member (parent, sibling or child) who has developed, or died from heart disease before the age of 65?","Yes"));
        questionModelArraylist.add(new questionaire("Do you ever experience rapid fatigue, shortness of breath, light-headedness from modest physical exertion, such as walking or climbing stairs?","Yes"));
        questionModelArraylist.add(new questionaire("Has a blood test in recent years shown that you Gave high cholesterol?","Yes"));
        questionModelArraylist.add(new questionaire("Has a blood test in recent years shown that your LDL is too high?","Yes"));
        questionModelArraylist.add(new questionaire("As a blood test in recent years shown that your HDL is too low?","Yes"));



    }

    public void setData(){


        if(questionModelArraylist.size()>currentPosition) {

            questionLabel.setText(questionModelArraylist.get(currentPosition).getQuestionString());


            questionCountLabel.setText("Question No : " + (currentPosition + 1));


        }else{


            new SweetAlertDialog(Patient.this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Check your result...")
                    .setConfirmText("Results")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();

                            value = String.valueOf(q_Probability);
                            Intent i = new Intent(Patient.this, Pridiction_Results.class);
                            i.putExtra("key",value);
                            startActivity(i);

                        }
                    })
                    .setCancelText("Close")
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            finish();
                        }
                    })
                    .show();

        }

    }


}