package com.me.noban.DengueSolution.Apointment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.me.noban.DengueSolution.Apointment.util.SearchForAppointmentUtil;
import com.me.noban.DengueSolution.R;

public class ApointMentHome extends Fragment {

    ArrayAdapter<String> adapter;
    AutoCompleteTextView searchText;
    FloatingActionButton it;


    private ArrayAdapter<CharSequence> mBranchAdapter;
    private ArrayAdapter<CharSequence> mGenderAdapter;

    private Spinner mSpecialitySpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.apointmen_home_fagment, container, false);

        it = view.findViewById(R.id.appointment_searchbutton);
        it.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoNext();
            }
        });


        mSpecialitySpinner = view.findViewById(R.id.spinner_doc_speciality);
        setUpSpinnerForMedicalBranch();


        return view;
    }

    private void gotoNext() {
        Fragment fragment = new ApointmentListDoctor();

        FragmentManager fragmentManager = this.getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment3, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    private void setUpSpinnerForMedicalBranch() {


        mBranchAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.name_of_sector, android.R.layout.simple_spinner_item);
        mBranchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpecialitySpinner.setAdapter(mBranchAdapter);
        mSpecialitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SearchForAppointmentUtil.scpecialityDoctorSearch = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


}
