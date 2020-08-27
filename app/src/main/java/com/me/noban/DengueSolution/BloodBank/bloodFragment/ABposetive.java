package com.me.noban.DengueSolution.BloodBank.bloodFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.me.noban.DengueSolution.BloodBank.ViewHolder.donerViewholder;
import com.me.noban.DengueSolution.BloodBank.utils.SearchUtil;
import com.me.noban.DengueSolution.R;
import com.me.noban.DengueSolution.model.Patient;

public class ABposetive extends Fragment {
    Context c;
    RecyclerView Mypost;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference Mypost_reference, user;
    ImageButton search;
    EditText et;
    FirebaseRecyclerAdapter<Patient, donerViewholder> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.bloodxml, container, false);
        Mypost = view.findViewById(R.id.bloodBankRecycklerView);
        layoutManager = new LinearLayoutManager(c);
        Mypost.setLayoutManager(layoutManager);
        database = FirebaseDatabase.getInstance();
        c = getActivity();


        Mypost_reference = database.getReference("data").child("0").child("patient_info").child("AB+");
        Mypost_reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    loadMypost();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        loadMypost();
        return view;


    }

    private void loadMypost() {


        if (!SearchUtil.Searchloaction.equals("")) {
            Query firebasequery1 = Mypost_reference.orderByChild("address").equalTo(SearchUtil.Searchloaction);
            adapter = new FirebaseRecyclerAdapter<Patient, donerViewholder>(Patient.class, R.layout.blooddoner_card_layout, donerViewholder.class, firebasequery1) {
                @Override
                protected void populateViewHolder(final donerViewholder viewHolder, final Patient model, int position) {

                    viewHolder.Name.setText(model.getName());
                    viewHolder.Location.setText(model.getAddress());
                    viewHolder.BloodGroup.setText(model.getBloodGroup());
                    viewHolder.Name.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
                }
            };
            adapter.notifyDataSetChanged();
            Mypost.setAdapter(adapter);
        } else {
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
    }

    public void loadsearchMypost(String searchtext) {
        Query firebasequery = Mypost_reference.orderByChild("address").startAt(searchtext).endAt(searchtext + "\uf8ff");

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
