package com.example.internshipbatch27may;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class SignupActivity extends AppCompatActivity {

    EditText name, email, contact, password, confirm_password;
    Button signup;
    TextView already_account;
    String email_pattern = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        db  = openOrCreateDatabase("InternshipBatch27May.db", MODE_PRIVATE, null);
        String userTable = "CREATE TABLE IF NOT EXISTS user(userid INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(50), email VARCHAR(100), contact VARCHAR(10), password VARCHAR(20))";
        db.execSQL(userTable);

        name = findViewById(R.id.signup_name);
        email = findViewById(R.id.signup_email);
        contact = findViewById(R.id.signup_contact);
        password = findViewById(R.id.signup_password);
        confirm_password = findViewById(R.id.signup_confirm_password);
        signup = findViewById(R.id.signup_login);
        already_account = findViewById(R.id.signup_already_account);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().trim().equals("")){
                    email.setError("Enter Email");
                } else if (!email.getText().toString().matches(email_pattern)) {
                    email.setError("Enter Valid Email");
                }

                else if(name.getText().toString().equals("")){
                    name.setError("Enter Name");
                }

                else if(contact.getText().toString().equals("")){
                    contact.setError("Enter Contact Number");
                }

                else if (password.getText().toString().trim().equals("")) {
                    password.setError("Enter Password");
                } else if (password.getText().toString().length()<6) {
                    password.setError("Minimum 6 characters");
                } else if (confirm_password.getText().toString().equals("")) {
                    confirm_password.setError("Enter Confirm Password");
                } else if (!confirm_password.getText().toString().matches(password.getText().toString())) {
                    confirm_password.setError("Confirm password Does not matches ");

                } else{

                    String insertQuery = "INSERT INTO user VALUES(NULL, '"+name.getText().toString()+"', '"+email.getText().toString()+"', '"+contact.getText().toString()+"', '"+password.getText().toString()+"')";
                    db.execSQL(insertQuery);



                    Toast.makeText(SignupActivity.this, "Account Created Successfull", Toast.LENGTH_SHORT).show();
//                    Snackbar.make(view, "LOgin Successfull", Snackbar.LENGTH_LONG).show();
                    Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        });

        already_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}