package com.example.inferno.rentmystuff;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserMessage extends AppCompatActivity {

    EditText edtMsg;
    Button btnSendMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_message);
        edtMsg = findViewById(R.id.edtMsg);
        btnSendMessage = findViewById(R.id.btnSendMessage);

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                        String msg = edtMsg.getText().toString();

                    SmsManager s = SmsManager.getDefault();

                    s.sendTextMessage(SeeProducts.itemRenterContact, null, msg, null, null);

                    Toast.makeText(UserMessage.this, "Sent...!!!", Toast.LENGTH_SHORT).show();
                }
                catch (Exception k){}

            }
        });
    }
}
