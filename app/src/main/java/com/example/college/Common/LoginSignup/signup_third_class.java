package com.example.college.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.college.R;

public class signup_third_class extends AppCompatActivity {

    ImageView back_btn;
    Button next, login;
    TextView title_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_third_class);
        back_btn = findViewById(R.id.sign_back_btn);
        next = findViewById(R.id.signu_next_btn);
        login = findViewById(R.id.signup_login_btn);
        title_text = findViewById(R.id.signup_title_text);
    }
    public void callNextSignupScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUp.class);

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
}