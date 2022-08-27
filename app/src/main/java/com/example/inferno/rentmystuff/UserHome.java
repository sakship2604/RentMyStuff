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

public class UserHome extends AppCompatActivity {


    ViewFlipper viewFlipper1;
    GestureDetector mGestureDetector;

    int images[] = {R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
    };
    Button btnUserProfile,btnUserProducts,btnUserUpdateProfile;
    String categoriesUrl = "http://www.rsservicejaipur.com/rentMyStuff/getCategory.php";
    static ArrayList<String> categoryUId,categoryUName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        btnUserProfile = findViewById(R.id.btnUserProfile);
        btnUserProducts = findViewById(R.id.btnUserProducts);
        btnUserUpdateProfile = findViewById(R.id.btnUserUpdateProfile);
        viewFlipper1 = (ViewFlipper)findViewById(R.id.viewFlipper1);


        categoryUId = new ArrayList<>();
        categoryUName = new ArrayList<>();

        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(UserHome.this);
            imageView.setImageResource(images[i]);
            viewFlipper1.addView(imageView);
        }

        viewFlipper1.setInAnimation(UserHome.this,android.R.anim.fade_in);
        viewFlipper1.setOutAnimation(UserHome.this,android.R.anim.fade_out);

        CustomGestureDetector1 customGestureDetector = new CustomGestureDetector1();
        mGestureDetector = new GestureDetector(UserHome.this, customGestureDetector);
        viewFlipper1.setAutoStart(true);
        viewFlipper1.setFlipInterval(2000);

        btnUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserHome.this,UserProfile.class);
                startActivity(i);
            }
        });
        btnUserUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserHome.this,UserUpdateProfile.class);
                startActivity(intent);
            }
        });
        btnUserProducts.setOnClickListener(new View.OnClickListener() {
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

                Intent i = new Intent(UserHome.this,SelectProduct.class);
                startActivity(i);
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();

                Toast.makeText(UserHome.this, "Please try again....!!!\n"+error, Toast.LENGTH_LONG).show();

            }
        });
        RequestQueue rQueue = Volley.newRequestQueue(UserHome.this);
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

                    categoryUId.add(jss.getString("categoryId"));
                    categoryUName.add(jss.getString("categoryName"));

                }
                //Toast.makeText(RenterHome.this, ""+categoryName, Toast.LENGTH_SHORT).show();


            }

            else{
                Toast.makeText(UserHome.this, "Please try again..?", Toast.LENGTH_LONG).show();

            }
            //Toast.makeText(MainActivity.this, "" + sts, Toast.LENGTH_LONG).show();
        }catch (Exception g){

            Toast.makeText(UserHome.this, ""+g, Toast.LENGTH_SHORT).show();
        }


    }
    class CustomGestureDetector1 extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            // Swipe left (next)
            if (e1.getX() > e2.getX()) {
                viewFlipper1.showNext();
            }

            // Swipe right (previous)
            if (e1.getX() < e2.getX()) {
                viewFlipper1.showPrevious();
            }

            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

}
