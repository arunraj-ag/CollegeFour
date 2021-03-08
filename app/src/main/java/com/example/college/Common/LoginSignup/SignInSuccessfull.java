package com.example.college.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.college.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInSuccessfull extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    TextView phone_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_successfull);
        phone_num = findViewById(R.id.phone_tv);
        firebaseAuth = FirebaseAuth.getInstance();
        checkUserStatus();
    }

    private void checkUserStatus() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser !=null){
          String phone = firebaseUser.getPhoneNumber();
          phone_num.setText(phone);
        }
        else {
            finish();
        }
    }

}