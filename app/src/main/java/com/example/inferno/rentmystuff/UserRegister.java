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

public class UserRegister extends AppCompatActivity {
    EditText edtURName,edtUREmail,edtURContactNo,edtURPassword,edtURAddress,edtURCity,edtURState;
    Button btnURRegister,btnURCancel;
    TextView txtURLogin;
    String url = "http://www.rsservicejaipur.com/rentMyStuff/userRegister.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        edtURName = findViewById(R.id.edtURName);
        edtUREmail = findViewById(R.id.edtUREmail);
        edtURContactNo = findViewById(R.id.edtURContactNo);
        edtURPassword = findViewById(R.id.edtURPassword);
        edtURAddress = findViewById(R.id.edtURAddress);
        edtURCity = findViewById(R.id.edtURCity);
        edtURState = findViewById(R.id.edtURState);
        btnURRegister = findViewById(R.id.btnURRegister);
        btnURCancel = findViewById(R.id.btnURCancel);
        txtURLogin = findViewById(R.id.txtURLogin);

        txtURLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserRegister.this,Login.class);
                startActivity(intent);
            }
        });

        btnURRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtURName.getText().toString();
                String email = edtUREmail.getText().toString();
                String contactNo = edtURContactNo.getText().toString();
                String password = edtURPassword.getText().toString();
                String address = edtURAddress.getText().toString();
                String city = edtURCity.getText().toString();
                String state = edtURState.getText().toString();

                if ((name.length()==0)||(email.length()==0)||(!isValidEmail(email))||(contactNo.length()==0)||(password.length()==0)||(address.length()==0)||(city.length()==0)||(state.length()==0)){
                    if (name.length()==0){
                        edtURName.setError("Enter your Name");
                        edtURName.requestFocus();
                    }
                    if (email.length()==0){
                        edtUREmail.setError("Enter your Email ID");
                        edtUREmail.requestFocus();
                    }
                    if (!isValidEmail(email)){
                        edtUREmail.setError("Enter Valid Email ID");
                        edtUREmail.requestFocus();
                    }
                    if (contactNo.length()==0){
                        edtURContactNo.setError("Enter your Contact No");
                        edtURContactNo.requestFocus();
                    }
                    if (password.length()==0){
                        edtURPassword.setError("Enter your Password");
                        edtURPassword.requestFocus();
                    }
                    if (address.length()==0){
                        edtURAddress.setError("Enter your Address");
                        edtURAddress.requestFocus();
                    }
                    if (city.length()==0){
                        edtURCity.setError("Enter your City");
                        edtURCity.requestFocus();
                    }
                    if (state.length()==0){
                        edtURState.setError("Enter your State");
                        edtURState.requestFocus();
                    }
                }
                else {
                    regiter();
                }


            }
        });
    }

    private void regiter() {
        final String name = edtURName.getText().toString();
        final String email = edtUREmail.getText().toString();
        final String contactNo = edtURContactNo.getText().toString();
        final String password = edtURPassword.getText().toString();
        final String address = edtURAddress.getText().toString();
        final String city = edtURCity.getText().toString();
        final String state = edtURState.getText().toString();

        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                loading.dismiss();

                Toast.makeText(UserRegister.this, "You are successfully Registered", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(UserRegister.this,Login.class);
                startActivity(i);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();

                Toast.makeText(UserRegister.this, "Please try again..."+error, Toast.LENGTH_LONG).show();

            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {

                // image = getStringImage(bitmap);

                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("userName",name);
                parameters.put("userEmail",email);
                parameters.put("userContact",contactNo);
                parameters.put("userPassword",password);
                parameters.put("userAddress",address);
                parameters.put("userCity",city);
                parameters.put("userState",state);


                return parameters;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(UserRegister.this);
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
