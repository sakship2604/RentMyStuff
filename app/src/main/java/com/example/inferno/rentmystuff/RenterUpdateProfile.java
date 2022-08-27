package com.example.inferno.rentmystuff;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RenterUpdateProfile extends AppCompatActivity {

    EditText edtRUpdateName,edtRUpdateEmail,edtRUpdateContactNo,edtRUpdatePassword,edtRUpdateAddress,edtRUpdateCity,edtRUpdateState;
    Button btnRUpdate,btnRUpdateCancel;
    String url = "http://www.rsservicejaipur.com/rentMyStuff/updateRenterProfile.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_update_profile);

        edtRUpdateName = findViewById(R.id.edtRUpdateName);
        edtRUpdateEmail = findViewById(R.id.edtRUpdateEmail);
        edtRUpdateContactNo = findViewById(R.id.edtRUpdateContactNo);
        edtRUpdatePassword = findViewById(R.id.edtRUpdatePassword);
        edtRUpdateAddress = findViewById(R.id.edtRUpdateAddress);
        edtRUpdateCity = findViewById(R.id.edtRUpdateCity);
        edtRUpdateState = findViewById(R.id.edtRUpdateState);
        btnRUpdate = findViewById(R.id.btnRUpdate);
        btnRUpdateCancel = findViewById(R.id.btnRUpdateCancel);

        edtRUpdateName.setText(Login.renterName);
        edtRUpdateEmail.setText(Login.renterEmail);
        edtRUpdateContactNo.setText(Login.renterContactNo);
        edtRUpdatePassword.setText(Login.renterPassword);
        edtRUpdateAddress.setText(Login.renterAddress);
        edtRUpdateState.setText(Login.renterState);
        edtRUpdateCity.setText(Login.renterCity);

        btnRUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = edtRUpdateName.getText().toString();
                final String email = edtRUpdateEmail.getText().toString();
                final String contactNo = edtRUpdateContactNo.getText().toString();
                final String password = edtRUpdatePassword.getText().toString();
                final String address = edtRUpdateAddress.getText().toString();
                final String city = edtRUpdateCity.getText().toString();
                final String state = edtRUpdateState.getText().toString();

                if ((name.length()==0)||(email.length()==0)||(!isValidEmail(email))||(contactNo.length()==0)||(password.length()==0)||(address.length()==0)||(city.length()==0)||(state.length()==0)){
                    if (name.length()==0){
                        edtRUpdateName.setError("Enter your Name");
                        edtRUpdateName.requestFocus();
                    }
                    if (email.length()==0){
                        edtRUpdateEmail.setError("Enter your Email ID");
                        edtRUpdateEmail.requestFocus();
                    }
                    if (!isValidEmail(email)){
                        edtRUpdateEmail.setError("Enter Valid Email ID");
                        edtRUpdateEmail.requestFocus();
                    }
                    if (contactNo.length()==0){
                        edtRUpdateContactNo.setError("Enter your Contact No");
                        edtRUpdateContactNo.requestFocus();
                    }
                    if (password.length()==0){
                        edtRUpdatePassword.setError("Enter your Password");
                        edtRUpdatePassword.requestFocus();
                    }
                    if (address.length()==0){
                        edtRUpdateAddress.setError("Enter your Address");
                        edtRUpdateAddress.requestFocus();
                    }
                    if (city.length()==0){
                        edtRUpdateCity.setError("Enter your City");
                        edtRUpdateCity.requestFocus();
                    }
                    if (state.length()==0){
                        edtRUpdateState.setError("Enter your State");
                        edtRUpdateState.requestFocus();
                    }
                }
                else {
                    update();
                }



            }
        });
    }

    private void update() {
        final String name = edtRUpdateName.getText().toString();
        final String email = edtRUpdateEmail.getText().toString();
        final String contactNo = edtRUpdateContactNo.getText().toString();
        final String password = edtRUpdatePassword.getText().toString();
        final String address = edtRUpdateAddress.getText().toString();
        final String city = edtRUpdateCity.getText().toString();
        final String state = edtRUpdateState.getText().toString();


        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                loading.dismiss();

                Toast.makeText(RenterUpdateProfile.this, "You have successfully Updated your Profile", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(RenterUpdateProfile.this,RenterHome.class);
                startActivity(i);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();

                Toast.makeText(RenterUpdateProfile.this, "Please try again..."+error, Toast.LENGTH_LONG).show();

            }
        }) {
            protected Map<String, String> getParams() {

                // image = getStringImage(bitmap);

                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("renterId",Login.renterId);
                parameters.put("renterName",name);
                parameters.put("renterEmail",email);
                parameters.put("renterContact",contactNo);
                parameters.put("renterPassword",password);
                parameters.put("renterAddress",address);
                parameters.put("renterCity",city);
                parameters.put("renterState",state);


                return parameters;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(RenterUpdateProfile.this);
        rQueue.add(request);

    }

    private boolean isValidEmail(String email)
    {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
