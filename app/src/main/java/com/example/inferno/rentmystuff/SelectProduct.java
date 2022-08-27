package com.example.inferno.rentmystuff;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SelectProduct extends AppCompatActivity {

    Spinner spinner2;
    Button btnSelect;
    static ArrayList<String> categoryUId,categoryUName;
    String getItemUrl = "http://www.rsservicejaipur.com/rentMyStuff/getItemWithCategoryId.php";
    static String categoryName,categoryId;
    static ArrayList<String> itemCategId,itemRenterId,itemName,itemCostDetail,itemDetail,itemImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_product);

        spinner2 = findViewById(R.id.spinner2);
        btnSelect = findViewById(R.id.btnSelect);

        categoryUId = new ArrayList<>();
        categoryUName = new ArrayList<>();
        this.categoryUName.addAll(UserHome.categoryUName);

        itemCategId = new ArrayList<>();
        itemRenterId = new ArrayList<>();
        itemName = new ArrayList<>();
        itemCostDetail = new ArrayList<>();
        itemDetail = new ArrayList<>();
        itemImage = new ArrayList<>();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(SelectProduct.this,android.R.layout.simple_list_item_1,categoryUName);
        spinner2.setAdapter(arrayAdapter);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String category = (String) adapterView.getItemAtPosition(i);
                String categoryId = String.valueOf(i+1);
                categoryValues(category,categoryId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectProduct();
            }
        });


    }

    private void selectProduct() {
        final ProgressDialog loading = ProgressDialog.show(this, "Checking...", "Please wait...", false, false);

        StringRequest request = new StringRequest(Request.Method.POST, getItemUrl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                loading.dismiss();

                String s = response;
            //    Toast.makeText(SelectProduct.this, ""+s, Toast.LENGTH_LONG).show();
                getItems(s);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();

                Toast.makeText(SelectProduct.this, "Please try again....!!!", Toast.LENGTH_LONG).show();

            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                //String image = getStringImage(bitmap);

                parameters.put("itemCategId",categoryId);
                return parameters;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(SelectProduct.this);
        rQueue.add(request);

    }

    private void getItems(String s) {
        try {

            JSONObject j = new JSONObject(s);
            JSONArray js = j.getJSONArray("data");


            String sts= j.getString("status");

            if(sts.equals("1")){

                for (int i = 0;i<js.length();i++) {
                    JSONObject jss = js.getJSONObject(i);

                   itemCategId.add(jss.getString("itemCategId"));
                   itemRenterId.add(jss.getString("itemRenterId"));
                   itemName.add(jss.getString("itemName"));
                   itemCostDetail.add(jss.getString("itemCostDetail"));
                   itemDetail.add(jss.getString("itemDetail"));
                   itemImage.add(jss.getString("itemImage"));
                }
                //Toast.makeText(this, "Id = "+userId+"\nName = "+userName+"\nEmail = "+userEmail+"\nContact = "+userContactNo+"\nAddress = "+userAddress+"\nCity = "+userCity+"\nState = "+userState, Toast.LENGTH_SHORT).show();

                Intent i = new Intent(SelectProduct.this,SeeProducts.class);
                startActivity(i);



            }

            else{
                Toast.makeText(SelectProduct.this, "Please try again..?", Toast.LENGTH_LONG).show();

            }
            //Toast.makeText(MainActivity.this, "" + sts, Toast.LENGTH_LONG).show();
        }catch (Exception g){

            Toast.makeText(SelectProduct.this, ""+g, Toast.LENGTH_SHORT).show();
        }

    }

    private void categoryValues(String category, String categoryId) {
        this.categoryName = category;
        this.categoryId = categoryId;
    }
}
