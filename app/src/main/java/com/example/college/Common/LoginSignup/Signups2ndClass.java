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

public class Signups2ndClass extends AppCompatActivity {
    ImageView back_btn2;
    Button next2, login2;
    TextView title_text2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signups2nd_class);

        back_btn2= findViewById(R.id.sign2_back_btn);
        next2 = findViewById(R.id.signu2_next_btn);
        login2 = findViewById(R.id.signup2_login_btn);
        title_text2 = findViewById(R.id.signup2_title_text);
    }
    public void callNextSignupthreeScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), signup_third_class.class);

        Pair[] pairs = new Pair[4];

        pairs[0] = new Pair<View, String>(back_btn2, "transition_back_arrow_btn");
        pairs[1] = new Pair<View, String>(next2, "transition_next_btn");
        pairs[2] = new Pair<View, String>(login2, "transition_login_btn");
        pairs[3] = new Pair<View, String>(title_text2, "transition_title_text");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Signups2ndClass.this, pairs);
            startActivity(intent, options.toBundle());
        } else
            startActivity(intent);
    }
}