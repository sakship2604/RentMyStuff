package com.example.inferno.rentmystuff;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class RenterRegister extends AppCompatActivity {

    EditText edtRRName,edtRREmail,edtRRContactNo,edtRRPassword,edtRRAddress,edtRRCity,edtRRState;
    Button btnRRRegister,btnRRCancel;
    TextView txtRRLogin;
    String url = "http://www.rsservicejaipur.com/rentMyStuff/renterRegister.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_register);

        edtRRName = findViewById(R.id.edtRRName);
        edtRREmail = findViewById(R.id.edtRREmail);
        edtRRContactNo = findViewById(R.id.edtRRContactNo);
        edtRRPassword = findViewById(R.id.edtRRPassword);
        edtRRAddress = findViewById(R.id.edtRRAddress);
        edtRRCity = findViewById(R.id.edtRRCity);
        edtRRState = findViewById(R.id.edtRRState);
        btnRRRegister = findViewById(R.id.btnRRRegister);
        btnRRCancel = findViewById(R.id.btnRRCancel);
        txtRRLogin = findViewById(R.id.txtRRLogin);

        txtRRLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RenterRegister.this,Login.class);
                startActivity(intent);
            }
        });

        btnRRRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtRRName.getText().toString();
                String email = edtRREmail.getText().toString();
                String contactNo = edtRRContactNo.getText().toString();
                String password = edtRRPassword.getText().toString();
                String address = edtRRAddress.getText().toString();
                String city = edtRRCity.getText().toString();
                String state = edtRRState.getText().toString();

                if ((name.length()==0)||(email.length()==0)||(!isValidEmail(email))||(contactNo.length()==0)||(password.length()==0)||(address.length()==0)||(city.length()==0)||(state.length()==0)){
                    if (name.length()==0){
                        edtRRName.setError("Enter your Name");
                        edtRRName.requestFocus();
                    }
                    if (email.length()==0){
                        edtRREmail.setError("Enter your Email ID");
                        edtRREmail.requestFocus();
                    }
                    if (!isValidEmail(email)){
                        edtRREmail.setError("Enter Valid Email ID");
                        edtRREmail.requestFocus();
                    }
                    if (contactNo.length()==0){
                        edtRRContactNo.setError("Enter your Contact No");
                        edtRRContactNo.requestFocus();
                    }
                    if (password.length()==0){
                        edtRRPassword.setError("Enter your Password");
                        edtRRPassword.requestFocus();
                    }
                    if (address.length()==0){
                        edtRRAddress.setError("Enter your Address");
                        edtRRAddress.requestFocus();
                    }
                    if (city.length()==0){
                        edtRRCity.setError("Enter your City");
                        edtRRCity.requestFocus();
                    }
                    if (state.length()==0){
                        edtRRState.setError("Enter your State");
                        edtRRState.requestFocus();
                    }
                }
                else {
                        register();
                }


            }
        });
    }

    private void register() {
        final String name = edtRRName.getText().toString();
        final String email = edtRREmail.getText().toString();
        final String contactNo = edtRRContactNo.getText().toString();
        final String password = edtRRPassword.getText().toString();
        final String address = edtRRAddress.getText().toString();
        final String city = edtRRCity.getText().toString();
        final String state = edtRRState.getText().toString();

        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                loading.dismiss();

                Toast.makeText(RenterRegister.this, "You are successfully Registered", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(RenterRegister.this,Login.class);
                startActivity(i);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();

                Toast.makeText(RenterRegister.this, "Please try again..."+error, Toast.LENGTH_LONG).show();

            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {

                // image = getStringImage(bitmap);

                Map<String, String> parameters = new HashMap<String, String>();

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

        RequestQueue rQueue = Volley.newRequestQueue(RenterRegister.this);
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

