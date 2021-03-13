package com.example.college.Common.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.audiofx.DynamicsProcessing;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.college.Databases.UserHelperClass;
import com.example.college.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class signup_third_class extends AppCompatActivity {

    ImageView back_btn;
    Button next, login,submit;
    TextView title_text;
    TextInputLayout phone_num,verification_code;
    String fullName,eMail,userName,passWord,date,gender,phoneNumber,phoneNumb;
    private PhoneAuthProvider.ForceResendingToken forceResendingToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationID;
    private static final String TAG = "MAIN_TAG";
    private FirebaseAuth firebaseAuth;
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_third_class);
        back_btn = findViewById(R.id.sign_back_btn);
        login = findViewById(R.id.signup_login_btn);
        title_text = findViewById(R.id.signup_title_text);
        phone_num = findViewById(R.id.phone_number);
        next = findViewById(R.id.signuthree_send_btn);
        submit = findViewById(R.id.signup_three_submit_btn);
        verification_code = findViewById(R.id.verification_code);
        phoneNumb = phone_num.getEditText().getText().toString().trim();
        phoneNumber = "+91"+phoneNumb.toString();


         fullName = getIntent().getStringExtra("fullName");
         eMail = getIntent().getStringExtra("email_");
         userName = getIntent().getStringExtra("user_name");
         passWord = getIntent().getStringExtra("password_");
         date = getIntent().getStringExtra("date");
         gender = getIntent().getStringExtra("gender");



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
               Toast.makeText(signup_third_class.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onCodeSent(@NonNull String verificationID, @NonNull PhoneAuthProvider.ForceResendingToken token) {
               super.onCodeSent(verificationID, forceResendingToken);
               Log.d(TAG, "onCodeSent:"+verificationID);
               mVerificationID = verificationID;
               forceResendingToken = token;
               pd.dismiss();

               Toast.makeText(signup_third_class.this, "Verification Code Sent...", Toast.LENGTH_SHORT).show();
           }
       };
    }


    public void sendCode(View v){
        String phoneNumb = phone_num.getEditText().getText().toString().trim();
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



    public void resendCode(View v){
        String phoneNumber = phone_num.getEditText().getText().toString().trim();
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

    public void submitCode(View v){
        String code = verification_code.getEditText().getText().toString().trim();
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
                    String phoneNumb = phone_num.getEditText().getText().toString().trim();
                    String phoneNumber ="+91"+phoneNumb.toString();
                    Toast.makeText(signup_third_class.this, "Verification SuccessFul", Toast.LENGTH_SHORT).show();
                    storeNewUsersData(phoneNumber);
                    Intent intent = new Intent(signup_third_class.this,Login.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    pd.dismiss();
                    Toast.makeText(signup_third_class.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                });

    }

    private void storeNewUsersData(String phone) {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("Users");

        UserHelperClass addNewUser = new UserHelperClass(fullName,userName,eMail,phone,passWord,date,gender);
        reference.child(phone).setValue(addNewUser);

    }


    /*private boolean validatePhoneNumber() {
        String phone_numberInput =phone_num.getEditText().getText().toString().trim();

        if (phone_numberInput.isEmpty()) {
            phone_num.setError("Phone Number can't be Empty");
            return false;
        } else if (!Patterns.PHONE.matcher(phone_numberInput).matches()) {
            phone_num.setError("Please Enter a valid Phone Number");
            return false;
        } else {
            phone_num.setError(null);
            return true;
        }

    }
    public void callVerifyOTPScreen(View view) {

        if (validatePhoneNumber()){
            String fullName = getIntent().getStringExtra("full_name");
            String eMail = getIntent().getStringExtra("email_");
            String userName = getIntent().getStringExtra("user_name");
            String passWord = getIntent().getStringExtra("password_");
            String date = getIntent().getStringExtra("date");
            String gender = getIntent().getStringExtra("gender");
            String phone_numb = phone_num.getEditText().getText().toString().trim();
            String phoneNumber = "+"+"91"+phone_numb;

            Intent intent = new Intent(getApplicationContext(), SignInSuccessfull.class);
            intent.putExtra("full_name",fullName);
            intent.putExtra("email_",eMail);
            intent.putExtra("user_name",userName);
            intent.putExtra("password_",passWord);
            intent.putExtra("date",date);
            intent.putExtra("gender",gender);
            intent.putExtra("phone_number",phoneNumber);

            Pair[] pairs = new Pair[4];

            pairs[0] = new Pair<View, String>(back_btn, "transition_back_arrow_btn");
            pairs[1] = new Pair<View, String>(next, "transition_next_btn");
            pairs[2] = new Pair<View, String>(login, "transition_login_btn");
            pairs[3] = new Pair<View, String>(title_text, "transition_title_text");

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(signup_third_class.this, pairs);
                startActivity(intent, options.toBundle());
            } else
                startActivity(intent);
        }
        else
            return;

    }*/
}