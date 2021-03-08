package com.example.college.Common.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.college.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyOTP extends AppCompatActivity {

    PinView pinfromUser;
    TextView phone_number_view;
    String _phoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_o_t_p);

        phone_number_view = findViewById(R.id.phone_view);
        pinfromUser = findViewById(R.id.pinView);

        _phoneNo = getIntent().getStringExtra("phone_number");
        phone_number_view.setText(_phoneNo);

        verifyPIN();


    }

    private void verifyPIN() {
        String phoneNumebr =_phoneNo;
        PhoneAuthProvider.getInstance();
    }

    public void callNextScreenfromOTP(View view){

    }
}