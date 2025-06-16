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
import java.util.Currency;

public class SubcategoryActivity extends AppCompatActivity {

    RecyclerView subcategory_recycler;

    int[] categoryid = {2,2,2,3,3,3};
    int[] subcategoryid = {1,2,3,4,5,6};
    String[] nameArray = {"Jeans", "Shirt", "Tshirt", "Mobile", "Headphones", "Earbuds"};
    int[] imageArray = {R.drawable.jeans, R.drawable.shirt, R.drawable.thsirt,
            R.drawable.mobile, R.drawable.headphone, R.drawable.earbuds};


    SQLiteDatabase db;

    SharedPreferences sp;

    ArrayList<SubcategoryList> arraylist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategory);

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



        subcategory_recycler = findViewById(R.id.subcategory_recycler);
        subcategory_recycler.setLayoutManager(new LinearLayoutManager(SubcategoryActivity.this));
        subcategory_recycler.setItemAnimator(new DefaultItemAnimator());


        for(int i =0; i<categoryid.length; i++){
            String selectQuery = "SELECT * FROM subcategory WHERE name = '"+nameArray[i]+"' AND categoryid = '"+categoryid[i]+"'";
            Cursor cursor = db.rawQuery(selectQuery, null);

            if(cursor.getCount() == 0){
                String insertQuery = "INSERT INTO subcategory VALUES(NULL, '"+categoryid[i]+"', '"+nameArray[i]+"', '"+imageArray[i]+"')";
                db.execSQL(insertQuery);
            }
        }

        arraylist = new ArrayList<>();

        String selectSubcategoryQuery = "SELECT * FROM subcategory WHERE categoryid = '"+sp.getString(ConstantSp.categoryid, "")+"'";
        Cursor cursor = db.rawQuery(selectSubcategoryQuery, null);

        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                SubcategoryList list = new SubcategoryList();
                list.setSubcategoryid(cursor.getInt(0));
                list.setCategoryid(cursor.getInt(1));
                list.setName(cursor.getString(2));
                list.setImage(cursor.getInt(3));
                arraylist.add(list);
            }
            SubCategoryAdapter adapter = new SubCategoryAdapter (SubcategoryActivity.this, arraylist);
            subcategory_recycler.setAdapter(adapter);
        }


    }
}