package com.example.internshipbatch27may;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {
    RecyclerView product_recycler;

    int[] productId = {1,2,3,4,5,6};
    int[] subcategoryId = {4,4,4,5,5,5};
    String[] productName = {"Redmi", "Oneplus", "Sony", "Airpods Max", "Sony Headphones", "Noise Headphones"};
    int[] productImage = {R.drawable.redmi, R.drawable.oneplus, R.drawable.sony,
            R.drawable.airpods_max, R.drawable.sony_headphones, R.drawable.noise};

    int[] productPrice = {10000, 20000, 30000, 40000, 50000, 60000};
    String[] productDesc = {"Redmi Description", "Oneplus Description", "Sony Description",
            "Airpods Max Description", "Sony Headphones Description",
            "Noise Headphones Description"};


    SQLiteDatabase db;
    SharedPreferences sp;

    ArrayList<ProductList> arraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product);

        sp = getSharedPreferences(ConstantSp.pref, MODE_PRIVATE);

        db  = openOrCreateDatabase("InternshipBatch27May.db", MODE_PRIVATE, null);
        String userTable = "CREATE TABLE IF NOT EXISTS user(userid INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(50), email VARCHAR(100), contact VARCHAR(10), password VARCHAR(20))";
        db.execSQL(userTable);
//
        String categoryTable = "CREATE TABLE IF NOT EXISTS category(categoryid INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(50), image VARCHAR(100))";
        db.execSQL(categoryTable);

        String subcategoryTable = "CREATE TABLE IF NOT EXISTS " +
                "subcategory(subcategoryid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "categoryid VARCHAR(10), name VARCHAR(50), image VARCHAR(100))";
        db.execSQL(subcategoryTable);

        String productTable = "CREATE TABLE IF NOT EXISTS product(productId INTEGER PRIMARY KEY AUTOINCREMENT, subcategoryid INTEGER, productName VARCHAR(50), productImage INTEGER, productPrice INTEGER, productDescription VARCHAR(100))";
        db.execSQL(productTable);



        product_recycler = findViewById(R.id.product_recycler);

        product_recycler.setLayoutManager(new LinearLayoutManager(ProductActivity.this));
        product_recycler.setItemAnimator(new DefaultItemAnimator());

        for(int i =0; i<productId.length; i++){
            String selectQuery = "SELECT * FROM product WHERE productName = '"+productName[i]+"' AND subcategoryid = '"+subcategoryId[i]+"'";
            Cursor cursor = db.rawQuery(selectQuery, null);

            if(cursor.getCount() == 0){
                String insertQuery = "INSERT INTO product VALUES(NULL, '"+subcategoryId[i]+"', '"+productName[i]+"', '"+productImage[i]+"', '"+productPrice[i]+"', '"+productDesc[i]+"')";
                db.execSQL(insertQuery);
            }
        }


        arraylist = new ArrayList<>();

        String selectProductQuery = "SELECT * FROM product WHERE subcategoryid = '"+sp.getString(ConstantSp.subcategoryid, "")+"'";
        Cursor cursor = db.rawQuery(selectProductQuery, null);

        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                ProductList list = new ProductList();
                list.setProductId(cursor.getInt(0));
                list.setSubcategoryId(cursor.getInt(1));
                list.setProductName(cursor.getString(2));
                list.setProductImage(cursor.getInt(3));
                list.setProductPrice(cursor.getInt(4));
                list.setProductDescription(cursor.getString(5));
                arraylist.add(list);
            }
            ProductAdapter adapter = new ProductAdapter (ProductActivity.this, arraylist);
            product_recycler.setAdapter(adapter);
        }



    }
}