package com.example.inferno.rentmystuff;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class UserUpdateProfile extends AppCompatActivity {
    EditText edtUUpdateName,edtUUpdateEmail,edtUUpdateContactNo,edtUUpdatePassword,edtUUpdateAddress,edtUUpdateCity,edtUUpdateState;
    Button btnUUpdate,btnUUpdateCancel;
    String url = "http://www.rsservicejaipur.com/rentMyStuff/updateUserProfile.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update_profile);

        edtUUpdateName = findViewById(R.id.edtUUpdateName);
        edtUUpdateEmail = findViewById(R.id.edtUUpdateEmail);
        edtUUpdateContactNo = findViewById(R.id.edtUUpdateContactNo);
        edtUUpdatePassword = findViewById(R.id.edtUUpdatePassword);
        edtUUpdateAddress = findViewById(R.id.edtUUpdateAddress);
        edtUUpdateCity = findViewById(R.id.edtUUpdateCity);
        edtUUpdateState = findViewById(R.id.edtUUpdateState);
        btnUUpdate = findViewById(R.id.btnUUpdate);
        btnUUpdateCancel = findViewById(R.id.btnUUpdateCancel);

        edtUUpdateName.setText(Login.userName);
        edtUUpdateEmail.setText(Login.userEmail);
        edtUUpdateContactNo.setText(Login.userContactNo);
        edtUUpdatePassword.setText(Login.userPassword);
        edtUUpdateAddress.setText(Login.userAddress);
        edtUUpdateState.setText(Login.userState);
        edtUUpdateCity.setText(Login.userCity);

        btnUUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = edtUUpdateName.getText().toString();
                final String email = edtUUpdateEmail.getText().toString();
                final String contactNo = edtUUpdateContactNo.getText().toString();
                final String password = edtUUpdatePassword.getText().toString();
                final String address = edtUUpdateAddress.getText().toString();
                final String city = edtUUpdateCity.getText().toString();
                final String state = edtUUpdateState.getText().toString();

                if ((name.length()==0)||(email.length()==0)||(!isValidEmail(email))||(contactNo.length()==0)||(password.length()==0)||(address.length()==0)||(city.length()==0)||(state.length()==0)){
                    if (name.length()==0){
                        edtUUpdateName.setError("Enter your Name");
                        edtUUpdateName.requestFocus();
                    }
                    if (email.length()==0){
                        edtUUpdateEmail.setError("Enter your Email ID");
                        edtUUpdateEmail.requestFocus();
                    }
                    if (!isValidEmail(email)){
                        edtUUpdateEmail.setError("Enter Valid Email ID");
                        edtUUpdateEmail.requestFocus();
                    }
                    if (contactNo.length()==0){
                        edtUUpdateContactNo.setError("Enter your Contact No");
                        edtUUpdateContactNo.requestFocus();
                    }
                    if (password.length()==0){
                        edtUUpdatePassword.setError("Enter your Password");
                        edtUUpdatePassword.requestFocus();
                    }
                    if (address.length()==0){
                        edtUUpdateAddress.setError("Enter your Address");
                        edtUUpdateAddress.requestFocus();
                    }
                    if (city.length()==0){
                        edtUUpdateCity.setError("Enter your City");
                        edtUUpdateCity.requestFocus();
                    }
                    if (state.length()==0){
                        edtUUpdateState.setError("Enter your State");
                        edtUUpdateState.requestFocus();
                    }
                }
                else {
                    update();
                }



            }
        });
    }

    private void update() {
        final String name = edtUUpdateName.getText().toString();
        final String email = edtUUpdateEmail.getText().toString();
        final String contactNo = edtUUpdateContactNo.getText().toString();
        final String password = edtUUpdatePassword.getText().toString();
        final String address = edtUUpdateAddress.getText().toString();
        final String city = edtUUpdateCity.getText().toString();
        final String state = edtUUpdateState.getText().toString();


        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                loading.dismiss();

                Toast.makeText(UserUpdateProfile.this, "You have successfully Updated your Profile", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(UserUpdateProfile.this,UserHome.class);
                startActivity(i);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();

                Toast.makeText(UserUpdateProfile.this, "Please try again..."+error, Toast.LENGTH_LONG).show();

            }
        }) {
            protected Map<String, String> getParams() {

                // image = getStringImage(bitmap);

                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("userId",Login.userId);
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

        RequestQueue rQueue = Volley.newRequestQueue(UserUpdateProfile.this);
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
