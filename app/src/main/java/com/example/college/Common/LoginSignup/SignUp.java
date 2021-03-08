package com.example.college.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.college.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^" +
            "(?=.*[0-9])" +
            "(?=.*[a-z])" +
            "(?=.*[A-Z])" +
            "(?=.*[@#$%^&*+=])" +
            "(?=\\S+$)" +
            ".{6,}" +
            "$");
    ImageView back_btn;
    Button next, login;
    TextView title_text;

    TextInputLayout fullName, userName, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        back_btn = findViewById(R.id.sign_back_btn);
        next = findViewById(R.id.signu_next_btn);
        login = findViewById(R.id.signup_login_btn);
        title_text = findViewById(R.id.signup_title_text);


        fullName = findViewById(R.id.signup_full_name);
        userName = findViewById(R.id.signup_user_name);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);

    }

    private boolean validateEmail() {
        String emailInput = email.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            email.setError("Email can't be Empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            email.setError("Please Enter a valid email Address");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    private boolean validateUserName() {
        String usernameInput = userName.getEditText().getText().toString().trim();
        if (usernameInput.isEmpty()) {
            userName.setError("UserName can't be Empty");
            return false;
        } else if (usernameInput.length() > 15) {
            userName.setError("Username too Long");
            return false;
        } else {
            userName.setError(null);
            return true;
        }
    }

    private boolean validateFullName() {
        String fullnameInput = fullName.getEditText().getText().toString().trim();
        if (fullnameInput.isEmpty()) {
            fullName.setError("FullName can't be Empty");
            return false;
        } else if (fullnameInput.length() > 15) {
            fullName.setError("FullName too Long");
            return false;
        } else {
            fullName.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = password.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()) {
            password.setError("Password can't be Empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            password.setError("Password should contain one uppercase,lowercase,special case and a number");
            return false;
        } else {
            password.setError(null);
            return true;
        }

    }

    public void callNextScreen(View view) {
        if (validateEmail() & validateUserName() & validateFullName() & validatePassword()) {
            //Extracting values from Views
            String fullNameI = fullName.getEditText().getText().toString().trim();
            String userNameI = userName.getEditText().getText().toString().trim();
            String emailI = email.getEditText().getText().toString().trim();
            String passwordI = password.getEditText().getText().toString().trim();

            //Passing intent values
            Intent intent = new Intent(getApplicationContext(), Signups2ndClass.class);
            intent.putExtra("full_name", fullNameI);
            intent.putExtra("user_name", userNameI);
            intent.putExtra("email_", emailI);
            intent.putExtra("password_", passwordI);

            Pair[] pairs = new Pair[4];

            pairs[0] = new Pair<View, String>(back_btn, "transition_back_arrow_btn2");
            pairs[1] = new Pair<View, String>(next, "transition_next_btn2");
            pairs[2] = new Pair<View, String>(login, "transition_login_btn2");
            pairs[3] = new Pair<View, String>(title_text, "transition_title_text2");

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this, pairs);
                startActivity(intent, options.toBundle());
            } else
                startActivity(intent);
        } else {
            return;
        }

    }


}