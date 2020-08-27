package com.me.noban.DengueSolution;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.me.noban.DengueSolution.model.Patient;
import com.me.noban.DengueSolution.preferences.UserTypePreference;

public class UserProfileActivity extends AppCompatActivity {

    Toolbar mToolbar;
    private TextView mNameTextView, mAddressTextView, mAgeTextView, mGenderTextView, mBloodGroup;
    private UserTypePreference mUserInfo;
    private Patient mPatient;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile2);

        initActionBar();

        initView();
        setValue();
    }

    private void initActionBar() {
        mToolbar = findViewById(R.id.user_profile_activity_toolbar);
        setSupportActionBar(mToolbar);

        mCollapsingToolbarLayout = findViewById(R.id.user_profile_activity_collapsing_toolbar_layout);
        mCollapsingToolbarLayout.setTitle("Profile");
    }

    private void setValue() {
        mNameTextView.setText(mPatient.getName());
        mAddressTextView.setText(mPatient.getAddress());
        mAgeTextView.setText(mPatient.getAge());
        mGenderTextView.setText(mPatient.getGender());
        mBloodGroup.setText(mPatient.getBloodGroup());
    }

    private void initView() {
        mNameTextView = findViewById(R.id.activity_user_info_name_text_view);
        mGenderTextView = findViewById(R.id.activity_user_info_gender_text_view);
        mAgeTextView = findViewById(R.id.activity_user_info_age_text_view);
        mAddressTextView = findViewById(R.id.activity_user_info_address_text_view);
        mBloodGroup = findViewById(R.id.activity_user_info_blood_group_text_view);

        mUserInfo = UserTypePreference.getPreferences(this);
        mPatient = mUserInfo.getUserInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedItemId = item.getItemId();

        if (selectedItemId == R.id.logout) {
            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else if (selectedItemId == R.id.update) {
            Toast.makeText(this, "update", Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);
    }
}
