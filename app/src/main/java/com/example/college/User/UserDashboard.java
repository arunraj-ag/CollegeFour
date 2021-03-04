package com.example.college.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.college.Common.LoginSignup.StartUpScreen;
import com.example.college.HelperClasses.CollegeCharacters.CollegeAdapter;
import com.example.college.HelperClasses.CollegeCharacters.CollegeHelperClass;
import com.example.college.HelperClasses.HomeAdapter.DeptAdapter;
import com.example.college.HelperClasses.HomeAdapter.DeptHelperClass;
import com.example.college.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class UserDashboard extends AppCompatActivity implements DeptAdapter.onDeptListener, NavigationView.OnNavigationItemSelectedListener {

    LinearLayout contentView;
    RecyclerView deptRecycler, collegeRecycler;
    RecyclerView.Adapter adapter;
    ArrayList<DeptHelperClass> deptLocations = new ArrayList<>();
    ArrayList<CollegeHelperClass> collegeLocations = new ArrayList<>();
    ImageView menuIcon;
    static final float END_SCALE = 0.7f;
    //DrawerMenu
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_dashboard);

        deptRecycler = findViewById(R.id.dept_recycler);
        collegeRecycler = findViewById(R.id.coll_char_recycler);


        //DrawerLayout Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        menuIcon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.content);


        navigationDrawer();
        deptRecycler();
        collegeRecycler();
    }




    //NavigationDrawerSection
    private void navigationDrawer() {
        //Navigation Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
        menuIcon.setOnClickListener(v -> {
            if (drawerLayout.isDrawerVisible(GravityCompat.START))
                drawerLayout.closeDrawer(GravityCompat.START);
            else drawerLayout.openDrawer(GravityCompat.START);
        });
        animateNavigationDrawer();
    }
    private void animateNavigationDrawer() {

        //Add any color or remove it to use the default one!
        //To make it transparent use Color.Transparent in side setScrimColor();
        //drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.setScrimColor(getResources().getColor(R.color.colorPrimary));
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });

    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_features:
                startActivity(new Intent(getApplicationContext(), Features.class));
                break;
        }
        return true;
    }





    //Department RecyclerSection
    private void deptRecycler() {
        deptRecycler.setHasFixedSize(true);
        deptRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        deptLocations.add(new DeptHelperClass(R.drawable.cse, "Computer Science & Engineering", "Dazzling CSE"));
        deptLocations.add(new DeptHelperClass(R.drawable.auto, "Automobile Engineering", "Amazing Auto"));
        deptLocations.add(new DeptHelperClass(R.drawable.mechanic, "Mechanical Engineering", "Royal Mech"));
        deptLocations.add(new DeptHelperClass(R.drawable.biotech, "Bio-Technology", "Beautiful Bio"));
        deptLocations.add(new DeptHelperClass(R.drawable.civil, "Civil Engineering", "Clever Civil"));
        deptLocations.add(new DeptHelperClass(R.drawable.electrical, "Electrical and Electronics Engineering", "#Psych EEE"));
        deptLocations.add(new DeptHelperClass(R.drawable.chemistry, "Chemistry", "Toxic chemistry"));
        deptLocations.add(new DeptHelperClass(R.drawable.computer_app, "Computer Applications", "Playful Applications"));
        deptLocations.add(new DeptHelperClass(R.drawable.ece, "ECE", "Dominating ECE"));
        deptLocations.add(new DeptHelperClass(R.drawable.english, "English", "Educative English"));
        deptLocations.add(new DeptHelperClass(R.drawable.it_dept, "Information Technology", "Informative IT"));
        deptLocations.add(new DeptHelperClass(R.drawable.management, "Management Studies", "Magical Management"));
        deptLocations.add(new DeptHelperClass(R.drawable.maths, "Mathematics", "Scary Mathematics"));
        deptLocations.add(new DeptHelperClass(R.drawable.petro, "Petrochemical Technology", "Purposeful Petro"));
        deptLocations.add(new DeptHelperClass(R.drawable.pharma, "Pharma", "Fundamental Pharma"));
        deptLocations.add(new DeptHelperClass(R.drawable.physics, "Physics", "Mesmerising Physics"));
        deptLocations.add(new DeptHelperClass(R.drawable.physical_edu, "Physical Education", "Sweaty PE"));

        adapter = new DeptAdapter(deptLocations, this);
        deptRecycler.setAdapter(adapter);
    }
    @Override
    public void onDeptClick(int position) {
        deptLocations.get(position);
        if (position == 0) {
            Intent intent = new Intent(this, comp_science.class);
            intent.putExtra("CSE", "Called");
            startActivity(intent);
        } else if (position == 1) {
            Intent intent = new Intent(this, auto_engg.class);
            intent.putExtra("Automobile", "Called");
            startActivity(intent);
        }
    }


    //CollegeRecyclerSection
    private void collegeRecycler() {
        if (collegeRecycler != null) {
            collegeRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            collegeRecycler.setHasFixedSize(true);

            collegeLocations.add(new CollegeHelperClass(R.drawable.anti_ragging, "AntiRagging", "Anti Ragging Committee helps to stop ragging activities in our College"));
            collegeLocations.add(new CollegeHelperClass(R.drawable.anti_ragging, "AntiRagging", "Anti Ragging Committee helps to stop ragging activities in our College"));
            collegeLocations.add(new CollegeHelperClass(R.drawable.anti_ragging, "AntiRagging", "Anti Ragging Committee helps to stop ragging activities in our College"));

            adapter = new CollegeAdapter(collegeLocations);
            collegeRecycler.setAdapter(adapter);
        }
    }



    public void callProfessorScreen(View view){
        startActivity(new Intent(getApplicationContext(), StartUpScreen.class));
    }

}