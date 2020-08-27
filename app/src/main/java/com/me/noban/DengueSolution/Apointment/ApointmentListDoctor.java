package com.me.noban.DengueSolution.Apointment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import com.google.firebase.database.ValueEventListener;
import com.me.noban.DengueSolution.Apointment.util.SearchForAppointmentUtil;
import com.me.noban.DengueSolution.R;
import com.me.noban.DengueSolution.model.Doctor;
import com.me.noban.DengueSolution.model.apoint_model;

public class ApointmentListDoctor extends Fragment {

    Context c;
    RecyclerView Mypost;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference Mypost_reference, Mypost_reference2, user;
    ImageButton search;
    EditText et;
    FirebaseRecyclerAdapter<Doctor, ApointmentViewHolder> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.apointment_doctor_list, container, false);
        Mypost = view.findViewById(R.id.apointment_doctor_list_recykler);
        layoutManager = new LinearLayoutManager(c);
        Mypost.setLayoutManager(layoutManager);
        database = FirebaseDatabase.getInstance();
        c = getActivity();


        Mypost_reference = database.getReference("data").child("0").child("doctor_info");
        Mypost_reference2 = database.getReference("data").child("0");
        ;

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

        adapter = new FirebaseRecyclerAdapter<Doctor, ApointmentViewHolder>(Doctor.class, R.layout.apointment_card_layout, ApointmentViewHolder.class, Mypost_reference.child(SearchForAppointmentUtil.scpecialityDoctorSearch)) {
            @Override
            protected void populateViewHolder(ApointmentViewHolder viewHolder, final Doctor model, int position) {
                viewHolder.speciality.setText(model.getSpeciality());
                viewHolder.docName.setText(model.getName());
                viewHolder.doclocation.setText(model.getTitle());
                viewHolder.appointNow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showChangeLangDialog(model.getUserId());
                    }
                });

            }


        };
        adapter.notifyDataSetChanged();
        Mypost.setAdapter(adapter);


    }

    private void apoint() {
    }


    public void showChangeLangDialog(final String docId) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.apointment_alart_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText name = (EditText) dialogView.findViewById(R.id.ap_alart_name);
        final EditText cell = (EditText) dialogView.findViewById(R.id.ap_alart_cell_number);


        dialogBuilder.setPositiveButton("Request", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                apoint_model ap = new apoint_model(name.getText().toString(), cell.getText().toString(), docId);
                Mypost_reference2.child("Apoint").child(System.currentTimeMillis() + name.getText().toString()).setValue(ap);
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }
}
