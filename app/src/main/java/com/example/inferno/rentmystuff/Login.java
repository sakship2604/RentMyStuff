package com.example.inferno.rentmystuff;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    RadioGroup rg;
    RadioButton radioButton;
    EditText edtEmail,edtPassword;
    Button btnLogin,btnCancel;
    TextView txtRegi;

    String renterUrl = "http://www.rsservicejaipur.com/rentMyStuff/renterLogin.php";
    String userUrl = "http://www.rsservicejaipur.com/rentMyStuff/userLogin.php";
    String lEmail,lPassword;
    static String userId,userName,userEmail,userContactNo,userPassword,userAddress,userCity,userState;
    static String renterId,renterName,renterEmail,renterContactNo,renterPassword,renterAddress,renterCity,renterState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        rg = findViewById(R.id.rg);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnCancel = findViewById(R.id.btnCancel);
        txtRegi = findViewById(R.id.txtRegi);


        txtRegi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int id = rg.getCheckedRadioButtonId();
                radioButton = findViewById(id);
                if (radioButton.getText().toString().equals("Renter")){

                    Intent intent = new Intent(Login.this,RenterRegister.class);
                    startActivity(intent);
                }
                if (radioButton.getText().toString().equals("User")){
                    Intent intent = new Intent(Login.this,UserRegister.class);
                    startActivity(intent);

                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lEmail = edtEmail.getText().toString();
                lPassword = edtPassword.getText().toString();

                if (lEmail.length()==0 || (!isValidEmail(lEmail)) || lPassword.length()==0){
                    if (lEmail.length()==0){
                        edtEmail.setError("Enter your Email Id");
                        edtEmail.requestFocus();
                    }
                    if (!isValidEmail(lEmail)){
                        edtEmail.setError("Enter Valid Email Id");
                        edtEmail.requestFocus();
                    }
                    if (lPassword.length()==0){
                        edtPassword.setError("Enter your Password");
                        edtPassword.requestFocus();
                    }
                }
                else {
                    int id = rg.getCheckedRadioButtonId();
                    radioButton = findViewById(id);
                    
                    if (radioButton.getText().toString().equals("Renter")){
                        loginRenter();
                    }
                    if(radioButton.getText().toString().equals("User")){
                        loginUser();
                    }
                    if (id==-1){

                        Toast.makeText(Login.this, "Please Select Category", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


    }

    private void loginUser() {
        lEmail = edtEmail.getText().toString();
        lPassword = edtPassword.getText().toString();
        final ProgressDialog loading = ProgressDialog.show(this, "Checking...", "Please wait...", false, false);

        StringRequest request = new StringRequest(Request.Method.POST, userUrl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                loading.dismiss();

                String s = response;
                //Toast.makeText(Login.this, ""+s, Toast.LENGTH_LONG).show();
                logUser(s);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();

                Toast.makeText(Login.this, "Please try again....!!!", Toast.LENGTH_LONG).show();

            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                //String image = getStringImage(bitmap);


                parameters.put("email",lEmail);
                parameters.put("password",lPassword);


                return parameters;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(Login.this);
        rQueue.add(request);


    }

    private void logUser(String s) {
        try {

            JSONObject j = new JSONObject(s);
            JSONArray js = j.getJSONArray("data");


            String sts= j.getString("status");

            if(sts.equals("1")){

                for (int i = 0;i<js.length();i++) {
                    JSONObject jss = js.getJSONObject(i);

                    userId = jss.getString("userId");
                    userName = jss.getString("userName");
                    userEmail = jss.getString("userEmail");
                    userContactNo = jss.getString("userContact");
                    userAddress = jss.getString("userAddress");
                    userCity = jss.getString("userCity");
                    userState = jss.getString("userState");
                    userPassword = jss.getString("userPassword");

                }
                //Toast.makeText(this, "Id = "+userId+"\nName = "+userName+"\nEmail = "+userEmail+"\nContact = "+userContactNo+"\nAddress = "+userAddress+"\nCity = "+userCity+"\nState = "+userState, Toast.LENGTH_SHORT).show();

                Intent i = new Intent(Login.this,UserHome.class);
                startActivity(i);
                finish();


            }

            else{
                Toast.makeText(Login.this, "Please try again..?", Toast.LENGTH_LONG).show();

            }
            //Toast.makeText(MainActivity.this, "" + sts, Toast.LENGTH_LONG).show();
        }catch (Exception g){

            Toast.makeText(Login.this, ""+g, Toast.LENGTH_SHORT).show();
        }

    }

    private void loginRenter() {
        
        lEmail = edtEmail.getText().toString();
        lPassword = edtPassword.getText().toString();
        final ProgressDialog loading = ProgressDialog.show(this, "Checking...", "Please wait...", false, false);

        StringRequest request = new StringRequest(Request.Method.POST, renterUrl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                loading.dismiss();

                String s = response;
                //Toast.makeText(Login.this, ""+s, Toast.LENGTH_LONG).show();
                logRenter(s);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();

                Toast.makeText(Login.this, "Please try again....!!!", Toast.LENGTH_LONG).show();

            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                //String image = getStringImage(bitmap);


                parameters.put("email",lEmail);
                parameters.put("password",lPassword);


                return parameters;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(Login.this);
        rQueue.add(request);



    }

    private void logRenter(String s) {
        try {

            JSONObject j = new JSONObject(s);
            JSONArray js = j.getJSONArray("data");


            String sts= j.getString("status");

            if(sts.equals("1")){

                for (int i = 0;i<js.length();i++) {
                    JSONObject jss = js.getJSONObject(i);

                    renterId = jss.getString("renterId");
                    renterName = jss.getString("renterName");
                    renterEmail = jss.getString("renterEmail");
                    renterContactNo = jss.getString("renterContact");
                    renterAddress = jss.getString("renterAddress");
                    renterCity = jss.getString("renterCity");
                    renterState = jss.getString("renterState");
                    renterPassword = jss.getString("renterPassword");

                }
                //  Toast.makeText(this, "Id = "+renterId+"\nName = "+renterName+"\nEmail = "+renterEmail+"\nContact = "+renterContactNo+"\nAddress = "+renterAddress+"\nCity = "+renterCity+"\nState = "+renterState, Toast.LENGTH_SHORT).show();

                Intent i = new Intent(Login.this,RenterHome.class);
                startActivity(i);
                finish();


            }

            else{
                Toast.makeText(Login.this, "Please try again..?", Toast.LENGTH_LONG).show();

            }
            //Toast.makeText(MainActivity.this, "" + sts, Toast.LENGTH_LONG).show();
        }catch (Exception g){

            Toast.makeText(Login.this, ""+g, Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isValidEmail(String email)
    {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
