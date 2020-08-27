package com.me.noban.DengueSolution;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.me.noban.DengueSolution.Ambulence.HospitalAmbulenceContact;
import com.me.noban.DengueSolution.Ambulence.view.bottomNav;
import com.me.noban.DengueSolution.Apointment.ApointMentHome;
import com.me.noban.DengueSolution.BloodBank.BloodbankMainUI;
import com.me.noban.DengueSolution.home.framgent.homeMain;
import com.me.noban.DengueSolution.maps.MapsActivity;
import com.me.noban.DengueSolution.model.Patient;
import com.me.noban.DengueSolution.preferences.UserTypePreference;
import com.me.noban.DengueSolution.pulseRate.pulsrateMain;

public class MainUIActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TabLayout tab_layout;
    LinearLayout bot_layout;

    Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigtionView;
    private ActionBarDrawerToggle toggle;

    private UserTypePreference mUserType;

    private DatabaseReference mPatientInfoDatabaseRef;

    private Patient patientInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        mUserType = UserTypePreference.getPreferences(this);
        mUserType.setUserType("Patient");

        patientInfo = mUserType.getUserInfo();

        Log.d("user", "onCreate: " + patientInfo.getName());

        mPatientInfoDatabaseRef = FirebaseDatabase.getInstance().getReference().child("data").child("0").child("patient_info");

        initToolbarAndDrawer();

        intcomponents();

    }

    private void initToolbarAndDrawer() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        mDrawerLayout = findViewById(R.id.navigation_drawer_layout);
        mNavigtionView = findViewById(R.id.navigation_view);
        toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mNavigtionView.setNavigationItemSelectedListener(this);

        setUserInfoToNavigationDrawer();
    }

    private void setUserInfoToNavigationDrawer() {
        View headerViewOfNavigationDrawer = mNavigtionView.getHeaderView(0);
        TextView name = headerViewOfNavigationDrawer.findViewById(R.id.name_text_view);
        TextView email = headerViewOfNavigationDrawer.findViewById(R.id.email_text_view);

        if (patientInfo != null) {
            name.setText(patientInfo.getName());
            email.setText(patientInfo.getEmail());
        }

    }

    private void intcomponents() {
        tab_layout = findViewById(R.id.tab_layout);
        bot_layout = findViewById(R.id.layout_bottom);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bot_layout.getLayoutParams();
        layoutParams.setBehavior(new bottomNav());


        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_home), 0);
        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_add), 1);
        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_ambulence), 2);
        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_blood), 3);
        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_heart), 4);
        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_chat), 5);
        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_hospital), 6);

        // set icon color pre-selected
        tab_layout.getTabAt(0).getIcon().setColorFilter(getResources().getColor(R.color.green_300), PorterDuff.Mode.SRC_IN);
        tab_layout.getTabAt(1).getIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        tab_layout.getTabAt(2).getIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        tab_layout.getTabAt(3).getIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        tab_layout.getTabAt(4).getIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        tab_layout.getTabAt(5).getIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        tab_layout.getTabAt(6).getIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);

        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(getResources().getColor(R.color.green_300), PorterDuff.Mode.SRC_IN);

                switch (tab.getPosition()) {
                    case 0:
                        gotoHome();
                        break;
                    case 1:
                        apointment();


                        break;
                    case 2:
                        HospitalAmbulenceContact();

                        break;
                    case 3:
                        gotoChecking();

                        break;
                    case 4:
                        gotoHeartrate();


                        break;
                    case 5:


                        break;
                    case 6:

                        gotoNearbyHospital();
                        break;
                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        gotoHome();
        // display image


    }

    public void gotoHeartrate() {

    }

    public void gotoHome() {
        Fragment fragment = new homeMain();
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment3, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void apointment() {
        Fragment fragment = new ApointMentHome();
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment3, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void gotoChecking() {
        Fragment fragment = new BloodbankMainUI();
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment3, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void gotoNearbyHospital() {
        Fragment fragment = new MapsActivity();
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment3, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void gotoPulse(View view) {
        startActivity(new Intent(MainUIActivity.this, pulsrateMain.class));
    }

    public void HospitalAmbulenceContact() {


        Fragment fragment = new HospitalAmbulenceContact();
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment3, fragment);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }

    public void Apointment(View view) {

    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.nav_profile) {
            startActivity(new Intent(MainUIActivity.this, UserProfileActivity.class));
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
