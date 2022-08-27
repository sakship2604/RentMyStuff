package com.example.inferno.rentmystuff;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RenterProfile extends AppCompatActivity {

    TextView txtRName,txtREmail,txtRContact,txtRPassword,txtRAddress,txtRCity,txtRState;
    Button btnRBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_profile);

        txtRName = findViewById(R.id.txtRName);
        txtREmail = findViewById(R.id.txtREmail);
        txtRContact = findViewById(R.id.txtRContact);
        txtRPassword = findViewById(R.id.txtRPassword);
        txtRCity = findViewById(R.id.txtRCity);
        txtRState = findViewById(R.id.txtRState);
        txtRAddress = findViewById(R.id.txtRAddress);
        btnRBack = findViewById(R.id.btnRBack);

        txtRName.setText(Login.renterName);
        txtREmail.setText(Login.renterEmail);
        txtRContact.setText(Login.renterContactNo);
        txtRPassword.setText(Login.renterPassword);
        txtRAddress.setText(Login.renterAddress);
        txtRCity.setText(Login.renterCity);
        txtRState.setText(Login.renterState);

        btnRBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RenterProfile.this,RenterHome.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
