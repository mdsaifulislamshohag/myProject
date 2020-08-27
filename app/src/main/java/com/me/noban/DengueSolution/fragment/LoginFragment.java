package com.me.noban.DengueSolution.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.me.noban.DengueSolution.DoctorHomeActivity;
import com.me.noban.DengueSolution.MainUIActivity;
import com.me.noban.DengueSolution.utilities.StringUtils;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private EditText mUserEditText, mPasswordEditText;
    private Button mLoginButton;
    private RadioGroup mUserTypeRadioGroup;

    private FirebaseAuth mAuth;
    private ProgressDialog mProgressDialog;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(com.me.noban.DengueSolution.R.layout.fragment_login_main, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mUserEditText = view.findViewById(com.me.noban.DengueSolution.R.id.fragment_login_in_user_mail_edit_text);
        mPasswordEditText = view.findViewById(com.me.noban.DengueSolution.R.id.fragment_login_in_password_edit_text);
        mLoginButton = view.findViewById(com.me.noban.DengueSolution.R.id.fragment_login_button);
        mUserTypeRadioGroup = view.findViewById(com.me.noban.DengueSolution.R.id.fragment_user_type);

        mAuth = FirebaseAuth.getInstance();

        mLoginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        login();
    }


    private void login() {
        String email = mUserEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();

        if (!StringUtils.isNullOrEmpty(email, password)) {
            attemptToLogIn(email, password);
        } else {
            if (StringUtils.isNullOrEmpty(email) && StringUtils.isNullOrEmpty(password)) {
                Toast.makeText(getContext(), "Please Fill Both Field",
                        Toast.LENGTH_LONG).show();
            } else if (StringUtils.isNullOrEmpty(email) && !StringUtils.isNullOrEmpty(password)) {
                Toast.makeText(getContext(), "Please Insert Email",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Please Insert Password",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void attemptToLogIn(String email, String password) {
        showProgressDialog();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                hideProgressDilog();

                if (task.isSuccessful()) {
                    goToDashBoardActivity();
                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException invalidEmail) {
                        Toast.makeText(getContext(), "Invalid Email",
                                Toast.LENGTH_LONG).show();
                    } catch (FirebaseAuthInvalidCredentialsException wrongPassword) {
                        Toast.makeText(getContext(), "Wrong Password",
                                Toast.LENGTH_LONG).show();
                    } catch (Exception exception) {
                        Toast.makeText(getContext(), "" + exception.getMessage().toString(),
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }


    private void goToDashBoardActivity() {
        int selectedUserType = mUserTypeRadioGroup.getCheckedRadioButtonId();

        switch (selectedUserType) {
            case -1:
                Toast.makeText(getContext(), "Please Checked One Type", Toast.LENGTH_SHORT).show();
                break;

            case com.me.noban.DengueSolution.R.id.doctor_radio_button:
                goToDoctorDashboardActivity();
                break;

            case com.me.noban.DengueSolution.R.id.patient_radio_button:
                goToPatientDashboardActivity();
                break;
        }

    }


    private void goToPatientDashboardActivity() {
        startActivity(new Intent(getActivity(), MainUIActivity.class));
        getActivity().finish();
    }

    private void goToDoctorDashboardActivity() {
        startActivity(new Intent(getActivity(), DoctorHomeActivity.class));
        getActivity().finish();
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getContext());
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
