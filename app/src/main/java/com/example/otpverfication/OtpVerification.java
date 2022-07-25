package com.example.otpverfication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class OtpVerification extends AppCompatActivity {
    //initializing the widgets
    Button nextBtn;
    EditText mobNum;
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        //assigning the id's
        mobNum = findViewById(R.id.mobileNumber);
        nextBtn = findViewById(R.id.button2);
        back = findViewById(R.id.backImgBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mobNum.getText().toString().trim().isEmpty()){
                    Toast.makeText(OtpVerification.this, "Please enter the  mobile number", Toast.LENGTH_SHORT).show(); //generates toast if user do not enters the number and moves to next activity
                }
                else {
                    if ((mobNum.getText().toString().trim()).length()==10){
                        Intent intent = new Intent(getApplicationContext(),OtpValidationActivity.class);
                        intent.putExtra("mobile",mobNum.getText().toString());
                        startActivity(intent); //redirects user for otp validation
                        finish();
                    }
                    else {
                        Toast.makeText(OtpVerification.this, "Please enter a correct number", Toast.LENGTH_SHORT).show(); //generates toast if phone number length is less than or greater than 10
                    }
                }
            }
        });

    }
}