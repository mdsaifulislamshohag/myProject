package com.me.noban.DengueSolution.BloodBank;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.me.noban.DengueSolution.BloodBank.ViewHolder.donerViewholder;
import com.me.noban.DengueSolution.BloodBank.adapter.pagerAdapter;
import com.me.noban.DengueSolution.R;
import com.me.noban.DengueSolution.model.Patient;

public class bloodbank extends android.support.v4.app.Fragment {
    Context c;
    RecyclerView Mypost;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference Mypost_reference, user;
    FirebaseRecyclerAdapter<Patient, donerViewholder> adapter;
    ImageButton search;
    EditText et;
    FloatingActionButton ft;

    public bloodbank() {
        // Required empty public constructor
    }

    public static bloodbank newInstance(String param1, String param2) {
        bloodbank fragment = new bloodbank();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = inflater.inflate(R.layout.activity_bloodbank_home, container, false);
        database = FirebaseDatabase.getInstance();
        c = getActivity();
        Mypost_reference = database.getReference("data").child("0").child("blood_doners");

        //     liscatagory.setHasFixedSize(true);
        //Tab Initialization
        TabLayout tabLayout = view.findViewById(R.id.bloodGroup_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("A+"));
        tabLayout.addTab(tabLayout.newTab().setText("B+"));
        tabLayout.addTab(tabLayout.newTab().setText("O+"));
        tabLayout.addTab(tabLayout.newTab().setText("AB+"));
        tabLayout.addTab(tabLayout.newTab().setText("A-"));
        tabLayout.addTab(tabLayout.newTab().setText("B-"));
        tabLayout.addTab(tabLayout.newTab().setText("O-"));
        tabLayout.addTab(tabLayout.newTab().setText("AB-"));
        final ViewPager viewPager = view.findViewById(R.id.bloodgroup_view_pager);
        final pagerAdapter adapter = new pagerAdapter
                (getFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        return view;
    }

    public void loadMypost() {
        adapter = new FirebaseRecyclerAdapter<Patient, donerViewholder>(Patient.class, R.layout.blooddoner_card_layout, donerViewholder.class, Mypost_reference) {
            @Override
            protected void populateViewHolder(final donerViewholder viewHolder, final Patient model, int position) {

                viewHolder.Name.setText(model.getName());
                viewHolder.Location.setText(model.getAddress());
                viewHolder.BloodGroup.setText(model.getBloodGroup());
            }
        };
        adapter.notifyDataSetChanged();
        Mypost.setAdapter(adapter);
    }

    public void loadsearchMypost(String searchtext) {
        Query firebasequery = Mypost_reference.orderByChild("location").startAt(searchtext).endAt(searchtext + "\uf8ff");
        adapter = new FirebaseRecyclerAdapter<Patient, donerViewholder>(Patient.class, R.layout.blooddoner_card_layout, donerViewholder.class, firebasequery) {
            @Override
            protected void populateViewHolder(final donerViewholder viewHolder, final Patient model, int position) {

                viewHolder.Name.setText(model.getName());
                viewHolder.Location.setText(model.getAddress());
                viewHolder.BloodGroup.setText(model.getBloodGroup());
            }
        };
        adapter.notifyDataSetChanged();
        Mypost.setAdapter(adapter);
    }

}
