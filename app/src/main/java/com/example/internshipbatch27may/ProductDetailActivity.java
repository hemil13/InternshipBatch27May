package com.example.internshipbatch27may;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProductDetailActivity extends AppCompatActivity {

    ImageView image, wishlist;
    TextView name, price, description;

    SharedPreferences sp;

    SQLiteDatabase db;

    boolean isWishlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_detail);

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

        String wishlistTable = "CREATE TABLE IF NOT EXISTS wishlist(wishlistId INTEGER PRIMARY KEY AUTOINCREMENT, productId INTEGER, userId INTEGER)";
        db.execSQL(wishlistTable);






        image = findViewById(R.id.product_detail_image);
        name = findViewById(R.id.product_detail_name);
        price = findViewById(R.id.product_detail_price);
        description = findViewById(R.id.product_detail_desc);
        wishlist = findViewById(R.id.product_detail_wishlist);


        image.setImageResource(sp.getInt(ConstantSp.product_image, 0));
        name.setText(sp.getString(ConstantSp.product_name, ""));
        price.setText(ConstantSp.ruppees + sp.getString(ConstantSp.product_price, ""));
        description.setText(sp.getString(ConstantSp.product_description, ""));



        String wishlistCheck = "SELECT * FROM wishlist WHERE productId = '"+sp.getString(ConstantSp.product_id, "")+"' AND userid = '"+sp.getString(ConstantSp.userid, "")+"'";
        Cursor wishlistCursor = db.rawQuery(wishlistCheck, null);

        if(wishlistCursor.getCount()>0){
            wishlist.setImageResource(R.drawable.wishlist_fill);
            isWishlist = true;
        }
        else{
            wishlist.setImageResource(R.drawable.wishlist_empty);
            isWishlist = false;
        }



        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isWishlist){
                    String insertQuery = "INSERT INTO wishlist VALUES(NULL, '"+sp.getString(ConstantSp.product_id, "")+"', '"+sp.getString(ConstantSp.userid, "")+"')";
                    db.execSQL(insertQuery);

                    wishlist.setImageResource(R.drawable.wishlist_fill);

                    Toast.makeText(ProductDetailActivity.this, "Added To Wishlist", Toast.LENGTH_SHORT).show();

                    isWishlist = true;

                }

                else {
                    String deleteQuery = "DELETE FROM wishlist WHERE productId = '"+sp.getString(ConstantSp.product_id, "")+"' AND userId = '"+sp.getString(ConstantSp.userid,"")+"'";
                    db.execSQL(deleteQuery);

                    wishlist.setImageResource(R.drawable.wishlist_empty);

                    Toast.makeText(ProductDetailActivity.this, "Removed From Wishlist", Toast.LENGTH_SHORT).show();

                    isWishlist  = false;
                }
            }
        });


    }
}