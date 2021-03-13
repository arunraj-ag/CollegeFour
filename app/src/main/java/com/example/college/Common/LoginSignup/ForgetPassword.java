package com.example.college.Common.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.college.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class ForgetPassword extends AppCompatActivity {

    private TextInputLayout phoneNumberTextField,verificationCode;
    private Button nextBtn;

    private PhoneAuthProvider.ForceResendingToken forceResendingToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationID;
    private static final String TAG = "MAIN_TAG";
    private FirebaseAuth firebaseAuth;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forget_password);

        phoneNumberTextField = findViewById(R.id.phone_number_forget_password);
        verificationCode = findViewById(R.id.verification_code_forget_password);
        nextBtn = findViewById(R.id.next_btn_forget_password);

        firebaseAuth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(this);
        pd.setTitle("Please Wait...");
        pd.setCanceledOnTouchOutside(false);

        mCallbacks   = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                pd.dismiss();
                Toast.makeText(ForgetPassword.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String verificationID, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                super.onCodeSent(verificationID, forceResendingToken);
                Log.d(TAG, "onCodeSent:"+verificationID);
                mVerificationID = verificationID;
                forceResendingToken = token;
                pd.dismiss();

                Toast.makeText(ForgetPassword.this, "Verification Code Sent...", Toast.LENGTH_SHORT).show();
            }
        };
    }
    public void sendCodeForgetPassword(View v){
        String phoneNumb = phoneNumberTextField.getEditText().getText().toString().trim();
        String phoneNumber ="+91"+phoneNumb.toString();
        if (TextUtils.isEmpty(phoneNumber))
            Toast.makeText(this, "Please Enter Phone Number", Toast.LENGTH_SHORT).show();
        else{
            startPhoneNumberVerification(phoneNumber);
        }
    }
    private void startPhoneNumberVerification(String phoneNumber) {
        pd.setMessage("Verifying Phone Number");
        pd.show();
        PhoneAuthOptions options =PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }



    public void resendCodeForgetPass(View v){
        String phoneNumber = phoneNumberTextField.getEditText().getText().toString().trim();
        if (TextUtils.isEmpty(phoneNumber))
            Toast.makeText(this, "Please Enter Phone Number", Toast.LENGTH_SHORT).show();
        else{
            resendVerificationCode(phoneNumber,forceResendingToken);
        }
    }
    private void resendVerificationCode(String phoneNumber,PhoneAuthProvider.ForceResendingToken token) {
        pd.setMessage("Resending Code");
        pd.show();
        PhoneAuthOptions options =PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .setForceResendingToken(token)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    public void submitCodeForgetPass(View v){
        String code = verificationCode.getEditText().getText().toString().trim();
        if (TextUtils.isEmpty(code)){
            Toast.makeText(this, "Please enter verification Code", Toast.LENGTH_SHORT).show();
        }
        else{
            verifyPhoneNumberWithCode(mVerificationID,code);
        }
    }
    private void verifyPhoneNumberWithCode(String mVerificationID, String code) {
        pd.setMessage("Verifying Code");
        pd.show();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationID,code);
        signInWithPhoneAuthCredential(credential);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        pd.setMessage("Logging In");
        firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener(authResult -> {
                    pd.dismiss();
                    String phone = firebaseAuth.getCurrentUser().getPhoneNumber();
                    String phoneNumb = phoneNumberTextField.getEditText().getText().toString().trim();
                    String phoneNumber ="+91"+phoneNumb.toString();
                    Toast.makeText(ForgetPassword.this, "Verification SuccessFul", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ForgetPassword.this,SetNewPassword.class);
                    intent.putExtra("phone_no_forget_pass",phoneNumber);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    pd.dismiss();
                    Toast.makeText(ForgetPassword.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                });

    }
}