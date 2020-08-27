package com.me.noban.DengueSolution;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.me.noban.DengueSolution.adapter.ViewPagerAdapter;
import com.me.noban.DengueSolution.fragment.LoginFragment;
import com.me.noban.DengueSolution.fragment.SignUpFragment;
import com.me.noban.DengueSolution.preferences.UserTypePreference;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private FirebaseAuth.AuthStateListener mUserStatusListener;
    private FirebaseAuth mAuth;

    private UserTypePreference mUserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mUserType = UserTypePreference.getPreferences(this);

        isUserLoggedIn();

        initViews();
        initFragment();
        setUpAdapter();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mUserStatusListener);
    }

    private void isUserLoggedIn() {
        mUserStatusListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {

                    if (mUserType.getUserType().equals("Doctor")) {
                        sendToDoctorDashBoard();
                    } else {
                        sendToPatientDashBoard();
                    }

                }
            }
        };
    }

    private void sendToPatientDashBoard() {
        startActivity(new Intent(MainActivity.this, MainUIActivity.class));
        finish();
    }

    private void sendToDoctorDashBoard() {
        startActivity(new Intent(MainActivity.this, DoctorHomeActivity.class));
        finish();
    }

    private void initViews() {
        mTabLayout = findViewById(R.id.activity_main_item_tab_layout);
        mViewPager = findViewById(R.id.activity_main_fragment_view_pager);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
    }

    private void initFragment() {
        mViewPagerAdapter.addFragment(new LoginFragment(), "LOGIN");
        mViewPagerAdapter.addFragment(new SignUpFragment(), "SIGNUP");
        // mViewPagerAdapter.addFragment(new EmergencyFragment(), "EMERGENCY");
    }

    private void setUpAdapter() {
        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }


}
