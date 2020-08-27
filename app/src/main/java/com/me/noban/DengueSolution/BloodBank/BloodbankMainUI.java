package com.me.noban.DengueSolution.BloodBank;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.me.noban.DengueSolution.BloodBank.utils.SearchUtil;
import com.me.noban.DengueSolution.R;

public class BloodbankMainUI extends Fragment {

    ArrayAdapter<String> adapter;
    AutoCompleteTextView searchText;
    FloatingActionButton it;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.bloodbankmainfragment, container, false);


        String[] dis = getResources().getStringArray(R.array.district);

        adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, dis);


        searchText = view.findViewById(R.id.SearcheditTextblood);
        searchText.setAdapter(adapter);
        it = view.findViewById(R.id.bloodbutton);
        it.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoSearch();
            }
        });


        return view;
    }


    public void gotoSearch() {
        SearchUtil.Searchloaction = searchText.getText().toString();
        Fragment fragment = new bloodbank();
        FragmentManager fragmentManager = this.getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment3, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}
