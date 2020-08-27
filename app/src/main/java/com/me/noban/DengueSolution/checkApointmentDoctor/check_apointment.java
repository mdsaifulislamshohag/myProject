package com.me.noban.DengueSolution.checkApointmentDoctor;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.me.noban.DengueSolution.model.apoint_model;

public class check_apointment extends AppCompatActivity {

    String uid;
    Context c;
    RecyclerView Mypost;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference Mypost_reference, user;
    FirebaseRecyclerAdapter<apoint_model, checkAppointMentViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.me.noban.DengueSolution.R.layout.activity_check_apointment);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();
        Mypost_reference = database.getReference("data").child("0").child("Apoint");
        Mypost = findViewById(com.me.noban.DengueSolution.R.id.check_apointmet_doc_recykler);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        Mypost.setLayoutManager(mLayoutManager);

        loadsearchMypost();

    }

    public void loadsearchMypost() {

        adapter = new FirebaseRecyclerAdapter<apoint_model, checkAppointMentViewHolder>(apoint_model.class, com.me.noban.DengueSolution.R.layout.check_appointment_card, checkAppointMentViewHolder.class, Mypost_reference.orderByChild("doctorId").equalTo(uid)) {
            @Override
            protected void populateViewHolder(checkAppointMentViewHolder viewHolder, apoint_model model, int position) {
                viewHolder.apName.setText(model.getName());
                viewHolder.apCell.setText(model.getNumber());

            }
        };
        adapter.notifyDataSetChanged();
        Mypost.setAdapter(adapter);
    }
}
