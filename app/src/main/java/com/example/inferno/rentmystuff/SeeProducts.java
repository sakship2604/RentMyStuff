package com.example.inferno.rentmystuff;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
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

public class SeeProducts extends AppCompatActivity {


    ListView listView;
    ImageButton btnBack1;
    ArrayList<String> itemName,itemImage;
    static String itemName1,itemCostDetails,itemDetails,itemImage1,itemRenterId,itemRenterName,itemRenterEmail,itemRenterContact,itemRenterAddress,itemRenterCity,itemRenterState;
    String renterDetailsURL = "http://www.rsservicejaipur.com/rentMyStuff/getRenterDetailWithId.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_products);

        listView = findViewById(R.id.listView);
        btnBack1 = findViewById(R.id.btnBack1);

        itemName = new ArrayList<>();
        itemImage = new ArrayList<>();

        this.itemName.addAll(SelectProduct.itemName);
        this.itemImage.addAll(SelectProduct.itemImage);

        CustomSeeProduct customSeeProduct = new CustomSeeProduct(getApplicationContext(),itemName,itemImage);
        listView.setAdapter(customSeeProduct);

        btnBack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SeeProducts.this,SelectProduct.class);
                startActivity(i);
                finish();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                itemName1 = itemName.get(i);
                itemCostDetails = SelectProduct.itemCostDetail.get(i);
                itemDetails = SelectProduct.itemDetail.get(i);
                itemImage1 = itemImage.get(i);
                itemRenterId = SelectProduct.itemRenterId.get(i);

                getRenterDetail(itemRenterId);

            }
        });

        }

    private void getRenterDetail(final String itemRenterId) {
        final ProgressDialog loading = ProgressDialog.show(this, "Checking...", "Please wait...", false, false);

        StringRequest request = new StringRequest(Request.Method.POST, renterDetailsURL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                loading.dismiss();

                String s = response;
               // Toast.makeText(SeeProducts.this, ""+s, Toast.LENGTH_LONG).show();
                getDetails(s);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();

                Toast.makeText(SeeProducts.this, "Please try again....!!!", Toast.LENGTH_LONG).show();

            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                //String image = getStringImage(bitmap);


                parameters.put("renterId",itemRenterId);



                return parameters;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(SeeProducts.this);
        rQueue.add(request);


    }

    private void getDetails(String s) {
        try {

            JSONObject j = new JSONObject(s);
            JSONArray js = j.getJSONArray("data");


            String sts= j.getString("status");

            if(sts.equals("1")){

                for (int i = 0;i<js.length();i++) {
                    JSONObject jss = js.getJSONObject(i);


                    itemRenterName = jss.getString("renterName");
                    itemRenterEmail = jss.getString("renterEmail");
                    itemRenterContact = jss.getString("renterContact");
                    itemRenterAddress = jss.getString("renterAddress");
                    itemRenterCity = jss.getString("renterCity");
                    itemRenterState = jss.getString("renterState");

                }
                //Toast.makeText(this, "Id = "+userId+"\nName = "+userName+"\nEmail = "+userEmail+"\nContact = "+userContactNo+"\nAddress = "+userAddress+"\nCity = "+userCity+"\nState = "+userState, Toast.LENGTH_SHORT).show();

                Intent i = new Intent(SeeProducts.this,UserItemDetails.class);
                startActivity(i);
                finish();


            }

            else{
                Toast.makeText(SeeProducts.this, "Please try again..?", Toast.LENGTH_LONG).show();

            }
            //Toast.makeText(MainActivity.this, "" + sts, Toast.LENGTH_LONG).show();
        }catch (Exception g){

            Toast.makeText(SeeProducts.this, ""+g, Toast.LENGTH_SHORT).show();
        }

    }

}
