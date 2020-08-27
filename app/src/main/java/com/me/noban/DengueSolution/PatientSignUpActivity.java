package com.me.noban.DengueSolution;

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
import com.me.noban.DengueSolution.model.Patient;
import com.me.noban.DengueSolution.preferences.UserTypePreference;
import com.me.noban.DengueSolution.utilities.StringUtils;

public class PatientSignUpActivity extends AppCompatActivity {

    private EditText mNameEditText;
    private EditText mAddressEditText;
    private EditText mAgeEditText;
    private EditText mMobileEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;

    private Spinner mBloodGroupSpinner;
    private Spinner mGenderSpinner;

    private Button mCreateButton;

    private ArrayAdapter<CharSequence> mGenderAdapter;
    private ArrayAdapter<CharSequence> mBloodGroupAdapter;

    private FirebaseAuth auth;
    private DatabaseReference mPatientInfoDatabaseRef;
    private ProgressDialog mProgressDialog;

    private String gender;
    private String bloodGroup;
    private String userId;

    private UserTypePreference mUserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_sign_up);

        initAll();

        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mNameEditText.getText().toString();
                String address = mAddressEditText.getText().toString();
                String age = mAgeEditText.getText().toString();
                String mobile = mMobileEditText.getText().toString();
                String email = mEmailEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();

                bloodGroup = mBloodGroupSpinner.getSelectedItem().toString();
                gender = mGenderSpinner.getSelectedItem().toString();


                if (!StringUtils.isNullOrEmpty(name, address, age, gender, bloodGroup, mobile, email, password)) {
                    attemptToSignUp(name, address, age, mobile, email, password);
                } else {
                    Toast.makeText(PatientSignUpActivity.this, "Please Fill all the Fields ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initAll() {
        auth = FirebaseAuth.getInstance();
        mPatientInfoDatabaseRef = FirebaseDatabase.getInstance().getReference().child("data").child("0").child("patient_info");

        mNameEditText = findViewById(R.id.patient_sign_up_name_editText);
        mAddressEditText = findViewById(R.id.patient_sign_up_address_editText);
        mAgeEditText = findViewById(R.id.patient_sign_up_age_editText);
        mMobileEditText = findViewById(R.id.patient_sign_up_mobile_editText);
        mEmailEditText = findViewById(R.id.patient_sign_up_email_editText);
        mPasswordEditText = findViewById(R.id.patient_sign_up_password_editText);

        mCreateButton = findViewById(R.id.patient_sign_up_account_button);

        mUserType = UserTypePreference.getPreferences(this);

        setUpSpinnerForGender();
        setUpSpinnerForBloodGroup();
    }

    private void setUpSpinnerForGender() {
        mGenderSpinner = findViewById(R.id.patient_sign_up_gender_spinner);

        mGenderAdapter = ArrayAdapter.createFromResource(PatientSignUpActivity.this, R.array.gender_list, android.R.layout.simple_spinner_item);
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

    private void setUpSpinnerForBloodGroup() {
        mBloodGroupSpinner = findViewById(R.id.patient_sign_up_blood_group_spinner);

        mBloodGroupAdapter = ArrayAdapter.createFromResource(PatientSignUpActivity.this, R.array.name_of_blood_group, android.R.layout.simple_spinner_item);
        mBloodGroupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBloodGroupSpinner.setAdapter(mBloodGroupAdapter);
        mBloodGroupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bloodGroup = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void attemptToSignUp(final String name, final String address,
                                 final String age, final String mobile,
                                 final String email, final String password) {

        showProgressDialog();

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                hideProgressDilog();

                if (task.isSuccessful()) {
                    Toast.makeText(PatientSignUpActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();

                    mUserType.setUserType("Patient");

                    userId = auth.getCurrentUser().getUid();
                    saveUserStateToFirebaseDatabase(userId, name, address, age, mobile, email, password);

                    goToPatientHomePage();
                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException weakPassword) {
                        Toast.makeText(PatientSignUpActivity.this, "Weak Password",
                                Toast.LENGTH_LONG).show();
                    } catch (FirebaseAuthInvalidCredentialsException malformedEmail) {
                        Toast.makeText(PatientSignUpActivity.this, "Mal-Formed Email",
                                Toast.LENGTH_LONG).show();
                    } catch (FirebaseAuthUserCollisionException existEmail) {
                        Toast.makeText(PatientSignUpActivity.this, "This Email already exist",
                                Toast.LENGTH_LONG).show();
                    } catch (Exception exception) {
                        Toast.makeText(PatientSignUpActivity.this, "" + exception.getMessage().toString(),
                                Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

    }

    private void goToPatientHomePage() {
        startActivity(new Intent(PatientSignUpActivity.this, MainUIActivity.class));
        finish();
    }

    private void saveUserStateToFirebaseDatabase(String userId, String name, String address,
                                                 String age, String mobile, String email,
                                                 String password) {

        if (auth.getCurrentUser() != null) {
            Patient patient = new Patient(userId, name, address, age, gender, bloodGroup, mobile, email, password);

            mUserType.setUserInfo(patient);

            mPatientInfoDatabaseRef.child(bloodGroup)
                    .child(userId)
                    .setValue(patient);
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
