package com.me.noban.DengueSolution.userProfile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.me.noban.DengueSolution.DoctorHomeActivity;
import com.me.noban.DengueSolution.R;
import com.me.noban.DengueSolution.model.Doctor;
import com.me.noban.DengueSolution.preferences.UserTypePreference;
import com.me.noban.DengueSolution.utilities.StringUtils;

public class DoctorSignUpActivity extends AppCompatActivity {

    private EditText mNameEditText;
    private EditText mTitleEditText;
    private EditText mDegreEditText;
    private EditText mMobileEditText;
    private EditText mEmailEditText;
    private EditText mPaswordEditText;
    private EditText mBMDCEditText;

    private Spinner mGenderSpinner;
    private Spinner mSpecialitySpinner;

    private Button mCreateButton;

    private ArrayAdapter<CharSequence> mBranchAdapter;
    private ArrayAdapter<CharSequence> mGenderAdapter;

    private FirebaseAuth auth;
    private DatabaseReference mDoctorInfoDatabaseRef;
    private ProgressDialog mProgressDialog;

    private String speciality;
    private String gender;
    private String userId;

    private UserTypePreference mUserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_sign_up);

        initAll();

        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mNameEditText.getText().toString();
                String title = mTitleEditText.getText().toString();
                String degree = mDegreEditText.getText().toString();
                String mobile = mMobileEditText.getText().toString();
                String email = mEmailEditText.getText().toString();
                String password = mPaswordEditText.getText().toString();
                String bmdc = mBMDCEditText.getText().toString();
                speciality = mSpecialitySpinner.getSelectedItem().toString();
                gender = mGenderSpinner.getSelectedItem().toString();


                if (!StringUtils.isNullOrEmpty(name, title, degree, mobile, email, password, bmdc, gender, speciality)) {
                    attemptToSignUp(name, title, degree, mobile, email, password, bmdc);
                } else {
                    Toast.makeText(DoctorSignUpActivity.this, "Please Fill all the Fields ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initAll() {
        auth = FirebaseAuth.getInstance();
        mDoctorInfoDatabaseRef = FirebaseDatabase.getInstance().getReference().child("data").child("0").child("doctor_info");

        mNameEditText = findViewById(R.id.doctor_sign_up_name_editText);
        mTitleEditText = findViewById(R.id.doctor_sign_up_title_editText);
        mDegreEditText = findViewById(R.id.doctor_sign_up_degree_editText);
        mMobileEditText = findViewById(R.id.doctor_sign_up_mobile_editText);
        mEmailEditText = findViewById(R.id.doctor_sign_up_email_editText);
        mPaswordEditText = findViewById(R.id.doctor_sign_up_password_editText);
        mBMDCEditText = findViewById(R.id.doctor_sign_up_bmdc_editText);

        mCreateButton = findViewById(R.id.doctor_sign_up_account_button);

        mUserType = UserTypePreference.getPreferences(this);

        setUpSpinnerForGender();
        setUpSpinnerForMedicalBranch();
    }

    private void setUpSpinnerForGender() {
        mGenderSpinner = findViewById(R.id.doctor_sign_up_gender_spinner);

        mGenderAdapter = ArrayAdapter.createFromResource(DoctorSignUpActivity.this, R.array.gender_list, android.R.layout.simple_spinner_item);
        mGenderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGenderSpinner.setAdapter(mGenderAdapter);
        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setUpSpinnerForMedicalBranch() {
        mSpecialitySpinner = findViewById(R.id.doctor_sign_up_speciality_spinner);

        mBranchAdapter = ArrayAdapter.createFromResource(DoctorSignUpActivity.this, R.array.name_of_sector, android.R.layout.simple_spinner_item);
        mBranchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpecialitySpinner.setAdapter(mBranchAdapter);
        mSpecialitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                speciality = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void attemptToSignUp(final String name, final String title,
                                 final String degree, final String mobile,
                                 final String email, final String password,
                                 final String bmdc) {

        showProgressDialog();

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                hideProgressDilog();

                if (task.isSuccessful()) {
                    Toast.makeText(DoctorSignUpActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();

                    mUserType.setUserType("Doctor");

                    userId = auth.getCurrentUser().getUid();
                    saveUserStateToFirebaseDatabase(userId, name, title, degree, mobile, email, password, bmdc);

                    goToDoctorHomePage();
                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException weakPassword) {
                        Toast.makeText(DoctorSignUpActivity.this, "Weak Password",
                                Toast.LENGTH_LONG).show();
                    } catch (FirebaseAuthInvalidCredentialsException malformedEmail) {
                        Toast.makeText(DoctorSignUpActivity.this, "Mal-Formed Email",
                                Toast.LENGTH_LONG).show();
                    } catch (FirebaseAuthUserCollisionException existEmail) {
                        Toast.makeText(DoctorSignUpActivity.this, "This Email already exist",
                                Toast.LENGTH_LONG).show();
                    } catch (Exception exception) {
                        Toast.makeText(DoctorSignUpActivity.this, "" + exception.getMessage().toString(),
                                Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

    }

    private void goToDoctorHomePage() {
        startActivity(new Intent(DoctorSignUpActivity.this, DoctorHomeActivity.class));
        finish();
    }

    private void saveUserStateToFirebaseDatabase(String userId, String name, String title,
                                                 String degree, String mobile, String email,
                                                 String password, String bmdc) {

        if (auth.getCurrentUser() != null) {
            Doctor doctor = new Doctor(userId, name, speciality, gender, title, degree, mobile, email, password, bmdc);

            mDoctorInfoDatabaseRef.child(speciality)
                    .child(userId)
                    .setValue(doctor);
        }

    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Loading ...");
        }
    }

    private void hideProgressDilog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}


