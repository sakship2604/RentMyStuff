package com.example.inferno.rentmystuff;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class RenterHome extends AppCompatActivity {

    ViewFlipper viewFlipper;
    GestureDetector mGestureDetector;

    int images[] = {R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
           };


    static ArrayList<String> categoryId,categoryName;
    Button btnRenterProfile,btnRenterItemList,btnRenterSeeRequest,btnRenterUpdateProfile,btnAddItem;
    String categoriesUrl = "http://www.rsservicejaipur.com/rentMyStuff/getCategory.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_home);

        btnRenterProfile = findViewById(R.id.btnRenterProfile);
        btnRenterUpdateProfile = findViewById(R.id.btnRenterUpdateProfile);
        btnAddItem = findViewById(R.id.btnAddItem);
        viewFlipper = (ViewFlipper)findViewById(R.id.viewFlipper);

        categoryId = new ArrayList<>();
        categoryName = new ArrayList<>();

        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(RenterHome.this);
            imageView.setImageResource(images[i]);
            viewFlipper.addView(imageView);
        }

        viewFlipper.setInAnimation(RenterHome.this,android.R.anim.fade_in);
        viewFlipper.setOutAnimation(RenterHome.this,android.R.anim.fade_out);

        CustomGestureDetector customGestureDetector = new CustomGestureDetector();
        mGestureDetector = new GestureDetector(RenterHome.this, customGestureDetector);
        viewFlipper.setAutoStart(true);
        viewFlipper.setFlipInterval(2000);



        btnRenterProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RenterHome.this,RenterProfile.class);
                startActivity(i);
            }
        });
        btnRenterUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RenterHome.this,RenterUpdateProfile.class);
                startActivity(i);
            }
        });

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCategories();
            }
        });
    }

    private void getCategories() {
        final ProgressDialog loading = ProgressDialog.show(this, "Loading...", "Please wait...", false, false);

        StringRequest request = new StringRequest(Request.Method.POST, categoriesUrl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                loading.dismiss();

                String s = response;
                //    Toast.makeText(Team.this, ""+s, Toast.LENGTH_LONG).show();
                abc(s);

                Intent i = new Intent(RenterHome.this,AddItem.class);
                startActivity(i);
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();

                Toast.makeText(RenterHome.this, "Please try again....!!!\n"+error, Toast.LENGTH_LONG).show();

            }
        });
        RequestQueue rQueue = Volley.newRequestQueue(RenterHome.this);
        rQueue.add(request);

    }

    private void abc(String s) {
        try {

            JSONObject j = new JSONObject(s);
            JSONArray js = j.getJSONArray("data");


            String sts= j.getString("status");

            if(sts.equals("1")){

                for (int i = 0;i<js.length();i++) {
                    JSONObject jss = js.getJSONObject(i);

                    categoryId.add(jss.getString("categoryId"));
                    categoryName.add(jss.getString("categoryName"));

                }
                 //Toast.makeText(RenterHome.this, ""+categoryName, Toast.LENGTH_SHORT).show();


            }

            else{
                Toast.makeText(RenterHome.this, "Please try again..?", Toast.LENGTH_LONG).show();

            }
            //Toast.makeText(MainActivity.this, "" + sts, Toast.LENGTH_LONG).show();
        }catch (Exception g){

            Toast.makeText(RenterHome.this, ""+g, Toast.LENGTH_SHORT).show();
        }


    }

    class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            // Swipe left (next)
            if (e1.getX() > e2.getX()) {
                viewFlipper.showNext();
            }

            // Swipe right (previous)
            if (e1.getX() < e2.getX()) {
                viewFlipper.showPrevious();
            }

            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
}




