package com.example.otpverfication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {
    //initializing the widgets
    RadioButton shippingRadio,transporterRadio;
    CardView shippingCv,transporterCv;
    Button continueBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //assigning the id's
        shippingRadio = findViewById(R.id.ShippingRb);
        transporterRadio = findViewById(R.id.TransporterRb);
        shippingCv = findViewById(R.id.shippingCardView);
        transporterCv = findViewById(R.id.transporterCardView);
        continueBtn = findViewById(R.id.continueBtn);

        shippingCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shippingRadio.setChecked(!shippingRadio.isChecked()); // if user clicks on the cardview which already checked then it unchecks it and if it is not checked it checks it
                if(transporterRadio.isChecked()){ /*ensures weather only one profile is checked*/
                    transporterRadio.setChecked(false);
                    shippingRadio.setChecked(true);
                }
            }
        });
        transporterCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transporterRadio.setChecked(!transporterRadio.isChecked());
                if(shippingRadio.isChecked()){
                    shippingRadio.setChecked(false);
                    transporterRadio.setChecked(true);
                }
            }
        });
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this,HomeActivity.class)); //directs user to home activity
            }
        });
    }
}