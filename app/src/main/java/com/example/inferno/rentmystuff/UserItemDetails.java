package com.example.inferno.rentmystuff;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class UserItemDetails extends AppCompatActivity {

    TextView txtItemDetailsName,txtItemPriceDetail,txtItemDetails,txtRenterNameDetails,txtRenterEmailDetail,txtRenterAddDetails,txtRenterCityDetail,txtRenterStateDetials;
    Button btnCall,btnMsg,btnEmail;
    ImageView imgItemDetailsImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_item_details);

        txtItemDetailsName = findViewById(R.id.txtItemDetailsName);
        txtItemPriceDetail = findViewById(R.id.txtItemPriceDetail);
        txtItemDetails = findViewById(R.id.txtItemDetails);
        txtRenterNameDetails = findViewById(R.id.txtRenterNameDetails);
        txtRenterEmailDetail = findViewById(R.id.txtRenterEmailDetail);
        txtRenterAddDetails = findViewById(R.id.txtRenterAddDetails);
        txtRenterCityDetail = findViewById(R.id.txtRenterCityDetail);
        txtRenterStateDetials = findViewById(R.id.txtRenterStateDetials);
        btnCall = findViewById(R.id.btnCall);
        btnMsg = findViewById(R.id.btnMsg);
        btnEmail = findViewById(R.id.btnEmail);
        imgItemDetailsImage = findViewById(R.id.imgItemDetailsImage);

        txtItemDetailsName.setText(SeeProducts.itemName1);
        txtItemPriceDetail.setText(SeeProducts.itemCostDetails);
        txtItemDetails.setText(SeeProducts.itemDetails);
        txtRenterNameDetails.setText(SeeProducts.itemRenterName);
        txtRenterEmailDetail.setText(SeeProducts.itemRenterEmail);
        txtRenterAddDetails.setText(SeeProducts.itemRenterAddress);
        txtRenterCityDetail.setText(SeeProducts.itemRenterCity);
        txtRenterStateDetials.setText(SeeProducts.itemRenterState);

        Picasso.with(UserItemDetails.this).load(SeeProducts.itemImage1).error(R.drawable.computer1).into(imgItemDetailsImage);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + SeeProducts.itemRenterContact));
                    if (ActivityCompat.checkSelfPermission(UserItemDetails.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    startActivity(i);
                }
                catch (Exception e){
                    Toast.makeText(UserItemDetails.this, "Please try again.....!!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(UserItemDetails.this,UserMessage.class);
               startActivity(intent);
            }
        });

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserItemDetails.this,UserMail.class);
                startActivity(intent);
            }
        });

    }
}
