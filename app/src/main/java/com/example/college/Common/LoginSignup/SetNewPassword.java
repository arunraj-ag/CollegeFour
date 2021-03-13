package com.example.college.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.college.Databases.UserHelperClass;
import com.example.college.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class SetNewPassword extends AppCompatActivity {
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^" +
            "(?=.*[0-9])" +
            "(?=.*[a-z])" +
            "(?=.*[A-Z])" +
            "(?=.*[@#$%^&*+=])" +
            "(?=\\S+$)" +
            ".{6,}" +
            "$");
    TextInputLayout new_password;
    Button ok_btn;
    String phone_num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_set_new_password);


        new_password = findViewById(R.id.password_new_password);
        ok_btn = findViewById(R.id.ok_btn_setnewpassword);

        phone_num = getIntent().getStringExtra("phone_no_forget_pass");

    }
    public void updatePassword(View v){
        String password_new  = new_password.getEditText().getText().toString();
        if (!validatePassword())
            return;
       // else{
      //      if (new_password.equals(confirm_new_password)){
                storeNewUsersData(phone_num,password_new);
       //     }
       //     else
        //    {
        //        Toast.makeText(this, "Passwords are not equal", Toast.LENGTH_SHORT).show();
         //   }

      //  }
    }

    private void storeNewUsersData(String phone_num,String new_password) {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("Users");
        reference.child(phone_num).child("passWord").setValue(new_password);
        startActivity(new Intent(getApplicationContext(),ForgetPasswordSuccessMessage.class));
        finish();
    }

    private boolean validatePassword() {
        String password_new  = new_password.getEditText().getText().toString();

        if (password_new.isEmpty()) {
            new_password.setError("Password can't be Empty");
            return false;
        }  else if (!PASSWORD_PATTERN.matcher(password_new).matches()) {
            new_password.setError("Password should contain one uppercase,lowercase,special case and a number");
            return false;
        }else {
            new_password.setError(null);
            return true;
        }

    }
}