package com.me.noban.DengueSolution.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.me.noban.DengueSolution.PatientSignUpActivity;
import com.me.noban.DengueSolution.userProfile.DoctorSignUpActivity;

public class SignUpFragment extends Fragment implements View.OnClickListener {

    private CardView mPatientCardView;
    private CardView mDoctorCardView;
    private ImageView mPatientCheckedButton;
    private ImageView mDoctorCheckedButton;

    public SignUpFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(com.me.noban.DengueSolution.R.layout.fragment_sign_up, container, false);
        initView(view);
        return view;
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case com.me.noban.DengueSolution.R.id.fragment_sign_up_doctor_cardView:
                mDoctorCheckedButton.setVisibility(View.VISIBLE);
                goToDoctorSignUpActivity();
                break;

            case com.me.noban.DengueSolution.R.id.fragment_sign_up_patient_cardView:
                mPatientCheckedButton.setVisibility(View.VISIBLE);
                goToPatientSignUpActivity();
                break;
        }
    }

    private void initView(View view) {
        mDoctorCardView = view.findViewById(com.me.noban.DengueSolution.R.id.fragment_sign_up_doctor_cardView);
        mPatientCardView = view.findViewById(com.me.noban.DengueSolution.R.id.fragment_sign_up_patient_cardView);

        mDoctorCheckedButton = view.findViewById(com.me.noban.DengueSolution.R.id.fragment_sign_up_doctor_check_button);
        mPatientCheckedButton = view.findViewById(com.me.noban.DengueSolution.R.id.fragment_sign_up_patient_check_button);

        mDoctorCardView.setOnClickListener(this);
        mPatientCardView.setOnClickListener(this);
    }

    private void goToPatientSignUpActivity() {
        startActivity(new Intent(getActivity(), PatientSignUpActivity.class));
        getActivity().finish();
    }

    private void goToDoctorSignUpActivity() {
        startActivity(new Intent(getActivity(), DoctorSignUpActivity.class));
        getActivity().finish();
    }
}
