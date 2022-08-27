package com.example.inferno.rentmystuff;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomSeeProduct extends BaseAdapter {

    ArrayList<String> itemName = new ArrayList<>();
    ArrayList<String> itemImage = new ArrayList<>();
    Context context;
    LayoutInflater inflater;


    public CustomSeeProduct(Context applicationContext, ArrayList<String> itemName, ArrayList<String> itemImage) {
        this.itemName.addAll(SelectProduct.itemName);
        this.itemImage.addAll(SelectProduct.itemImage);
        context = applicationContext;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return itemName.size();
    }

    @Override
    public Object getItem(int i) {
        return itemName.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = inflater.inflate(R.layout.activity_custom_see_product,null);
        ImageView image = view.findViewById(R.id.imageView);
        TextView txtItemName = view.findViewById(R.id.txtItemName);

        txtItemName.setText(itemName.get(i));
        Picasso.with(context).load(itemImage.get(i)).error(R.drawable.computer1).into(image);

        return view;
    }
}
