package com.me.noban.DengueSolution.BloodBank;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageButton;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.me.noban.DengueSolution.BloodBank.ViewHolder.donerViewholder;
import com.me.noban.DengueSolution.R;
import com.me.noban.DengueSolution.model.Patient;

public class bloodbankHome extends AppCompatActivity {
    RecyclerView Mypost;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference Mypost_reference, user;
    FirebaseRecyclerAdapter<Patient, donerViewholder> adapter;
    ImageButton search;
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        database = FirebaseDatabase.getInstance();
        Mypost_reference = database.getReference("data").child("0").child("patient_info");
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_bloodbank_home);
        Mypost = (RecyclerView) findViewById(R.id.bloodBankRecycklerView);
        //     liscatagory.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        Mypost.setLayoutManager(layoutManager);
        loadMypost();


    }


    private void loadMypost() {
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

    private void loadsearchMypost(String searchtext) {
        Query firebasequery = Mypost_reference.orderByChild("address").startAt(searchtext).endAt(searchtext + "\uf8ff");
        adapter = new FirebaseRecyclerAdapter<Patient, donerViewholder>(Patient.class, R.layout.blooddoner_card_layout, donerViewholder.class, firebasequery) {
            @Override
            protected void populateViewHolder(final donerViewholder viewHolder, final Patient model, int position) {

                viewHolder.Name.setText(model.getName());
                viewHolder.Location.setText(model.getAddress());
                viewHolder.BloodGroup.setText(model.getAge());
            }
        };
        adapter.notifyDataSetChanged();
        Mypost.setAdapter(adapter);
    }
}
