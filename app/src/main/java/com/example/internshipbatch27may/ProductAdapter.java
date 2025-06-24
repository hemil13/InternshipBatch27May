package com.example.internshipbatch27may;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyHolder> {
    Context context;
    ArrayList<ProductList> arrayList;

    SharedPreferences sp;

    public ProductAdapter(Context context, ArrayList<ProductList> arraylist) {
        this.context = context;
        this.arrayList = arraylist;
        sp = context.getSharedPreferences(ConstantSp.pref, MODE_PRIVATE);
    }

    @NonNull
    @Override
    public ProductAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        ImageView product_image, product_wishlist;
        TextView product_name, product_price;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            product_image = itemView.findViewById(R.id.product_image);
            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            product_wishlist = itemView.findViewById(R.id.product_wishlist);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.MyHolder holder, int position) {
        holder.product_image.setImageResource(arrayList.get(position).getProductImage());
        holder.product_name.setText(arrayList.get(position).getProductName());
        holder.product_price.setText(ConstantSp.ruppees + arrayList.get(position).getProductPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.edit().putString(ConstantSp.product_name, arrayList.get(position).getProductName()).commit();
                sp.edit().putString(ConstantSp.product_price, String.valueOf(arrayList.get(position).getProductPrice())).commit();
                sp.edit().putString(ConstantSp.product_description, arrayList.get(position).getProductDescription()).commit();
                sp.edit().putInt(ConstantSp.product_image, arrayList.get(position).getProductImage()).commit();
                Intent intent = new Intent(context, ProductDetailActivity.class);
                context.startActivity(intent);
            }
        });


        holder.product_wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.product_wishlist.setImageResource(R.drawable.wishlist_fill);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
