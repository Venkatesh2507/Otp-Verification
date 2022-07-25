package com.example.otpverfication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpValidationActivity extends AppCompatActivity {
    //initializing the widgets
    PinView otpCode;
    TextView numberTv;
    ImageButton backBtn;
    Button verify;
    ProgressBar progressBar;
    String verificationCode;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_validation);
        //assigning the id's
        otpCode = findViewById(R.id.pinView);
        numberTv = findViewById(R.id.textView6);
        verify = findViewById(R.id.verifyOtp);
        backBtn = findViewById(R.id.imageButton);
        progressBar = findViewById(R.id.progressBar);
        String phoneNumber = getIntent().getStringExtra("mobile"); //phone number entered in the otp verification activity
        sendOtp(phoneNumber); //function to send the otp on user's phone
        String code = otpCode.getText().toString(); //gets the code going to be entered in pinview
        numberTv.setText(String.format("Code is sent to the number %s",getIntent().getStringExtra("mobile")));
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            } //user will be directed to previous activity
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(code.isEmpty()||code.length()<6){
                    otpCode.setError("Wrong Otp...."); // to check weather the otp entered is correct or not. handling edge cases
                    otpCode.requestFocus();
                }
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code); // function which verifies the otp
            }

        });


    }

    private void sendOtp(String phoneNumber) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91"+phoneNumber,60, TimeUnit.SECONDS,this,mCallBacks); // sends otp to the phone through PhoneAuth
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationCode = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if(code!=null){
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(OtpValidationActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show(); //toasts a message if there is any failure in otp verification
        }
    };
    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode,code);
        signInUser(credential);
    }

    private void signInUser(PhoneAuthCredential credential) { //function to make user sign in through FirebaseAuth
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithCredential(credential).addOnCompleteListener(OtpValidationActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if (task.isSuccessful()){
                       Intent intent = new Intent(OtpValidationActivity.this,ProfileActivity.class);
                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                   startActivity(intent);
                   Toast.makeText(OtpValidationActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show(); //generates toast if authentication is successful
               }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(OtpValidationActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show(); //generates a toast if there is exception is signing
            }
        });
    }
}