package com.example.inferno.rentmystuff;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserProfile extends AppCompatActivity {

    TextView txtUName,txtUEmail,txtUContact,txtUPassword,txtUAddress,txtUCity,txtUState;
    Button btnUBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        txtUName = findViewById(R.id.txtUName);
        txtUEmail = findViewById(R.id.txtUEmail);
        txtUContact = findViewById(R.id.txtUContact);
        txtUPassword = findViewById(R.id.txtUPassword);
        txtUCity = findViewById(R.id.txtUCity);
        txtUState = findViewById(R.id.txtUState);
        txtUAddress = findViewById(R.id.txtUAddress);
        btnUBack = findViewById(R.id.btnUBack);

        txtUName.setText(Login.userName);
        txtUEmail.setText(Login.userEmail);
        txtUContact.setText(Login.userContactNo);
        txtUPassword.setText(Login.userPassword);
        txtUAddress.setText(Login.userAddress);
        txtUCity.setText(Login.userCity);
        txtUState.setText(Login.userState);

        btnUBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfile.this,UserHome.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
