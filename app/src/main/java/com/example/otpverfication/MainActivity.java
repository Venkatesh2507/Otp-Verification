package com.example.otpverfication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    //initializing the widgets
    Spinner spinner;
    ArrayAdapter<String> arrayAdapter;
    String[] languages = {"English","Portugese","German","Chinese"}; //array of string that contains languages for dropdown
    Button nextBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //assigning the id's
        spinner = findViewById(R.id.spinner);
        nextBtn = findViewById(R.id.button);
        arrayAdapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,languages); //array adapter created for adaptation of string array
        arrayAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        if (getSupportActionBar() != null) { /*hide the action bar*/
            getSupportActionBar().hide();
        }
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,OtpVerification.class)); //user will be directed to for otp verification
            }
        });

    }
}