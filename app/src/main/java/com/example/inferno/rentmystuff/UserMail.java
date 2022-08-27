package com.example.inferno.rentmystuff;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UserMail extends AppCompatActivity {

    EditText edtSubject,edtEmailMsg;
    Button btnSendEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_mail);

        edtSubject = findViewById(R.id.edtSubject);
        edtEmailMsg = findViewById(R.id.edtEmailMsg);
        btnSendEmail = findViewById(R.id.btnSendEmail);

        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));

                String subject = edtSubject.getText().toString();
                String msg = edtEmailMsg.getText().toString();

                emailIntent.putExtra(Intent.EXTRA_EMAIL,SeeProducts.itemRenterEmail);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT,subject);
                emailIntent.putExtra(Intent.EXTRA_TEXT,msg);
                emailIntent.setType("message/rfc822");

                startActivity(Intent.createChooser(emailIntent,"Sending Email to Renter"));

            }
        });
    }
}
