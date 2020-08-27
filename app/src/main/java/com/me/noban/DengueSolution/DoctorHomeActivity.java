package com.me.noban.DengueSolution;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.me.noban.DengueSolution.checkApointmentDoctor.check_apointment;
import com.me.noban.DengueSolution.preferences.UserTypePreference;

public class DoctorHomeActivity extends AppCompatActivity {

//    private FirebaseAuth.AuthStateListener mUserStatusListener;
//    private FirebaseAuth mAuth;

    Button logout, checkapp;
    private UserTypePreference mUserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);

        mUserType = UserTypePreference.getPreferences(this);
        mUserType.setUserType("Doctor");

        logout = findViewById(R.id.doc_logout);
        checkapp = findViewById(R.id.check_ap_button);
        checkapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorHomeActivity.this, check_apointment.class);
                startActivity(intent);

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(DoctorHomeActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });


//        mAuth = FirebaseAuth.getInstance();
//        isUserLoggedIn();
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        mAuth.addAuthStateListener(mUserStatusListener);
//    }
//
//    private void isUserLoggedIn() {
//        mUserStatusListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                if (firebaseAuth.getCurrentUser() == null) {
//                    sendToLoginActivity();
//                }
//            }
//        };
//    }
//
//    private void sendToLoginActivity() {
//        startActivity(new Intent(DoctorHomeActivity.this, MainActivity.class));
//        finish();
//    }


    @Override
    public void onBackPressed() {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
}
