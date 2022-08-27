package com.example.inferno.rentmystuff;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddItem extends AppCompatActivity {

    Spinner spinner1;
    ArrayList<String> categoryName;
    String category,categoryId;
    String url = "http://www.rsservicejaipur.com/rentMyStuff/insertItem.php";
    ImageView imgItemImage;
    int PICK_IMAGE_REQUEST = 111;
    static String imageString;
    Bitmap bitmap;
    EditText edtItemName,edtItemCostDetails,edtItemDetails;
    Button btnAddItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        edtItemName = findViewById(R.id.edtItemName);
        edtItemCostDetails = findViewById(R.id.edtItemCostDetails);
        edtItemDetails = findViewById(R.id.edtItemDetails);
        btnAddItem = findViewById(R.id.btnAddItem);
        imgItemImage = findViewById(R.id.imgItemImage);

        spinner1 = findViewById(R.id.spinner1);
        categoryName = new ArrayList<>();

        this.categoryName.addAll(RenterHome.categoryName);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(AddItem.this,android.R.layout.simple_list_item_1,categoryName);
        spinner1.setAdapter(arrayAdapter);

        imgItemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);

            }
        });

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String category = (String) adapterView.getItemAtPosition(i);
                //Toast.makeText(AddItem.this, ""+i, Toast.LENGTH_SHORT).show();
                String categoryId = String.valueOf(i+1);
                categoryValues(category,categoryId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //Toast.makeText(this, ""+categoryId+"\n"+Login.renterId+"\nCat = "+category, Toast.LENGTH_SHORT).show();
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String itemName = edtItemName.getText().toString();
                final String itemCostDetail = edtItemCostDetails.getText().toString();
                final String itemDetail = edtItemDetails.getText().toString();
                final String imageString;

                //converting image to base64 string
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                getImageName(imageString);

                if (itemName.length()==0 || itemCostDetail.length()==0 || itemDetail.length()==0){
                    if (itemName.length()==0){
                        edtItemName.setError("Enter Item Name");
                        edtItemName.requestFocus();
                    }
                    if (itemCostDetail.length()==0){
                        edtItemCostDetails.setError("Enter Item Cost Details");
                        edtItemCostDetails.requestFocus();
                    }
                    if (itemDetail.length()==0){
                        edtItemDetails.setError("Enter Item Details");
                        edtItemDetails.requestFocus();
                    }

                }
                else {
                    //addItem();
                    final ProgressDialog loading = ProgressDialog.show(AddItem.this, "Uploading...", "Please wait...", false, false);

                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {


                        @Override
                        public void onResponse(String response) {
                            loading.dismiss();

                            Toast.makeText(AddItem.this, "You are successfully Add an Item", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(AddItem.this,RenterHome.class);
                            startActivity(i);



                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            loading.dismiss();

                            Toast.makeText(AddItem.this, "Please try again..."+error, Toast.LENGTH_LONG).show();

                        }
                    }) {
                        protected Map<String, String> getParams() {

                            // image = getStringImage(bitmap);

                            Map<String, String> parameters = new HashMap<String, String>();

                            parameters.put("itemCategId",categoryId);
                            parameters.put("itemRenterId",Login.renterId);
                            parameters.put("itemName",itemName);
                            parameters.put("itemCostDetail",itemCostDetail);
                            parameters.put("itemDetail",itemDetail);
                            parameters.put("itemImage",imageString);

                            return parameters;
                        }
                    };

                    RequestQueue rQueue = Volley.newRequestQueue(AddItem.this);
                    rQueue.add(request);

                }
            }
        });



    }

    private void getImageName(String imageString) {
        this.imageString = imageString;

    }

    private void categoryValues(String category, String categoryId) {
        this.category = category;
        this.categoryId = categoryId;
        Toast.makeText(this, ""+this.categoryId+"\n"+Login.renterId+"\nCat = "+this.category, Toast.LENGTH_SHORT).show();

    }

    private void addItem() {
        final String itemName = edtItemName.getText().toString();
        final String itemCostDetail = edtItemCostDetails.getText().toString();
        final String itemDetail = edtItemDetails.getText().toString();

       // Toast.makeText(this, "CatId = "+categoryId+"\nRenterId = "+Login.renterId+"Itemname = "+itemName+"\nitemCostDetails = "+itemCostDetail+"\nDetails = "+itemDetail+"\nImage = "+imageString, Toast.LENGTH_SHORT).show();

//        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
//
//        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//
//
//            @Override
//            public void onResponse(String response) {
//                loading.dismiss();
//
//                Toast.makeText(AddItem.this, "You are successfully Add an Item", Toast.LENGTH_SHORT).show();
//                Intent i = new Intent(AddItem.this,RenterHome.class);
//                startActivity(i);
//
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                loading.dismiss();
//
//                Toast.makeText(AddItem.this, "Please try again..."+error, Toast.LENGTH_LONG).show();
//
//            }
//        }) {
//            protected Map<String, String> getParams() {
//
//                // image = getStringImage(bitmap);
//
//                Map<String, String> parameters = new HashMap<String, String>();
//
//                parameters.put("itemCategId",categoryId);
//                parameters.put("itemRenterId",Login.renterId);
//                parameters.put("itemName",itemName);
//                parameters.put("itemCostDetail",itemCostDetail);
//                parameters.put("itemDetail",itemDetail);
//                parameters.put("itemImage",imageString);
//
//                return parameters;
//            }
//        };
//
//        RequestQueue rQueue = Volley.newRequestQueue(AddItem.this);
//        rQueue.add(request);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();

            try {
                //getting image from gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                //Setting image to ImageView
                imgItemImage.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
