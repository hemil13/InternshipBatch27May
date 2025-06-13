package com.example.internshipbatch27may;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    RecyclerView category_recycler;

    int[] idArray = {1,2,3};
    String[] nameArray = {"Books", "Clothes", "Electronics"};
    int[] imageArray = {R.drawable.books, R.drawable.clothes, R.drawable.electronics};

    SQLiteDatabase db;

    ArrayList<CategoryList> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        db  = openOrCreateDatabase("InternshipBatch27May.db", MODE_PRIVATE, null);
        String userTable = "CREATE TABLE IF NOT EXISTS user(userid INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(50), email VARCHAR(100), contact VARCHAR(10), password VARCHAR(20))";
        db.execSQL(userTable);
//
        String categoryTable = "CREATE TABLE IF NOT EXISTS category(categoryid INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(50), image VARCHAR(100))";
        db.execSQL(categoryTable);



        category_recycler = findViewById(R.id.category_recycler);

        category_recycler.setLayoutManager(new LinearLayoutManager(CategoryActivity.this));
//        category_recycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        category_recycler.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));


//        CategoryAdapter adapter = new CategoryAdapter(CategoryActivity.this, idArray, nameArray, imageArray);
//        category_recycler.setAdapter(adapter);


        for(int i=0; i<idArray.length; i++){
            String checkCategory = "SELECT * FROM category WHERE name = '"+nameArray[i]+"'";
            Cursor cursor = db.rawQuery(checkCategory, null);

//            if(cursor.getCount()>0){
//                // do nothing
//            }
//            else{
//                String insertCategory = "INSERT INTO category VALUES(NULL, '"+nameArray[i]+"', '"+imageArray[i]+"')";
//                db.execSQL(insertCategory);
//            }

            if(cursor.getCount()==0){
                String insertCategory = "INSERT INTO category VALUES(NULL, '"+nameArray[i]+"', '"+imageArray[i]+"')";
                db.execSQL(insertCategory);
            }
        }


        String selectQurery = "SELECT * FROM category";
        Cursor cursor = db.rawQuery(selectQurery, null);


        arrayList = new ArrayList<>();


        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                CategoryList list = new CategoryList();
                list.setId(cursor.getInt(0));
                list.setName(cursor.getString(1));
                list.setImage(cursor.getInt(2));
                arrayList.add(list);
            }
            CategoryAdapter adapter = new CategoryAdapter(CategoryActivity.this, arrayList);
            category_recycler.setAdapter(adapter);
        }
    }
}