package com.example.college.Common.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.college.R;
import com.example.college.User.UserDashboard;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    TextInputLayout phonenumber, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        phonenumber = findViewById(R.id.phone_number_login);
        password = findViewById(R.id.password_login);
    }

    public void letUserLogin(View v) {

        if (!valiDateFields())
            return;

        //Get Values from Fields in LoginScreen
        String phoneNumb = phonenumber.getEditText().getText().toString().trim();
        String phoneNumber = "+91"+phoneNumb.toString();
        String passWord = password.getEditText().getText().toString().trim();

        //Firebase Query
        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNumber").equalTo(phoneNumber);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    phonenumber.setError(null);
                    phonenumber.setErrorEnabled(false);

                    String systemPassword = snapshot.child(phoneNumber).child("passWord").getValue(String.class);
                    if (systemPassword.equals(passWord)) {
                        password.setError(null);
                        password.setErrorEnabled(false);
                        Intent intent = new Intent(Login.this, UserDashboard.class);
                        startActivity(intent);
                        finish();
                    } else
                        Toast.makeText(Login.this, "Password doesn't match!", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(Login.this, "Data doesn't exists!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Login.this, "No such User exists", Toast.LENGTH_SHORT).show();
            }
        });


    }
    private boolean valiDateFields() {
        String phoneNumb = phonenumber.getEditText().getText().toString().trim();
        String phoneNumber = "+91"+phoneNumb.toString();
        String passWord = password.getEditText().getText().toString().trim();

        if (phoneNumber.isEmpty()){
            phonenumber.setError("Phone Number can't be Empty");
            phonenumber.requestFocus();
            return  false;
        }
        else if(passWord.isEmpty()){
            password.setError("Password can't be Empty");
            password.requestFocus();
            return  false;
        }
        else
            return true;
    }

    public void callForgetPassword(View v){
        Intent intent = new Intent(Login.this,ForgetPassword.class);
        startActivity(intent);
    }
}