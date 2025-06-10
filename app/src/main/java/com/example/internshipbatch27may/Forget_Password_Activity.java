package com.example.internshipbatch27may;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Forget_Password_Activity extends AppCompatActivity {

    EditText email, new_password, new_confirm_password;
    Button change_password;

    String email_pattern = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";

    SQLiteDatabase db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        db  = openOrCreateDatabase("InternshipBatch27May.db", MODE_PRIVATE, null);
        String userTable = "CREATE TABLE IF NOT EXISTS user(userid INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(50), email VARCHAR(100), contact VARCHAR(10), password VARCHAR(20))";
        db.execSQL(userTable);

        email = findViewById(R.id.forget_email);
        new_password = findViewById(R.id.forget_password);
        new_confirm_password = findViewById(R.id.forget_confirm_password);
        change_password = findViewById(R.id.forget_login);
        
        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().trim().equals("")) {
                    email.setError("Enter Email");
                } else if (!email.getText().toString().matches(email_pattern)) {
                    email.setError("Enter Valid Email");
                } else if (new_password.getText().toString().trim().equals("")) {
                    new_password.setError("Enter Password");
                } else if (new_password.getText().toString().length() < 6) {
                    new_password.setError("Minimum 6 characters");
                } else if (new_confirm_password.getText().toString().equals("")) {
                    new_confirm_password.setError("Enter Confirm Password");
                } else if (!new_confirm_password.getText().toString().matches(new_password.getText().toString())) {
                    new_confirm_password.setError("Confirm password Does not matches ");
                } else {
                    String checkEmail = "SELECT * FROM user WHERE email = '"+email.getText().toString()+"'";
                    Cursor cursor = db.rawQuery(checkEmail,null);
                    if(cursor.getCount()>0){
                        String updatePassword = "UPDATE user SET password = '"+new_password.getText().toString()+"' WHERE email = '"+email.getText().toString()+"'";
                        db.execSQL(updatePassword);
                        Toast.makeText(Forget_Password_Activity.this, "Password Changed Successfully", Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }
                    else {
                        Toast.makeText(Forget_Password_Activity.this,"Email Doesn't Exists", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
}