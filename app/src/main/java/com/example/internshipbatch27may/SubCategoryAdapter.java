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

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.MyHolder> {

    Context context;
    ArrayList<SubcategoryList> arrayList;
    SharedPreferences sp;


    public SubCategoryAdapter(Context context, ArrayList<SubcategoryList> arraylist) {
        this.context = context;
        this.arrayList = arraylist;
        sp = context.getSharedPreferences(ConstantSp.pref, MODE_PRIVATE);
    }

    @NonNull
    @Override
    public SubCategoryAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new SubCategoryAdapter.MyHolder(view);
    }

    public class MyHolder extends  RecyclerView.ViewHolder {

        ImageView category_image;
        TextView category_text;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            category_image = itemView.findViewById(R.id.category_image);
            category_text = itemView.findViewById(R.id.category_text);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryAdapter.MyHolder holder, int position) {
        holder.category_image.setImageResource(arrayList.get(position).getImage());
        holder.category_text.setText(arrayList.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.edit().putString(ConstantSp.subcategoryid, String.valueOf(arrayList.get(position).getSubcategoryid())).commit();
                Intent intent = new Intent(context, ProductActivity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


}
